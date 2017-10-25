package yutiantian.normal.widget;

import android.content.Context;
import android.support.v7.widget.AppCompatEditText;
import android.text.TextUtils;
import android.util.AttributeSet;

/**
 * Created by Tina on 2017/3/16.
 * Description: 基础EditText,针对初始设定文本时,将光标移到最后一位
 */

public class NormalEditText extends AppCompatEditText {


    public NormalEditText(Context context) {
        super(context);
    }

    public NormalEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public NormalEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    /**
     * 填充文本后,光标自动切到最后一位
     */
    public void setNormalText(CharSequence text) {
        this.setText(text);
        if (!TextUtils.isEmpty(getText())) {
            this.setSelection(getText().length());
        }
    }
}
