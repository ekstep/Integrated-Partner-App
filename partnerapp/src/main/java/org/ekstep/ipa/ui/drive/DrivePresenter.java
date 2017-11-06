package org.ekstep.ipa.ui.drive;

import android.accounts.AccountManager;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.drive.Drive;
import com.google.android.gms.drive.DriveId;
import com.google.android.gms.drive.OpenFileActivityBuilder;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential;
import com.google.api.client.googleapis.extensions.android.gms.auth.UserRecoverableAuthIOException;
import com.google.api.client.googleapis.json.GoogleJsonResponseException;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.ExponentialBackOff;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.SheetsScopes;
import com.google.api.services.sheets.v4.model.ValueRange;

import org.ekstep.genie.BuildConfig;
import org.ekstep.genie.base.BaseView;
import org.ekstep.genie.util.preference.PreferenceUtil;
import org.ekstep.ipa.db.StudentDAO;
import org.ekstep.ipa.ui.landing.LandingActivity;
import org.ekstep.ipa.util.preference.PartnerPrefUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

/**
 * @author vinayagasundar
 */

public class DrivePresenter implements DriveContract.Presenter {

    static final int REQUEST_GOOGLE_PLAY_SERVICES = 1002;
    static final int REQUEST_PERMISSION_GET_ACCOUNTS = 1003;

    private static final int REQUEST_ACCOUNT_PICKER = 1000;
    private static final int REQUEST_AUTHORIZATION = 1001;
    private static final int REQUEST_CODE_FILE_PICKER = 1004;
    private static final int REQUEST_CODE_GOOGLE_API_CONNECT_RES = 1005;


    private static final String[] SCOPES = {
            SheetsScopes.DRIVE,
            SheetsScopes.DRIVE_FILE,
            SheetsScopes.SPREADSHEETS,
            SheetsScopes.SPREADSHEETS_READONLY
    };

    private static final String[] FILE_MIME_FILTER = {
            "application/vnd.google-apps.spreadsheet"
    };

    private GoogleAccountCredential mCredential;

    private GoogleApiClient mGoogleAPIClient;

    private Activity mActivity;

    private DriveContract.View mDriveView;

    private Handler mHandler = new Handler();

    private String mFileResId;

    @Override
    public void bindView(BaseView view, Context context) {
        mDriveView = (DriveContract.View) view;
        mActivity = (Activity) context;
    }

    @Override
    public void unbindView() {
        mDriveView = null;
        mActivity = null;
    }

    @Override
    public void initGoogleApiClient() {
        if (mActivity == null) {
            return;
        }

        // Initialize credentials and service object.
        mCredential = GoogleAccountCredential.usingOAuth2(
                mActivity.getApplicationContext(), Arrays.asList(SCOPES))
                .setBackOff(new ExponentialBackOff());
    }

    @Override
    public void downloadAndSyncPartnerData() {
        if (!isDeviceOnline()) {
            mDriveView.showErrorMessageDialog(mActivity.getString(org.ekstep.genie.R.string.error_network2));
            return;
        }

        if (!isGooglePlayServicesAvailable()) {
            mDriveView.acquireGooglePlayServices();
        } else if (mCredential.getSelectedAccountName() == null) {
            String accountName = PartnerPrefUtil.getGoogleAccount();
            if (accountName != null) {
                mCredential.setSelectedAccountName(accountName);
                startFilePicker();
            } else {
                mDriveView.showAccountDialog(mCredential, REQUEST_ACCOUNT_PICKER);
            }

        } else {
            startFilePicker();
        }
    }

    @Override
    public void handleActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case REQUEST_GOOGLE_PLAY_SERVICES:
                if (resultCode != Activity.RESULT_OK) {
                    AlertDialog dialog = new AlertDialog.Builder(mActivity)
                            .setMessage( "This app requires Google Play Services. Please install " +
                                    "Google Play Services on your device and relaunch this app.")
                            .create();
                    dialog.show();
                } else {
                    downloadAndSyncPartnerData();
                }
                break;

            case REQUEST_ACCOUNT_PICKER:
                if (resultCode == Activity.RESULT_OK && data != null
                        && data.getExtras() != null) {
                    String accountName = data.getStringExtra(AccountManager.KEY_ACCOUNT_NAME);
                    PartnerPrefUtil.setGoogleAccount(accountName);
                    mCredential.setSelectedAccountName(accountName);
                    downloadAndSyncPartnerData();
                }
                break;

            case REQUEST_CODE_GOOGLE_API_CONNECT_RES:
                if (resultCode == Activity.RESULT_OK) {
                    downloadAndSyncPartnerData();
                }
                break;

            case REQUEST_CODE_FILE_PICKER:
                if (resultCode == Activity.RESULT_OK) {
                    DriveId driveId = data.getParcelableExtra(
                            OpenFileActivityBuilder.EXTRA_RESPONSE_DRIVE_ID);
                    mFileResId = driveId.getResourceId();
                    downloadAndSyncData(mFileResId);
                }
                break;

            case REQUEST_AUTHORIZATION:
                if (resultCode == Activity.RESULT_OK) {
                    downloadAndSyncData(mFileResId);
                }
                break;
        }
    }

    @Override
    public void permissionGranted(int requestCode) {
        if (requestCode == REQUEST_PERMISSION_GET_ACCOUNTS) {
            downloadAndSyncPartnerData();
        }
    }

    @Override
    public void permissionDenied(int requestCode, List<String> deniedPermissions) {

    }


    /**
     * Start the file Picker
     */
    private void startFilePicker() {
        if (mGoogleAPIClient != null && mGoogleAPIClient.isConnected()) {
            mDriveView.showFilePicker(REQUEST_CODE_FILE_PICKER, mGoogleAPIClient, FILE_MIME_FILTER);
            return;
        }

        mGoogleAPIClient = new GoogleApiClient.Builder(mActivity)
                .addApi(Drive.API)
                .addScope(Drive.SCOPE_FILE)
                .addConnectionCallbacks(new GoogleApiClient.ConnectionCallbacks() {
                    @Override
                    public void onConnected(@Nullable Bundle bundle) {
                        downloadAndSyncPartnerData();
                    }

                    @Override
                    public void onConnectionSuspended(int i) {

                    }
                })
                .addOnConnectionFailedListener(new GoogleApiClient.OnConnectionFailedListener() {
                    @Override
                    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
                        if (connectionResult.hasResolution()) {
                            try {
                                connectionResult.startResolutionForResult(
                                        mActivity,
                                        REQUEST_CODE_GOOGLE_API_CONNECT_RES);
                            } catch (IntentSender.SendIntentException e) {
                                e.printStackTrace();
                                mDriveView.showErrorMessageDialog("Not able to connect Google API");
                            }

                        }
                    }
                })
                .setAccountName(mCredential.getSelectedAccountName())
                .build();


        mGoogleAPIClient.connect();
    }



    private void downloadAndSyncData(final String resourceId) {
        Runnable downloadAndSync = new Runnable() {
            @Override
            public void run() {
                HttpTransport transport = AndroidHttp.newCompatibleTransport();
                JsonFactory jsonFactory = JacksonFactory.getDefaultInstance();

                Sheets mSheetsService = new Sheets.Builder(transport, jsonFactory, mCredential)
                        .setApplicationName(BuildConfig.APPLICATION_ID)
                        .build();

                String fileName = String.format(Locale.ENGLISH, "%s!%s", "data", "A2:L");

                try {
                    ValueRange valueRange = mSheetsService.spreadsheets().values()
                            .get(resourceId, fileName)
                            .execute();

                    List<List<Object>> values = valueRange.getValues();

                    if (values != null) {

                        final List<ContentValues> contentValues = new ArrayList<>();

                        int counter = 0;

                        for (List row : values) {

                            if (row.size() == StudentDAO.DEFAULT_INSERT_COLUMN_MAP.length) {

                                ContentValues insertValues = new ContentValues();

                                for (int index = 0; index < row.size(); index++) {
                                    insertValues.put(StudentDAO.DEFAULT_INSERT_COLUMN_MAP[index],
                                            row.get(index).toString().trim());
                                }

                                contentValues.add(insertValues);

                                counter++;

                                if (counter % 1000 == 0) {
                                    StudentDAO.getInstance().insertData(contentValues);
                                    contentValues.clear();
                                    counter = 0;
                                }
                            }
                        }

                        StudentDAO.getInstance().insertData(contentValues);

                        PartnerPrefUtil.setPartnerDataSynced(System.currentTimeMillis());


                        mHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                if (mDriveView != null) {
                                    mDriveView.showSuccessMessageAndRedirect(
                                            new Intent(mActivity, LandingActivity.class));
                                }
                            }
                        });
                    }
                } catch (final IOException ioException) {
                    if (ioException instanceof UserRecoverableAuthIOException) {

                        final UserRecoverableAuthIOException ex =
                                (UserRecoverableAuthIOException) ioException;
                        mHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                if (mDriveView != null) {
                                    mDriveView.requestDriveAuthorization(ex.getIntent(),
                                            REQUEST_AUTHORIZATION);
                                }
                            }
                        });

                    } else if (ioException instanceof GoogleJsonResponseException) {

                        final GoogleJsonResponseException ex =
                                (GoogleJsonResponseException) ioException;
                        mHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                if (mDriveView != null) {
                                    mDriveView.showErrorMessageDialog(ex.getDetails().getMessage());
                                }
                            }
                        });

                    } else {
                        mHandler.post((new Runnable() {
                            @Override
                            public void run() {
                                if (mDriveView != null) {
                                    mDriveView.showErrorMessageDialog(ioException.getMessage());
                                }
                            }
                        }));
                    }
                }

            }
        };

       new Thread(downloadAndSync).start();
    }


    /**
     * Checks whether the device currently has a network connection.
     * @return true if the device has a network connection, false otherwise.
     */
    private boolean isDeviceOnline() {
        ConnectivityManager connMgr =
                (ConnectivityManager) mActivity.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        return (networkInfo != null && networkInfo.isConnected());
    }

    /**
     * Check that Google Play services APK is installed and up to date.
     * @return true if Google Play Services is available and up to
     *     date on this device; false otherwise.
     */
    private boolean isGooglePlayServicesAvailable() {
        GoogleApiAvailability apiAvailability =
                GoogleApiAvailability.getInstance();
        final int connectionStatusCode =
                apiAvailability.isGooglePlayServicesAvailable(mActivity);
        return connectionStatusCode == ConnectionResult.SUCCESS;
    }
}
