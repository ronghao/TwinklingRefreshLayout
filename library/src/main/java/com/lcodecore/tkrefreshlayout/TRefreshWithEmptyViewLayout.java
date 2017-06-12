package com.lcodecore.tkrefreshlayout;

import android.content.Context;
import android.database.DataSetObserver;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.RelativeLayout;

/**
 * 下拉刷新带空view
 *
 * @author haohao on 2017/6/12 10:23
 * @version v1.0
 */
public class TRefreshWithEmptyViewLayout extends TwinklingRefreshLayout {
    private boolean isError = false;
    private View errorView;
    private View emptyView;
    private Adapter mAdapter;
    private RelativeLayout mRelativeLayout;

    public TRefreshWithEmptyViewLayout(Context context) {
        super(context);
        init();
    }

    public TRefreshWithEmptyViewLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public TRefreshWithEmptyViewLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public void init() {
        cp = new CustomCoContext();
    }

    public void setError(boolean error) {
        isError = error;
        checkErrorAndEmpty();
    }

    public void setEmptyView(View emptyView) {
        if (emptyView == null)
            throw new RuntimeException("emptyViw is not null");
        this.emptyView = emptyView;
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        if (mRelativeLayout == null) {
            mRelativeLayout = new RelativeLayout(getContext());
        }
        mRelativeLayout.addView(emptyView, params);
        emptyView.setVisibility(INVISIBLE);
    }

    public void setErrorView(View errorView) {
        if (errorView == null)
            throw new RuntimeException("errorView is not null");
        this.errorView = errorView;
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        if (mRelativeLayout == null) {
            mRelativeLayout = new RelativeLayout(getContext());
        }
        mRelativeLayout.addView(errorView, params);
        errorView.setVisibility(INVISIBLE);
    }


    public void showEmpty() {
        if (emptyView == null)
            throw new RuntimeException("emptyView is not null");
        emptyView.setVisibility(VISIBLE);
    }

    public void hideEmpty() {
        if (emptyView != null && emptyView.isShown()) {
            emptyView.setVisibility(INVISIBLE);
        }
    }

    public void showError() {
        if (errorView == null)
            throw new RuntimeException("errorView is not null");
        errorView.setVisibility(VISIBLE);
    }

    public void hideError() {
        if (errorView != null && errorView.isShown()) {
            errorView.setVisibility(INVISIBLE);
        }
    }

    public void setAdapter(Adapter adapter) {
        mAdapter = adapter;
        this.mAdapter = adapter;
        showEmpty();
        mAdapter.registerDataSetObserver(new DataSetObserver() {
            @Override
            public void onChanged() {
                checkErrorAndEmpty();
            }

            @Override
            public void onInvalidated() {
                checkErrorAndEmpty();
            }
        });
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        removeView(mChildView);
        if (mRelativeLayout == null) {
            mRelativeLayout = new RelativeLayout(getContext());
        }
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        addView(mRelativeLayout, params);
        mRelativeLayout.addView(mChildView,0, params);
    }

    private void checkErrorAndEmpty() {
        if (isError) {
            showError();
        } else {
            hideError();
            if (mAdapter != null) {
                final boolean emptyViewVisible = mAdapter.getCount() == 0;
                if (emptyViewVisible) {
                    showEmpty();
                } else {
                    post(new Runnable() {
                        @Override
                        public void run() {
                            hideEmpty();
                        }
                    });

                }
            } else {
                showEmpty();
            }
        }

    }

    public void setEmptyAndErrorView(View emptyView, View errorView) {
        setEmptyView(emptyView);
        setErrorView(errorView);
    }

    class CustomCoContext extends CoContext {
        @Override
        public View getTargetView() {
            return mRelativeLayout;
        }
    }
}
