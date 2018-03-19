package org.ekstep.genie.ui.settings.advancedsettings;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import org.ekstep.genie.R;
import org.ekstep.genie.base.BasePresenter;
import org.ekstep.genie.base.BaseView;
import org.ekstep.genie.base.IPresenterFactory;
import org.ekstep.genie.customview.EkStepCustomTextView;
import org.ekstep.genie.fragment.BaseFragment;
import org.ekstep.genie.telemetry.EnvironmentId;
import org.ekstep.genie.telemetry.TelemetryBuilder;
import org.ekstep.genie.telemetry.TelemetryHandler;
import org.ekstep.genie.telemetry.TelemetryStageId;
import org.ekstep.genie.telemetry.enums.ImpressionType;
import org.ekstep.genie.ui.settings.advancedsettings.addnewtag.AddNewTagActivity;
import org.ekstep.genieservices.commons.bean.Tag;

import java.util.List;

public class AdvancedSettingsFragment extends BaseFragment implements AdvancedSettingsContract.View, OnClickListener {

    private static final String TAG = AdvancedSettingsFragment.class.getSimpleName();

    private static final String KEY_TAG_DATA = "tag_data";
    private RecyclerView mRecyclerView = null;
    private EkStepCustomTextView mTxtCreateNewTag = null;
    private RelativeLayout mLayoutTags = null;
    private RelativeLayout mLayoutNoTag = null;

    private AdvancedSettingsPresenter mPresenter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = (AdvancedSettingsPresenter) presenter;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_advanced_settings, container, false);
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.handleGetTags();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initViews(view);
        mPresenter.handleGetTags();
//        TelemetryHandler.saveTelemetry(TelemetryBuilder.buildGEInteract(TelemetryStageId.SETTINGS_ADVANCED));
        TelemetryHandler.saveTelemetry(TelemetryBuilder.buildImpressionEvent(EnvironmentId.SETTINGS, TelemetryStageId.SETTINGS_ADVANCED, ImpressionType.VIEW));
    }

    @Override
    protected IPresenterFactory getPresenterFactory() {
        return new IPresenterFactory() {
            @Override
            public BasePresenter create() {
                return new AdvancedSettingsPresenter();
            }
        };
    }

    @Override
    protected String getTAG() {
        return getClass().getName();
    }

    @Override
    protected BaseView getBaseView() {
        return this;
    }

    private void initViews(View view) {

        mTxtCreateNewTag = (EkStepCustomTextView) view.findViewById(R.id.txt_create_tag);
        mTxtCreateNewTag.setOnClickListener(this);
        mLayoutTags = ((RelativeLayout) view.findViewById(R.id.layout_program_tags));
        mLayoutNoTag = ((RelativeLayout) view.findViewById(R.id.layout_no_tag));
        mLayoutNoTag.setOnClickListener(this);

        mRecyclerView = (RecyclerView) view.findViewById(R.id.recylerview_tags);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setHasFixedSize(true);
    }

    @Override
    public void showNoTagsLayout() {
        mLayoutNoTag.setVisibility(View.VISIBLE);
        mTxtCreateNewTag.setVisibility(View.GONE);
        mLayoutTags.setVisibility(View.GONE);
    }

    @Override
    public void hideNoTagsLayout() {
        mLayoutNoTag.setVisibility(View.GONE);
        mTxtCreateNewTag.setVisibility(View.VISIBLE);
        mLayoutTags.setVisibility(View.VISIBLE);
    }

    @Override
    public void showTagList(List<Tag> tagList) {
        TagsListAdapter tagsListAdapter = new TagsListAdapter(getActivity(), tagList, mPresenter);
        mRecyclerView.setAdapter(tagsListAdapter);
    }

    @Override
    public void navigateToAddNewTag(Tag tag) {
        Intent intent = new Intent(getActivity(), AddNewTagActivity.class);
        intent.putExtra(KEY_TAG_DATA, tag);
        startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.txt_create_tag || i == R.id.layout_no_tag) {
            Intent intent = new Intent(getActivity(), AddNewTagActivity.class);
            startActivity(intent);

        } else {
        }
    }
}
