package com.anningtex.library.animation;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.view.View;

/**
 * @ClassName: AlphaInAnimation
 * @Description: java类作用描述
 * @Author: alvis
 * @CreateDate: 2020/6/8 13:45
 */
public class AlphaInAnimation extends BaseAnimation {
    private static final float DEFAULT_ALPHA_FROM = 0f;
    private final float mFrom;

    public AlphaInAnimation() {
        this(DEFAULT_ALPHA_FROM);
    }

    public AlphaInAnimation(float from) {
        this.mFrom = from;
    }

    @Override
    public Animator[] getAnimators(View view) {
        return new Animator[]{ObjectAnimator.ofFloat(view, "alpha", mFrom, 1f)};
    }
}
