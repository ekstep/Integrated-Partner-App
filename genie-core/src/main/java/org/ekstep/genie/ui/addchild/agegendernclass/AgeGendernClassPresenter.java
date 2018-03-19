package org.ekstep.genie.ui.addchild.agegendernclass;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;

import org.ekstep.genie.CoreApplication;
import org.ekstep.genie.base.BaseView;
import org.ekstep.genie.telemetry.EnvironmentId;
import org.ekstep.genie.telemetry.TelemetryBuilder;
import org.ekstep.genie.telemetry.TelemetryHandler;
import org.ekstep.genie.telemetry.TelemetryStageId;
import org.ekstep.genie.telemetry.enums.ImpressionType;
import org.ekstep.genie.util.Constant;
import org.ekstep.genie.util.LogUtil;
import org.ekstep.genieservices.IConfigService;
import org.ekstep.genieservices.commons.bean.GenieResponse;
import org.ekstep.genieservices.commons.bean.MasterData;
import org.ekstep.genieservices.commons.bean.MasterDataValues;
import org.ekstep.genieservices.commons.bean.Profile;
import org.ekstep.genieservices.commons.bean.enums.MasterDataType;
import org.ekstep.genieservices.commons.utils.StringUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created on 22/12/16.
 *
 * @author swayangjit
 */
public class AgeGendernClassPresenter implements AgeGendernClassContract.Presenter {

    static final int GENDER_MALE = 0;
    static final int GENDER_FEMALE = 1;

    private static final String TAG = "AgeGendernClassPresenter";
    HashMap<String, String> mClassMap = new LinkedHashMap<String, String>() {{
        put("KG", "0");
        put("Grade 1", "1");
        put("Grade 2", "2");
        put("Grade 3", "3");
        put("Grade 4", "4");
        put("Grade 5", "5");
        put("Grade 6", "6");
        put("Grade 7", "7");
        put("Grade 8", "8");
        put("Grade 9", "9");
        put("Grade 10", "10");
        put("Grade 11", "11");
        put("Grade 12", "12");
        put("Others", "-1");
    }};
    @NonNull
    private AgeGendernClassContract.View mAgeView;
    private boolean mIsGroup;
    private Profile mProfile = null;
    private boolean mIsEditMode = false;
    private List<String> mAgeList;
    private List<String> mClassList;
    private int mSelectedAge = -1;
    private int mSelectedClass = -1;
    private int mSelectedGender = -1;
    private Map<String, String> mAgeMap = null;
    private boolean isAgeMarked = false;
    private boolean isGenderMarked = false;
    private boolean isClassMarked = false;

    public AgeGendernClassPresenter() {

    }

    @Override
    public void fetchBundleExtras(Bundle bundle) {
        if (bundle != null) {
            mIsGroup = bundle.getBoolean(Constant.BUNDLE_KEY_MODE);
            mProfile = (Profile) bundle.getSerializable(Constant.BUNDLE_UPDATED_PROFILE);
            mIsEditMode = bundle.getBoolean(Constant.BUNDLE_EDIT_MODE);
        }
    }

    @Override
    public void openAgeLayout() {

        TelemetryHandler.saveTelemetry(TelemetryBuilder.buildImpressionEvent(EnvironmentId.HOME, TelemetryStageId.ADD_CHILD_AGE_GENDER_CLASS, ImpressionType.VIEW));

        mClassList = new ArrayList<>(mClassMap.keySet());

        LogUtil.i(TAG, mProfile.getHandle());

        IConfigService configService = CoreApplication.getGenieSdkInstance().getConfigService();

        GenieResponse<MasterData> ageResponse = configService.getMasterData(MasterDataType.AGE);

        if (ageResponse != null && ageResponse.getStatus()) {
            MasterData masterData = ageResponse.getResult();

            //get all the age labels of the MasterDataValues into a list
            if (masterData != null && masterData.getValues().size() > 0) {
                mAgeList = new ArrayList<>();
                mAgeMap = new HashMap<>();
                for (MasterDataValues masterDataValue :
                        masterData.getValues()) {
                    mAgeList.add(masterDataValue.getLabel());
                    mAgeMap.put(masterDataValue.getLabel(), masterDataValue.getValue());
                }

                //if the profile is in edit mode, then get the board label from already prepared list of board
                if (mIsEditMode || mProfile != null) {
                    setAge(String.valueOf(mProfile.getAge()));
                    setStandard(String.valueOf(mProfile.getStandard()));
                    setGender(mProfile.getGender());
                }
            }
        }
    }

    private void setStandard(String standard) {
        for (String object : mClassMap.keySet()) {
            if (object != null && mClassMap.get(object).equalsIgnoreCase(standard)) {
                isClassMarked = true;
                mAgeView.setStandard(object);
            }
        }
    }

    private void setAge(String age) {
        for (String object : mAgeMap.keySet()) {
            if (object != null && mAgeMap.get(object).equalsIgnoreCase(age)) {
                isAgeMarked = true;
                mAgeView.setAge(object);
            }
        }
    }

    private void setGender(String gender) {
        if (gender != null) {
            if (gender.equalsIgnoreCase("male")) {
                toggleGender(GENDER_MALE);
            } else if (gender.equalsIgnoreCase("female")) {
                toggleGender(GENDER_FEMALE);
            }
        }
    }

    @Override
    public Profile getAgeGendernClass() {
        return mProfile;
    }

    @Override
    public void handleAgeClick(View view) {
        mAgeView.showAgeListPopup(mAgeList, view);
    }

    @Override
    public void handleAgeItemClick(int position) {
        String age = mAgeList.get(position);
        mAgeView.setAge(mAgeList.get(position));

        if (mAgeMap != null && !StringUtil.isNullOrEmpty(mAgeMap.get(age))) {
            mSelectedAge = Integer.valueOf(mAgeMap.get(age));
        }

        mProfile.setAge(mSelectedAge);

        //check if it is in edit mode
        if (!mIsEditMode) {
            isAgeMarked = true;

            if (isAgeMarked && isGenderMarked && isClassMarked) {
                //start the animation
                mAgeView.showNextButtonAnimation();
            }
        }
    }

    @Override
    public void handleClassClick(View view) {
        mAgeView.showClassListPopup(mClassList, view);
    }

    @Override
    public void handleClassItemClick(int position) {
        String standard = mClassList.get(position);
        mAgeView.setStandard(mClassList.get(position));

        if (mClassMap != null && !StringUtil.isNullOrEmpty(mClassMap.get(standard))) {
            mSelectedClass = Integer.valueOf(mClassMap.get(standard));
        }

        mProfile.setStandard(mSelectedClass);

        //check if it is in the edit mode
        if (!mIsEditMode && isClassValid(mSelectedClass)) {
            isClassMarked = true;

            if (isClassMarked && isAgeMarked && isGenderMarked) {
                //start the animation
                mAgeView.showNextButtonAnimation();
            }
        }
    }

    @Override
    public boolean isClassValid(int standard) {
        return (standard != -99);
    }

    @Override
    public void toggleGender(int gender) {
        mSelectedGender = gender;

        if (mSelectedGender == 0) {
            mProfile.setGender("male");
        } else if (mSelectedGender == 1) {
            mProfile.setGender("female");
        }
        mAgeView.showGender(gender);

        //check if it is in edit mode
        if (!mIsEditMode) {
            isGenderMarked = true;

            if (isAgeMarked && isGenderMarked && isClassMarked) {
                //start the animation
                mAgeView.showNextButtonAnimation();
            }
        }
    }

    @Override
    public void handleAgeNotAvailable() {
        mAgeView.showEmptyAgeAnimation();
    }

    @Override
    public void handleGenderNotAvailable() {
        mAgeView.showEmptyGenderAnimation();
    }

    @Override
    public void handleClassNotAvailable() {
        mAgeView.showEmptyClassAnimation();
    }


    @Override
    public void bindView(BaseView view, Context context) {
        mAgeView = (AgeGendernClassContract.View) view;

    }

    @Override
    public void unbindView() {

    }
}

