package com.yilong.keyboard;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

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

    protected boolean isBroughtToFront() {
        return false;
    }

    protected abstract void setContentView();

    protected abstract void initPresenter();

    protected abstract void initUI();

    protected abstract void initListener();

    protected abstract void initData();


}
