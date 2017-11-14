package org.ekstep.genie.ui.progressreports;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import org.ekstep.genie.R;
import org.ekstep.genie.base.BasePresenter;
import org.ekstep.genie.base.BaseView;
import org.ekstep.genie.base.IPresenterFactory;
import org.ekstep.genie.fragment.BaseFragment;
import org.ekstep.genie.ui.addchild.AddChildActivity;
import org.ekstep.genie.ui.progressreportdetail.ProgressReportDetailActivity;
import org.ekstep.genie.util.Constant;
import org.ekstep.genie.util.DeviceUtility;
import org.ekstep.genie.util.GlideImageUtil;
import org.ekstep.genie.util.Util;
import org.ekstep.genie.util.geniesdk.UserProfileUtil;
import org.ekstep.genieservices.commons.bean.Content;
import org.ekstep.genieservices.commons.bean.HierarchyInfo;
import org.ekstep.genieservices.commons.bean.LearnerAssessmentSummary;
import org.ekstep.genieservices.commons.bean.Profile;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 * Created on 12/23/2016.
 *
 * @author anil
 */
public class ProgressReportsFragment extends BaseFragment
        implements ProgressReportsContract.View, View.OnClickListener {

    private ProgressReportsContract.Presenter mPresenter;

    private RecyclerView mRecyclerView;

    private TextView mNameTv;
    private TextView mTitleTv;
    private ImageView mContentIconIv;
    private ImageView mProfileIconIv;
    private View mProfileActionView;

    private View mProgressReportView;
    private TextView mAverageTimeTv;
    private TextView mScoreTv;
    private TextView mProgressReportTypeTv;

    private TextView mNoProgressReportTv;
    private TextView mEditTv;
    private TextView mDeleteTv;
    private Dialog mDialog;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = (ProgressReportsPresenter)presenter;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_progress_reports, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews(view);
        mPresenter.fetchBundleExtras(getArguments());
    }

    @Override
    protected IPresenterFactory getPresenterFactory() {
        return new IPresenterFactory() {
            @Override
            public BasePresenter create() {
                return new ProgressReportsPresenter();
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
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_summerizer);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setFocusable(false);

        mProfileActionView = view.findViewById(R.id.layout_action);

        mNameTv = (TextView) view.findViewById(R.id.txt_kid_name);
        mProfileIconIv = (ImageView) view.findViewById(R.id.img_avatar);
        mContentIconIv = (ImageView) view.findViewById(R.id.img_content);
        mTitleTv = (TextView) view.findViewById(R.id.txt_header);

        mProgressReportView = view.findViewById(R.id.layout_progres_report);
        mAverageTimeTv = (TextView) view.findViewById(R.id.txt_average_time);
        mScoreTv = (TextView) view.findViewById(R.id.txt_score);
        mProgressReportTypeTv = (TextView) view.findViewById(R.id.txt_summerizer_type);

        mNoProgressReportTv = (TextView) view.findViewById(R.id.txt_no_progress_report);

        view.findViewById(R.id.back_btn).setOnClickListener(this);
        mEditTv = (TextView) view.findViewById(R.id.txt_edit);
        mEditTv.setOnClickListener(this);
        mDeleteTv = (TextView) view.findViewById(R.id.txt_delete);
        mDeleteTv.setOnClickListener(this);
    }

    @Override
    public void showToast(int resId) {
        Util.showCustomToast(resId);
    }

    @Override
    public void showTitle(int title) {
        mTitleTv.setText(getResources().getString(title));
    }

    @Override
    public void showName(String name) {
        mNameTv.setText(name);
    }

    @Override
    public void showContentIcon(String url) {
        mContentIconIv.setVisibility(View.VISIBLE);
        GlideImageUtil.loadImageUrl(getActivity(), url, mContentIconIv);
    }

    @Override
    public void hideContentIcon() {
        mContentIconIv.setVisibility(View.GONE);
    }

    @Override
    public void showProfileIcon(String avatar, boolean isGroup) {
        mProfileIconIv.setVisibility(View.VISIBLE);
        UserProfileUtil.setProfileAvatar(mProfileIconIv, avatar, isGroup);
    }

    @Override
    public void hideProfileIcon() {
        mProfileIconIv.setVisibility(View.GONE);
    }

    @Override
    public void hideEditOption() {
        mEditTv.setVisibility(View.GONE);
    }

    @Override
    public void hideDeleteOption() {
        mDeleteTv.setVisibility(View.GONE);
    }

    @Override
    public void showProfileAction() {
        mProfileActionView.setVisibility(View.VISIBLE);
    }

    @Override
    public void showProgressReport() {
        mProgressReportView.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgressReport() {
        mProgressReportView.setVisibility(View.GONE);
    }

    @Override
    public void showAssesmentSummary(List<LearnerAssessmentSummary> learnerAssessmentSummaryList, Map<String, Object> assesmentMap, boolean isContentProgress) {
        ProgressReportsAdapter progressReportsAdapter = new ProgressReportsAdapter(getActivity(), mPresenter);
        progressReportsAdapter.setData(learnerAssessmentSummaryList, assesmentMap, isContentProgress);
        mRecyclerView.setAdapter(progressReportsAdapter);
    }

    @Override
    public void showAverageTime(String averageTime) {
        mAverageTimeTv.setText(averageTime);
    }

    @Override
    public void showScore(String score) {
        mScoreTv.setText(score);
    }

    @Override
    public void showNoProgressReports(int noReportsText) {
        mNoProgressReportTv.setVisibility(View.VISIBLE);
        mNoProgressReportTv.setText(getResources().getString(noReportsText));
    }

    @Override
    public void hideNoProgressReport() {
        mNoProgressReportTv.setVisibility(View.GONE);
    }

    @Override
    public void showProgressReportType(int type) {
        mProgressReportTypeTv.setText(getResources().getString(type));
    }

    @Override
    public void finishActivity() {
        getActivity().finish();
    }

    @Override
    public void showEditProfileScreen(Profile profile) {
        Intent addChildIntent = new Intent(mActivity, AddChildActivity.class);
        addChildIntent.putExtra(Constant.BUNDLE_UPDATED_PROFILE, profile);
        addChildIntent.putExtra(Constant.BUNDLE_EDIT_MODE, true);
        startActivity(addChildIntent);
        getActivity().finish();
    }

    @Override
    public void showDeleteProfileDialog(final Profile profile) {
        mDialog = new Dialog(mActivity);
        mDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        mDialog.setContentView(R.layout.dialog_delete_confirmation);
        mDialog.findViewById(R.id.delete_yes).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialog.dismiss();
                mPresenter.deleteProfile(profile);
            }
        });
        mDialog.findViewById(R.id.delete_no).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialog.dismiss();
            }
        });

        DeviceUtility.displayFullScreenDialog(mDialog, mActivity);
    }

    @Override
    public void showProfileProgressReportDetails(LearnerAssessmentSummary learnerAssessmentSummary, Profile profile, Content content) {
        Intent intent = new Intent(mActivity, ProgressReportDetailActivity.class);
        intent.putExtra(Constant.BundleKey.BUNDLE_KEY_LEARNER_ASSESMENT, learnerAssessmentSummary);
        intent.putExtra(Constant.BundleKey.BUNDLE_KEY_PROFILE_OR_CONTENT_INFO, profile);
        Bundle bundle = new Bundle();
        bundle.putSerializable(Constant.BundleKey.BUNDLE_KEY_CONTENT_HIERARCHY_INFO, (ArrayList<HierarchyInfo>) content.getHierarchyInfo());
        intent.putExtras(bundle);
        mActivity.startActivity(intent);
    }

    @Override
    public void showContentProgressReportDetails(LearnerAssessmentSummary learnerAssessmentSummary, Content content) {
        Intent intent = new Intent(mActivity, ProgressReportDetailActivity.class);
        intent.putExtra(Constant.BundleKey.BUNDLE_KEY_LEARNER_ASSESMENT, learnerAssessmentSummary);
        intent.putExtra(Constant.BundleKey.BUNDLE_KEY_PROFILE_OR_CONTENT_INFO, content);
        mActivity.startActivity(intent);
    }

    @Override
    public void setTagInDeleteLayout(Profile profile) {
        mDeleteTv.setTag(profile);
    }

    @Override
    public void setTagInEditLayout(Profile profile) {
        mEditTv.setTag(profile);
    }

    @Override
    public void onClick(View view) {
        int i = view.getId();
        if (i == R.id.back_btn) {
            mPresenter.handleBackButtonClick();

        } else if (i == R.id.txt_edit) {
            mPresenter.editProfile((Profile) view.getTag());

        } else if (i == R.id.txt_delete) {
            showDeleteProfileDialog((Profile) view.getTag());

        }
    }

}
