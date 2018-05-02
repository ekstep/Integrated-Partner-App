package org.ekstep.genie.util.geniesdk;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.widget.Toast;

import org.ekstep.genie.CoreApplication;
import org.ekstep.genie.util.ContentLanguageJsonCreator;
import org.ekstep.genie.util.LogUtil;
import org.ekstep.genie.util.Util;
import org.ekstep.genieservices.commons.IParams;
import org.ekstep.genieservices.commons.IPlayerConfig;
import org.ekstep.genieservices.commons.bean.Content;
import org.ekstep.genieservices.commons.utils.ReflectionUtil;
import org.ekstep.genieservices.commons.utils.StringUtil;
import org.ekstep.genieservices.content.ContentConstants;
import org.ekstep.genieservices.utils.BuildConfigUtil;

import java.util.HashMap;

/**
 * Created on 7/17/2017.
 *
 * @author anil
 */
public class PlayerConfig implements IPlayerConfig {

    private static final String TAG = PlayerConfig.class.getSimpleName();

    private static final String GENIE_CANVAS_PACKAGE = "org.ekstep.geniecanvas";
    private static final String GENIE_QUIZ_APP_PACKAGE = "org.ekstep.quiz.app";
    private static final String GENIE_CANVAS_ACTIVITY = "org.ekstep.geniecanvas.MainActivity";
    private static final String BUNDLE_KEY_CONFIG = "config";

    @Override
    public Intent getPlayerIntent(Context context, Content content) {
        PackageManager manager = context.getPackageManager();
        String osId = content.getContentData().getOsId();

        Intent intent;
        if (isApk(content.getMimeType())) {
            if (isAppInstalled(context, osId)) {
                intent = manager.getLaunchIntentForPackage(osId);
            } else {
                openPlaystore(context, osId);
                return null;
            }

        } else {
            if (osId == null || GENIE_QUIZ_APP_PACKAGE.equals(osId) || GENIE_CANVAS_PACKAGE.equals(osId)) {
                Class<?> className = ReflectionUtil.getClass(GENIE_CANVAS_ACTIVITY);
                if (className == null) {
                    Toast.makeText(context, "Content player not found", Toast.LENGTH_SHORT).show();
                    return null;
                }
                intent = new Intent(context, className);
            } else {
                Toast.makeText(context, "Content player not found", Toast.LENGTH_SHORT).show();
                return null;
            }
        }
        if (Util.isChildSwitcherEnabled()) {
            HashMap<String, Object> userSwitcher = new HashMap<>();
            userSwitcher.put("enableUserSwitcher", false);
            intent.putExtra(BUNDLE_KEY_CONFIG, userSwitcher);
        }
        intent.putExtra("languageInfo", ContentLanguageJsonCreator.createLanguageBundleMap());
        intent.putExtra("origin", "Genie");
        String packageName = CoreApplication.getInstance().getClientPackageName();
        String appId = BuildConfigUtil.getBuildConfigValue(packageName, IParams.Key.APPLICATION_ID).toString();
        intent.putExtra("appQualifier", appId);

        return intent;
    }

    private boolean isEcmlOrHtml(String mimeType) {
        return ContentConstants.MimeType.ECML.equals(mimeType)
                || ContentConstants.MimeType.HTML.equals(mimeType);
    }

    private boolean isApk(String mimeType) {
        return ContentConstants.MimeType.APK.equals(mimeType);
    }

    private boolean isAppInstalled(Context context, String packageName) {
        if (StringUtil.isNullOrEmpty(packageName)) {
            return false;
        }

        boolean isInstalled;
        try {
            PackageManager pm = context.getPackageManager();
            pm.getPackageInfo(packageName, PackageManager.GET_ACTIVITIES);
            isInstalled = true;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            LogUtil.e(TAG, e.getMessage(), e);
            isInstalled = false;
        }

        return isInstalled;
    }

    private void openPlaystore(Context context, String osId) {
        Intent launchIntent = context.getPackageManager().getLaunchIntentForPackage("com.android.vending");
        ComponentName comp = new ComponentName("com.android.vending", "com.google.android.finsky.activities.LaunchUrlHandlerActivity");
        launchIntent.setComponent(comp);
        launchIntent.setData(Uri.parse("market://details?id=" + osId));
        context.startActivity(launchIntent);
    }
}
