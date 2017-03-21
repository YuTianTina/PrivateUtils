package yutiantian.normal.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;

import yutiantian.library2.R;

/**
 * Created by Tina on 2017/3/21.
 * Description: 横向均分图片控件,宽高比自定义
 */

public class AverageImageView extends ImageView {
    private Context context;
    private float mRatio;//宽高比
    private int mNum;//均分个数
    private int space;//横向间隙
    public AverageImageView(Context context) {
        super(context);
    }

    public AverageImageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.AverageImageView);
        mRatio = a.getFloat(R.styleable.AverageImageView_average_ratio,1f);
        mNum = a.getInt(R.styleable.AverageImageView_average_number,1);
        space = a.getDimensionPixelOffset(R.styleable.AverageImageView_average_space,0);
        a.recycle();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if(mRatio > 0.0f){
            int width = (View.MeasureSpec.getSize(widthMeasureSpec) - mNum * space) / mNum;
            int height = (int) (width * mRatio);
            setMeasuredDimension(width,height);
        }else{
            super.onMeasure(widthMeasureSpec,heightMeasureSpec);
        }
    }
}
