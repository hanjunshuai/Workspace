package com.anningtex.library.animation;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.view.View;

/**
 * @ClassName: ScaleInAnimation
 * @Description: java类作用描述
 * @Author: alvis
 * @CreateDate: 2020/6/8 13:49
 */
public class ScaleInAnimation extends BaseAnimation {
    private static final float DEFAULT_ALPHA_FROM = 0.5f;
    private final float mFrom;

    public ScaleInAnimation() {
        this(DEFAULT_ALPHA_FROM);
    }

    public ScaleInAnimation(float from) {
        mFrom = from;
    }

    @Override
    public Animator[] getAnimators(View view) {
        ObjectAnimator scaleX = ObjectAnimator.ofFloat(view, "scaleX", mFrom, 1f);
        ObjectAnimator scaleY = ObjectAnimator.ofFloat(view, "scaleY", mFrom, 1f);
        return new Animator[]{scaleX, scaleY};
    }
}
