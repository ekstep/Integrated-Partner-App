package org.ekstep.genie.ui.progressreportdetail;

import android.os.Bundle;

import org.ekstep.genie.base.BasePresenter;
import org.ekstep.genie.base.BaseView;
import org.ekstep.genieservices.commons.bean.LearnerAssessmentDetails;

import java.util.List;

/**
 * Created on 12/23/2016.
 *
 * @author anil
 */
public interface ProgressReportDetailContract {

    interface View extends BaseView {

        void finishActivity();

        void showTitle(String name);

        void showName(String name);

        void showTotalTIme(String totalTime);

        void showScore(String score);

        void showContentIcon(String url);

        void hideContentIcon();

        void showAvatarIcon(String icon, boolean isGroupUser);

        void hideAvatarIcon();

        void showNoProgressReport(int resId);

        void hideNoProgressReport();

        void showProgressReport();

        void hideProgressReport();

        void showLearnerAssesments(List<LearnerAssessmentDetails> learnerAssessmentDetailsList);
    }

    interface Presenter extends BasePresenter {

        void fetchBundleExtras(Bundle arguments);

        void handleBackButtonClick();
    }

}
