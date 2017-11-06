package org.ekstep.genie.ui.addchild.nickname;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import org.ekstep.genie.base.BaseView;
import org.ekstep.genie.telemetry.TelemetryAction;
import org.ekstep.genie.telemetry.TelemetryBuilder;
import org.ekstep.genie.telemetry.TelemetryHandler;
import org.ekstep.genie.telemetry.TelemetryStageId;
import org.ekstep.genie.ui.addchild.AddChildActivity;
import org.ekstep.genie.util.Constant;
import org.ekstep.genie.util.Util;
import org.ekstep.genie.util.preference.PreferenceUtil;
import org.ekstep.genieservices.commons.bean.Profile;
import org.ekstep.genieservices.commons.bean.enums.InteractionType;

import static org.ekstep.genie.util.FontConstants.LANG_ENGLISH;
import static org.ekstep.genie.util.FontConstants.LANG_TELUGU;

/**
 * Created on 22/12/16.
 *
 * @author swayangjit
 */
public class NickNamePresenter implements NickNameContract.Presenter {

    private static final String[] mNames = new String[]{"Happy", "Tiger", "Tiger",
            "Star", "Royal", "Royal", "Ninja", "Ninja", "Red", "Blue",
            "Toffee", "Sparkle"};

    @NonNull
    private NickNameContract.View mNickNameView;

    @NonNull
    private Context mContext;

    private boolean mIsGroup;
    private boolean mIsEditMode = false;
    private Profile mProfile = null;
    private String mRandomName = null;
    private boolean mIsRandomNameChosen;

    public NickNamePresenter() {


    }

    @Override
    public void fetchBundleExtras(Bundle bundle) {
        if (bundle != null) {
            mProfile = (Profile) bundle.getSerializable(Constant.BUNDLE_UPDATED_PROFILE);
            if (!mIsGroup) {
                mIsGroup = bundle.getBoolean(Constant.BUNDLE_KEY_MODE);
            }
            mIsEditMode = bundle.getBoolean(Constant.BUNDLE_EDIT_MODE);
        }
    }

    @Override
    public void openNickNameLayout() {
        if (mIsGroup) {
            TelemetryHandler.saveTelemetry(TelemetryBuilder.buildGEInteract(TelemetryStageId.ADD_GROUP_NAME));
        } else {
            TelemetryHandler.saveTelemetry(TelemetryBuilder.buildGEInteract(TelemetryStageId.ADD_CHILD_NAME));
        }
        adjustUIAccordingLanguage();
        //Set Random Name
        int randomNumber = Util.getRandomInteger(100, 10);
        int randomName = Util.getRandomInteger(10, 0);

        if (mRandomName == null) {
            mRandomName = mNames[randomName] + String.valueOf(randomNumber);
        }
        mNickNameView.setRandomName(mRandomName);

        if (mIsEditMode) {
            mNickNameView.setName(mProfile.getHandle());
        }
    }

    @Override
    public void adjustUIAccordingLanguage() {
        if (!PreferenceUtil.getLanguage().equalsIgnoreCase(LANG_ENGLISH)) {
            if (PreferenceUtil.getLanguage().equalsIgnoreCase(LANG_TELUGU)) {
                mNickNameView.adjustUiForTelugu();
            } else {
                mNickNameView.adjustUiForOthers();
            }
        }
    }

    @Override
    public boolean isValidNickName() {
        if (Util.isWhitespace(mNickNameView.getNickName()) && mNickNameView.getNickName().length() > 0) {
            mNickNameView.showBlankNickNameMessage();

            return false;
        } else {
            return true;
        }
    }

    @Override
    public Profile getName() {
        String nickName;
        if (mIsRandomNameChosen) {
            nickName = mRandomName;
        } else {
            if (TextUtils.isEmpty(mNickNameView.getNickName())) {
                nickName = mRandomName;
            } else {
                nickName = mNickNameView.getNickName();
            }
        }
        mProfile.setHandle(nickName);
        mProfile.setGroupUser(mIsGroup);

        return mProfile;
    }

    @Override
    public void handleNavigationToAgeClassGenderFragment(boolean isRandomNameChosen) {
        mIsRandomNameChosen = isRandomNameChosen;
        ((AddChildActivity) mContext).mPresenter.handleNextButtonClick();
    }

    @Override
    public void toggleMode() {
        if (mIsGroup) {
            mIsGroup = false;

            PreferenceUtil.setGroup("false");
            TelemetryHandler.saveTelemetry(TelemetryBuilder.buildGEInteract(InteractionType.TOUCH, TelemetryStageId.MANAGE_CHILDREN, TelemetryAction.ADD_CHILD_INITIATE));
            TelemetryHandler.saveTelemetry(TelemetryBuilder.buildGEInteract(TelemetryStageId.ADD_CHILD_NAME));
        } else {
            mIsGroup = true;

            mProfile.setBoard(null);
            mProfile.setMedium(null);
            mProfile.setGender("");
            mProfile.setAge(0);
            mProfile.setStandard(-99);

            PreferenceUtil.setGroup("true");

            TelemetryHandler.saveTelemetry(TelemetryBuilder.buildGEInteract(InteractionType.TOUCH, TelemetryStageId.MANAGE_GROUPS, TelemetryAction.ADD_GROUP_INITIATE));
            TelemetryHandler.saveTelemetry(TelemetryBuilder.buildGEInteract(TelemetryStageId.ADD_GROUP_NAME));
        }
    }


    @Override
    public void bindView(BaseView view, Context context) {
        mNickNameView = (NickNameContract.View) view;
        mContext = context;
    }

    @Override
    public void unbindView() {
        mNickNameView = null;
        mContext = null;
    }
}
