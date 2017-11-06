package org.ekstep.genie.ui.progressreports;

import android.os.Bundle;

import org.ekstep.genie.base.BasePresenter;
import org.ekstep.genie.base.BaseView;
import org.ekstep.genieservices.commons.bean.Content;
import org.ekstep.genieservices.commons.bean.LearnerAssessmentSummary;
import org.ekstep.genieservices.commons.bean.Profile;

import java.util.List;
import java.util.Map;

/**
 * Created on 12/23/2016.
 *
 * @author anil
 */
public interface ProgressReportsContract {

    interface View extends BaseView {

        void showToast(int resId);

        void showTitle(int title);

        void showName(String name);

        void showContentIcon(String localPath);

        void hideContentIcon();

        void showProfileIcon(String avatar, boolean isGroup);

        void hideProfileIcon();

        void showProfileAction();

        void showProgressReport();

        void hideProgressReport();

        void showAssesmentSummary(List<LearnerAssessmentSummary> summarizerItemList, Map<String, Object> assesmentMap, boolean isContentProgress);

        void showAverageTime(String averageTime);

        void showScore(String score);

        void showNoProgressReports(int noReportsText);

        void hideNoProgressReport();

        void showProgressReportType(int type);

        void finishActivity();

        void showEditProfileScreen(Profile profile);

        void showDeleteProfileDialog(Profile profile);

        void showProfileProgressReportDetails(LearnerAssessmentSummary learnerAssessmentSummary, Profile profile, Content content);

        void showContentProgressReportDetails(LearnerAssessmentSummary learnerAssessmentSummary, Content content);

        void setTagInDeleteLayout(Profile profile);

        void setTagInEditLayout(Profile profile);
    }

    interface Presenter extends BasePresenter {

        void fetchBundleExtras(Bundle arguments);

        void handleBackButtonClick();

        void editProfile(Profile profile);

        void deleteProfile(Profile profile);

        void handleAssesmentItemClick(LearnerAssessmentSummary learnerAssessmentSummary, boolean isContentProgress);

        void getContentProgressReport(String contentId);

        void getChildProgressReport(String uid);

        void renderProgressReport(List<LearnerAssessmentSummary> learnerAssessmentSummaryList, boolean isContentProgress);

        String getAverageTime(List<LearnerAssessmentSummary> learnerAssessmentSummaryList);

        String getAverageOfCorrectQuestions(List<LearnerAssessmentSummary> learnerAssessmentSummaryList, boolean isContentProgress);

        String getMaxQuestionAttended(List<LearnerAssessmentSummary> learnerAssessmentSummaryList, boolean isContentProgress);

        void populateProfiles(final List<LearnerAssessmentSummary> learnerAssessmentSummaryList, final boolean isContentProgress);

        void populateContents(final List<LearnerAssessmentSummary> learnerAssessmentSummaryList, final boolean isContentProgress);

        List<String> getAllContentIdsFromLearnerSummary(List<LearnerAssessmentSummary> learnerAssessmentSummaryList);

        void publishRefreshProfileEvent();
    }

}
