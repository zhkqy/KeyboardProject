package com.yilong.keyboard.view;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.yilong.keyboard.R;


/**
 * Created by zhkqy on 2018/5/29.
 */

public class BlurImageView extends ImageView {

    public BlurImageView(Context context) {
        super(context);
    }

    public BlurImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        canvas.drawColor(getContext().getResources().getColor(R.color.blackOpaque40));
    }
}
