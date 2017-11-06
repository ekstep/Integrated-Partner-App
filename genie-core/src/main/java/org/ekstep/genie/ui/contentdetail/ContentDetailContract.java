package org.ekstep.genie.ui.contentdetail;

import android.os.Bundle;
import android.widget.RatingBar;

import org.ekstep.genie.base.BasePresenter;
import org.ekstep.genie.base.BaseView;
import org.ekstep.genieservices.commons.bean.ContentImportResponse;
import org.ekstep.genieservices.commons.bean.DownloadProgress;

import java.io.File;
import java.util.List;

/**
 * Created on 20/12/16.
 *
 * @author swayangjit
 */
public interface ContentDetailContract {

    interface View extends BaseView {

        void setProgressIndicator(boolean active);

        void showTitle(String title);

        void showName(String name);

        void showIcon(String appIcon);

        void showLocalIcon(File appIcon);

        void showDefaultIcon(int appIcon);

        void showLanguage(String language);

        void hideLanguage();

        void showType(String type);

        void hideType();

        void showSize(String size);

        void hideSize();

        void showPublisher(String publisher);

        void hidePublisher();

        void showCreator(String creator);

        void hideCreator();

        void showDescription(String description);

        void showMoreDescription();

        void showLicense(String text);

        void showCopyRight(String copyRight);

        void hideCopyRight();

        void showUserIcon();

        void showUserRating(String rating);

        void showUserRatingBar(float rating);

        void showFeedBackLayout();

        void hideFeedBackLayout();

        void showAverageRating(String rating);

        void showAverageRatingBar(float rating);

        void showDownload();

        void showUpdate();

        void hideUpdate();

        void showDownloadProgress();

        void hideDownloadProgress();

        void showDownloadProgressText();

        void setDownloadProgressText(String text);

        void setDownloadProgress(int progress);

        void hideDownloadProgressText();

        void hideDownload();

        void showPlay();

        void hidePlay();

        void showDelete();

        void hideDelete();

        void showSuccessMessage(int message);

        void showFailureMessage(int message);

        void handleMissingContent();

        void showShareOption();

        void hideShareOption();

        void showPreview();

        void hidePreview();

        void showOpen();

        void showDeleteConfirmationDialog(int refCount, String identifier);

        void showFeedBackSuccessfulMessage();

        void updateRating(RatingBar ratingBar, float rating);

        void showContentScreenshots(List<String> screenshotUrlList);

        void showDeviceMemoryLowDialog();

        void showSdcardMemoryLowDialog();
    }

    interface Presenter extends BasePresenter {

        void fetchBundleExtras(Bundle bundle);

        void showContentInfo();

        void initDownload();

        void downloadContent();

        void cancelDownload();

        void handleDeleteClick();

        void playContent();

        void shareContent();

        void rateContent();

        void previewContent();

        void showMoreDescription(int lineCount);

        void handleBackButtonClick();

        void onResume();

        void updateProgressBar(DownloadProgress progress);

        void manageImportSuccess(ContentImportResponse importResponse);

        void deleteContent(String identifier);

        void handleFeedBackSuccess(float rating, boolean showSuccessfullMessage);

        void postRequiredStickyEvents();
    }

}
