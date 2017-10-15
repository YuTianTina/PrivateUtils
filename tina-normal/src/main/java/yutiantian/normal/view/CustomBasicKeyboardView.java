package yutiantian.normal.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.inputmethodservice.Keyboard;
import android.inputmethodservice.KeyboardView;
import android.support.annotation.DrawableRes;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;

import java.lang.ref.WeakReference;
import java.lang.reflect.Field;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;

/**
 * Description:  自定义键盘界面的基础类
 *
 * @author YuTianTian
 */

public class CustomBasicKeyboardView extends KeyboardView {
    private Keyboard mKeyboard;
    private WeakHashMap<Integer, Integer> codeMaps;
    public CustomBasicKeyboardView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomBasicKeyboardView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mKeyboard = this.getKeyboard();
        List<Keyboard.Key> keys = null;
        if (mKeyboard != null) {
            keys = mKeyboard.getKeys();
        }

        if (keys != null) {
            for (Keyboard.Key key : keys) {

                if(null != codeMaps){
                    for (Map.Entry<Integer, Integer> entry: codeMaps.entrySet()
                         ) {
                        if(key.codes[0] == entry.getKey()){
                            drawKeyBackground(entry.getValue(), canvas, key);
                            drawText(canvas, key);
                        }
                    }

                }

            }
        }
    }

    /**
     * 添加需要重新绘制背景的key
     * @param codeKey
     * @param drawableResId
     * @return
     */
    public CustomBasicKeyboardView addCodeDrawable(int codeKey, @DrawableRes int drawableResId){
        if(null == codeMaps){
            codeMaps = new WeakHashMap<>();
        }
        codeMaps.put(codeKey, drawableResId);
        return this;
    }

    /**
     * 背景色绘制
     * @param drawableId
     * @param canvas
     * @param key
     */
    private void drawKeyBackground(int drawableId, Canvas canvas, Keyboard.Key key) {
        Drawable npd = ContextCompat.getDrawable(getContext(), drawableId);
        int[] drawableState = key.getCurrentDrawableState();
        if (key.codes[0] != 0) {
            npd.setState(drawableState);
        }
        npd.setBounds(key.x, key.y, key.x + key.width, key.y
                + key.height);
        npd.draw(canvas);
    }

    /**
     * 文本绘制
     * @param canvas
     * @param key
     */
    private void drawText(Canvas canvas, Keyboard.Key key) {
        Rect bounds = new Rect();
        Paint paint = new Paint();
        paint.setTextAlign(Paint.Align.CENTER);


        paint.setAntiAlias(true);

        paint.setColor(Color.WHITE);
        if (key.label != null) {
            String label = key.label.toString();

            Field field;

            if (label.length() > 1 && key.codes.length < 2) {
                int labelTextSize = 0;
                try {
                    field = KeyboardView.class.getDeclaredField("mLabelTextSize");
                    field.setAccessible(true);
                    labelTextSize = (int) field.get(this);
                } catch (NoSuchFieldException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
                paint.setTextSize(labelTextSize);
                paint.setTypeface(Typeface.DEFAULT_BOLD);
            } else {
                int keyTextSize = 0;
                try {
                    field = KeyboardView.class.getDeclaredField("mLabelTextSize");
                    field.setAccessible(true);
                    keyTextSize = (int) field.get(this);
                } catch (NoSuchFieldException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
                paint.setTextSize(keyTextSize);
                paint.setTypeface(Typeface.DEFAULT);
            }

            paint.getTextBounds(key.label.toString(), 0, key.label.toString()
                    .length(), bounds);
            canvas.drawText(key.label.toString(), key.x + (key.width / 2),
                    (key.y + key.height / 2) + bounds.height() / 2, paint);
        } else if (key.icon != null) {
            key.icon.setBounds(key.x + (key.width - key.icon.getIntrinsicWidth()) / 2, key.y + (key.height - key.icon.getIntrinsicHeight()) / 2,
                    key.x + (key.width - key.icon.getIntrinsicWidth()) / 2 + key.icon.getIntrinsicWidth(), key.y + (key.height - key.icon.getIntrinsicHeight()) / 2 + key.icon.getIntrinsicHeight());
            key.icon.draw(canvas);
        }

    }
}
