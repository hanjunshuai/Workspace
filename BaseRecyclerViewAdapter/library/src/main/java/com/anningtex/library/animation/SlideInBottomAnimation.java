package com.anningtex.library.animation;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.view.View;

/**
 * @ClassName: SlideInBottomAnimation
 * @Description: java类作用描述
 * @Author: alvis
 * @CreateDate: 2020/6/8 13:51
 */
public class SlideInBottomAnimation extends BaseAnimation {
    @Override
    public Animator[] getAnimators(View view) {
        return new Animator[]{ObjectAnimator.ofFloat(view, "translationY", view.getMeasuredHeight(), 0)};
    }
}
