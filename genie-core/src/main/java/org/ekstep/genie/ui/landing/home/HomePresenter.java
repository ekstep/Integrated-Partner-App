package org.ekstep.genie.ui.landing.home;

import android.content.Context;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;

import org.ekstep.genie.CoreApplication;
import org.ekstep.genie.R;
import org.ekstep.genie.base.BaseView;
import org.ekstep.genie.customview.EkStepCustomTextView;
import org.ekstep.genie.model.DownloadQueueItem;
import org.ekstep.genie.model.enums.ContentType;
import org.ekstep.genie.telemetry.TelemetryAction;
import org.ekstep.genie.telemetry.TelemetryBuilder;
import org.ekstep.genie.telemetry.TelemetryConstant;
import org.ekstep.genie.telemetry.TelemetryHandler;
import org.ekstep.genie.telemetry.TelemetryStageId;
import org.ekstep.genie.telemetry.enums.CoRelationIdContext;
import org.ekstep.genie.telemetry.enums.EntityType;
import org.ekstep.genie.util.AvatarUtil;
import org.ekstep.genie.util.Constant;
import org.ekstep.genie.util.DeviceUtility;
import org.ekstep.genie.util.LogUtil;
import org.ekstep.genie.util.NetworkUtil;
import org.ekstep.genie.util.PopUpWindowUtil;
import org.ekstep.genie.util.ThemeUtility;
import org.ekstep.genie.util.Util;
import org.ekstep.genie.util.geniesdk.ConfigUtil;
import org.ekstep.genie.util.geniesdk.ContentUtil;
import org.ekstep.genie.util.preference.PreferenceKey;
import org.ekstep.genie.util.preference.PreferenceUtil;
import org.ekstep.genieservices.IContentService;
import org.ekstep.genieservices.async.ConfigService;
import org.ekstep.genieservices.async.ContentService;
import org.ekstep.genieservices.async.UserService;
import org.ekstep.genieservices.commons.IResponseHandler;
import org.ekstep.genieservices.commons.bean.Content;
import org.ekstep.genieservices.commons.bean.ContentAccess;
import org.ekstep.genieservices.commons.bean.ContentData;
import org.ekstep.genieservices.commons.bean.ContentFilterCriteria;
import org.ekstep.genieservices.commons.bean.ContentImportResponse;
import org.ekstep.genieservices.commons.bean.ContentListing;
import org.ekstep.genieservices.commons.bean.ContentListingCriteria;
import org.ekstep.genieservices.commons.bean.ContentListingSection;
import org.ekstep.genieservices.commons.bean.CorrelationData;
import org.ekstep.genieservices.commons.bean.GenieResponse;
import org.ekstep.genieservices.commons.bean.HierarchyInfo;
import org.ekstep.genieservices.commons.bean.MasterData;
import org.ekstep.genieservices.commons.bean.Profile;
import org.ekstep.genieservices.commons.bean.enums.ContentAccessStatus;
import org.ekstep.genieservices.commons.bean.enums.ContentImportStatus;
import org.ekstep.genieservices.commons.bean.enums.InteractionType;
import org.ekstep.genieservices.commons.bean.enums.MasterDataType;
import org.ekstep.genieservices.commons.utils.CollectionUtil;
import org.ekstep.genieservices.commons.utils.DateUtil;
import org.ekstep.genieservices.commons.utils.GsonUtil;
import org.ekstep.genieservices.commons.utils.StringUtil;
import org.ekstep.genieservices.eventbus.EventBus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * This is the Presenter class for Home Fragment, any calls/action done from Home Fragment comes here and any necessary action
 * logic is implemented in the respective method and given back again to the Fragment which implements View
 * <p>
 * Created by shriharsh on 4/1/17.
 */

public class HomePresenter implements HomeContract.Presenter {

    private static final int COLLECTION_TYPE_VIEW = 2;
    private static final int TEXT_BOOK_TYPE_VIEW = 1;
    private static final int NORMAL_TYPE_VIEW = 0;
    private static String TAG = HomePresenter.class.getSimpleName();
    @NonNull
    private Context mContext;
    @NonNull
    private HomeContract.View mHomeView;
    /*Current active user will be set to this profile*/
    private Profile mActiveProfile;
    private List<String> mSubjectsList;

    private boolean isKnownUserProfile = false;
    private List<Map<String, Object>> mSectionMapList = new ArrayList();
    private boolean mShouldCallAPI;
    private boolean mScrollTOBestOfGenie;

    //sectionCount is to check odd or even sections
    private int sectionCount = 0;
    private UserService mUserService = null;
    private ContentService mContentService = null;
    private ConfigService mConfigService = null;
    private IContentService mSyncContentService = null;
    private boolean isLocalContentsCalledAlready = false;

    public HomePresenter() {
        mUserService = CoreApplication.getGenieAsyncService().getUserService();
        mContentService = CoreApplication.getGenieAsyncService().getContentService();
        mConfigService = CoreApplication.getGenieAsyncService().getConfigService();
        mSyncContentService = CoreApplication.getGenieSdkInstance().getContentService();
    }

    @Override
    public void openNavigationDrawer() {
        mHomeView.openNavigationDrawer();
    }

    @Override
    public void searchContent(String searchString, EditText searchEditText) {
        if (NetworkUtil.isNetworkAvailable(mContext)) {
            if (searchString.length() > 2) {
                String searchQuery = searchString;

                mHomeView.hideKeyboard(searchEditText);
                mHomeView.setSearchBarVisibility(View.GONE);

                openSearchResultActivity(searchQuery, true);
            } else {
                Util.showCustomToast(R.string.error_search);
            }
        } else {
            Util.showCustomToast(R.string.error_network_1);
        }
    }

    @Override
    public void changeSubject(int position) {
        if (mSubjectsList != null && mSubjectsList.size() > 0) {
            //check if the change subject list is shown in on-boarding
            if (PreferenceUtil.isFirstTime().equalsIgnoreCase("true")) {
                //when the user has seen how to change the subject, then change the user to not first time user
                PreferenceUtil.setFirstTime("false");

                incrementOnBoardingState();

                mHomeView.disableOnBoardingViews();

                PopUpWindowUtil.dismissPopUpWindow();
            }

            if (!NetworkUtil.isNetworkAvailable(mContext)) {
                String choosenSubject = PreferenceUtil.getChosenSubject();

                mHomeView.showGenieOfflineLayout();

                if (choosenSubject != null) {
                    PreferenceUtil.setOfflineChosenSubject(choosenSubject);
                }

            } else {
                PreferenceUtil.setChosenSubject(mSubjectsList.get(position));
            }


            if ((Constant.ALL_SUBJECTS).equalsIgnoreCase(mSubjectsList.get(position))) {
                mHomeView.generateSubjectChangedTelemetry("All Subjects");
            } else {
                mHomeView.generateSubjectChangedTelemetry(mSubjectsList.get(position));
            }

            mHomeView.setSubject(mSubjectsList.get(position));

            // Get profile and subject specific online content
            getOnlineContents();
        }
    }

    @Override
    public void addOrSwitchChild() {
        if (!Util.isChildSwitcherEnabled()) {
            doGetAllProfiles();
        }


    }

    @Override
    public void openHome(Bundle arguments) {
        if (arguments != null && arguments.containsKey(Constant.EXTRAS_SCROLL_TO_BEST_OF_GENIE)) {
            mScrollTOBestOfGenie = arguments.getBoolean(Constant.EXTRAS_SCROLL_TO_BEST_OF_GENIE);
        }
    }

    @Override
    public void getOnlineContents() {
        mHomeView.showProgressDialog();
        String selectedSubject = PreferenceUtil.getChosenSubject();
        String offlineSelectedSubject = PreferenceUtil.getOfflineChosenSubject();

        if (!NetworkUtil.isNetworkAvailable(mContext)) {
            selectedSubject = offlineSelectedSubject;
        } else {
            mHomeView.hideGenieOfflineLayout();
            PreferenceUtil.setOfflineChosenSubject("");
        }

        if (selectedSubject != null && selectedSubject.equalsIgnoreCase(Constant.ALL_SUBJECTS)) {
            selectedSubject = "";
        }

        ContentListingCriteria.Builder listingCriteria = new ContentListingCriteria.Builder();
        String identifier = Constant.HOME_IDENTIFIER;

        String partnerInfo = PreferenceUtil.getPartnerInfo();
        if (!StringUtil.isNullOrEmpty(partnerInfo)) {
            HashMap<String, Object> partnerMap = GsonUtil.fromJson(partnerInfo, HashMap.class);
            if (partnerMap.get("mode") != null) {
                String mode = (String) partnerMap.get("mode");
                if (mode.equalsIgnoreCase("hard")) {
                    identifier = Constant.PARTNER_HOME_IDENTIFIER;
                }
            }

            //Apply Channel filter
            if (partnerMap.containsKey(Constant.BUNDLE_KEY_PARTNER_CHANNEL_ARRAY)) {
                ArrayList<String> channel = (ArrayList<String>) partnerMap.get(Constant.BUNDLE_KEY_PARTNER_CHANNEL_ARRAY);
                if (channel != null) {
                    listingCriteria.channel(channel.toArray(new String[channel.size()]));
                }
            }

            //Apply audience filter
            if (partnerMap.containsKey(Constant.BUNDLE_KEY_PARTNER_AUDIENCE_ARRAY)) {
                ArrayList<String> audience = (ArrayList<String>) partnerMap.get(Constant.BUNDLE_KEY_PARTNER_AUDIENCE_ARRAY);
                if (audience != null) {
                    listingCriteria.audience(audience.toArray(new String[audience.size()]));
                }
            }
        }

        listingCriteria.listingId(identifier).subject(selectedSubject);
        if (mActiveProfile != null) {
            listingCriteria.forUser(mActiveProfile.getUid()).age(mActiveProfile.getAge()).board(mActiveProfile.getBoard()).
                    grade(mActiveProfile.getStandard()).medium(mActiveProfile.getMedium()).forLanguage(mActiveProfile.getLanguage());
        }
        ContentListingCriteria criteria = listingCriteria.build();

        mContentService.getContentListing(criteria, new IResponseHandler<ContentListing>() {
            @Override
            public void onSuccess(GenieResponse<ContentListing> genieResponse) {
                mHomeView.hideProgressDialog();

                ContentListing result = genieResponse.getResult();

                String responseId = result.getResponseMessageId();
                if (!StringUtil.isNullOrEmpty(responseId)) {
                    PreferenceUtil.setCoRelationIdContext(CoRelationIdContext.PAGE_ASSEMBLE);
                    PreferenceUtil.setPageAssembleApiResponseMessageId(responseId);
                    PreferenceUtil.setCoRelationType(result.getContentListingId());
                } else {
                    PreferenceUtil.setPageAssembleApiResponseMessageId(null);
                }

                mHomeView.hideFooter();
                mHomeView.hideNoDataAvailableView();
                mHomeView.showSectionsLayout();
                mHomeView.removeAllSections();

                List<ContentListingSection> sectionList = result.getContentListingSections();
                if (sectionList != null && sectionList.size() > 0) {

                    for (int i = 0; i < sectionList.size(); i++) {
                        ContentListingSection section = sectionList.get(i);
                        List<ContentData> contentDataList = section.getContentDataList();
                        if (contentDataList != null && contentDataList.size() > 0) {
                            mHomeView.inflateSectionWithContents(section, sectionCount, contentDataList);
                            sectionCount++;
                        } else {
                            mHomeView.inflateEmptySection(section, sectionCount);

                            sectionCount++;
                        }
                    }
                    mHomeView.showFooter();
                    if (mScrollTOBestOfGenie) {
                        mHomeView.scrollToBestofGenie();
                    }
                    mShouldCallAPI = true;

                }
            }

            @Override
            public void onError(GenieResponse<ContentListing> genieResponse) {
                mHomeView.hideSectionLayout();
                mHomeView.showNoDataAvailableView(genieResponse);
                mHomeView.showFooter();
                mShouldCallAPI = true;
            }
        });
    }

    @Override
    public void getAllProfiles() {
        doGetAllProfiles();
    }

    @Override
    public void setSwitchedKidDetails(Profile profile, int size, boolean shouldCallApi) {
        setCurrentUser(profile, size, shouldCallApi);
    }

    @Override
    public void setLocalContentDetails(Content content, LocalContentAdapter.NormalContentViewHolder normalContentViewHolder, LocalContentAdapter localContentAdapter, long profileCreationTime) {
        //this is to check if the content has expired or in drafts
        if (!ContentUtil.isContentLive(content.getContentData().getStatus())) {
            if (ContentUtil.isContentExpired(content.getContentData().getExpires())) {
                localContentAdapter.setNormalContentBackground(normalContentViewHolder, ThemeUtility.getDrawable(mContext, new int[]{R.attr.expiredContentBackground}));
            } else {
                localContentAdapter.setNormalContentBackground(normalContentViewHolder, ThemeUtility.getDrawable(mContext, new int[]{R.attr.draftContentBackground}));
            }

            localContentAdapter.setNormalContentBackgroundColor(normalContentViewHolder, mContext.getResources().getColor(android.R.color.transparent));
            localContentAdapter.setNormalContentNameColor(normalContentViewHolder, mContext.getResources().getColor(android.R.color.white));
        } else {
            localContentAdapter.setNormalContentBackground(normalContentViewHolder, ThemeUtility.getDrawable(mContext, new int[]{R.attr.contentBackground}));
            localContentAdapter.setNormalContentNameColor(normalContentViewHolder, ThemeUtility.getColor(mContext, new int[]{R.attr.contentNameFontColor}));
        }

        //Check status of the game, to show NEW label
        setNewLabelVisibility(normalContentViewHolder.vhTvNew, content.getLastUpdatedTime(), getContentAccess(content).getStatus(), profileCreationTime);

        if (!StringUtil.isNullOrEmpty(content.getContentData().getAppIcon())) {
            localContentAdapter.setNormalContentIcon(mContext, content.getBasePath() + "/" + content.getContentData().getAppIcon(), normalContentViewHolder);
        } else {
            localContentAdapter.setDefaultNormalContentIcon(normalContentViewHolder);
        }
    }

    @Override
    public void setLocalCollectionDetails(Content content, LocalContentAdapter.CollectionContentViewHolder collectionContentViewHolder, LocalContentAdapter localContentAdapter, long profileCreationTime) {
        //this is to check if the content has expired or in drafts
        if (!ContentUtil.isContentLive(content.getContentData().getStatus())) {
            if (ContentUtil.isContentExpired(content.getContentData().getExpires())) {
                localContentAdapter.setCollectionContentBackground(collectionContentViewHolder, ThemeUtility.getDrawable(mContext, new int[]{R.attr.expiredContentBackground}));
            } else {
                localContentAdapter.setCollectionContentBackground(collectionContentViewHolder, ThemeUtility.getDrawable(mContext, new int[]{R.attr.draftContentBackground}));
            }

            localContentAdapter.setCollectionContentBackgroundColor(collectionContentViewHolder, mContext.getResources().getColor(android.R.color.transparent));
            localContentAdapter.setCollectionContentNameColor(collectionContentViewHolder, ThemeUtility.getColor(mContext, new int[]{R.attr.contentNameFontColor}));
        } else {
            localContentAdapter.setCollectionContentNameColor(collectionContentViewHolder, ThemeUtility.getColor(mContext, new int[]{R.attr.contentNameFontColor}));
            localContentAdapter.setCollectionContentBackground(collectionContentViewHolder, ThemeUtility.getDrawable(mContext, new int[]{R.attr.collectionBackground}));

        }

        //Check status of the game, to show NEW label
        setNewLabelVisibility(collectionContentViewHolder.vhTvNew, content.getLastUpdatedTime(), getContentAccess(content).getStatus(), profileCreationTime);

        if (!StringUtil.isNullOrEmpty(content.getContentData().getAppIcon())) {
            localContentAdapter.setCollectionContentIcon(mContext, content.getBasePath() + "/" + content.getContentData().getAppIcon(), collectionContentViewHolder);
        } else {
            localContentAdapter.setDefaultCollectionContentIcon(collectionContentViewHolder);
        }
    }

    @Override
    public void setLocalTextbookDetails(Content content, LocalContentAdapter.TextBookContentViewHolder textBookContentViewHolder, LocalContentAdapter localContentAdapter, long profileCreationTime) {
        //this is to check if the content has expired or in drafts
        if (!ContentUtil.isContentLive(content.getContentData().getStatus())) {
            if (ContentUtil.isContentExpired(content.getContentData().getExpires())) {
                localContentAdapter.setTextbookContentBackground(textBookContentViewHolder, ThemeUtility.getDrawable(mContext, new int[]{R.attr.draftContentBackground}));
            } else {
                localContentAdapter.setTextbookContentBackground(textBookContentViewHolder, ThemeUtility.getDrawable(mContext, new int[]{R.attr.expiredContentBackground}));
            }

            localContentAdapter.setTextbookContentBackgroundColor(textBookContentViewHolder, mContext.getResources().getColor(android.R.color.transparent));
            localContentAdapter.setTextbookContentNameColor(textBookContentViewHolder, ThemeUtility.getColor(mContext, new int[]{R.attr.contentNameFontColor}));
        } else {
            localContentAdapter.setTextbookContentNameColor(textBookContentViewHolder, ThemeUtility.getColor(mContext, new int[]{R.attr.contentNameFontColor}));
            localContentAdapter.setTextbookContentBackground(textBookContentViewHolder, ThemeUtility.getDrawable(mContext, new int[]{R.attr.bookBackground}));

        }

        //Check status of the game, to show NEW label
        setNewLabelVisibility(textBookContentViewHolder.vhTvNew, content.getLastUpdatedTime(), getContentAccess(content).getStatus(), profileCreationTime);

        if (!StringUtil.isNullOrEmpty(content.getContentData().getAppIcon())) {
            localContentAdapter.setTextbookContentIcon(mContext, content.getBasePath() + "/" + content.getContentData().getAppIcon(), textBookContentViewHolder);
        } else {
            localContentAdapter.setDefaultTextbookContentIcon(textBookContentViewHolder);
        }
    }

    private void setNewLabelVisibility(EkStepCustomTextView vhTvNew, long contentCreationTime, ContentAccessStatus statusType, long profileCreationTime) {
        boolean isNewContent = false;

        if (contentCreationTime > 0 && profileCreationTime > 0) {
            isNewContent = (contentCreationTime > profileCreationTime) && (statusType == ContentAccessStatus.NOT_PLAYED);
        }

        if (isKnownUserProfile && isNewContent) {
            vhTvNew.setVisibility(View.VISIBLE);
        } else {
            vhTvNew.setVisibility(View.INVISIBLE);
        }
    }

    private ContentAccess getContentAccess(Content content) {
        List<ContentAccess> contentAccessList = content.getContentAccess();
        ContentAccess contentAccess = null;
        if (contentAccessList.size() > 0) {
            contentAccess = contentAccessList.get(0);
        } else {
            contentAccess = new ContentAccess();
        }
        return contentAccess;
    }

    @Override
    public void setSectionContentDetails(Set<String> identifierList, ContentData contentData, SectionAdapter.NormalContentViewHolder normalContentViewHolder, SectionAdapter sectionAdapter) {
        if (identifierList.contains(contentData.getIdentifier())) {
            sectionAdapter.setNormalContentTickVisibility(normalContentViewHolder, View.VISIBLE);
        } else {
            sectionAdapter.setNormalContentTickVisibility(normalContentViewHolder, View.INVISIBLE);
        }

        if (!ContentUtil.isContentLive(contentData.getStatus())) {
            if (ContentUtil.isContentExpired(contentData.getExpires())) {
                sectionAdapter.setNormalContentBackgroundResource(normalContentViewHolder, ThemeUtility.getDrawable(mContext, new int[]{R.attr.expiredContentBackground}));
            } else {
                sectionAdapter.setNormalContentBackgroundResource(normalContentViewHolder, ThemeUtility.getDrawable(mContext, new int[]{R.attr.draftContentBackground}));
            }

            sectionAdapter.setNormalMainLayoutBackgroundColor(normalContentViewHolder, mContext.getResources().getColor(android.R.color.transparent));
            sectionAdapter.setNormalContentNameColor(normalContentViewHolder, mContext.getResources().getColor(android.R.color.white));
        } else {
            sectionAdapter.setNormalContentNameColor(normalContentViewHolder, ThemeUtility.getColor(mContext, new int[]{R.attr.contentNameFontColor}));
            sectionAdapter.setNormalContentBackgroundResource(normalContentViewHolder, ThemeUtility.getDrawable(mContext, new int[]{R.attr.contentBackground}));
        }

        if (!StringUtil.isNullOrEmpty(contentData.getAppIcon())) {
            sectionAdapter.setNormalContentIcon(mContext, contentData.getAppIcon(), normalContentViewHolder);
        } else {
            sectionAdapter.setDefaultNormalContentIcon(mContext, normalContentViewHolder);
        }
    }

    @Override
    public void setSectionCollectionDetails(Set<String> identifierList, ContentData contentData, SectionAdapter.CollectionContentViewHolder collectionContentViewHolder, SectionAdapter sectionAdapter) {
        if (identifierList.contains(contentData.getIdentifier())) {
            sectionAdapter.setCollectionContentTickVisibility(collectionContentViewHolder, View.VISIBLE);
        } else {
            sectionAdapter.setCollectionContentTickVisibility(collectionContentViewHolder, View.INVISIBLE);
        }

        if (!ContentUtil.isContentLive(contentData.getStatus())) {
            if (ContentUtil.isContentExpired(contentData.getExpires())) {
                sectionAdapter.setCollectionContentBackgroundResource(collectionContentViewHolder, ThemeUtility.getDrawable(mContext, new int[]{R.attr.expiredContentBackground}));
            } else {
                sectionAdapter.setCollectionContentBackgroundResource(collectionContentViewHolder, ThemeUtility.getDrawable(mContext, new int[]{R.attr.draftContentBackground}));
            }

            sectionAdapter.setCollectionMainLayoutBackgroundColor(collectionContentViewHolder, mContext.getResources().getColor(android.R.color.transparent));
            sectionAdapter.setCollectionContentNameColor(collectionContentViewHolder, mContext.getResources().getColor(android.R.color.white));
        } else {
            sectionAdapter.setCollectionContentNameColor(collectionContentViewHolder, ThemeUtility.getColor(mContext, new int[]{R.attr.contentNameFontColor}));
            sectionAdapter.setCollectionContentBackgroundResource(collectionContentViewHolder, ThemeUtility.getDrawable(mContext, new int[]{R.attr.collectionBackground}));
        }

        if (!StringUtil.isNullOrEmpty(contentData.getAppIcon())) {
            sectionAdapter.setCollectionContentIcon(mContext, contentData.getAppIcon(), collectionContentViewHolder);
        } else {
            sectionAdapter.setDefaultCollectionContentIcon(mContext, collectionContentViewHolder);
        }
    }

    @Override
    public void setSectionTextbookDetails(Set<String> identifierList, ContentData contentData, SectionAdapter.TextBookContentViewHolder textBookContentViewHolder, SectionAdapter sectionAdapter) {
        if (identifierList.contains(contentData.getIdentifier())) {
            sectionAdapter.setTextbookContentTickVisibility(textBookContentViewHolder, View.VISIBLE);
        } else {
            sectionAdapter.setTextbookContentTickVisibility(textBookContentViewHolder, View.INVISIBLE);
        }

        if (!ContentUtil.isContentLive(contentData.getStatus())) {
            if (ContentUtil.isContentExpired(contentData.getExpires())) {
                sectionAdapter.setTextbookContentBackgroundResource(textBookContentViewHolder, ThemeUtility.getDrawable(mContext, new int[]{R.attr.expiredContentBackground}));
            } else {
                sectionAdapter.setTextbookContentBackgroundResource(textBookContentViewHolder, ThemeUtility.getDrawable(mContext, new int[]{R.attr.expiredContentBackground}));
            }

            sectionAdapter.setTextbookMainLayoutBackgroundColor(textBookContentViewHolder, mContext.getResources().getColor(android.R.color.transparent));
            sectionAdapter.setTextbookContentNameColor(textBookContentViewHolder, mContext.getResources().getColor(android.R.color.white));
        } else {
            sectionAdapter.setTextbookContentNameColor(textBookContentViewHolder, ThemeUtility.getColor(mContext, new int[]{R.attr.contentNameFontColor}));
            sectionAdapter.setTextbookContentBackgroundResource(textBookContentViewHolder, ThemeUtility.getDrawable(mContext, new int[]{R.attr.bookBackground}));
        }

        if (!StringUtil.isNullOrEmpty(contentData.getAppIcon())) {
            sectionAdapter.setTextbookContentIcon(mContext, contentData.getAppIcon(), textBookContentViewHolder);
        } else {
            sectionAdapter.setDefaultTextbookContentIcon(mContext, textBookContentViewHolder);
        }
    }

    @Override
    public int getItemTypeInSectionAdapter(int position, List<ContentData> contentDataList) {
        if (contentDataList != null && contentDataList.size() > 0) {
            ContentData contentData = contentDataList.get(position);
            String contentType = contentData.getContentType();
            //Collection View Type
            if (contentType.equalsIgnoreCase(ContentType.COLLECTION)) {
                return COLLECTION_TYPE_VIEW;
            } else if (contentType.equalsIgnoreCase(ContentType.TEXTBOOK)) {
                return TEXT_BOOK_TYPE_VIEW;
            } else {
                return NORMAL_TYPE_VIEW;
            }
        }

        return NORMAL_TYPE_VIEW;
    }

    @Override
    public int getItemTypeInLocalAdapter(int position, List<Content> contentList) {
        if (contentList != null && contentList.size() > 0) {
            Content content = contentList.get(position);

            String contentType = content.getContentType();
            //Collection View Type
            if (contentType.equalsIgnoreCase(ContentType.COLLECTION)) {
                return COLLECTION_TYPE_VIEW;
            } else if (contentType.equalsIgnoreCase(ContentType.TEXTBOOK)) {
                return TEXT_BOOK_TYPE_VIEW;
            } else {
                return NORMAL_TYPE_VIEW;
            }
        }

        return NORMAL_TYPE_VIEW;
    }

    @Override
    public void handleSectionItemClicked(ContentData contentData, int positionClicked, Map<String, String> sectionMap) {
        PreferenceUtil.setPageAssembleApiResponseMessageId(sectionMap.get(Constant.SECTION_REMSGID));

        PreferenceUtil.setCoRelationType(sectionMap.get(Constant.SECTION_APIID));
        List<CorrelationData> cdata = PreferenceUtil.getCoRelationList();
        Map<String, Object> map = new HashMap<>();
        map.put(TelemetryConstant.POSITION_CLICKED, positionClicked);
        map.put(EntityType.SEARCH_PHRASE, sectionMap.get(Constant.SECTION_NAME));

        String contentId = contentData.getIdentifier();
        if (!StringUtil.isNullOrEmpty(contentId)) {
            String presentOnDevice = ContentUtil.getLocalContentsCache().contains(contentId) ? "Y" : "N";
            map.put(TelemetryConstant.PRESENT_ON_DEVICE, presentOnDevice);
        }
        TelemetryHandler.saveTelemetry(TelemetryBuilder.buildGEInteract(InteractionType.TOUCH, TelemetryStageId.GENIE_HOME, TelemetryAction.CONTENT_CLICKED, contentId, map, cdata));

        ContentUtil.navigateToContentDetails(mContext, contentData, new ArrayList<HierarchyInfo>(), false, false, false);

    }

    @Override
    public void getLocalContents() {
        if (!isLocalContentsCalledAlready) {
            isLocalContentsCalledAlready = true;
            LogUtil.e(TAG + "@getLocalContents", "Called !");
            ContentFilterCriteria.Builder contentFilterCriteria = new ContentFilterCriteria.Builder();
            contentFilterCriteria.contentTypes(new String[]{ContentType.GAME, ContentType.STORY, ContentType.WORKSHEET, ContentType.COLLECTION})
                    .withContentAccess();
            ContentUtil.applyPartnerFilter(contentFilterCriteria);

            mContentService.getAllLocalContent(contentFilterCriteria.build(), new IResponseHandler<List<Content>>() {
                @Override
                public void onSuccess(GenieResponse<List<Content>> genieResponse) {
                    List<Content> contentList = genieResponse.getResult();
                    if (contentList != null) {
                        //download content layout visibility - GONE
                        mHomeView.hideDownloadContent();
                        mHomeView.showLessonsViewAll();
                        showOrHideMyLessons(contentList);

                        setLocalContentCache(contentList);
                    } else {
                        mHomeView.showDownloadContent();
                        mHomeView.hideLessonsViewAll();
                    }

                }

                @Override
                public void onError(GenieResponse<List<Content>> genieResponse) {

                }
            });

            getLocalTextBookContent();
        }
    }

    @Override
    public void setLocalContentsCalledAlreadyToFalse() {
        //This flag is set to false in the onPause, so that when the HomeFragment comes in foreground,
        //irrespective of onResume or onViewCreated is called first, getLocalContents() will be called once
        isLocalContentsCalledAlready = false;
    }

    @Override
    public void manageRefreshProfile(String refreshProfile) {
        if (!StringUtil.isNullOrEmpty(refreshProfile) && refreshProfile.equalsIgnoreCase(Constant.EventKey.EVENT_KEY_REFRESH_PROFILE)) {
            getCurrentUser();
            getLocalContents();
            EventBus.removeStickyEvent(Constant.EventKey.EVENT_KEY_REFRESH_PROFILE);

        }
    }

    @Override
    public void hideSoftKeyboard() {
        mHomeView.hideSoftKeyboard();
    }

    @Override
    public void handleViewAllClick(String lessonType) {
        mHomeView.navigateToMyLessonsActivity(lessonType, mActiveProfile, isKnownUserProfile);
    }

    @Override
    public void checkIfUserHasOnBoardedChangeSubjectStep() {
        if (PreferenceUtil.isFirstTime().equalsIgnoreCase("true") && PreferenceUtil.getOnBoardingState() == Constant.On_BOARD_STATE_CHANGE_SUBJECT) {
            showOnBoardChangeSubject();
        }
    }

    @Override
    public void checkForOnBoarding() {
        //check if user has already seen on boarding screens
        if (PreferenceUtil.isFirstTime().equalsIgnoreCase("true")) {
            //if the user is on boarding for first time, show him on boarding of app
            showOnBoarding();
        }
    }

    private void setLocalContentCache(List<Content> contentList) {
        Set<String> identifierList = new HashSet<>();
        for (Content content : contentList) {
            identifierList.add(content.getIdentifier());
        }
        ContentUtil.setLocalContentsCache(identifierList);
    }

    private void getLocalTextBookContent() {
        ContentFilterCriteria.Builder contentFilterCriteria = new ContentFilterCriteria.Builder().contentTypes(new String[]{ContentType.TEXTBOOK})
                .withContentAccess();
        ContentUtil.applyPartnerFilter(contentFilterCriteria);
        mContentService.getAllLocalContent(contentFilterCriteria.build(), new IResponseHandler<List<Content>>() {
            @Override
            public void onSuccess(GenieResponse<List<Content>> genieResponse) {
                List<Content> contentList = genieResponse.getResult();
                if (contentList != null) {
                    //download content layout visibility - GONE
                    mHomeView.hideDownloadContent();
                    showOrHideMyTextbooks(contentList);
                    setLocalContentCache(contentList);
                } else {
                    mHomeView.showDownloadContent();
                    mHomeView.hideBooksViewAll();
                }
            }

            @Override
            public void onError(GenieResponse<List<Content>> genieResponse) {

            }
        });
    }

    private void showOrHideMyTextbooks(List<Content> textbookList) {
        if (textbookList != null && textbookList.size() > 0) {
            //my lesson visibility - VIEW
            mHomeView.showMyTextbooksVisibility();
            mHomeView.showBooksViewAll();
            mHomeView.showMyTextbooksLocalContentAdapter(textbookList, mActiveProfile, isKnownUserProfile);

        } else {
            //my text books layout visibility - GONE
            mHomeView.hideMyTextBooks();
            mHomeView.hideBooksViewAll();
        }
    }

    private void showOrHideMyLessons(List<Content> contentAndCollectionList) {
        if (contentAndCollectionList != null && contentAndCollectionList.size() > 0) {
            //my lesson visibility - VIEW
            mHomeView.showMyLessonsVisibility();
            mHomeView.showMyLessonsLocalContentAdapter(contentAndCollectionList, mActiveProfile, isKnownUserProfile);

        } else {
            //my lessons layout visibility - GONE
            mHomeView.hideMyLessons();
            mHomeView.hideLessonsViewAll();
        }
    }

    @Override
    public void switchToChildActivity() {
        mHomeView.changeToAddChildActivity();
    }

    @Override
    public void openSearchResultActivity(String query, boolean isExplicit) {
        mHomeView.openSearchActivity(query, isExplicit, mActiveProfile);
    }

    @Override
    public void getSubjectsList() {
        mConfigService.getMasterData(MasterDataType.SUBJECT, new IResponseHandler<MasterData>() {
            @Override
            public void onSuccess(GenieResponse<MasterData> genieResponse) {
                mSubjectsList = ConfigUtil.getMasterDataLabelList(genieResponse.getResult());
                if (mSubjectsList != null) {
                    mSubjectsList.add(0, Constant.ALL_SUBJECTS);
                }
                mHomeView.popUpSubjects(mSubjectsList);
            }

            @Override
            public void onError(GenieResponse<MasterData> genieResponse) {

            }
        });

    }

    @Override
    public void showOnBoarding() {
        switch (PreferenceUtil.getOnBoardingState()) {
            case Constant.On_BOARD_STATE_ADD_CHILD:
                mHomeView.showAddChildOnBoarding();
                break;

            case Constant.On_BOARD_STATE_CHANGE_SUBJECT:
                mHomeView.showChangeSubjectOnBoarding();
                break;

            default:
                break;
        }
    }

    @Override
    public void skipCurrentOnBoarding() {
        switch (PreferenceUtil.getOnBoardingState()) {
            case Constant.On_BOARD_STATE_ADD_CHILD:
                mHomeView.generateAddChildSkippedTelemetry();
                checkIfPartnerHasSkippedChangeSubject(true);
                break;

            case Constant.On_BOARD_STATE_CHANGE_SUBJECT:
                //when the user has seen both the on boarding screens, set first time to false
                PreferenceUtil.setFirstTime("false");
                mHomeView.generateSubjectSkippedTelemetry();
                incrementOnBoardingState();
                mHomeView.disableOnBoardingViews();
                PopUpWindowUtil.dismissPopUpWindow();
                break;

            default:
                break;
        }
    }

    public void checkIfPartnerHasSkippedChangeSubject(boolean shouldShowSubjectChange) {
        //If the partner has set the on-boarding of the change subject to be false,
        //then we need to directly increment the onboardingState to finished
        String partnerInfo = PreferenceUtil.getPartnerInfo();

        if (!StringUtil.isNullOrEmpty(partnerInfo)) {
            HashMap<String, Object> partnerMap = GsonUtil.fromJson(partnerInfo, HashMap.class);

            //Check if the Change Subject on-boarding is skipped
            if (partnerMap.containsKey(Constant.BUNDLE_KEY_ENABLE_ONBOARDING_CHOOSE_SUBJECT)) {
                if (!(boolean) partnerMap.get(Constant.BUNDLE_KEY_ENABLE_ONBOARDING_CHOOSE_SUBJECT)) {
                    //increment on-boarding state
                    incrementOnBoardingState();

                    //Subject choosing on-boarding is the last step, and if the partner has specified to be not shown, then its assumed that Genie on-boarding steps are done
                    // and the user will be not first time user
                    PreferenceUtil.setFirstTime("false");

                    mHomeView.disableOnBoardingViews();
                    PopUpWindowUtil.dismissPopUpWindow();
                } else {
                    incrementOnBoardingAndProceedToChangeSubject(shouldShowSubjectChange);
                }
            } else {
                incrementOnBoardingAndProceedToChangeSubject(shouldShowSubjectChange);
            }
        } else {
            incrementOnBoardingAndProceedToChangeSubject(shouldShowSubjectChange);
        }
    }

    @Override
    public void checkForDeviceMemoryLow() {
        if (DeviceUtility.isDeviceMemoryLow()) {
            mHomeView.showDeviceMemoryLowView();
        }
    }

    @Override
    public void checkForLowInternalMemory() {
        if (DeviceUtility.isDeviceMemoryLow() || !PreferenceUtil.getPreferenceWrapper().
                getBoolean(PreferenceKey.KEY_HIDE_MEMORY_CARD_DIALOG, false)) {
            mHomeView.showAddMemoryCardDialog();
        }
    }

    private void incrementOnBoardingAndProceedToChangeSubject(boolean shouldShowSubjectChange) {
        if (shouldShowSubjectChange) {
            incrementOnBoardingState();
            mHomeView.showChangeSubjectOnBoarding();
        }
    }

    @Override
    public void showOnBoardChangeSubject() {
        mHomeView.showChangeSubjectOnBoarding();
    }

    @Override
    public void incrementOnBoardingState() {
        PreferenceUtil.setOnBoardingState((PreferenceUtil.getOnBoardingState() + 1));
    }

    @Override
    public void navigateToSearchResultActivity(ContentListingSection section) {
        if (!StringUtil.isNullOrEmpty(section.getSectionName())) {
            mHomeView.generateViewMoreClickedTelemetry(section.getSectionName());
        }
        if (section.getContentSearchCriteria() != null) {
            mHomeView.navigateToSearchWithSearchSortFilterCriteria(section, mActiveProfile);
        } else {
            mHomeView.navigateToSearchWithRecommendationCriteria(mActiveProfile);
        }
    }

    @Override
    public void setSubjectName() {
        String subjectName = PreferenceUtil.getChosenSubject();

        if (TextUtils.isEmpty(subjectName)) {
            mHomeView.setChosenSubjectName(mContext.getResources().getString(R.string.label_home_default_subject));
        } else {
            mHomeView.setChosenSubjectName(subjectName);
        }
    }

    @Override
    public void startGame(Content content) {
        mHomeView.playContent(content);
    }

    @Override
    public void checkViewVisibility(List<View> viewList, ScrollView scrollView) {

        for (View view : viewList) {
            int percentage = isViewVisible(view, scrollView);
            if (percentage != -1) {
                if (view != null && view.getTag() != null) {
                    Map<String, Object> sectionMap = new HashMap<>();
                    sectionMap.put("sectionName", view.getTag().toString());
                    sectionMap.put("percentage", String.valueOf(percentage));
                    sectionMap.put("time", DateUtil.getCurrentTimestamp());
                    mSectionMapList.add(sectionMap);
                }

            }
        }
    }

    @Override
    public void onPause() {
        mShouldCallAPI = false;
        if (mSectionMapList.size() > 0) {
            mHomeView.generateSectionViewdTelemetryEvent(mSectionMapList);
            mSectionMapList.clear();
        }
    }

    @Override
    public void onScrollChanged(RecyclerView recyclerView, int newState, TextView layout_More) {
        if (newState == RecyclerView.SCROLL_STATE_IDLE) {
            LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
            SectionAdapter sectionAdapter = (SectionAdapter) recyclerView.getAdapter();

            if ((sectionAdapter.getItemCount() - 1) == layoutManager.findLastCompletelyVisibleItemPosition()) {
                mHomeView.shakeLayoutMore(layout_More);
            }
        }
    }

    @Override
    public void manageDrawer() {
        if (PreferenceUtil.isFirstTime().equalsIgnoreCase("true")) {
            mHomeView.lockDrawer();
        } else {
            mHomeView.unLockDrawer();
        }
    }

    @Override
    public void manageNetworkConnectivity(Context context) {
        int status = NetworkUtil.getConnectivityStatusString(context);
        if (status != NetworkUtil.NETWORK_STATUS_NOT_CONNECTED) {
            if (mShouldCallAPI) {
                getOnlineContents();
            }

        }
    }


    private int isViewVisible(View view, ScrollView scrollView) {
        Rect scrollBounds = new Rect();
        scrollView.getHitRect(scrollBounds);
        if (view.getLocalVisibleRect(scrollBounds)) {
            Rect rect = new Rect();
            view.getGlobalVisibleRect(rect);
            double visible = rect.width() * rect.height();
            double total = view.getWidth() * view.getHeight();
            int percentage = (int) (100 * visible / total);
            LogUtil.i(TAG, view.getTag().toString() + " " + percentage + " % Visible");
            if (percentage >= 50) {
                return percentage;
            } else {
                return -1;
            }

        } else {
            return -1;
        }
    }

    /**
     * Set the current user details and start the session accordingly
     *
     * @param profile
     * @param size
     */
    private void setCurrentUser(Profile profile, int size, boolean shouldCallApi) {
        if (profile.getUid() != null) {
            PreferenceUtil.setAnonymousSessionStatus(false);
            startNamedKidSession(profile, size, shouldCallApi);
        } else {
            PreferenceUtil.setAnonymousSessionStatus(true);
            startAnonymousUserSession(size, shouldCallApi);
        }
    }

    /**
     * Start known user session
     *
     * @param profile
     * @param size
     */
    private void startNamedKidSession(final Profile profile, final int size, final boolean shouldCallApi) {

        mUserService.setCurrentUser(profile.getUid(), new IResponseHandler<Void>() {
            @Override
            public void onSuccess(GenieResponse<Void> genieResponse) {
                isKnownUserProfile = true;
                Map<String, Object> eksMap = new HashMap<>();
                eksMap.put(TelemetryConstant.CHILDREN_ON_DEVICE, "" + size);
                TelemetryHandler.saveTelemetry(TelemetryBuilder.buildGEInteract(InteractionType.TOUCH, TelemetryStageId.GENIE_HOME, TelemetryAction.SWITCH_CHILD, profile.getUid(), eksMap));
                getCurrentUser();
            }

            @Override
            public void onError(GenieResponse<Void> genieResponse) {
                Util.processFailure(genieResponse);
            }
        });
    }

    /**
     * Start Anonymous user session
     *
     * @param size
     */
    private void startAnonymousUserSession(final int size, final boolean shouldCallApi) {
        mUserService.setAnonymousUser(new IResponseHandler<String>() {
            @Override
            public void onSuccess(GenieResponse<String> genieResponse) {
                isKnownUserProfile = false;

                getCurrentUser();
                Map<String, Object> eksMap = new HashMap<>();
                eksMap.put(TelemetryConstant.CHILDREN_ON_DEVICE, "" + size);
                TelemetryHandler.saveTelemetry(TelemetryBuilder.buildGEInteract(InteractionType.TOUCH, TelemetryStageId.GENIE_HOME, TelemetryAction.SWITCH_CHILD, "0", eksMap));
            }

            @Override
            public void onError(GenieResponse<String> genieResponse) {
                Util.processFailure(genieResponse);
            }
        });
    }

    /**
     * Get all the profiles
     */
    private void doGetAllProfiles() {

        mUserService.getAllUserProfile(new IResponseHandler<List<Profile>>() {
            @Override
            public void onSuccess(GenieResponse<List<Profile>> genieResponse) {
                final List<Profile> profileList = genieResponse.getResult();

                //check if their exists at least a profile and then add the anonymous user to the list of profiles
                if (profileList != null && profileList.size() >= 1) {
                    Profile profile = new Profile("", "", "");
                    profile.setHandle(mContext.getResources().getString(R.string.label_all_anonymous));
                    profile.setAvatar(Constant.DEFAULT_AVATAR);
                    profileList.add(profile);

                    mUserService.getCurrentUser(new IResponseHandler<Profile>() {
                        @Override
                        public void onSuccess(GenieResponse<Profile> genieResponse) {
                            mActiveProfile = genieResponse.getResult();

                            //show the list of profiles dialog
                            mHomeView.showSwitchProfileDialog(profileList, mActiveProfile);
                        }

                        @Override
                        public void onError(GenieResponse<Profile> genieResponse) {

                        }
                    });
                } else {
                    mHomeView.changeToAddChildActivity();
                }
            }

            @Override
            public void onError(GenieResponse<List<Profile>> genieResponse) {
                mHomeView.showAddChildImage();
            }
        });
    }

    /**
     * Gets the current user profile
     */
    @Override
    public void getCurrentUser() {
        mUserService.getCurrentUser(new IResponseHandler<Profile>() {
            @Override
            public void onSuccess(GenieResponse<Profile> genieResponse) {

                Profile profile = genieResponse.getResult();
//                if (mActiveProfile == null || !mActiveProfile.getUid().equalsIgnoreCase(profile.getUid())) {
                mActiveProfile = profile;

                if (TextUtils.isEmpty(profile.getHandle())) {
                    isKnownUserProfile = false;
                    if (PreferenceUtil.isAnonymousSessionStartedManually()) {
                        setProfileInfo(profile);
                    } else {
                        mHomeView.showAddChildImage();
                    }
                } else {
                    isKnownUserProfile = true;
                    setProfileInfo(profile);
                }
                mHomeView.generateGenieHomeTelemetry(profile.getUid());
                getOnlineContents();
            }
//            }

            @Override
            public void onError(GenieResponse<Profile> genieResponse) {
                mHomeView.showAddChildImage();
                isKnownUserProfile = false;
            }
        });

    }

    private void setProfileInfo(Profile profile) {
        if (TextUtils.isEmpty(profile.getHandle())) {
            profile.setHandle(mContext.getResources().getString(R.string.label_all_anonymous));
            profile.setAvatar(Constant.DEFAULT_AVATAR);
        }

        String avatar = profile.getAvatar();

        if (!profile.isGroupUser()) {
            Map<String, Integer> avatars = AvatarUtil.populateSwitchAvatars();

            if (!TextUtils.isEmpty(avatar) && avatars.get(avatar) != null) {
                mHomeView.showChildOrGroupProfileAvatar(avatars.get(avatar));
            } else {
                mHomeView.showChildOrGroupProfileAvatar(R.drawable.avatar_anonymous);
            }
        } else {
            Map<String, Integer> badges = AvatarUtil.populateBadges();

            if (!TextUtils.isEmpty(avatar) && badges.get(avatar) != null) {
                mHomeView.showChildOrGroupProfileAvatar(badges.get(avatar));
            } else {
                mHomeView.showChildOrGroupProfileAvatar(R.drawable.avatar_anonymous);
            }
        }
    }


    /**
     * Since we do not the user profile and other details, we need to start
     * anonymous session
     */
    private void startAnonymousSession() {
        mUserService.setAnonymousUser(new IResponseHandler<String>() {
            @Override
            public void onSuccess(GenieResponse<String> genieResponse) {

            }

            @Override
            public void onError(GenieResponse<String> genieResponse) {

            }
        });

    }

    @Override
    public void manageLocalContentImportSuccess(String importSuccess) {
        if (!StringUtil.isNullOrEmpty(importSuccess) && importSuccess.equalsIgnoreCase(Constant.EventKey.EVENT_KEY_IMPORT_LOCAL_CONTENT)) {
            getLocalContents();
            getOnlineContents();
            EventBus.removeStickyEvent(Constant.EventKey.EVENT_KEY_IMPORT_LOCAL_CONTENT);
        }
    }

    @Override
    public void manageImportnDeleteSuccess(Object response) {
        setLocalContentsCalledAlreadyToFalse();
        getLocalContents();
        getOnlineContents();
        EventBus.removeStickyEvent(response);
    }

//    @Override
//    public void editChildSuccess(String editChildSuccess) {
//        if (!StringUtil.isNullOrEmpty(editChildSuccess) && editChildSuccess.equalsIgnoreCase(Constant.EventKey.EVENT_KEY_EDIT_CHILD)) {
//            getCurrentUser();
//            getLocalContents();
//            EventBus.removeStickyEvent(Constant.EventKey.EVENT_KEY_EDIT_CHILD);
//        }
//    }

    @Override
    public void bindView(BaseView view, Context context) {
        mContext = context;
        mHomeView = (HomeContract.View) view;
    }

    @Override
    public void unbindView() {
        mContext = null;
        mHomeView = null;
    }

    @Override
    public void manageImportResponse(final ContentImportResponse response) {
        if (response.getStatus().getValue() == ContentImportStatus.IMPORT_COMPLETED.getValue()) {
            handleDownloadingAnimation(response.getIdentifier());
        }
        manageImportnDeleteSuccess(response);
    }

    @Override
    public void handleDownloadingAnimation(String identifier) {
        PreferenceUtil.removeDownloadQueueItem(identifier);
        List<DownloadQueueItem> mDownloadQueueItemList = PreferenceUtil.getDownloadQueueItemList();
        if (!CollectionUtil.isNullOrEmpty(mDownloadQueueItemList)) {
            for (int i = 0; i < mDownloadQueueItemList.size(); i++) {
                if (!ContentUtil.isContentImported(mSyncContentService, mDownloadQueueItemList.get(i).getIdentifier())) {
                    mHomeView.startDownloadAnimation();
                } else {
                    handleDownloadingAnimation(identifier);
                }
            }
        } else {
            mHomeView.stopDownloadAnimation();
        }
    }
}
