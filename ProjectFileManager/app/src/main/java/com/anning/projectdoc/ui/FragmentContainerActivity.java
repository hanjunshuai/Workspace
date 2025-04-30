package com.anning.projectdoc.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.MenuItem;

import com.anning.projectdoc.R;
import com.anning.projectdoc.adapter.FragmentAdapter;
import com.anning.projectdoc.lib.content.ZFileContent;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class FragmentContainerActivity extends AppCompatActivity {
    private Toolbar containerToolBar;
    private TabLayout containerTabLayout;
    private ViewPager containerViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment_container);

        containerToolBar = findViewById(R.id.container_toolBar);
        containerTabLayout = findViewById(R.id.container_tabLayout);
        containerViewPager = findViewById(R.id.container_viewPager);

        containerToolBar.setTitle("所有文档");
        containerToolBar.inflateMenu(R.menu.menu_file_child);
        containerToolBar.setOnMenuItemClickListener(item -> {
            switch (item.getItemId()) {
                case R.id.menu_sort:
                    break;
            }
            return false;
        });

        List<Fragment> fragments = new ArrayList<>();
        List<String> titles = new ArrayList<>();
        titles.add("全部");
        titles.add("DOC");
        titles.add("PPT");
        titles.add("XLS");
        titles.add("PDF");
        for (String title : titles) {
            fragments.add(FileChildFragment.newInstance(title));
        }
        FragmentAdapter fragmentAdapter = new FragmentAdapter(fragments, titles, getSupportFragmentManager());
        containerViewPager.setAdapter(fragmentAdapter);
        containerViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        containerTabLayout.setupWithViewPager(containerViewPager);
    }
}