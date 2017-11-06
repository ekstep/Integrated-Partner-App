package org.ekstep.genie.ui;

import android.support.test.filters.SdkSuppress;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.uiautomator.UiObjectNotFoundException;

import org.ekstep.genie.GenieTestBase;
import org.ekstep.genie.R;
import org.ekstep.genie.custom.action.ViewAction;
import org.ekstep.genie.custom.matcher.Matcher;
import org.ekstep.genie.ui.landing.LandingActivity;
import org.ekstep.genie.util.EcarCopyUtil;
import org.ekstep.genie.util.GenieDBHelper;
import org.junit.FixMethodOrder;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;

import java.io.IOException;

/**
 * Created by Swayangjit on 30-08-2016.
 */

@RunWith(AndroidJUnit4.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@SdkSuppress(minSdkVersion = 18)

public class _15_FileBrowserTest extends GenieTestBase {
    private static final int LAUNCH_TIMEOUT = 5000;

    private static final String FILE_EXPLORE_FOLDER_NAME = "_FileExploreTest";
    private static final String FILE_EXPLORE_ECAR = "Filebrowserecar.ecar";
    private static final String FILE_EXPLORE_EPAR = "Filebrowsertest.epar";
    private static final String ECAR_IDENTIFIER = "do_30014523";
    private static final String EPAR_UID = "01e7f4d8-3853-47eb-a6ce-d3a1ed9d2405";

    private static final String SAMPLE_ECAR = "FileExploreTest/Filebrowserecar.ecar";
    private static final String SAMPLE_EPAR = "FileExploreTest/Filebrowsertest.epar";
    private static final String SAMPLE_APK_ECAR = "Download/Treefrog Treasure-v2.0.ecar";

    private static final String TREE_FROG_TREASURE_ECAR_NAME = "Treefrog Treasure-v2.0.ecar";
    private static final String TREE_FROG_TREASURE_PACKAGE_NAME = "air.edu.washington.cs.treefrog";

    @Rule
    public ActivityTestRule<LandingActivity> mActivityRule = new ActivityTestRule<>(LandingActivity.class);


    @Override
    public void setup() throws IOException {
        super.setup();
    }

    @Test
    public void test0CopyNeccesoryFiles() {

        EcarCopyUtil.createFileFromAsset(SAMPLE_ECAR, DESTINATION);
        EcarCopyUtil.createFileFromAsset(SAMPLE_EPAR, DESTINATION);
        EcarCopyUtil.createFileFromAsset(SAMPLE_APK_ECAR, DESTINATION);
    }

    @Test
    public void test1ShouldNavigateToFileBrowserAndImportEcarSuccessfully() {
        ViewAction.navaigateToDrawerItems(3);
        //click on Import lessson
        ViewAction.clickonView(R.id.menu_import_btn);

        //click on view
        ViewAction.clickonView(R.id.import__btn__file_explorer);
//
        //Click on device_storage
        ViewAction.clickonString(R.string.title_fileexplorer_device_storage);

        //Click on the folder
        ViewAction.clickOnRecylerViewItemText(FILE_EXPLORE_FOLDER_NAME);

        //Click on Ecar file
        ViewAction.clickOnRecylerViewItemText(FILE_EXPLORE_ECAR);

        //Assert for Succcess message
        Matcher.isToastMessageDisplayed(R.string.msg_import_success);

        //Delete the content
        deleteContent(ECAR_IDENTIFIER);
    }

    @Test
    public void test2ShouldNavigateToFileBrowserAndImportEparSuccessfully() {

        GenieDBHelper.deleteAllUserProfile();
        ViewAction.navaigateToDrawerItems(2);

        //Click on "ImportProfile"
        ViewAction.clickonView(R.id.layout_more);

        //click on view
        ViewAction.clickonView(R.id.import__btn__file_explorer);
//
        //Click on device_storage
        ViewAction.clickonString(R.string.title_fileexplorer_device_storage);

        //Click on the folder
        ViewAction.clickOnRecylerViewItemText(FILE_EXPLORE_FOLDER_NAME);

        //Click on Epar file
        ViewAction.clickOnRecylerViewItemText(FILE_EXPLORE_EPAR);

        String successMessage = "1 " + mActivityRule.getActivity().getString(R.string.msg_import_successfull);

        //Assert for "ImportProfile successful" string
        Matcher.isToastMessageDisplayed(successMessage);

        //Delete the child
        deleteProfile(EPAR_UID);

        ViewAction.clickOnRecylerViewItemWithAllof(R.id.recylerview_files, 0);

    }

    @Test
    public void test3ShouldImportAPKContentAndInstallAPKS() throws UiObjectNotFoundException {

        ViewAction.navaigateToDrawerItems(3);


        //Click on "ImportProfile"
        ViewAction.clickonView(R.id.menu_import_btn);

        //click on view
        ViewAction.clickonView(R.id.import__btn__file_explorer);
//
        //Click on device_storage
        ViewAction.clickonString(R.string.title_fileexplorer_device_storage);

        //Click on the folder
        ViewAction.clickOnRecylerViewItemText(FILE_EXPLORE_FOLDER_NAME);


        //Click on Ecar file
        ViewAction.clickOnRecylerViewItemText(TREE_FROG_TREASURE_ECAR_NAME);

        //Click on Install button
        installApk();

        // Uninstall  the apk from device
        unInstallApk(mActivityRule.getActivity(), TREE_FROG_TREASURE_PACKAGE_NAME);

    }

}
