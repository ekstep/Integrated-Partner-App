package org.ekstep.ipa.ui.addchild.searchchild;

import android.content.Context;
import android.text.TextUtils;

import org.ekstep.genie.base.BaseView;
import org.ekstep.genieservices.GenieService;
import org.ekstep.genieservices.commons.bean.GenieResponse;
import org.ekstep.genieservices.commons.bean.PartnerData;
import org.ekstep.genieservices.commons.bean.Profile;
import org.ekstep.genieservices.commons.utils.GsonUtil;
import org.ekstep.ipa.PartnerApp;
import org.ekstep.ipa.adapter.ChildrenAdapter;
import org.ekstep.ipa.db.StudentDAO;
import org.ekstep.ipa.model.Partner;
import org.ekstep.ipa.model.Student;

import java.util.ArrayList;
import java.util.List;

/**
 * @author vinayagasundar
 */

public class SearchChildPresenter
        implements SearchChildContract.Presenter, ChildrenAdapter.OnSelectedChildListener {

    private SearchChildContract.View mSearchChildView;

    private List<Student> mSelectedStudent;

    private String mSchoolId;

    private String mKlass;

    public SearchChildPresenter() {
        mSelectedStudent = new ArrayList<>();
    }

    @Override
    public void bindView(BaseView view, Context context) {
        mSearchChildView = (SearchChildContract.View) view;
    }

    @Override
    public void unbindView() {
        mSearchChildView = null;
    }

    @Override
    public void init(String schoolId, String klass) {
        mSchoolId = schoolId;
        mKlass = klass;
    }

    @Override
    public void getAllChildren() {
        if (TextUtils.isEmpty(mSchoolId) || TextUtils.isEmpty(mKlass)) {
            return;
        }

        mSelectedStudent.clear();

        List<Student> studentList = StudentDAO.getInstance().getAllStudent(mSchoolId, mKlass);
        mSearchChildView.hideCreateChild();

        if (studentList != null && studentList.size() > 0) {
            mSearchChildView.showAllChildren(studentList, this);
        }
    }

    @Override
    public void searchOnChildren(String searchText) {
        if (TextUtils.isEmpty(searchText) || TextUtils.isEmpty(mSchoolId)
                || TextUtils.isEmpty(mKlass)) {
            return;
        }

        List<Student> studentList = StudentDAO.getInstance()
                .searchOnStudent(mSchoolId, mKlass, searchText);

        if (studentList != null && studentList.size() > 0) {
            mSearchChildView.hideCreateChild();
            mSearchChildView.updateChildren(studentList);
        } else {
            mSearchChildView.showCreateChild();
        }
    }

    @Override
    public void handleAddChild() {

        Partner partner = PartnerApp.getPartnerApp().getPartnerConfig().getPartner();

        for (Student student : mSelectedStudent) {
            String handle = student.getName();
            String gender = student.getGender();
            Profile profile = new Profile(handle, "Avatar","en");

            if (!TextUtils.isEmpty(gender)) {
                profile.setGender(gender);
            }

            GenieResponse<Profile> profileGenieResponse = GenieService.getService().getUserService()
                    .createUserProfile(profile);

            if (profileGenieResponse != null && profileGenieResponse.getResult() != null) {
                String uid = profileGenieResponse.getResult().getUid();
                student.setUid(uid);

                PartnerData partnerData = new PartnerData(null, null, partner.getId(),
                        GsonUtil.toJson(student), partner.getPublicKey());
                GenieService.getService().getPartnerService().sendData(partnerData);

                StudentDAO.getInstance().updateStudentUID(student.getStudentId(), student.getUid());
            }
        }
    }

    @Override
    public void onChildSelected(Student student) {
        addOrRemoveStudent(student, false);
    }

    @Override
    public void onChildUnSelected(Student student) {
        addOrRemoveStudent(student, true);
    }

    @Override
    public void showCreateChild() {
        mSearchChildView.showCreateChildScreen();
    }

    private void addOrRemoveStudent(Student student, boolean isRemove) {
        if (isRemove) {
            mSelectedStudent.remove(student);
        } else {
            mSelectedStudent.add(student);
        }

        if (mSelectedStudent.size() > 0) {
            mSearchChildView.enableAddChild();
        } else {
            mSearchChildView.disableAddChild();
        }
    }
}
