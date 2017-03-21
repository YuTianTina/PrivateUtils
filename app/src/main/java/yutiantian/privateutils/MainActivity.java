package yutiantian.privateutils;

import android.app.Activity;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import yutiantian.normal.widget.EditTextWithClear;

public class MainActivity extends Activity {

    @Bind(R.id.et_clear)
    EditTextWithClear etClear;
    private List<String> mList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }
}
