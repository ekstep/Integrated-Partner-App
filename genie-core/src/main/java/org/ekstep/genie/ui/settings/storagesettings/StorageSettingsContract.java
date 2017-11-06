package org.ekstep.genie.ui.settings.storagesettings;

import android.content.Context;

import org.ekstep.genie.base.BasePresenter;
import org.ekstep.genie.base.BaseView;
import org.ekstep.genieservices.commons.bean.MoveContentProgress;

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

        void showDialogToSetMobileDeviceAsDefault();

        void showDialogtoSetExternalDeviceAsDefault();

        void showExternalDeviceAsSelected();

        void showMobileDeviceAsSelected();

        void showLowSpaceInDeviceDialog();

        void showLowSpaceInSdcardDialog();

        void showMergeContentDialog();

        void disableExternalDevice();

        void showMoveContentProgressDialog(MoveContentProgress moveContentProgress);

        void dismissMoveContentDialog();
    }

    interface Presenter extends BasePresenter {

        void calculateSdcardStorageValue(Context context);

        void calculateMobileStorageValue(Context context);

        void setMobileDeviceAsDefault();

        void setExternalDeviceAsDefault();

        int selectAptDefaultStorageOption();

        void checkExternalStorageAvaibility();

        void handleOnMoveContentProgress(MoveContentProgress moveContentProgress);
    }
}
