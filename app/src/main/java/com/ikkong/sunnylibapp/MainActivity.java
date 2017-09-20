package com.ikkong.sunnylibapp;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.View;

import com.ikkong.imagepreview.ui.KKImagePreviewActivity;
import com.ikkong.sunnylibapp.delegate.MainDelegate;
import com.ikkong.sunnylibapp.fragment.HotwxListFragment;
import com.ikkong.sunnylibapp.fragment.JokeContainerFragment;
import com.ikkong.sunnylibapp.fragment.WebFragment;
import com.ikkong.sunnylibrary.base.BaseFrameActivity;
import com.ikkong.sunnylibrary.base.BaseMainFragment;
import com.ikkong.sunnylibrary.model.Event;
import com.kymjs.rxvolley.rx.RxBus;
import com.kymjs.rxvolley.toolbox.Loger;

import rx.Subscription;
import rx.functions.Action1;


public class MainActivity extends BaseFrameActivity<MainDelegate> {
    public static final String MENU_CLICK_EVEN = "slid_menu_click_event";

    private BaseMainFragment currentFragment; //当前内容所显示的Fragment
    private BaseMainFragment content1 = new HotwxListFragment();
    private BaseMainFragment content2 = new JokeContainerFragment();
    private BaseMainFragment content3 = new WebFragment();

    private Subscription rxBusSubscript;

    @Override
    protected Class<MainDelegate> getDelegateClass() {
        return MainDelegate.class;
    }


    private static long DOUBLE_CLICK_TIME = 0L;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (viewDelegate.menuIsOpen()) {
                viewDelegate.changeMenuState();
            } else {
                if ((System.currentTimeMillis() - DOUBLE_CLICK_TIME) > 2000) {
                    viewDelegate.toast(getString(R.string.double_click_exit));
                    DOUBLE_CLICK_TIME = System.currentTimeMillis();
                } else {
                    finish();
                }
            }
            return true;
        } else {
            return super.onKeyDown(keyCode, event);
        }
    }

    /**
     * 用Fragment替换内容区
     *
     * @param targetFragment 用来替换的Fragment
     */
    public void changeFragment(BaseMainFragment targetFragment) {
        if (targetFragment.equals(currentFragment)) {
            return;
        }
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        if (!targetFragment.isAdded()) {
            transaction.add(R.id.main_content, targetFragment, targetFragment.getClass()
                    .getName());
            targetFragment.onShow();
        }
        if (targetFragment.isHidden()) {
            transaction.show(targetFragment);
            targetFragment.onShow();
        }
        if (currentFragment != null && currentFragment.isVisible()) {
            transaction.hide(currentFragment);
        }
        currentFragment = targetFragment;
        transaction.commit();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        changeFragment(content1);
        rxBusSubscript = RxBus.getDefault().take(Event.class)
                .subscribe(new Action1<Event>() {
                    @Override
                    public void call(Event event) {
                        Loger.debug("==接收到===MainSlidMenu=onClick====");
                        changeContent(event);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                    }
                });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (rxBusSubscript != null && rxBusSubscript.isUnsubscribed())
            rxBusSubscript.unsubscribe();
    }

    /**
     * 根据MainSlidMenu点击选择不同的响应
     *
     * @param event
     */
    private void changeContent(Event event) {
        if (!MENU_CLICK_EVEN.equals(event.getAction())) return;
        View view = event.getObject();
        switch (view.getId()) {
            case R.id.menu_item_tag1:
                changeFragment(content1);
                break;
            case R.id.menu_item_tag2:
                changeFragment(content2);
                break;
            case R.id.menu_item_tag3:
                KKImagePreviewActivity.goPreview(this,1,getData());
                break;
            case R.id.menu_item_tag4:
                changeFragment(content3);
                break;
            default:
                break;
        }
        viewDelegate.changeMenuState();
        onMenuSelected(view);
    }

    private String[] getData() {
        return new String[]{
                imageUrls1[0],imageUrls1[1],imageUrls1[2],imageUrls1[3]
        };
    }

    private final static String[] imageUrls1 = new String[]{
            "http://img4.duitang.com/uploads/blog/201311/04/20131104193715_NCexN.thumb.jpeg",
            "http://cdn.duitang.com/uploads/blog/201401/07/20140107223310_LH3Uy.thumb.jpeg",
            "http://img5.duitang.com/uploads/item/201405/09/20140509222156_kVexJ.thumb.jpeg",
            "http://img5.duitang.com/uploads/item/201306/14/20130614185903_raNR3.thumb.jpeg",
    };
    
    private void onMenuSelected(View view) {
        viewDelegate.get(R.id.menu_item_tag1).setBackgroundResource(R.drawable.selector_text_bg);
        viewDelegate.get(R.id.menu_item_tag2).setBackgroundResource(R.drawable.selector_text_bg);
        viewDelegate.get(R.id.menu_item_tag3).setBackgroundResource(R.drawable.selector_text_bg);
        viewDelegate.get(R.id.menu_item_tag4).setBackgroundResource(R.drawable.selector_text_bg);
        view.setBackgroundResource(R.drawable.bg_text_press);
    }
}