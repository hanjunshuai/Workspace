package com.anningtex.baserecyclerviewadapter.act;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.os.Bundle;

import com.anningtex.baserecyclerviewadapter.R;
import com.anningtex.baserecyclerviewadapter.adapter.SectionAdapter;
import com.anningtex.baserecyclerviewadapter.data.DataServer;
import com.anningtex.baserecyclerviewadapter.decoration.GridItemDecoration;

/**
 * @Description: des
 * @ClassName: SectionUerActivity
 * @Author: alvis
 * @CreateDate:2020-6-8 17:39:36
 */
public class SectionUerActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_section_uer);
        mRecyclerView = findViewById(R.id.rv_list);
        SectionAdapter sectionAdapter = new SectionAdapter(this, R.layout.image_view, R.layout.def_section_head, DataServer.getSampleData());
        mRecyclerView.addItemDecoration(new GridItemDecoration(this, R.drawable.list_divider));
        mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL));
        mRecyclerView.setAdapter(sectionAdapter);
    }
}
