package org.ekstep.genie.ui.mycontent;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.text.TextUtils;
import android.view.View;

import org.ekstep.genie.CoreApplication;
import org.ekstep.genie.R;
import org.ekstep.genie.activity.RuntimePermissionsActivity;
import org.ekstep.genie.base.BaseView;
import org.ekstep.genie.model.SelectedContent;
import org.ekstep.genie.model.enums.ContentType;
import org.ekstep.genie.telemetry.TelemetryAction;
import org.ekstep.genie.telemetry.TelemetryBuilder;
import org.ekstep.genie.telemetry.TelemetryHandler;
import org.ekstep.genie.telemetry.TelemetryStageId;
import org.ekstep.genie.ui.landing.LandingActivity;
import org.ekstep.genie.ui.share.ShareActivity;
import org.ekstep.genie.util.Constant;
import org.ekstep.genie.util.GlideImageUtil;
import org.ekstep.genie.util.LogUtil;
import org.ekstep.genie.util.PlayerUtil;
import org.ekstep.genie.util.ThemeUtility;
import org.ekstep.genie.util.Util;
import org.ekstep.genie.util.geniesdk.ContentUtil;
import org.ekstep.genie.util.preference.PreferenceUtil;
import org.ekstep.genieservices.IUserService;
import org.ekstep.genieservices.async.ContentService;
import org.ekstep.genieservices.commons.IResponseHandler;
import org.ekstep.genieservices.commons.bean.ChildContentRequest;
import org.ekstep.genieservices.commons.bean.Content;
import org.ekstep.genieservices.commons.bean.ContentDelete;
import org.ekstep.genieservices.commons.bean.ContentDeleteRequest;
import org.ekstep.genieservices.commons.bean.ContentDeleteResponse;
import org.ekstep.genieservices.commons.bean.ContentDetailsRequest;
import org.ekstep.genieservices.commons.bean.ContentFilterCriteria;
import org.ekstep.genieservices.commons.bean.ContentImportResponse;
import org.ekstep.genieservices.commons.bean.ContentSortCriteria;
import org.ekstep.genieservices.commons.bean.GenieResponse;
import org.ekstep.genieservices.commons.bean.enums.ContentImportStatus;
import org.ekstep.genieservices.commons.bean.enums.InteractionType;
import org.ekstep.genieservices.commons.utils.CollectionUtil;
import org.ekstep.genieservices.commons.utils.StringUtil;
import org.ekstep.genieservices.eventbus.EventBus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created on 1/24/2017.
 *
 * @author Sneha
 */
public class MyContentPresenter implements MyContentContract.Presenter {

    private static final String TAG = MyContentPresenter.class.getSimpleName();
    private MyContentContract.View mMyContentView;
    private ContentService mContentService = null;
    private IUserService mUserService = null;
    private Activity mActivity;
    private boolean mIsContentDeleted;
    private String mIdentifier;
    private List<Map<String, String>> mNestedContentHeader = null;
    private Map<String, List<Content>> mContentListMap = null;
    private List<Integer> mPositionList = new ArrayList<>();
    private String mSelectedIdentifier = "all";

    public MyContentPresenter() {
        mContentService = CoreApplication.getGenieAsyncService().getContentService();
        mUserService = CoreApplication.getGenieSdkInstance().getUserService();
    }

    @Override
    public void importContent() {
        if (Build.VERSION.SDK_INT >= 23) {
            ((LandingActivity) mActivity).requestAppPermissions(new
                            String[]{Manifest.permission.READ_EXTERNAL_STORAGE,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE}, "Genie needs to access your data"
                    , RuntimePermissionsActivity.REQUEST_PERMISSION_IMPORT_CONTENT);
        } else {
            ((LandingActivity) mActivity).launchImportArchiveView(false);
        }
    }

    @Override
    public void handleShareVisibility(int visibility) {
        if (visibility == View.VISIBLE) {
            mMyContentView.hideDoneBtn();
            mMyContentView.showShareBtn();
            clearSelection();
            mMyContentView.enableMenuImportBtn();
        } else {
            mMyContentView.hideShareBtn();
            mMyContentView.showDoneBtn();
            mMyContentView.disableMenuImportBtn();
            shareButtonClicked();
        }
    }

    @Override
    public void handleBackButtonClicked() {
        if (mMyContentView.getMenuBtnVisibility() == View.VISIBLE) {
            handleShareVisibility(View.VISIBLE);
        } else {
            ((LandingActivity) mActivity).showHome(false);
        }
        postDeleteSuccessEvent();
    }

    @Override
    public void handleSortButtonClick() {
        mMyContentView.showSortDialog();
    }

    @Override
    public void shareButtonClicked() {
        if (mMyContentView.getContentAdapter() != null) {
            mMyContentView.showSelectorLayer();
        }
    }

    @Override
    public void clearSelection() {
        if (mMyContentView.getContentAdapter() != null) {
            mMyContentView.clearSelected();
            mMyContentView.hideSelectorLayer();
        }
    }

    @Override
    public void renderMyContent() {
        renderLocalContents();
        TelemetryHandler.saveTelemetry(TelemetryBuilder.buildGEInteract(TelemetryStageId.MY_CONTENT));
    }

    @Override
    public void shareContent() {
        List<SelectedContent> selectedContentList = mMyContentView.getSelectedItems();
        if (mMyContentView.getSelectedCount() > 0) {

            Map<String, String> values = new HashMap<>();
            if (selectedContentList.size() == 1) {
                values.put(Constant.SHARE_NAME, selectedContentList.get(0).getIdentifier());
            } else {
                values.put(Constant.SHARE_NAME, "Collection");
            }
            values.put(Constant.SHARE_SCREEN_NAME, TelemetryStageId.MY_CONTENT);
            List<String> identifierList = new ArrayList<>();
            for (int i = 0; i < selectedContentList.size(); i++) {
                identifierList.add(selectedContentList.get(i).getIdentifier());
            }
            ShareActivity.startContetExportIntent(mActivity, false, values, identifierList);
        } else {
            mMyContentView.showCustomToast(R.string.msg_transfer_select_content);
        }
    }

    @Override
    public void renderLocalContents() {
        ContentFilterCriteria.Builder contentFilterCriteria = new ContentFilterCriteria.Builder();
        contentFilterCriteria.withContentAccess().withFeedback();
        contentFilterCriteria.forUser(mUserService.getCurrentUser().getResult().getUid());
        ContentUtil.applyPartnerFilter(contentFilterCriteria);
        mContentService.getAllLocalContent(contentFilterCriteria.build(), new IResponseHandler<List<Content>>() {
            @Override
            public void onSuccess(GenieResponse<List<Content>> genieResponse) {
                if (mMyContentView == null) {
                    return;
                }

                List<Content> contentList = genieResponse.getResult();
                if (contentList.size() > 0) {
                    mMyContentView.hideNoContentCard();
                    mMyContentView.showMyContentRecyclerView();
                    handleShareVisibility(View.VISIBLE);
                    mMyContentView.showLocalContents(new ArrayList<Content>(contentList));
                    mContentListMap = new HashMap<String, List<Content>>();
                    mContentListMap.put("all", contentList);
                } else {
                    handleShareVisibility(View.GONE);
                    mMyContentView.hideMyContentRecyclerView();
                    mMyContentView.showNoContentCard();
                }
            }

            @Override
            public void onError(GenieResponse<List<Content>> genieResponse) {
                handleShareVisibility(View.GONE);
                mMyContentView.hideMyContentRecyclerView();
                mMyContentView.showNoContentCard();
            }
        });
    }

    @Override
    public void renderLocalContents(ContentSortCriteria contentSortCriteria) {
        ContentFilterCriteria.Builder contentFilterCriteria = new ContentFilterCriteria.Builder();
        contentFilterCriteria.withContentAccess().withFeedback();
        contentFilterCriteria.forUser(mUserService.getCurrentUser().getResult().getUid());
        ContentUtil.applyPartnerFilter(contentFilterCriteria);
        List<ContentSortCriteria> contentSortCriteriaList = new ArrayList<>();
        contentSortCriteriaList.add(contentSortCriteria);
        contentFilterCriteria.sort(contentSortCriteriaList);
        mContentService.getAllLocalContent(contentFilterCriteria.build(), new IResponseHandler<List<Content>>() {
            @Override
            public void onSuccess(GenieResponse<List<Content>> genieResponse) {
                if (mMyContentView == null) {
                    return;
                }

                List<Content> contentList = genieResponse.getResult();
                if (contentList.size() > 0) {
                    mMyContentView.hideNoContentCard();
                    mMyContentView.showMyContentRecyclerView();
                    handleShareVisibility(View.VISIBLE);
                    mMyContentView.updateLocalContentList(new ArrayList<Content>(contentList));
                    mContentListMap = new HashMap<String, List<Content>>();
                    mContentListMap.put("all", contentList);
                } else {
                    handleShareVisibility(View.GONE);
                    mMyContentView.hideMyContentRecyclerView();
                    mMyContentView.showNoContentCard();
                }
            }

            @Override
            public void onError(GenieResponse<List<Content>> genieResponse) {
                handleShareVisibility(View.GONE);
                mMyContentView.hideMyContentRecyclerView();
                mMyContentView.showNoContentCard();
            }
        });
    }

    @Override
    public void deleteContent(final List<SelectedContent> selectedContentList) {
        TelemetryHandler.saveTelemetry(TelemetryBuilder.buildGEInteract(InteractionType.TOUCH, TelemetryStageId.MY_CONTENT, TelemetryAction.DELETE_CONTENT_INITIATED, TextUtils.join(",", selectedContentList)));
        ContentDeleteRequest.Builder request = new ContentDeleteRequest.Builder();
        for (SelectedContent selectedContent : selectedContentList) {
            ContentDelete contentDelete = new ContentDelete(selectedContent.getIdentifier(), selectedContent.isChild());
            request.add(contentDelete);
        }
        final String size = Util.humanReadableByteCount(getSelectedContentSize(selectedContentList), true);
        mContentService.deleteContent(request.build(), new IResponseHandler<List<ContentDeleteResponse>>() {
            @Override
            public void onSuccess(GenieResponse<List<ContentDeleteResponse>> genieResponse) {
                for (ContentDeleteResponse response : genieResponse.getResult()) {
                    ContentUtil.removeFromLocalCache(response.getIdentifier());
                }
                deleteLocalCopyofContent(selectedContentList);
                mMyContentView.showContentDeletedSuccessfullMessage(size);
            }

            @Override
            public void onError(GenieResponse<List<ContentDeleteResponse>> genieResponse) {

            }
        });
    }

    @Override
    public void toggleContentIcon(Content content) {
        if (ContentUtil.isContentLive(content.getContentData().getStatus())) {
            SelectedContent selectedContent = new SelectedContent(content.getIdentifier(), content.getSizeOnDevice(), content.getHierarchyInfo() != null);
            selectedContent.setRefCount(content.getReferenceCount());
            mMyContentView.toggleSelected(selectedContent);

            List<SelectedContent> selectedContents = new ArrayList<>();
            selectedContents.addAll(mMyContentView.getSelectedItems());

            if (selectedContents != null && selectedContents.size() != 0) {
                mMyContentView.setSelectedContentSize(getSelectedContentSize(selectedContents));
                mMyContentView.setSelectedContentCount(selectedContents.size());
            }
        } else {
            mMyContentView.showContentisDraftMessage();
        }
    }

    public long getSelectedContentSize(List<SelectedContent> selectedContents) {
        long size = 0;
        for (int i = 0; i < selectedContents.size(); i++) {
            size += selectedContents.get(i).getSize();
        }
        return size;
    }

    @Override
    public void playContent(Content content) {
        PreferenceUtil.setCdataStatus(true);
        PlayerUtil.startContent(mActivity, content, TelemetryStageId.CONTENT_DETAIL, true, false);
    }

    @Override
    public void showMoreDialog(final Content content) {
        ContentDetailsRequest.Builder detailsRequest = new ContentDetailsRequest.Builder().forContent(content.getIdentifier()).withFeedback().withContentAccess();
        mContentService.getContentDetails(detailsRequest.build(), new IResponseHandler<Content>() {
            @Override
            public void onSuccess(GenieResponse<Content> genieResponse) {
                Content selectedContent = genieResponse.getResult();
                selectedContent.setHierarchyInfo(content.getHierarchyInfo());
                mMyContentView.showMoreActionDialog(selectedContent);
            }

            @Override
            public void onError(GenieResponse<Content> genieResponse) {
                LogUtil.e(TAG, "error " + genieResponse.getErrorMessages().get(0));
            }
        });
    }

    @Override
    public void handleContentDetailClick(Content content) {
        mMyContentView.showContentDetailsActivity(content);
    }

    @Override
    public void handleShareClick(Content content) {
        mMyContentView.showShareOptionsLayout(content);
    }

    @Override
    public void handleProgressClick(Content content) {
        mMyContentView.showProgressReportActivity(content);
    }

    @Override
    public void handleFeedbackClick(Content content) {
        mMyContentView.showFeedBackDialog(content.getIdentifier(), ContentUtil.getPreviousRating(content.getContentFeedback()));
    }

    @Override
    public void handleDeleteClick(Content content) {
        mMyContentView.showDeleteConfirmationLayout(content.getSizeOnDevice(), content.getReferenceCount(), content.getIdentifier());
    }

    @Override
    public void postDeleteSuccessEvent() {
        if (mIsContentDeleted) {
            ContentImportResponse deletedContentResponse = new ContentImportResponse(mIdentifier, ContentImportStatus.IMPORT_COMPLETED);
            EventBus.postStickyEvent(deletedContentResponse);
        }
    }

    @Override
    public void manageImportAndDeleteSuccess(Object response) {
        renderLocalContents();
        EventBus.removeStickyEvent(response);
    }

    @Override
    public void manageLocalContentImportSuccess(String importSuccess) {
        if (!StringUtil.isNullOrEmpty(importSuccess) && importSuccess.equalsIgnoreCase(Constant.EventKey.EVENT_KEY_IMPORT_LOCAL_CONTENT)) {
            renderLocalContents();
            EventBus.removeStickyEvent(Constant.EventKey.EVENT_KEY_IMPORT_LOCAL_CONTENT);
        }
    }

    @Override
    public void manageContentExportSuccess(String exportSuccess) {
        if (!StringUtil.isNullOrEmpty(exportSuccess) && exportSuccess.equalsIgnoreCase(Constant.EventKey.EVENT_KEY_EXPORT_CONTENT)) {
            handleShareVisibility(View.VISIBLE);
            EventBus.removeStickyEvent(Constant.EventKey.EVENT_KEY_EXPORT_CONTENT);
        }
    }

    @Override
    /**
     * Set content related data to View
     *
     * @param content
     * @param contentViewHolder
     */
    public void setContentToViews(MyContentAdapter.ContentWrapper contentwrapper, MyContentAdapter.ContentViewHolder contentViewHolder, MyContentAdapter myContentAdapter) {
        Content content = contentwrapper.getContent();
        contentViewHolder.vhCbContentCheckBox.setTag(content);
        contentViewHolder.vhIvMore.setTag(content);
        contentViewHolder.vhImgContent.setTag(R.id.download_iv_content_icon, contentwrapper);
        contentViewHolder.vhTvContentName.setText(content.getContentData().getName());
        contentViewHolder.vhTvContentType.setText(Util.capitalizeFirstLetter(content.getContentType()));
        contentViewHolder.vhCbContentCheckBox.setChecked(myContentAdapter.getSelectedItems().contains(new SelectedContent(content.getIdentifier())));
        String size = String.valueOf(Util.humanReadableByteCount(content.getSizeOnDevice(), true));
        String[] sizeArray = size.split(" ");
        contentViewHolder.vhTvContentSize.setText(sizeArray[0]);
        contentViewHolder.vhTvContentSizeMetric.setText(" " + sizeArray[1]);
        if (content.getLastUsedTime() != null && content.getLastUsedTime() != 0) {
            String[] time = Util.toTimesAgo(content.getLastUsedTime());
            contentViewHolder.vhTvContentDownloadedTimeAgo.setVisibility(View.VISIBLE);
            contentViewHolder.vhTvContentDownloadedSince.setText(time[0]);
            contentViewHolder.vhTvContentDownloadedTimeAgo.setText(time[1]);

        } else {
            contentViewHolder.vhTvContentDownloadedSince.setText("-");
            contentViewHolder.vhTvContentDownloadedTimeAgo.setVisibility(View.GONE);
        }

        if (!ContentUtil.isContentLive(content.getContentData().getStatus())) {
        } else {
            contentViewHolder.vhTvContentName.setTextColor(ThemeUtility.getColor(mActivity, new int[]{R.attr.contentNameFontColor}));

            String contentType = content.getContentType();
            if (contentType.equalsIgnoreCase(ContentType.COLLECTION)) {
                contentViewHolder.vhTvContentType.setTextColor(ThemeUtility.getColor(mActivity, new int[]{R.attr.collectionContentTypeFontColor}));
            } else if (contentType.equalsIgnoreCase(ContentType.TEXTBOOK)) {
                contentViewHolder.vhTvContentType.setTextColor(ThemeUtility.getColor(mActivity, new int[]{R.attr.textbookContentTypeFontColor}));
            } else {
                contentViewHolder.vhTvContentType.setTextColor(ThemeUtility.getColor(mActivity, new int[]{R.attr.normalContentTypeFontColor}));
            }
        }

        if (!StringUtil.isNullOrEmpty(content.getContentData().getAppIcon())) {
            GlideImageUtil.loadImageUrl(mActivity, content.getBasePath() + "/" + content.getContentData().getAppIcon(), contentViewHolder.vhImgContent);
        } else {
            GlideImageUtil.loadImageUrl(mActivity, contentViewHolder.vhImgContent);
        }

        if (myContentAdapter.getSelectedItems().contains(new SelectedContent(content.getIdentifier()))) {
            contentViewHolder.vhRlCompleteRow.setBackgroundColor(ThemeUtility.getColor(mActivity, new int[]{R.attr.downloadItemSelectedBackgroundColor}));
        } else {
            contentViewHolder.vhRlCompleteRow.setBackgroundColor(ThemeUtility.getColor(mActivity, new int[]{R.attr.downloadItemBackgroundColor}));
        }

        if (myContentAdapter.getSelectedItems() != null && myContentAdapter.getSelectedItems().size() > 0) {
            mMyContentView.showSelectedContentOptions();
            mMyContentView.hideNormalOptions();
        } else {
            mMyContentView.hideSelectedContentOptions();
            mMyContentView.showNormalOptions();
        }
    }

    @Override
    public void handleContentItemClick(final MyContentAdapter.ContentWrapper contentWrapper) {
        final Content content = contentWrapper.getContent();
        if (ContentUtil.isCollectionOrTextBook(content.getContentType())) {
            mSelectedIdentifier = content.getIdentifier();
            mPositionList.add(contentWrapper.getPosition());
            ChildContentRequest.Builder contentRequest = new ChildContentRequest.Builder()
                    .forContent(content.getIdentifier())
                    .hierarchyInfo(content.getHierarchyInfo()).nextLevel();
            mContentService.getChildContents(contentRequest.build(), new IResponseHandler<Content>() {
                @Override
                public void onSuccess(GenieResponse<Content> genieResponse) {
                    List<Content> contentList = genieResponse.getResult().getChildren();
                    mMyContentView.hideContentHeader();
                    mMyContentView.showNestedContentHeader();
                    if (!CollectionUtil.isNullOrEmpty(contentList)) {
                        mContentListMap.put(content.getIdentifier(), contentList);
                    }

                    Map<String, String> headerMap = new HashMap<String, String>();
                    headerMap.put("name", content.getContentData().getName());
                    headerMap.put("identifier", content.getIdentifier());
                    if (mNestedContentHeader == null) {
                        mNestedContentHeader = new ArrayList<>();
                    }
                    mNestedContentHeader.add(headerMap);
                    mMyContentView.showHeaderBreadCrumb(mNestedContentHeader);
                    mMyContentView.hideContentSort();
                    mMyContentView.smoothScrollTo(0);
                    mMyContentView.updateLocalContentList(contentList != null ? new ArrayList<Content>(contentList) : new ArrayList<Content>());
                }

                @Override
                public void onError(GenieResponse<Content> genieResponse) {
                    LogUtil.e(TAG, "error: " + genieResponse.getErrorMessages().get(0));
                }
            });
        }
    }

    @Override
    public void handleHeaderBackClick() {
        List<Content> contentList;
        int position = mPositionList.get(mPositionList.size() - 1);
        mPositionList.remove(mPositionList.size() - 1);
        if (mNestedContentHeader.size() == 1) {
            contentList = mContentListMap.get("all");
            mSelectedIdentifier = "all";
            mNestedContentHeader.clear();
            mMyContentView.hideNestedContentHeader();
            mMyContentView.showContentHeader();
            mMyContentView.showContentSort();
            mMyContentView.updateLocalContentList(new ArrayList<Content>(contentList));
            mMyContentView.smoothScrollTo(position);
        } else {
            Map<String, String> headerMap = mNestedContentHeader.get(mNestedContentHeader.size() - 2);
            contentList = mContentListMap.get(headerMap.get("identifier"));
            mNestedContentHeader.remove(mNestedContentHeader.size() - 1);
            mMyContentView.showHeaderBreadCrumb(mNestedContentHeader);
            mMyContentView.updateLocalContentList(contentList);
            mMyContentView.smoothScrollTo(position);
        }

    }

    public void deleteLocalCopyofContent(List<SelectedContent> selectedContents) {
        for (SelectedContent selectedContent : selectedContents) {
            for (List<Content> contentList : mContentListMap.values()) {
                for (int i = 0; i < contentList.size(); i++) {
                    Content content = contentList.get(i);
                    if (selectedContent.getIdentifier().equalsIgnoreCase(content.getIdentifier())) {
                        if (content.getHierarchyInfo() == null) {
                            contentList.remove(i);
                        }
                    }
                }
            }
        }
        List<Content> contentList = mContentListMap.get(mSelectedIdentifier);
        mMyContentView.getSelectedItems().clear();
        mMyContentView.updateLocalContentList(new ArrayList<Content>(contentList));
    }

    @Override
    public void bindView(BaseView view, Context context) {
        mMyContentView = (MyContentContract.View) view;
        mActivity = (Activity) context;
    }

    @Override
    public void unbindView() {
        mMyContentView = null;
        mActivity = null;
    }


}
