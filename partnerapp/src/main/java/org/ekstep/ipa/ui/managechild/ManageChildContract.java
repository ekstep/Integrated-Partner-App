package org.ekstep.ipa.ui.managechild;

import android.app.DatePickerDialog.OnDateSetListener;

import org.ekstep.genie.base.BasePresenter;
import org.ekstep.genie.base.BaseView;
import org.ekstep.ipa.model.Student;

/**
 * @author vinayagasundar
 */

public interface ManageChildContract {

    interface View extends BaseView {
        void showCalendar(OnDateSetListener onDateSetListener);

        void displayDob(String date);

        void showGenieHomeScreen();

        void enableSaveButton();

        void disableSaveButton();
    }

    interface Presenter extends BasePresenter {
        void start();

        void handleCalenderIconClick();

        void saveChild(Student student);
    }
}
