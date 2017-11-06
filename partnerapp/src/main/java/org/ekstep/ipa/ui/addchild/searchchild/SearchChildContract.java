package org.ekstep.ipa.ui.addchild.searchchild;

import org.ekstep.genie.base.BasePresenter;
import org.ekstep.genie.base.BaseView;
import org.ekstep.ipa.adapter.ChildrenAdapter;
import org.ekstep.ipa.model.Student;

import java.util.List;


/**
 * @author vinayagasundar
 */

public interface SearchChildContract {

    interface View extends BaseView {
        void showAllChildren(List<Student> studentList,
                             ChildrenAdapter.OnSelectedChildListener onSelectedChildListener);

        void updateChildren(List<Student> studentList);

        void enableAddChild();

        void disableAddChild();

        void showCreateChild();

        void hideCreateChild();

        void showCreateChildScreen();
    }


    interface Presenter extends BasePresenter {
        void init(String schoolId, String klass);

        void getAllChildren();

        void searchOnChildren(String searchText);

        void handleAddChild();

        void showCreateChild();
    }
}
