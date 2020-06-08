package com.anningtex.baserecyclerviewadapter.act;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.anningtex.baserecyclerviewadapter.R;
import com.anningtex.baserecyclerviewadapter.adapter.QuickAdapter;
import com.anningtex.library.BaseQuickAdapter;
import com.anningtex.library.animation.BaseAnimation;
import com.jaredrummler.materialspinner.MaterialSpinner;

/**
 * @Description: des
 * @ClassName: MainActivity
 * @Author: alvis
 * @CreateDate:2020-6-8 11:13:24
 */
public class AnimationActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private QuickAdapter quickAdapter;
    private MaterialSpinner mSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mSpinner = findViewById(R.id.spinner);
        recyclerView = findViewById(R.id.rv_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        // def adapter
        //RecyclerView.Adapter adapter = new DefAdpater(this);
        // quick adapter

        quickAdapter = new QuickAdapter(this);
        quickAdapter.setOnRecyclerViewItemClickListener(new BaseQuickAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Toast.makeText(AnimationActivity.this, position + "", Toast.LENGTH_LONG).show();
            }
        });
        quickAdapter.setFirstOnly(false);
        quickAdapter.openLoadAnimation();
        recyclerView.setAdapter(quickAdapter);
        initMenu();
    }

    private void initMenu() {
        mSpinner.setItems("AlphaIn", "ScaleIn", "SlideInBottom", "SlideInLeft", "SlideInRight", "Custom");
        mSpinner.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, Object item) {
                switch (position) {
                    case 0:
                        quickAdapter.openLoadAnimation(BaseQuickAdapter.ALPHAIN);
                        break;
                    case 1:
                        quickAdapter.openLoadAnimation(BaseQuickAdapter.SCALEIN);
                        break;
                    case 2:
                        quickAdapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_BOTTOM);
                        break;
                    case 3:
                        quickAdapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_LEFT);
                        break;
                    case 4:
                        quickAdapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_RIGHT);
                        break;
                    case 5:
                        quickAdapter.openLoadAnimation(new BaseAnimation() {
                            @Override
                            public Animator[] getAnimators(View view) {
                                return new Animator[]{ObjectAnimator.ofFloat(view, "scaleY", 1, 1.1f, 1), ObjectAnimator.ofFloat(view, "scaleX", 1, 1.1f, 1)
                                };
                            }
                        });
                        break;
                    default:
                        throw new IllegalStateException("Unexpected value: " + position);
                }
                recyclerView.setAdapter(quickAdapter);
            }
        });
    }
}
