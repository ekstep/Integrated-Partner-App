package org.ekstep.genie.ui.addchild;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;

import org.ekstep.genie.CoreApplication;
import org.ekstep.genie.R;
import org.ekstep.genie.activity.BaseActivity;
import org.ekstep.genie.base.BaseView;
import org.ekstep.genie.telemetry.TelemetryAction;
import org.ekstep.genie.telemetry.TelemetryBuilder;
import org.ekstep.genie.telemetry.TelemetryConstant;
import org.ekstep.genie.telemetry.TelemetryHandler;
import org.ekstep.genie.telemetry.TelemetryStageId;
import org.ekstep.genie.ui.addchild.agegendernclass.AgeGenderClassFragment;
import org.ekstep.genie.ui.addchild.avatar.AvatarFragment;
import org.ekstep.genie.ui.addchild.mediumnboard.MediumnBoardFragment;
import org.ekstep.genie.ui.addchild.nickname.NickNameFragment;
import org.ekstep.genie.util.Constant;
import org.ekstep.genie.util.LogUtil;
import org.ekstep.genie.util.Util;
import org.ekstep.genie.util.preference.PreferenceUtil;
import org.ekstep.genieservices.async.UserService;
import org.ekstep.genieservices.commons.IResponseHandler;
import org.ekstep.genieservices.commons.bean.GenieResponse;
import org.ekstep.genieservices.commons.bean.Profile;
import org.ekstep.genieservices.commons.bean.enums.InteractionType;
import org.ekstep.genieservices.eventbus.EventBus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created on 22/12/16.
 *
 * @author swayangjit
 */
public class AddChildPresenter implements AddChildContract.Presenter {

    private static final String TAG = AddChildPresenter.class.getSimpleName();
    private static final int NOT_SELECTED = -99;
    private static final long MIN_DELAY_MS = 100;

    private AddChildContract.View mAddChildlView;

    private Context mContext;
    private Profile mProfile = null;
    private boolean mIsGroup = false;
    private boolean mIsEditMode = false;
    private boolean mIsBackPressed;
    private long mLastClickTime;
    private UserService mUserService = null;

    public AddChildPresenter() {
        mUserService = CoreApplication.getGenieAsyncService().getUserService();
    }

    @Override
    public void fetchBundleExtras(Bundle bundle) {
        if (bundle != null) {
            mProfile = (Profile) bundle.getSerializable(Constant.BUNDLE_UPDATED_PROFILE);
            mIsEditMode = bundle.getBoolean(Constant.BUNDLE_EDIT_MODE);
            mIsGroup = bundle.getBoolean(Constant.BUNDLE_GROUP_MODE);
        }
    }

    @Override
    public void openAddChildLayout() {
        mAddChildlView.showTitle(getLayoutTitle());

        if (mIsGroup) {
            mAddChildlView.showAddChildIcon();
            mAddChildlView.hideAddGroupIcon();
            mAddChildlView.showAddGroupProgress();
        } else {
            mAddChildlView.showAddChildProgress();
        }

        if (mIsEditMode) {
            mAddChildlView.hideAddChildIcon();
            mAddChildlView.hideAddGroupIcon();
        }
        if (mIsGroup) {
            TelemetryHandler.saveTelemetry(TelemetryBuilder.buildGEInteract(InteractionType.TOUCH, TelemetryStageId.MANAGE_GROUPS, TelemetryAction.ADD_GROUP_INITIATE));
        } else {
            TelemetryHandler.saveTelemetry(TelemetryBuilder.buildGEInteract(InteractionType.TOUCH, TelemetryStageId.MANAGE_CHILDREN, TelemetryAction.ADD_CHILD_INITIATE));
        }

        mAddChildlView.showNickNameFragment(mProfile, mIsGroup);
    }

    @Override
    public void handleToggleProfileMode() {
        if (mIsGroup) {
            mAddChildlView.showAddChildProgress();
            mAddChildlView.hideAddChildIcon();
            mAddChildlView.showAddGroupIcon();
            mAddChildlView.showTitle(R.string.title_addchild);
            mIsGroup = false;
        } else {
            mAddChildlView.showAddGroupProgress();
            mAddChildlView.showAddChildIcon();
            mAddChildlView.hideAddGroupIcon();
            mAddChildlView.showTitle(R.string.title_addchild_group);
            mIsGroup = true;
        }
    }

    @Override
    public void handleNextButtonClick() {
        Fragment currentFragment = ((AddChildActivity) mContext).getRunningFragment();

        if (currentFragment instanceof NickNameFragment) {
            if (mAddChildlView.isValidNickName()) {
                mProfile = mAddChildlView.getNickNameData();

                if (mProfile.isGroupUser()) {
                    generateInteractEvent(TelemetryStageId.ADD_GROUP_NAME, mProfile, 0);
                } else {
                    generateInteractEvent(TelemetryStageId.ADD_CHILD_NAME, mProfile, 0);
                }


                mAddChildlView.showSkip();
                mAddChildlView.hideToggle();
                mAddChildlView.hideBack();

                if (mIsGroup) {
                    mAddChildlView.hideNext();
                    mAddChildlView.showAvatarFragment(mProfile);
                } else {
                    mAddChildlView.showAgeGenderAndClassFragment(mProfile);
                }

                mAddChildlView.setProgressBarState(2);
            }

        } else if (currentFragment instanceof AgeGenderClassFragment) {
            mProfile = mAddChildlView.getAgeGenderAndClassData();

            generateInteractEvent(TelemetryStageId.ADD_CHILD_AGE_GENDER_CLASS, mProfile, 1);

            if (mProfile.getStandard() != NOT_SELECTED && mProfile.getAge() != 0 && !TextUtils.isEmpty(mProfile.getGender())) {
                mAddChildlView.hideToggle();
                mAddChildlView.showSkip();
                mAddChildlView.showMediumAndBoardFragment(mProfile);
                mAddChildlView.setProgressBarState(3);

                if (mIsGroup) {
                    mAddChildlView.hideAddChildIcon();
                } else {
                    mAddChildlView.hideAddGroupIcon();
                }
            } else if (mProfile.getAge() == 0) {
                mAddChildlView.showAgeNotAvailableError();
            } else if (TextUtils.isEmpty(mProfile.getGender())) {
                mAddChildlView.showGenderNotAvailableError();
            } else if (mProfile.getStandard() == NOT_SELECTED) {
                mAddChildlView.showClassNotAvailableError();
            }

        } else if (currentFragment instanceof MediumnBoardFragment) {
            mProfile = mAddChildlView.getMediumAndBoardData();

            generateInteractEvent(TelemetryStageId.ADD_CHILD_MEDIUM_BOARD, mProfile, 2);

            if (mProfile.getMedium() != null && mProfile.getBoard() != null) {
                mAddChildlView.hideToggle();
                mAddChildlView.showSkip();
                mAddChildlView.hideNext();
                mAddChildlView.showTitle(R.string.label_addchild_pick_avatar);
                mAddChildlView.showAvatarFragment(mProfile);
                mAddChildlView.setProgressBarState(4);
            } else if (mProfile.getMedium() == null) {
                mAddChildlView.showMediumNotAvailableError();
            } else if (mProfile.getBoard() == null) {
                mAddChildlView.showBoardNotAvailableError();
            }
        }
    }

    @Override
    public void generateInteractEvent(String screenName, Profile profile, int flow) {

        List<String> validationErrors = new ArrayList<>();
        Map<String, String> map = new HashMap<>();
        switch (flow) {
            case 0:
                map.put(TelemetryConstant.NAME, String.valueOf(profile.getHandle()));
                break;
            case 1:
                if (profile.getAge() == 0) {
                    validationErrors.add(TelemetryConstant.AGE_VALIDATION_ERROR);
                } else {
                    map.put(TelemetryConstant.AGE, String.valueOf(profile.getAge()));
                }

                if (TextUtils.isEmpty(profile.getGender())) {
                    validationErrors.add(TelemetryConstant.GENDER_VALIDATION_ERROR);
                } else {
                    map.put(TelemetryConstant.GENDER, String.valueOf(profile.getGender()));
                }


                if (profile.getStandard() == NOT_SELECTED) {
                    validationErrors.add(TelemetryConstant.GRADE_VALIDATION_ERROR);
                } else {
                    map.put(TelemetryConstant.GRADE, String.valueOf(profile.getStandard()));
                }


                break;
            case 2:
                if (profile.getBoard() == null) {
                    validationErrors.add(TelemetryConstant.BOARD_VALIDATION_ERROR);
                } else {
                    map.put(TelemetryConstant.BOARD, String.valueOf(profile.getBoard()));
                }
                if (profile.getMedium() == null) {
                    validationErrors.add(TelemetryConstant.MEDIUM_VALIDATION_ERROR);
                } else {
                    map.put(TelemetryConstant.MEDIUM, String.valueOf(profile.getMedium()));
                }
                break;
            case 3:
                String avatar = profile.getAvatar();
                String[] avtArray = avatar.split("/");
                if (avtArray != null && avtArray[1] != null) {
                    String selectedAvatar = avtArray[1];
                    map.put(TelemetryConstant.AVATAR, selectedAvatar);
                }

                break;
        }

        generateNextButtonTelemetry(screenName, map, validationErrors);
    }


    @Override
    public void handleGenieBackButtonClick() {

        long lastClickTime = mLastClickTime;
        long now = System.currentTimeMillis();
        mLastClickTime = now;
        if (now - lastClickTime < MIN_DELAY_MS) {
            // Too fast: ignore
        } else {
            performBackClick();
        }

    }

    private void performBackClick() {
        mIsBackPressed = true;
        ((BaseActivity) mContext).popBackStack();

        Fragment currentFragment = ((AddChildActivity) mContext).getRunningFragment();
        if (currentFragment instanceof NickNameFragment) {
            mAddChildlView.hideSkip();
        } else if (currentFragment instanceof AgeGenderClassFragment) {
            mAddChildlView.showBack();
            mAddChildlView.hideSkip();
            mAddChildlView.showToggle();
            if (!mIsEditMode) {
                mAddChildlView.showAddGroupIcon();
            }
            mAddChildlView.setProgressBarState(1);
        } else if (currentFragment instanceof MediumnBoardFragment) {
            mAddChildlView.setProgressBarState(2);
        } else if (currentFragment instanceof AvatarFragment) {
            mAddChildlView.showNext();
            mAddChildlView.showTitle(getLayoutTitle());

            if (mIsGroup) {
                mAddChildlView.showToggle();
                mAddChildlView.hideSkip();
                mAddChildlView.showBack();
                mAddChildlView.setProgressBarState(1);
            } else {
                mAddChildlView.setProgressBarState(3);
            }
        }
    }


    @Override
    public void handleAvatarClick() {
        Profile profile = mAddChildlView.getAvatarData();

        if (profile.isGroupUser()) {
            generateInteractEvent(TelemetryStageId.ADD_GROUP_AVATAR, profile, 3);
        } else {
            generateInteractEvent(TelemetryStageId.ADD_CHILD_AVATAR, profile, 3);
        }

        if (profile.getUid() != null) {
            handleUpdateUserProfile(profile);
        } else {
            LogUtil.i(TAG, profile.toString());
            if (profile.isGroupUser()) {
                profile.setStandard(-1);
            }
            addProfile(profile);
        }
    }

    @Override
    public void addProfile(Profile profile) {
        mUserService.createUserProfile(profile, new IResponseHandler<Profile>() {
            @Override
            public void onSuccess(GenieResponse<Profile> genieResponse) {
                Profile createdProfile = genieResponse.getResult();
                mAddChildlView.hideNavigationHeader();
                mAddChildlView.hideSkip();
                mAddChildlView.hideNext();
                mAddChildlView.hideProgress();
                mAddChildlView.showWelcomeAboardFragment(createdProfile);

                if (mIsGroup) {
                    TelemetryHandler.saveTelemetry(TelemetryBuilder.buildGEInteract(InteractionType.OTHER, TelemetryStageId.MANAGE_GROUPS, TelemetryAction.ADD_GROUP_SUCCESS));
                } else {
                    TelemetryHandler.saveTelemetry(TelemetryBuilder.buildGEInteract(InteractionType.OTHER, TelemetryStageId.MANAGE_CHILDREN, TelemetryAction.ADD_CHILD_SUCCESS));
                }
                setCurrentUser(createdProfile.getUid());
            }

            @Override
            public void onError(GenieResponse<Profile> genieResponse) {

            }
        });
    }

    @Override
    public void handleUpdateUserProfile(Profile profile) {
        mUserService.updateUserProfile(profile, new IResponseHandler<Profile>() {
            @Override
            public void onSuccess(GenieResponse<Profile> genieResponse) {
//                Intent intent = new Intent(Constant.INTENT_ACTION_EDIT_CHILD);
//                LocalBroadcastManager.getInstance(mContext).sendBroadcast(intent);
                EventBus.postStickyEvent(Constant.EventKey.EVENT_KEY_EDIT_CHILD);
                mAddChildlView.finishActivity();
            }

            @Override
            public void onError(GenieResponse<Profile> genieResponse) {
                Util.processFailure(mContext, genieResponse);
            }
        });
    }

    @Override
    public boolean isBackPressed() {
        return mIsBackPressed;
    }

    @Override
    public boolean isEditMode() {
        return mIsEditMode;
    }

    private void setGroupMode(boolean mode) {
        mIsGroup = mode;
    }

    public void setCurrentUser(String uid) {
        mUserService.setCurrentUser(uid, new IResponseHandler<Void>() {
            @Override
            public void onSuccess(GenieResponse<Void> genieResponse) {
                mAddChildlView.waitAndFinishActivity();
            }

            @Override
            public void onError(GenieResponse<Void> genieResponse) {
                mAddChildlView.waitAndFinishActivity();
            }
        });
    }

    @Override
    public void handleNextButtonAnimation() {
        mAddChildlView.showNextButtonAnimation();
    }

    @Override
    public int getLayoutTitle() {
        int title = R.string.title_addchild;

        if (mProfile != null) {
            if (mProfile.isGroupUser()) {
                setGroupMode(true);

                if (mIsEditMode) {
                    title = R.string.title_addchild_edit_group;
                } else {
                    title = R.string.title_addchild_group;
                }

            } else {
                setGroupMode(false);

                if (mIsEditMode) {
                    title = R.string.title_addchild_edit;
                }
            }
        } else {
            mProfile = getDefaultProfile();

            if (mIsGroup) {
                title = R.string.title_addchild_group;
            } else {
                title = R.string.title_addchild;
            }
        }

        return title;
    }

    @Override
    public Profile getDefaultProfile() {
        Profile profile = new Profile("", "", PreferenceUtil.getLanguage());
        profile.setBoard(null);
        profile.setMedium(null);
        profile.setGender("");
        profile.setAge(0);
        profile.setStandard(-99);
        return profile;
    }

    @Override
    public void generateNextButtonTelemetry(String screenName, Map<String, String> map, List<String> validationErrors) {
        HashMap<String, Object> values = new HashMap<>();
        values.putAll(map);
        if (validationErrors != null && validationErrors.size() > 0) {
            Map<String, Object> validationMap = new HashMap<>();
            validationMap.put(TelemetryConstant.VALIDATION_ERRORS, validationErrors);
            values.putAll(validationMap);
        }
        TelemetryHandler.saveTelemetry(TelemetryBuilder.buildGEInteract(InteractionType.TOUCH, screenName, null, values));
    }

    @Override
    public void bindView(BaseView view, Context context) {
        mAddChildlView = (AddChildContract.View) view;
        mContext = context;
    }

    @Override
    public void unbindView() {
        mAddChildlView = null;
        mContext = null;
    }
}
