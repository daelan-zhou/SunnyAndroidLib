package com.ikkong.sunnylibapp.fragment;

import android.os.Bundle;
import android.support.v4.view.ViewPager;

import com.ikkong.sunnylibapp.R;
import com.ikkong.sunnylibapp.delegate.JokeContainerDelegate;
import com.ikkong.sunnylibrary.base.BaseMainFragment;
import com.ogaclejapan.smarttablayout.SmartTabLayout;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItemAdapter;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItems;

/**
 * Author:  ikkong
 * Email:   ikkong@163.com
 * Date:    2016/4/25
 * Description:
 */
public class JokeContainerFragment extends BaseMainFragment<JokeContainerDelegate> {
    ViewPager viewPager;
    @Override
    protected Class getDelegateClass() {
        return JokeContainerDelegate.class;
    }

    @Override
    protected void bindEvenListener() {
        super.bindEvenListener();
        Bundle bc = new Bundle();
        bc.putInt(JokeListFragment.TYPE_KEY,JokeListFragment.TYPE_JOKEC);
        Bundle bi = new Bundle();
        bi.putInt(JokeListFragment.TYPE_KEY,JokeListFragment.TYPE_JOKEI);
        FragmentPagerItemAdapter adapter = new FragmentPagerItemAdapter(
                getFragmentManager(), FragmentPagerItems.with(viewDelegate.getActivity())
                .add("笑话", JokeListFragment.class, bc)
                .add("趣图", JokeListFragment.class, bi)
                .create());

        viewPager = viewDelegate.get(R.id.viewpager);
        viewPager.setAdapter(adapter);

        SmartTabLayout viewPagerTab = viewDelegate.get(R.id.viewpagertab);
        viewPagerTab.setViewPager(viewPager);
    }
}
