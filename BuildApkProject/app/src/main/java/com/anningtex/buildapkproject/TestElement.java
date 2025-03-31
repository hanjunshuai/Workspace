package com.anningtex.buildapkproject;

import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.widget.AppCompatImageView;

import com.anningtex.buildapkproject.sticker.DecorationElement;

public class TestElement extends DecorationElement {
    private ImageView mTestImageView;

    public TestElement(float originWidth, float originHeight) {
        super(originWidth, originHeight);
    }

    @Override
    protected View initView() {
        mTestImageView = new AppCompatImageView(mElementContainerView.getContext());
        mTestImageView.setImageResource(R.mipmap.ic_launcher);
        return mTestImageView;
    }
}
