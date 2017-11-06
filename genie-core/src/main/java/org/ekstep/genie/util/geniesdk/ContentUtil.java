package org.ekstep.genie.util.geniesdk;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.RatingBar;
import android.widget.TextView;

import org.ekstep.genie.R;
import org.ekstep.genie.base.BasePresenter;
import org.ekstep.genie.model.DownloadQueueItem;
import org.ekstep.genie.model.enums.ContentType;
import org.ekstep.genie.ui.collection.CollectionPresenter;
import org.ekstep.genie.ui.contentdetail.ContentDetailActivity;
import org.ekstep.genie.ui.contentdetail.ContentDetailPresenter;
import org.ekstep.genie.ui.mycontent.MyContentPresenter;
import org.ekstep.genie.ui.textbook.TextbookPresenter;
import org.ekstep.genie.util.Constant;
import org.ekstep.genie.util.DeviceUtility;
import org.ekstep.genie.util.LogUtil;
import org.ekstep.genie.util.preference.PreferenceUtil;
import org.ekstep.genieservices.GenieService;
import org.ekstep.genieservices.IContentService;
import org.ekstep.genieservices.async.UserService;
import org.ekstep.genieservices.commons.IResponseHandler;
import org.ekstep.genieservices.commons.bean.Content;
import org.ekstep.genieservices.commons.bean.ContentAccess;
import org.ekstep.genieservices.commons.bean.ContentData;
import org.ekstep.genieservices.commons.bean.ContentFeedback;
import org.ekstep.genieservices.commons.bean.ContentFilterCriteria;
import org.ekstep.genieservices.commons.bean.ContentImportResponse;
import org.ekstep.genieservices.commons.bean.ContentVariant;
import org.ekstep.genieservices.commons.bean.GenieResponse;
import org.ekstep.genieservices.commons.bean.HierarchyInfo;
import org.ekstep.genieservices.commons.bean.enums.ContentAccessStatus;
import org.ekstep.genieservices.commons.bean.enums.ContentImportStatus;
import org.ekstep.genieservices.commons.utils.CollectionUtil;
import org.ekstep.genieservices.commons.utils.DateUtil;
import org.ekstep.genieservices.commons.utils.GsonUtil;
import org.ekstep.genieservices.commons.utils.StringUtil;
import org.ekstep.genieservices.eventbus.EventPublisherThreadPool;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.util.AsyncExecutor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created on 6/3/2016.
 *
 * @author swayangjit_gwl
 */
public class ContentUtil {

    private static final String TAG = "ContentUtil";

    public static List<String> searchHistoryList = new ArrayList<>();

    public static Set<String> sLocalContentCache;

    public static boolean isCollectionOrTextBook(String contentType) {
        return contentType.equalsIgnoreCase(ContentType.COLLECTION) || contentType.equalsIgnoreCase(ContentType.TEXTBOOK) || contentType.equalsIgnoreCase(ContentType.TEXTBOOKUNIT);
    }

    /**
     * Add content access state to update the "new" label in respective adapter
     *
     * @param content
     */
    public static void addContentAccess(Content content) {
        UserService userService = GenieService.getAsyncService().getUserService();
        if (content != null) {
            ContentAccess contentAccess = new ContentAccess();
            contentAccess.setStatus(1);
            contentAccess.setContentId(content.getIdentifier());
            userService.addContentAccess(contentAccess, new IResponseHandler<Void>() {
                @Override
                public void onSuccess(GenieResponse<Void> genieResponse) {
                    LogUtil.d(TAG, "Added content access" + genieResponse.getStatus());
                }

                @Override
                public void onError(GenieResponse<Void> genieResponse) {

                }
            });
        }
    }

    public static void navigateToContentDetails(Context context, Content content, List<HierarchyInfo> contentInfoList, boolean isFromDownloads, boolean isSpineAvailable, boolean isFromCollectionOrTextbook, boolean isParentTextbookOrCollection) {
        Intent intent = new Intent(context, ContentDetailActivity.class);
        intent.putExtra(Constant.BundleKey.BUNDLE_KEY_CONTENT, content);
        intent.putExtra(Constant.BundleKey.BUNDLE_KEY_IS_SPINE_AVAILABLE, isSpineAvailable);
        intent.putExtra(Constant.BundleKey.BUNDLE_KEY_LOCAL_PATH, content.getBasePath());
        intent.putExtra(Constant.BundleKey.BUNDLE_KEY_IS_FROM_DOWNLOADS, isFromDownloads);
        intent.putExtra(Constant.BundleKey.BUNDLE_KEY_HIERARCHY_INFO, (ArrayList<HierarchyInfo>) contentInfoList);
        intent.putExtra(Constant.BundleKey.BUNDLE_KEY_IS_FROM_COLLECTION_OR_TEXTBOOK, isFromCollectionOrTextbook);
        intent.putExtra(Constant.BundleKey.BUNDLE_KEY_IS_PARENT_TEXTBOOK_OR_COLLECTION, isParentTextbookOrCollection);
        context.startActivity(intent);
    }

    public static void navigateToContentDetails(Context context, ContentData contentData, ArrayList<HierarchyInfo> contentInfoList, boolean isFromDownloads, boolean isSpineAvailable, boolean isFromCollectionOrTextbook) {
        Intent intent = new Intent(context, ContentDetailActivity.class);
        intent.putExtra(Constant.BundleKey.BUNDLE_KEY_CONTENT_DATA, contentData);
        intent.putExtra(Constant.BundleKey.BUNDLE_KEY_IS_SPINE_AVAILABLE, isSpineAvailable);
        intent.putExtra(Constant.BundleKey.BUNDLE_KEY_IS_FROM_DOWNLOADS, isFromDownloads);
        intent.putExtra(Constant.BundleKey.BUNDLE_KEY_HIERARCHY_INFO, contentInfoList);
        intent.putExtra(Constant.BundleKey.BUNDLE_KEY_IS_FROM_COLLECTION_OR_TEXTBOOK, isFromCollectionOrTextbook);
        context.startActivity(intent);
    }

    public static void showDeleteContentConfirmationDialog(Context context, final BasePresenter presenter, final int isAccessedElsewhere, final String identifier) {
        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_delete_confirmation);

        TextView dialog_msg = (TextView) dialog.findViewById(R.id.txt_delete);

        if (isAccessedElsewhere > 1) {
            dialog_msg.setText(R.string.msg_dialog_delete_confirmation_for_content_multiple_existence);
        } else {
            dialog_msg.setText(R.string.msg_dialog_delete_confirmation);
        }
        dialog.findViewById(R.id.delete_yes).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                if (presenter instanceof MyContentPresenter) {
//                    MyContentPresenter myContentPresenter = (MyContentPresenter) presenter;
//                    myContentPresenter.deleteContent(identifier);
                } else if (presenter instanceof ContentDetailPresenter) {
                    ContentDetailPresenter contentDetailPresenter = (ContentDetailPresenter) presenter;
                    contentDetailPresenter.deleteContent(identifier);
                } else if (presenter instanceof CollectionPresenter) {
                    CollectionPresenter collectionPresenter = (CollectionPresenter) presenter;
                    collectionPresenter.deleteContent(identifier, isAccessedElsewhere > 1 ? true : false);
                } else if (presenter instanceof TextbookPresenter) {
                    TextbookPresenter textbookPresenter = (TextbookPresenter) presenter;
                    textbookPresenter.deleteContent(identifier);
                }

            }
        });

        dialog.findViewById(R.id.delete_no).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        DeviceUtility.displayFullScreenDialog(dialog, (Activity) context);
    }


    public static Set<String> getLocalContentsCache() {
        return sLocalContentCache != null ? sLocalContentCache : new HashSet<String>();
    }

    public static void setLocalContentsCache(Set<String> identfierList) {
        if (sLocalContentCache == null) {
            sLocalContentCache = new HashSet<>();
        }
        sLocalContentCache.addAll(identfierList);
    }

    public static void removeFromLocalCache(String identfier) {
        if (sLocalContentCache != null) {
            sLocalContentCache.remove(identfier);
        }

    }

    public static float getPreviousRating(List<ContentFeedback> contententFeedbackList) {
        if (!CollectionUtil.isNullOrEmpty(contententFeedbackList)) {
            ContentFeedback contentFeedback = contententFeedbackList.get(0);
            return contentFeedback != null ? contentFeedback.getRating() : 0;
        }
        return 0;
    }

    public static boolean isContentLive(String status) {
        boolean isLive = false;
        if (status.equalsIgnoreCase(Constant.CONTENT_STATUS_LIVE)) {
            isLive = true;
        }
        return isLive;
    }

    /**
     * @param expiryDate
     * @return
     */
    public static boolean isContentExpired(String expiryDate) {
        boolean expired = false;

        if (!TextUtils.isEmpty(expiryDate)) {
            long millis = -1;

            try {
                millis = DateUtil.getTime(expiryDate);
            } catch (Exception e) {
                LogUtil.e(TAG, "Error in parsing expiry date.");
            }

            if (millis > 0 && System.currentTimeMillis() > millis) {
                expired = true;
            }
        }

        return expired;
    }

    /**
     * Updates the Rating bar depending upon the given rating
     *
     * @param content
     * @param ratingBar
     */
    public static void updateRating(Content content, RatingBar ratingBar) {
        if (content != null) {
            if (content.getContentFeedback() != null && content.getContentFeedback().size() > 0) {
                ContentFeedback contentFeedback = content.getContentFeedback().get(0);
                //Feedback feedBack = game.getFeedback();
                if (contentFeedback != null) {
                    ratingBar.setRating(Float.valueOf(contentFeedback.getRating()));
                }
            }
        }
    }


    public static List<String> getSearchHistoryList() {
        return searchHistoryList;
    }

    public static void addOrUpdateSearchHistoryList(String queryString) {
        if (searchHistoryList != null && !searchHistoryList.contains(queryString) && queryString.length() > 0) {
            searchHistoryList.add(0, queryString);

            if (searchHistoryList.size() > 5) {
                searchHistoryList.remove(searchHistoryList.size() - 1);
            }
        }
    }

    public static void applyPartnerFilter(ContentFilterCriteria.Builder builder) {
        String partnerInfo = PreferenceUtil.getPartnerInfo();
        if (!StringUtil.isNullOrEmpty(partnerInfo)) {
            HashMap<String, Object> partnerMap = GsonUtil.fromJson(partnerInfo, HashMap.class);

            //Apply audience filter
            if (partnerMap.containsKey(Constant.BUNDLE_KEY_PARTNER_AUDIENCE_ARRAY)) {
                ArrayList<String> audience = (ArrayList<String>) partnerMap.get(Constant.BUNDLE_KEY_PARTNER_AUDIENCE_ARRAY);
                if (audience != null) {
                    builder.audience(audience.toArray(new String[audience.size()]));
                }
            }
        }
    }

    /**
     * Send the event to refresh the current selected profile in home screen.
     */
    public static void publishRefreshProfileEvent() {
        EventPublisherThreadPool
                .getInstance()
                .execute(new AsyncExecutor.RunnableEx() {
                    @Override
                    public void run() throws Exception {
                        EventBus.getDefault().postSticky(Constant.EventKey.EVENT_KEY_REFRESH_PROFILE);
                    }
                });
    }

    public static boolean isNewContent(long contentCreationTime, ContentAccessStatus statusType, long profileCreationTime, boolean isNotAnonymousProfile) {
        boolean isNewContent = false;

        if (contentCreationTime > 0 && profileCreationTime > 0) {
            isNewContent = (contentCreationTime > profileCreationTime) && (statusType == ContentAccessStatus.NOT_PLAYED);
        }

        return (isNotAnonymousProfile && isNewContent);


    }

    public static ContentAccess getContentAccess(Content content) {
        List<ContentAccess> contentAccessList = content.getContentAccess();
        ContentAccess contentAccess = null;
        if (contentAccessList.size() > 0) {
            contentAccess = contentAccessList.get(0);
        } else {
            contentAccess = new ContentAccess();
        }
        return contentAccess;
    }

    public static void addDownloadQueueItem(String identifier, String name, String size, String parentIdentifier, String parentName, int childCount) {
        DownloadQueueItem downloadQueueItem = new DownloadQueueItem(identifier, name, size, parentIdentifier, parentName, childCount);
        List<DownloadQueueItem> downloadQueueItemList = PreferenceUtil.getDownloadQueueItemList();
        if (!downloadQueueItemList.contains(downloadQueueItem)) {
            downloadQueueItemList.add(downloadQueueItem);
        }
        PreferenceUtil.setDownloadQueueItemList(downloadQueueItemList);
    }

    public static boolean isContentImported(IContentService syncContentService, String identifier) {
        ContentImportResponse response = syncContentService.getImportStatus(identifier).getResult();
        if (response.getStatus().getValue() == ContentImportStatus.NOT_FOUND.getValue()
                || response.getStatus().getValue() == ContentImportStatus.IMPORT_COMPLETED.getValue()) {
            PreferenceUtil.removeDownloadQueueItem(identifier);
            return true;
        }
        return false;
    }

    public static String getContentSize(ContentData contentData) {
        List<ContentVariant> variants = contentData.getVariants();
        if (variants != null && variants.size() > 0 && isCollectionOrTextBook(contentData.getContentType())) {
            return variants.get(0).getSize();
        } else {
            return contentData.getSize();
        }
    }
}
