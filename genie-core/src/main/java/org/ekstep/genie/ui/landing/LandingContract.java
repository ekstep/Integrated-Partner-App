package org.ekstep.genie.ui.landing;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import org.ekstep.genie.base.BasePresenter;
import org.ekstep.genie.base.BaseView;
import org.ekstep.genieservices.commons.bean.Profile;

import java.util.List;

/**
 * Created on 16/1/17.
 *
 * @author shriharsh
 */
public interface LandingContract {

    interface View extends BaseView {

        void adjustUIForTelugu();

        void adjustNavigationDrawerPadding();

        void callAddChildActivity(Profile profile);

        void showTransferFragment();

        void showManageChildFragment();

        void showDataSettingsFragment();

        void showMyContentsFragment();

        void showDummyLanguageFragment();

        void doShowSyncPromt();

        void showHome(boolean b);

        void showNotificationFragment();

        void showMyContent();

        void showSettings();

        void showProgressDialog(String message);

        void dismissProgressDialog();

        void showSyncingDialog(String message);

        void finishViewAffinity();

        void setRespectiveFragment(Fragment fragment, boolean flag);

        void endGenieDialog();

        void syncNowDialog();

        void launchImportArchiveView(boolean b);

        void showToast(String string);

        void showUnreadNotificationCount(int count);

        void switchToRelevantFragment(Bundle savedInstanceState, Bundle extras);

        void getFcmTokenAndDisableNCReceiver();
    }

    interface Presenter extends BasePresenter {
        void handleAppUpdate();

        void importExternalContent();

        void showUIAccordingToLanguage();

        void adjustNavigationDrawerPadding();

        void callAddChildActivity(Profile profile);

        void showTransferScreen();

        void showManageChildScreen();

        void setDataSettingsScreen();

        void setMyContentsScreen();

        void setDummyLanguageScreen();

        void showSyncPromt();

        void showHomeScreen(boolean b);

        void showNotificationScreen();

        void showMyContentScreen();

        void showSettingsScreen();

        void syncNow();

        void saveEndGenieEvent();

        void onBackPressed(Fragment fragment);

        void setCorelationIdAndStartGenieShare();

        void showArchieveView(boolean b);

        void permissionGranted(int requestCode);

        void permissionDenied(int requestCode, List<String> deniedPermissions);

        void updateUnreadNotificationCount();

        void switchToRelevantFragment(Bundle savedInstanceState, Bundle extras);

        void getFcmTokenAndDisableNCReceiver();
    }
}
