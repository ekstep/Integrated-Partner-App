package org.ekstep.genie.ui.splash;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;

import org.ekstep.genie.CoreApplication;
import org.ekstep.genie.R;
import org.ekstep.genie.activity.BaseActivity;
import org.ekstep.genie.activity.RuntimePermissionsActivity;
import org.ekstep.genie.base.BaseView;
import org.ekstep.genie.base.ProfileConfig;
import org.ekstep.genie.callback.IInitAndExecuteGenie;
import org.ekstep.genie.hooks.IStartUp;
import org.ekstep.genie.notification.NotificationManagerUtil;
import org.ekstep.genie.telemetry.EnvironmentId;
import org.ekstep.genie.telemetry.TelemetryAction;
import org.ekstep.genie.telemetry.TelemetryBuilder;
import org.ekstep.genie.telemetry.TelemetryConstant;
import org.ekstep.genie.telemetry.TelemetryHandler;
import org.ekstep.genie.telemetry.TelemetryStageId;
import org.ekstep.genie.telemetry.enums.CoRelationIdContext;
import org.ekstep.genie.telemetry.enums.ObjectType;
import org.ekstep.genie.ui.landing.LandingActivity;
import org.ekstep.genie.util.Constant;
import org.ekstep.genie.util.DeepLinkNavigation;
import org.ekstep.genie.util.DeepLinkUtility;
import org.ekstep.genie.util.FileHandler;
import org.ekstep.genie.util.FontConstants;
import org.ekstep.genie.util.FontUtil;
import org.ekstep.genie.util.LaunchUtil;
import org.ekstep.genie.util.LogUtil;
import org.ekstep.genie.util.ResUtil;
import org.ekstep.genie.util.TagValidator;
import org.ekstep.genie.util.Util;
import org.ekstep.genie.util.geniesdk.ImportExportUtil;
import org.ekstep.genie.util.preference.PreferenceKey;
import org.ekstep.genie.util.preference.PreferenceUtil;
import org.ekstep.genieservices.commons.IDeviceInfo;
import org.ekstep.genieservices.commons.IResponseHandler;
import org.ekstep.genieservices.commons.bean.GenieResponse;
import org.ekstep.genieservices.commons.bean.Notification;
import org.ekstep.genieservices.commons.bean.Tag;
import org.ekstep.genieservices.commons.bean.enums.InteractionType;
import org.ekstep.genieservices.commons.utils.GsonUtil;
import org.ekstep.genieservices.commons.utils.StringUtil;
import org.ekstep.genieservices.utils.BuildConfigUtil;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by anil on 12/21/2016.
 *
 * @author anil
 */
public class SplashPresenter implements SplashContract.Presenter {
    private static final String TAG = "SplashPresenter";
    private static final String PROFILES_UPDATED_VERSION = "profiles_updated_version";
    private static final String ARE_PROFILE_IMAGES_COPIED = "are_profile_images_copied";
    private static final String GENIE_SUPPORT_FILE = "genie_support.txt";
    private static final String GENIE_SUPPORT_DIRECTORY = "GenieSupport";
    private static final String SEPERATOR = "~";
    private static final int SPLASH_TIME_OUT = 1000;
    private Intent mIntent = null;

    @NonNull
    private Activity mActivity;

    @NonNull
    private SplashContract.View mSplashView;

    private IStartUp mIStartUp;

    private IInitAndExecuteGenie mIInitAndExecuteGenie = new IInitAndExecuteGenie() {
        @Override
        public void initAndExecuteGenie() {
            initAndExecute();
        }
    };

    private DeepLinkNavigation.IValidateDeepLink mIValidateDeepLink = new DeepLinkNavigation.IValidateDeepLink() {

        @Override
        public void validLocalDeepLink() {
        }

        @Override
        public void validServerDeepLink() {
            mSplashView.showSplash();
        }

        @Override
        public void notAValidDeepLink() {
            // Set splash screen
            mSplashView.showSplash();

            boolean shouldInitAndExecute = true;
            if (!PreferenceUtil.isFirstTime().equalsIgnoreCase("true") && (mActivity.getIntent().getFlags() &
                    Intent.FLAG_ACTIVITY_LAUNCHED_FROM_HISTORY) == 0) {  // If Genie is not launched from history/activity stack.
                // Initiate import file
                shouldInitAndExecute = !ImportExportUtil.initiateImportFile(mActivity,
                        mIInitAndExecuteGenie, mActivity.getIntent(), true);
            }

            if (shouldInitAndExecute) {
                initAndExecute();
            }
        }

        @Override
        public void onTagDeeplinkFound(String tagName, String description, String startDate, String endDate) {
            boolean shouldInitAndExecute = true;

            if (StringUtil.isNullOrEmpty(tagName)) {
                Util.showCustomToast(R.string.error_tag_cant_be_empty);
            } else if (!Util.isAlphaNumeric(tagName)) {
                Util.showCustomToast(R.string.error_tag_should_be_alpha_numeric);
            } else if (TagValidator.checkStartEndDateSplash(startDate, endDate)) {
                Util.showCustomToast(R.string.error_tag_inavlid);
            } else {

                Tag tag;
                if (!startDate.contains("-") && !endDate.contains("-")) {
                    String sDate = TagValidator.changeDateFormat(startDate);
                    String eDate = TagValidator.changeDateFormat(endDate);
                    tag = new Tag(tagName, description,
                            StringUtil.isNullOrEmpty(sDate) ? null : sDate,
                            StringUtil.isNullOrEmpty(eDate) ? null : eDate);
                } else {
                    tag = new Tag(tagName, description,
                            StringUtil.isNullOrEmpty(startDate) ? null : startDate,
                            StringUtil.isNullOrEmpty(endDate) ? null : endDate);
                }

                shouldInitAndExecute = false;

                CoreApplication.getGenieAsyncService().getTagService().setTag(tag, new IResponseHandler<Void>() {
                    @Override
                    public void onSuccess(GenieResponse<Void> genieResponse) {
//                        TelemetryHandler.saveTelemetry(TelemetryBuilder.buildGEInteract(InteractionType.TOUCH, TelemetryStageId.SPLASH, TelemetryAction.ADD_TAG_DEEP_LINK));
                        TelemetryHandler.saveTelemetry(TelemetryBuilder.buildInteractEvent(EnvironmentId.HOME, InteractionType.TOUCH, TelemetryAction.ADD_TAG_DEEP_LINK, TelemetryStageId.SPLASH));
                        Util.showCustomToast(R.string.msg_tag_added_sussessfully);
                        mIInitAndExecuteGenie.initAndExecuteGenie();
                    }

                    @Override
                    public void onError(GenieResponse<Void> genieResponse) {
                        mIInitAndExecuteGenie.initAndExecuteGenie();
                    }
                });
            }

            if (shouldInitAndExecute) {
                initAndExecute();
            }
        }
    };

    public SplashPresenter() {
        mIStartUp = BuildConfigUtil.getBuildConfigValue(CoreApplication.getInstance().getClientPackageName(), "STARTUP");
    }

    @Override
    public void saveGenieStartEvent() {
        if (CoreApplication.getInstance().isGenieAlive() == -1) {
            CoreApplication.getInstance().setGenieAlive(1);
            PreferenceUtil.setGenieStartTimeStamp("" + System.currentTimeMillis());
            TelemetryHandler.saveTelemetry(TelemetryBuilder.buildStartEvent(mActivity, TelemetryStageId.SPLASH, TelemetryConstant.APP, null, null, null, null));
            PreferenceUtil.setCdataStatus(false);
            this.initCoRelationData();

            if (!StringUtil.isNullOrEmpty(PreferenceUtil.getPartnerInfo())) {
                PreferenceUtil.remove(PreferenceKey.KEY_PARTNER_INFO);
                PreferenceUtil.setLanguage(FontConstants.LANG_ENGLISH);
                PreferenceUtil.setLanguageSelected(true);
                FontUtil.getInstance().changeLocale();
            }

            if (PreferenceUtil.isFirstTime().equalsIgnoreCase("false")) {
                resetSubjectAndGenerateTE();
            }

            callGenieSupport();
        }
    }

    private void resetSubjectAndGenerateTE() {
        //when the app re-launches every time, it should have the Choose Subject set to All Subjects
        PreferenceUtil.setChosenSubject(mActivity.getString(R.string.label_home_all_subjects));
        PreferenceUtil.setOfflineChosenSubject(mActivity.getString(R.string.label_home_all_subjects));

        // buildGEInteract
//        TelemetryHandler.saveTelemetry(TelemetryBuilder.buildGEInteract(InteractionType.OTHER, TelemetryStageId.GENIE_HOME,
//                TelemetryAction.SUBJECT_RESET, ""));
        TelemetryHandler.saveTelemetry(TelemetryBuilder.buildInteractEvent(EnvironmentId.HOME, InteractionType.OTHER, TelemetryAction.SUBJECT_RESET, TelemetryStageId.GENIE_HOME, "", ObjectType.CONTENT));
    }

    private void callGenieSupport() {
        try {
            makeEntryInGenieSupportFile();
            removeDetailsFiles();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initCoRelationData() {
        PreferenceUtil.setCoRelationIdContext(CoRelationIdContext.NONE);
        PreferenceUtil.setPageAssembleApiResponseMessageId(null);
        PreferenceUtil.setSearchApiResponseMessageId(null);
    }

    @Override
    public void openSplash(Intent intent) {
        if (mIStartUp != null) {
            mIStartUp.init(mActivity);
        }

        mIntent = intent;

        mSplashView.showSplash();

        checkForLocalFilePath(intent);

        if (Build.VERSION.SDK_INT >= 23) {
            requestRuntimePermissions(RuntimePermissionsActivity.REQUEST_PERMISSION);
        } else {
            handleGenieLaunch(intent);
        }
    }

    /**
     * This method checks the proper scenario for which screen has to be shown, based on the condition accordingly
     *
     * @param intent
     */
    private void handleGenieLaunch(Intent intent) {
        if (intent.getExtras() != null && isFromNotification(intent.getExtras())) {
            handleNotification(intent);
        } else if (intent.getExtras() != null && isFromPartner(intent.getExtras())) {
            handlePartnerLaunch(intent);
        } else {
            startGenie();
        }
    }

    /**
     * Handle the launch of Genie by partner, if the Genie is being launched for first time,
     * then all the conditions are checked in checkIfLanguageSelectionIsSkipped method and Genie is launched
     * accordingly for the satisfied conditions else Genie will be taken to Landing Activity.
     *
     * @param intent
     */
    private void handlePartnerLaunch(Intent intent) {
        if (PreferenceUtil.isFirstTime().equalsIgnoreCase("false") || PreferenceUtil.isLanguageSelected()) {
            LaunchUtil.handlePartnerInfo(intent.getExtras(), false);
            startLandingActivity();
        } else {
            checkIfLanguageSelectionIsSkipped(intent);
        }
    }

    /**
     * Check if the partner has mentioned to skip Language Selection
     *
     * @param intent
     */
    private void checkIfLanguageSelectionIsSkipped(Intent intent) {
        //Set all partner related data
        LaunchUtil.handlePartnerInfo(intent.getExtras(), true);

        if (intent != null && intent.getExtras() != null
                && intent.getExtras().containsKey(Constant.BUNDLE_KEY_ENABLE_LANGUAGE_SELECTION)
                && !intent.getExtras().getBoolean(Constant.BUNDLE_KEY_ENABLE_LANGUAGE_SELECTION)) {

            //This conditions is true when only the Language Selection is skipped, but the profile creation and subject choosing
            //on-boarding are still enabled
            if (PreferenceUtil.getOnBoardingState() == Constant.On_BOARD_STATE_DEFAULT) {
                PreferenceUtil.setOnBoardingState(PreferenceUtil.getOnBoardingState() + 1);
            }

            //directly launch home screen
            mSplashView.showHomeScreen();
        } else {
            intent.putExtra(Constant.EXTRA_LAUNCH_IS_BY_PARTNER, true);
            mSplashView.postPartnerLaunchIntent(intent);
            initAndExecute();
        }
    }

    /**
     * Check if the intent contains the path for the local ECAR, EPAR or GSA files
     *
     * @param intent
     */
    private void checkForLocalFilePath(Intent intent) {
        if (intent != null && intent.getData() != null && intent.getData().getPath() != null
                && (Util.isEcar(intent.getData().getPath()) || Util.isEpar(intent.getData().getPath()) || Util.isGsa(intent.getData().getPath()))) {
            intent.putExtra(Constant.EXTRA_LAUNCH_IS_FILE_PATH, true);
            mSplashView.postFilePathIntent(intent);
        }
    }

    private void startLandingActivity() {
        Intent intent = new Intent(mActivity, LandingActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        mActivity.startActivity(intent);
        mActivity.finish();
    }


    private boolean isFromNotification(Bundle extras) {
        return extras.containsKey(Constant.BUNDLE_KEY_NOTIFICATION_DATA_MODEL);
    }

    private boolean isFromPartner(Bundle extras) {
        return extras.containsKey(Constant.BUNDLE_KEY_PARTNER_CHANNEL_ARRAY)
                || extras.containsKey(Constant.BUNDLE_KEY_PARTNER_TAG)
                || extras.containsKey(Constant.BUNDLE_KEY_PARTNER_AUDIENCE_ARRAY)
                || extras.containsKey(Constant.BUNDLE_KEY_PARTNER_PRAGMA_ARRAY);
    }

    private void handleNotification(Intent intent) {
        if (mIStartUp != null) {
            mIStartUp.handleNotification(intent);
        }
    }

    private void requestRuntimePermissions(int requestCode) {
        ((BaseActivity) mActivity).requestAppPermissions(new
                        String[]{Manifest.permission.CAMERA,
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.RECORD_AUDIO}, "Genie needs to access your data"
                , requestCode);
    }

    private void startGenie() {
        if (PreferenceUtil.isFirstTime().equalsIgnoreCase("false")) {
            DeepLinkNavigation deepLinkNavigation = new DeepLinkNavigation(mActivity);
            deepLinkNavigation.validateAndHandleDeepLink(mActivity.getIntent(), mIValidateDeepLink, mIStartUp);
        } else {
            checkForDeepLink();

            initAndExecute();
        }
    }

    /**
     * If the intent contains any of the schemes like ekstep, settag, http and https, then this event will be posted
     */
    private void checkForDeepLink() {
        if (mActivity.getIntent() != null && mActivity.getIntent().getData() != null && (DeepLinkUtility.isDeepLink(mActivity.getIntent().getData()) || DeepLinkUtility.isDeepLinkSetTag(mActivity.getIntent().getData())
                || DeepLinkUtility.isDeepLinkHttp(mActivity.getIntent().getData()) || DeepLinkUtility.isDeepLinkHttps(mActivity.getIntent().getData()))) {
            Intent intent = mActivity.getIntent();
            intent.putExtra(Constant.EXTRA_LAUNCH_IS_DEEPLINK, true);

            mSplashView.postDeepLinkIntent(mActivity.getIntent());
        }
    }

    @Override
    public void onPermissionsGranted(int requestCode) {
        if (requestCode == RuntimePermissionsActivity.REQUEST_PERMISSION) {
            File genieSupportDirectory = FileHandler.getRequiredDirectory(Environment.getExternalStorageDirectory(), GENIE_SUPPORT_DIRECTORY);
            String filePath = genieSupportDirectory + "/" + GENIE_SUPPORT_FILE;

            //for the first time when file does not exists
            if (!FileHandler.checkIfFileExists(filePath)) {
                callGenieSupport();
            }

            handleGenieLaunch(mIntent);
        }
    }

    @Override
    public void onPermissionsDenied(List<String> deniedPermissions) {
        handleGenieLaunch(mIntent);
    }

    @Override
    public void copyProfilesToExternalDirectory() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                //check in the Session if the profile images were already copied
                String profileDataFromSession = PreferenceUtil.getProfilesCopyStatus();

                if (profileDataFromSession == null) {
                    ResUtil.readAndSaveDrawables(mActivity, R.drawable.class, new ProfileConfig().getProfilePath(mActivity));

                    setProfileCopyStatus();
                } else {
                    Map<String, String> profileMap = GsonUtil.fromJson(profileDataFromSession, Map.class);

                    if (profileMap != null) {
                        String version = profileMap.get(PROFILES_UPDATED_VERSION);
                        String isUpdated = profileMap.get(ARE_PROFILE_IMAGES_COPIED);

                        String packageName = CoreApplication.getInstance().getClientPackageName();

                        if (!version.equalsIgnoreCase(BuildConfigUtil.getBuildConfigValue(packageName, Constant.BuildConfigKey.VERSION_CODE).toString()) || isUpdated.equalsIgnoreCase("false")) {
                            ResUtil.readAndSaveDrawables(mActivity, R.drawable.class, new ProfileConfig().getProfilePath(mActivity));

                            setProfileCopyStatus();
                        }
                    }
                }
            }
        }).start();
    }

    @Override
    public void makeEntryInGenieSupportFile() throws IOException {
        File genieSupportDirectory = FileHandler.getRequiredDirectory(Environment.getExternalStorageDirectory(), GENIE_SUPPORT_DIRECTORY);
        String filePath = genieSupportDirectory + "/" + GENIE_SUPPORT_FILE;
        String packageName = CoreApplication.getInstance().getClientPackageName();
        String versionName = BuildConfigUtil.getBuildConfigValue(packageName, Constant.BuildConfigKey.VERSION_NAME).toString();

        //for the first time when file does not exists
        if (!FileHandler.checkIfFileExists(filePath)) {
            makeFirstEntryInTheFile(versionName, filePath);
        } else {
            String lastLineOfFile = FileHandler.readLastLineFromFile(filePath);
            if (!StringUtil.isNullOrEmpty(lastLineOfFile)) {
                String[] partsOfLastLine = lastLineOfFile.split(SEPERATOR);

                if (versionName.equalsIgnoreCase(partsOfLastLine[0])) {
                    //just remove the last line from the file and update it their
                    FileHandler.removeLastLineFromFile(filePath);

                    String previousOpenCount = partsOfLastLine[2];
                    int count = Integer.parseInt(previousOpenCount);
                    count++;
                    String updateEntry = versionName + SEPERATOR + partsOfLastLine[1] + SEPERATOR + count;
                    FileHandler.saveToFile(filePath, updateEntry);
                } else {
                    //make a new entry to the file
                    String newEntry = versionName + SEPERATOR + System.currentTimeMillis() + SEPERATOR + "1";
                    FileHandler.saveToFile(filePath, newEntry);
                }
            } else {
                //make a new entry to the file
                String newEntry = versionName + SEPERATOR + System.currentTimeMillis() + SEPERATOR + "1";
                FileHandler.saveToFile(filePath, newEntry);
            }
        }
    }

    public void removeDetailsFiles() {
        File genieSupportDirectory = FileHandler.getRequiredDirectory(Environment.getExternalStorageDirectory(), GENIE_SUPPORT_DIRECTORY);
        if (genieSupportDirectory != null && genieSupportDirectory.exists()) {
            if (genieSupportDirectory.listFiles() != null && genieSupportDirectory.listFiles().length > 0) {
                for (File file : genieSupportDirectory.listFiles()) {
                    if (file.getName().startsWith("Details")) {
                        file.delete();
                    }
                }
            }
        }
    }

    private void makeFirstEntryInTheFile(String versionName, String filePath) throws IOException {
        FileHandler.createFileInTheDirectory(filePath);
        String firstEntry = versionName + SEPERATOR + System.currentTimeMillis() + SEPERATOR + "1";
        FileHandler.saveToFile(filePath, firstEntry);
    }

    private void setProfileCopyStatus() {
        String packageName = CoreApplication.getInstance().getClientPackageName();

        Map<String, String> newProfileData = new HashMap<>();
        newProfileData.put(PROFILES_UPDATED_VERSION, BuildConfigUtil.getBuildConfigValue(packageName, Constant.BuildConfigKey.VERSION_CODE).toString());
        newProfileData.put(ARE_PROFILE_IMAGES_COPIED, "true");

        PreferenceUtil.setProfilesCopyStatus(GsonUtil.toJson(newProfileData));
        LogUtil.i(TAG + "@setProfileCopyStatus", "Profile images copied!");
    }

    /**
     * Initiate and execute the Genie service.
     */
    private void initAndExecute() {
        // Initialize Genie
        IDeviceInfo deviceInfo = CoreApplication.getGenieSdkInstance().getDeviceInfo();
        PreferenceUtil.setUniqueDeviceId(deviceInfo.getDeviceID());

        if (mSplashView == null) {
            return;
        }

        FontUtil.getInstance().changeLocale();

        if (mIStartUp != null) {
            mIStartUp.nextScreen(mActivity);
        }

    }

    @Override
    public void bindView(BaseView view, Context context) {
        mSplashView = (SplashContract.View) view;
        mActivity = (Activity) context;
    }

    @Override
    public void unbindView() {
        mSplashView = null;
        mActivity = null;
    }


    private void registerOnboardNotification() {
        NotificationManagerUtil notificationManagerUtil = new NotificationManagerUtil(mActivity);

        for (Notification genieNotification : notificationManagerUtil.getOnboardNotificationList()) {
            notificationManagerUtil.handleNotification(genieNotification);
        }
    }
}
