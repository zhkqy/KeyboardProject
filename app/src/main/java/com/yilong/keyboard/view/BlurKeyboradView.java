package com.yilong.keyboard.view;

import android.app.Activity;
import android.content.Context;
import android.graphics.Rect;
import android.text.InputFilter;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.yilong.keyboard.R;
import com.yilong.keyboard.utils.ScreenUtils;

import static android.text.InputType.TYPE_TEXT_FLAG_MULTI_LINE;

/**
 * Created by chenlei on 2018/5/29.
 */

public class BlurKeyboradView extends FrameLayout {

    Context mContext;
    EditText edittext;

    View bottomLL;

    private boolean onPause = false;

    Listener listener;

    public BlurKeyboradView(Context context) {
        super(context);
        initView(context);
    }

    public BlurKeyboradView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    private void initView(Context context) {
        this.mContext = context;
        inflate(mContext, R.layout.view_blur_keyborad, this);
        edittext = findViewById(R.id.edittext);
        edittext.setFilters(new InputFilter[]{new InputFilter.LengthFilter(200)});
        bottomLL = findViewById(R.id.bottomLL);

        edittext.setFocusable(true);
        edittext.setFocusableInTouchMode(true);
        edittext.requestFocus();
        edittext.findFocus();
        edittext.setInputType(TYPE_TEXT_FLAG_MULTI_LINE);
        edittext.setSingleLine(false);
        edittext.setMaxLines(5);
        edittext.setOnKeyListener(new OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if ((event.getAction() == KeyEvent.ACTION_UP) && (keyCode == KeyEvent.KEYCODE_BACK)) {
                    ((Activity) getContext()).finish();
                    return true;
                }
                return false;
            }
        });

        edittext.setOnEditorActionListener(new EditText.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                switch (actionId) {
                    case EditorInfo.IME_ACTION_SEND:
                        if (listener != null) {
                            listener.send();
                        }
                        break;
                }
                return true;

            }
        });
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);

        addOnSoftKeyBoardVisibleListener();
    }

    //一个静态变量存储高度
    public static int keyboardHeight = 0;
    ViewTreeObserver.OnGlobalLayoutListener onGlobalLayoutListener = null;

    private boolean isShowKeyBoard = false;

    public void addOnSoftKeyBoardVisibleListener() {
        final View decorView = ((Activity) getContext()).getWindow().getDecorView();
        onGlobalLayoutListener = new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                Rect rect = new Rect();
                decorView.getWindowVisibleDisplayFrame(rect); //计算出可见屏幕的高度
                int displayHight = rect.bottom - rect.top; //获得屏幕整体的高度
                int hight = decorView.getHeight();
                int statusBarHeight = ScreenUtils.getStatusBarHeight(mContext);
                keyboardHeight = hight - displayHight - statusBarHeight - ScreenUtils.getBottomKeyboardHeight(mContext);

                if (bottomLL != null) {
                    if (keyboardHeight > 0 && !isShowKeyBoard) {
                        isShowKeyBoard = true;
                        LayoutParams params = (LayoutParams) bottomLL.getLayoutParams();
                        params.setMargins(0, 0, 0, keyboardHeight);
                        bottomLL.setLayoutParams(params);
                        onPause = false;
                    } else if (keyboardHeight <= 0 && isShowKeyBoard) {
                        isShowKeyBoard = false;
                        LayoutParams params = (LayoutParams) bottomLL.getLayoutParams();
                        params.setMargins(0, 0, 0, 0);
                        bottomLL.setLayoutParams(params);
                        if (!onPause) {
                            ((Activity) getContext()).finish();
                        }
                    }
                }
                decorView.getViewTreeObserver()
                        .removeGlobalOnLayoutListener(this);
            }
        };
        decorView.getViewTreeObserver().addOnGlobalLayoutListener(onGlobalLayoutListener);
    }

    public void onPause() {
        onPause = true;
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
    }

    public String getEditTextStr() {
        return edittext.getText().toString();
    }

    public EditText getEditText() {
        return edittext;
    }


    public void setEditText(String editText) {
        edittext.setText(editText);
        edittext.setSelection(editText.length());//将光标移至文字末尾
    }

    public void setEditHintText(String hint) {
        if (!TextUtils.isEmpty(hint)) {
            edittext.setHint(hint);
        }
    }

    public Listener getListener() {
        return listener;
    }

    public void setListener(Listener listener) {
        this.listener = listener;
    }

    public interface Listener {

        void send();
    }
}
