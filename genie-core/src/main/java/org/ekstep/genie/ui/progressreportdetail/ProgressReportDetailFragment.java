package org.ekstep.genie.ui.progressreportdetail;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import org.ekstep.genie.R;
import org.ekstep.genie.base.BasePresenter;
import org.ekstep.genie.base.BaseView;
import org.ekstep.genie.base.IPresenterFactory;
import org.ekstep.genie.fragment.BaseFragment;
import org.ekstep.genie.util.GlideImageUtil;
import org.ekstep.genie.util.geniesdk.UserProfileUtil;
import org.ekstep.genieservices.commons.bean.LearnerAssessmentDetails;

import java.util.List;

/**
 * Created on 12/23/2016.
 *
 * @author anil
 */
public class ProgressReportDetailFragment extends BaseFragment
        implements ProgressReportDetailContract.View, View.OnClickListener {

    private ProgressReportDetailContract.Presenter mPresenter;

    private RecyclerView mRecyclerView;
    private TextView mTitleTv;
    private TextView mNameTv;
    private TextView mTotalTimeTv;
    private TextView mScoreTv;

    private ImageView mContentIconIv;
    private ImageView mAvatarIconIv;

    private TextView mNoProgressReportTv;
    private View mProgressReportView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = (ProgressReportDetailPresenter)presenter;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_progress_report_detail, container, false);
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
                return new ProgressReportDetailPresenter();
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
        mTitleTv = (TextView) view.findViewById(R.id.txt_header);
        mNameTv = (TextView) view.findViewById(R.id.txt_content_name);
        mTotalTimeTv = (TextView) view.findViewById(R.id.txt_total_time);
        mScoreTv = (TextView) view.findViewById(R.id.txt_score);

        mContentIconIv = (ImageView) view.findViewById(R.id.img_content);
        mAvatarIconIv = (ImageView) view.findViewById(R.id.img_avatar);

        mNoProgressReportTv = (TextView) view.findViewById(R.id.txt_no_progress_report);
        mProgressReportView = view.findViewById(R.id.layout_progres_report);


        mRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_summerizer_details);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setFocusable(false);

        view.findViewById(R.id.back_btn).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int i = view.getId();
        if (i == R.id.back_btn) {
            mPresenter.handleBackButtonClick();

        }
    }

    @Override
    public void finishActivity() {
        getActivity().finish();
    }

    @Override
    public void showTitle(String name) {
        mTitleTv.setText(name);
    }

    @Override
    public void showName(String name) {
        mNameTv.setText(name);
    }

    @Override
    public void showTotalTIme(String totalTime) {
        mTotalTimeTv.setText(totalTime);
    }

    @Override
    public void showScore(String score) {
        mScoreTv.setText(score);
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
    public void showAvatarIcon(String icon, boolean isGroupUser) {
        mAvatarIconIv.setVisibility(View.VISIBLE);

        UserProfileUtil.setProfileAvatar(mAvatarIconIv, icon, isGroupUser);
    }

    @Override
    public void hideAvatarIcon() {
        mAvatarIconIv.setVisibility(View.GONE);
    }

    @Override
    public void showNoProgressReport(int resId) {
        mNoProgressReportTv.setText(resId);

        mNoProgressReportTv.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideNoProgressReport() {
        mNoProgressReportTv.setVisibility(View.GONE);
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
    public void showLearnerAssesments(List<LearnerAssessmentDetails> learnerAssessmentDetailsList) {
        ProgressReportDetailAdapter progressReportDetailAdapter = new ProgressReportDetailAdapter(mActivity);
        progressReportDetailAdapter.setData(learnerAssessmentDetailsList);
        mRecyclerView.setAdapter(progressReportDetailAdapter);
    }

}
