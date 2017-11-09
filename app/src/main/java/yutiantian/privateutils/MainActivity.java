package yutiantian.privateutils;

import android.app.Activity;
import android.inputmethodservice.Keyboard;
import android.os.Bundle;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import yutiantian.normal.listener.KeyboardWatcher;
import yutiantian.normal.utils.KeyboardUtil;
import yutiantian.normal.view.CustomBasicKeyboardView;
import yutiantian.normal.widget.EditTextWithClear;

public class MainActivity extends Activity implements KeyboardWatcher.OnKeyboardToggleListener {

    @BindView(R.id.et_clear)
    EditTextWithClear etClear;
    @BindView(R.id.keyboard_view)
    CustomBasicKeyboardView keyboardView;
    @BindView(R.id.activity_main)
    RelativeLayout activityMain;
    private List<String> mList = new ArrayList<>();
    KeyboardUtil keyboardUtil;
    KeyboardWatcher watcher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        keyboardUtil = new KeyboardUtil(this, R.xml.layout_key, R.id.keyboard_view)
                .addKeyDrawble(Keyboard.KEYCODE_DONE, R.drawable.bg_keyboardview_yes);
        keyboardUtil.invalidate();

        watcher = new KeyboardWatcher(this)
                .setListener(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        watcher.destroy();
    }

    @Override
    public void onKeyboardShown(int keyboardSize) {
        Toast.makeText(this, "open", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onKeyboardClosed() {
        Toast.makeText(this, "close", Toast.LENGTH_SHORT).show();
    }
}
