package com.anningtex.buildapkproject;


import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.anningtex.base.AbsBaseActivity;
import com.anningtex.buildapkproject.sticker.AnimationElement;
import com.anningtex.buildapkproject.sticker.Sticker;
import com.anningtex.buildapkproject.sticker.TrashElementContainerView;

public class MainActivity extends AbsBaseActivity {
    private TrashElementContainerView mElementContainerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView text = findViewById(R.id.text);
        text.setText(BuildConfig.appName);

        Sticker.initialize(this);
        mElementContainerView = findViewById(R.id.element_container_view);
        mElementContainerView.setNeedAutoUnSelect(false);

        Button addTestElement = findViewById(R.id.add_test_element);
        addTestElement.setOnClickListener(v -> {
            TestElement testElementDrawer = new TestElement(500, 500);
            mElementContainerView.unSelectElement();
//            mElementContainerView.addElement(testElementDrawer);
            mElementContainerView.addSelectAndUpdateElement(testElementDrawer);
        });

//        Button addStaticElement = findViewById(R.id.add_static_element);
//        addStaticElement.setOnClickListener(v -> {
//            StaticStickerElement staticStickerElement = StaticStickerElement.getStaticStickerElementByUri(
//                    500, 500, "https://ss0.bdstatic.com/70cFvHSh_Q1YnxGkpoWK1HF6hhy/it/u=2335883016,884179302&fm=200&gp=0.jpg");
//            mElementContainerView.unSelectElement();
//            mElementContainerView.addSelectAndUpdateElement(staticStickerElement);
//        });

        Button startAnimation = findViewById(R.id.start_animation);
        startAnimation.setOnClickListener(v -> {
            AnimationElement element = (AnimationElement) mElementContainerView.getSelectElement();
            AnimationElement.TransformParam to = new AnimationElement.TransformParam(element);
            to.mRotate = 0;
            to.mScale = 1;
            to.mMoveY += 500;
            to.mMoveX += 500;
            element.startElementAnimation(to);
        });

        Button restoreAnimation = findViewById(R.id.restore_animation);
        restoreAnimation.setOnClickListener(v -> {
            AnimationElement element = (AnimationElement) mElementContainerView.getSelectElement();
            element.restoreToBeforeAnimation();
        });
    }
}