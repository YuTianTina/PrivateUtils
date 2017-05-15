package yutiantian.normal.swipeRefresh.view;

import android.content.Context;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by Tina on 2017/5/15.
 * Description:
 * Extends {@link BaseSwipeRefreshLayout} to support non-direct descendant scrolling views.
 * <p>
 * {@link BaseSwipeRefreshLayout} works as expected when a scroll view is a direct child: it triggers
 * the refresh only when the view is on top. This class adds a way (@link #setScrollUpChild} to
 * define which view controls this behavior.
 */

public class ScrollChildSwipeRefreshLayout extends BaseSwipeRefreshLayout {
    private View mScrollUpChild;
    public ScrollChildSwipeRefreshLayout(Context context) {
        super(context);
    }

    public ScrollChildSwipeRefreshLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean canChildScrollUp() {
        if (mScrollUpChild != null){
            return ViewCompat.canScrollVertically(mScrollUpChild, -1);
        }
        return super.canChildScrollUp();
    }

    public void setScrollUpChild(View view) {
        this.mScrollUpChild = view;
    }
}
