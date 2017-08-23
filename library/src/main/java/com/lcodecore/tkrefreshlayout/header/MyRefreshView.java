package com.lcodecore.tkrefreshlayout.header;

import android.content.Context;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import com.lcodecore.tkrefreshlayout.IHeaderView;
import com.lcodecore.tkrefreshlayout.OnAnimEndListener;
import com.lcodecore.tkrefreshlayout.R;
import com.lcodecore.tkrefreshlayout.utils.TextAnimUtil;

/**
 * 标准头部
 *
 * @author haohao on 2017/8/23 11:01
 * @version v1.0
 */
public class MyRefreshView extends FrameLayout implements IHeaderView {

    private final String pullDownStr = "下拉刷新";
    private final String releaseRefreshStr = "释放刷新";
    private final String refreshingStr = "加载中";
    private final String refreshingFinish = "刷新完成";

    private Animation anim;

    private TextView refreshTextView;
    private ImageView refreshImageView;

    public MyRefreshView(@NonNull Context context) {
        super(context);
        init();
    }

    public MyRefreshView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public MyRefreshView(@NonNull Context context, @Nullable AttributeSet attrs,
            @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        View rootView = View.inflate(getContext(), R.layout.view_myheader, null);
        refreshTextView = (TextView) rootView.findViewById(R.id.myheader_refresh);
        refreshImageView = (ImageView) rootView.findViewById(R.id.myheader_progressbar);
        addView(rootView);

        anim = AnimationUtils.loadAnimation(getContext(), R.anim.progress_anim);
        anim.setInterpolator(new LinearInterpolator());
    }

    @Override
    public View getView() {
        return this;
    }

    @Override
    public void onPullingDown(float fraction, float maxHeadHeight, float headHeight) {
        if (fraction < 1f) refreshTextView.setText(pullDownStr);
        if (fraction > 1f) refreshTextView.setText(releaseRefreshStr);
    }

    @Override
    public void onPullReleasing(float fraction, float maxHeadHeight, float headHeight) {
        if (fraction < 1f) {
            refreshTextView.setText(pullDownStr);
            refreshImageView.setVisibility(INVISIBLE);
        }
    }

    @Override
    public void startAnim(float maxHeadHeight, float headHeight) {
        refreshImageView.setVisibility(VISIBLE);
        refreshImageView.startAnimation(anim);
        TextAnimUtil.showTextLoadingAnim(refreshTextView, refreshingStr);
    }

    @Override
    public void onFinish(OnAnimEndListener animEndListener) {
        refreshImageView.setVisibility(INVISIBLE);
        refreshImageView.clearAnimation();
        TextAnimUtil.stopTextLoadingAnim(refreshTextView);
        refreshTextView.setText(refreshingFinish);
        animEndListener.onAnimEnd();
    }

    @Override
    public void reset() {
        refreshImageView.setVisibility(INVISIBLE);
        refreshImageView.clearAnimation();
        refreshTextView.setText(pullDownStr);
    }
}
