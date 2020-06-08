package com.anningtex.baserecyclerviewadapter.act;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.anningtex.baserecyclerviewadapter.R;
import com.anningtex.baserecyclerviewadapter.adapter.QuickAdapter;
import com.anningtex.library.BaseQuickAdapter;

/**
 * @Description: des
 * @ClassName: HeaderAndFooterUseActivity
 * @Author: alvis
 * @CreateDate:2020-6-8 16:39:56
 */
public class HeaderAndFooterUseActivity extends AppCompatActivity {
    private QuickAdapter mQuickAdapter;
    private static final int PAGE_SIZE = 3;
    private RecyclerView mRecycler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_header_and_footer_use);
        mRecycler = findViewById(R.id.recycler);
        mRecycler.setLayoutManager(new LinearLayoutManager(this));
        initAdapter();
        mQuickAdapter.addHeaderView(getView());
        mQuickAdapter.addFooterView(getView());
        mRecycler.setAdapter(mQuickAdapter);
    }

    private View getView() {
        View view = getLayoutInflater().inflate(R.layout.head_view, null, false);
        view.setLayoutParams(new DrawerLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(HeaderAndFooterUseActivity.this, "click View", Toast.LENGTH_LONG).show();
            }
        });
        return view;
    }

    private void initAdapter() {
        mQuickAdapter = new QuickAdapter(HeaderAndFooterUseActivity.this, PAGE_SIZE);
        mQuickAdapter.openLoadAnimation();
        mRecycler.setAdapter(mQuickAdapter);
        mQuickAdapter.setOnRecyclerViewItemClickListener(new BaseQuickAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Toast.makeText(HeaderAndFooterUseActivity.this, "" + position, Toast.LENGTH_LONG).show();
            }
        });
    }
}
