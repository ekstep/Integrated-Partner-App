package org.ekstep.genie.ui.filter;

import android.content.Intent;

import org.ekstep.genie.base.BasePresenter;
import org.ekstep.genie.base.BaseView;
import org.ekstep.genieservices.commons.bean.ContentSearchCriteria;
import org.ekstep.genieservices.commons.bean.ContentSearchFilter;
import org.ekstep.genieservices.commons.bean.FilterValue;

import java.util.List;

/**
 * Created on 30/6/17.
 *
 * @author swayangjit
 */
public interface FilterContract {

    interface View extends BaseView {

        void finishActivity();

        void navigateToSearchActivity(ContentSearchCriteria contentSearchCriteria);

        void showFilterTypeList(List<ContentSearchFilter> contentSearchFilter);

        void refreshFilterTypeList();

        void removePreviousFilter();

        void showFilters(FilterValue filterValue, String facetType);
    }

    interface Presenter extends BasePresenter {

        void fetchBundleExtras(Intent intent);

        void handleBackClick();

        void handleApplyClick();

        void handleFilterTypeItemClick(int position);

        void clearAllFilters();

        void markFilterAppliedStatus(String filter, String filterType, boolean markAll, boolean status);

        void handleFilterVisibility(boolean addAll, final String facetType);
    }
}
