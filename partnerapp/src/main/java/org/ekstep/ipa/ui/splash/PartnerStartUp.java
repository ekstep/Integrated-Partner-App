package org.ekstep.ipa.ui.splash;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;

import org.ekstep.genie.CoreApplication;
import org.ekstep.genie.hooks.IStartUp;
import org.ekstep.genie.ui.contentdetail.ContentDetailActivity;
import org.ekstep.genie.util.Constant;
import org.ekstep.genie.util.DeepLinkNavigation;
import org.ekstep.genie.util.LaunchUtil;
import org.ekstep.genie.util.LogUtil;
import org.ekstep.genie.util.preference.PreferenceUtil;
import org.ekstep.genieservices.commons.bean.HierarchyInfo;
import org.ekstep.genieservices.commons.utils.GsonUtil;
import org.ekstep.ipa.R;
import org.ekstep.ipa.ui.drive.DriveActivity;
import org.ekstep.ipa.ui.intro.IntroActivity;
import org.ekstep.ipa.ui.landing.LandingActivity;
import org.ekstep.ipa.util.Utils;
import org.ekstep.ipa.util.preference.PartnerPrefUtil;
import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author vinayagasundar
 */

public class PartnerStartUp implements IStartUp {

    private static final int SPLASH_TIME_OUT = 1000;

    private Activity mActivity;

    @Override
    public void init(Activity activity) {
        mActivity = activity;
    }

    @Override
    public void handleNotification(Intent intent) {
        Intent handleNotification = LaunchUtil.handleNotification(mActivity, intent.getExtras());

        if (PreferenceUtil.isFirstTime().equalsIgnoreCase("false")) {
            handleNotification.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            mActivity.startActivity(handleNotification);
            mActivity.finish();
        } else {
            handleNotification.putExtra(Constant.EXTRA_LAUNCH_IS_NOTIFICATION, true);
            EventBus.getDefault().postSticky(handleNotification);
            nextScreen(mActivity);
        }
    }

    @Override
    public void handleLocalDeepLink(Intent intent) {
        handleLocalDeepLink(intent.getData());
    }

    @Override
    public void handleServerDeepLink(Intent intent) {
        String url = intent.getData().toString();

        String newString = url.replace("https://", "").replace("http://", "");
        String[] pair = newString.split("/");
        if (pair[1].equalsIgnoreCase(DeepLinkNavigation.DEEPLINK_EXPLORE_CONTENT_IDENTIFIER)) {
//            LogUtil.i(TAG, "Pair2: " + pair[2]);

            startHomeActivity(pair[2]);
        } else {
            String identifier = url.substring(url.lastIndexOf('/') + 1, url.length());
            startContentDetailActivity(identifier, null, false);
        }
    }

    @Override
    public String[] requiredPermissions() {
        return new String[0];
    }

    @Override
    public void nextScreen(final Activity activity) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // Set the selected locale.

                Utils.registerPartnerIfNeed();

                if (!PartnerPrefUtil.isPartnerFirstLaunch()) {
                    Intent intent = new Intent(activity, LandingActivity.class);
                    activity.startActivity(intent);
                    activity.finish();
                    // Activity animation from right to left.
                    activity.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                } else {    // When Genie launch for the first time after installation.
                    PartnerPrefUtil.setPartnerFirstLaunch(false);
                    Intent intent = new Intent(activity, IntroActivity.class);
                    activity.startActivity(intent);
                    activity.finish();
                    // Activity animation from right to left.
                    activity.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                }
            }
        }, SPLASH_TIME_OUT);
    }

    private void handleLocalDeepLink(Uri intentData) {
        Object[] pathSegments = intentData.getPathSegments().toArray();
        List<HierarchyInfo> contentInfoList = null;
        String identifier = "";
        if (intentData.getHost().equalsIgnoreCase(CoreApplication.getInstance().getString(org.ekstep.genie.R.string.deeplink_ekstep_host_show_content))) {   // Content detail deeplink
            String[] strArray = intentData.getPath().toString().split("&");

            if (strArray != null && strArray.length == 1) {
                identifier = strArray[0].replace("/", "");
            } else if (strArray != null && strArray.length > 1) {
                identifier = strArray[0].replace("/", "");
                String[] infoArray = strArray[1].split("=");
                if (infoArray != null & infoArray.length > 1) {
                    LogUtil.e("ContentInfo", "" + infoArray[1]);
                    Map hierachyMap = GsonUtil.fromJson(infoArray[1], Map.class);
                    contentInfoList = createHierarchyInfo(hierachyMap);
                }

            }
            startContentDetailActivity(identifier, contentInfoList, true);
        }
    }

    private void startContentDetailActivity(String identifier, List<HierarchyInfo> hierarchyInfoList, boolean isCanvasDeeplink) {
        Intent intent = new Intent(mActivity, ContentDetailActivity.class);

//        LogUtil.i(TAG, "Identifier: " + identifier);
        intent.putExtra(Constant.BundleKey.BUNDLE_KEY_DEEP_LINK_IDENTIFIER, identifier);
        intent.putExtra(Constant.BundleKey.BUNDLE_KEY_HIERARCHY_INFO, (ArrayList<HierarchyInfo>) hierarchyInfoList);

        if (isCanvasDeeplink) {
            intent.putExtra(Constant.BundleKey.BUNDLE_KEY_IS_CANVAS_DEEPLINK, true);
        } else {
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        }


        mActivity.startActivity(intent);

        mActivity.finishAffinity();
    }


    private List<HierarchyInfo> createHierarchyInfo(Map hierarchyData) {
        List<HierarchyInfo> hierarchyInfo = new ArrayList<>();
        String[] identifiers = hierarchyData.get("id").toString().split("/");
        String identifierType = hierarchyData.get("type").toString();
        for (String id : identifiers) {
            hierarchyInfo.add(new HierarchyInfo(id, identifierType));
            //reset identifierType to null as we only get the root elements identifierType and we dont have to set idnetifier for the other elements
            identifierType = null;
        }
        return hierarchyInfo;
    }

    private void startHomeActivity(String exploreContent) {
        Intent intent = new Intent(mActivity, org.ekstep.genie.ui.landing.LandingActivity.class);

        // This screen is not exists after feb 2017 release
//        intent.putExtra(Constant.EXTRA_DEEP_LINK_EXPLORE_CONTENT, exploreContent);

        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        mActivity.startActivity(intent);

        mActivity.finishAffinity();
    }
}
