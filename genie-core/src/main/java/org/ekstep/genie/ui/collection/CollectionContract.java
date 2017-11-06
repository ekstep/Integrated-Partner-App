package org.ekstep.genie.ui.collection;

import android.os.Bundle;
import android.widget.ProgressBar;

import org.ekstep.genie.base.BasePresenter;
import org.ekstep.genie.base.BaseView;
import org.ekstep.genieservices.commons.bean.Content;
import org.ekstep.genieservices.commons.bean.ContentImportResponse;
import org.ekstep.genieservices.commons.bean.CorrelationData;
import org.ekstep.genieservices.commons.bean.HierarchyInfo;

import java.io.File;
import java.util.List;

/**
 * Created on 8/2/17.
 *
 * @author shriharsh
 */
public interface CollectionContract {

    interface View extends BaseView {

        void showCollectionShelf(List<Content> contentList, boolean mIsFromDownloadsScreen);

        void refreshAdapter(List<Content> contentList, boolean mIsFromDownloadsScreen);

        void showContentDetails(Content content, List<HierarchyInfo> contentInfoList, boolean isFromDownloads, boolean isSpineAvailable, boolean isParentCollection);

        void showIcon(String appIcon);

        void showLocalIcon(File file);

        void showDefaultIcon(int ic_launcher);

        void setCollectionName(String name);

        void finish();

        void setCollectionSize(String size);

        void notifyAdapterDataSetChanged();

        void showCustomToast(String string);

        void showDownloadLayout();

        void hideDownloadLayout();

        void disableDownloadLayout();

        void showDownloadAllLayout();

        void hideDownloadAllLayout();

        void showDownloadingLayout();

        void hideDownloadingLayout();

        void showNoNetworkError();

        void showLanguage(String language);

        void hideLanguage();

        void showGrade(String grade);

        void hideGrade();

        void hideGradeDivider();

        void showDeleteConfirmationLayout(String identifier, int refCount);

        void showFeedbackDialog(String identifier, float previousRating);

        void showMoreDialog(Content content);

        void showProgressReportActivity(Content content);

        void showDeviceMemoryLowDialog();

        void showSdcardMemoryLowDialog();
    }

    interface Presenter extends BasePresenter {

        void fetchBundleExtras(Bundle arguments);

        void showCollectionInfo();

        void showChildContents(boolean refreshLayout);

        void handleBackButtonClick();

        void handleDownloadAllClick();

        void showCollectionDetails();

        void handleMoreClick(Content content);

        void handleContentClick(Content content);

        void handleDeleteClick(Content content);

        void handleFeedbackClick(Content content);

        void handleViewDetailsClick(Content content);

        void colorProgressBar(ProgressBar progressBar);

        void checkIfDownloadInProgress(String identifier);

        void manageImportSuccess(ContentImportResponse importResponse);

        void handleDownloadVisibilityByContentSize(List<Content> contentList);

        void calculateLocalContentCount(List<Content> contentList);

        List<String> getLocallyAvailableCollectionContent();

        List<Content> getModifiedContentList(List<Content> contentList);

        void deleteContent(String identifier, boolean isAccessedElseWhere);

        void manageDeleteContentSuccess(String identifier, boolean isAccessedElseWhere);

        void generateInteractEvent(String identifier, int localCount, int totalCount, List<CorrelationData> cData);

        void setCollectionContentIcon();

        void postRequiredStickyEvents();

        void handleProgressClick(Content content);
    }
}
