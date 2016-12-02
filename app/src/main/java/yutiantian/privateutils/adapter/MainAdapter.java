package yutiantian.privateutils.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;

import java.util.List;

import yutiantian.baserecycler.adapter.BaseRecyclerAdapter;
import yutiantian.baserecycler.viewholder.ViewHolder;
import yutiantian.privateutils.R;


/**
 * Created by Tina on 2016/12/1.
 * Description:
 */

public class MainAdapter extends BaseRecyclerAdapter<String> {

    public MainAdapter(Context context, RecyclerView recyclerView, List<String> list) {
        super(context, recyclerView, R.layout.layout_recycler_item, list);
    }

    @Override
    protected void convert(ViewHolder holder, int position) {
        holder.setText(R.id.tv_right,mLists.get(position));
    }
}
