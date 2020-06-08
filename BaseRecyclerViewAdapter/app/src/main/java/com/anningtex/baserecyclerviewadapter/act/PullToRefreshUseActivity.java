package com.anningtex.baserecyclerviewadapter.act;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.anningtex.baserecyclerviewadapter.R;
import com.anningtex.baserecyclerviewadapter.adapter.QuickAdapter;
import com.anningtex.baserecyclerviewadapter.data.DataServer;
import com.anningtex.library.BaseQuickAdapter;

/**
 * @Description: des
 * @ClassName: PullToRefreshUseActivity
 * @Author: alvis
 * @CreateDate:2020-6-8 16:40:14
 */
public class PullToRefreshUseActivity extends AppCompatActivity implements BaseQuickAdapter.RequestLoadMoreListener, SwipeRefreshLayout.OnRefreshListener {
    private RecyclerView mRecyclerView;
    private QuickAdapter mQuickAdapter;
    private SwipeRefreshLayout mSwipeRefreshLayout;

    private static final int TOTAL_COUNTER = 18;

    private static final int PAGE_SIZE = 6;

    private int mCurrentCounter = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pull_to_refresh_use);
        mRecyclerView = findViewById(R.id.rv_list);
        mSwipeRefreshLayout = findViewById(R.id.swipeLayout);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        initAdapter();
        addHeadView();
        mRecyclerView.setAdapter(mQuickAdapter);
    }

    private void addHeadView() {
        View headView = getLayoutInflater().inflate(R.layout.head_view, null);
        headView.setLayoutParams(new DrawerLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        headView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(PullToRefreshUseActivity.this, "click HeadView", Toast.LENGTH_LONG).show();
            }
        });
        mQuickAdapter.addHeaderView(headView);
    }

    private int delayMillis = 1000;

    @Override
    public void onLoadMoreRequested() {
        if (mCurrentCounter >= TOTAL_COUNTER) {
            mRecyclerView.post(new Runnable() {
                @Override
                public void run() {
                    mQuickAdapter.isNextLoad(false);
                }
            });

        } else {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    DataServer.addData(mQuickAdapter.getData(), PAGE_SIZE);
                    mCurrentCounter = mQuickAdapter.getItemCount();
                    mQuickAdapter.isNextLoad(true);

                }
            }, delayMillis);
        }
    }


    @Override
    public void onRefresh() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                initAdapter();
                mSwipeRefreshLayout.setRefreshing(false);
            }
        }, delayMillis);
    }

    private void initAdapter() {
        mQuickAdapter = new QuickAdapter(PullToRefreshUseActivity.this, PAGE_SIZE);
        mQuickAdapter.openLoadAnimation();
        mRecyclerView.setAdapter(mQuickAdapter);
        mCurrentCounter = mQuickAdapter.getItemCount();
        mQuickAdapter.setLoadMoreListener(PAGE_SIZE, this);
        addHeadView();
        mQuickAdapter.setOnRecyclerViewItemClickListener(new BaseQuickAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Toast.makeText(PullToRefreshUseActivity.this, "" + position, Toast.LENGTH_LONG).show();
            }
        });
    }
}
