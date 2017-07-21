package yutiantian.normal.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.annotation.DrawableRes;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.MotionEvent;

import yutiantian.library2.R;


/**
 * Created by Tina on 2017/3/18.
 * Description: 自带清除Icon的EditText
 */

public class EditTextWithClear extends NormalEditText {
    private Context context;
    private @DrawableRes int drawableResId;

    Drawable[] drawables;
    public EditTextWithClear(Context context) {
        super(context);
    }

    public EditTextWithClear(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.EditTextWithClear);
        drawableResId = typedArray.getResourceId(R.styleable.EditTextWithClear_clear_icon_src, -1);
        init(context);
        typedArray.recycle();
    }

    private void init(Context context) {
        this.context = context;
        if (drawableResId == -1) {
            //默认icon
            drawableResId = R.mipmap.clear_input_icon;
        }
        drawables = getCompoundDrawablesRelative();
        setCLearIconVisible(false);
    }

    @Override
    protected void onTextChanged(CharSequence text, int start, int lengthBefore, int lengthAfter) {
        if (text.length() == 0 && lengthBefore > 0) {
            //从有文字删除到无文字
            setCLearIconVisible(false);
            return;
        }
        if (start == 0 && text.length() > 0) {
            //从无文字删除到有文字
            setCLearIconVisible(true);
        }
    }

    /**
     * 设置区域icon是否可见
     */
    private void setCLearIconVisible(boolean visible) {
        try {
            if (visible) {
                setCompoundDrawablesWithIntrinsicBounds(drawables[0], drawables[1], ContextCompat.getDrawable(context, drawableResId), drawables[3]);
            } else {
                setCompoundDrawablesWithIntrinsicBounds(drawables[0], drawables[1], null, drawables[3]);
            }
        }catch (NullPointerException e){
            e.printStackTrace();
            setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
        }
    }

    @Override
    public void setCompoundDrawablesRelativeWithIntrinsicBounds(@DrawableRes int start, @DrawableRes int top, @DrawableRes int end, @DrawableRes int bottom) {
        super.setCompoundDrawablesRelativeWithIntrinsicBounds(start, top, end, bottom);
        drawables = getCompoundDrawablesRelative();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_UP: {
                if (getCompoundDrawables()[2] != null) {
                    if (getWidth() - getTotalPaddingRight() < event.getX() && getWidth() - getPaddingRight() > event.getX()) {
                        this.setText("");
                    }
                }
                break;
            }
        }
        return super.onTouchEvent(event);
    }
}
