package org.ekstep.genie.ui.textbook;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;

import org.ekstep.genie.CoreApplication;
import org.ekstep.genie.R;
import org.ekstep.genie.base.BaseView;
import org.ekstep.genie.model.TextbookSection;
import org.ekstep.genie.model.enums.ContentType;
import org.ekstep.genie.telemetry.TelemetryStageId;
import org.ekstep.genie.util.Constant;
import org.ekstep.genie.util.LogUtil;
import org.ekstep.genie.util.PlayerUtil;
import org.ekstep.genie.util.Util;
import org.ekstep.genie.util.geniesdk.ContentUtil;
import org.ekstep.genieservices.async.ContentService;
import org.ekstep.genieservices.commons.IResponseHandler;
import org.ekstep.genieservices.commons.bean.Content;
import org.ekstep.genieservices.commons.bean.ContentData;
import org.ekstep.genieservices.commons.bean.ContentDelete;
import org.ekstep.genieservices.commons.bean.ContentDeleteRequest;
import org.ekstep.genieservices.commons.bean.ContentDeleteResponse;
import org.ekstep.genieservices.commons.bean.ContentDetailsRequest;
import org.ekstep.genieservices.commons.bean.ContentImportResponse;
import org.ekstep.genieservices.commons.bean.GenieResponse;
import org.ekstep.genieservices.commons.utils.StringUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by shriharsh on 27/2/17.
 */

public class DownloadedTextbooksPresenter implements DownloadedTextbooksContract.Presenter {

    private static final String TAG = DownloadedTextbooksPresenter.class.getSimpleName();
    private DownloadedTextbooksContract.View mDownloadedTextbookView;
    private Context mContext;
    private Content mContent;
    private ContentData mContentData;
    private String mLocalPath;
    private boolean mIsFromDownloadsScreen;
    private List<Content> mDownloadedContentsFromTextbookSectionList;
    private ContentService mContentService = null;
    private Content mContentToBeDeleted;

    public DownloadedTextbooksPresenter() {
        mContentService = CoreApplication.getGenieAsyncService().getContentService();
    }

    @Override
    public void fetchBundleExtras(Bundle arguments) {
        mContent = (Content) arguments.getSerializable(Constant.BundleKey.BUNDLE_KEY_CONTENT);
        mContentData = mContent.getContentData();

        mLocalPath = arguments.getString(Constant.BundleKey.BUNDLE_KEY_LOCAL_PATH, "");
        mIsFromDownloadsScreen = arguments.getBoolean(Constant.BundleKey.BUNDLE_KEY_IS_FROM_DOWNLOADS, false);

        mDownloadedTextbookView.setIsFromDownload(mIsFromDownloadsScreen);
        LogUtil.i(TAG, "LocalPath :: " + mLocalPath);

        setContentIcon();

        mDownloadedTextbookView.setTextbookName(mContentData.getName());

        if (mContentData.getGradeLevel() != null && mContentData.getGradeLevel().size() > 0) {
            mDownloadedTextbookView.showGrade(mContentData.getGradeLevel().get(0));
        } else {
            mDownloadedTextbookView.hideGrade();
            mDownloadedTextbookView.hideGradeDivider();
        }

        if (mContentData.getLanguage() != null && mContentData.getLanguage().size() > 0) {
            mDownloadedTextbookView.showLanguage(mContentData.getLanguage().get(0));
        } else {
            mDownloadedTextbookView.hideLanguage();
        }

    }

    @Override
    public void showTextbookDetails() {
        LogUtil.d("showTextbookDetails", "mContentData " + mContentData);
        mDownloadedTextbookView.showDetails(mContent, null, mIsFromDownloadsScreen, true, true, true);
    }


    @Override
    public void showAllDownloadedContents(final boolean refreshLayout) {
        if (mDownloadedContentsFromTextbookSectionList != null && mDownloadedContentsFromTextbookSectionList.size() > 0) {
            mDownloadedTextbookView.showDownloadedLessonsView();
            mDownloadedTextbookView.hideGetContentNowView();
            if (refreshLayout) {
                if (mDownloadedContentsFromTextbookSectionList.size() > 0) {
                    mDownloadedTextbookView.showAllDownloadedTextbookContents(mDownloadedContentsFromTextbookSectionList, mIsFromDownloadsScreen);
                }
            } else {
                mDownloadedTextbookView.refreshAdapter(mDownloadedContentsFromTextbookSectionList);
            }
        } else {
            mDownloadedTextbookView.showGetContentNowView();
            mDownloadedTextbookView.hideDownloadedLessonsView();
        }
    }

    @Override
    public void handleMoreClick(Content content) {
        mDownloadedTextbookView.showMoreDialog(content);
    }

    @Override
    public void handleContentClick(Content content) {
        if (content.isAvailableLocally()) {
            PlayerUtil.startContent(mContext, content, TelemetryStageId.CONTENT_DETAIL, mIsFromDownloadsScreen);
        } else {
            mDownloadedTextbookView.showDetails(content, (ArrayList) content.getHierarchyInfo(), mIsFromDownloadsScreen, true, true, false);
        }
    }

    @Override
    public void handleContentDeleteClick(final Content content) {
        mContentToBeDeleted = content;
        String identifier = content.getIdentifier();
        if (!TextUtils.isEmpty(identifier) && mContentToBeDeleted != null) {
            ContentDeleteRequest.Builder builder = new ContentDeleteRequest.Builder();
            builder.add(new ContentDelete(identifier, true));

            mContentService.deleteContent(builder.build(), new IResponseHandler<List<ContentDeleteResponse>>() {
                @Override
                public void onSuccess(GenieResponse<List<ContentDeleteResponse>> genieResponse) {
                    onDeleteContentSuccess(content);
                }

                @Override
                public void onError(GenieResponse<List<ContentDeleteResponse>> genieResponse) {
                    Log.e(TAG, "error deleting content");
                }
            });
        }
    }


    @Override
    public void manageImportSuccess(List<TextbookSection> textbookSectionList, ContentImportResponse importResponse) {
        //update the content of the textbook section that its downloaded
        List<TextbookSection> updatedTextbookSectionList = updateTextbookSectionList(textbookSectionList, importResponse.getIdentifier());

        //re-fetch all the  textbook section list from the existing
        getAllDownloadedContentsFromTextbookSectionList(updatedTextbookSectionList);

        mDownloadedTextbookView.refreshAdapter(mDownloadedContentsFromTextbookSectionList);
    }

    @Override
    public void removeContentToBeDeletedValue() {
        mContentToBeDeleted = null;
    }

    private List<TextbookSection> updateTextbookSectionList(List<TextbookSection> textbookSectionList, String identifier) {
        List<TextbookSection> updatedTextbookSectionList = null;
        if (textbookSectionList != null && textbookSectionList.size() > 0 && !StringUtil.isNullOrEmpty(identifier)) {
            updatedTextbookSectionList = new ArrayList<>();
            updatedTextbookSectionList.addAll(textbookSectionList);

            for (TextbookSection eachTextbookSection : updatedTextbookSectionList) {
                if (eachTextbookSection.getSectionContentIdentifiers().contains(identifier)) {
                    for (Content eachContentWithinTextbookSection : eachTextbookSection.getSectionContents()) {
                        if (eachContentWithinTextbookSection.getIdentifier().equalsIgnoreCase(identifier)) {
                            eachContentWithinTextbookSection.setAvailableLocally(true);
                        }
                    }
                }
            }
        }
        return updatedTextbookSectionList;
    }

    @Override
    public void getAllDownloadedContentsFromTextbookSectionList(List<TextbookSection> textbookSectionList) {
        mDownloadedContentsFromTextbookSectionList = new ArrayList<>();
        for (TextbookSection eachSection : textbookSectionList) {
            List<Content> eachSectionsLessons = eachSection.getSectionContents();
            if (eachSectionsLessons != null && eachSectionsLessons.size() > 0) {
                for (Content content : eachSectionsLessons) {
                    if (content.isAvailableLocally()) {
                        mDownloadedContentsFromTextbookSectionList.add(content);
                    }
                }
            }
        }
    }

    @Override
    public void handleFeedbackClick(Content content) {
        ContentDetailsRequest.Builder detailsRequest = new ContentDetailsRequest.Builder().forContent(content.getIdentifier());
        mContentService.getContentDetails(detailsRequest.build(), new IResponseHandler<Content>() {
            @Override
            public void onSuccess(GenieResponse<Content> genieResponse) {
                Content selectedContent = genieResponse.getResult();
                // DialogUtils.showFeedbackDialog(mContext, selectedContent, null); // TODO need to check whether dialog has be shown or not
            }

            @Override
            public void onError(GenieResponse<Content> genieResponse) {
                Log.e(TAG, "error " + genieResponse.getErrorMessages().get(0));
            }
        });
    }

    @Override
    public void handleDetailsClick(Content content) {
        ContentUtil.navigateToContentDetails(mContext, content, content.getHierarchyInfo(), mIsFromDownloadsScreen, true, true, false);
    }

    public void onDeleteContentSuccess(Content content) {
        if (mDownloadedTextbookView == null) {
            return;
        }

        if (mContentToBeDeleted != null) {
            mDownloadedTextbookView.showCustomToast(mContext.getResources().getString(R.string.msg_content_deletion_sucessfull));

            if (mContentToBeDeleted.getContentType().equalsIgnoreCase(ContentType.COLLECTION)
                    || mContentToBeDeleted.getContentType().equalsIgnoreCase(ContentType.TEXTBOOK)) {
                return;
            }
        }

        content.setAvailableLocally(false);

        if (mDownloadedContentsFromTextbookSectionList != null && mDownloadedContentsFromTextbookSectionList.size() > 0) {
            mDownloadedContentsFromTextbookSectionList.remove(content);
            mDownloadedTextbookView.refreshAdapter(mDownloadedContentsFromTextbookSectionList);
        } else {
            mDownloadedTextbookView.hideDownloadedLessonsView();
            mDownloadedTextbookView.showGetContentNowView();
        }
    }

    private void setContentIcon() {
        String appIcon = mContentData.getAppIcon();
        if (appIcon != null) {
            if (Util.isValidURL(appIcon)) {
                mDownloadedTextbookView.showIcon(appIcon);
            } else {
                mDownloadedTextbookView.showLocalIcon(new File(mLocalPath + "/" + appIcon));
            }
        } else {
            mDownloadedTextbookView.showDefaultIcon(R.drawable.ic_launcher);
        }
    }

    @Override
    public void bindView(BaseView view, Context context) {
        mDownloadedTextbookView = (DownloadedTextbooksContract.View) view;
        mContext = context;
    }

    @Override
    public void unbindView() {
        mDownloadedTextbookView = null;
        mContext = null;
    }
}
