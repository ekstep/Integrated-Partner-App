package org.ekstep.genie.ui.settings;

import org.ekstep.genie.base.BasePresenter;
import org.ekstep.genie.base.BaseView;

/**
 * Created by swayangjit on 14/9/17.
 */

public class SettingsContract {

    interface View extends BaseView {

        void showSettingsList(int selectedPosition);

        void navigateTo(int position);

    }

    interface Presenter extends BasePresenter {

        void handleSettingsListItemClick(int position);

    }
}
