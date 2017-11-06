package org.ekstep.genie.ui.textbook;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageButton;
import android.widget.ProgressBar;

import org.ekstep.genie.base.BasePresenter;
import org.ekstep.genie.base.BaseView;
import org.ekstep.genie.customview.treeview.model.TreeNode;
import org.ekstep.genie.customview.treeview.view.AndroidTreeView;
import org.ekstep.genie.model.TextbookSection;
import org.ekstep.genieservices.commons.bean.Content;
import org.ekstep.genieservices.commons.bean.HierarchyInfo;

import java.io.File;
import java.util.List;
import java.util.Map;

/**
 * Created by shriharsh on 8/2/17.
 */

public interface TextbookContract {

    interface View extends BaseView {

        void showTableOfContents(TreeNode root);

        void finish();

        void setTextbookName(String name);

        void showIcon(String appIcon);

        void showLocalIcon(File file);

        void showDefaultIcon(int ic_launcher);

        void setIsFromDownload(boolean mIsFromDownloadsScreen);

        void showLanguage(String language);

        void hideLanguage();

        void showGrade(String type);

        void hideGrade();

        void hideGradeDivider();

        void showTextBookShelf(List<TextbookSection> textbookSectionsList, boolean mIsFromDownloadsScreen);

        void showDetails(Content content, List<HierarchyInfo> contentInfoList, boolean isFromDownloads, boolean isSpineAvailable, boolean isFromCollectionOrTextbook, boolean isParentTextbook);

        void showCustomToast(String string);

        void refreshAdapter(List<TextbookSection> textbookSectionsList);

        void generateTOCInteractEvent(String identifier);

        void collapseAllLeaf();

        void scrollTo(int position);

        void hideNoTextBooksView();

        void showTextBooksRecyclerView();

        void showNoTextBooksView();

        void hideTextBooksRecyclerView();

        void generateSectionViewedEvent(String identifier, List<Map<String, Object>> mSectionMapList);

        void showDownloadedLessonText();

        void hideDownloadedLessonText();

        void showTickImage();

        void hideTickImage();

        void setIsRequiredToCallApi(boolean isRequiredToCallApi);

        AndroidTreeView getAndroidTreeView();

        LinearLayoutManager getLinearLayoutManager();

        void showDownloadDialog(List<Content> contentsToBeDownloaded, int numberOfNotDownloadedContents, String sectionName, String s, ImageButton chapterDownloadButton, ProgressBar progressBarChapterDownload);

        void showFeedbackDialog(String identifier, float previousRating);

        void showProgressReportActivity(Content content);
    }

    interface Presenter extends BasePresenter {

        void fetchTextbooks(Bundle arguments);

        void fetchTableOfContentsAndLessons(boolean refreshLayout);

        void handleBackButtonClick();

        void downloadedLessons();

        void handleDetailsClick();

        void handleMoreClick(Content content);

        void handleContentClick(Content content);

        void handleViewDetailsClick(Content content);

        void handleFeedbackClick(Content content);

        void handleDeleteClick(Content content);

        void handleTreeNodeClick(TreeNode node, Object value);

        void generateLocalCount(List<TextbookSection> textbookSectionList, int offset);

        void handleScrollChanged(RecyclerView recyclerView, int newState);

        void manageImportSuccess(String identifier);

        void manageContentDeleteSuccess(String identifier);

        void checkToShowProgressOrDownloadChapterButton(List<Content> contentList, android.view.View downloadChapterButton, ProgressBar progressBarChapterDownload);

        void calculateSize(List<Content> gameList, String sectionName, ImageButton chapterDownloadButton, ProgressBar progressBarChapterDownload);

        boolean isDownloadingAnyChapter(TextbookSection textbookSection);

        void generateHomeInteractEvent(String identifier, int localCount, int totalCount, int position);

        void setLearnerState(LinearLayoutManager mLinearLayoutManager);

        void checkAndGetTableOfContentsAndLessonsFromApi(boolean isRequiredToCallApi);

        void deleteContent(String identifier);

        void downloadAll(List<Content> contentsToBeDownloaded);

        /**
         * This method updates the isAvailableLocally value to true if content is imported; false if content is deleted
         * for all the lessons list available, having matched with identifier of the imported/deleted content
         *
         * @param textbookSectionList {@link List<TextbookSection>}
         */
        void updateTextbookSectionList(List<TextbookSection> textbookSectionList, String identifier, boolean isAvailableLocally);

        void manageImportAndDeleteSuccess(Object response);

        void removeContentToBeDeletedValue();

        void handleProgressClick(Content content);
    }
}
