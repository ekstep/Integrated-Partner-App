package org.ekstep.genie.ui.progressreportdetail;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;

import org.ekstep.genie.CoreApplication;
import org.ekstep.genie.R;
import org.ekstep.genie.base.BaseView;
import org.ekstep.genie.telemetry.TelemetryBuilder;
import org.ekstep.genie.telemetry.TelemetryHandler;
import org.ekstep.genie.telemetry.TelemetryStageId;
import org.ekstep.genie.telemetry.enums.EntityType;
import org.ekstep.genie.util.Constant;
import org.ekstep.genieservices.async.SummarizerService;
import org.ekstep.genieservices.commons.IResponseHandler;
import org.ekstep.genieservices.commons.bean.Content;
import org.ekstep.genieservices.commons.bean.GenieResponse;
import org.ekstep.genieservices.commons.bean.HierarchyInfo;
import org.ekstep.genieservices.commons.bean.LearnerAssessmentDetails;
import org.ekstep.genieservices.commons.bean.LearnerAssessmentSummary;
import org.ekstep.genieservices.commons.bean.Profile;
import org.ekstep.genieservices.commons.bean.SummaryRequest;
import org.ekstep.genieservices.commons.bean.enums.InteractionType;
import org.ekstep.genieservices.commons.utils.DateUtil;

import java.util.List;

/**
 * Created on 12/23/2016.
 *
 * @author anil
 */
public class ProgressReportDetailPresenter implements ProgressReportDetailContract.Presenter {

    private ProgressReportDetailContract.View mProgressReportDetailView;
    private Activity mActivity;
    private SummarizerService mSummarizerService = null;

    public ProgressReportDetailPresenter() {
        mSummarizerService = CoreApplication.getGenieAsyncService().getSummarizerService();
    }

    private String createHierarchyData(List<HierarchyInfo> hierarchyInfo) {
        String id = "";
        String identifierType = null;
        for (HierarchyInfo infoItem : hierarchyInfo) {
            if (identifierType == null) {
                identifierType = infoItem.getContentType();
            }
            id += id.length() == 0 ? "" : "/";
            id += infoItem.getIdentifier();
        }
        return id;
    }


    @Override
    public void handleBackButtonClick() {
        mProgressReportDetailView.finishActivity();
    }

    @Override
    public void fetchBundleExtras(Bundle bundle) {
        if (bundle != null) {
            Object object = bundle.getSerializable(Constant.BundleKey.BUNDLE_KEY_PROFILE_OR_CONTENT_INFO);
            Object hierarchyInfoObject = bundle.getSerializable(Constant.BundleKey.BUNDLE_KEY_CONTENT_HIERARCHY_INFO);

            LearnerAssessmentSummary learnerAssessmentSummary = (LearnerAssessmentSummary) bundle.getSerializable(Constant.BundleKey.BUNDLE_KEY_LEARNER_ASSESMENT);
            boolean isContentProgress = false;

            mProgressReportDetailView.showTotalTIme(DateUtil.formatSecond(learnerAssessmentSummary.getTotalTimespent()));
            mProgressReportDetailView.showScore(learnerAssessmentSummary.getCorrectAnswers() + "/" + learnerAssessmentSummary.getNoOfQuestions());

            if (object instanceof Profile) {
                isContentProgress = true;
                Profile profile = (Profile) object;
                renderProfileInfo(profile);
            } else {
                Content content = (Content) object;
                renderContentInfo(content);
            }

            getReport(learnerAssessmentSummary.getUid(), learnerAssessmentSummary.getContentId(), isContentProgress, learnerAssessmentSummary.getHierarchyData());

        }
    }

    private void renderProfileInfo(Profile profile) {
        String name;

        if (TextUtils.isEmpty(profile.getHandle())) {
            name = mActivity.getResources().getString(R.string.label_all_anonymous);
        } else {
            name = profile.getHandle();
        }
        mProgressReportDetailView.showTitle(name);
        mProgressReportDetailView.showName(name);
        mProgressReportDetailView.hideContentIcon();
        mProgressReportDetailView.showAvatarIcon(profile.getAvatar(), profile.isGroupUser());
    }

    private void renderContentInfo(Content content) {
        mProgressReportDetailView.showTitle(content.getContentData().getName());
        mProgressReportDetailView.showName(content.getContentData().getName());
        mProgressReportDetailView.showContentIcon(content.getBasePath() + "/" + content.getContentData().getAppIcon());
        mProgressReportDetailView.hideAvatarIcon();
    }

    private void getReport(final String uid, final String contentId, final boolean isContentProgress, String hData) {
        SummaryRequest summaryRequest = new SummaryRequest.Builder().contentId(contentId).uid(uid).hierarchyData(hData).build();
        mSummarizerService.getLearnerAssessmentDetails(summaryRequest, new IResponseHandler<List<LearnerAssessmentDetails>>() {
            @Override
            public void onSuccess(GenieResponse<List<LearnerAssessmentDetails>> genieResponse) {
                List<LearnerAssessmentDetails> assesmentDetailsList = genieResponse.getResult();
                if (assesmentDetailsList != null && assesmentDetailsList.size() > 0) {
                    mProgressReportDetailView.hideNoProgressReport();
                    mProgressReportDetailView.showProgressReport();
                    mProgressReportDetailView.showLearnerAssesments(assesmentDetailsList);
                    if (isContentProgress) {
                        TelemetryHandler.saveTelemetry(TelemetryBuilder.buildGEInteract(InteractionType.SHOW, TelemetryStageId.SUMMARIZER_CHILD_CONTENT_DETAILS, EntityType.CONTENT_ID, uid));
                    } else {
                        TelemetryHandler.saveTelemetry(TelemetryBuilder.buildGEInteract(InteractionType.SHOW, TelemetryStageId.SUMMARIZER_CHILD_CONTENT_DETAILS, EntityType.CONTENT_ID, contentId));
                    }
                } else {
                    if (isContentProgress) {
                        mProgressReportDetailView.showNoProgressReport(R.string.msg_summarizer_no_content_summary);
                    } else {
                        mProgressReportDetailView.showNoProgressReport(R.string.msg_summarizer_no_profile_summary);
                    }

                    mProgressReportDetailView.hideProgressReport();
                }
            }

            @Override
            public void onError(GenieResponse<List<LearnerAssessmentDetails>> genieResponse) {

            }
        });
    }

    @Override
    public void bindView(BaseView view, Context context) {
        mProgressReportDetailView = (ProgressReportDetailContract.View) view;
        mActivity = (Activity) context;
    }

    @Override
    public void unbindView() {
        mProgressReportDetailView = null;
        mActivity = null;
    }
}
