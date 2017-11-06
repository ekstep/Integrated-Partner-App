package org.ekstep.genie.customview;

import android.app.Activity;
import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import org.ekstep.genie.R;
import org.ekstep.genie.util.ThemeUtility;

/**
 * Created by swayangjit on 26/1/17.
 */

public class EkstepStepperProgress {
    private Context mContext;
    private EkStepCustomTextView mTxt_Step_1;
    private EkStepCustomTextView mTxt_Step_2;
    private EkStepCustomTextView mTxt_Step_3;
    private EkStepCustomTextView mTxt_Step_4;
    private ImageView mImg_Tick1;
    private ImageView mImg_Tick2;
    private ImageView mImg_Tick3;
    private RelativeLayout mLayout_Stepper_Progress;


    public EkstepStepperProgress(Context context) {
        mContext = context;
        init();
    }

    public void init() {
        Activity activity = (Activity) mContext;
        mTxt_Step_1 = (EkStepCustomTextView) activity.findViewById(R.id.txt_step_1);
        mTxt_Step_2 = (EkStepCustomTextView) activity.findViewById(R.id.txt_step_2);
        mTxt_Step_3 = (EkStepCustomTextView) activity.findViewById(R.id.txt_step_3);
        mTxt_Step_4 = (EkStepCustomTextView) activity.findViewById(R.id.txt_step_4);
        mImg_Tick1 = (ImageView) activity.findViewById(R.id.img_tick_1);
        mImg_Tick2 = (ImageView) activity.findViewById(R.id.img_tick_2);
        mImg_Tick3 = (ImageView) activity.findViewById(R.id.img_tick_3);
        mLayout_Stepper_Progress = (RelativeLayout) activity.findViewById(R.id.layout_progress);

    }

    public void setMaxStateNumber(int number) {
        if (number == 2) {
            manageVisibility(View.GONE);
        } else {
            manageVisibility(View.VISIBLE);
        }
    }

    public void setVisibility(int visibility) {
        mLayout_Stepper_Progress.setVisibility(visibility);
    }

    private void manageVisibility(int visibility) {
        Activity activity = (Activity) mContext;
        activity.findViewById(R.id.layout_step_3).setVisibility(visibility);
        mTxt_Step_4.setVisibility(visibility);
        activity.findViewById(R.id.img_connector2).setVisibility(visibility);
        activity.findViewById(R.id.img_connector3).setVisibility(visibility);
    }

    public void setCurrentStateNumber(int currentStateNumber) {
        switch (currentStateNumber) {
            case 1:
                mTxt_Step_2.setBackground(ThemeUtility.getDrawable(mContext, new int[]{R.attr.addChildStepperBackgroundUnfilled}));
                mTxt_Step_2.setText("2");
                mTxt_Step_2.setTextColor(ThemeUtility.getColor(mContext, new int[]{R.attr.addChildStepperTextColorUnfilled}));

                mTxt_Step_1.setText("1");
                mImg_Tick1.setVisibility(View.GONE);

                break;
            case 2:
                mTxt_Step_3.setBackground(ThemeUtility.getDrawable(mContext, new int[]{R.attr.addChildStepperBackgroundUnfilled}));
                mTxt_Step_3.setTextColor(ThemeUtility.getColor(mContext, new int[]{R.attr.addChildStepperTextColorUnfilled}));

                mTxt_Step_2.setBackground(ThemeUtility.getDrawable(mContext, new int[]{R.attr.addChildStepperBackgroundFilled}));
                mTxt_Step_2.setText("2");
                mTxt_Step_2.setTextColor(ThemeUtility.getColor(mContext, new int[]{R.attr.addChildStepperTextColorFilled}));
                mImg_Tick2.setVisibility(View.GONE);

                mTxt_Step_1.setText("");
                mImg_Tick1.setVisibility(View.VISIBLE);

                break;
            case 3:
                mTxt_Step_4.setBackground(ThemeUtility.getDrawable(mContext, new int[]{R.attr.addChildStepperBackgroundUnfilled}));
                mTxt_Step_4.setTextColor(ThemeUtility.getColor(mContext, new int[]{R.attr.addChildStepperTextColorUnfilled}));

                mTxt_Step_3.setBackground(ThemeUtility.getDrawable(mContext, new int[]{R.attr.addChildStepperBackgroundFilled}));
                mTxt_Step_3.setTextColor(ThemeUtility.getColor(mContext, new int[]{R.attr.addChildStepperTextColorFilled}));
                mImg_Tick3.setVisibility(View.GONE);
                mTxt_Step_3.setText("3");

                mTxt_Step_2.setText("");
                mImg_Tick2.setVisibility(View.VISIBLE);

                break;
            case 4:
                mTxt_Step_4.setBackground(ThemeUtility.getDrawable(mContext, new int[]{R.attr.addChildStepperBackgroundFilled}));
                mTxt_Step_4.setTextColor(ThemeUtility.getColor(mContext, new int[]{R.attr.addChildStepperTextColorFilled}));
                mTxt_Step_4.setText("4");

                mTxt_Step_3.setText("");
                mImg_Tick3.setVisibility(View.VISIBLE);

                break;
        }
    }
}
