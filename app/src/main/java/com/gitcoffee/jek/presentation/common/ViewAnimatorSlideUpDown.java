package com.gitcoffee.jek.presentation.common;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.res.Resources;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;

public class ViewAnimatorSlideUpDown {

    private static final int ANIMATION_DURATION = 350;

    public interface OnCompleteAnimation{
        void onComplete();
    }
  
    public static void slideDown(final View view,final OnCompleteAnimation onCompleteAnimation) {
        view.setVisibility(View.VISIBLE);
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        layoutParams.height = 1;
        view.setLayoutParams(layoutParams);

        view.measure(View.MeasureSpec.makeMeasureSpec(Resources.getSystem().getDisplayMetrics().widthPixels,
                View.MeasureSpec.EXACTLY),
                View.MeasureSpec.makeMeasureSpec(0,
                        View.MeasureSpec.UNSPECIFIED));

        final int height = view.getMeasuredHeight();
        ValueAnimator valueAnimator = ObjectAnimator.ofInt(1, height);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int value = (int) animation.getAnimatedValue();
                if (height > value) {
                    ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
                    layoutParams.height = value;
                    view.setLayoutParams(layoutParams);
                }else{
                    ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
                    layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT;
                    view.setLayoutParams(layoutParams);
                    onCompleteAnimation.onComplete();
                }
            }
        });
        valueAnimator.start();
    }


    public static void hideView(final View view,final OnCompleteAnimation onCompleteAnimation) {

        view.post(new Runnable() {
            @Override
            public void run() {
                final int height = view.getHeight();

                ValueAnimator valueAnimator = ObjectAnimator.ofInt(height, 0);
                valueAnimator.setInterpolator(new AccelerateInterpolator());
                valueAnimator.setDuration(ANIMATION_DURATION);

                valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        int value = (int) animation.getAnimatedValue();
                        if (value > 0) {
                            ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
                            layoutParams.height = value;
                            view.setLayoutParams(layoutParams);
                        }else{
                            onCompleteAnimation.onComplete();
                            view.setVisibility(View.GONE);
                        }
                    }
                });
                valueAnimator.start();
            }
        });
    }


}