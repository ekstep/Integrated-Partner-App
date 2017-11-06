package org.ekstep.genie.ui.textbook;

import android.os.Bundle;

import org.ekstep.genie.base.BasePresenter;
import org.ekstep.genie.base.BaseView;
import org.ekstep.genie.model.TextbookSection;
import org.ekstep.genieservices.commons.bean.Content;
import org.ekstep.genieservices.commons.bean.ContentImportResponse;
import org.ekstep.genieservices.commons.bean.HierarchyInfo;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by shriharsh on 27/2/17.
 */

public interface DownloadedTextbooksContract {

    interface View extends BaseView {

        void setIsFromDownload(boolean mIsFromDownloadsScreen);

        void showIcon(String appIcon);

        void showLocalIcon(File file);

        void showDefaultIcon(int ic_launcher);

        void setTextbookName(String name);

        void showAllDownloadedTextbookContents(List<Content> gameList, boolean isFromDownloadedScreen);

        void showLanguage(String language);

        void hideLanguage();

        void showGrade(String type);

        void hideGrade();

        void hideGradeDivider();

        void showDetails(Content content, ArrayList<HierarchyInfo> contentInfoList, boolean isFromDownloads, boolean isSpineAvailable, boolean isFromCollectionOrTextbook, boolean isParentTextbook);

        void showCustomToast(String msg);

        void refreshAdapter(List<Content> gameList);

        void showDownloadedLessonsView();

        void hideGetContentNowView();

        void showGetContentNowView();

        void hideDownloadedLessonsView();

        void showMoreDialog(Content content);
    }

    interface Presenter extends BasePresenter {

        void fetchBundleExtras(Bundle arguments);

        void showTextbookDetails();

        void showAllDownloadedContents(boolean refreshLayout);

        void handleMoreClick(Content content);

        void handleContentClick(Content content);

        void handleDetailsClick(Content content);

        void handleFeedbackClick(Content content);

        void handleContentDeleteClick(Content content);

        void getAllDownloadedContentsFromTextbookSectionList(List<TextbookSection> mTextbookSectionList);

        void manageImportSuccess(List<TextbookSection> textbookSectionList, ContentImportResponse importResponse);

        void removeContentToBeDeletedValue();
    }

}
