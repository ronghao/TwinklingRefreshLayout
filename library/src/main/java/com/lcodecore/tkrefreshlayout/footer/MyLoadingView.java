package com.lcodecore.tkrefreshlayout.footer;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;
import com.lcodecore.tkrefreshlayout.IBottomView;
import com.lcodecore.tkrefreshlayout.R;
import com.lcodecore.tkrefreshlayout.utils.DensityUtil;
import com.lcodecore.tkrefreshlayout.utils.TextAnimUtil;

/**
 * 标准上拉刷新
 *
 * @author haohao on 2017/8/23 13:16
 * @version v1.0
 */
public class MyLoadingView extends TextView implements IBottomView {

    private final String pullUpStr = "上拉加载更多";
    private final String releaseRefreshStr = "释放刷新";
    private final String refreshingStr = "加载中";
    private final String refreshingFinish = "刷新完成";
    private final String refreshingFinishNoData = "没有更多内容了";

    public MyLoadingView(Context context) {
        this(context, null);
    }

    public MyLoadingView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyLoadingView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        int width = DensityUtil.dp2px(context, 100);
        int height = DensityUtil.dp2px(context, 36);
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(width, height);
        params.gravity = Gravity.CENTER;
        params.topMargin = DensityUtil.dp2px(context, 5);
        setLayoutParams(params);
        setGravity(Gravity.CENTER_HORIZONTAL | Gravity.TOP);
        setTextColor(getResources().getColor(R.color.text_load_gray));
    }

    @Override
    public View getView() {
        return this;
    }

    @Override
    public void onPullingUp(float fraction, float maxHeadHeight, float headHeight) {
        if (fraction < 1f) this.setText(pullUpStr);
        if (fraction > 1f) this.setText(releaseRefreshStr);
    }

    @Override
    public void startAnim(float maxHeadHeight, float headHeight) {
        TextAnimUtil.showTextLoadingAnim(this, refreshingStr);
    }

    @Override
    public void onPullReleasing(float fraction, float maxHeadHeight, float headHeight) {
        if (fraction < 1f) {
            this.setText(pullUpStr);
        }
    }

    @Override
    public void onFinish() {
        TextAnimUtil.stopTextLoadingAnim(this);
        this.setText(refreshingFinish);
    }

    @Override
    public void reset() {
        this.setText(pullUpStr);
    }
}
