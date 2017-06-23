package yutiantian.normal.listener;

import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;

/**
 * Created by Tina on 2017/6/23.
 * Description: 搜索输入框点击搜索的通用监听
 */

public class OnNormalEditorActionListener implements TextView.OnEditorActionListener {
    private OnSearchListener onSearchListener;

    public OnNormalEditorActionListener(OnSearchListener onSearchListener) {
        this.onSearchListener = onSearchListener;
    }
    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if(actionId == EditorInfo.IME_ACTION_SEARCH || (null != event && KeyEvent.KEYCODE_ENTER == event.getKeyCode())){
            switch (event.getAction()){
                case KeyEvent.ACTION_UP:
                    if(null != onSearchListener){
                        onSearchListener.search(v);
                    }
                    return true;
                default:
                    return true;
            }
        }
        return false;
    }
    public interface OnSearchListener{
        void search(TextView v);
    }
}
