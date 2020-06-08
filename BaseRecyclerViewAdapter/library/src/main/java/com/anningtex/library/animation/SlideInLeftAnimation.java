package com.anningtex.library.animation;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.view.View;

/**
 * @ClassName: SlideInLeftAnimation
 * @Description: java类作用描述
 * @Author: alvis
 * @CreateDate: 2020/6/8 13:53
 */
public class SlideInLeftAnimation extends BaseAnimation {
    @Override
    public Animator[] getAnimators(View view) {
        return new Animator[]{ObjectAnimator.ofFloat(view, "translationX", -view.getRootView().getWidth(), 0)};
    }
}
