package org.ekstep.genie.util;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.graphics.Typeface;

import org.ekstep.genie.CoreApplication;
import org.ekstep.genie.R;
import org.ekstep.genie.util.preference.PreferenceUtil;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.Locale;
import java.util.Map;

/**
 * Created by swayangjit_gwl on 5/10/2016.
 */
public class FontUtil {

    private static final int[] mLanguageResources = {
            R.drawable.bg_english,
            R.drawable.bg_hindi,
            R.drawable.bg_kannada,
            R.drawable.bg_telugu,
            R.drawable.bg_marathi,
            R.drawable.bg_tamil,
            R.drawable.bg_punjabi,
            /*R.drawable.bg_malayalam,
            R.drawable.bg_bengali,
            R.drawable.bg_gujrati,
            R.drawable.bg_assamese,
            R.drawable.bg_odia,
            R.drawable.bg_marathi*/
    };

    private static final String TAG = FontUtil.class.getSimpleName();

    private static final Hashtable<String, Typeface> mFontCacheLato = new Hashtable<>();
    private static final Hashtable<String, Typeface> mFontCacheNoto = new Hashtable<>();
    private static final int POS_LANG_ENGLISH = 0;
    private static final int POS_LANG_HINDI = 1;
    private static final int POS_LANG_KANNADA = 2;
    private static final int POS_LANG_TELUGU = 3;
    private static final int POS_LANG_MARATHI = 4;
    private static final int POS_LANG_TAMIL = 5;
    private static final int POS_LANG_PUNJABI = 6;
    private static final int POS_LANG_MALAYALAM = 7;
    private static final int POS_LANG_BENGALI = 8;
    private static final int POS_LANG_GUJARATI = 9;
    private static final int POS_LANG_ASSAMESE = 10;
    private static final int POS_LANG_ORIYA = 11;

    private static Map mResourceBundleCache = new HashMap();
    private static FontUtil mInstance = null;

    private FontUtil() {
    }

    public static FontUtil getInstance() {
        if (mInstance == null) {
            mInstance = new FontUtil();
        }

        return mInstance;
    }

    public static void setResourceBundle(Map resourceBundle) {
        mResourceBundleCache = resourceBundle;
    }

    public int[] getLanguageResources() {
        return mLanguageResources;
    }

    public void changeLocale() {
        String localeString = PreferenceUtil.getLanguage();
        LogUtil.i(TAG, "locale ::: " + localeString);

        Locale defaultLocale = CoreApplication.getInstance().getResources().getConfiguration().locale;
        LogUtil.i(TAG, "changeLocale ==> default Language " + defaultLocale);

        if (!localeString.equalsIgnoreCase(defaultLocale.getLanguage())) {
            Locale locale = new Locale(localeString);
            Configuration config = new Configuration();
            config.locale = locale;
            CoreApplication.getInstance().getBaseContext().getResources().updateConfiguration(config, CoreApplication.getInstance().getBaseContext().getResources().getDisplayMetrics());
        }
    }

    public Typeface getLatoTypeFace(Context context) {
        synchronized (mFontCacheLato) {

            String locale = PreferenceUtil.getLanguage();

            if (!mFontCacheLato.containsKey(locale)) {
                Typeface tf = null;
                try {
                    String path = null;
                    if (locale.equalsIgnoreCase(FontConstants.LANG_ENGLISH)) {
                        path = getFontPath(context, R.style.Theme_Yellow, new int[]{R.attr.primaryFontPath});
                    } else if (locale.equalsIgnoreCase(FontConstants.LANG_HINDI)) {
                        path = getFontPath(context, R.style.Theme_Yellow, new int[]{R.attr.hindiFontPath});
                    } else if (locale.equalsIgnoreCase(FontConstants.LANG_KANNADA)) {
                        path = getFontPath(context, R.style.Theme_Yellow, new int[]{R.attr.kannadaFontPath});
                    } else if (locale.equalsIgnoreCase(FontConstants.LANG_TELUGU)) {
                        path = getFontPath(context, R.style.Theme_Yellow, new int[]{R.attr.teleguFontPath});
                    } else if (locale.equalsIgnoreCase(FontConstants.LANG_MARATHI)) {
                        path = getFontPath(context, R.style.Theme_Yellow, new int[]{R.attr.marathiFontPath});
                    } else if (locale.equalsIgnoreCase(FontConstants.LANG_TAMIL)) {
                        path = getFontPath(context, R.style.Theme_Yellow, new int[]{R.attr.tamilFontPath});
                    } else if (locale.equalsIgnoreCase(FontConstants.LANG_PUNJABI)) {
                        path = getFontPath(context, R.style.Theme_Yellow, new int[]{R.attr.punjabiFontPath});
                    }

                    if (path != null) {
                        tf = Typeface.createFromAsset(context.getAssets(), path);
                    }

                    mFontCacheLato.put(locale, tf);
                } catch (Exception e) {
                    tf = Typeface.createFromAsset(context.getAssets(), FontConstants.LATOO_ENGLISH);
                    mFontCacheLato.put(locale, tf);
                }

            }
            return mFontCacheLato.get(locale);
        }
    }

    public Typeface getNotoTypeFace(Context context) {
        synchronized (mFontCacheNoto) {

            String locale = PreferenceUtil.getLanguage();

            if (!mFontCacheNoto.containsKey(locale)) {
                Typeface tf = null;
                try {
                    String path = null;

                    path = getFontPath(context, R.style.Theme_Yellow, new int[]{R.attr.secondaryFontPath});

                    if (path != null) {
                        tf = Typeface.createFromAsset(context.getAssets(), path);
                    }

                    mFontCacheNoto.put(locale, tf);
                } catch (Exception e) {
                    tf = Typeface.createFromAsset(context.getAssets(), FontConstants.NOTO_COMBINED);
                    mFontCacheNoto.put(locale, tf);
                }
            }

            return mFontCacheNoto.get(locale);
        }
    }

    private String getFontPath(Context context, int theme, int[] attributes) {
        TypedArray a = context.getTheme().obtainStyledAttributes(theme, attributes);
        String attributeResourceId = a.getString(0);

        return attributeResourceId;
    }

    public String getLocaleAtPosition(int position) {
        String locale = "";
        switch (position) {
            case POS_LANG_ENGLISH:
                locale = FontConstants.LANG_ENGLISH;
                break;

            case POS_LANG_HINDI:
                locale = FontConstants.LANG_HINDI;
                break;

            case POS_LANG_KANNADA:
                locale = FontConstants.LANG_KANNADA;
                break;

            case POS_LANG_TELUGU:
                locale = FontConstants.LANG_TELUGU;
                break;

            case POS_LANG_MARATHI:
                locale = FontConstants.LANG_MARATHI;
                break;

            case POS_LANG_TAMIL:
                locale = FontConstants.LANG_TAMIL;
                break;

            case POS_LANG_PUNJABI:
                locale = FontConstants.LANG_PUNJABI;
                break;

           /* case POS_LANG_MALAYALAM:
                locale = FontConstants.LANG_MALAYALAM;
                break;

            case POS_LANG_BENGALI:
                locale = FontConstants.LANG_BENGALI;
                break;

            case POS_LANG_GUJARATI:
                locale = FontConstants.LANG_GUJARATI;
                break;

            case POS_LANG_ASSAMESE:
                locale = FontConstants.LANG_ASSAMESE;
                break;

            case POS_LANG_ORIYA:
                locale = FontConstants.LANG_ORIYA;
                break;
*/
            default:
                locale = FontConstants.LANG_ENGLISH;
                break;
        }

        return locale;
    }

    public int getPositionForSelectedLocale() {
        int position = -1;

        String locale = PreferenceUtil.getLanguage();

        if (locale.equalsIgnoreCase(FontConstants.LANG_ENGLISH)) {
            position = POS_LANG_ENGLISH;
        } else if (locale.equalsIgnoreCase(FontConstants.LANG_HINDI)) {
            position = POS_LANG_HINDI;
        } else if (locale.equalsIgnoreCase(FontConstants.LANG_KANNADA)) {
            position = POS_LANG_KANNADA;
        } else if (locale.equalsIgnoreCase(FontConstants.LANG_TELUGU)) {
            position = POS_LANG_TELUGU;
        } else if (locale.equalsIgnoreCase(FontConstants.LANG_MARATHI)) {
            position = POS_LANG_MARATHI;
        } else if (locale.equalsIgnoreCase(FontConstants.LANG_TAMIL)) {
            position = POS_LANG_TAMIL;
        } else if (locale.equalsIgnoreCase(FontConstants.LANG_PUNJABI)) {
            position = POS_LANG_PUNJABI;
        } /*else if (locale.equalsIgnoreCase(FontConstants.LANG_MALAYALAM)) {
            position = POS_LANG_MALAYALAM;
        } else if (locale.equalsIgnoreCase(FontConstants.LANG_BENGALI)) {
            position = POS_LANG_BENGALI;
        } else if (locale.equalsIgnoreCase(FontConstants.LANG_GUJARATI)) {
            position = POS_LANG_GUJARATI;
        } else if (locale.equalsIgnoreCase(FontConstants.LANG_ASSAMESE)) {
            position = POS_LANG_ASSAMESE;
        } else if (locale.equalsIgnoreCase(FontConstants.LANG_ORIYA)) {
            position = POS_LANG_ORIYA;
        } */

        return position;
    }

    public String getResourceName(String key) {
        String filterName = key;

        if (mResourceBundleCache != null && mResourceBundleCache.containsKey(key)) {
            filterName = mResourceBundleCache.get(key).toString();
        }

        String resourceName = filterName;

        if (PreferenceUtil.getLanguage().equalsIgnoreCase(FontConstants.LANG_ENGLISH)) {
            resourceName = Util.capitalizeFirstLetter(filterName);
        }

        return resourceName;
    }

}
