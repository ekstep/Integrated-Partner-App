package org.ekstep.genie.ui.addchild.mediumnboard;

import android.content.Context;
import android.os.Bundle;
import android.view.View;

import org.ekstep.genie.CoreApplication;
import org.ekstep.genie.base.BaseView;
import org.ekstep.genie.telemetry.TelemetryBuilder;
import org.ekstep.genie.telemetry.TelemetryHandler;
import org.ekstep.genie.telemetry.TelemetryStageId;
import org.ekstep.genie.util.Constant;
import org.ekstep.genie.util.FontConstants;
import org.ekstep.genie.util.geniesdk.ConfigUtil;
import org.ekstep.genie.util.preference.PreferenceUtil;
import org.ekstep.genieservices.IConfigService;
import org.ekstep.genieservices.commons.bean.GenieResponse;
import org.ekstep.genieservices.commons.bean.MasterData;
import org.ekstep.genieservices.commons.bean.MasterDataValues;
import org.ekstep.genieservices.commons.bean.Profile;
import org.ekstep.genieservices.commons.bean.enums.MasterDataType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by swayangjit on 22/12/16.
 */

public class MediumnBoardPresenter implements MediumnBoardContract.Presenter {


    private static final String TAG = MediumnBoardPresenter.class.getSimpleName();
    private boolean isMediumMarked = false;
    private boolean isBoardMarked = false;
    private MediumnBoardContract.View mMediumBoardView;
    private Context mContext;
    private boolean mIsEditMode;
    private Profile mProfile = null;
    private String mSelectedMedium = "";
    private String mSelectedBoard = "";

    private List<String> mBoardList = null;
    private List<String> mMediumList = null;
    private Map<String, String> mLocaleMap = null;

    public MediumnBoardPresenter() {
    }

    @Override
    public void fetchBundleExtras(Bundle bundle) {
        if (bundle != null) {
            mProfile = (Profile) bundle.getSerializable(Constant.BUNDLE_UPDATED_PROFILE);
            mIsEditMode = bundle.getBoolean(Constant.BUNDLE_EDIT_MODE);
        }
    }

    @Override
    public void openMediumnBoard() {
        TelemetryHandler.saveTelemetry(TelemetryBuilder.buildGEInteract(TelemetryStageId.ADD_CHILD_MEDIUM_BOARD));
        populateMediumnBoard();
    }

    @Override
    public void onMediumClick(View view) {
        mMediumBoardView.showMediumListPopup(mMediumList, view);
    }

    @Override
    public void onMediumItemClick(int position) {
        mSelectedMedium = mMediumList.get(position);
        mProfile.setMedium(ConfigUtil.getRelevantMasterData(mMediumList, mSelectedMedium));
        mMediumBoardView.showMedium(mMediumList.get(position));
        setMediumMarked();
    }

    @Override
    public void setMediumMarked() {
        if (!mIsEditMode) {
            isMediumMarked = true;

            if (isMediumMarked && isBoardMarked) {
                //start the animation
                mMediumBoardView.animateNextButton();
            }
        }
    }

    @Override
    public void onBoardClick(View view) {
        mMediumBoardView.showBoardListPopup(mBoardList, view);
    }

    @Override
    public void onBoardItemClick(int position) {
        mSelectedBoard = mBoardList.get(position);
        mProfile.setBoard(ConfigUtil.getRelevantMasterData(mBoardList, mSelectedBoard));
        mMediumBoardView.showBoard(mBoardList.get(position));
        setBoardMarked();
    }

    @Override
    public void setBoardMarked() {
        if (!mIsEditMode) {
            isBoardMarked = true;

            if (isMediumMarked && isBoardMarked) {
                //start the animation
                mMediumBoardView.animateNextButton();
            }
        }
    }

    @Override
    public void handleEmptyMedium() {
        mMediumBoardView.showEmptyMediumAnimation();
    }

    @Override
    public void handleEmptyBoard() {
        mMediumBoardView.showEmptyBoardAnimation();
    }

    @Override
    public void populateMediumnBoard() {
        IConfigService configService = CoreApplication.getGenieSdkInstance().getConfigService();

        //get all the board values
        GenieResponse<MasterData> boardResponse = configService.getMasterData(MasterDataType.BOARD);

        if (boardResponse != null && boardResponse.getStatus()) {
            MasterData masterData = boardResponse.getResult();

            //get all the board labels of the MasterDataValues into a list
            if (masterData != null && masterData.getValues().size() > 0) {
                mBoardList = new ArrayList<>();
                for (MasterDataValues masterDataValue : masterData.getValues()) {
                    mBoardList.add(masterDataValue.getLabel());
                }

                //if the profile is in edit mode, then get the board label from already prepared list of board
                if (mIsEditMode || mProfile != null) {
                    if (mProfile.getBoard() != null) {
                        if (mBoardList.size() > 0) {
                            String boardLabel = ConfigUtil.getRelevantMasterData(mBoardList, mProfile.getBoard());
                            mMediumBoardView.showBoard(boardLabel);
                            setBoardMarked();
                        }
                    }
                }
            }
        }

        //get all the medium values
        GenieResponse<MasterData> mediumResponse = configService.getMasterData(MasterDataType.MEDIUM);

        if (mediumResponse != null && mediumResponse.getStatus()) {
            MasterData masterData = mediumResponse.getResult();

            //get all the medium labels of the MasterDataValues into a list
            if (masterData != null && masterData.getValues().size() > 0) {
                mMediumList = new ArrayList<>();
                for (MasterDataValues masterDataValue :
                        masterData.getValues()) {
                    mMediumList.add(masterDataValue.getLabel());
                }

                //if the profile is in edit mode, then get the medium label from already prepared list of medium
                if (mIsEditMode || mProfile != null) {

                    if (mProfile.getMedium() != null) {
                        String label = ConfigUtil.getRelevantMasterData(mMediumList, mProfile.getMedium());
                        mMediumBoardView.showMedium(label);
                        setMediumMarked();
                    } else {
                        //Set Selected language as default medium of instruction.
                        String defaultMedium = getMediumofInstruction();
                        mProfile.setMedium(ConfigUtil.getRelevantMasterData(mMediumList, defaultMedium));
                        mMediumBoardView.showMedium(defaultMedium);
                        setMediumMarked();
                    }
                }
            }
        }
    }

    private Map<String, String> getLocaleMap() {
        if (mLocaleMap == null) {
            mLocaleMap = new HashMap<>();
            mLocaleMap.put(FontConstants.LANG_ENGLISH, FontConstants.ENGLISH);
            mLocaleMap.put(FontConstants.LANG_HINDI, FontConstants.HINDI);
            mLocaleMap.put(FontConstants.LANG_KANNADA, FontConstants.KANNADA);
            mLocaleMap.put(FontConstants.LANG_TELUGU, FontConstants.TELUGU);
            mLocaleMap.put(FontConstants.LANG_TAMIL, FontConstants.TAMIL);
            mLocaleMap.put(FontConstants.LANG_MALAYALAM, FontConstants.MALAYALAM);
            mLocaleMap.put(FontConstants.LANG_MARATHI, FontConstants.MARATHI);
            mLocaleMap.put(FontConstants.LANG_GUJARATI, FontConstants.GUJARATI);
            mLocaleMap.put(FontConstants.LANG_PUNJABI, FontConstants.PUNJABI);
            mLocaleMap.put(FontConstants.LANG_ORIYA, FontConstants.ORIYA);
            mLocaleMap.put(FontConstants.LANG_BENGALI, FontConstants.BENGALI);
            mLocaleMap.put(FontConstants.LANG_ASSAMESE, FontConstants.ASSAMESE);
            mLocaleMap.put(FontConstants.LANG_ENGLISH, FontConstants.ENGLISH);
        }
        return mLocaleMap;
    }

    @Override
    public String getMediumofInstruction() {
        return getLocaleMap().get(PreferenceUtil.getLanguage());
    }

    @Override
    public Profile getMediumnBoard() {
        return mProfile;
    }


    @Override
    public void bindView(BaseView view, Context context) {
        mContext = context;
        mMediumBoardView = (MediumnBoardContract.View) view;
    }

    @Override
    public void unbindView() {
        mContext = null;
        mMediumBoardView = null;
    }
}


