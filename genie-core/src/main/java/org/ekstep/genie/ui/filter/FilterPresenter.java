package org.ekstep.genie.ui.filter;

import android.content.Context;
import android.content.Intent;

import org.ekstep.genie.R;
import org.ekstep.genie.base.BaseView;
import org.ekstep.genie.util.Constant;
import org.ekstep.genieservices.commons.bean.ContentSearchCriteria;
import org.ekstep.genieservices.commons.bean.ContentSearchFilter;
import org.ekstep.genieservices.commons.bean.FilterValue;
import org.ekstep.genieservices.commons.utils.CollectionUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created on 30/6/17.
 *
 * @author swayangjit
 */
public class FilterPresenter implements FilterContract.Presenter {

    private Context mContext;
    private FilterContract.View mFilterView;
    private ContentSearchCriteria mContentSearchCriteria;
    private String mCurrentFacet;

    public FilterPresenter() {
    }

    @Override
    public void fetchBundleExtras(Intent intent) {
        mContentSearchCriteria = (ContentSearchCriteria) intent.getSerializableExtra(Constant.BundleKey.BUNDLE_KEY_CONTENT_FILTER_FACETS);
        mFilterView.showFilterTypeList(mContentSearchCriteria.getFacetFilters());
        if (!CollectionUtil.isNullOrEmpty(mContentSearchCriteria.getFacetFilters())) {
            mCurrentFacet = mContentSearchCriteria.getFacetFilters().get(0).getName();
            handleFilterVisibility(true, mCurrentFacet);
        }
    }

    @Override
    public void handleBackClick() {
        mFilterView.finishActivity();
    }

    @Override
    public void handleApplyClick() {
        mFilterView.navigateToSearchActivity(mContentSearchCriteria);
    }

    @Override
    public void handleFilterTypeItemClick(int position) {
        mFilterView.refreshFilterTypeList();
        mCurrentFacet = mContentSearchCriteria.getFacetFilters().get(position).getName();
        handleFilterVisibility(true, mCurrentFacet);
    }

    @Override
    public void clearAllFilters() {
        for (ContentSearchFilter filter : mContentSearchCriteria.getFacetFilters()) {
            markFilterAppliedStatus(null, filter.getName(), true, false);
        }
    }

    @Override
    public void markFilterAppliedStatus(String filter, String filterType, boolean markAll, boolean status) {
        List<FilterValue> valuesList = getValueList(mContentSearchCriteria.getFacetFilters(), filterType);
        for (FilterValue value : valuesList) {
            if (markAll) {
                value.setApply(status);
            } else {
                if (filter.equalsIgnoreCase(value.getName()) || value.getName().equalsIgnoreCase(mContext.getString(R.string.title_filter_all))) {
                    value.setApply(status);
                }
            }
        }
    }

    @Override
    public void handleFilterVisibility(boolean addAll, String facetType) {
        if (!CollectionUtil.isNullOrEmpty(mContentSearchCriteria.getFacetFilters())) {
            if (facetType == null) {
                facetType = mCurrentFacet;
            }
            List<FilterValue> filterValueList = getValueList(mContentSearchCriteria.getFacetFilters(), facetType);
            mFilterView.removePreviousFilter();
            if (addAll) {
                FilterValue value = new FilterValue();
                value.setName(mContext.getString(R.string.title_filter_all));
                value.setApply(isAllApplied(facetType));
                if (!filterValueList.isEmpty()
                        && filterValueList.get(0).getName().equalsIgnoreCase(mContext.getString(R.string.title_filter_all))) {
                    filterValueList.set(0, value);
                } else {
                    filterValueList.add(0, value);
                }
            }

            for (FilterValue value : filterValueList) {
                mFilterView.showFilters(value, facetType);
            }
        }
    }

    private boolean isAllApplied(String facetType) {
        List<FilterValue> valuesList = getValueList(mContentSearchCriteria.getFacetFilters(), facetType);
        for (FilterValue value : valuesList) {
            if (!value.isApply()) {
                return false;
            }
        }
        return true;
    }

    private List<FilterValue> getValueList(List<ContentSearchFilter> facetsList, String facetType) {
        for (ContentSearchFilter value : facetsList) {
            if (value.getName().toLowerCase().equalsIgnoreCase(facetType.toLowerCase())) {
                return value.getValues();
            }
        }
        return new ArrayList<>();
    }

    @Override
    public void bindView(BaseView view, Context context) {
        mContext = context;
        mFilterView = (FilterContract.View) view;
    }

    @Override
    public void unbindView() {
        mContext = null;
        mFilterView = null;
    }
}
