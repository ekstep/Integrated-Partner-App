package org.ekstep.genie.ui.settings.storagesettings;

import android.content.Context;

import org.ekstep.genie.base.BasePresenter;
import org.ekstep.genie.base.BaseView;
import org.ekstep.genieservices.commons.bean.MoveContentProgress;
import org.ekstep.genieservices.commons.bean.enums.ExistingContentAction;

/**
 * Created by Sneha on 9/17/2017.
 */

public class StorageSettingsContract {

    interface View extends BaseView {

        void displaySdcardAvailableSpace(String sdcardSize);

        void displaySdcardUsedSpace(String usedSize);

        void displaySdcardGenieSpace(String genieSize);

        void displaySdcardNotAvailable();

        void displayMobileAvailableSpace(String availableSize);

        void displayMobileUsedSpace(String usedSize);

        void displayMobileGenieSpace(String genieSize);

        void showExternalDeviceAsSelected();

        void showMobileDeviceAsSelected();

        void showLowSpaceInDeviceDialog();

        void showLowSpaceInSdcardDialog();

        void showMergeContentDialog();

        void disableExternalDevice();

        void showMoveContentProgressDialog(MoveContentProgress moveContentProgress);

        void dismissMoveContentDialog();

        void showLoaders();

        void hideLoaders();

        void showMoveReplaceAndDontMoveDialog(boolean showReplace, String path, boolean sdCardAsDefault);
    }

    interface Presenter extends BasePresenter {

        void calculateMobileNSdcardStorage(Context context);

        void setMobileDeviceAsDefault(boolean showReplace);

        void setExternalDeviceAsDefault(boolean showReplace);

        int selectAptDefaultStorageOption();

        void checkExternalStorageAvaibility();

        void handleOnMoveContentProgress(MoveContentProgress moveContentProgress);

        void switchSource(String path, boolean sdCardAsDefault);

        void mergeContents(ExistingContentAction existingContentAction, String path, boolean sdCardAsDefault);

        void deleteAndMoveContents(ExistingContentAction existingContentAction, String path, boolean sdCardAsDefault);
    }
}
