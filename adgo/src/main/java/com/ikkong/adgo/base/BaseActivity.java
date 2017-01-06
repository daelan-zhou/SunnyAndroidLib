package com.ikkong.adgo.base;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;

import com.ikkong.adgo.R;
import com.ikkong.adgo.helper.SimplexToast;
import com.ikkong.adgo.helper.dialog.DialogControl;
import com.ikkong.adgo.helper.dialog.DialogHelper;
import com.ikkong.adgo.interf.BaseViewInterface;
import com.ikkong.adgo.utils.TDevice;

import butterknife.ButterKnife;

/**
 * baseActionBar Activity
 *
 * @author FireAnt（http://my.oschina.net/LittleDY）
 * @created 2014年9月25日 上午11:30:15 引用自：tonlin
 */
public abstract class BaseActivity extends AppCompatActivity implements
        DialogControl, View.OnClickListener, BaseViewInterface {
    private boolean _isVisible;
    private ProgressDialog _waitDialog;

    protected LayoutInflater mInflater;
    protected ActionBar mActionBar;

    private final String packageName4Umeng = this.getClass().getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        onBeforeSetContentLayout();
        if (getLayoutId() != 0) {
            setContentView(getLayoutId());
        }
        mActionBar = getSupportActionBar();
        mInflater = getLayoutInflater();
        if (hasActionBar()) {
            initActionBar(mActionBar);
        }

        // 通过注解绑定控件
        ButterKnife.bind(this);

        init(savedInstanceState);
        initView();
        initData();
        _isVisible = true;

    }

    @Override
    protected void onPause() {
        super.onPause();
        if (this.isFinishing()) {
            TDevice.hideSoftKeyboard(getCurrentFocus());
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    protected void onBeforeSetContentLayout() {
    }

    protected boolean hasActionBar() {
        return getSupportActionBar() != null;
    }

    protected int getLayoutId() {
        return 0;
    }

    protected View inflateView(int resId) {
        return mInflater.inflate(resId, null);
    }

    protected int getActionBarTitle() {
        return R.string.app_name;
    }

    protected boolean hasBackButton() {
        return false;
    }

    protected void init(Bundle savedInstanceState) {
    }

    protected void initActionBar(ActionBar actionBar) {
        if (actionBar == null)
            return;
        if (hasBackButton()) {
            mActionBar.setDisplayHomeAsUpEnabled(true);
            mActionBar.setHomeButtonEnabled(true);
        } else {
            actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_TITLE);
            actionBar.setDisplayUseLogoEnabled(false);
            int titleRes = getActionBarTitle();
            if (titleRes != 0) {
                actionBar.setTitle(titleRes);
            }
        }
    }

    public void setActionBarTitle(int resId) {
        if (resId != 0) {
            setActionBarTitle(getString(resId));
        }
    }

    public void setActionBarTitle(String title) {
        if (TextUtils.isEmpty(title)) {
            title = getString(R.string.app_name);
        }
        if (hasActionBar() && mActionBar != null) {
            mActionBar.setTitle(title);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;

            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }
    
    public void showToast(String msg){
        SimplexToast.show(msg);
    }

    @Override
    public ProgressDialog showWaitDialog() {
        return showWaitDialog("加载中...");
    }

    @Override
    public ProgressDialog showWaitDialog(int resid) {
        return showWaitDialog(getString(resid));
    }

    @Override
    public ProgressDialog showWaitDialog(String message) {
        if (_isVisible) {
            if (_waitDialog == null) {
                _waitDialog = DialogHelper.getProgressDialog(this, message);
            }
            if (_waitDialog != null) {
                _waitDialog.setMessage(message);
                _waitDialog.show();
            }
            return _waitDialog;
        }
        return null;
    }

    @Override
    public void hideWaitDialog() {
        if (_isVisible && _waitDialog != null) {
            try {
                _waitDialog.dismiss();
                _waitDialog = null;
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }
}
