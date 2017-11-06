package org.ekstep.genie.ui.managechild;

import org.ekstep.genie.base.BasePresenter;
import org.ekstep.genie.base.BaseView;
import org.ekstep.genieservices.commons.bean.Profile;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created on 12/21/2016.
 *
 * @author anil
 */

public interface ManageChildContract {

    interface View extends BaseView {

        void showProgressDialog(int resId);

        void dismissProgressDialog();

        void showDeleteSuccessMessage(int resId);

        void showShareView();

        void hideShareView();

        void showDoneView();

        void hideDoneView();

        int getDoneViewVisibility();

        void showProfilesAdapter(List<Profile> userList, ManageChildAdapter.Callback callback, Map<String, Integer> badges);

        void setSelectedUid(Set<String> selected);

        void notifyAdapterDataSetChanged();

        void showSelectorLayer(boolean status);

        void setProfilesAdapter();

        int getProfilesCount();

        void showHomeScreen();

        void showProfileMoreDialog(Profile profile);

        void showDeleteProfileDialog(Profile profile);

        void showEditScreen(Profile profile);

        void showShareScreen(List<String> profileUidList, Map<String, String> values);

        void showProgressReportScreen(Profile profile);

        void showAddProfileScreen(boolean isGroup);

        Set<String> getSelectedItems();

        int getSelectedProfileCount();

        void clearProfileSelection();

        void showImportProfileScreen(boolean isProfile);

        void showChildrenTab();

        void showGroupTab();

        void showEmptyProfileLayout();

        void showProfilesAvailableLayout();
    }

    interface Presenter extends BasePresenter {

        void handleToggleTabSelection(boolean isChildrenTabSelected);

        void openManageChild();

        void fetchAllUserProfiles();

        void handleDeleteProfile(Profile profile);

        void handleEditProfile(Profile profile);

        void handleShareProfile(Profile profile);

        void handleOpenDrawer();

        void handleOpenChildren();

        void handleOpenGroup();

        void showGroupProfiles();

        void showChildrenProfiles();

        void handleOpenAddProfileScreen();

        void openProgressReport(Profile profile);

        void handleShareMultipleProfile();

        void handleShareProfile();

        void handleBackButtonClicked();

        void handleImportProfile();

        void manageExportProfileSuccess(String exportProfile);

        void fetchUserProfile(boolean isProfileAPI);

        void handleProfileDeleteSuccess(Profile profile);

        boolean shouldCallProfileApi();

        void clearProfileSelection();

        void setCurrentUser(Profile profile);

        void toggleSelected(String uid);

        void handleEmptyProfileList(boolean isListEmpty);
    }
}
