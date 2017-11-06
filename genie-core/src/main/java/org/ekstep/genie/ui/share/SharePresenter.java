package org.ekstep.genie.ui.share;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;

import org.ekstep.genie.CoreApplication;
import org.ekstep.genie.R;
import org.ekstep.genie.base.BaseView;
import org.ekstep.genie.model.DisplayResolveInfo;
import org.ekstep.genie.model.enums.ContentType;
import org.ekstep.genie.util.Constant;
import org.ekstep.genie.util.FileHandler;
import org.ekstep.genie.util.geniesdk.ContentUtil;
import org.ekstep.genie.util.preference.PreferenceUtil;
import org.ekstep.genieservices.commons.bean.Content;
import org.ekstep.genieservices.commons.bean.ContentFilterCriteria;
import org.ekstep.genieservices.commons.bean.GenieResponse;
import org.ekstep.genieservices.commons.bean.Profile;
import org.ekstep.genieservices.commons.bean.enums.JWTokenType;
import org.ekstep.genieservices.commons.utils.Base64Util;
import org.ekstep.genieservices.commons.utils.CryptoUtil;
import org.ekstep.genieservices.utils.DeviceSpec;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kannappan on 12/26/2016.
 */

public class SharePresenter implements ShareContract.Presenter {
    private static final String GENIE_SUPPORT_FILE = "genie_support.txt";
    private static final String GENIE_SUPPORT_DIRECTORY = "GenieSupport";
    private ShareContract.View mView;
    private Context mContext;

    public SharePresenter() {
    }

    private static String encodeToBase64Uri(byte[] data) {
        //The 11 magic number indicates that it should be
        // base64Uri and without wrap and with the = at the end
        return Base64Util.encodeToString(data, 11);
    }

    @Override
    public void handleAndPopulateShareOptions(Bundle bundle) {
        List<String> identifierList = bundle.getStringArrayList(Constant.SHARE_IDENTIFIER);
        String contentName = bundle.getString(Constant.SHARE_NAME);
        String screenName = bundle.getString(Constant.SHARE_SCREEN_NAME);
        if (isEcarnLink(bundle)) {
            //Populates intent for both Ecar and Link
            mView.displayEcarLinkShareView(identifierList, contentName, screenName);
        } else if (isLink(bundle)) {
            //Populates intent for only Link
            mView.displayLinkShareView(identifierList, contentName, screenName);
        } else if (isGenie(bundle)) {
            //Populates intent for only Genie apk
            mView.displayGenieShareView();
        } else if (isGenieConfigurations(bundle)) {
            //Populates intent for only Genie Configurations
            mView.displayGenieConfigShareView();
        } else if (isProfile(bundle)) {
            //Populates intent for only profile
            mView.displayProfileShareView(identifierList, contentName, screenName);
        } else if (isTelemetry(bundle)) {
            //Populates intent for Telemtry(.gsa files)
            mView.displayTelemetryShareView(identifierList, contentName, screenName);
        } else {
            //Populates intent for only files
            mView.displayFileShareView(identifierList, contentName, screenName);
        }
    }

    private boolean isProfile(@Nullable Bundle savedInstanceState) {
        return savedInstanceState.getBoolean(Constant.IS_PROFILE, false);
    }

    private boolean isLink(@Nullable Bundle savedInstanceState) {
        return savedInstanceState.getBoolean(Constant.IS_LINK, false);
    }

    private boolean isEcarnLink(@Nullable Bundle savedInstanceState) {
        return savedInstanceState.getBoolean(Constant.IS_ECAR_N_LINK, false);
    }

    private boolean isTelemetry(@Nullable Bundle savedInstanceState) {
        return savedInstanceState.getBoolean(Constant.IS_TELEMETRY, false);
    }

    private boolean isGenie(@Nullable Bundle savedInstanceState) {
        return savedInstanceState.getBoolean(Constant.IS_GENIE, false);
    }

    private boolean isGenieConfigurations(@Nullable Bundle savedInstanceState) {
        return savedInstanceState.getBoolean(Constant.IS_GENIE_CONFIGURATIONS, false);
    }

    @Override
    public Intent getEcarShareIntent(List<String> identifierList, String contentName, String screenName) {
        Intent shareIntent = createCommonShareIntent(identifierList, contentName, screenName);
        if (identifierList.size() == 1) {
            shareIntent.putExtra(Intent.EXTRA_SUBJECT, mContext.getResources().getString(R.string.msg_share_lesson_subject));
        } else {
            shareIntent.putExtra(Intent.EXTRA_SUBJECT, mContext.getResources().getString(R.string.msg_share_collection_subject));
        }
        shareIntent.putExtra(Intent.EXTRA_TEXT, mContext.getResources().getString(R.string.msg_share_content) + contentName
                + mContext.getResources().getString(R.string.msg_share_content1));
        shareIntent.setType(Constant.ZIP_MIME_TYPE);
        shareIntent.putExtra(Constant.IS_ECAR, true);
        return shareIntent;
    }

    @Override
    public Intent getLinkShareIntent(List<String> identifierList, String contentName, String screenName) {
        Intent shareIntent = createCommonShareIntent(identifierList, contentName, screenName);
        shareIntent.putExtra(Intent.EXTRA_SUBJECT, mContext.getResources().getString(R.string.msg_share_lesson_subject));
        shareIntent.putExtra(Intent.EXTRA_TEXT, mContext.getResources().getString(R.string.msg_share_content) +
                contentName + mContext.getResources().getString(R.string.msg_share_content1) +
                "\n" + Constant.EKSTEP_URL + identifierList.get(0));
        shareIntent.setType(Constant.TXT_MIME_TYPE);
        shareIntent.putExtra(Constant.IS_LINK, true);
        return shareIntent;
    }

    @Override
    public Intent getProfileShareIntent(List<String> identifierList, String contentName, String screenName) {
        Intent shareIntent = createCommonShareIntent(identifierList, contentName, screenName);
        shareIntent.putExtra(Intent.EXTRA_SUBJECT, mContext.getResources().getString(R.string.msg_share_profile_subject));
        shareIntent.setType(Constant.ZIP_MIME_TYPE);
        shareIntent.putExtra(Constant.IS_PROFILE, true);
        return shareIntent;
    }


    @Override
    public Intent getShareIntent(List<String> identifierList, String contentName, String screenName) {
        Intent shareIntent = createCommonShareIntent(identifierList, contentName, screenName);
        shareIntent.setType(Constant.ZIP_MIME_TYPE);
        return shareIntent;
    }

    private Intent createCommonShareIntent(List<String> identifierList, String contentName, String screenName) {
        Intent shareIntent = new Intent();
        shareIntent.setAction(Intent.ACTION_SEND);
        shareIntent.putStringArrayListExtra(Constant.SHARE_IDENTIFIER, (ArrayList<String>) identifierList);
        shareIntent.putExtra(Constant.SHARE_SCREEN_NAME, screenName);
        shareIntent.putExtra(Constant.SHARE_NAME, contentName);
        shareIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        return shareIntent;
    }

    /**
     * Populates intent for sharing Genie
     *
     * @param isLink whether the file is text or not.
     * @return Intent.
     */
    @Override
    public Intent getGenieShareIntent(boolean isLink) {
        Intent shareIntent = new Intent();
        shareIntent.setAction(Intent.ACTION_SEND);
        shareIntent.putExtra(Intent.EXTRA_SUBJECT, mContext.getResources().getString(R.string.msg_share_genie_subject));
        if (isLink) {
            shareIntent.setType(Constant.TXT_MIME_TYPE);
            shareIntent.putExtra(Constant.IS_LINK, true);
            String playStoreUtmLink = "https://play.google.com/store/apps/details?id=org.ekstep.genieservices&referrer=utm_source%3D"
                    + PreferenceUtil.getUniqueDeviceId() + "%26utm_campaign%3Dshare";
            shareIntent.putExtra(Intent.EXTRA_TEXT, mContext.getResources().getString(R.string.msg_share_genie) + "\n\n" + playStoreUtmLink);
        } else {
            shareIntent.putExtra(Intent.EXTRA_TEXT, mContext.getResources().getString(R.string.msg_share_genie) + "\n");
            shareIntent.setType(Constant.ZIP_MIME_TYPE);
        }
        return shareIntent;
    }

    /**
     * Populates intent for sharing Genie Configurations
     *
     * @return Intent.
     */
    @Override
    public Intent getGenieConfigurationsShareIntent(boolean isText) {
        Intent shareIntent = new Intent();
        shareIntent.setAction(Intent.ACTION_SEND);
        shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Details:" + PreferenceUtil.getUniqueDeviceId() + "_" + System.currentTimeMillis());

        String genieConfigurations = getGenieConfigurations();
        if (isText) {
            shareIntent.setType(Constant.TXT_MIME_TYPE);
            shareIntent.putExtra(Intent.EXTRA_TEXT, genieConfigurations);
            shareIntent.putExtra(Constant.IS_TEXT, true);
        } else {
            shareIntent.putExtra(Constant.SUPPORT_DATA, genieConfigurations);
            shareIntent.setType(Constant.ZIP_MIME_TYPE);
        }
        return shareIntent;
    }

    private String getGenieConfigurations() {
        StringBuilder configString = new StringBuilder();

        //add device id
        configString.append("did:");
        configString.append(PreferenceUtil.getUniqueDeviceId());
        configString.append("||");

        //add device model
        configString.append("mdl:");
        configString.append(DeviceSpec.getDeviceModel());
        configString.append("||");

        //add device make
        configString.append("mak:");
        configString.append(DeviceSpec.getDeviceMaker());
        configString.append("||");

        //add Android OS version
        configString.append("dos:");
        configString.append(DeviceSpec.getOSVersion());
        configString.append("||");

        //add Crosswalk version
        configString.append("cwv:");
        configString.append(getCrossWalkVersion());
        configString.append("||");

        //add Screen Resolution
        configString.append("res:");
        configString.append(DeviceSpec.getScreenWidth(mContext) + "x" + DeviceSpec.getScreenHeight(mContext));
        configString.append("||");

        //add Screen DPI
        configString.append("dpi:");
        configString.append(DeviceSpec.getDeviceDensityInDpi(mContext));
        configString.append("||");

        //add Total disk space
        configString.append("tsp:");
        configString.append(DeviceSpec.getTotalExternalMemorySize() + DeviceSpec.getTotalInternalMemorySize());
        configString.append("||");

        //add free space
        configString.append("fsp:");
        configString.append(DeviceSpec.getAvailableExternalMemorySize() + DeviceSpec.getAvailableInternalMemorySize());
        configString.append("||");

        //add Count of content on device
        configString.append("cno:");
        configString.append(getLocalContentsCount());
        configString.append("||");

        //add total users on device
        configString.append("uno:");
        configString.append(getUsersCount());
        configString.append("||");

        File genieSupportDirectory = FileHandler.getRequiredDirectory(Environment.getExternalStorageDirectory(), GENIE_SUPPORT_DIRECTORY);
        String filePath = genieSupportDirectory + "/" + GENIE_SUPPORT_FILE;
        String versionsAndOpenTimes = FileHandler.readFile(filePath);

        //add genie version history
        configString.append("gv:");
        configString.append(versionsAndOpenTimes);
        configString.append("||");

        //add current timestamp
        configString.append("ts:");
        configString.append(System.currentTimeMillis());

        //calculate checksum before adding pipes
        String checksum = encodeToBase64Uri(CryptoUtil.generateHMAC(configString.toString().trim(),
                PreferenceUtil.getUniqueDeviceId().getBytes(), JWTokenType.HS256.getAlgorithmName()));
        configString.append("||");

        //add HMAC
        configString.append("csm:");
        configString.append(checksum);

        return configString.toString();
    }

    private int getUsersCount() {
        GenieResponse<List<Profile>> response = CoreApplication.getGenieSdkInstance().getUserService().getAllUserProfile();
        List<Profile> mUsersList = response.getResult();
        return mUsersList == null ? 0 : mUsersList.size();
    }

    private int getLocalContentsCount() {
        ContentFilterCriteria.Builder contentFilterCriteria = new ContentFilterCriteria.Builder();
        contentFilterCriteria.contentTypes(new String[]{ContentType.GAME, ContentType.STORY,
                ContentType.WORKSHEET, ContentType.COLLECTION, ContentType.TEXTBOOK})
                .withContentAccess();
        ContentUtil.applyPartnerFilter(contentFilterCriteria);
        GenieResponse<List<Content>> genieResponse = CoreApplication.getGenieSdkInstance().getContentService().
                getAllLocalContent(contentFilterCriteria.build());
        List<Content> contentList = genieResponse.getResult();
        return contentList == null ? 0 : contentList.size();
    }

    private String getCrossWalkVersion() {
        PackageInfo pInfo = null;
        try {
            pInfo = mContext.getPackageManager().getPackageInfo("org.xwalk.core", 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return pInfo == null ? "na" : pInfo.versionName;
    }

    /**
     * Extracts List of ResolveInfo from the given intent
     *
     * @param shareIntent intent from which the ResolveInfo will be extracted.
     * @return List<DisplayResolveInfo>.
     */
    @Override
    public List<DisplayResolveInfo> getResolverListForShareIntent(Context context, Intent shareIntent) {
        List<ResolveInfo> resolveInfos = new ArrayList<ResolveInfo>();
        resolveInfos = getOsPackageManager().queryIntentActivities(shareIntent,
                PackageManager.COMPONENT_ENABLED_STATE_DEFAULT);

        List<DisplayResolveInfo> resolveInfoList = new ArrayList<>(resolveInfos.size());

        for (ResolveInfo resolveInfo : resolveInfos) {
            Intent intent = new Intent(shareIntent);
            intent.setClassName(resolveInfo.activityInfo.packageName, resolveInfo.activityInfo.name);
            resolveInfoList.add(new DisplayResolveInfo(resolveInfo, intent));
        }
        return resolveInfoList;
    }

    private PackageManager getOsPackageManager() {
        return mContext.getPackageManager();
    }

    @Override
    public void bindView(BaseView view, Context context) {
        mView = (ShareContract.View) view;
        mContext = context;
    }

    @Override
    public void unbindView() {
        mView = null;
        mContext = null;
    }
}
