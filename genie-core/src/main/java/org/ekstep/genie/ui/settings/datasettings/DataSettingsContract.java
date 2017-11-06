package org.ekstep.genie.ui.settings.datasettings;

import org.ekstep.genie.base.BasePresenter;
import org.ekstep.genie.base.BaseView;

/**
 * Created by Sneha on 9/16/2017.
 */

public class DataSettingsContract {

    interface View extends BaseView {

        void displayLastSyncTime(String timeInMillis);

        void displayCurrentSyncConfiguration();

        void showSyncDialog();

        void showSyncFailedMsg(String errorMsg);

        void showInternetConnectivityError();

        boolean isAdded();
    }

    interface Presenter extends BasePresenter {

        void getLastSyncTime();

        void handleSyncNow();
    }
}
