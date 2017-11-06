package org.ekstep.genie.ui.progressreportdetail;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.ekstep.genie.R;
import org.ekstep.genieservices.commons.bean.LearnerAssessmentDetails;
import org.ekstep.genieservices.commons.utils.DateUtil;
import org.ekstep.genieservices.commons.utils.StringUtil;

import java.util.List;

/**
 * Created on 12/23/2016.
 *
 * @author anil
 */
public class ProgressReportDetailAdapter extends RecyclerView.Adapter<ProgressReportDetailAdapter.ViewHolder> {

    private Context mContext = null;
    private List<LearnerAssessmentDetails> mLearnerAssessmentDetailsList = null;

    public ProgressReportDetailAdapter(Context context) {
        this.mContext = context;
    }

    public void setData(List<LearnerAssessmentDetails> summarizerItemList) {
        this.mLearnerAssessmentDetailsList = summarizerItemList;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemLayoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_summerizer_details_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(itemLayoutView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, int position) {
        LearnerAssessmentDetails learnerAssessmentDetails = mLearnerAssessmentDetailsList.get(position);
        if (!StringUtil.isNullOrEmpty(learnerAssessmentDetails.getQdesc())) {
            viewHolder.vhtvQuestionDescription.setText(learnerAssessmentDetails.getQdesc());
        }
        viewHolder.vhtvQuestionNo.setText(String.valueOf(Double.valueOf(learnerAssessmentDetails.getQindex()).intValue()));
        viewHolder.vhtvTimeTaken.setText(DateUtil.formatSecond(Double.valueOf(learnerAssessmentDetails.getTimespent().toString())));
        GradientDrawable shape = new GradientDrawable();
        shape.setCornerRadius(5);
        double correct = Double.valueOf(learnerAssessmentDetails.getScore());

        int score = (int) correct;
        if (score >= 1) {
            shape.setColor(mContext.getResources().getColor(R.color.color_primary));
        } else {
            shape.setColor(mContext.getResources().getColor(R.color.red_1));
        }

        viewHolder.vhtvScore.setBackground(shape);
        viewHolder.vhtvScore.setText(String.valueOf(score));
    }

    public LearnerAssessmentDetails getItem(int index) {
        return mLearnerAssessmentDetailsList.get(index);
    }

    @Override
    public int getItemCount() {
        return mLearnerAssessmentDetailsList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView vhtvTimeTaken;
        private TextView vhtvScore;
        private TextView vhtvQuestionNo;
        private TextView vhtvQuestionDescription;
        private View vhView;

        public ViewHolder(View itemLayoutView) {
            super(itemLayoutView);
            vhtvTimeTaken = (TextView) itemLayoutView.findViewById(R.id.txt_time_taken);
            vhtvScore = (TextView) itemLayoutView.findViewById(R.id.txt_score);
            vhtvQuestionNo = (TextView) itemLayoutView.findViewById(R.id.txt_question_no);
            vhtvQuestionDescription = (TextView) itemLayoutView.findViewById(R.id.txt_short_description);
            vhView = itemLayoutView;
        }
    }

}
