package org.ekstep.genie.ui.mycontent;

import org.ekstep.genie.base.BasePresenter;
import org.ekstep.genie.base.BaseView;
import org.ekstep.genie.model.SelectedContent;
import org.ekstep.genieservices.commons.bean.Content;
import org.ekstep.genieservices.commons.bean.ContentSortCriteria;

import java.util.List;
import java.util.Map;

/**
 * Created by Sneha on 1/24/2017.
 */

public interface MyContentContract {

    interface View extends BaseView {

        void dismissProgressDialog();

        void showDoneBtn();

        void hideDoneBtn();

        void showShareBtn();

        void hideShareBtn();

        void disableMenuImportBtn();

        void enableMenuImportBtn();

        int getMenuBtnVisibility();

        void notifyAdapterDataSetChanged();

        void showSelectorLayer();

        void hideSelectorLayer();

        void clearSelected();

        void showMyContentRecyclerView();

        void hideMyContentRecyclerView();

        List<SelectedContent> getSelectedItems();

        int getSelectedCount();

        int getItemCount();

        void showCustomToast(int message);

        void hideNoContentCard();

        void showNoContentCard();

        void showLocalContents(List<Content> gameList);

        void updateLocalContentList(List<Content> updatedContentList);

        void toggleSelected(SelectedContent content);

        void showMoreActionDialog(Content content);

        MyContentAdapter getContentAdapter();

        void showContentisDraftMessage();

        void showContentDetailsActivity(Content content);

        void showShareOptionsLayout(Content content);

        void showProgressReportActivity(Content content);

        void showFeedBackDialog(String identifier, float previuosRating);

        void showDeleteConfirmationLayout(long size, int refCount, String identifier);

        void showSelectedContentOptions();

        void hideNormalOptions();

        void hideSelectedContentOptions();

        void showNormalOptions();

        void showSortDialog();

        void showContentHeader();

        void hideContentHeader();

        void showNestedContentHeader();

        void hideNestedContentHeader();

        void showHeaderBreadCrumb(List<Map<String, String>> contentHeader);

        void smoothScrollTo(int position);

        void setSelectedContentSize(long size);

        void setSelectedContentCount(int count);

        void showDeleteContentConfirmationDialog(List<SelectedContent> selectedContents);

        void showContentDeletedSuccessfullMessage(String size);

        void showContentDeletedSuccessfullyMessage(long size, final int refCount, String identifier);

        void showContentSort();

        void hideContentSort();

        void showAscendingSortIcon(boolean isSizeSort);

        void showDescendingSortIcon(boolean isSizeSort);
    }

    interface Presenter extends BasePresenter {

        void importContent();

        void handleShareVisibility(int visibility);

        void handleBackButtonClicked();

        void handleSortButtonClick();

        void clearSelection();

        void shareButtonClicked();

        void renderMyContent();

        void shareContent();

        void renderLocalContents();

        void renderLocalContents(ContentSortCriteria contentSortCriteria);

        void deleteContent(List<SelectedContent> selectedContentList);

        void toggleContentIcon(Content content);

        void playContent(Content content);

        void showMoreDialog(Content content);

        void handleContentDetailClick(Content content);

        void handleShareClick(Content content);

        void handleProgressClick(Content content);

        void handleFeedbackClick(Content content);

        void handleDeleteClick(Content content);

        void postDeleteSuccessEvent();

        void manageImportAndDeleteSuccess(Object response);

        void manageLocalContentImportSuccess(String importSuccess);

        void manageContentExportSuccess(String exportSuccess);

        void setContentToViews(MyContentAdapter.ContentWrapper contentwrapper, MyContentAdapter.ContentViewHolder contentViewHolder, MyContentAdapter myContentAdapter);

        void handleContentItemClick(MyContentAdapter.ContentWrapper contentwrapper);

        void handleHeaderBackClick();

        void clearBreadcrumbHeader();

        void onBreadcrumbHeaderClick(int position, String identifier);

        void handleContentSortBySize();

        void handleContentSortByLastUsed();
    }
}
