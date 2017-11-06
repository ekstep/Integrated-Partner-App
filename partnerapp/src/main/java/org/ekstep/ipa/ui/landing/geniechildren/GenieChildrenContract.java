package org.ekstep.ipa.ui.landing.geniechildren;

import org.ekstep.genie.base.BasePresenter;
import org.ekstep.genie.base.BaseView;
import org.ekstep.ipa.adapter.ChildrenAdapter;
import org.ekstep.ipa.model.Student;

import java.util.List;


/**
 * @author vinayagasundar
 */

public interface GenieChildrenContract {


    interface View extends BaseView {
        void setGenieChildListAdapter(List<Student> studentList,
                                      ChildrenAdapter.OnSelectedChildListener onSelectedChildListener);

        void showLoader();

        void hideLoader();

        void showEmptyView();

        void hideEmptyView();

        void enableBrowseLesson();

        void disableBrowseLesson();

        void showGenieHomeScreen();
    }

    interface Presenter extends BasePresenter {
        void getAllGenieChildren();

        void launchGenieForSelectedStudent();
    }
}
