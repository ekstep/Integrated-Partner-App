package org.ekstep.genie.ui.managechild;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.ekstep.genie.R;
import org.ekstep.genie.base.BasePresenter;
import org.ekstep.genie.base.BaseView;
import org.ekstep.genie.base.IPresenterFactory;
import org.ekstep.genie.fragment.BaseFragment;
import org.ekstep.genie.ui.addchild.AddChildActivity;
import org.ekstep.genie.ui.importarchive.ImportArchiveActivity;
import org.ekstep.genie.ui.landing.LandingActivity;
import org.ekstep.genie.ui.progressreports.ProgressReportsActivity;
import org.ekstep.genie.ui.share.ShareActivity;
import org.ekstep.genie.util.Constant;
import org.ekstep.genie.util.DeviceUtility;
import org.ekstep.genie.util.ShowProgressDialog;
import org.ekstep.genie.util.Util;
import org.ekstep.genie.util.preference.PreferenceUtil;
import org.ekstep.genieservices.commons.bean.Profile;
import org.ekstep.genieservices.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;
import java.util.Map;
import java.util.Set;


/**
 * Created on 12/21/2016.
 *
 * @author anil
 */
public class ManageChildFragment extends BaseFragment
        implements ManageChildContract.View, View.OnClickListener {

    private ManageChildAdapter mManageChildAdapter;

    private RecyclerView mRecyclerView;
    private View mView_children;
    private View mView_group;

    private LinearLayout mLayoutNoUser;
    private Dialog mDialog;

    private View mFabAddUser;
    private View mMenuShareBtn;
    private View mMenuDoneBtn;
    private View mMenuMoreBtn;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_manage_child, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mView_children = view.findViewById(R.id.view_children);
        mView_group = view.findViewById(R.id.view_groups);

        mLayoutNoUser = (LinearLayout) view.findViewById(R.id.layout_no_User);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerview_user_list);

        mFabAddUser = view.findViewById(R.id.fab_add_user);
        mMenuShareBtn = view.findViewById(R.id.btn_share);
        mMenuDoneBtn = view.findViewById(R.id.btn_done);
        mMenuMoreBtn = view.findViewById(R.id.layout_more);

        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2, LinearLayoutManager.VERTICAL, false));

        view.findViewById(R.id.back_btn).setOnClickListener(this);
        view.findViewById(R.id.rl_children).setOnClickListener(this);
        view.findViewById(R.id.rl_group).setOnClickListener(this);
        view.findViewById(R.id.fab_add_userCenter).setOnClickListener(this);
        mFabAddUser.setOnClickListener(this);
        mMenuShareBtn.setOnClickListener(this);
        mMenuDoneBtn.setOnClickListener(this);
        mMenuMoreBtn.setOnClickListener(this);

        PreferenceUtil.setCoRelationIdContext(null);

        ((ManageChildPresenter)presenter).fetchUserProfile(true);
        ((ManageChildPresenter)presenter).openManageChild();
    }

    @Override
    protected IPresenterFactory getPresenterFactory() {
        return new IPresenterFactory() {
            @Override
            public BasePresenter create() {
                return new ManageChildPresenter();
            }
        };
    }

    @Override
    protected String getTAG() {
        return getClass().getName();
    }

    @Override
    protected BaseView getBaseView() {
        return this;
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.back_btn) {
            ((ManageChildPresenter) presenter).handleOpenDrawer();

        } else if (id == R.id.rl_children) {
            ((ManageChildPresenter) presenter).handleOpenChildren();

        } else if (id == R.id.rl_group) {
            ((ManageChildPresenter) presenter).handleOpenGroup();

        } else if (id == R.id.fab_add_user || id == R.id.fab_add_userCenter) {
            ((ManageChildPresenter) presenter).handleOpenAddProfileScreen();

        } else if (id == R.id.layout_more) {
            ((ManageChildPresenter) presenter).handleImportProfile();

        } else if (id == R.id.btn_share) {
            mMenuMoreBtn.setClickable(false);
            ((ManageChildPresenter) presenter).handleShareMultipleProfile();

        } else if (id == R.id.btn_done) {
            ((ManageChildPresenter) presenter).handleShareProfile();

        } else {
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.registerSubscriber(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.unregisterSubscriber(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        mMenuMoreBtn.setClickable(true);
        if (!((ManageChildPresenter)presenter).shouldCallProfileApi()) {
            ((ManageChildPresenter)presenter).fetchAllUserProfiles();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        ((ManageChildPresenter)presenter).fetchUserProfile(false);
    }

    @Override
    public void showProgressDialog(int resId) {
        ShowProgressDialog.showProgressDialog(getActivity(), getResources().getString(resId));
    }

    @Override
    public void showDeleteSuccessMessage(int resId) {
        Util.showCustomToast(resId);
    }

    @Override
    public void dismissProgressDialog() {
        ShowProgressDialog.dismissDialog();
    }

    @Override
    public void showEmptyProfileLayout() {
        mLayoutNoUser.setVisibility(View.VISIBLE);
        mFabAddUser.setVisibility(View.GONE);
        mRecyclerView.setVisibility(View.GONE);
    }

    @Override
    public void showProfilesAvailableLayout() {
        mLayoutNoUser.setVisibility(View.GONE);
        mFabAddUser.setVisibility(View.VISIBLE);
        mRecyclerView.setVisibility(View.VISIBLE);
    }

    @Override
    public void showChildrenTab() {
        mView_children.setBackgroundColor(getResources().getColor(R.color.header_text_color));
        mView_group.setBackgroundColor(getResources().getColor(R.color.black7));
    }

    @Override
    public void showGroupTab() {
        mView_children.setBackgroundColor(getResources().getColor(R.color.black7));
        mView_group.setBackgroundColor(getResources().getColor(R.color.header_text_color));
    }

    @Override
    public void showShareView() {
        mMenuShareBtn.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideShareView() {
        mMenuShareBtn.setVisibility(View.GONE);
    }

    @Override
    public void showDoneView() {
        mMenuDoneBtn.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideDoneView() {
        mMenuDoneBtn.setVisibility(View.GONE);
    }

    @Override
    public int getDoneViewVisibility() {
        return mMenuDoneBtn.getVisibility();
    }

    @Override
    public void showProfilesAdapter(List<Profile> userList, ManageChildAdapter.Callback callback, Map<String, Integer> badges) {
        mManageChildAdapter = new ManageChildAdapter(getActivity(), userList, callback, badges);
    }

    @Override
    public void setSelectedUid(Set<String> selected) {
        mManageChildAdapter.setSelectedUid(selected);
    }

    @Override
    public void notifyAdapterDataSetChanged() {
        mManageChildAdapter.notifyDataSetChanged();
    }

    @Override
    public void showSelectorLayer(boolean status) {
        mManageChildAdapter.showSelectorLayer(status);
    }

    @Override
    public void setProfilesAdapter() {
        mRecyclerView.setAdapter(mManageChildAdapter);
    }

    @Override
    public int getProfilesCount() {
        return mManageChildAdapter.getItemCount();
    }

    @Override
    public void showHomeScreen() {
        if (getActivity() != null) {
            ((LandingActivity) getActivity()).showHome(false);
        }
    }

    @Override
    public void showProfileMoreDialog(final Profile profile) {
        mDialog = new Dialog(mActivity);
        mDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        mDialog.setContentView(R.layout.dialog_user_action);
        ((TextView) mDialog.findViewById(R.id.txt_user_name)).setText(profile.getHandle());

        //delete
        mDialog.findViewById(R.id.layout_delete).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialog.dismiss();
                showDeleteProfileDialog(profile);
            }
        });

        //edit
        mDialog.findViewById(R.id.layout_edit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialog.dismiss();

                ((ManageChildPresenter)presenter).handleEditProfile(profile);
            }
        });

        //share
        mDialog.findViewById(R.id.layout_share).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialog.dismiss();

                ((ManageChildPresenter)presenter).handleShareProfile(profile);
            }
        });

        //progress
        mDialog.findViewById(R.id.layout_report_progress).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialog.dismiss();

                ((ManageChildPresenter)presenter).openProgressReport(profile);
            }
        });

        mDialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        mDialog.setCancelable(true);

        DeviceUtility.displayFullScreenDialog(mDialog, getActivity());
    }

    @Override
    public void showDeleteProfileDialog(final Profile profile) {
        mDialog = new Dialog(mActivity);
        mDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        mDialog.setContentView(R.layout.dialog_delete_confirmation);
        mDialog.findViewById(R.id.delete_yes).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialog.dismiss();
                ((ManageChildPresenter)presenter).handleDeleteProfile(profile);
            }
        });
        mDialog.findViewById(R.id.delete_no).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialog.dismiss();
            }
        });

        DeviceUtility.displayFullScreenDialog(mDialog, mActivity);
    }

    @Override
    public void showEditScreen(Profile profile) {
        Intent intent = new Intent(getActivity(), AddChildActivity.class);
        intent.putExtra(Constant.BUNDLE_UPDATED_PROFILE, profile);
        intent.putExtra(Constant.BUNDLE_EDIT_MODE, true);
        startActivity(intent);
    }

    @Override
    public void showShareScreen(List<String> profileUidList, Map<String, String> values) {
        ShareActivity.startProfileExportIntent(getActivity(), profileUidList, values);
    }

    @Override
    public void showProgressReportScreen(Profile profile) {
        Intent intent = new Intent(getActivity(), ProgressReportsActivity.class);
        intent.putExtra(Constant.PROFILE_SUMMARY, profile);
        getActivity().startActivity(intent);
    }

    @Override
    public void showAddProfileScreen(boolean isGroup) {
        Intent intent = new Intent(getActivity(), AddChildActivity.class);
        intent.putExtra(Constant.BUNDLE_GROUP_MODE, isGroup);
        getActivity().startActivity(intent);
    }

    @Override
    public Set<String> getSelectedItems() {
        return mManageChildAdapter.getSelectedItems();
    }

    @Override
    public int getSelectedProfileCount() {
        return mManageChildAdapter.getSelectedCount();
    }

    @Override
    public void clearProfileSelection() {
        mManageChildAdapter.clearSelected();
    }

    @Override
    public void showImportProfileScreen(boolean isProfile) {
        Intent intent = new Intent(getActivity(), ImportArchiveActivity.class);
        intent.putExtra(Constant.IS_PROFILE, isProfile);
        startActivity(intent);
    }

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void onExportProfileResponse(String exportProfile) throws InterruptedException {
        ((ManageChildPresenter)presenter).manageExportProfileSuccess(exportProfile);
    }
}
