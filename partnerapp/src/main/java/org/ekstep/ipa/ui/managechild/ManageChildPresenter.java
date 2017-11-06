package org.ekstep.ipa.ui.managechild;

import android.app.DatePickerDialog;
import android.content.Context;
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.widget.DatePicker;

import org.ekstep.genie.base.BaseView;
import org.ekstep.genieservices.GenieService;
import org.ekstep.genieservices.commons.bean.GenieResponse;
import org.ekstep.genieservices.commons.bean.PartnerData;
import org.ekstep.genieservices.commons.bean.Profile;
import org.ekstep.genieservices.commons.utils.GsonUtil;
import org.ekstep.ipa.PartnerApp;
import org.ekstep.ipa.db.StudentDAO;
import org.ekstep.ipa.model.Partner;
import org.ekstep.ipa.model.Student;
import org.ekstep.ipa.util.preference.PartnerPrefUtil;


import java.util.Calendar;

/**
 * @author vinayagasundar
 */

public class ManageChildPresenter implements ManageChildContract.Presenter,
        DatePickerDialog.OnDateSetListener {


    private ManageChildContract.View mChildView;

    private String mDob;

    @Override
    public void bindView(BaseView view, Context context) {
        mChildView = (ManageChildContract.View) view;
    }

    @Override
    public void unbindView() {
        mChildView = null;
    }

    @Override
    public void start() {

    }

    @Override
    public void handleCalenderIconClick() {
        mChildView.showCalendar(this);
    }



    @Override
    public void saveChild(Student student) {
        student.setDob(mDob);
        student.setDistrict(PartnerPrefUtil.getDistrict());
        student.setBlock(PartnerPrefUtil.getBlock());
        student.setCluster(PartnerPrefUtil.getCluster());
        student.setSchoolCode(PartnerPrefUtil.getSchoolId());
        student.setSchoolName(PartnerPrefUtil.getSchool());
        student.setStudentClass(PartnerPrefUtil.getStudentClass());

        createGenieChild(student);
    }

    @Override
    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, monthOfYear);
        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

        mDob = (String) DateFormat.format("dd-MM-yyyy", calendar);

        mChildView.displayDob(mDob);
    }


    private void createGenieChild(Student student) {
        Partner partner = PartnerApp.getPartnerApp().getPartnerConfig().getPartner();

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

            StudentDAO.getInstance().insertData(student);
            mChildView.showGenieHomeScreen();
        }
    }
}
