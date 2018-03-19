package org.ekstep.genie.ui.managechild;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.view.View;

import org.ekstep.genie.CoreApplication;
import org.ekstep.genie.R;
import org.ekstep.genie.activity.RuntimePermissionsActivity;
import org.ekstep.genie.base.BaseView;
import org.ekstep.genie.telemetry.EnvironmentId;
import org.ekstep.genie.telemetry.TelemetryAction;
import org.ekstep.genie.telemetry.TelemetryBuilder;
import org.ekstep.genie.telemetry.TelemetryConstant;
import org.ekstep.genie.telemetry.TelemetryHandler;
import org.ekstep.genie.telemetry.TelemetryStageId;
import org.ekstep.genie.telemetry.enums.ImpressionType;
import org.ekstep.genie.telemetry.enums.ObjectType;
import org.ekstep.genie.ui.landing.LandingActivity;
import org.ekstep.genie.util.AvatarUtil;
import org.ekstep.genie.util.Constant;
import org.ekstep.genie.util.LogUtil;
import org.ekstep.genie.util.Util;
import org.ekstep.genie.util.preference.PreferenceUtil;
import org.ekstep.genieservices.async.UserService;
import org.ekstep.genieservices.commons.IResponseHandler;
import org.ekstep.genieservices.commons.bean.GenieResponse;
import org.ekstep.genieservices.commons.bean.Profile;
import org.ekstep.genieservices.commons.bean.enums.InteractionType;
import org.ekstep.genieservices.commons.utils.StringUtil;
import org.ekstep.genieservices.eventbus.EventBus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created on 12/21/2016.
 *
 * @author anil
 */

public class ManageChildPresenter implements ManageChildContract.Presenter, ManageChildAdapter.Callback {

    private static final String TAG = "ManageChildPresenter";

    private ManageChildContract.View mManageChildView;

    private Activity mActivity;
    private ArrayList<Profile> mChildListProfile;
    private ArrayList<Profile> mGroupListProfile;
    private boolean mIsGroup = false;
    private Set<String> mSelected = new HashSet<>();
    private boolean isProfileAPI;
    private UserService mUserService = null;

    public ManageChildPresenter() {
        mUserService = CoreApplication.getGenieAsyncService().getUserService();
    }

    @Override
    public void openManageChild() {
        if (PreferenceUtil.isGroup().equalsIgnoreCase("true")) {
//            TelemetryHandler.saveTelemetry(TelemetryBuilder.buildGEInteract(TelemetryStageId.MANAGE_GROUPS));
            TelemetryHandler.saveTelemetry(TelemetryBuilder.buildImpressionEvent(EnvironmentId.HOME, TelemetryStageId.MANAGE_GROUPS, ImpressionType.VIEW));
        } else {
//            TelemetryHandler.saveTelemetry(TelemetryBuilder.buildGEInteract(TelemetryStageId.MANAGE_CHILDREN));
            TelemetryHandler.saveTelemetry(TelemetryBuilder.buildImpressionEvent(EnvironmentId.HOME, TelemetryStageId.MANAGE_CHILDREN, ImpressionType.VIEW));

        }
        fetchAllUserProfiles();
    }

    @Override
    public void fetchAllUserProfiles() {
        if (isProfileAPI) {
            mManageChildView.showProgressDialog(R.string.msg_children_fetching_profile);
        }

        mUserService.getAllUserProfile(new IResponseHandler<List<Profile>>() {
            @Override
            public void onSuccess(GenieResponse<List<Profile>> genieResponse) {
                mManageChildView.dismissProgressDialog();
                List<Profile> profileList = genieResponse.getResult();

                mChildListProfile = new ArrayList<>();
                mGroupListProfile = new ArrayList<>();
                if (profileList != null) {
                    for (Profile profile : profileList) {
                        if (profile.isGroupUser()) {
                            mGroupListProfile.add(profile);
                        } else {
                            mChildListProfile.add(profile);
                        }
                    }
                }

                if (PreferenceUtil.isGroup().equalsIgnoreCase("true")) {
                    mIsGroup = true;
                    showGroupProfiles();
                } else {
                    PreferenceUtil.setGroup("false");
                    mIsGroup = false;
                    showChildrenProfiles();
                }
            }

            @Override
            public void onError(GenieResponse<List<Profile>> genieResponse) {
                mManageChildView.dismissProgressDialog();
            }
        });
    }

    @Override
    public void handleDeleteProfile(final Profile profile) {
        if (profile.isGroupUser()) {
//            TelemetryHandler.saveTelemetry(TelemetryBuilder.buildGEInteract(InteractionType.TOUCH, TelemetryStageId.MANAGE_GROUPS, TelemetryAction.DELETE_GROUP_INITIATED, profile.getUid()));
            TelemetryHandler.saveTelemetry(TelemetryBuilder.buildInteractEvent(EnvironmentId.USER, InteractionType.TOUCH, TelemetryAction.DELETE_GROUP_INITIATED, TelemetryStageId.MANAGE_GROUPS, profile.getUid(), ObjectType.USER));
        } else {
//            TelemetryHandler.saveTelemetry(TelemetryBuilder.buildGEInteract(InteractionType.TOUCH, TelemetryStageId.MANAGE_CHILDREN, TelemetryAction.DELETE_CHILD_INITIATED, profile.getUid()));
            TelemetryHandler.saveTelemetry(TelemetryBuilder.buildInteractEvent(EnvironmentId.USER, InteractionType.TOUCH, TelemetryAction.DELETE_CHILD_INITIATED, TelemetryStageId.MANAGE_CHILDREN, profile.getUid(), ObjectType.USER));
        }

        UserService userService = CoreApplication.getGenieAsyncService().getUserService();

        userService.deleteUser(profile.getUid(), new IResponseHandler<Void>() {
            @Override
            public void onSuccess(GenieResponse<Void> genieResponse) {
                handleProfileDeleteSuccess(profile);
            }

            @Override
            public void onError(GenieResponse<Void> genieResponse) {

            }
        });
    }

    @Override
    public void handleEditProfile(Profile profile) {
        PreferenceUtil.setFromManageUser(true);
        mManageChildView.showEditScreen(profile);
    }

    @Override
    public void handleShareProfile(Profile profile) {
        List<String> profileUidList = new ArrayList<String>();
        profileUidList.add(profile.getUid());

        Map<String, String> values = new HashMap<>();
        values.put(Constant.SHARE_SCREEN_NAME, TelemetryStageId.MANAGE_CHILDREN);
        values.put(Constant.SHARE_NAME, profile.getHandle() + ".epar");

        mManageChildView.showShareScreen(profileUidList, values);
    }

    @Override
    public void handleOpenDrawer() {
        PreferenceUtil.setGroup("");
        mManageChildView.showHomeScreen();
    }

    @Override
    public void handleOpenChildren() {
//        TelemetryHandler.saveTelemetry(TelemetryBuilder.buildGEInteract(TelemetryStageId.MANAGE_CHILDREN));
        TelemetryHandler.saveTelemetry(TelemetryBuilder.buildImpressionEvent(EnvironmentId.HOME, TelemetryStageId.MANAGE_CHILDREN, ImpressionType.VIEW));
        PreferenceUtil.setGroup("false");
        mIsGroup = false;
        showChildrenProfiles();
    }

    @Override
    public void handleOpenGroup() {
//        TelemetryHandler.saveTelemetry(TelemetryBuilder.buildGEInteract(TelemetryStageId.MANAGE_GROUPS));
        TelemetryHandler.saveTelemetry(TelemetryBuilder.buildImpressionEvent(EnvironmentId.HOME, TelemetryStageId.MANAGE_GROUPS, ImpressionType.VIEW));
        PreferenceUtil.setGroup("true");
        mIsGroup = true;
        showGroupProfiles();
    }

    @Override
    public void handleOpenAddProfileScreen() {
        if (!Util.isChildSwitcherEnabled()) {
            PreferenceUtil.setFromManageUser(true);
            mManageChildView.showAddProfileScreen(mIsGroup);
        }
    }

    @Override
    public void openProgressReport(Profile profile) {
        mManageChildView.showProgressReportScreen(profile);
    }

    @Override
    public void handleShareMultipleProfile() {
        mManageChildView.hideShareView();
        mManageChildView.showDoneView();
        mManageChildView.showSelectorLayer(true);
    }

    @Override
    public void handleShareProfile() {
        List<String> selectedProfileUidList = new ArrayList(mManageChildView.getSelectedItems());
        if (mManageChildView.getSelectedProfileCount() > 0) {

//            TelemetryHandler.saveTelemetry(TelemetryBuilder.buildGEInteract(InteractionType.TOUCH, TelemetryStageId.MANAGE_CHILDREN, TelemetryAction.SHARE_PROFILE_INITIATE, selectedProfileUidList.get(0)));
            TelemetryHandler.saveTelemetry(TelemetryBuilder.buildInteractEvent(EnvironmentId.USER,InteractionType.TOUCH, TelemetryAction.SHARE_PROFILE_INITIATE, TelemetryStageId.MANAGE_CHILDREN, selectedProfileUidList.get(0), ObjectType.USER));

            Map<String, String> values = new HashMap<>();
            values.put(Constant.SHARE_SCREEN_NAME, TelemetryStageId.MANAGE_CHILDREN);
            values.put(Constant.SHARE_NAME, "profile.epar");

            mManageChildView.showShareScreen(selectedProfileUidList, values);
        } else {
            Util.showCustomToast(R.string.msg_transfer_select_profile);
        }
    }

    @Override
    public void handleBackButtonClicked() {
        if (mManageChildView.getDoneViewVisibility() == View.VISIBLE) {
            clearProfileSelection();
        } else {
            mManageChildView.showHomeScreen();
        }
    }

    @Override
    public void clearProfileSelection() {
        mManageChildView.clearProfileSelection();
        fetchAllUserProfiles();
        mManageChildView.showSelectorLayer(false);
        mSelected.clear();
        mManageChildView.showShareView();
        mManageChildView.hideDoneView();
    }

    @Override
    public void handleImportProfile() {
        if (Build.VERSION.SDK_INT >= 23) {
            ((LandingActivity) mActivity).requestAppPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    "Genie needs to access your data",
                    RuntimePermissionsActivity.REQUEST_PERMISSION_IMPORT_PROFILE);
        } else {
            mManageChildView.showImportProfileScreen(true);
        }
    }

    @Override
    public void manageExportProfileSuccess(String exportProfile) {
        if (!StringUtil.isNullOrEmpty(exportProfile) && exportProfile.equalsIgnoreCase(Constant.EventKey.EVENT_KEY_EXPORT_PROFILE)) {
            clearProfileSelection();
            EventBus.removeStickyEvent(Constant.EventKey.EVENT_KEY_EXPORT_PROFILE);
        }
    }

    @Override
    public void showChildrenProfiles() {
        handleToggleTabSelection(true);
        if (mChildListProfile != null && !mChildListProfile.isEmpty()) {
            handleEmptyProfileList(false);
            mManageChildView.showProfilesAdapter(mChildListProfile, this, AvatarUtil.populateAvatars());
            mManageChildView.setSelectedUid(mSelected);
            if (mManageChildView.getDoneViewVisibility() == View.VISIBLE) {
                mManageChildView.showSelectorLayer(true);
                mManageChildView.hideShareView();
            } else {
                mManageChildView.showSelectorLayer(false);
                mManageChildView.showShareView();
            }
            mManageChildView.setProfilesAdapter();

        } else {
            handleEmptyProfileList(true);
            mManageChildView.hideShareView();
            mManageChildView.hideDoneView();
        }
    }

    @Override
    public void handleToggleTabSelection(boolean isChildrenTabSelected) {
        if (isChildrenTabSelected) {
            mManageChildView.showChildrenTab();
        } else {
            mManageChildView.showGroupTab();
        }
    }

    @Override
    public void handleEmptyProfileList(boolean isListEmpty) {
        if (isListEmpty) {
            mManageChildView.showEmptyProfileLayout();
        } else {
            mManageChildView.showProfilesAvailableLayout();
        }
    }

    @Override
    public void showGroupProfiles() {
        handleToggleTabSelection(false);

        if (mGroupListProfile != null && !mGroupListProfile.isEmpty()) {
            handleEmptyProfileList(false);

            mManageChildView.showProfilesAdapter(mGroupListProfile, this, AvatarUtil.populateBadges());
            mManageChildView.setSelectedUid(mSelected);

            if (mManageChildView.getDoneViewVisibility() == View.VISIBLE) {
                mManageChildView.showSelectorLayer(true);
                mManageChildView.hideShareView();
            } else {
                mManageChildView.showSelectorLayer(false);
                mManageChildView.showShareView();
            }

            mManageChildView.setProfilesAdapter();

        } else {
            handleEmptyProfileList(true);
            mManageChildView.hideShareView();
            mManageChildView.hideDoneView();
        }
    }

    @Override
    public void onIconClicked(Profile profile, boolean isSelected) {
        if (isSelected) {
            toggleSelected(profile.getUid());
        } else {
            if (!Util.isChildSwitcherEnabled()) {
                setCurrentUser(profile);
            }

        }
    }

    @Override
    public void onMoreIconClicked(Profile profile, boolean isSelected) {
        if (isSelected) {
            toggleSelected(profile.getUid());
        } else {
            mManageChildView.showProfileMoreDialog(profile);
        }
    }

    @Override
    public void toggleSelected(String uid) {
        final boolean newState = !mSelected.contains(uid);

        if (newState) {
            mSelected.add(uid);
        } else {
            mSelected.remove(uid);
        }

        mManageChildView.setSelectedUid(mSelected);
        mManageChildView.notifyAdapterDataSetChanged();
    }

    @Override
    public void setCurrentUser(final Profile profile) {
        mUserService.setCurrentUser(profile.getUid(), new IResponseHandler<Void>() {
            @Override
            public void onSuccess(GenieResponse<Void> genieResponse) {
                PreferenceUtil.setGroup("");
                Map<String, Object> map = new HashMap<>();
                map.put(TelemetryConstant.CHILDREN_ON_DEVICE, "" + (mChildListProfile.size() + mGroupListProfile.size()));

//                TelemetryHandler.saveTelemetry(TelemetryBuilder.buildGEInteract(InteractionType.TOUCH, TelemetryStageId.MANAGE_CHILDREN, TelemetryAction.SWITCH_CHILD, profile.getUid(), map));
                TelemetryHandler.saveTelemetry(TelemetryBuilder.buildInteractEvent(EnvironmentId.USER,InteractionType.TOUCH, TelemetryAction.SWITCH_CHILD, TelemetryStageId.MANAGE_CHILDREN, profile.getUid(), ObjectType.USER, map));
                mManageChildView.showHomeScreen();
            }

            @Override
            public void onError(GenieResponse<Void> genieResponse) {
                LogUtil.i(TAG, "setCurrentUser Failed");
            }
        });
    }

    @Override
    public void handleProfileDeleteSuccess(Profile profile) {
        if (profile.isGroupUser()) {
            for (int i = 0; i < mGroupListProfile.size(); i++) {
                if (mGroupListProfile.get(i).getUid().equalsIgnoreCase(profile.getUid())) {
                    mGroupListProfile.remove(i);
                }
            }
        } else {
            for (int i = 0; i < mChildListProfile.size(); i++) {
                if (mChildListProfile.get(i).getUid().equalsIgnoreCase(profile.getUid())) {
                    mChildListProfile.remove(i);
                }
            }
        }

        mManageChildView.notifyAdapterDataSetChanged();

        mManageChildView.showDeleteSuccessMessage(R.string.msg_children_profile_deletion_successful);

        if (mManageChildView.getProfilesCount() == 0) {
            handleEmptyProfileList(true);

            mManageChildView.hideShareView();
        }
    }

    @Override
    public void fetchUserProfile(boolean isProfileAPI) {
        this.isProfileAPI = isProfileAPI;
    }

    @Override
    public boolean shouldCallProfileApi() {
        return isProfileAPI;
    }

    @Override
    public void bindView(BaseView view, Context context) {
        mActivity = (Activity) context;
        mManageChildView = (ManageChildContract.View) view;
    }

    @Override
    public void unbindView() {
        mActivity = null;
        mManageChildView = null;
    }
}
