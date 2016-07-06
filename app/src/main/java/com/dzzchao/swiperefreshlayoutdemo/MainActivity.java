package com.dzzchao.swiperefreshlayoutdemo;

import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.dzzchao.swiperefreshlayoutdemo.adapter.MyListAdapter;
import com.dzzchao.swiperefreshlayoutdemo.view.CustomSwipeRefreshLayout;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private List dataList;
    private MyListAdapter mListAdapter;
    private CustomSwipeRefreshLayout swipeRefreshLayout;
    private CoordinatorLayout coordinatorLayout;

    private View footerLayout;
    private TextView textMore;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initData();
        initView();
    }


    private void initData() {
        dataList = new ArrayList();
        for (int i = 0; i < 20; i++) {
            dataList.add("T" + i);
        }
    }

    private void initView() {
        swipeRefreshLayout = (CustomSwipeRefreshLayout) findViewById(R.id.swipeRefreshLayout);
        //设置显示的颜色
        swipeRefreshLayout.setColorSchemeResources(R.color.red);

        ListView mListView = (ListView) findViewById(R.id.listView);


        //底部加载更多
        footerLayout = getLayoutInflater().inflate(R.layout.listview_footer, null);
        textMore = (TextView) footerLayout.findViewById(R.id.text_more);
        progressBar = (ProgressBar) footerLayout.findViewById(R.id.load_progress_bar);
        textMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                simulateLoadingData();
            }
        });


        mListView.addFooterView(footerLayout);
        swipeRefreshLayout.setChildView(mListView);

        //初始化ListView的Adapter
        mListAdapter = new MyListAdapter(this, dataList);
        mListView.setAdapter(mListAdapter);

        //使用SwipeRefreshLayout的下拉刷新监听
        //use SwipeRefreshLayout OnRefreshListener
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                simulateFetchingData();
            }
        });


        //使用自定义的RefreshLayout加载更多监听
        //use customed RefreshLayout OnLoadListener
        swipeRefreshLayout.setOnLoadListener(new CustomSwipeRefreshLayout.OnLoadListener() {
            @Override
            public void onLoad() {
                simulateLoadingData();
            }
        });

        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordinatorLayout);
    }

    /**
     * 模拟一个耗时操作，获取完数据后刷新ListView
     * simulate update ListView and stop refresh after a time-consuming task
     */
    private void simulateFetchingData() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                dataList.add(0, "下拉刷新数据");
                swipeRefreshLayout.setRefreshing(false);
                mListAdapter.notifyDataSetChanged();
                textMore.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.GONE);
                Snackbar.make(coordinatorLayout, "下拉刷新完毕", Snackbar.LENGTH_SHORT).setAction("点我", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(MainActivity.this, "点我干嘛", Toast.LENGTH_LONG).show();
                    }
                }).show();
            }
        }, 1000);
    }


    /**
     * 模拟一个耗时操作，加载完更多底部数据后刷新ListView
     * simulate update ListView and stop load more after after a time-consuming task
     */
    private void simulateLoadingData() {
        textMore.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 5; i++) {
                    dataList.add(dataList.size(), "上拉刷新" + i);
                }
                swipeRefreshLayout.setLoading(false);
                mListAdapter.notifyDataSetChanged();
                textMore.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.GONE);
                Toast.makeText(MainActivity.this, "Load Finished!", Toast.LENGTH_SHORT).show();
            }
        }, 1000);
    }
}