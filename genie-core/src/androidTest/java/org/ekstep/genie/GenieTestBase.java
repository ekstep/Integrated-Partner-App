package org.ekstep.genie;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.os.RemoteException;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.Espresso;
import android.support.test.espresso.IdlingPolicies;
import android.support.test.espresso.IdlingResource;
import android.support.test.uiautomator.By;
import android.support.test.uiautomator.UiDevice;
import android.support.test.uiautomator.UiObject;
import android.support.test.uiautomator.UiObjectNotFoundException;
import android.support.test.uiautomator.UiSelector;
import android.support.test.uiautomator.Until;

import org.ekstep.genie.custom.idlingResource.ElapsedTimeIdlingResource;
import org.ekstep.genie.util.CommonUtil;
import org.ekstep.genie.util.GenieDBHelper;
import org.ekstep.genie.util.MockServer;
import org.ekstep.genie.util.geniesdk.ImportExportUtil;
import org.ekstep.genieservices.async.UserService;
import org.ekstep.genieservices.commons.AndroidAppContext;
import org.ekstep.genieservices.commons.AppContext;
import org.ekstep.genieservices.commons.IResponseHandler;
import org.ekstep.genieservices.commons.bean.ContentDeleteRequest;
import org.ekstep.genieservices.commons.bean.GenieResponse;
import org.ekstep.genieservices.commons.bean.Profile;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * Created by swayangjit on 2/4/17.
 */
// TODO: 20/7/17 Changes are made to this test class, check if it requies re-implmentation

public class GenieTestBase {

    protected static final String DESTINATION = Environment.getExternalStorageDirectory().toString() + File.separator + "GenieTestDump";
    protected static final long TIMEOUT = 10000;
    protected static final String TEXT_ALLOW = "Allow";
    protected static final String SELECT_LANGUAGE = "SELECT LANGUAGE";
    protected String SAMPLE_ECAR = "Download/Multiplication2.ecar";
    protected String SAMPLE_IDENTIFIER = "org.ekstep.money.worksheet";
    protected String CONTENT_IDENTIFIER = "do_30013486";
    protected MockServer mMockServer;
    protected String[] mPermissions = {
            android.Manifest.permission.CAMERA,
            android.Manifest.permission.READ_EXTERNAL_STORAGE,
            android.Manifest.permission.ACCESS_FINE_LOCATION};
    private IdlingResource idlingResource;

    @Before
    public void setup() throws IOException {
        CommonUtil.stopAutoSyncing();
        IdlingPolicies.setMasterPolicyTimeout(60, TimeUnit.SECONDS);
        IdlingPolicies.setIdlingResourceTimeout(26, TimeUnit.SECONDS);
        AppContext appContext = AndroidAppContext.buildAppContext(CoreApplication.getInstance(), "org.ekstep.genie");
        GenieDBHelper.init(appContext);
    }


    @After
    public void tearDown() throws IOException {

    }

    /**
     * Make sure to call any esspresso view checks after this method call or else it will fail to wait.
     */
    protected void waitFor(long time) {

        // Make sure Espresso does not time out
        IdlingPolicies.setMasterPolicyTimeout(time * 2, TimeUnit.MILLISECONDS);
        IdlingPolicies.setIdlingResourceTimeout(time * 2, TimeUnit.MILLISECONDS);

        idlingResource = new ElapsedTimeIdlingResource(time);
        Espresso.registerIdlingResources(idlingResource);
    }

    protected void clearResource() {
        Espresso.unregisterIdlingResources(idlingResource);
    }

    /**
     * Starts the mock server.
     */
    protected void startMockServer() {
        try {
            mMockServer = new MockServer();
            mMockServer.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Stops the mock server.
     */
    protected void shutDownMockServer() {
        try {
            if (mMockServer != null) {
                mMockServer.shutDown();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected void grantPermission(UiDevice device) throws UiObjectNotFoundException {
        UiObject permissionEntry = device.findObject(new UiSelector().text(TEXT_ALLOW).className("android.widget.Button"));
        permissionEntry.click();
    }


    protected void deleteProfile(String uid) {
        CoreApplication.getGenieAsyncService().getUserService().deleteUser(uid, new IResponseHandler<Void>() {
            @Override
            public void onSuccess(GenieResponse<Void> genieResponse) {

            }

            @Override
            public void onError(GenieResponse<Void> genieResponse) {

            }
        });
    }

    protected void deleteContent(String identifier) {
        ContentDeleteRequest request = new ContentDeleteRequest.Builder().contentId(identifier).build();
        CoreApplication.getGenieAsyncService().getContentService().deleteContent(request, new IResponseHandler<Void>() {
            @Override
            public void onSuccess(GenieResponse<Void> genieResponse) {

            }

            @Override
            public void onError(GenieResponse<Void> genieResponse) {

            }
        });
    }


    protected void importContent(Context context, String filePath) {
        ImportExportUtil.importContent(context, filePath, new ImportExportUtil.IImport() {
            @Override
            public void onImportSuccess() {
                junit.framework.Assert.assertTrue(true);
            }

            @Override
            public void onImportFailure() {
            }

            @Override
            public void onOutDatedEcarFound() {

            }
        });
    }

    /**
     * Delete the collection content if it exists.
     */
    protected void removeContent(String identifier) {
        ContentDeleteRequest.Builder request = new ContentDeleteRequest.Builder();
        request.contentId(identifier);
        CoreApplication.getGenieAsyncService().getContentService().deleteContent(request.build(), new IResponseHandler<Void>() {
            @Override
            public void onSuccess(GenieResponse<Void> genieResponse) {
                junit.framework.Assert.assertTrue(true);
            }

            @Override
            public void onError(GenieResponse<Void> genieResponse) {

            }
        });
    }

    protected void addSingleChild() {
        Profile profile1 = new Profile("test", "@drawable/ic_avatar2", "en");
        UserService userService = CoreApplication.getGenieAsyncService().getUserService();
        userService.createUserProfile(profile1, new IResponseHandler<Profile>() {
            @Override
            public void onSuccess(GenieResponse<Profile> genieResponse) {

            }

            @Override
            public void onError(GenieResponse<Profile> genieResponse) {

            }
        });
    }

    protected void addGroupUser() {
        Profile profile1 = new Profile("test", "@drawable/ic_avatar1", "en", -1, "");
        Profile profile2 = new Profile("test", "@drawable/ic_avatar1", "en", -1, "");
        profile1.setGroupUser(true);
        profile2.setGroupUser(true);
        UserService userService = CoreApplication.getGenieAsyncService().getUserService();
        userService.createUserProfile(profile1, new IResponseHandler<Profile>() {
            @Override
            public void onSuccess(GenieResponse<Profile> genieResponse) {

            }

            @Override
            public void onError(GenieResponse<Profile> genieResponse) {

            }
        });
        userService.createUserProfile(profile2, new IResponseHandler<Profile>() {
            @Override
            public void onSuccess(GenieResponse<Profile> genieResponse) {

            }

            @Override
            public void onError(GenieResponse<Profile> genieResponse) {

            }
        });
    }

    protected void installApk() throws UiObjectNotFoundException {
        // Initialize UiDevice instance
        UiDevice uiDevice = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());

        Assert.assertNotNull(uiDevice);

        int i = 0;
        while (i < 2) {

            uiDevice.wait(Until.hasObject(By.text("Install")), 5000);
            UiObject installButton = null;
            try {
                installButton = uiDevice.findObject(new UiSelector().text("Install").className("android.widget.Button"));
            } catch (Exception e) {
                break;
            }

            // Simulate a user-click on the install button, if found.
            if (installButton.exists() && installButton.isEnabled()) {
                installButton.click();
            }

            // Wait for the app to install
            uiDevice.wait(Until.hasObject(By.text("Done")), 15000);

            UiObject doneButton = uiDevice.findObject(new UiSelector().text("Done").className("android.widget.Button"));
            if (doneButton.exists() && doneButton.isEnabled()) {
                doneButton.click();
            }

            i++;
        }

        uiDevice.pressBack();
    }

    protected void unInstallApk(Context context, String packageName) throws UiObjectNotFoundException {
        Uri packageURI = Uri.parse("package:" + packageName);
        Intent uninstallIntent = new Intent(Intent.ACTION_DELETE, packageURI);
        context.startActivity(uninstallIntent);

        // Initialize UiDevice instance
        UiDevice uiDevice = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());

        uiDevice.wait(Until.hasObject(By.text("OK")), 5000);
        UiObject okButton = null;
        try {
            okButton = uiDevice.findObject(new UiSelector().text("OK").className("android.widget.Button"));
        } catch (Exception e) {
        }
        if (okButton.exists() && okButton.isEnabled()) {
            okButton.click();
        }

        uiDevice.waitForIdle(TIMEOUT);
    }

    protected void launchAppUsingPkgName(String packageName, UiDevice uiDevice) {
        // Launch Genie app
        Context context = InstrumentationRegistry.getContext();
        final Intent intent = context.getPackageManager()
                .getLaunchIntentForPackage(packageName);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        context.startActivity(intent);

        // Wait for the app to appear
        uiDevice.wait(Until.hasObject(By.pkg(packageName).depth(0)), TIMEOUT);
    }


    protected void removeAppsFromRecents(UiDevice uiDevice) throws RemoteException {
        uiDevice.pressHome();

        uiDevice.pressRecentApps();

        uiDevice.wait(Until.hasObject(By.desc("Dismiss Genie.").pkg("com.android.systemui")), TIMEOUT);

        UiObject uiObject1 = uiDevice.findObject(new UiSelector().description("Dismiss Genie.").packageName("com.android.systemui").className("android.widget.ImageView"));

        try {
            uiObject1.click();
        } catch (UiObjectNotFoundException e) {
            e.printStackTrace();
        }

        CoreApplication.getInstance().setGenieAlive(-1);

    }

}
