package org.ekstep.genie.util;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.text.format.Formatter;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.KeyCharacterMap;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.WindowManager;

import org.ekstep.genieservices.utils.DeviceSpec;


@SuppressWarnings("deprecation")
public class DeviceUtility {

    public static double getItemSpan(Activity activity) {
        Display display = activity.getWindowManager().getDefaultDisplay();
        int mWidth = display.getWidth();
        double viewWidth = mWidth / 4;
        return viewWidth;
    }

    public static double getItemHeight(Activity activity) {
        Display display = activity.getWindowManager().getDefaultDisplay();
        int mHeight = display.getHeight() - dipToPx(activity, 48);
        double viewHeight = mHeight / 2;
        return viewHeight;
    }

    public static double getItemWidth(Activity activity) {
        Display display = activity.getWindowManager().getDefaultDisplay();
        int mWidth = display.getWidth();
        double viewWidth = mWidth / 2;
        return viewWidth;
    }

    public static int getDeviceHeight(Activity activity) {
        Display display = activity.getWindowManager().getDefaultDisplay();
        int mWidth = display.getHeight();
        return mWidth;
    }

    public static int getDeviceWidth(Activity activity) {
        Display display = activity.getWindowManager().getDefaultDisplay();
        int mWidth = display.getWidth();
        return mWidth;
    }

    public static int dipToPx(Activity activity, float dpValue) {
        final float scale = activity.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    public static int dp2px(Context context, float dipValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

    public static int px2dp(Context context, float pixel) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        return (int) ((pixel / displayMetrics.density) + 0.5);
    }

    public static void displayFullScreen(Activity activity) {
        activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN
                | WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS | WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN);

        if (Build.VERSION.SDK_INT > 11 && Build.VERSION.SDK_INT < 19) { // lower api
            View v = activity.getWindow().getDecorView();
            v.setSystemUiVisibility(View.GONE);
        } else if (Build.VERSION.SDK_INT >= 19) {
            //for new api versions.
            View decorView = activity.getWindow().getDecorView();
            int uiOptions = View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
            decorView.setSystemUiVisibility(uiOptions);
        }
    }

    public static void displayFullScreenDialog(Dialog mDialog, Activity activity) {
        //Set the dialog to not focusable (makes navigation ignore us adding the window)
        mDialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE, WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE);

        //Set the dialog to immersive
        mDialog.getWindow().getDecorView().setSystemUiVisibility(
                activity.getWindow().getDecorView().getSystemUiVisibility());

        //Show the dialog! (Hopefully no soft navigation...)
        mDialog.show();

        //Clear the not focusable flag from the window
        mDialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE);
    }

    public static void displayFullScreenPopUp(Activity activity) {

        if (Build.VERSION.SDK_INT > 11 && Build.VERSION.SDK_INT < 19) { // lower api
            View v = activity.getWindow().getDecorView();
            v.setSystemUiVisibility(View.GONE);
        } else if (Build.VERSION.SDK_INT >= 19) {
            //for new api versions.
            View decorView = activity.getWindow().getDecorView();
            int uiOptions = View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
            decorView.setSystemUiVisibility(uiOptions);
        }
    }

    public static void displayFullScreenAfterCancelEditKey(Activity activity) {
        if (Build.VERSION.SDK_INT > 11 && Build.VERSION.SDK_INT < 19) { // lower api
            View v = activity.getWindow().getDecorView();
            v.setSystemUiVisibility(View.GONE);
        } else if (Build.VERSION.SDK_INT >= 19) {
            //for new api versions.
            View decorView = activity.getWindow().getDecorView();
            int uiOptions =
                    View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
            decorView.setSystemUiVisibility(uiOptions);
        }
    }

    public static boolean isDeviceWithNavigationBar(Context context) {
        boolean hasMenuKey = ViewConfiguration.get(context).hasPermanentMenuKey();
        boolean hasBackKey = false;
        try {
            hasBackKey = KeyCharacterMap.deviceHasKey(KeyEvent.KEYCODE_BACK);
        } catch (NullPointerException e) {
        }

        if (!hasMenuKey && !hasBackKey) {
            return true;
        }
        return false;
    }

    public static int getNavigationBarHeight(Context context) {
        int navigationBarHeight = 0;

        if (isDeviceWithNavigationBar(context)) {
            //The device has a soft navigation bar
            Resources resources = context.getResources();
            int orientation = resources.getConfiguration().orientation;
            int resourceId;
            if (isTablet(context)) {
                resourceId = resources.getIdentifier(orientation == Configuration.ORIENTATION_PORTRAIT ? "navigation_bar_height" : "navigation_bar_height_landscape", "dimen", "android");
            } else {
                resourceId = resources.getIdentifier(orientation == Configuration.ORIENTATION_PORTRAIT ? "navigation_bar_height" : "navigation_bar_width", "dimen", "android");
            }

            if (resourceId > 0) {
                return resources.getDimensionPixelSize(resourceId);
            }
        }

        return navigationBarHeight;
    }

    public static int getNavigationBarPadding(Context context) {
        int navigationBarPadding = 0;

        if (isDeviceWithNavigationBar(context)) {
            navigationBarPadding = dipToPx((Activity) context, 20);
        }

        return navigationBarPadding;
    }

    public static boolean isTablet(Context context) {
        return (context.getResources().getConfiguration().screenLayout
                & Configuration.SCREENLAYOUT_SIZE_MASK)
                >= Configuration.SCREENLAYOUT_SIZE_LARGE;
    }

    public static String getReadableSize(long size, Context context) {
        return Formatter.formatFileSize(context, size);
    }

    public static boolean isDeviceMemoryLow() {
        int MEMORY_LIMIT = 100000;
        long availableSpace = DeviceSpec.getAvailableInternalMemorySize();
        if (availableSpace <= MEMORY_LIMIT)
            return true;
        else
            return false;
    }

    public static boolean isSdCardMemoryLow() {
        int MEMORY_LIMIT = 100000;
        long availableSpace = DeviceSpec.getAvailableExternalMemorySize();
        if (availableSpace <= MEMORY_LIMIT)
            return true;
        else
            return false;
    }
}
