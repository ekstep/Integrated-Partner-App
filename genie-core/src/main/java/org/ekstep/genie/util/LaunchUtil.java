package org.ekstep.genie.util;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;

import org.ekstep.genie.CoreApplication;
import org.ekstep.genie.notification.enums.NotificationActionId;
import org.ekstep.genie.telemetry.EnvironmentId;
import org.ekstep.genie.telemetry.TelemetryAction;
import org.ekstep.genie.telemetry.TelemetryBuilder;
import org.ekstep.genie.telemetry.TelemetryConstant;
import org.ekstep.genie.telemetry.TelemetryHandler;
import org.ekstep.genie.telemetry.TelemetryStageId;
import org.ekstep.genie.ui.contentdetail.ContentDetailActivity;
import org.ekstep.genie.ui.landing.LandingActivity;
import org.ekstep.genie.ui.search.SearchActivity;
import org.ekstep.genie.util.preference.PreferenceUtil;
import org.ekstep.genieservices.async.TagService;
import org.ekstep.genieservices.async.UserService;
import org.ekstep.genieservices.commons.IResponseHandler;
import org.ekstep.genieservices.commons.bean.ActionData;
import org.ekstep.genieservices.commons.bean.GenieResponse;
import org.ekstep.genieservices.commons.bean.Notification;
import org.ekstep.genieservices.commons.bean.Profile;
import org.ekstep.genieservices.commons.bean.Tag;
import org.ekstep.genieservices.commons.bean.enums.InteractionType;
import org.ekstep.genieservices.commons.utils.GsonUtil;
import org.ekstep.genieservices.commons.utils.StringUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created on 19/9/17.
 * shriharsh
 */

public class LaunchUtil {

    public static void handlePartnerInfo(final Bundle extras, boolean isFirstTime) {
        if (extras != null) {
            String[] channelArray = getStringArrayFromBundle(extras, Constant.BUNDLE_KEY_PARTNER_CHANNEL_ARRAY);
            String[] audienceArray = getStringArrayFromBundle(extras, Constant.BUNDLE_KEY_PARTNER_AUDIENCE_ARRAY);
            String[] pragmaArray = getStringArrayFromBundle(extras, Constant.BUNDLE_KEY_PARTNER_PRAGMA_ARRAY);
            String language = getStringFromBundle(extras, Constant.BUNDLE_KEY_PARTNER_LANGUAGE);
            String mode = getStringFromBundle(extras, Constant.BUNDLE_KEY_HOME_MODE);

            //Check if Child Switching is enabled
            Boolean enableChildSwitcher = Boolean.FALSE;
            if (extras.containsKey(Constant.BUNDLE_KEY_ENABLE_CHILD_SWITCHER)) {
                enableChildSwitcher = extras.getBoolean(Constant.BUNDLE_KEY_ENABLE_CHILD_SWITCHER);
            }

            //Check if Child Profile Creation On Boarding step is enabled
            Boolean enableProfileCreationOnBoarding = Boolean.TRUE;
            if (extras.containsKey(Constant.BUNDLE_KEY_ENABLE_ONBOARDING_PROFILE_CREATION)) {
                enableProfileCreationOnBoarding = extras.getBoolean(Constant.BUNDLE_KEY_ENABLE_ONBOARDING_PROFILE_CREATION);
            }

            //Check if Subject Choosing On Boarding step is enabled
            Boolean enableSubjectChooseOnBoarding = Boolean.TRUE;
            if (extras.containsKey(Constant.BUNDLE_KEY_ENABLE_ONBOARDING_CHOOSE_SUBJECT)) {
                enableSubjectChooseOnBoarding = extras.getBoolean(Constant.BUNDLE_KEY_ENABLE_ONBOARDING_CHOOSE_SUBJECT);
            }

            //Check if Language choosing step is enabled
            Boolean enableLanguageChoose = Boolean.TRUE;
            if (extras.containsKey(Constant.BUNDLE_KEY_ENABLE_LANGUAGE_SELECTION)) {
                enableLanguageChoose = extras.getBoolean(Constant.BUNDLE_KEY_ENABLE_LANGUAGE_SELECTION);
            }

            //If language setting is not enabled and language is set empty then by default we set it to English
            if (!enableLanguageChoose && TextUtils.isEmpty(language)) {
                language = "en";
            }

            //Sets program tag
            setProgramTag(extras);

            //Saves partner information
            Map hashMap = new HashMap();
            hashMap.put(Constant.BUNDLE_KEY_PARTNER_CHANNEL_ARRAY, channelArray);
            hashMap.put(Constant.BUNDLE_KEY_PARTNER_AUDIENCE_ARRAY, audienceArray);
            hashMap.put(Constant.BUNDLE_KEY_PARTNER_PRAGMA_ARRAY, pragmaArray);
            hashMap.put(Constant.BUNDLE_KEY_PARTNER_LANGUAGE, language);
            hashMap.put(Constant.BUNDLE_KEY_HOME_MODE, mode);
            hashMap.put(Constant.BUNDLE_KEY_ENABLE_CHILD_SWITCHER, enableChildSwitcher);
            hashMap.put(Constant.BUNDLE_KEY_ENABLE_ONBOARDING_PROFILE_CREATION, enableProfileCreationOnBoarding);
            hashMap.put(Constant.BUNDLE_KEY_ENABLE_ONBOARDING_CHOOSE_SUBJECT, enableSubjectChooseOnBoarding);
            hashMap.put(Constant.BUNDLE_KEY_ENABLE_LANGUAGE_SELECTION, enableLanguageChoose);
            PreferenceUtil.setPartnerInfo(GsonUtil.toJson(hashMap));

            //Changes the locale
            if (!StringUtil.isNullOrEmpty(language)) {
                PreferenceUtil.setLanguageSelected(true);
                PreferenceUtil.setLanguage(language);
                FontUtil.getInstance().changeLocale();
            }

            if (isFirstTime) {
                //We need to check if the partner has already created a profile, if the partner has already created a profile
                //then we need to skip the profile creation on-boarding step, this can be done using getAllProfiles
                UserService userService = CoreApplication.getGenieAsyncService().getUserService();

                userService.getAllUserProfile(new IResponseHandler<List<Profile>>() {
                    @Override
                    public void onSuccess(GenieResponse<List<Profile>> genieResponse) {
                        final List<Profile> profileList = genieResponse.getResult();

                        //Check if Child Creation on-boarding is enabled
                        if ((profileList != null && profileList.size() > 0)) {
                            incrementOnBoardingAndCheckForChangeSubjectOnBoarding(extras);
                        }
                    }

                    @Override
                    public void onError(GenieResponse<List<Profile>> genieResponse) {

                    }
                });
            } else {
                if ((extras.containsKey(Constant.BUNDLE_KEY_ENABLE_ONBOARDING_PROFILE_CREATION) && !extras.getBoolean(Constant.BUNDLE_KEY_ENABLE_ONBOARDING_PROFILE_CREATION))) {
                    incrementOnBoardingAndCheckForChangeSubjectOnBoarding(extras);
                }
            }

        }
    }

    private static void incrementOnBoardingAndCheckForChangeSubjectOnBoarding(Bundle extras) {
        PreferenceUtil.setOnBoardingState(Constant.On_BOARD_STATE_ADD_CHILD + 1);

        //Check if Subject Choosing on-boarding is enabled
        if (extras.containsKey(Constant.BUNDLE_KEY_ENABLE_ONBOARDING_CHOOSE_SUBJECT) && !extras.getBoolean(Constant.BUNDLE_KEY_ENABLE_ONBOARDING_CHOOSE_SUBJECT)) {
            PreferenceUtil.setOnBoardingState(Constant.On_BOARD_STATE_CHANGE_SUBJECT + 1);
        }
    }

    private static String[] getStringArrayFromBundle(Bundle extras, String bundleKey) {
        String[] bundleValue = null;
        if (extras.containsKey(bundleKey)) {
            bundleValue = extras.getStringArray(bundleKey);
        }
        return bundleValue;
    }

    private static String getStringFromBundle(Bundle extras, String bundleKey) {
        String bundleValue = null;
        if (extras.containsKey(bundleKey)) {
            bundleValue = extras.getString(bundleKey);
        }
        return bundleValue;
    }

    /**
     * sets program tag
     *
     * @param extras
     */
    private static void setProgramTag(Bundle extras) {
        HashMap<String, String> programTag = (HashMap<String, String>) extras.getSerializable(Constant.BUNDLE_KEY_PARTNER_TAG);
        if (programTag != null) {
            TagService tagService = CoreApplication.getGenieAsyncService().getTagService();
            Tag tag = new Tag(programTag.get("name"), programTag.get("desc"), programTag.get("startDate"), programTag.get("endDate"));
            tagService.setTag(tag, new IResponseHandler<Void>() {
                @Override
                public void onSuccess(GenieResponse<Void> genieResponse) {

                }

                @Override
                public void onError(GenieResponse<Void> genieResponse) {

                }
            });
        }
    }

    public static Intent handleNotification(Activity activity, Bundle extras) {
        Notification notification = (Notification) extras.getSerializable(Constant.BUNDLE_KEY_NOTIFICATION_DATA_MODEL);
        if (notification == null) {
            return null;
        }
        //update notification status as read.
        int msgId = notification.getMsgid();
        Notification newNotification = new Notification();
        newNotification.setMsgid(msgId);
        CoreApplication.getGenieAsyncService().getNotificationService().updateNotification(newNotification, new IResponseHandler<Notification>() {
            @Override
            public void onSuccess(GenieResponse<Notification> genieResponse) {

            }

            @Override
            public void onError(GenieResponse<Notification> genieResponse) {

            }
        });
        Map<String, Object> map = new HashMap<>();
        if (notification != null) {
            map.put(TelemetryConstant.NOTIFICATION_DATA, GsonUtil.toJson(notification));
        }
        if (extras.containsKey(Constant.BUNDLE_FROM_NOTIFICATION_LIST)) {

//            TelemetryHandler.saveTelemetry(TelemetryBuilder.buildGEInteract(InteractionType.TOUCH, TelemetryStageId.NOTIFICATION_LIST, TelemetryAction.NOTIFICATION_CLICKED, String.valueOf(msgId), map));
            TelemetryHandler.saveTelemetry(TelemetryBuilder.buildInteractEvent(EnvironmentId.HOME, InteractionType.TOUCH, TelemetryAction.NOTIFICATION_CLICKED, TelemetryStageId.NOTIFICATION_LIST, String.valueOf(msgId), null, map));
        } else {
            if (notification.getRelativetime() > 0) {

//                TelemetryHandler.saveTelemetry(TelemetryBuilder.buildGEInteract(InteractionType.TOUCH, TelemetryStageId.LOCAL_NOTIFICATION, TelemetryAction.NOTIFICATION_CLICKED, String.valueOf(msgId), map));
                TelemetryHandler.saveTelemetry(TelemetryBuilder.buildInteractEvent(EnvironmentId.HOME, InteractionType.TOUCH, TelemetryAction.NOTIFICATION_CLICKED, TelemetryStageId.LOCAL_NOTIFICATION, String.valueOf(msgId), null, map));
            } else {

//                TelemetryHandler.saveTelemetry(TelemetryBuilder.buildGEInteract(InteractionType.TOUCH, TelemetryStageId.SERVER_NOTIFICATION, TelemetryAction.NOTIFICATION_CLICKED, String.valueOf(msgId)));
                TelemetryHandler.saveTelemetry(TelemetryBuilder.buildInteractEvent(EnvironmentId.HOME, InteractionType.TOUCH, TelemetryAction.NOTIFICATION_CLICKED, TelemetryStageId.SERVER_NOTIFICATION, String.valueOf(msgId), null));
            }
        }

        Intent intent = new Intent(activity, LandingActivity.class);

        switch (notification.getActionid()) {
            case NotificationActionId.GENIE_HOME:
                // Do nothing.
                break;

            case NotificationActionId.EXPLORE_CONTENT_SCREEN:
                // Do nothing. This screen is not exists after feb 2017 release
                break;

            case NotificationActionId.LESSON_DETAIL_SCREEN:
                if (notification.getActiondata() != null && !TextUtils.isEmpty(notification.getActiondata().getContentid())) {
                    intent = new Intent(activity, ContentDetailActivity.class);
                    intent.putExtra(Constant.EXTRA_DEEP_LINK_IDENTIFIER, notification.getActiondata().getContentid());
                }
                break;

            case NotificationActionId.TRANSFER_SCREEN:
                intent.putExtra(Constant.EXTRA_DEEP_LINK_TRANSFER, true);
                break;

            case NotificationActionId.MANAGE_CHILD_SCREEN:
                intent.putExtra(Constant.EXTRA_DEEP_LINK_MANAGE_CHILD, true);
                break;

            case NotificationActionId.LIST_OF_STORIES:
                if (notification.getActiondata() != null) {
                    ActionData actionData = notification.getActiondata();

                    intent = new Intent(activity, SearchActivity.class);
                    intent.putExtra(Constant.BundleKey.BUNDLE_KEY_IS_FROM_DEEPLINK, true);
                    if (!TextUtils.isEmpty(actionData.getSearchquery())) {
                        if (actionData.getSearchquery().equalsIgnoreCase("all")) {
                            intent.putExtra(Constant.BundleKey.BUNDLE_KEY_SEARCH_QUERY, "");
                        } else {
                            intent.putExtra(Constant.BundleKey.BUNDLE_KEY_SEARCH_QUERY, actionData.getSearchquery());
                        }

                    }

                    if (!TextUtils.isEmpty(actionData.getSearchsortquery())) {

                        intent.putExtra(Constant.EXTRA_DEEP_LINK_SEARCH_SORT, actionData.getSearchsortquery());
                    }

                    if (!TextUtils.isEmpty(actionData.getSearchlanguagequery())) {

                        intent.putExtra(Constant.EXTRA_DEEP_LINK_SEARCH_LANGUAGE, actionData.getSearchlanguagequery());
                    }

                    if (!TextUtils.isEmpty(actionData.getSearchdomain())) {
                        intent.putExtra(Constant.EXTRA_DEEP_LINK_SEARCH_DOMAIN, actionData.getSearchdomain());
                    }

                    if (!TextUtils.isEmpty(actionData.getSearchcontenttype())) {
                        intent.putExtra(Constant.EXTRA_DEEP_LINK_SEARCH_TYPE, actionData.getSearchcontenttype());
                    }

                    if (!TextUtils.isEmpty(actionData.getSearchgradelevel())) {
                        intent.putExtra(Constant.EXTRA_DEEP_LINK_GRADE_LEVEL, actionData.getSearchgradelevel());
                    }
                }
                break;

            case NotificationActionId.DATA_SYNC_SETTINGS_SCREEN:
                intent.putExtra(Constant.EXTRA_DEEP_LINK_DATA_SYNC, true);
                break;

            case NotificationActionId.MY_CONTENT_SCREEN:
                intent.putExtra(Constant.EXTRA_MY_CONTENT, true);
                break;

            default:
                // Do nothing.
                break;
        }

        return intent;
    }

}
