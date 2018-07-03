package com.yilong.keyboard;

import android.content.Intent;
import android.view.View;

import butterknife.OnClick;

public class MainActivity extends BaseActivity {

    @Override
    protected void setContentView() {
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void initPresenter() {

    }

    @Override
    protected void initUI() {

    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {

    }

    @OnClick(R.id.bottomInput)
    public void btnonClick(View v) {
        BlurKeyboradActivity.bgView = getWindow().getDecorView().getRootView();
        startActivity(new Intent(this, BlurKeyboradActivity.class));
    }

}
