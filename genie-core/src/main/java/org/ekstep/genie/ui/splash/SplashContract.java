package org.ekstep.genie.ui.splash;

import android.content.Intent;

import org.ekstep.genie.base.BasePresenter;
import org.ekstep.genie.base.BaseView;

import java.io.IOException;
import java.util.List;

/**
 * Created on 12/21/2016.
 *
 * @author anil
 */
public interface SplashContract {

    interface View extends BaseView {
        void showSplash();

        void showHomeScreen();

        void showWelcomeScreen();

        void postFilePathIntent(Intent eventObject);

        void postDeepLinkIntent(Intent intent);

        void postNotificationIntent(Intent intent);

        void postPartnerLaunchIntent(Intent intent);
    }

    interface Presenter extends BasePresenter {
        void saveGenieStartEvent();

        void initCoRelationData();

        void openSplash(Intent intent);

        void onPermissionsGranted(int requestCode);

        void onPermissionsDenied(List<String> deniedPermissions);

        void copyProfilesToExternalDirectory();

        void makeEntryInGenieSupportFile() throws IOException;
    }

}
