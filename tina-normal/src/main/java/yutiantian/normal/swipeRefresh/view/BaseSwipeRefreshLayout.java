package yutiantian.normal.swipeRefresh.view;

import android.content.Context;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.AttributeSet;

import yutiantian.library2.R;
import yutiantian.normal.swipeRefresh.listener.RefreshDoingListener;

/**
 * Created by Tina on 2017/2/27.
 * Description: 下拉刷新控件封装
 */

public class BaseSwipeRefreshLayout extends SwipeRefreshLayout {
    private int[] colors = getContext().getResources().getIntArray(R.array.progress_colors);
    private SwipeRefreshLayout refreshLayout;
    public BaseSwipeRefreshLayout(Context context) {
        super(context);
    }

    public BaseSwipeRefreshLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        refreshLayout = this;
    }

    public void setColors(int[] colorResIds){
        this.colors = colorResIds;
    }
    //控件默认初始化
    public void initSwipeRefresh(OnRefreshListener onRefreshListener){
        refreshLayout.setColorSchemeColors(colors);
        refreshLayout.setOnRefreshListener(onRefreshListener);
    }
    //自动刷新
    public void refreshDelay(RefreshDoingListener onRefreshDoing){
        this.setColorSchemeColors(colors);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                refreshLayout.setRefreshing(false);
            }
        },1*1000);
        refreshLayout.setRefreshing(true);
        onRefreshDoing.refreshDoing();
    }
    //刷新完成
    public void refreshComplete(){
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                refreshLayout.setRefreshing(false);
            }
        },5*100);
    }

}
