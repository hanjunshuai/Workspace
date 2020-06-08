package com.anningtex.library.animation;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.view.View;

/**
 * @ClassName: SlideInRightAnimation
 * @Description: java类作用描述
 * @Author: alvis
 * @CreateDate: 2020/6/8 13:54
 */
public class SlideInRightAnimation extends BaseAnimation {
    @Override
    public Animator[] getAnimators(View view) {
        return new Animator[]{ObjectAnimator.ofFloat(view, "translationX", view.getRootView().getWidth(), 0)};
    }
}
