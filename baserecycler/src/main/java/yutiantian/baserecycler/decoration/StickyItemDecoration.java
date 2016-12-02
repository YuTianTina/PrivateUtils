package yutiantian.baserecycler.decoration;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.View;

import java.util.List;

import yutiantian.baserecycler.bean.StickyBaseBean;

/**
 * Created by Tina on 2016/10/11.
 * Description:导航分组头
 */

public class StickyItemDecoration<T extends StickyBaseBean> extends RecyclerView.ItemDecoration {
    private List<T> mList;
    private Paint mPaint;
    private Rect mBounds;

    private int mTitleHeight;//title的高
    private static int COLOR_TITLE_BG = Color.parseColor("#FFDFDFDF");
    private static int COLOR_TITLE_FONT = Color.parseColor("#FF000000");
    private static int mTitleFontSize;//title字体大小

    public void setmList(List<T> mList) {
        this.mList = mList;
    }

    public StickyItemDecoration(Context mContext, List<T> mList) {
        this.mList = mList;
        this.mPaint=new Paint();
        this.mBounds=new Rect();
        mTitleHeight = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 30, mContext.getResources().getDisplayMetrics());
        mTitleFontSize = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 16, mContext.getResources().getDisplayMetrics());
        mPaint.setTextSize(mTitleFontSize);
        mPaint.setAntiAlias(true);
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        int position=((RecyclerView.LayoutParams)view.getLayoutParams()).getViewLayoutPosition();
        if(position<=-1)
            return;
        if(position==0){
            outRect.set(0,mTitleHeight,0,0);
        }else{
            if(mList.size()<=position)
                return;
            if(mList.get(position)==null)
                return;
            if(mList.get(position).getHeadMsg()!=null&&!mList.get(position).getHeadMsg().equals(mList.get(position-1).getHeadMsg())){
                outRect.set(0,mTitleHeight,0,0);
            }else{
                outRect.set(0,0,0,0);
            }
        }
    }

    @Override
    public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
        int pos = ((LinearLayoutManager)(parent.getLayoutManager())).findFirstVisibleItemPosition();

        String headMsg=mList.get(pos).getHeadMsg();
        //View child = parent.getChildAt(pos);
        View child = parent.findViewHolderForLayoutPosition(pos).itemView;
        mPaint.setColor(COLOR_TITLE_BG);
        c.drawRect(parent.getPaddingLeft(), parent.getPaddingTop(), parent.getRight() - parent.getPaddingRight(), parent.getPaddingTop() + mTitleHeight, mPaint);
        mPaint.setColor(COLOR_TITLE_FONT);
        mPaint.getTextBounds(headMsg, 0, headMsg.length(), mBounds);
        c.drawText(headMsg, child.getPaddingLeft(),
                parent.getPaddingTop() + mTitleHeight - (mTitleHeight / 2 - mBounds.height() / 2),
                mPaint);
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDraw(c, parent, state);
        final int left = parent.getPaddingLeft();
        final int right = parent.getWidth() - parent.getPaddingRight();
        final int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            final View child = parent.getChildAt(i);
            final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child
                    .getLayoutParams();
            int position = params.getViewLayoutPosition();
            //我记得Rv的item position在重置时可能为-1.保险点判断一下吧
            if (position > -1) {
                if (position == 0) {//等于0肯定要有title的
                    drawTitleArea(c, left, right, child, params, position);

                } else {//其他的通过判断
                    if(mList.size()<=position)
                        return;
                    if(mList.get(position)==null)
                        return;
                    if (null != mList.get(position).getHeadMsg() && !mList.get(position).getHeadMsg().equals(mList.get(position - 1).getHeadMsg())) {
                        //不为空 且跟前一个tag不一样了，说明是新的分类，也要title
                        drawTitleArea(c, left, right, child, params, position);
                    } else {
                        //none
                    }
                }
            }
        }
    }

    /**
     * 绘制Title区域背景和文字的方法
     *
     * @param c
     * @param left
     * @param right
     * @param child
     * @param params
     * @param position
     */
    private void drawTitleArea(Canvas c, int left, int right, View child, RecyclerView.LayoutParams params, int position) {//最先调用，绘制在最下层
        mPaint.setColor(COLOR_TITLE_BG);
        c.drawRect(left, child.getTop() - params.topMargin - mTitleHeight, right, child.getTop() - params.topMargin, mPaint);
        mPaint.setColor(COLOR_TITLE_FONT);
/*
        Paint.FontMetricsInt fontMetrics = mPaint.getFontMetricsInt();
        int baseline = (getMeasuredHeight() - fontMetrics.bottom + fontMetrics.top) / 2 - fontMetrics.top;*/

        mPaint.getTextBounds(mList.get(position).getHeadMsg(), 0, mList.get(position).getHeadMsg().length(), mBounds);
        c.drawText(mList.get(position).getHeadMsg(), child.getPaddingLeft(), child.getTop() - params.topMargin - (mTitleHeight / 2 - mBounds.height() / 2), mPaint);
    }
}
