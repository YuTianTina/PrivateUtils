package yutiantian.normal.utils;

import android.app.Activity;
import android.content.Context;
import android.inputmethodservice.Keyboard;
import android.inputmethodservice.KeyboardView;
import android.os.Build;
import android.support.annotation.DrawableRes;
import android.support.annotation.IdRes;
import android.support.annotation.XmlRes;
import android.text.Editable;
import android.text.InputType;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import java.lang.reflect.Method;

import yutiantian.normal.view.CustomBasicKeyboardView;

/**
 * Description: 自定义键盘相关工具类
 * @author YuTianTian
 * @version
 * @since
*/

public class KeyboardUtil {
    private Activity mActivity;

    private CustomBasicKeyboardView mKeyboardView;
    private Keyboard mKeyboardNumber;//数字键盘
    private EditText mEditText;

    private @XmlRes int keyboardXmlRes;
    private @IdRes int keyboardViewId;

    public KeyboardUtil(Activity activity, int keyboardXmlRes, int keyboardViewId) {
        this.mActivity = activity;
        this.keyboardXmlRes = keyboardXmlRes;
        this.keyboardViewId = keyboardViewId;
        mKeyboardNumber = new Keyboard(mActivity, keyboardXmlRes);
        mKeyboardView = (CustomBasicKeyboardView) mActivity.findViewById(keyboardViewId);
    }

    public KeyboardUtil addKeyDrawble(int keyCode,@DrawableRes int drawbleResId){
        if(null != mKeyboardView){
            mKeyboardView.addCodeDrawable(keyCode, drawbleResId);
        }
        return this;
    }

    public void invalidate(){
        if(null != mKeyboardView){
            mKeyboardView.invalidate();
        }
    }

    /**
     * edittext绑定自定义键盘
     *
     * @param editText 需要绑定自定义键盘的edittext
     */
    public void attachTo(EditText editText) {
        this.mEditText = editText;
        hideSystemSofeKeyboard(mActivity.getApplicationContext(), mEditText);
        showSoftKeyboard();
    }

    public void showSoftKeyboard() {
        if (mKeyboardNumber == null) {
            mKeyboardNumber = new Keyboard(mActivity, keyboardXmlRes);
        }
        if (mKeyboardView == null) {
            mKeyboardView = (CustomBasicKeyboardView) mActivity.findViewById(keyboardViewId);
        }
        mKeyboardView.setKeyboard(mKeyboardNumber);
        mKeyboardView.setEnabled(true);
        mKeyboardView.setPreviewEnabled(false);
        mKeyboardView.setVisibility(View.VISIBLE);
        mKeyboardView.setOnKeyboardActionListener(mOnKeyboardActionListener);

    }

    private KeyboardView.OnKeyboardActionListener mOnKeyboardActionListener = new KeyboardView.OnKeyboardActionListener() {
        @Override
        public void onPress(int primaryCode) {

        }

        @Override
        public void onRelease(int primaryCode) {

        }

        @Override
        public void onKey(int primaryCode, int[] keyCodes) {
            Editable editable = mEditText.getText();
            int start = mEditText.getSelectionStart();
            if (primaryCode == Keyboard.KEYCODE_DELETE) {// 回退
                if (editable != null && editable.length() > 0) {
                    if (start > 0) {
                        editable.delete(start - 1, start);
                    }
                }
            } else if (primaryCode == Keyboard.KEYCODE_CANCEL) {// 隐藏键盘
                hideKeyboard();
                if (mOnCancelClick != null) {
                    mOnCancelClick.onCancellClick();
                }
            } else if (primaryCode == Keyboard.KEYCODE_DONE) {// 隐藏键盘
                hideKeyboard();
                if (mOnOkClick != null) {
                    mOnOkClick.onOkClick();
                }
            } else {
                editable.insert(start, Character.toString((char) primaryCode));
            }
        }

        @Override
        public void onText(CharSequence text) {

        }

        @Override
        public void swipeLeft() {

        }

        @Override
        public void swipeRight() {

        }

        @Override
        public void swipeDown() {

        }

        @Override
        public void swipeUp() {

        }
    };


    /**
     * 隐藏系统键盘
     *
     * @param editText
     */
    public static void hideSystemSofeKeyboard(Context context, EditText editText) {
        int sdkInt = Build.VERSION.SDK_INT;
        if (sdkInt >= 11) {
            try {
                Class<EditText> cls = EditText.class;
                Method setShowSoftInputOnFocus;
                setShowSoftInputOnFocus = cls.getMethod("setShowSoftInputOnFocus", boolean.class);
                setShowSoftInputOnFocus.setAccessible(true);
                setShowSoftInputOnFocus.invoke(editText, false);

            } catch (SecurityException e) {
                e.printStackTrace();
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            editText.setInputType(InputType.TYPE_NULL);
        }
        // 如果软键盘已经显示，则隐藏
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(editText.getWindowToken(), 0);
    }

    public interface OnOkClick {
        void onOkClick();
    }

    public interface onCancelClick {
        void onCancellClick();
    }

    public OnOkClick mOnOkClick = null;
    public onCancelClick mOnCancelClick;

    public void setOnOkClick(OnOkClick onOkClick) {
        mOnOkClick = onOkClick;
    }

    public void setOnCancelClick(onCancelClick onCancelClick) {
        mOnCancelClick = onCancelClick;
    }

    public void showKeyboard() {
        int visibility = mKeyboardView.getVisibility();
        if (visibility == View.GONE || visibility == View.INVISIBLE) {
            mKeyboardView.setVisibility(View.VISIBLE);
        }
    }

    public void hideKeyboard() {
        int visibility = mKeyboardView.getVisibility();
        if (visibility == View.VISIBLE) {
            mKeyboardView.setVisibility(View.GONE);
        }
    }
}