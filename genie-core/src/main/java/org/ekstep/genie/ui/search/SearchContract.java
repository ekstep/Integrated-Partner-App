package org.ekstep.genie.ui.search;

import org.ekstep.genie.base.BasePresenter;
import org.ekstep.genie.base.BaseView;
import org.ekstep.genieservices.commons.bean.ContentData;
import org.ekstep.genieservices.commons.bean.ContentImportResponse;
import org.ekstep.genieservices.commons.bean.ContentSearchCriteria;
import org.ekstep.genieservices.commons.bean.ContentSearchFilter;
import org.ekstep.genieservices.commons.bean.ContentSearchResult;
import org.ekstep.genieservices.commons.bean.Profile;
import org.ekstep.genieservices.commons.bean.enums.SortOrder;

import java.util.List;
import java.util.Set;

/**
 * Created on 7/9/17.
 * shriharsh
 */

public interface SearchContract {

    interface View extends BaseView {

        int REQUEST_GET_FILTER_CRITERIA = 1;
        int REQUEST_GET_REFRESH_INTENT = 2;

        void processSearchResult(List<ContentData> contentDataList, List<ContentSearchFilter> appliedFilters);

        void processSearchFailure(String errorCode);

        void navigateToContentDetailsActivity(ContentData contentData);

        void showSearchingContent();

        void setContentSearchResult(ContentSearchResult contentSearchResult);

        void setContentSearchCriteria(ContentSearchCriteria contentSearchCriteria);

        void setContentsList(List<ContentData> contentDataList);

        //// TODO: 12/9/17 This method should be removed, as currently SearchHistortAdapter has dependency, so is kept
        void hideSoftKeyBoard();

        void refreshContentListAdapter(List<ContentData> contentDataList);

        Set<String> getIdentifierList();
    }

    interface Presenter extends BasePresenter {

        int ACTION_NONE = -1;
        int ACTION_SORT = 1;
        int ACTION_FILTER = 2;
        int REQUEST_GET_FILTER_CRITERIA = 1;
        int REQUEST_GET_REFRESH_INTENT = 2;

        void fetchRecommendedContent(String searchedQuery);

        void fetchRelatedContent(String contentId);

        void applyFilters(ContentSearchCriteria.SearchBuilder searchBuilder, Profile profile);

        void applySortNSearch(String sortType, SortOrder sortOrder, boolean isSearchedExplicitly);

        void manageImportResponse(ContentImportResponse importResponse);
    }
}
