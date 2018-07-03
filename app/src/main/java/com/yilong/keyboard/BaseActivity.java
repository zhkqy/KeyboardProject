package com.yilong.keyboard;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;

import butterknife.ButterKnife;

public abstract class BaseActivity extends Activity {
    protected Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (isBroughtToFront()) {
            finish();
            return;
        }
        mContext = this;
        setContentView();

        ButterKnife.bind(this);

        initPresenter();
        initUI();
        initListener();
        initData();
    }


    /**
     * 设置沉浸式状态栏
     */
    protected void setImmergeState() {
        // 当系统版本为4.4或者4.4以上时可以使用沉浸式状态栏
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            // 透明状态栏
            getWindow().addFlags(
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                getWindow().setStatusBarColor(
                        getResources().getColor(R.color.black));
            }
            LinearLayout linear_bar = (LinearLayout) findViewById(R.id.linear_bar);
            if (linear_bar == null) {
                return;
            }
            linear_bar.setVisibility(View.VISIBLE);
            int statusHeight = getStatusBarHeight();
            ViewGroup.LayoutParams params = linear_bar
                    .getLayoutParams();

            if (params != null) {
                params.height = statusHeight;
                linear_bar.setLayoutParams(params);
            }
        }
    }

    /**
     * 用于获取状态栏的高度。 使用Resource对象获取
     *
     * @return 返回状态栏高度的像素值。
     */
    public int getStatusBarHeight() {
        int result = 0;
        int resourceId = getResources().getIdentifier("status_bar_height",
                "dimen", "android");
        if (resourceId > 0) {
            result = getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }


    protected boolean isBroughtToFront() {
        return false;
    }

    protected abstract void setContentView();

    protected abstract void initPresenter();

    protected abstract void initUI();

    protected abstract void initListener();

    protected abstract void initData();


}
