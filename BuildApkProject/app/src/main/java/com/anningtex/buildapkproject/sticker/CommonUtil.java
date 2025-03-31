package com.anningtex.buildapkproject.sticker;

import android.content.Context;
import android.view.MotionEvent;

import androidx.annotation.DimenRes;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class CommonUtil {
    private CommonUtil() {
    }

    public static int dimen(@DimenRes int res, Context context) {
        return context.getResources().getDimensionPixelOffset(res);
    }

    public static MotionEvent copyMotionEvent(MotionEvent motionEvent) {
        Class<?> c = MotionEvent.class;
        Method motionEventMethod = null;
        try {
            motionEventMethod = c.getMethod("copy");
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        MotionEvent copyMotionEvent = null;
        try {
            copyMotionEvent = (MotionEvent) motionEventMethod.invoke(motionEvent);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return copyMotionEvent;
    }
}
