package com.lcodecore.twinklingrefreshlayout;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TRefreshWithEmptyViewLayout;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.lcodecore.tkrefreshlayout.footer.MyLoadingView;
import com.lcodecore.tkrefreshlayout.header.MyRefreshView;
import com.lcodecore.twinklingrefreshlayout.adapter.MusicAdapter;
import com.lcodecore.twinklingrefreshlayout.utils.ToastUtil;

/**
 * 包含空view的listview
 *
 * @author haohao on 2017/8/23 10:52
 * @version v1.0
 */
public class MyActivity extends AppCompatActivity {

    private MusicAdapter adapter;
    private TRefreshWithEmptyViewLayout refreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_emptyview);

        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setNavigationIcon(R.drawable.back);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        setupListView((ListView) findViewById(R.id.listView));
        refreshLayout.startRefresh();
    }

    private void setupListView(final ListView listView) {
        refreshLayout = (TRefreshWithEmptyViewLayout) findViewById(R.id.refresh);
        View error = LayoutInflater.from(MyActivity.this).inflate(R.layout.base_errorview, null);
        refreshLayout.setErrorView(error, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                refreshLayout.startRefresh();
            }
        });
        View empty = LayoutInflater.from(MyActivity.this).inflate(R.layout.base_emptyview, null);
        refreshLayout.setEmptyView(empty, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                refreshLayout.startRefresh();
            }
        });

        MyRefreshView headerView = new MyRefreshView(this);
        refreshLayout.setHeaderView(headerView);
        MyLoadingView loadingView = new MyLoadingView(this);
        refreshLayout.setBottomView(loadingView);
        refreshLayout.setOverScrollRefreshShow(false);
        refreshLayout.setAutoLoadMore(true);
        refreshLayout.setHeaderHeight(36);
        refreshLayout.setBottomHeight(36);

        adapter = new MusicAdapter();
        listView.setAdapter(adapter);
        refreshLayout.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                ToastUtil.show("item clicked!");
            }
        });

        refreshLayout.setOnRefreshListener(new RefreshListenerAdapter() {
            @Override
            public void onRefresh(final TwinklingRefreshLayout refreshLayout1) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        adapter.refreshCard1();
                        refreshLayout.finishRefreshing();
                        refreshLayout.hideError();
                        refreshLayout.setEnableLoadmore(true);
                    }
                }, 2000);
            }

            @Override
            public void onLoadMore(final TwinklingRefreshLayout refreshLayout1) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        refreshLayout.finishLoadmore();
                        refreshLayout.showError();
                        refreshLayout.showEmpty();
                        //adapter.loadMoreCard1();
                        //listView.addFooterView(LayoutInflater.from(MyActivity.this).inflate(R.layout.base_errorview, null));
                        //refreshLayout.setEnableLoadmore(false);
                    }
                }, 2000);
            }
        });
    }
}
