package com.arronyee.form.utils;

import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.CycleInterpolator;
import android.view.animation.TranslateAnimation;

/**
 * Author   :hymanme
 * Email    :hymanme@163.com
 * Create at 2016/3/9 0009
 * Description:
 */
public class AnimationUtils {
    /**
     * 晃动动画
     *
     * @param counts 1秒钟晃动多少下
     * @return
     */
    public static Animation shakeAnimation(int counts) {
        Animation translateAnimation = new TranslateAnimation(0, 10, 0, 0);
        translateAnimation.setInterpolator(new CycleInterpolator(counts));
        translateAnimation.setDuration(1000);
        return translateAnimation;
    }

    /**
     * 背景变暗
     *
     * @param mWindow
     * @param bgColor 背景明暗程度
     */
    public static void darkBackgroundColor(Window mWindow, Float bgColor) {
        WindowManager.LayoutParams lp = mWindow.getAttributes();
        lp.alpha = bgColor;
//        mWindow.addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);//导致界面销毁的时候黑屏
        mWindow.setAttributes(lp);
    }

}
