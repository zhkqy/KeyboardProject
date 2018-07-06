package com.yilong.keyboard.view;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.yilong.keyboard.R;
import com.yilong.keyboard.utils.ScreenUtils;


/**
 * Created by zhkqy on 2018/5/29.
 */

public class BlurImageView extends ImageView {

    private Context mContext;

    public BlurImageView(Context context) {
        super(context);
        init(context);
    }


    public BlurImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);

    }

    private void init(Context context) {
        this.mContext = context;
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int height = ScreenUtils.getScreenHeight(mContext);
        super.onMeasure(widthMeasureSpec, MeasureSpec.makeMeasureSpec(height, MeasureSpec.EXACTLY));
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        canvas.drawColor(getContext().getResources().getColor(R.color.blackOpaque40));
    }
}
