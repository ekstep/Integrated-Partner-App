package org.ekstep.ipa.ui.addchild.childfilter;

import android.content.Context;
import android.text.TextUtils;

import org.ekstep.genie.base.BaseView;
import org.ekstep.genie.util.preference.PreferenceUtil;
import org.ekstep.ipa.db.StudentDAO;
import org.ekstep.ipa.util.preference.PartnerPrefUtil;

import java.util.HashMap;
import java.util.List;

/**
 * @author vinayagasundar
 */

public class ChildFilterPresenter implements ChildFilterContract.Presenter {


    private ChildFilterContract.View mChildFilterView;

    private String mSelectedDistrict;
    private String mSelectedBlock;
    private String mSelectedCluster;
    private String mSelectedSchoolId;
    private String mSelectedSchool;
    private String mSelectedClass;

    public ChildFilterPresenter() {
        mSelectedDistrict = PartnerPrefUtil.getDistrict();
        mSelectedBlock = PartnerPrefUtil.getBlock();
        mSelectedCluster = PartnerPrefUtil.getCluster();
        mSelectedSchoolId = PartnerPrefUtil.getSchoolId();
        mSelectedSchool = PartnerPrefUtil.getSchool();
        mSelectedClass = PartnerPrefUtil.getStudentClass();
    }

    @Override
    public void bindView(BaseView view, Context context) {
        mChildFilterView = (ChildFilterContract.View) view;
    }

    @Override
    public void unbindView() {
        mChildFilterView = null;
    }

    @Override
    public void getAllDistrict() {
        List<String> allDistrict = StudentDAO.getInstance()
                .getUniqueFieldData(StudentDAO.COLUMN_DISTRICT, "Please Select District");

        mChildFilterView.showDistrictData(allDistrict, mSelectedDistrict);

        isValid();
    }

    @Override
    public void getAllBlockForDistrict(String district) {
        mSelectedDistrict = district;

        List<String> allBlock = StudentDAO.getInstance()
                .getUniqueFieldData(StudentDAO.COLUMN_BLOCK, "Please Select Block",
                        StudentDAO.COLUMN_DISTRICT, mSelectedDistrict);
        mChildFilterView.showBlockData(allBlock, mSelectedBlock);

        isValid();
    }

    @Override
    public void getAllClusterForBlock(String block) {
        mSelectedBlock = block;

        List<String> allCluster = StudentDAO.getInstance()
                .getUniqueFieldData(StudentDAO.COLUMN_CLUSTER, "Please Select Cluster",
                        StudentDAO.COLUMN_DISTRICT, StudentDAO.COLUMN_BLOCK,
                        mSelectedDistrict, mSelectedBlock);

        mChildFilterView.showClusterData(allCluster, mSelectedCluster);

        isValid();
    }

    @Override
    public void getAllSchoolForCluster(String cluster) {
        mSelectedCluster = cluster;

        HashMap<String, String> schoolData = StudentDAO.getInstance()
                .getAllSchool(mSelectedDistrict, mSelectedBlock, mSelectedCluster);

        mChildFilterView.showSchoolData(schoolData, mSelectedSchoolId);

        isValid();
    }

    @Override
    public void getClassForSchool(String schoolId, String schoolName) {
        mSelectedSchoolId = schoolId;
        mSelectedSchool = schoolName;

        List<String> allClass = StudentDAO.getInstance()
                .getUniqueFieldData(StudentDAO.COLUMN_CLASS, "Please select class",
                        StudentDAO.COLUMN_SCHOOL_CODE, mSelectedSchoolId);

        mChildFilterView.showClassData(allClass, mSelectedClass);
        isValid();
    }

    @Override
    public void selectedClass(String klass) {
        mSelectedClass = klass;
        isValid();
    }

    @Override
    public void handleSearchClick() {
        PartnerPrefUtil.setDistrict(mSelectedDistrict);
        PartnerPrefUtil.setBlock(mSelectedBlock);
        PartnerPrefUtil.setCluster(mSelectedCluster);
        PartnerPrefUtil.setSchoolId(mSelectedSchoolId);
        PartnerPrefUtil.setStudentClass(mSelectedClass);
        PartnerPrefUtil.setSchool(mSelectedSchool);


        mChildFilterView.showChildSearchScreen(mSelectedSchoolId, mSelectedClass);
    }

    private void isValid() {
        if (TextUtils.isEmpty(mSelectedDistrict) || TextUtils.isEmpty(mSelectedBlock)
                || TextUtils.isEmpty(mSelectedCluster) || TextUtils.isEmpty(mSelectedSchoolId)
                || TextUtils.isEmpty(mSelectedClass)) {
            mChildFilterView.disableSearchButton();
            return;
        }


        if (isPlaceHolderValue(mSelectedDistrict) || isPlaceHolderValue(mSelectedBlock)
                || isPlaceHolderValue(mSelectedCluster) || isPlaceHolderValue(mSelectedSchoolId)
                || isPlaceHolderValue(mSelectedClass)) {
            mChildFilterView.disableSearchButton();
            return;
        }


        mChildFilterView.enableSearchButton();
    }

    private boolean isPlaceHolderValue(String value) {
        return value.toLowerCase().contains("please select");
    }


}
