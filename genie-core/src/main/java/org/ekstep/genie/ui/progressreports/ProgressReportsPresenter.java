package org.ekstep.genie.ui.progressreports;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;

import org.ekstep.genie.CoreApplication;
import org.ekstep.genie.R;
import org.ekstep.genie.base.BaseView;
import org.ekstep.genie.telemetry.TelemetryAction;
import org.ekstep.genie.telemetry.TelemetryBuilder;
import org.ekstep.genie.telemetry.TelemetryConstant;
import org.ekstep.genie.telemetry.TelemetryHandler;
import org.ekstep.genie.telemetry.TelemetryStageId;
import org.ekstep.genie.telemetry.enums.EntityType;
import org.ekstep.genie.util.Constant;
import org.ekstep.genie.util.geniesdk.ContentUtil;
import org.ekstep.genie.util.preference.PreferenceUtil;
import org.ekstep.genieservices.async.ContentService;
import org.ekstep.genieservices.async.SummarizerService;
import org.ekstep.genieservices.async.UserService;
import org.ekstep.genieservices.commons.IResponseHandler;
import org.ekstep.genieservices.commons.bean.Content;
import org.ekstep.genieservices.commons.bean.ContentDetailsRequest;
import org.ekstep.genieservices.commons.bean.ContentFilterCriteria;
import org.ekstep.genieservices.commons.bean.GenieResponse;
import org.ekstep.genieservices.commons.bean.LearnerAssessmentSummary;
import org.ekstep.genieservices.commons.bean.Profile;
import org.ekstep.genieservices.commons.bean.SummaryRequest;
import org.ekstep.genieservices.commons.bean.enums.InteractionType;
import org.ekstep.genieservices.commons.utils.CollectionUtil;
import org.ekstep.genieservices.commons.utils.DateUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * Created on 12/23/2016.
 *
 * @author anil
 */
public class ProgressReportsPresenter implements ProgressReportsContract.Presenter {

    @NonNull
    private ProgressReportsContract.View mProgressReportView;
    private Context mContext;
    private SummarizerService mSummarizerService;
    private int mClickCount;
    private Map<String, Object> mAssesmentMap = new HashMap<>();
    private List<Profile> mProfileList = new ArrayList<Profile>();
    private Content mContent = null;

    public ProgressReportsPresenter() {

        this.mSummarizerService = CoreApplication.getGenieAsyncService().getSummarizerService();
    }


    @Override
    public void fetchBundleExtras(Bundle bundle) {
        if (bundle.containsKey(Constant.BundleKey.BUNDLE_KEY_CONTENT)) {
            mContent = (Content) bundle.getSerializable(Constant.BundleKey.BUNDLE_KEY_CONTENT);
            mProgressReportView.showTitle(R.string.title_summarizer_content_details);
            mProgressReportView.showName(mContent.getContentData().getName());
            mProgressReportView.hideProfileIcon();
            mProgressReportView.showContentIcon(mContent.getBasePath() + "/" + mContent.getContentData().getAppIcon());

            getContentProgressReport(mContent.getIdentifier());
        } else if (bundle.containsKey(Constant.PROFILE_SUMMARY)) {
            Profile profile = (Profile) bundle.getSerializable(Constant.PROFILE_SUMMARY);
            mProgressReportView.showTitle(R.string.title_summarizer_profile_details);
            mProgressReportView.showName(profile.getHandle());
            mProgressReportView.hideContentIcon();
            mProgressReportView.showProfileIcon(profile.getAvatar(), profile.isGroupUser());
            mProgressReportView.showProfileAction();
            mProgressReportView.setTagInEditLayout(profile);
            mProgressReportView.setTagInDeleteLayout(profile);
            getChildProgressReport(profile.getUid());
        }
    }

    @Override
    public void handleBackButtonClick() {
        mProgressReportView.finishActivity();
    }

    @Override
    public void editProfile(Profile profile) {
        mProgressReportView.showEditProfileScreen(profile);
    }

    @Override
    public void deleteProfile(final Profile profile) {
        if (profile.isGroupUser()) {
            TelemetryHandler.saveTelemetry(TelemetryBuilder.buildGEInteract(InteractionType.TOUCH, TelemetryStageId.SUMMARIZER_CHILD_SUMMARY, TelemetryAction.DELETE_GROUP_INITIATED, profile.getUid()));
        } else {
            TelemetryHandler.saveTelemetry(TelemetryBuilder.buildGEInteract(InteractionType.TOUCH, TelemetryStageId.SUMMARIZER_CHILD_SUMMARY, TelemetryAction.DELETE_CHILD_INITIATED, profile.getUid()));
        }

        UserService userService = CoreApplication.getGenieAsyncService().getUserService();

        userService.deleteUser(profile.getUid(), new IResponseHandler<Void>() {
            @Override
            public void onSuccess(GenieResponse<Void> genieResponse) {
                mProgressReportView.showToast(R.string.msg_children_profile_deletion_successful);
                PreferenceUtil.setAnonymousSessionStatus(true);
                publishRefreshProfileEvent();
                mProgressReportView.finishActivity();
            }

            @Override
            public void onError(GenieResponse<Void> genieResponse) {

            }
        });
    }

    @Override
    public void handleAssesmentItemClick(final LearnerAssessmentSummary learnerAssessmentSummary, final boolean isContentProgress) {
        mClickCount += 1;
        if (mClickCount == 1) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (isContentProgress) {
                        if (mContent != null) {
                            mProgressReportView.showProfileProgressReportDetails(learnerAssessmentSummary, (Profile) mAssesmentMap.get(learnerAssessmentSummary.getUid()), mContent);
                        }
                    } else {
                        mProgressReportView.showContentProgressReportDetails(learnerAssessmentSummary, (Content) mAssesmentMap.get(learnerAssessmentSummary.getContentId()));
                    }

                    mClickCount = 0;
                }
            }, 300);
        }
    }

    @Override
    public void getContentProgressReport(final String contentId) {
        SummaryRequest summaryRequest = new SummaryRequest.Builder().contentId(contentId).build();
        mSummarizerService.getSummary(summaryRequest, new IResponseHandler<List<LearnerAssessmentSummary>>() {
            @Override
            public void onSuccess(GenieResponse<List<LearnerAssessmentSummary>> genieResponse) {
                Map<String, Object> valuesMap = new HashMap<>();
                List<LearnerAssessmentSummary> assessmentSummaryList = genieResponse.getResult();
                valuesMap.put(TelemetryConstant.CHILDREN_WITH_RESULTS, assessmentSummaryList != null ? genieResponse.getResult().size() : 0);
                TelemetryHandler.saveTelemetry(TelemetryBuilder.buildGEInteract(InteractionType.SHOW, TelemetryStageId.SUMMARIZER_CONTENT_SUMMARY, EntityType.CONTENT_ID, contentId, valuesMap));
                renderProgressReport(assessmentSummaryList, true);
            }

            @Override
            public void onError(GenieResponse<List<LearnerAssessmentSummary>> genieResponse) {

            }
        });
    }

    @Override
    public void getChildProgressReport(final String uid) {
        SummaryRequest summaryRequest = new SummaryRequest.Builder().uid(uid).build();
        mSummarizerService.getSummary(summaryRequest, new IResponseHandler<List<LearnerAssessmentSummary>>() {
            @Override
            public void onSuccess(GenieResponse<List<LearnerAssessmentSummary>> genieResponse) {
                Map<String, Object> valuesMap = new HashMap<>();
                List<LearnerAssessmentSummary> assessmentSummaryList = genieResponse.getResult();
                valuesMap.put(TelemetryConstant.CONTENTS_WITH_RESULTS, assessmentSummaryList != null ? genieResponse.getResult().size() : 0);
                TelemetryHandler.saveTelemetry(TelemetryBuilder.buildGEInteract(InteractionType.SHOW, TelemetryStageId.SUMMARIZER_CHILD_SUMMARY, EntityType.CHILD_ID, uid, valuesMap));
                renderProgressReport(genieResponse.getResult(), false);
            }

            @Override
            public void onError(GenieResponse<List<LearnerAssessmentSummary>> genieResponse) {

            }
        });
    }

    @Override
    public void renderProgressReport(List<LearnerAssessmentSummary> learnerAssessmentSummaryList, boolean isContentProgress) {
        if (mProgressReportView == null) {
            return;
        }

        if (learnerAssessmentSummaryList.size() > 0) {
            mProgressReportView.hideNoProgressReport();
            mProgressReportView.showProgressReport();

            if (isContentProgress) {
                mProgressReportView.showProgressReportType(R.string.title_children);
            } else {
                mProgressReportView.showProgressReportType(R.string.label_summarizer_lesson);
            }

            if (isContentProgress) {
                populateProfiles(learnerAssessmentSummaryList, isContentProgress);
            } else {
                populateContents(learnerAssessmentSummaryList, isContentProgress);
            }

            mProgressReportView.showAverageTime(getAverageTime(learnerAssessmentSummaryList));

            String score = getAverageOfCorrectQuestions(learnerAssessmentSummaryList, isContentProgress) + "/" + getMaxQuestionAttended(learnerAssessmentSummaryList, isContentProgress);
            mProgressReportView.showScore(score);

        } else {
            int noReportsText;

            if (isContentProgress) {
                noReportsText = R.string.msg_summarizer_no_content_summary;
            } else {
                noReportsText = R.string.msg_summarizer_no_profile_summary;
            }

            mProgressReportView.hideProgressReport();
            mProgressReportView.showNoProgressReports(noReportsText);
        }
    }

    @Override
    public String getAverageTime(List<LearnerAssessmentSummary> learnerAssessmentSummaryList) {
        double sum = 0;
        for (LearnerAssessmentSummary learnerAssessmentSummary : learnerAssessmentSummaryList) {
            sum += learnerAssessmentSummary.getTotalTimespent();
        }

        return DateUtil.formatSecond((double) sum / learnerAssessmentSummaryList.size());
    }

    @Override
    public String getAverageOfCorrectQuestions(List<LearnerAssessmentSummary> learnerAssessmentSummaryList, boolean isContentProgress) {
        int sum = 0;
        for (LearnerAssessmentSummary learnerAssessmentSummary : learnerAssessmentSummaryList) {
            sum += learnerAssessmentSummary.getCorrectAnswers();
        }
        if (isContentProgress) {
            return String.format(Locale.US, "%.1f", ((double) sum / learnerAssessmentSummaryList.size()));
        } else {
            return String.valueOf(sum);
        }
    }

    @Override
    public String getMaxQuestionAttended(List<LearnerAssessmentSummary> learnerAssessmentSummaryList, boolean isContentProgress) {
        int sum = 0;
        int maxQuesAttended = 0;

        for (LearnerAssessmentSummary learnerAssessmentSummary : learnerAssessmentSummaryList) {
            int questionCount = learnerAssessmentSummary.getNoOfQuestions();
            sum += questionCount;
            if (isContentProgress) {
                if (maxQuesAttended < questionCount) {
                    maxQuesAttended = questionCount;
                }
            }
        }

        if (isContentProgress) {
            return String.valueOf(maxQuesAttended);
        } else {
            return String.valueOf(sum);
        }
    }

    @Override
    public void populateProfiles(final List<LearnerAssessmentSummary> learnerAssessmentSummaryList, final boolean isContentProgress) {
        final UserService userService = CoreApplication.getGenieAsyncService().getUserService();
        userService.getAllUserProfile(new IResponseHandler<List<Profile>>() {
            @Override
            public void onSuccess(GenieResponse<List<Profile>> genieResponse) {
                mProfileList.clear();

                if (genieResponse != null && genieResponse.getResult().size() > 0) {
                    mProfileList = genieResponse.getResult();
                }

                //get the anonymous user
                userService.getAnonymousUser(new IResponseHandler<Profile>() {
                    @Override
                    public void onSuccess(GenieResponse<Profile> genieResponse) {

                        mProfileList.add(genieResponse.getResult());

                        for (Profile profile : mProfileList) {
                            mAssesmentMap.put(profile.getUid(), profile);
                        }

                        if (mAssesmentMap.size() > 0) {
                            mProgressReportView.showAssesmentSummary(learnerAssessmentSummaryList, mAssesmentMap, isContentProgress);
                        } else {
                            mProgressReportView.hideProgressReport();
                            mProgressReportView.showNoProgressReports(R.string.msg_summarizer_no_profile_summary);
                        }
                    }

                    @Override
                    public void onError(GenieResponse<Profile> genieResponse) {

                    }
                });
            }

            @Override
            public void onError(GenieResponse<List<Profile>> genieResponse) {

            }
        });
    }

    @Override
    public void populateContents(final List<LearnerAssessmentSummary> learnerAssessmentSummaryList, final boolean isContentProgress) {
        ContentService contentService = CoreApplication.getGenieAsyncService().getContentService();

        final List<String> allLearnerContentIds = getAllContentIdsFromLearnerSummary(learnerAssessmentSummaryList);

        ContentFilterCriteria.Builder contentFilterCriteria = new ContentFilterCriteria.Builder();
        ContentUtil.applyPartnerFilter(contentFilterCriteria);
        contentService.getAllLocalContent(contentFilterCriteria.build(), new IResponseHandler<List<Content>>() {
            @Override
            public void onSuccess(GenieResponse<List<Content>> genieResponse) {
                List<Content> contentList = genieResponse.getResult();
//                for (Content content : contentList) {
//                    if (allLearnerContentIds != null && allLearnerContentIds.size() > 0 && allLearnerContentIds.contains(content.getIdentifier())) {
//                        mAssesmentMap.put(content.getIdentifier(), content);
//                        allLearnerContentIds.remove()
//                    }
//                }

                for (int i = 0; i < contentList.size(); i++) {
                    if (!CollectionUtil.isNullOrEmpty(allLearnerContentIds) && !allLearnerContentIds.contains(contentList.get(i).getIdentifier())) {
                        mAssesmentMap.put(contentList.get(i).getIdentifier(), contentList.get(i));
                        allLearnerContentIds.remove(contentList.get(i).getIdentifier());
                    }
                }

                if (allLearnerContentIds.size() > 0) {
                    for (String id : allLearnerContentIds) {
                        mAssesmentMap.put(id, getContentDetails(id));
                    }
                }

//                Set<String> keySet = mAssesmentMap.keySet();
//                List<LearnerAssessmentSummary> learnerAssessmentSummaryListNew = learnerAssessmentSummaryList;
//                for (int i = 0; i < learnerAssessmentSummaryList.size(); i++) {
//                    LearnerAssessmentSummary learnerAssessmentSummary = learnerAssessmentSummaryList.get(i);
//                    if (!keySet.contains(learnerAssessmentSummary.getContentId())) {
//                        learnerAssessmentSummaryListNew.remove(i);
//                    }
//                }

                if (mAssesmentMap.size() > 0) {
                    mProgressReportView.showAssesmentSummary(learnerAssessmentSummaryList, mAssesmentMap, isContentProgress);
                } else {
                    mProgressReportView.hideProgressReport();
                    mProgressReportView.showNoProgressReports(R.string.msg_summarizer_no_content_summary);
                }
            }

            @Override
            public void onError(GenieResponse<List<Content>> genieResponse) {

            }
        });

    }

    private Content getContentDetails(String identifier) {
        ContentDetailsRequest.Builder detailsRequest = new ContentDetailsRequest.Builder().forContent(identifier);

        return CoreApplication.getGenieSdkInstance().getContentService().getContentDetails(detailsRequest.build()).getResult();

    }

    @Override
    public List<String> getAllContentIdsFromLearnerSummary(List<LearnerAssessmentSummary> learnerAssessmentSummaryList) {
        List<String> allContentIds = new ArrayList<>();
        for (LearnerAssessmentSummary learnerAssessmentSummary : learnerAssessmentSummaryList) {
            allContentIds.add(learnerAssessmentSummary.getContentId());
        }
        return allContentIds;
    }

    @Override
    public void publishRefreshProfileEvent() {
        ContentUtil.publishRefreshProfileEvent();
    }

    @Override
    public void bindView(BaseView view, Context context) {
        mProgressReportView = (ProgressReportsContract.View) view;
        mContext = context;
    }

    @Override
    public void unbindView() {
        mProgressReportView = null;
        mContext = null;
    }
}
