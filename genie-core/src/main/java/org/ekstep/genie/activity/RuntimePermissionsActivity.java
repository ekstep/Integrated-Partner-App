package org.ekstep.genie.activity;

import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.SparseArray;

import java.util.ArrayList;
import java.util.List;

/**
 * This is used to ask permissions at runtime on android SDK_VERSION 23 and above.
 */
public abstract class RuntimePermissionsActivity extends AppCompatActivity {
    public static final int REQUEST_PERMISSION = 111;
    public static final int REQUEST_PERMISSION_IMPORT_CONTENT = 112;
    public static final int REQUEST_PERMISSION_IMPORT_PROFILE = 113;
    private SparseArray mDescription;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mDescription = new SparseArray();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        List<String> permissionsDenied = new ArrayList<String>();
        int permissionCheck = PackageManager.PERMISSION_GRANTED;

        for (int i = 0; i < permissions.length; i++) {
            int permission = grantResults[i];
            if (permission != PackageManager.PERMISSION_GRANTED) {
                permissionsDenied.add(permissions[i]);
            }
            permissionCheck = permissionCheck + permission;
        }

        if ((grantResults.length > 0) && permissionCheck == PackageManager.PERMISSION_GRANTED) {
            onPermissionsGranted(requestCode);
        } else {
            onPermissionsDenied(requestCode, permissionsDenied);
        }
    }


    /**
     * request a permissions at runtime.
     *
     * @param requestedPermissions
     * @param description
     * @param requestCode
     */
    public void requestAppPermissions(final String[] requestedPermissions, final String description, final int requestCode) {

        if (Build.VERSION.SDK_INT < 23) {
            onPermissionsGranted(requestCode);
            return;
        }
        mDescription.put(requestCode, description);
        List<String> permissionsList = new ArrayList<String>();

        int permissionCheck = PackageManager.PERMISSION_GRANTED;
        boolean shouldShowRequestPermissionRationale = false;
        for (String permission : requestedPermissions) {
            int permissionNeedCheck = ContextCompat.checkSelfPermission(this, permission);
            if (permissionNeedCheck != PackageManager.PERMISSION_GRANTED) {
                permissionsList.add(permission);
            }
            permissionCheck = permissionCheck + permissionNeedCheck;
            shouldShowRequestPermissionRationale = shouldShowRequestPermissionRationale || ActivityCompat.shouldShowRequestPermissionRationale(this, permission);
        }

        String[] permissionsNeeded = new String[permissionsList.size()];
        permissionsNeeded = permissionsList.toArray(permissionsNeeded);

        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            if (shouldShowRequestPermissionRationale) {
                ActivityCompat.requestPermissions(this, permissionsNeeded, requestCode);
            } else {
                ActivityCompat.requestPermissions(this, permissionsNeeded, requestCode);
            }
        } else {
            onPermissionsGranted(requestCode);
        }
    }

    public abstract void onPermissionsGranted(int requestCode);

    public abstract void onPermissionsDenied(int requestCode, List<String> deniedPermissions);

}