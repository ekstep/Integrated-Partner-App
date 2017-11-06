package org.ekstep.ipa.ui.addchild.childfilter;

import org.ekstep.genie.base.BasePresenter;
import org.ekstep.genie.base.BaseView;

import java.util.HashMap;
import java.util.List;

/**
 * @author vinayagasundar
 */

public interface ChildFilterContract {

    interface View extends BaseView {
        void showDistrictData(List<String> districtData, String selectedValue);

        void showBlockData(List<String> blockData, String selectedValue);

        void showClusterData(List<String> clusterData, String selectedValue);

        void showSchoolData(HashMap<String, String> schoolData, String selectedId);

        void showClassData(List<String> classData, String selectedValue);

        void enableSearchButton();

        void disableSearchButton();

        void showChildSearchScreen(String schoolId, String klass);
    }

    interface Presenter extends BasePresenter {
        void getAllDistrict();

        void getAllBlockForDistrict(String district);

        void getAllClusterForBlock(String block);

        void getAllSchoolForCluster(String cluster);

        void getClassForSchool(String schoolId, String schoolName);

        void selectedClass(String klass);

        void handleSearchClick();
    }
}
