package com.anningtex.baserecyclerviewadapter.act;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.anningtex.baserecyclerviewadapter.R;
import com.anningtex.baserecyclerviewadapter.adapter.HomeAdapter;
import com.anningtex.baserecyclerviewadapter.entity.HomeItem;
import com.anningtex.library.BaseQuickAdapter;

import java.util.ArrayList;

/**
 * @Description: des
 * @ClassName: MainActivity
 * @Author: alvis
 * @CreateDate:2020-6-8 14:28:12
 */
public class MainActivity extends AppCompatActivity {
    private static final Class<?>[] ACTIVITY = {AnimationActivity.class, HeaderAndFooterUseActivity.class,
            PullToRefreshUseActivity.class, SectionUerActivity.class, MultipleItemUseActivity.class};
    private static final String[] TITLE = {"Animation Use", "HeaderAndFooter Use", "PullToRefresh Use", "SectionUer use ", "MultipleItem Use"};
    private ArrayList<HomeItem> mDataList;
    private RecyclerView mRecycler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        mRecycler = findViewById(R.id.recycler);

        initData();
        mRecycler.setLayoutManager(new GridLayoutManager(this, 3));
        BaseQuickAdapter homeAdapter = new HomeAdapter(this, R.layout.home_item_view, mDataList);
        homeAdapter.setOnRecyclerViewItemClickListener(new BaseQuickAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(MainActivity.this, ACTIVITY[position]);
                startActivity(intent);
            }
        });
        mRecycler.setAdapter(homeAdapter);
    }

    private void initData() {
        mDataList = new ArrayList<>();
        for (int i = 0; i < TITLE.length; i++) {
            HomeItem item = new HomeItem();
            item.title = TITLE[i];
            item.activity = ACTIVITY[i];
            mDataList.add(item);
        }
    }
}
