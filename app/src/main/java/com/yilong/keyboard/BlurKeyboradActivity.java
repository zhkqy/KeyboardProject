package com.yilong.keyboard;

import android.animation.ObjectAnimator;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.view.View;
import android.widget.ImageView;

import com.yilong.keyboard.utils.BlurBitmapUtil;
import com.yilong.keyboard.utils.ScreenUtils;
import com.yilong.keyboard.view.BlurKeyboradView;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * 评论弹窗
 */

public class BlurKeyboradActivity extends BaseActivity {

    public static View bgView;

    private Bitmap bgBitmap;
    private Bitmap blurBitmap;
    private Bitmap cutBitmap;

    @BindView(R.id.imgbg)
    public ImageView imgbg;

    @BindView(R.id.blurkey)
    public BlurKeyboradView blurKeyboradView;


    @Override
    protected void setContentView() {
        setContentView(R.layout.ac_blur_keyboard);
        setImmergeState();
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
        if (bgView != null) {
            bgBitmap = loadBitmapFromView(bgView);

            int bottomHeight = ScreenUtils.getBottomKeyboardHeight(this);

            if (bottomHeight > 0) {
                int w = bgBitmap.getWidth(); // 得到图片的宽，高
                int h = bgBitmap.getHeight();
                cutBitmap = Bitmap.createBitmap(bgBitmap, 0, 0, w, h - bottomHeight, null, false);
                blurBitmap = BlurBitmapUtil.blurBitmap(this, cutBitmap, 12f);
            } else {
                blurBitmap = BlurBitmapUtil.blurBitmap(this, bgBitmap, 12f);
            }

            imgbg.setImageBitmap(blurBitmap);

            ObjectAnimator animator = ObjectAnimator.ofFloat(imgbg, "alpha", 0f, 1f);
            animator.setDuration(300);
            animator.start();
        }
    }

    private Bitmap loadBitmapFromView(View v) {
        int w = v.getWidth();
        int h = v.getHeight();
        Bitmap bmp = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(bmp);

        v.layout(0, 0, w, h);
        v.draw(c);

        return bmp;
    }

    @OnClick(R.id.imgbg)
    public void imgOnclicked(View v) {
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (bgBitmap != null) {
            bgBitmap.recycle();
            bgBitmap = null;
        }
        if (blurBitmap != null) {
            blurBitmap.recycle();
            blurBitmap = null;
        }

        if (cutBitmap != null) {
            cutBitmap.recycle();
            cutBitmap = null;
        }
        bgView = null;
    }


    @Override
    protected void onPause() {
        super.onPause();
        blurKeyboradView.onPause();
    }

}
