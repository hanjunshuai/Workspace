package com.anningtex.library.animation;

import android.animation.Animator;
import android.view.View;

/**
 * @ClassName: BaseAnimation
 * @Description: java类作用描述
 * @Author: alvis
 * @CreateDate: 2020/6/8 13:43
 */
public abstract class BaseAnimation {
    public abstract Animator[] getAnimators(View view);
}
