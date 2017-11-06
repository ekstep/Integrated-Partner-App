package org.ekstep.genie.util.preference;

import android.content.Context;
import android.text.TextUtils;

import com.google.gson.reflect.TypeToken;

import org.ekstep.genie.model.CurrentGame;
import org.ekstep.genie.model.DownloadQueueItem;
import org.ekstep.genie.model.enums.SyncConfiguration;
import org.ekstep.genie.telemetry.TelemetryAction;
import org.ekstep.genie.telemetry.TelemetryBuilder;
import org.ekstep.genie.telemetry.TelemetryHandler;
import org.ekstep.genie.telemetry.TelemetryStageId;
import org.ekstep.genie.telemetry.enums.CoRelationIdContext;
import org.ekstep.genie.telemetry.enums.CoRelationType;
import org.ekstep.genie.util.Constant;
import org.ekstep.genieservices.commons.bean.CorrelationData;
import org.ekstep.genieservices.commons.bean.enums.InteractionType;
import org.ekstep.genieservices.commons.db.cache.PreferenceWrapper;
import org.ekstep.genieservices.commons.utils.GsonUtil;
import org.ekstep.genieservices.commons.utils.StringUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.ekstep.genie.util.FontConstants.LANG_ENGLISH;

/**
 * This class is used to get all the Genie service instance.
 * <p/>
 * Created on 9/1/2016.
 */
public class PreferenceUtil {

    private static final String SHARED_PREF_NAME = "EkStep";
    private static PreferenceWrapper sPreferenceWrapper;

    public static void initPreference(Context context) {
        if (sPreferenceWrapper == null) {
            sPreferenceWrapper = new PreferenceWrapper(context, SHARED_PREF_NAME);
        }
    }

    public static PreferenceWrapper getPreferenceWrapper() {
        if (sPreferenceWrapper == null) {
            throw new RuntimeException(
                    "Must run initPreference(Application application)" + " before an mInstance can be obtained");
        }
        return sPreferenceWrapper;
    }

    /**
     * Get Corelation Context
     *
     * @return
     */
    public static String getCoRelationIdContext() {
        return PreferenceUtil.getPreferenceWrapper().getString(PreferenceKey.METADATA_CORELATION_ID, null);
    }

    /**
     * Set Corelation Id Context
     *
     * @param coRelationIdContext
     */
    public static void setCoRelationIdContext(String coRelationIdContext) {
        if (!StringUtil.isNullOrEmpty(coRelationIdContext)) {
            PreferenceUtil.getPreferenceWrapper().putString(PreferenceKey.METADATA_CORELATION_ID, coRelationIdContext);
        } else {
            PreferenceUtil.getPreferenceWrapper().putString(PreferenceKey.METADATA_CORELATION_ID, null);
        }
    }

    public static String getCoRelationType() {
        return PreferenceUtil.getPreferenceWrapper().getString("type_" + getCoRelationIdContext(), null);
    }

    public static void setCoRelationType(String id) {
        PreferenceUtil.getPreferenceWrapper().putString("type_" + getCoRelationIdContext(), id);
    }

    /**
     * Clear local session data
     */
    public static void clear() {
        // Delete preferences data.
        PreferenceUtil.getPreferenceWrapper().clear();
    }

    /**
     * Removes the key
     */
    public static void remove(String key) {
        // Delete preferences data.
        PreferenceUtil.getPreferenceWrapper().remove(key);
    }

    /**
     * Get Corelation Context
     *
     * @return
     */
    public static String getCoRelationId() {
        return PreferenceUtil.getPreferenceWrapper().getString(getCoRelationIdContext(), null);
    }

    /**
     * Returns CoRelationId
     *
     * @return
     */
    public static List<CorrelationData> getCoRelationList() {
        List<CorrelationData> cdata = null;
        String coRelationContext = getCoRelationIdContext();
        String coRelationId = null;
        if (!StringUtil.isNullOrEmpty(coRelationContext)) {
            coRelationId = getCoRelationId();
            if (!StringUtil.isNullOrEmpty(coRelationId)) {
                String coRelationType = null;

                if (coRelationContext.equalsIgnoreCase(CoRelationIdContext.ONBOARDING)) {
                    coRelationType = CoRelationType.ONBRDNG;
                } else if (coRelationContext.equalsIgnoreCase(CoRelationIdContext.SEARCH)
                        || coRelationContext.equalsIgnoreCase(CoRelationIdContext.PAGE_ASSEMBLE)) {
                    coRelationType = CoRelationType.API + "-" + getCoRelationType();
                }

                CorrelationData corelationData = new CorrelationData(coRelationId, coRelationType);
                cdata = getCdata(corelationData);
            }
        }

        return cdata;
    }

    public static List<CorrelationData> getCdata(CorrelationData... correlationData) {
        List<CorrelationData> cdata = new ArrayList<>();

        for (CorrelationData data : correlationData) {
            cdata.add(data);
        }

        return cdata;
    }

    /**
     * Get whether is first time or not.
     *
     * @return Return "true" if first time else "false".
     */
    public static String isFirstTime() {
        return PreferenceUtil.getPreferenceWrapper().getString(PreferenceKey.IS_FIRST_TIME, "true");
    }

    /**
     * Set is first time value.
     *
     * @param isFirstTime Set "false" after first launch.
     */
    public static void setFirstTime(String isFirstTime) {
        PreferenceUtil.getPreferenceWrapper().putString(PreferenceKey.IS_FIRST_TIME, isFirstTime);
    }

    /**
     * Get onboarding state.
     *
     * @return Get the current state of onboarding.
     */
    public static int getOnBoardingState() {
        return PreferenceUtil.getPreferenceWrapper().getInt(PreferenceKey.ON_BOARDING_STATE, Constant.On_BOARD_STATE_DEFAULT);
    }

    /**
     * Set onboarding state value.
     *
     * @param onBoardingState Set the current state of onboarding.
     */
    public static void setOnBoardingState(int onBoardingState) {
        PreferenceUtil.getPreferenceWrapper().putInt(PreferenceKey.ON_BOARDING_STATE, onBoardingState);
    }

    /**
     * Get onboard notification state.
     *
     * @return
     */
    public static long getGenieFirstLaunchTime() {
        return PreferenceUtil.getPreferenceWrapper().getLong(PreferenceKey.GENIE_FIRST_LAUNCH_TIME, -1);
    }

    /**
     * Set onboard notification state value.
     *
     * @param genieFirstLaunchTime
     */
    public static void setGenieFirstLaunchTime(long genieFirstLaunchTime) {
        PreferenceUtil.getPreferenceWrapper().putLong(PreferenceKey.GENIE_FIRST_LAUNCH_TIME, genieFirstLaunchTime);
    }

    /**
     * Get onboard notification state.
     *
     * @return Get the onboard notification state.
     */
    public static int getOnBoardingNotificationState() {
        return PreferenceUtil.getPreferenceWrapper().getInt(PreferenceKey.ON_BOARD_NOTIFICATION_STATE, -1);
    }

    /**
     * Set onboard notification state value.
     *
     * @param onBoardingNotificationState Set the onboard notification state.
     */
    public static void setOnBoardingNotificationState(int onBoardingNotificationState) {
        PreferenceUtil.getPreferenceWrapper().putInt(PreferenceKey.ON_BOARD_NOTIFICATION_STATE, onBoardingNotificationState);
    }

    /**
     * Get fcm token.
     *
     * @return Get the current FCM token.
     */
    public static String getFcmToken() {
        return PreferenceUtil.getPreferenceWrapper().getString(PreferenceKey.FCM_TOKEN, null);
    }

    /**
     * Set fcm token value.
     *
     * @param token Set the current FCM token.
     */
    public static void setFcmToken(String token) {
        PreferenceUtil.getPreferenceWrapper().putString(PreferenceKey.FCM_TOKEN, token);
    }

    /**
     * Get whether Genie session ended or not.
     *
     * @return
     */
    public static String isGenieSessionEnded() {
        return PreferenceUtil.getPreferenceWrapper().getString(PreferenceKey.GENIE_SESSION_ENDED, "false");
    }

    /**
     * Set Genie session ended value.
     *
     * @param isEnded
     */
    public static void setGenieSessionEnded(String isEnded) {
        PreferenceUtil.getPreferenceWrapper().putString(PreferenceKey.GENIE_SESSION_ENDED, isEnded);
    }

    /**
     * Get the start time stamp.
     *
     * @return
     */
    public static String getGenieStartTimeStamp() {
        return PreferenceUtil.getPreferenceWrapper().getString(PreferenceKey.START_GENIE_TIME_STAMP, "");
    }

    /**
     * Set the start time stamp.
     *
     * @param timeStamp
     */
    public static void setGenieStartTimeStamp(String timeStamp) {
        PreferenceUtil.getPreferenceWrapper().putString(PreferenceKey.START_GENIE_TIME_STAMP, timeStamp);
    }

    /**
     * Get the unique device id.
     *
     * @return Get the unique device id.
     */
    public static String getUniqueDeviceId() {
        return PreferenceUtil.getPreferenceWrapper().getString(PreferenceKey.UNIQUE_DEVICE_UDID, "");
    }

    /**
     * Set the unique device id.
     *
     * @param udid Set the did.
     */
    public static void setUniqueDeviceId(String udid) {
        PreferenceUtil.getPreferenceWrapper().putString(PreferenceKey.UNIQUE_DEVICE_UDID, udid);
    }

    /**
     * Get the view from.
     *
     * @return
     */
    public static String getViewFrom() {
        return PreferenceUtil.getPreferenceWrapper().getString(PreferenceKey.VIEW_FROM, null);
    }

    /**
     * Set the view from.
     *
     * @param viewFrom
     */
    public static void setViewFrom(String viewFrom) {
        PreferenceUtil.getPreferenceWrapper().putString(PreferenceKey.VIEW_FROM, viewFrom);
    }

    /**
     * Get the language.
     *
     * @return
     */
    public static String getLanguage() {
        return PreferenceUtil.getPreferenceWrapper().getString(PreferenceKey.LANGUAGE, LANG_ENGLISH);
    }

    /**
     * Set the language.
     *
     * @param language
     */
    public static void setLanguage(String language) {
        TelemetryHandler.saveTelemetry(TelemetryBuilder.buildGEInteract(InteractionType.OTHER, TelemetryStageId.SETTINGS_LANGUAGE, TelemetryAction.LANGUAGE_SETTINGS_SUCCESS, language));
        PreferenceUtil.getPreferenceWrapper().putString(PreferenceKey.LANGUAGE, language);
    }

    /**
     * Get if the language selected.
     */
    public static boolean isLanguageSelected() {
        return PreferenceUtil.getPreferenceWrapper().getBoolean(PreferenceKey.IS_LANGUAGE_SELECTED, false);
    }

    /**
     * Set if the language selected.
     *
     * @param selected
     */
    public static void setLanguageSelected(boolean selected) {
        PreferenceUtil.getPreferenceWrapper().putBoolean(PreferenceKey.IS_LANGUAGE_SELECTED, selected);
    }

    /**
     * Get cuurent game.
     *
     * @return
     */
    public static List<CurrentGame> getCurrentGameList() {
        List<CurrentGame> currentGameList;

        String jsonContents = PreferenceUtil.getPreferenceWrapper().getString(PreferenceKey.CURRENT_GAME, null);
        if (!TextUtils.isEmpty(jsonContents)) {
            CurrentGame[] contentItems = GsonUtil.fromJson(jsonContents, CurrentGame[].class);
            currentGameList = Arrays.asList(contentItems);
            currentGameList = new ArrayList<CurrentGame>(currentGameList);
        } else {
            currentGameList = new ArrayList<CurrentGame>();
        }

        return currentGameList;
    }

    /**
     * Save current Game.
     *
     * @param currentGameList
     */
    public static void saveCurrentGame(List<CurrentGame> currentGameList) {
        String jsonContents = GsonUtil.toJson(currentGameList);
        PreferenceUtil.getPreferenceWrapper().putString(PreferenceKey.CURRENT_GAME, jsonContents);
    }

    /**
     * Get the cdata Status.
     *
     * @return
     */
    public static boolean getCdataStatus() {
        return PreferenceUtil.getPreferenceWrapper().getBoolean(PreferenceKey.DONT_SEND_CDATA, false);
    }

    /**
     * Set tthe cdata Status.
     *
     * @param status
     */
    public static void setCdataStatus(boolean status) {
        PreferenceUtil.getPreferenceWrapper().putBoolean(PreferenceKey.DONT_SEND_CDATA, status);
    }

    /**
     * Set the current playing game identifier.
     *
     * @param game
     */
    public static void setCurrentPlayingGameIdentifier(String game) {
        PreferenceUtil.getPreferenceWrapper().putString(PreferenceKey.CURRENT_PLAYING_GAME_IDENTIFIER, game);
    }

    /**
     * Get whether group or not.
     *
     * @return
     */
    public static String isGroup() {
        return PreferenceUtil.getPreferenceWrapper().getString(PreferenceKey.IS_GROUP, "");
    }

    /**
     * Set group value.
     *
     * @param isGroup
     */
    public static void setGroup(String isGroup) {
        PreferenceUtil.getPreferenceWrapper().putString(PreferenceKey.IS_GROUP, isGroup);
    }

    /**
     * Get whether show dummy fragment or not.
     *
     * @return
     */
    public static String isShowDummyFragment() {
        return PreferenceUtil.getPreferenceWrapper().getString(PreferenceKey.SHOW_DUMMY_FRAG, "");
    }

    /**
     * Set show dummy fragment value.
     *
     * @param showDummyFragment
     */
    public static void setShowDummyFragment(String showDummyFragment) {
        PreferenceUtil.getPreferenceWrapper().putString(PreferenceKey.SHOW_DUMMY_FRAG, showDummyFragment);
    }

    /**
     * Set local content refresh value.
     *
     * @param isRefreshLocalContent
     */
    public static void setFromManageUser(boolean isRefreshLocalContent) {
        PreferenceUtil.getPreferenceWrapper().putBoolean(PreferenceKey.FROM_MANAGE_USER, isRefreshLocalContent);
    }

    /**
     * Set response message id value.
     *
     * @param responseMessageId
     */
    public static void setSearchApiResponseMessageId(String responseMessageId) {
        PreferenceUtil.getPreferenceWrapper().putString(CoRelationIdContext.SEARCH, responseMessageId);
    }

    /**
     * Set response message id value.
     *
     * @param responseMessageId
     */
    public static void setPageAssembleApiResponseMessageId(String responseMessageId) {
        PreferenceUtil.getPreferenceWrapper().putString(CoRelationIdContext.PAGE_ASSEMBLE, responseMessageId);
    }

    /**
     * Set Onboarding corelationId.
     *
     * @param onboardingCorelationId
     */
    public static void setOnBoardingCorelationId(String onboardingCorelationId) {
        PreferenceUtil.getPreferenceWrapper().putString(CoRelationIdContext.ONBOARDING, onboardingCorelationId);
    }

    /**
     * @return supportedGenieVersions json string
     */
    public static String getSupportedGenieVersions() {
        return PreferenceUtil.getPreferenceWrapper().getString(PreferenceKey.SUPPORTED_GENIE_VERSIONS, "");
    }

    /**
     * Set supportedGenieVersions json string.
     *
     * @param supportedGenieVersions json string.
     */
    public static void setSupportedGenieVersions(String supportedGenieVersions) {
        PreferenceUtil.getPreferenceWrapper().putString(PreferenceKey.SUPPORTED_GENIE_VERSIONS, supportedGenieVersions);
    }

    /**
     * @return telemetrySyncInterval json string
     */
    public static String getTelemetrySyncInterval() {
        return PreferenceUtil.getPreferenceWrapper().getString(PreferenceKey.TELEMETRY_SYNC_INTERVAL, Constant.DEFAULT_SYNC_TYPE_AND_INTERVAL);
    }

    /**
     * Set telemetrySyncInterval json string.
     *
     * @param telemetrySyncInterval json string.
     */
    public static void setTelemetrySyncInterval(String telemetrySyncInterval) {
        PreferenceUtil.getPreferenceWrapper().putString(PreferenceKey.TELEMETRY_SYNC_INTERVAL, telemetrySyncInterval);
    }

    /**
     * @return profilesData json string
     */
    public static String getProfilesCopyStatus() {
        return PreferenceUtil.getPreferenceWrapper().getString(PreferenceKey.IS_PROFILES_COPIED, Constant.DEFAULT_PROFILE_COPIED_DATA);
    }

    /**
     * Set profile data json string.
     *
     * @param profilesData json string.
     */
    public static void setProfilesCopyStatus(String profilesData) {
        PreferenceUtil.getPreferenceWrapper().putString(PreferenceKey.IS_PROFILES_COPIED, profilesData);
    }

    /**
     * Gets the user chosen subject
     */
    public static String getChosenSubject() {
        return PreferenceUtil.getPreferenceWrapper().getString(PreferenceKey.CHOSEN_SUBJECT_NAME, null);
    }

    /**
     * Sets the user chosen subject
     *
     * @param subjectName subject name
     */
    public static void setChosenSubject(String subjectName) {
        PreferenceUtil.getPreferenceWrapper().putString(PreferenceKey.CHOSEN_SUBJECT_NAME, subjectName);
    }

    /**
     * Gets the user chosen subject
     */
    public static String getOfflineChosenSubject() {
        return PreferenceUtil.getPreferenceWrapper().getString(PreferenceKey.OFFLINE_CHOSEN_SUBJECT_NAME, null);
    }

    /**
     * Sets the user chosen subject
     *
     * @param subjectName subject name
     */
    public static void setOfflineChosenSubject(String subjectName) {
        PreferenceUtil.getPreferenceWrapper().putString(PreferenceKey.OFFLINE_CHOSEN_SUBJECT_NAME, subjectName);
    }

    /**
     * Gets sync configuration
     */
    public static String getSyncConfiguration() {
        return PreferenceUtil.getPreferenceWrapper().getString(PreferenceKey.KEY_SYNC_CONFIG, SyncConfiguration.OVER_ANY_MODE.toString());
    }

    /**
     * Sets sync configuration
     *
     * @param configuration configuration
     */
    public static void setSyncConfiguration(String configuration) {
        PreferenceUtil.getPreferenceWrapper().putString(PreferenceKey.KEY_SYNC_CONFIG, configuration);
    }

    /**
     * Gets whether anonymous session started manually or not
     */
    public static boolean isAnonymousSessionStartedManually() {
        return PreferenceUtil.getPreferenceWrapper().getBoolean(PreferenceKey.KEY_ANONYMOUS_SESSION_STARTED_MANUALLY, false);
    }

    /**
     * Sets anonymous status
     *
     * @param status status
     */
    public static void setAnonymousSessionStatus(boolean status) {
        PreferenceUtil.getPreferenceWrapper().putBoolean(PreferenceKey.KEY_ANONYMOUS_SESSION_STARTED_MANUALLY, status);
    }

    /**
     * Gets partner info
     */
    public static String getPartnerInfo() {
        return PreferenceUtil.getPreferenceWrapper().getString(PreferenceKey.KEY_PARTNER_INFO, null);
    }

    /**
     * Sets partner info
     *
     * @param partnerInfo partnerInfo
     */
    public static void setPartnerInfo(String partnerInfo) {
        PreferenceUtil.getPreferenceWrapper().putString(PreferenceKey.KEY_PARTNER_INFO, partnerInfo);
    }

    /**
     * Gets Default Storage Option
     */
    public static Map<String, String> getDefaultStorageOption() {
        String storage = PreferenceUtil.getPreferenceWrapper().getString(PreferenceKey.KEY_STORAGE_OPTION, null);
        return storage != null ? GsonUtil.fromJson(storage, Map.class) : null;
    }

    /**
     * Sets partner info
     *
     * @param storageOption storageOption
     */
    public static void setDefaultStorageOption(Map<String, String> storageOption) {
        PreferenceUtil.getPreferenceWrapper().putString(PreferenceKey.KEY_STORAGE_OPTION, GsonUtil.toJson(storageOption));
    }

    /**
     * set Download Queue
     *
     * @param downloadQueueItemList
     */
    public static void setDownloadQueueItemList(List<DownloadQueueItem> downloadQueueItemList) {
        String downloadQueueItemListJson = GsonUtil.toJson(downloadQueueItemList);
        PreferenceUtil.getPreferenceWrapper().putString(PreferenceKey.KEY_DOWNLOAD_QUEUE_ITEM_LIST, downloadQueueItemListJson);
    }

    /**
     * get Download Queue
     *
     * @return
     */
    public static List<DownloadQueueItem> getDownloadQueueItemList() {
        List<DownloadQueueItem> downloadQueueItemList = GsonUtil.fromJson(PreferenceUtil.getPreferenceWrapper().
                getString(PreferenceKey.KEY_DOWNLOAD_QUEUE_ITEM_LIST, null), new TypeToken<List<DownloadQueueItem>>() {
        }.getType());
        if (downloadQueueItemList == null) {
            downloadQueueItemList = new ArrayList<>();
        }
        return downloadQueueItemList;
    }

    /**
     * remove DownloadQueueItem from Prefs
     *
     * @param identifier
     */
    public static void removeDownloadQueueItem(String identifier) {
        if (StringUtil.isNullOrEmpty(identifier)) {
            return;
        }
        List<DownloadQueueItem> downloadQueueItemList = getDownloadQueueItemList();
        if (!downloadQueueItemList.isEmpty()) {
            for (int i = 0; i < downloadQueueItemList.size(); i++) {
                if (identifier.equals(downloadQueueItemList.get(i).getIdentifier())) {
                    downloadQueueItemList.remove(i);
                    setDownloadQueueItemList(downloadQueueItemList);
                    break;
                }
            }
        }
    }

}
