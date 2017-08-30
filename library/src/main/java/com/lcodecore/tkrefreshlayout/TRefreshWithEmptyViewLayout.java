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
    private View emptyView;
    private View errorView;
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

    public void setEmptyView(View emptyView) {
        setEmptyView(emptyView, null);
    }

    public void setEmptyView(View emptyView, OnClickListener clickListener) {
        if (emptyView == null) return;
        this.emptyView = emptyView;
        ViewGroup.LayoutParams params =
                new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT);
        if (mRelativeLayout == null) {
            mRelativeLayout = new RelativeLayout(getContext());
        }
        if (errorView == null) {
            mRelativeLayout.addView(emptyView, params);
        } else {
            int index = mRelativeLayout.indexOfChild(errorView);
            if (index == -1) {
                mRelativeLayout.addView(emptyView, params);
            } else {
                mRelativeLayout.addView(emptyView, index, params);
            }
        }
        emptyView.setVisibility(INVISIBLE);

        if (clickListener != null) {
            emptyView.setOnClickListener(clickListener);
        } else {
            emptyView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
        }
    }

    public void setErrorView(View errorView) {
        setErrorView(errorView, null);
    }

    public void setErrorView(View errorView, OnClickListener onClickListener) {
        if (errorView == null) return;
        this.errorView = errorView;
        ViewGroup.LayoutParams params =
                new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT);
        if (mRelativeLayout == null) {
            mRelativeLayout = new RelativeLayout(getContext());
        }
        mRelativeLayout.addView(errorView, params);
        errorView.setVisibility(INVISIBLE);
        if (onClickListener != null) {
            errorView.setOnClickListener(onClickListener);
        } else {
            errorView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
        }
    }

    public void showEmpty() {
        if (emptyView != null) emptyView.setVisibility(VISIBLE);
    }

    public void hideEmpty() {
        if (emptyView != null && emptyView.isShown()) {
            emptyView.setVisibility(INVISIBLE);
        }
    }

    public void showError() {
        if (errorView != null) {
            errorView.setVisibility(VISIBLE);
            hideEmpty();
        }
    }

    public void hideError() {
        if (errorView != null && errorView.isShown()) {
            errorView.setVisibility(INVISIBLE);
        }
        checkEmpty();
    }

    public void setAdapter(Adapter adapter) {
        this.mAdapter = adapter;
        mAdapter.registerDataSetObserver(new DataSetObserver() {
            @Override
            public void onChanged() {
                checkEmpty();
            }

            @Override
            public void onInvalidated() {
                checkEmpty();
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
        ViewGroup.LayoutParams params =
                new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT);
        addView(mRelativeLayout, params);
        mRelativeLayout.addView(mChildView, 0, params);
    }

    private void checkEmpty() {
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

    class CustomCoContext extends CoContext {
        @Override
        public View getTargetView() {
            return mRelativeLayout;
        }
    }
}
