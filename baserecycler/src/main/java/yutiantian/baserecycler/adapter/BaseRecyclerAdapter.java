package yutiantian.baserecycler.adapter;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import yutiantian.baserecycler.R;
import yutiantian.baserecycler.viewholder.ViewHolder;

/**
 * Created by Tina on 2016/9/14.
 */
public abstract class BaseRecyclerAdapter<T> extends RecyclerView.Adapter<ViewHolder> {
    protected Context mContext;
    protected List<T> mLists;
    protected RecyclerView mRecyclerView;
    private OnLoadMoreListener mOnLoadMoreListener;
    private final static int NORMAL_TYPE = 1;
    private final static int LOAD_MORE_TYPE = 2;
    private int mLayoutID;

    public int curPage = 1;
    public int row = 20;
    public boolean hasMore = false;
    protected boolean isLoading = false;

    public void resetPage() {
        this.curPage = 1;
        this.row = 20;
        hasMore = false;
    }

    public void addPage() {
        this.curPage++;
    }

    public void setHasMore(boolean hasMore) {
        this.hasMore = hasMore;
//        finishLoadMore();
    }

    public BaseRecyclerAdapter(Context context, RecyclerView recyclerView, int layoutID, List<T> list) {
        mContext = context;
        mRecyclerView = recyclerView;
        mLists = list;
        mLayoutID = layoutID;
        final LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
        if (linearLayoutManager instanceof LinearLayoutManager) {//线性布局下的recyclerview滚动分页
            recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                    super.onScrolled(recyclerView, dx, dy);
                    int lastVisibleItemPosition = linearLayoutManager.findLastVisibleItemPosition();
                    if (dy <= 0)
                        return;
                    if (recyclerView.getAdapter().getItemCount() > 1 && lastVisibleItemPosition >= recyclerView.getAdapter().getItemCount() - 1
                            && hasMore && !isLoading
                            && recyclerView.getScrollState() != RecyclerView.SCROLL_STATE_IDLE) {
                        if (mOnLoadMoreListener != null) {
                            mLists.add(null);//null值为分页加载页
                            recyclerView.getAdapter().notifyDataSetChanged();
                            isLoading = true;
                            mOnLoadMoreListener.onLoadMoreRequest();
                        }
                    }
                }
            });
        }
    }

    public void finishLoadMore() {
        isLoading = false;
        if (mLists != null && mLists.size() > 0) {
            if (mLists.get(mLists.size() - 1) == null) {
                mLists.remove(mLists.size() - 1);//移除数据列最后一行,最后一行空值表示加载页
                notifyDataSetChanged();
            }
        }
    }

    protected void setListener(final ViewHolder viewHolder) {
        viewHolder.getConvertView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnItemClickListener != null) {
                    int position = viewHolder.getAdapterPosition();
                    mOnItemClickListener.onItemClick(v, viewHolder, position);
                }
            }
        });
        viewHolder.getConvertView().setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (mOnItemClickListener != null) {
                    int position = viewHolder.getAdapterPosition();
                    return mOnItemClickListener.onItemLongClick(v, viewHolder, position);
                }
                return false;
            }
        });
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewHolder viewHolder;
        if (viewType == NORMAL_TYPE) {
            viewHolder = ViewHolder.createViewHolder(mContext, parent, mLayoutID);
            setListener(viewHolder);
        } else
            viewHolder = ViewHolder.createViewHolder(mContext, parent, R.layout.layout_load_more);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if (holder.getItemViewType() == NORMAL_TYPE) {
            convert(holder, position);
        }
        if (holder.getItemViewType() == LOAD_MORE_TYPE)
            holder.setVisible(R.id.load_more_progressBar, true);
    }

    protected abstract void convert(ViewHolder holder, int position);


    @Override
    public int getItemViewType(int position) {
        return mLists.get(position) != null ? NORMAL_TYPE : LOAD_MORE_TYPE;
    }


    @Override
    public int getItemCount() {
        return mLists == null ? 0 : mLists.size();
    }

    public interface OnLoadMoreListener {
        void onLoadMoreRequest();
    }

    public void setmOnLoadMoreListener(OnLoadMoreListener mOnLoadMoreListener) {
        this.mOnLoadMoreListener = mOnLoadMoreListener;
    }

    private OnItemClickListener mOnItemClickListener;

    public interface OnItemClickListener {
        void onItemClick(View view, RecyclerView.ViewHolder holder, int position);

        boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }

}
