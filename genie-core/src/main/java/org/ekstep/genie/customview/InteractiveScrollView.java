package org.ekstep.genie.customview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ScrollView;

/**
 * Created by shriharsh on 3/2/17.
 */

public class InteractiveScrollView extends ScrollView {
    private OnInteractListener mListener;
    private Runnable scrollerTask;
    private int initialPosition;
    private int newCheck = 100;


    public InteractiveScrollView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public InteractiveScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
        scrollerTask = new Runnable() {

            public void run() {
                int newPosition = getScrollY();
                if (initialPosition - newPosition == 0) {

                    if (mListener != null) {

                        mListener.onScrollStopped();
                    }
                } else {
                    initialPosition = getScrollY();
                    postDelayed(scrollerTask, newCheck);
                }
            }
        };
    }

    public InteractiveScrollView(Context context) {
        super(context);
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        View view = (View) getChildAt(getChildCount() - 1);
        int diff = (view.getBottom() - (getHeight() + getScrollY()));

        if (diff == 0 && mListener != null) {
            mListener.onBottomReached();
        }

        super.onScrollChanged(l, t, oldl, oldt);
    }


    @Override
    protected void onOverScrolled(int scrollX, int scrollY, boolean clampedX, boolean clampedY) {
        //When scrollview is overscrolled.
        if (clampedY && scrollY > 0 && mListener != null) {
            mListener.onBottomReached();
        }
        super.onOverScrolled(scrollX, scrollY, clampedX, clampedY);
    }

    public void setOnBottomReachedListener(OnInteractListener onBottomReachedListener) {
        mListener = onBottomReachedListener;
    }


    public void startScrollerTask() {
        initialPosition = getScrollY();
        postDelayed(scrollerTask, newCheck);
    }


    public interface OnInteractListener {
        public void onBottomReached();

        public void onScrollStopped();
    }

}