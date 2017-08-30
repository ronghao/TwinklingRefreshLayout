package com.lcodecore.tkrefreshlayout.sample;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import com.lcodecore.tkrefreshlayout.R;
import com.lcodecore.tkrefreshlayout.TRefreshWithEmptyViewLayout;
import com.lcodecore.tkrefreshlayout.footer.MyLoadingView;
import com.lcodecore.tkrefreshlayout.header.MyRefreshView;
import com.lcodecore.tkrefreshlayout.utils.DensityUtil;

/**
 * 示例代码RefreshLayout
 *
 * @author haohao on 2017/8/30 17:15
 * @version v1.0
 */
public class SampleRefreshLayout extends TRefreshWithEmptyViewLayout {

    private View error;
    private View empty;

    public SampleRefreshLayout(Context context) {
        super(context);
        initView();
    }

    public SampleRefreshLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public SampleRefreshLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView() {
        error = LayoutInflater.from(getContext()).inflate(R.layout.base_errorview, null);
        setErrorView(error, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startRefresh();
            }
        });
        empty = LayoutInflater.from(getContext()).inflate(R.layout.base_emptyview, null);
        setEmptyView(empty, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startRefresh();
            }
        });

        MyRefreshView headerView = new MyRefreshView(getContext());
        setHeaderView(headerView);
        MyLoadingView loadingView = new MyLoadingView(getContext());
        setBottomView(loadingView);
        setOverScrollRefreshShow(false);
        setAutoLoadMore(true);
        setHeaderHeight(DensityUtil.dp2px(getContext(), 12));
        setBottomHeight(DensityUtil.dp2px(getContext(), 12));
    }
}
