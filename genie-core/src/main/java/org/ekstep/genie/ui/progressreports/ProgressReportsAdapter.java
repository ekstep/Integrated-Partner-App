package org.ekstep.genie.ui.progressreports;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import org.ekstep.genie.R;
import org.ekstep.genie.util.GlideImageUtil;
import org.ekstep.genie.util.geniesdk.UserProfileUtil;
import org.ekstep.genieservices.commons.bean.Content;
import org.ekstep.genieservices.commons.bean.LearnerAssessmentSummary;
import org.ekstep.genieservices.commons.bean.Profile;
import org.ekstep.genieservices.commons.utils.DateUtil;
import org.ekstep.genieservices.commons.utils.StringUtil;

import java.util.List;
import java.util.Map;

/**
 * Created on 7/8/2016.
 *
 * @author swayangjit_gwl
 */
public class ProgressReportsAdapter extends RecyclerView.Adapter<ProgressReportsAdapter.ViewHolder> {

    private Context mContext = null;
    private List<LearnerAssessmentSummary> mLearnerAssessmentSummaryList = null;
    private boolean mIsContentProgress = false;
    private Map<String, Object> mAssesmentMap = null;
    private ProgressReportsContract.Presenter mPresenter = null;

    public ProgressReportsAdapter(Context context, ProgressReportsContract.Presenter presenter) {
        this.mContext = context;
        this.mPresenter = presenter;
    }

    public void setData(List<LearnerAssessmentSummary> learnerAssessmentSummaryList, Map<String, Object> assesmentMap, boolean isContentProgress) {
        this.mLearnerAssessmentSummaryList = learnerAssessmentSummaryList;
        this.mAssesmentMap = assesmentMap;
        this.mIsContentProgress = isContentProgress;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemLayoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_progress_report_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(itemLayoutView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, int position) {
        LearnerAssessmentSummary learnerAssessmentSummary = mLearnerAssessmentSummaryList.get(position);

        if (mIsContentProgress) {
            viewHolder.vhImgContent.setVisibility(View.GONE);
            viewHolder.vhImgProfile.setVisibility(View.VISIBLE);
            Profile profile = (Profile) mAssesmentMap.get(learnerAssessmentSummary.getUid());
            if (profile != null && !StringUtil.isNullOrEmpty(profile.getAvatar())) {
                UserProfileUtil.setProfileAvatar(viewHolder.vhImgProfile, profile.getAvatar(), profile.isGroupUser());
            } else {
                viewHolder.vhImgProfile.setImageResource(R.drawable.avatar_anonymous);
            }

            if (profile != null) {
                if (StringUtil.isNullOrEmpty(profile.getHandle())) {
                    viewHolder.vhtvName.setText("Anonymous");
                } else {
                    viewHolder.vhtvName.setText(profile.getHandle());
                }
            } else {
                viewHolder.vhtvName.setText("Anonymous");
            }


        } else {
            Content content = (Content) mAssesmentMap.get(learnerAssessmentSummary.getContentId());
            viewHolder.vhImgContent.setVisibility(View.VISIBLE);
            viewHolder.vhImgProfile.setVisibility(View.GONE);
            if (content != null) {
                GlideImageUtil.loadImageUrl(mContext, content.getBasePath() + "/" + content.getContentData().getAppIcon(), viewHolder.vhImgContent);
                viewHolder.vhtvName.setText(content.getContentData().getName());
            }
        }


        viewHolder.vhtvTimeTaken.setText(DateUtil.formatSecond(learnerAssessmentSummary.getTotalTimespent()));
        viewHolder.vhtvScore.setText(learnerAssessmentSummary.getCorrectAnswers() + "/" + learnerAssessmentSummary.getNoOfQuestions());

        if (position > 9) {
            viewHolder.vhtvCount.setText(String.valueOf((position + 1)));
        } else {
            viewHolder.vhtvCount.setText("0" + String.valueOf((position + 1)));
        }

        viewHolder.vhView.setTag(learnerAssessmentSummary);
    }

    public LearnerAssessmentSummary getItem(int index) {
        return mLearnerAssessmentSummaryList.get(index);
    }

    @Override
    public int getItemCount() {
        return mLearnerAssessmentSummaryList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public View vhView;
        private ImageView vhImgContent;
        private ImageView vhImgProfile;
        private TextView vhtvTimeTaken;
        private TextView vhtvScore;
        private TextView vhtvCount;
        private TextView vhtvName;
        private int clickCount = 0;

        public ViewHolder(View itemLayoutView) {
            super(itemLayoutView);
            vhtvTimeTaken = (TextView) itemLayoutView.findViewById(R.id.txt_time_taken);
            vhtvScore = (TextView) itemLayoutView.findViewById(R.id.txt_score);
            vhImgContent = (ImageView) itemLayoutView.findViewById(R.id.img_content);
            vhImgProfile = (ImageView) itemLayoutView.findViewById(R.id.img_avatar);
            vhtvCount = (TextView) itemLayoutView.findViewById(R.id.txt_count);
            vhtvName = (TextView) itemLayoutView.findViewById(R.id.txt_name);
            vhView = itemLayoutView;


            vhView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View v) {
                    LearnerAssessmentSummary learnerAssessmentSummary = (LearnerAssessmentSummary) v.getTag();
                    mPresenter.handleAssesmentItemClick(learnerAssessmentSummary, mIsContentProgress);
                }
            });
        }
    }

}
