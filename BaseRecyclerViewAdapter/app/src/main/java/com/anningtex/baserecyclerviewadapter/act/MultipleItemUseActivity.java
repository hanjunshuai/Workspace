package com.anningtex.baserecyclerviewadapter.act;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.anningtex.baserecyclerviewadapter.R;
import com.anningtex.baserecyclerviewadapter.adapter.MultipleItemQuickAdapter;
import com.anningtex.baserecyclerviewadapter.data.DataServer;

/**
 * @Description: des
 * @ClassName: MultipleItemUseActivity
 * @Author: alvis
 * @CreateDate:2020-6-12 14:44:30
 */
public class MultipleItemUseActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multiple_item_use);
        mRecyclerView = findViewById(R.id.rv_list);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        MultipleItemQuickAdapter multipleItemAdapter = new MultipleItemQuickAdapter(this, DataServer.getMultipleItemData());
        mRecyclerView.setAdapter(multipleItemAdapter);
    }
}
