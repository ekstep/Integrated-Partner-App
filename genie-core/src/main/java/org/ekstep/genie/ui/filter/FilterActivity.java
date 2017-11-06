package org.ekstep.genie.ui.filter;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckedTextView;
import android.widget.LinearLayout;

import org.ekstep.genie.R;
import org.ekstep.genie.activity.BaseActivity;
import org.ekstep.genie.base.BasePresenter;
import org.ekstep.genie.base.BaseView;
import org.ekstep.genie.base.IPresenterFactory;
import org.ekstep.genie.customview.EkStepNotoCheckedTextView;
import org.ekstep.genie.customview.SimpleDividerItemDecoration;
import org.ekstep.genie.util.Constant;
import org.ekstep.genie.util.FontUtil;
import org.ekstep.genieservices.commons.bean.ContentSearchCriteria;
import org.ekstep.genieservices.commons.bean.ContentSearchFilter;
import org.ekstep.genieservices.commons.bean.FilterValue;

import java.util.List;

/**
 * Created on 30/6/17.
 *
 * @author swayangjit
 */
public class FilterActivity extends BaseActivity
        implements View.OnClickListener, FilterContract.View {

    private FilterContract.Presenter mPresenter;
    private LinearLayout mLayout_Category;
    private RecyclerView mRecyclerView_Filter;
    private FilterTypeAdapter mFilterTypeAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_filter);

        mPresenter = (FilterPresenter)presenter;

        initViews();

        mPresenter.fetchBundleExtras(getIntent());
    }

    @Override
    protected IPresenterFactory getPresenterFactory() {
        return new IPresenterFactory() {
            @Override
            public BasePresenter create() {
                return new FilterPresenter();
            }
        };
    }

    @Override
    protected String getTAG() {
        return getLocalClassName();
    }

    @Override
    protected BaseView getBaseView() {
        return this;
    }

    private void initViews() {

        mRecyclerView_Filter = (RecyclerView) findViewById(R.id.recyclerview_filter);
        mRecyclerView_Filter.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView_Filter.addItemDecoration(new SimpleDividerItemDecoration(this));
        mRecyclerView_Filter.setHasFixedSize(true);
        mLayout_Category = (LinearLayout) findViewById(R.id.layout_category);
        findViewById(R.id.back_btn).setOnClickListener(this);
        findViewById(R.id.apply_btn).setOnClickListener(this);
        findViewById(R.id.clear_btn).setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.back_btn) {
            mPresenter.handleBackClick();

        } else if (i == R.id.apply_btn) {
            mPresenter.handleApplyClick();
            overridePendingTransition(R.anim.push_down_in, R.anim.push_down_out);

        } else if (i == R.id.clear_btn) {
            mPresenter.clearAllFilters();
            mPresenter.handleFilterVisibility(true, null);

        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        mPresenter.handleBackClick();
    }

    @Override
    public void finishActivity() {
        finish();
        overridePendingTransition(R.anim.push_down_in, R.anim.push_down_out);
    }

    @Override
    public void navigateToSearchActivity(ContentSearchCriteria contentSearchCriteria) {
        Intent intent = new Intent();
        intent.putExtra(Constant.BundleKey.BUNDLE_KEY_CONTENT_FILTER_FACETS, contentSearchCriteria);
        setResult(Activity.RESULT_OK, intent);
        finish();
        overridePendingTransition(R.anim.push_down_in, R.anim.push_down_out);
    }

    @Override
    public void showFilterTypeList(List<ContentSearchFilter> contentSearchFilter) {
        mFilterTypeAdapter = new FilterTypeAdapter(this, mPresenter);
        mFilterTypeAdapter.setData(contentSearchFilter);
        mRecyclerView_Filter.setAdapter(mFilterTypeAdapter);
    }

    @Override
    public void refreshFilterTypeList() {
        mFilterTypeAdapter.notifyDataSetChanged();
    }

    @Override
    public void removePreviousFilter() {
        mLayout_Category.removeAllViews();
    }

    @Override
    public void showFilters(FilterValue filterValue, final String facetType) {
        View view = getLayoutInflater().inflate(R.layout.layout_filter_check_item, null);

        EkStepNotoCheckedTextView ekStepCheckedTextView = (EkStepNotoCheckedTextView) view.findViewById(R.id.checkedText);
        String resourceName = FontUtil.getInstance().getResourceName(filterValue.getName());

        ekStepCheckedTextView.setText(resourceName);
        ekStepCheckedTextView.setTag(filterValue.getName());

        if (filterValue.isApply()) {
            ekStepCheckedTextView.setChecked(true);
        }

        ekStepCheckedTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((CheckedTextView) v).toggle();
                String tag = v.getTag().toString();
                if (((CheckedTextView) v).isChecked()) {
                    if (tag.equalsIgnoreCase(getResources().getString(R.string.title_filter_all))) {
                        mPresenter.markFilterAppliedStatus(tag, facetType, true, true);
                        removePreviousFilter();
                        mPresenter.handleFilterVisibility(false, facetType);
                    } else {
                        mPresenter.markFilterAppliedStatus(tag, facetType, false, true);
                    }
                } else {
                    mPresenter.markFilterAppliedStatus(tag, facetType, false, false);
                    mPresenter.handleFilterVisibility(false, facetType);
                }
            }
        });

        mLayout_Category.addView(view);
    }
}
