package org.ekstep.genie.ui.addchild.avatar;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;

import org.ekstep.genie.base.BaseView;
import org.ekstep.genie.base.ProfileConfig;
import org.ekstep.genie.telemetry.EnvironmentId;
import org.ekstep.genie.telemetry.TelemetryBuilder;
import org.ekstep.genie.telemetry.TelemetryHandler;
import org.ekstep.genie.telemetry.TelemetryStageId;
import org.ekstep.genie.telemetry.enums.ImpressionType;
import org.ekstep.genie.ui.addchild.AddChildActivity;
import org.ekstep.genie.util.AvatarUtil;
import org.ekstep.genie.util.Constant;
import org.ekstep.genie.util.preference.PreferenceUtil;
import org.ekstep.genieservices.commons.bean.Profile;

import java.util.Map;

import static org.ekstep.genie.util.FontConstants.LANG_ENGLISH;
import static org.ekstep.genie.util.FontConstants.LANG_TELUGU;

/**
 * Created on 22/12/16.
 *
 * @author swayangjit
 */
public class AvatarPresenter implements AvatarContract.Presenter {

    private static final String TAG = AvatarPresenter.class.getSimpleName();
    @NonNull
    private AvatarContract.View mAvatarView;

    @NonNull
    private Context mContext;

    private AvatarCoverflowAdapter mAvatarAdapter = null;

    private boolean mIsGroup = false;

    private boolean mIsEditMode = false;

    private Profile mProfile = null;

    private Map<String, Integer> mBadges = null;

    private int mSelectedPosition = -1;

    public AvatarPresenter() {


    }

    @Override
    public void fetchBundleExtras(Bundle bundle) {
        if (bundle != null) {
            mProfile = (Profile) bundle.getSerializable(Constant.BUNDLE_UPDATED_PROFILE);
            mIsEditMode = bundle.getBoolean(Constant.BUNDLE_EDIT_MODE);
        }
    }

    @Override
    public void openAvatarLayout() {

        mBadges = AvatarUtil.populateBadges();

        adjustUIAccordingLanguage();

        mIsGroup = mProfile.isGroupUser();

        if (mIsGroup) {
            TelemetryHandler.saveTelemetry(TelemetryBuilder.buildImpressionEvent(EnvironmentId.HOME, TelemetryStageId.ADD_GROUP_AVATAR, ImpressionType.VIEW));
        } else {
            TelemetryHandler.saveTelemetry(TelemetryBuilder.buildImpressionEvent(EnvironmentId.HOME, TelemetryStageId.ADD_CHILD_AVATAR, ImpressionType.VIEW));

        }
        String gender = mProfile.getGender();
        mAvatarAdapter = new AvatarCoverflowAdapter(mContext, mIsGroup, mProfile.getGender());
        mAvatarView.showAvatarList(mAvatarAdapter);

        if (mSelectedPosition != -1) {
            handleAvatarSelection(mSelectedPosition);
        }

        if (mIsEditMode) {
            if (mIsGroup) {
                mSelectedPosition = AvatarUtil.getPosition(mBadges, mProfile.getAvatar());
            } else {
                if (gender.equalsIgnoreCase("male")) {
                    mSelectedPosition = AvatarUtil.getPosition(AvatarUtil.populateMaleAvatars(), mProfile.getAvatar());
                } else {
                    mSelectedPosition = AvatarUtil.getPosition(AvatarUtil.populateFemaleAvatars(), mProfile.getAvatar());
                }
            }

            mAvatarAdapter.getEditedAvatarPosition(mProfile.getAvatar(), mIsGroup);

            //This is only for partner apps
            if (mSelectedPosition == -1) {
                mSelectedPosition = 0;
            }
            mAvatarView.setSelectedAvatar(mSelectedPosition);
            handleAvatarSelection(mSelectedPosition);
        }
    }

    @Override
    public void adjustUIAccordingLanguage() {
        if (!PreferenceUtil.getLanguage().equalsIgnoreCase(LANG_ENGLISH)) {
            if (PreferenceUtil.getLanguage().equalsIgnoreCase(LANG_TELUGU)) {
                mAvatarView.adjustUiForTelugu();
            } else {
                mAvatarView.adjustUiForOthers();
            }
        }
    }

    @Override
    public void handleAvatarSelection(int position) {
        mSelectedPosition = position;
        mAvatarAdapter.setSelectedPosition(position);
        mAvatarAdapter.notifyDataSetChanged();
    }

    @Override
    public void handleOnAvatarClick(int position) {
        mAvatarView.showInvisibleLayout();

        handleAvatarSelection(position);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                ((AddChildActivity) mContext).mPresenter.handleAvatarClick();
            }
        }, 500);
    }

    @Override
    public String fetchAvatarData(int position) {
        String selectedImage;

        if (mIsGroup) {
            selectedImage = AvatarUtil.getAvatarData(mBadges, position);
        } else {
            String gender = mProfile.getGender();
            if (gender.equalsIgnoreCase("male")) {
                selectedImage = AvatarUtil.getAvatarData(AvatarUtil.populateMaleAvatars(), position);
            } else {
                selectedImage = AvatarUtil.getAvatarData(AvatarUtil.populateFemaleAvatars(), position);
            }
        }
        return selectedImage;
    }

    @Override
    public String fetchAvatarName(int position) {
        String selectedImage;

        if (mIsGroup) {
            selectedImage = AvatarUtil.populateBadgeNames().get(position);
        } else {
            String gender = mProfile.getGender();
            if (gender.equalsIgnoreCase("male")) {
                selectedImage = AvatarUtil.populateMaleAvatarNames().get(position);
            } else {
                selectedImage = AvatarUtil.populateFemaleAvatarNames().get(position);
            }
        }
        return selectedImage;
    }

    @Override
    public Profile getProfile() {
        String selectedImage = fetchAvatarData(mAvatarAdapter.getSelectedAvatar());
        String selectedImageName = fetchAvatarName(mAvatarAdapter.getSelectedAvatar());
        mProfile.setAvatar(selectedImage);
        String profileImagePath = new ProfileConfig().getProfilePath(mContext);
        mProfile.setProfileImage(profileImagePath + "/" + selectedImageName);
        return mProfile;
    }


    @Override
    public void bindView(BaseView view, Context context) {
        mAvatarView = (AvatarContract.View) view;
        mContext = context;
    }

    @Override
    public void unbindView() {
        mAvatarView = null;
        mContext = null;
    }
}
