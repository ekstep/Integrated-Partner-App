package org.ekstep.genie.util;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;

import org.ekstep.genie.R;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * Created on 25/7/17.
 * shriharsh
 */

public class ThemeUtility {
    public final static int THEME_YELLOW = 0;
    public final static int THEME_BLUE = 1;
    private static int sThemeNumber;

    public static void changeToTheme(int theme) {
        sThemeNumber = theme;
    }

    public static void onActivityCreateSetTheme(Activity activity) {
        switch (sThemeNumber) {
            default:
            case THEME_YELLOW:
                activity.setTheme(R.style.Theme_Yellow);
                break;
//            case THEME_BLUE:
//                activity.setTheme(R.style.Theme_Blue);
//                break;
        }
    }

    public static int getThemeId(Context context) {
        try {
            Class<?> wrapper = Context.class;
            Method method = wrapper.getMethod("getThemeResId");
            method.setAccessible(true);
            return (Integer) method.invoke(context);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static Drawable getDrawable(Context context, int[] attributes) {
        TypedArray a = context.getTheme().obtainStyledAttributes(getThemeId(context), attributes);
        Drawable attributeResourceId = a.getDrawable(0);
        return attributeResourceId;
    }

    public static int getColor(Context context, int[] attributes) {
        TypedArray a = context.getTheme().obtainStyledAttributes(getThemeId(context), attributes);
        return a.getColor(0, 0);
    }

    public static List<Drawable> getIconsFromAttributes(Context context, int[] attributes) {
        List<Drawable> drawerIconsList = new ArrayList<>();
        for (int i = 0; i < attributes.length; i++) {
            drawerIconsList.add(i, ThemeUtility.getDrawable(context, new int[]{attributes[i]}));
        }
        return drawerIconsList;
    }
}
