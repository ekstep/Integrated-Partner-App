package org.ekstep.genie.util;

import android.app.Activity;
import android.content.Context;
import android.support.v7.view.ContextThemeWrapper;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.PopupWindow;

import org.ekstep.genie.R;
import org.ekstep.genie.customview.SimpleDividerItemDecoration;

/**
 * This Utility shows the PopUpWindow
 * <p>
 * Created by shriharsh on 11/1/17.
 */

public class PopUpWindowUtil {

    public static PopupWindow mPopupWindow;


    /**
     * Shows Pop up window with all the list of items
     *
     * @param popupwindowArgBuilder
     */
    public static void showPopUpWindow(final PopupWindowArgBuilder popupwindowArgBuilder) {
        mPopupWindow = new PopupWindow(popupwindowArgBuilder.context);

        RecyclerView recyclerView = new RecyclerView(new ContextThemeWrapper(popupwindowArgBuilder.context, R.style.ScrollbarRecyclerView));
        recyclerView.setLayoutManager(new LinearLayoutManager(popupwindowArgBuilder.context));
        recyclerView.addItemDecoration(new SimpleDividerItemDecoration(popupwindowArgBuilder.context));
        recyclerView.setHasFixedSize(true);
        recyclerView.setId(popupwindowArgBuilder.resourceId);
        recyclerView.setAdapter(popupwindowArgBuilder.adapter);
        recyclerView.setVerticalScrollBarEnabled(popupwindowArgBuilder.isScrollbarEnabled);
        recyclerView.setScrollbarFadingEnabled(popupwindowArgBuilder.isFadingScrollBarEnabled);
        mPopupWindow.setFocusable(!popupwindowArgBuilder.onBoardingStatus);
        mPopupWindow.setWidth(popupwindowArgBuilder.anchor.getWidth());
        mPopupWindow.setBackgroundDrawable(popupwindowArgBuilder.context.getResources().getDrawable(popupwindowArgBuilder.drawable));
        mPopupWindow.setHeight(WindowManager.LayoutParams.WRAP_CONTENT);
        mPopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                if (popupwindowArgBuilder.transparentView != null) {
                    popupwindowArgBuilder.transparentView.setVisibility(View.GONE);
                }
                clearPopUpWindow();
            }
        });
        mPopupWindow.setContentView(recyclerView);
        if (android.os.Build.VERSION.SDK_INT > 24) {
            int[] a = new int[2];
            popupwindowArgBuilder.anchor.getLocationInWindow(a);
            mPopupWindow.showAtLocation(((Activity) popupwindowArgBuilder.context).getWindow().getDecorView(), Gravity.NO_GRAVITY, a[0], a[1] + popupwindowArgBuilder.anchor.getHeight());
        } else {
            mPopupWindow.showAsDropDown(popupwindowArgBuilder.anchor, 0, 0);
        }
    }

    /**
     * Dismisses pop up window
     */
    public static void dismissPopUpWindow() {
        try {
            if (mPopupWindow != null && mPopupWindow.isShowing()) {
                mPopupWindow.dismiss();
                mPopupWindow = null;
            }
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }

    }

    public static void clearPopUpWindow() {
        mPopupWindow = null;
    }

    public static class PopupWindowArgBuilder {
        public View anchor;
        public int resourceId;
        public Context context;
        public RecyclerView.Adapter adapter;
        public int drawable;
        boolean onBoardingStatus = false;
        private View transparentView;
        private boolean focus = true;
        private boolean isScrollbarEnabled = true;
        private boolean isFadingScrollBarEnabled = false;

        public PopupWindowArgBuilder setScrollbarEnabled(boolean scrollbarEnabled) {
            isScrollbarEnabled = scrollbarEnabled;
            return this;
        }

        public PopupWindowArgBuilder setFadingScrollBarEnabled(boolean fadingScrollBarEnabled) {
            isFadingScrollBarEnabled = fadingScrollBarEnabled;
            return this;
        }

        public PopupWindowArgBuilder setAnchor(View view) {
            this.anchor = view;
            return this;
        }

        public PopupWindowArgBuilder setId(int resourceId) {
            this.resourceId = resourceId;
            return this;
        }

        public PopupWindowArgBuilder setAdapter(RecyclerView.Adapter adapter) {
            this.adapter = adapter;
            return this;
        }

        public PopupWindowArgBuilder setContext(Context context) {
            this.context = context;
            return this;
        }

        public PopupWindowArgBuilder setDrawable(int drawable) {
            this.drawable = drawable;
            return this;
        }

        public PopupWindowArgBuilder setTransparentView(View view) {
            this.transparentView = view;
            return this;
        }

        public PopupWindowArgBuilder setFocusable(boolean focus) {
            this.focus = focus;
            return this;
        }

        public PopupWindowArgBuilder isOnBoarding(String onBoardingStatus) {
            if (onBoardingStatus.equalsIgnoreCase("true")) {
                this.onBoardingStatus = true;
            }

            return this;
        }
    }

}
