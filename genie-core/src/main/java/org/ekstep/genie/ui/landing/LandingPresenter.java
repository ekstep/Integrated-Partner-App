package org.ekstep.genie.ui.landing;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;

import org.ekstep.genie.BuildConfig;
import org.ekstep.genie.CoreApplication;
import org.ekstep.genie.R;
import org.ekstep.genie.base.BasePresenter;
import org.ekstep.genie.base.BaseView;
import org.ekstep.genie.fragment.BaseFragment;
import org.ekstep.genie.telemetry.TelemetryAction;
import org.ekstep.genie.telemetry.TelemetryBuilder;
import org.ekstep.genie.telemetry.TelemetryHandler;
import org.ekstep.genie.telemetry.TelemetryStageId;
import org.ekstep.genie.ui.landing.home.HomeFragment;
import org.ekstep.genie.ui.managechild.ManageChildFragment;
import org.ekstep.genie.ui.managechild.ManageChildPresenter;
import org.ekstep.genie.ui.mycontent.MyContentFragment;
import org.ekstep.genie.ui.mycontent.MyContentPresenter;
import org.ekstep.genie.ui.share.ShareActivity;
import org.ekstep.genie.util.DialogUtils;
import org.ekstep.genie.util.FontConstants;
import org.ekstep.genie.util.LogUtil;
import org.ekstep.genie.util.Util;
import org.ekstep.genie.util.geniesdk.SyncServiceUtil;
import org.ekstep.genie.util.preference.PreferenceUtil;
import org.ekstep.genieservices.GenieService;
import org.ekstep.genieservices.async.NotificationService;
import org.ekstep.genieservices.commons.IResponseHandler;
import org.ekstep.genieservices.commons.bean.GenieResponse;
import org.ekstep.genieservices.commons.bean.Notification;
import org.ekstep.genieservices.commons.bean.NotificationFilterCriteria;
import org.ekstep.genieservices.commons.bean.Profile;
import org.ekstep.genieservices.commons.bean.SyncStat;
import org.ekstep.genieservices.commons.bean.enums.InteractionType;
import org.ekstep.genieservices.commons.bean.enums.NotificationStatus;
import org.ekstep.genieservices.commons.bean.telemetry.GEEvent;
import org.ekstep.genieservices.commons.utils.GsonUtil;

import java.util.List;
import java.util.Map;

import static org.ekstep.genie.activity.RuntimePermissionsActivity.REQUEST_PERMISSION_IMPORT_CONTENT;
import static org.ekstep.genie.activity.RuntimePermissionsActivity.REQUEST_PERMISSION_IMPORT_PROFILE;

//import org.ekstep.genie.fragment.AddNewTagFragment;
//import org.ekstep.genie.fragment.AdvancedSettingFragment;

/**
 * Created on 16/1/17.
 *
 * @author shriharsh
 */
public class LandingPresenter implements LandingContract.Presenter {

    private static String TAG = LandingPresenter.class.getSimpleName();

    private Context mContext;
    private LandingContract.View mLandingView;

    private NotificationService mNotificationService;

    public LandingPresenter() {
        mNotificationService = GenieService.getAsyncService().getNotificationService();
    }

    @Override
    public void handleAppUpdate() {
        String supportedGenieVersions = PreferenceUtil.getSupportedGenieVersions();
        if (!TextUtils.isEmpty(supportedGenieVersions)) {

            Map mapData = GsonUtil.fromJson(supportedGenieVersions, Map.class);

            double minVersion = (double) mapData.get("minVersion");
            double currentVersion = (double) mapData.get("currentVersion");

            if (BuildConfig.VERSION_CODE < minVersion) {
                DialogUtils.showDialogUpgradeGenie(mContext, true);
            } else if (BuildConfig.VERSION_CODE < currentVersion) {
                DialogUtils.showDialogUpgradeGenie(mContext, false);
            }
        }
    }

    @Override
    public void importExternalContent() {
        if (PreferenceUtil.isFirstTime().equalsIgnoreCase("true")) {    // After first launch.
            return;
        }

    }

    @Override
    public void showUIAccordingToLanguage() {
        if (PreferenceUtil.getLanguage().equalsIgnoreCase(FontConstants.LANG_TELUGU)) {
            mLandingView.adjustUIForTelugu();
        }
    }

    @Override
    public void adjustNavigationDrawerPadding() {
        mLandingView.adjustNavigationDrawerPadding();
    }

    @Override
    public void callAddChildActivity(Profile profile) {
        mLandingView.callAddChildActivity(profile);
    }

    @Override
    public void showTransferScreen() {
        mLandingView.showTransferFragment();
    }

    @Override
    public void showManageChildScreen() {
        mLandingView.showManageChildFragment();
    }

    @Override
    public void setDataSettingsScreen() {
        mLandingView.showDataSettingsFragment();

    }

    @Override
    public void setMyContentsScreen() {
        mLandingView.showMyContentsFragment();
    }

    @Override
    public void setDummyLanguageScreen() {
        mLandingView.showDummyLanguageFragment();
    }

    @Override
    public void showSyncPromt() {
        if (SyncServiceUtil.shouldShowSyncPrompt()) {
            if (PreferenceUtil.isFirstTime().equalsIgnoreCase("false")) {
                mLandingView.syncNowDialog();
            }
        }
    }

    @Override
    public void showHomeScreen(boolean b) {
        mLandingView.showHome(b);
    }

    @Override
    public void showNotificationScreen() {
        mLandingView.showNotificationFragment();
    }

    @Override
    public void showMyContentScreen() {
        mLandingView.showMyContent();

    }

    @Override
    public void showSettingsScreen() {
        mLandingView.showSettings();
    }

    @Override
    public void syncNow() {
        try {
            mLandingView.showProgressDialog(mContext.getResources().getString(R.string.msg_syncing));

            TelemetryHandler.saveTelemetry(TelemetryBuilder.buildGEInteract(InteractionType.TOUCH, TelemetryStageId.GENIE_HOME, TelemetryAction.MANUAL_SYNC_INITIATED));
            SyncServiceUtil.getSyncService().sync(new IResponseHandler<SyncStat>() {
                @Override
                public void onSuccess(GenieResponse<SyncStat> genieResponse) {
                    LogUtil.i(TAG, "Manual Sync Success");
                    SyncStat syncStat = genieResponse.getResult();
                    TelemetryHandler.saveTelemetry(TelemetryBuilder.buildGEInteract(InteractionType.OTHER, TelemetryStageId.GENIE_HOME, TelemetryAction.MANUAL_SYNC_SUCCESS, SyncServiceUtil.getFileSizeMap(syncStat)));
                    mLandingView.dismissProgressDialog();

                    Util.processSuccess(genieResponse);

                    if (mLandingView != null) {
                        mLandingView.showSyncingDialog(mContext.getResources().getString(R.string.msg_sync_successfull));
                    }

                }

                @Override
                public void onError(GenieResponse<SyncStat> genieResponse) {
                    LogUtil.e(TAG, "Manual Sync Failure");
                    Util.processFailure(genieResponse);
                }
            });

        } catch (Throwable e) {
            mLandingView.showToast(mContext.getResources().getString(R.string.error_network));
        }
    }

    @Override
    public void saveEndGenieEvent() {
        GEEvent event = TelemetryBuilder.buildGenieEndEvent();
        TelemetryHandler.saveTelemetry(event, new IResponseHandler() {
            @Override
            public void onSuccess(GenieResponse genieResponse) {
                Util.processSuccess(genieResponse);
                CoreApplication.getInstance().setGenieAlive(-1);

                mLandingView.finishViewAffinity();
            }

            @Override
            public void onError(GenieResponse genieResponse) {
                Util.processFailure(genieResponse);
            }
        });
    }

    @Override
    public void onBackPressed(Fragment fragment) {
        BasePresenter basePresenter = ((BaseFragment) fragment).getPresenter();
//        if (fragment instanceof LanguageFragment
//                || fragment instanceof AboutFragment
//                || fragment instanceof AdvancedSettingFragment) {
//            mLandingView.setRespectiveFragment(new SettingsFragment(), false);
//        } else if (fragment instanceof PrivacyPolicyFragment
//                || fragment instanceof AboutUsFragment
//                || fragment instanceof TermsnConditionFragment
//                || fragment instanceof DummyLanguageTestFragment) {
//            mLandingView.setRespectiveFragment(new AboutFragment(), false);
//        } else
            if (fragment instanceof MyContentFragment) {
            ((MyContentPresenter) basePresenter).handleBackButtonClicked();
        } else if (fragment instanceof ManageChildFragment) {
            ((ManageChildPresenter) basePresenter).handleBackButtonClicked();
        }
//        else if (fragment instanceof AddNewTagFragment) {
//            mLandingView.setRespectiveFragment(new AdvancedSettingFragment(), false);
//        }
        else if (!(fragment instanceof HomeFragment)) {
            mLandingView.showHome(false);
        } else {
            mLandingView.endGenieDialog();
        }
    }

    @Override
    public void setCorelationIdAndStartGenieShare() {
        PreferenceUtil.setCoRelationIdContext(null);
        ShareActivity.startGenieShareIntent(mContext);
    }

    @Override
    public void showArchieveView(boolean b) {
        mLandingView.launchImportArchiveView(b);
    }

    @Override
    public void permissionGranted(int requestCode) {
        switch (requestCode) {
            case REQUEST_PERMISSION_IMPORT_CONTENT:
                showArchieveView(false);
                break;

            case REQUEST_PERMISSION_IMPORT_PROFILE:
                showArchieveView(true);
                break;
        }
    }

    @Override
    public void permissionDenied(int requestCode, List<String> deniedPermissions) {
        switch (requestCode) {
            case REQUEST_PERMISSION_IMPORT_CONTENT:
                showArchieveView(false);
                break;

            case REQUEST_PERMISSION_IMPORT_PROFILE:
                showArchieveView(true);
                break;
        }
    }

    @Override
    public void updateUnreadNotificationCount() {
        NotificationFilterCriteria.Builder request = new NotificationFilterCriteria.Builder();
        request.notificationStatus(NotificationStatus.UNREAD);

        mNotificationService.getAllNotifications(request.build(), new IResponseHandler<List<Notification>>() {
            @Override
            public void onSuccess(GenieResponse<List<Notification>> genieResponse) {
                if (genieResponse != null) {
                    List<Notification> unreadNotifications = genieResponse.getResult();
                    if (unreadNotifications != null && unreadNotifications.size() > 0) {
                        int count = unreadNotifications.size();
                        mLandingView.showUnreadNotificationCount(count);
                    }
                }

            }

            @Override
            public void onError(GenieResponse<List<Notification>> genieResponse) {
            }
        });
    }

    @Override
    public void switchToRelevantFragment(Bundle savedInstanceState, Bundle extras) {
        mLandingView.switchToRelevantFragment(savedInstanceState, extras);
    }

    @Override
    public void getFcmTokenAndDisableNCReceiver() {
        mLandingView.getFcmTokenAndDisableNCReceiver();
    }

    @Override
    public void bindView(BaseView view, Context context) {
        mContext = context;
        mLandingView = (LandingContract.View) view;
    }

    @Override
    public void unbindView() {
        mContext = null;
        mLandingView = null;
    }
}
