package yutiantian.privateutils;

import android.app.Activity;
import android.inputmethodservice.Keyboard;
import android.os.Bundle;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import yutiantian.normal.utils.KeyboardUtil;
import yutiantian.normal.widget.EditTextWithClear;

public class MainActivity extends Activity {

    @Bind(R.id.et_clear)
    EditTextWithClear etClear;
    private List<String> mList = new ArrayList<>();
    KeyboardUtil keyboardUtil;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        keyboardUtil  = new KeyboardUtil(this, R.xml.layout_key, R.id.keyboard_view)
                .addKeyDrawble(Keyboard.KEYCODE_DONE, R.drawable.bg_keyboardview_yes);
        keyboardUtil.invalidate();
        etClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                keyboardUtil.attachTo(etClear);
            }
        });
    }
}
