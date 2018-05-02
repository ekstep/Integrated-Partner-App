package org.ekstep.genie.ui.search;

import android.content.Context;

import org.ekstep.genie.CoreApplication;
import org.ekstep.genie.base.BaseView;
import org.ekstep.genie.telemetry.EnvironmentId;
import org.ekstep.genie.telemetry.TelemetryBuilder;
import org.ekstep.genie.telemetry.TelemetryConstant;
import org.ekstep.genie.telemetry.TelemetryHandler;
import org.ekstep.genie.telemetry.TelemetryStageId;
import org.ekstep.genie.telemetry.enums.CoRelationIdContext;
import org.ekstep.genie.telemetry.enums.ImpressionType;
import org.ekstep.genie.util.Constant;
import org.ekstep.genie.util.geniesdk.ConfigUtil;
import org.ekstep.genie.util.geniesdk.ContentUtil;
import org.ekstep.genie.util.preference.PreferenceUtil;
import org.ekstep.genieservices.async.ContentService;
import org.ekstep.genieservices.commons.IResponseHandler;
import org.ekstep.genieservices.commons.bean.ContentData;
import org.ekstep.genieservices.commons.bean.ContentImportResponse;
import org.ekstep.genieservices.commons.bean.ContentSearchCriteria;
import org.ekstep.genieservices.commons.bean.ContentSearchFilter;
import org.ekstep.genieservices.commons.bean.ContentSearchResult;
import org.ekstep.genieservices.commons.bean.ContentSortCriteria;
import org.ekstep.genieservices.commons.bean.CorrelationData;
import org.ekstep.genieservices.commons.bean.FilterValue;
import org.ekstep.genieservices.commons.bean.GenieResponse;
import org.ekstep.genieservices.commons.bean.Profile;
import org.ekstep.genieservices.commons.bean.RecommendedContentRequest;
import org.ekstep.genieservices.commons.bean.RecommendedContentResult;
import org.ekstep.genieservices.commons.bean.RelatedContentRequest;
import org.ekstep.genieservices.commons.bean.RelatedContentResult;
import org.ekstep.genieservices.commons.bean.enums.ContentImportStatus;
import org.ekstep.genieservices.commons.bean.enums.InteractionType;
import org.ekstep.genieservices.commons.bean.enums.SortOrder;
import org.ekstep.genieservices.commons.utils.CollectionUtil;
import org.ekstep.genieservices.commons.utils.GsonUtil;
import org.ekstep.genieservices.commons.utils.StringUtil;
import org.ekstep.genieservices.content.ContentConstants;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created on 7/9/17.
 * shriharsh
 */

public class SearchPresenter implements SearchContract.Presenter {

    private ContentService mContentService;
    private SearchContract.View mSearchView;
    private List<ContentData> mContentDataList;
    private ContentSearchCriteria mContentSearchCriteria;
    private Context mContext;


    public SearchPresenter() {
        mContentService = CoreApplication.getGenieAsyncService().getContentService();
    }

    @Override
    public void bindView(BaseView view, Context context) {
        mSearchView = (SearchContract.View) view;
        mContext = context;
    }

    @Override
    public void unbindView() {
        mSearchView = null;
        mContext = null;
    }

    public void fetchRecommendedContent(final String searchedQuery) {
        RecommendedContentRequest request = new RecommendedContentRequest.Builder().byLanguage(PreferenceUtil.getLanguage()).build();
        mContentService.getRecommendedContent(request, new IResponseHandler<RecommendedContentResult>() {
            @Override
            public void onSuccess(GenieResponse<RecommendedContentResult> genieResponse) {
                if (mSearchView == null) {
                    return;
                }
                RecommendedContentResult recommendedSearchResult = genieResponse.getResult();
                saveCoRelationContext(recommendedSearchResult.getResponseMessageId(), recommendedSearchResult.getId());
                mContentDataList = recommendedSearchResult.getContents();
                mSearchView.setContentsList(mContentDataList);
                generateSearchEvents(new HashMap<String, Object>(), mContentDataList.size(), ACTION_NONE, searchedQuery, false);
                mSearchView.processSearchResult(mContentDataList, null);
            }

            @Override
            public void onError(GenieResponse<RecommendedContentResult> genieResponse) {
                mSearchView.processSearchFailure(genieResponse.getError());
            }
        });
    }

    public void fetchRelatedContent(String contentId) {
        RelatedContentRequest request = new RelatedContentRequest.Builder().forContent(contentId).build();
        mContentService.getRelatedContent(request, new IResponseHandler<RelatedContentResult>() {
            @Override
            public void onSuccess(GenieResponse<RelatedContentResult> genieResponse) {
                if (mSearchView == null) {
                    return;
                }
                RelatedContentResult relatedResult = genieResponse.getResult();
                saveCoRelationContext(relatedResult.getResponseMessageId(), relatedResult.getId());
                // TODO: 6/23/2017 - Uncomment this onse it will return ContentData instead of Content.
//                mContentDataList = relatedResult.getRelatedContents();
//                generateSearchEvents(new HashMap<String, Object>(), mContentDataList.size(), ACTION_NONE);
                mSearchView.processSearchResult(mContentDataList, null);
            }

            @Override
            public void onError(GenieResponse<RelatedContentResult> genieResponse) {
                mSearchView.processSearchFailure(genieResponse.getError());
            }
        });
    }

    private void saveCoRelationContext(String msgId, String coRelationType) {
        if (msgId != null) {
            PreferenceUtil.setCoRelationIdContext(CoRelationIdContext.SEARCH);
            PreferenceUtil.setSearchApiResponseMessageId(msgId);
            PreferenceUtil.setCoRelationType(coRelationType);
        } else {
            PreferenceUtil.setSearchApiResponseMessageId(null);
        }
    }

    private void generateSearchEvents(Map<String, Object> request, int size, int actionId, String searchedQuery, boolean isSearchedExplicitly) {
        Map<String, Object> params = new HashMap<>();

        //Generate events for sort/filter
        switch (actionId) {
            case ACTION_SORT:
                if (mContentSearchCriteria != null && !CollectionUtil.isNullOrEmpty(mContentSearchCriteria.getSortCriteria())) {
                    ContentSortCriteria sortCriteria = mContentSearchCriteria.getSortCriteria().get(0);
                    Map<String, String> criteria = new HashMap<>();
                    criteria.put(sortCriteria.getSortAttribute(), sortCriteria.getSortOrder().getValue());
                    params.put(TelemetryConstant.SORT_CRITERIA, criteria);
//                    TelemetryHandler.saveTelemetry(TelemetryBuilder.buildGEInteract(InteractionType.SHOW, EntityType.SORT, null, searchedQuery, params));
                    TelemetryHandler.saveTelemetry(TelemetryBuilder.buildInteractEvent(EnvironmentId.HOME, InteractionType.TOUCH, null, TelemetryStageId.CONTENT_SEARCH, params));
                } else {
                    Map<String, String> criteria = new HashMap<>();
                    criteria.put("relevance", "");
                    params.put(TelemetryConstant.SORT_CRITERIA, criteria);
//                    TelemetryHandler.saveTelemetry(TelemetryBuilder.buildGEInteract(InteractionType.SHOW, EntityType.SORT, null, searchedQuery, params));
                    TelemetryHandler.saveTelemetry(TelemetryBuilder.buildInteractEvent(EnvironmentId.HOME, InteractionType.TOUCH, null, TelemetryStageId.CONTENT_SEARCH, params));
                }
                break;

            case ACTION_FILTER:
                if (mContentSearchCriteria != null && mContentSearchCriteria.getFacetFilters() != null) {
                    params.put(TelemetryConstant.FILTER_CRITERIA, getFilterCriteria(mContentSearchCriteria.getFacetFilters()));
                    TelemetryHandler.saveTelemetry(TelemetryBuilder.buildInteractEvent(EnvironmentId.HOME, InteractionType.TOUCH, null, TelemetryStageId.CONTENT_SEARCH, params));
                }
                break;
        }

        //Generate Screen view telemetry events
        List<CorrelationData> cdata = PreferenceUtil.getCoRelationList();
        params.clear();
        params.put(TelemetryConstant.SEARCH_RESULTS, size);
        params.put(TelemetryConstant.SEARCH_CRITERIA, request);
        params.put(TelemetryConstant.SEARCH_PHRASE, searchedQuery);
        if (isSearchedExplicitly) {

            TelemetryHandler.saveTelemetry(TelemetryBuilder.buildImpressionEvent(EnvironmentId.HOME, TelemetryStageId.CONTENT_SEARCH, ImpressionType.SEARCH, TelemetryConstant.EXPLICIT_SEARCH, cdata));
            TelemetryHandler.saveTelemetry(TelemetryBuilder.buildLogEvent(EnvironmentId.HOME, TelemetryStageId.CONTENT_SEARCH, ImpressionType.SEARCH, TelemetryStageId.CONTENT_SEARCH, params));
        } else {

            TelemetryHandler.saveTelemetry(TelemetryBuilder.buildImpressionEvent(EnvironmentId.HOME, TelemetryStageId.CONTENT_LIST, ImpressionType.SEARCH, TelemetryConstant.IMPLICIT_SEARCH, cdata));
            TelemetryHandler.saveTelemetry(TelemetryBuilder.buildLogEvent(EnvironmentId.HOME, TelemetryStageId.CONTENT_LIST, ImpressionType.SEARCH, TelemetryConstant.IMPLICIT_SEARCH, params));
        }
    }

    private Map<String, List<String>> getFilterCriteria(List<ContentSearchFilter> filterCriteria) {
        Map<String, List<String>> filter = new HashMap<>();
        for (int i = 0; i < filterCriteria.size(); i++) {
            ContentSearchFilter searchFilter = filterCriteria.get(i);
            List<FilterValue> filterValueList = searchFilter.getValues();
            List<String> valueList = new ArrayList<>();
            if (!CollectionUtil.isNullOrEmpty(filterValueList)) {
                for (FilterValue value : filterValueList) {
                    if (value.isApply()) {
                        valueList.add(value.getName());
                    }
                }
            }

            if (!CollectionUtil.isNullOrEmpty(valueList)) {
                filter.put(searchFilter.getName(), valueList);
            }
        }
        return filter;
    }

    private void applyPartnerFilter(ContentSearchCriteria.SearchBuilder searchBuilder) {
        searchBuilder.facets(ConfigUtil.getFilterConfig(ContentConstants.CONFIG_FACETS));
        searchBuilder.contentTypes(ConfigUtil.getFilterConfig(ContentConstants.CONFIG_CONTENT_TYPE));
        String partnerInfo = PreferenceUtil.getPartnerInfo();
        if (!StringUtil.isNullOrEmpty(partnerInfo)) {
            HashMap<String, Object> partnerMap = GsonUtil.fromJson(partnerInfo, HashMap.class);
            //Apply Channel filter
            if (partnerMap.containsKey(Constant.BUNDLE_KEY_PARTNER_CHANNEL_ARRAY)) {
                ArrayList<String> channel = (ArrayList<String>) partnerMap.get(Constant.BUNDLE_KEY_PARTNER_CHANNEL_ARRAY);
                if (channel != null) {
                    searchBuilder.channel(channel.toArray(new String[channel.size()]));
                }
            }
            //Apply audience filter
            if (partnerMap.containsKey(Constant.BUNDLE_KEY_PARTNER_AUDIENCE_ARRAY)) {
                ArrayList<String> audience = (ArrayList<String>) partnerMap.get(Constant.BUNDLE_KEY_PARTNER_AUDIENCE_ARRAY);
                if (audience != null) {
                    searchBuilder.audience(audience.toArray(new String[audience.size()]));
                }
            }
            //Apply pragma filter
            if (partnerMap.containsKey(Constant.BUNDLE_KEY_PARTNER_PRAGMA_ARRAY)) {
                ArrayList<String> pragma = (ArrayList<String>) partnerMap.get(Constant.BUNDLE_KEY_PARTNER_PRAGMA_ARRAY);
                if (pragma != null) {
                    searchBuilder.excludePragma(pragma.toArray(new String[pragma.size()]));
                }
            }
        } else {
            //Apply pragma filter
            searchBuilder.excludePragma(ConfigUtil.getFilterConfig(ContentConstants.CONFIG_EXCLUDE_PRAGMA));

        }

    }

    private void applyProfileFilter(ContentSearchCriteria.SearchBuilder searchBuilder, Profile profile) {
        if (profile != null) {
            searchBuilder.age(profile.getAge());
            searchBuilder.board(profile.getBoard());
            searchBuilder.grade(profile.getStandard());
            searchBuilder.medium(profile.getMedium());
        }
    }

    public void applyFilters(ContentSearchCriteria.SearchBuilder searchBuilder, Profile profile) {
        applyPartnerFilter(searchBuilder);
        applyProfileFilter(searchBuilder, profile);
    }

    public void searchContent(final ContentSearchCriteria contentSearchCriteria, final int actionId, final boolean isSearchedExplicitly) {
        ContentUtil.addOrUpdateSearchHistoryList(contentSearchCriteria.getQuery());
        mContentService.searchContent(contentSearchCriteria, new IResponseHandler<ContentSearchResult>() {
            @Override
            public void onSuccess(GenieResponse<ContentSearchResult> genieResponse) {
                if (mSearchView == null) {
                    return;
                }

                ContentSearchResult contentSearchResult = genieResponse.getResult();
                mSearchView.setContentSearchResult(contentSearchResult);
                mContentSearchCriteria = contentSearchResult.getFilterCriteria();
                mSearchView.setContentSearchCriteria(mContentSearchCriteria);

                saveCoRelationContext(contentSearchResult.getResponseMessageId(), contentSearchResult.getId());
                if (contentSearchResult.getContentDataList() != null) {
                    mContentDataList = contentSearchResult.getContentDataList();
                } else {
                    //this is done to avoid null pointer exception
                    mContentDataList = new ArrayList<>();
                }

                mSearchView.setContentsList(mContentDataList);

                generateSearchEvents(contentSearchResult.getRequest(), mContentDataList.size(), actionId, contentSearchCriteria.getQuery(), isSearchedExplicitly);
                List<ContentSearchFilter> filter = null;
                if (contentSearchResult.getFilterCriteria() != null) {
                    filter = contentSearchResult.getFilterCriteria().getFacetFilters();
                }

                mSearchView.processSearchResult(mContentDataList, filter);
            }

            @Override
            public void onError(GenieResponse<ContentSearchResult> genieResponse) {
                mSearchView.processSearchFailure(genieResponse.getError());
            }
        });

        mSearchView.showSearchingContent();
    }

    public void applySortNSearch(String sortType, SortOrder sortOrder, boolean isSearchedExplicitly) {
        if (!StringUtil.isNullOrEmpty(sortType)) {
            if (mContentSearchCriteria.getSortCriteria().isEmpty()) {
                ContentSortCriteria contentSortCriteria = new ContentSortCriteria(sortType, sortOrder);
                mContentSearchCriteria.getSortCriteria().add(contentSortCriteria);
            } else {
                mContentSearchCriteria.getSortCriteria().get(0).setSortAttribute(sortType);
                mContentSearchCriteria.getSortCriteria().get(0).setSortOrder(sortOrder);
            }
        } else {
            mContentSearchCriteria.getSortCriteria().clear();
        }

        searchContent(mContentSearchCriteria, ACTION_SORT, isSearchedExplicitly);
    }

    @Override
    public void manageImportResponse(ContentImportResponse importResponse) {
        if (importResponse.getStatus().getValue() == ContentImportStatus.IMPORT_COMPLETED.getValue()) {
            Set<String> identifierList = new HashSet<>();
            identifierList.add(importResponse.getIdentifier());
            ContentUtil.setLocalContentsCache(identifierList);
            if (mSearchView.getIdentifierList().contains(importResponse.getIdentifier())) {
                mSearchView.refreshContentListAdapter(mContentDataList);
            }
        }
    }

    public void hideSoftKeyboard() {
        mSearchView.hideSoftKeyBoard();
    }
}
