package org.ekstep.ipa.ui.drive;

import android.Manifest;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.widget.Toast;

import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.drive.Drive;
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential;

import org.ekstep.genie.activity.BaseActivity;
import org.ekstep.genie.base.BasePresenter;
import org.ekstep.genie.base.BaseView;
import org.ekstep.genie.base.IPresenterFactory;
import org.ekstep.ipa.R;

import java.util.Arrays;

/**
 * @author vinayagasundar
 */

public class DriveActivity extends BaseActivity implements DriveContract.View {

    private DriveContract.Presenter mPresenter;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drive);

        mPresenter = (DriveContract.Presenter) presenter;
        mPresenter.initGoogleApiClient();

        mPresenter.downloadAndSyncPartnerData();
    }

    @Override
    protected IPresenterFactory getPresenterFactory() {
        return new IPresenterFactory() {
            @Override
            public BasePresenter create() {
                return new DrivePresenter();
            }
        };
    }

    @Override
    protected String getTAG() {
        return getLocalClassName();
    }

    @Override
    protected BaseView getBaseView() {
        return this;
    }


    @Override
    public void showProgressLoading() {

    }

    @Override
    public void acquireGooglePlayServices() {
        GoogleApiAvailability apiAvailability =
                GoogleApiAvailability.getInstance();
        final int connectionStatusCode =
                apiAvailability.isGooglePlayServicesAvailable(this);
        if (apiAvailability.isUserResolvableError(connectionStatusCode)) {
            showGooglePlayServicesAvailabilityErrorDialog(connectionStatusCode);
        }
    }

    @Override
    public void showAccountDialog(GoogleAccountCredential mCredential, int requestCode) {
        if (ActivityCompat.checkSelfPermission(
                this, Manifest.permission.GET_ACCOUNTS) == PackageManager.PERMISSION_GRANTED) {
            startActivityForResult(mCredential.newChooseAccountIntent(), requestCode);
        } else {
            // Request the GET_ACCOUNTS permission via a user dialog
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.GET_ACCOUNTS},
                    DrivePresenter.REQUEST_PERMISSION_GET_ACCOUNTS);
        }
    }

    @Override
    public void showFilePicker(int requestId, GoogleApiClient googleApiClient, String[] mimeType) {

        IntentSender intentSender = Drive.DriveApi.newOpenFileActivityBuilder()
                .setMimeType(mimeType)
                .build(googleApiClient);

        try {
            startIntentSenderForResult(intentSender, requestId, null, 0, 0, 0);
        } catch (IntentSender.SendIntentException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void showErrorMessageDialog(String message) {
        AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle(org.ekstep.genie.R.string.app_name)
                .setMessage(message)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
//                        moveToLoginScreen();
                        // TODO: 18/9/17 Added screen redirects
                    }
                })
                .setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                })
                .create();

        dialog.show();
    }


    @Override
    public void requestDriveAuthorization(Intent intent, int requestCode) {
        if (intent != null) {
            startActivityForResult(intent, requestCode);
        }
    }

    @Override
    public void showSuccessMessageAndRedirect(Intent intent) {
        Toast.makeText(this, "Synced Successfully", Toast.LENGTH_SHORT).show();
        startActivity(intent);
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mPresenter.handleActivityResult(requestCode, resultCode, data);
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == DrivePresenter.REQUEST_PERMISSION_GET_ACCOUNTS) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                mPresenter.permissionGranted(DrivePresenter.REQUEST_PERMISSION_GET_ACCOUNTS);
            } else {
                mPresenter.permissionDenied(DrivePresenter.REQUEST_PERMISSION_GET_ACCOUNTS,
                        Arrays.asList(permissions));
            }
        }
    }

    /**
     * Display an error dialog showing that Google Play Services is missing
     * or out of date.
     * @param connectionStatusCode code describing the presence (or lack of)
     *     Google Play Services on this device.
     */
    void showGooglePlayServicesAvailabilityErrorDialog(
            final int connectionStatusCode) {
        GoogleApiAvailability apiAvailability = GoogleApiAvailability.getInstance();
        Dialog dialog = apiAvailability.getErrorDialog(
                DriveActivity.this,
                connectionStatusCode,
                DrivePresenter.REQUEST_GOOGLE_PLAY_SERVICES);
        dialog.show();
    }
}
