package org.ekstep.genie.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import org.ekstep.genie.BuildConfig;
import org.ekstep.genie.CoreApplication;
import org.ekstep.genie.R;
import org.ekstep.genie.adapters.LanguageCoverflowAdapter;
import org.ekstep.genie.asynctask.ChangeLanguageAsynTask;
import org.ekstep.genie.base.BaseView;
import org.ekstep.genie.base.IPresenterFactory;
import org.ekstep.genie.callback.IInitAndExecuteGenie;
import org.ekstep.genie.customview.FancyCoverFlow;
import org.ekstep.genie.notification.NotificationManagerUtil;
import org.ekstep.genie.telemetry.EnvironmentId;
import org.ekstep.genie.telemetry.TelemetryAction;
import org.ekstep.genie.telemetry.TelemetryBuilder;
import org.ekstep.genie.telemetry.TelemetryConstant;
import org.ekstep.genie.telemetry.TelemetryHandler;
import org.ekstep.genie.telemetry.TelemetryStageId;
import org.ekstep.genie.telemetry.enums.CoRelationIdContext;
import org.ekstep.genie.telemetry.enums.ImpressionType;
import org.ekstep.genie.ui.landing.LandingActivity;
import org.ekstep.genie.util.AnimationUtils;
import org.ekstep.genie.util.Constant;
import org.ekstep.genie.util.DeepLinkNavigation;
import org.ekstep.genie.util.DeviceUtility;
import org.ekstep.genie.util.FileHandler;
import org.ekstep.genie.util.FontConstants;
import org.ekstep.genie.util.FontUtil;
import org.ekstep.genie.util.LaunchUtil;
import org.ekstep.genie.util.LogUtil;
import org.ekstep.genie.util.ShowProgressDialog;
import org.ekstep.genie.util.TagValidator;
import org.ekstep.genie.util.Util;
import org.ekstep.genie.util.geniesdk.ImportExportUtil;
import org.ekstep.genie.util.preference.PreferenceUtil;
import org.ekstep.genieservices.async.ContentService;
import org.ekstep.genieservices.commons.IResponseHandler;
import org.ekstep.genieservices.commons.bean.Content;
import org.ekstep.genieservices.commons.bean.GenieResponse;
import org.ekstep.genieservices.commons.bean.Notification;
import org.ekstep.genieservices.commons.bean.Tag;
import org.ekstep.genieservices.commons.bean.enums.InteractionType;
import org.ekstep.genieservices.commons.utils.StringUtil;
import org.ekstep.genieservices.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LanguageFragment extends BaseFragment
        implements OnClickListener {

    private static final String TAG = LanguageFragment.class.getSimpleName();

    private ImageView mOkBtn;
    private FancyCoverFlow mFancyCoverFlow;
    private Animation mPulseAnimation = null;
    private ContentService mContentService;
    private String mFilePath = null;
    private Intent mReceivedIntent = null;
    private Uri mDeepLinkUri = null;
    private Intent mNotificationIntent = null;
    private boolean mIsPartnerLaunch = false;
    private boolean mIsFromNotification = false;

    private DeepLinkNavigation.IValidateDeepLink mIValidateDeepLink = new DeepLinkNavigation.IValidateDeepLink() {

        @Override
        public void validLocalDeepLink() {
        }

        @Override
        public void validServerDeepLink() {
            if (PreferenceUtil.getOnBoardingState() == Constant.On_BOARD_STATE_DEFAULT) {
                PreferenceUtil.setOnBoardingState(PreferenceUtil.getOnBoardingState() + 1);
            }
        }

        @Override
        public void notAValidDeepLink() {
        }

        @Override
        public void onTagDeeplinkFound(String tagName, String description, String startDate, String endDate) {

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

                CoreApplication.getGenieAsyncService().getTagService().setTag(tag, new IResponseHandler<Void>() {
                    @Override
                    public void onSuccess(GenieResponse<Void> genieResponse) {
                        //        TODO: (s)to be implemented
//                        TelemetryHandler.saveTelemetry(TelemetryBuilder.buildGEInteract(InteractionType.TOUCH, TelemetryStageId.SPLASH, TelemetryAction.ADD_TAG_DEEP_LINK));
                        Util.showCustomToast(R.string.msg_tag_added_sussessfully);
                        proceedToLanding();
                    }

                    @Override
                    public void onError(GenieResponse<Void> genieResponse) {
                        proceedToLanding();
                    }
                });
            }
        }
    };

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void onDeepLinkIntentReceived(Intent intent) throws InterruptedException {
        if (intent != null && intent.getBooleanExtra(Constant.EXTRA_LAUNCH_IS_DEEPLINK, false)) {
            mDeepLinkUri = intent.getData();

            EventBus.removeStickyEvent(intent);
        }
    }


    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void onFilePathIntentReceived(Intent intent) throws InterruptedException {
        if (intent != null && intent.getBooleanExtra(Constant.EXTRA_LAUNCH_IS_FILE_PATH, false)) {
            mFilePath = intent.getData().getPath();
            mReceivedIntent = intent;

            EventBus.removeStickyEvent(intent);
        }
    }

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void onNotificationIntentReceived(Intent intent) throws InterruptedException {
        if (intent != null && intent.getBooleanExtra(Constant.EXTRA_LAUNCH_IS_NOTIFICATION, false)) {
            LogUtil.e(getTAG(), "Notification Intent - " + intent.getBooleanExtra(Constant.EXTRA_LAUNCH_IS_NOTIFICATION, false));

            mNotificationIntent = intent;

            mIsFromNotification = true;

            EventBus.removeStickyEvent(intent);
        }
    }

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void onPartnerLaunchIntentReceived(Intent intent) throws InterruptedException {
        if (intent != null && intent.getBooleanExtra(Constant.EXTRA_LAUNCH_IS_BY_PARTNER, false)) {
            LogUtil.e(getTAG(), "Partner Launch Intent - " + intent.getBooleanExtra(Constant.EXTRA_LAUNCH_IS_BY_PARTNER, false));

            LaunchUtil.handlePartnerInfo(intent.getExtras(), true);

            mIsPartnerLaunch = true;

            EventBus.removeStickyEvent(intent);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        if (!EventBus.isSubscriberRegistered(this)) {
            EventBus.registerSubscriber(this);
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.unregisterSubscriber(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_language, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mContentService = CoreApplication.getGenieAsyncService().getContentService();

        mFancyCoverFlow = (FancyCoverFlow) view.findViewById(R.id.fancyCoverFlow);
        mFancyCoverFlow.setUnselectedAlpha(1.0f);
        mFancyCoverFlow.setUnselectedSaturation(0.0f);
        mFancyCoverFlow.setUnselectedScale(0.5f);
        mFancyCoverFlow.setMaxRotation(0);
        mFancyCoverFlow.setActionDistance(FancyCoverFlow.ACTION_DISTANCE_AUTO);

        final LanguageCoverflowAdapter mAvatarAdapter = new LanguageCoverflowAdapter(getActivity());
        mFancyCoverFlow.setAdapter(mAvatarAdapter);
        if (PreferenceUtil.isFirstTime().equalsIgnoreCase("true")) {
            mAvatarAdapter.setSelectedPosition(-1);
        } else {
            mFancyCoverFlow.setSelection(FontUtil.getInstance().getPositionForSelectedLocale(), true);
        }


        mFancyCoverFlow.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mAvatarAdapter.setSelectedPosition(position);
                mAvatarAdapter.notifyDataSetChanged();
                mOkBtn.setVisibility(View.VISIBLE);
                if (!BuildConfig.FLAVOR.equalsIgnoreCase("integrationTest")) {
                    if (mPulseAnimation != null) {
                        mPulseAnimation.cancel();
                    }
                    mPulseAnimation = AnimationUtils.showPulseAnimation(mOkBtn);
                }

            }
        });

        mOkBtn = (ImageView) view.findViewById(R.id.language_ok_btn);
        mOkBtn.setOnClickListener(this);

        if (!PreferenceUtil.getLanguage().equalsIgnoreCase(FontConstants.LANG_ENGLISH)) {
            ((TextView) view.findViewById(R.id.txt_header)).setTextSize(20);
        }

        if (PreferenceUtil.isFirstTime().equalsIgnoreCase("true")) {
            mOkBtn.setVisibility(View.INVISIBLE);
            ((TextView) view.findViewById(R.id.txt_header)).setPadding(0, DeviceUtility.dp2px(mActivity, 12), 0, 0);
        }
        TelemetryHandler.saveTelemetry(TelemetryBuilder.buildImpressionEvent(EnvironmentId.HOME, TelemetryStageId.LANGUAGE_SELECTION, ImpressionType.VIEW));
    }

    @Override
    protected IPresenterFactory getPresenterFactory() {
        return null;
    }

    @Override
    protected String getTAG() {
        return LanguageFragment.class.getSimpleName();
    }

    @Override
    protected BaseView getBaseView() {
        return null;
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.language_ok_btn) {
            if (mPulseAnimation != null) {
                mPulseAnimation.cancel();
            }
            mOkBtn.setVisibility(View.VISIBLE);
            setLanguage();


//            case R.id.back_btn:
//                if (mPulseAnimation != null) {
//                    mPulseAnimation.cancel();
//                }
//                if (this.getActivity() instanceof LandingActivity) {
//                    ((LandingActivity) this.getActivity()).setFragment(new SettingsFragment(), false);
//                }
//                break;
        } else {
        }
    }

    private void setLanguage() {
        mOkBtn.setEnabled(false);

        String locale = FontUtil.getInstance().getLocaleAtPosition(mFancyCoverFlow.getSelectedItemPosition());
        LogUtil.i(TAG, "Locale: " + locale);

        if (PreferenceUtil.isFirstTime().equalsIgnoreCase("false")) {
            ChangeLanguageAsynTask changeLanguageAsyncTask = new ChangeLanguageAsynTask(getActivity());
            changeLanguageAsyncTask.execute(locale);

        } else {
            PreferenceUtil.setLanguageSelected(true);
            PreferenceUtil.setLanguage(locale);
            FontUtil.getInstance().changeLocale();
            ShowProgressDialog.showProgressDialog(getActivity(), getString(R.string.msg_setting_changing_language));
            Map<String, Object> map = new HashMap<>();
            map.put(TelemetryConstant.LANGUAGE_SELECTED, locale);
            TelemetryHandler.saveTelemetry(TelemetryBuilder.buildInteractEvent(EnvironmentId.HOME, InteractionType.TOUCH, TelemetryAction.LANGUAGE_SETTINGS_SUCCESS, TelemetryStageId.LANGUAGE_SELECTION, map));
            mContentService.getAllLocalContent(null, new IResponseHandler<List<Content>>() {
                @Override
                public void onSuccess(GenieResponse<List<Content>> genieResponse) {
                    List<Content> contentList = genieResponse.getResult();
                    if (genieResponse.getResult().size() == 0) {
                        importContent(Constant.CONTENT1_FILE_PATH, false);
                    } else if (contentList.size() == 1) {
                        importContent(Constant.CONTENT2_FILE_PATH, false);
                    } else if (contentList.size() == 2) {
                        importContent(Constant.CONTENT3_FILE_PATH, true);

                    }
                }

                @Override
                public void onError(GenieResponse<List<Content>> genieResponse) {

                }
            });

        }
    }

    private void importContent(final String path, final boolean dismissProgressDialog) {
        ImportExportUtil.importContent(getActivity(), FileHandler.createFileFromAsset(CoreApplication.getInstance(), path), new ImportExportUtil.IImport() {
            @Override
            public void onImportSuccess() {
                if (dismissProgressDialog && mDeepLinkUri == null && !mIsPartnerLaunch && !mIsFromNotification) {
                    proceedToLanding();
                } else {
                    if (Constant.CONTENT1_FILE_PATH.equalsIgnoreCase(path)) {
                        importContent(Constant.CONTENT2_FILE_PATH, false);
                    } else if (Constant.CONTENT2_FILE_PATH.equalsIgnoreCase(path)) {
                        if (!TextUtils.isEmpty(mFilePath)) {
                            importContent(Constant.CONTENT3_FILE_PATH, false);
                        } else {
                            importContent(Constant.CONTENT3_FILE_PATH, true);
                        }
                    } else if (!TextUtils.isEmpty(mFilePath)) {
                        ImportExportUtil.initiateImportFile(getActivity(),
                                new IInitAndExecuteGenie() {
                                    @Override
                                    public void initAndExecuteGenie() {
                                        proceedToLanding();
                                    }
                                }, mReceivedIntent, false);

                        mFilePath = null;
                        mReceivedIntent = null;
                    } else if (mDeepLinkUri != null) {
                        DeepLinkNavigation deepLinkNavigation = new DeepLinkNavigation(mActivity);
                        deepLinkNavigation.validateAndHandleDeepLink(mDeepLinkUri, mIValidateDeepLink);
                    } else if (mIsPartnerLaunch) {
                        proceedToLanding();

                        mIsPartnerLaunch = false;
                    } else if (mIsFromNotification) {
                        if (mNotificationIntent != null) {
                            if (PreferenceUtil.getOnBoardingState() == Constant.On_BOARD_STATE_DEFAULT) {
                                PreferenceUtil.setOnBoardingState(PreferenceUtil.getOnBoardingState() + 1);
                            }

                            mActivity.startActivity(mNotificationIntent);
                            mActivity.finish();
                        }
                    }
                }
            }

            @Override
            public void onImportFailure() {
                LogUtil.i(TAG, "importContent ==> onImportFailure");
            }

            @Override
            public void onOutDatedEcarFound() {
                LogUtil.i(TAG, "importContent ==> onOutDatedEcarFound");
            }
        }, false, false);
    }

    private void proceedToLanding() {
        registerOnboardNotification();

        ShowProgressDialog.dismissDialog();

        if (PreferenceUtil.getOnBoardingState() == Constant.On_BOARD_STATE_DEFAULT) {
            PreferenceUtil.setOnBoardingState(PreferenceUtil.getOnBoardingState() + 1);
        }

        PreferenceUtil.setCoRelationIdContext(CoRelationIdContext.ONBOARDING);

        PreferenceUtil.setOnBoardingCorelationId(Util.getOnBoardingCoRelationId());
        LogUtil.i("Corelation ID", "" + PreferenceUtil.getCoRelationId());

//        TODO: (s)to be implemented
//        TelemetryHandler.saveTelemetry(TelemetryBuilder.buildGEInteract(InteractionType.OTHER, TelemetryStageId.GENIE_HOME_ONBOARDING_SCREEN, null, null, new HashMap<String, Object>(), PreferenceUtil.getCoRelationList()));

        Intent intent = new Intent(getActivity(), LandingActivity.class);
        startActivity(intent);
        getActivity().finish();
    }

    private void registerOnboardNotification() {
        NotificationManagerUtil notificationManagerUtil = new NotificationManagerUtil(mActivity);

        for (Notification genieNotification : notificationManagerUtil.getOnboardNotificationList()) {
            notificationManagerUtil.handleNotification(genieNotification);
        }
    }

}
