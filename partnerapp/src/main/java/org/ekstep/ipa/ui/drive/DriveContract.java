package org.ekstep.ipa.ui.drive;

import android.content.Intent;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential;

import org.ekstep.genie.base.BasePresenter;
import org.ekstep.genie.base.BaseView;

import java.util.List;

/**
 * @author vinayagasundar
 */

public interface DriveContract {


    interface View extends BaseView {
        void showProgressLoading();

        void acquireGooglePlayServices();

        void showAccountDialog(GoogleAccountCredential mCredential, int requestCode);

        void showFilePicker(int requestId, GoogleApiClient googleApiClient, String[] mimeType);

        void showErrorMessageDialog(String message);

        void requestDriveAuthorization(Intent intent, int requestCode);

        void showSuccessMessageAndRedirect(Intent intent);
    }


    interface Presenter extends BasePresenter {

        void initGoogleApiClient();

        void downloadAndSyncPartnerData();

        void handleActivityResult(int requestCode, int resultCode, Intent data);

        void permissionGranted(int requestCode);

        void permissionDenied(int requestCode, List<String> deniedPermissions);
    }
}

