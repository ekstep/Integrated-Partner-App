package org.ekstep.genie.ui.addchild.agegendernclass;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.ekstep.genie.BuildConfig;
import org.ekstep.genie.R;
import org.ekstep.genie.adapters.PopUpWindowAdapter;
import org.ekstep.genie.base.BasePresenter;
import org.ekstep.genie.base.BaseView;
import org.ekstep.genie.base.IPresenterFactory;
import org.ekstep.genie.callback.IMenuItemClick;
import org.ekstep.genie.fragment.BaseFragment;
import org.ekstep.genie.ui.addchild.AddChildActivity;
import org.ekstep.genie.util.Constant;
import org.ekstep.genie.util.DeviceUtility;
import org.ekstep.genie.util.PopUpWindowUtil;
import org.ekstep.genie.util.ThemeUtility;
import org.ekstep.genieservices.commons.bean.Profile;

import java.util.List;

/**
 * Created by swayangjit on 22/12/16.
 */

public class AgeGenderClassFragment extends BaseFragment
        implements AgeGendernClassContract.View, View.OnClickListener {
    private static final String TAG = AgeGenderClassFragment.class.getName();

    private AgeGendernClassContract.Presenter mAgePresenter;

    private TextView mMonthView = null;
    private TextView mDayOfMonthView = null;
    private TextView mTxt_Age = null;
    //    private TextView mTxt_BirthDay = null;
    private TextView mTxt_Class = null;

    // Gender
    private ImageView mGenderBoyImg = null;
    private ImageView mGenderGirlImg = null;

    private RelativeLayout mGenderBoyLayout;
    private RelativeLayout mGenderGirlLayout;

    private View mLayout_Transparent_Bg = null;

    private RelativeLayout mRelativeLayoutAge;
    private RelativeLayout mRelativeLayoutClass;

    public static AgeGenderClassFragment newInstance(Profile profile, boolean isEditMode) {
        AgeGenderClassFragment fragment = new AgeGenderClassFragment();
        Bundle args = new Bundle();
        args.putSerializable(Constant.BUNDLE_UPDATED_PROFILE, profile);
        args.putBoolean(Constant.BUNDLE_EDIT_MODE, isEditMode);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAgePresenter = (AgeGendernClassPresenter)presenter;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_age_gender_class, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews(view);
        mAgePresenter.fetchBundleExtras(getArguments());
        mAgePresenter.openAgeLayout();
    }

    @Override
    protected IPresenterFactory getPresenterFactory() {
        return new IPresenterFactory() {
            @Override
            public BasePresenter create() {
                return new AgeGendernClassPresenter();
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

        mTxt_Age = (TextView) view.findViewById(R.id.txt_age);

        mTxt_Class = (TextView) view.findViewById(R.id.txt_class);

        mGenderBoyImg = (ImageView) view.findViewById(R.id.gender_boy_btn);
        mGenderBoyImg.setOnClickListener(this);

        mGenderGirlImg = (ImageView) view.findViewById(R.id.gender_girl_btn);
        mGenderGirlImg.setOnClickListener(this);

        mGenderBoyLayout = (RelativeLayout) view.findViewById(R.id.gender_boy_layout);
        mGenderGirlLayout = (RelativeLayout) view.findViewById(R.id.gender_girl_layout);

        mLayout_Transparent_Bg = getActivity().findViewById(R.id.layout_transparent_bg);
        mLayout_Transparent_Bg.setOnClickListener(this);

        mRelativeLayoutAge = (RelativeLayout) getActivity().findViewById(R.id.rl_age);
        mRelativeLayoutAge.setOnClickListener(this);

        mRelativeLayoutClass = (RelativeLayout) getActivity().findViewById(R.id.rl_class);
        mRelativeLayoutClass.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.rl_age) {
            mLayout_Transparent_Bg.setVisibility(View.VISIBLE);
            mAgePresenter.handleAgeClick(v);

        } else if (i == R.id.rl_class) {
            mLayout_Transparent_Bg.setVisibility(View.VISIBLE);
            mAgePresenter.handleClassClick(v);

        } else if (i == R.id.gender_boy_btn) {
            mAgePresenter.toggleGender(AgeGendernClassPresenter.GENDER_MALE);

        } else if (i == R.id.gender_girl_btn) {
            mAgePresenter.toggleGender(AgeGendernClassPresenter.GENDER_FEMALE);

        }
    }

    @Override
    public void showAgeListPopup(List<String> mediumList, View view) {
        PopUpWindowUtil.PopupWindowArgBuilder popupWindowArgBuilder = new PopUpWindowUtil.PopupWindowArgBuilder().
                setAnchor(mRelativeLayoutAge).
                setContext(getActivity()).
                setAdapter(new PopUpWindowAdapter(mediumList, new IMenuItemClick() {
                    @Override
                    public void onMenuItemClick(int position) {
                        DeviceUtility.displayFullScreen(getActivity());
                        mLayout_Transparent_Bg.setVisibility(View.GONE);
                        PopUpWindowUtil.dismissPopUpWindow();
                        mAgePresenter.handleAgeItemClick(position);

                    }
                })).
                setDrawable(R.drawable.layer_dialog_with_border).
                setId(R.id.recycler_view_age).
                setTransparentView(mLayout_Transparent_Bg);
        PopUpWindowUtil.showPopUpWindow(popupWindowArgBuilder);
    }

    @Override
    public String getAge() {
        return mTxt_Age.getText().toString();
    }

    @Override
    public void setAge(String age) {
        mTxt_Age.setText(age);
    }

    @Override
    public String getDay() {
        return mDayOfMonthView.getText().toString();
    }

    @Override
    public String getMonth() {
        return mMonthView.getText().toString();
    }

    @Override
    public void showClassListPopup(List<String> classList, View view) {
        PopUpWindowUtil.PopupWindowArgBuilder popupWindowArgBuilder = new PopUpWindowUtil.PopupWindowArgBuilder().
                setAnchor(mRelativeLayoutClass).
                setContext(getActivity()).
                setAdapter(new PopUpWindowAdapter(classList, new IMenuItemClick() {
                    @Override
                    public void onMenuItemClick(int position) {
                        DeviceUtility.displayFullScreen(getActivity());
                        mLayout_Transparent_Bg.setVisibility(View.GONE);
                        PopUpWindowUtil.dismissPopUpWindow();
                        mAgePresenter.handleClassItemClick(position);
                    }
                })).
                setDrawable(R.drawable.layer_dialog_with_border).
                setId(R.id.recycler_view_class).
                setTransparentView(mLayout_Transparent_Bg);
        PopUpWindowUtil.showPopUpWindow(popupWindowArgBuilder);
    }

    @Override
    public String getStandard() {
        return mTxt_Class.getText().toString();
    }

    @Override
    public void setStandard(String standard) {
        mTxt_Class.setText(standard);
    }

    @Override
    public void showGender(int gender) {
        mGenderBoyImg.setImageDrawable(ThemeUtility.getDrawable(mActivity, new int[]{R.attr.addChildIconMaleUnselected}));
        mGenderGirlImg.setImageDrawable(ThemeUtility.getDrawable(mActivity, new int[]{R.attr.addChildIconFemaleUnselected}));

        switch (gender) {
            case AgeGendernClassPresenter.GENDER_MALE:
                mGenderBoyImg.setImageDrawable(ThemeUtility.getDrawable(mActivity, new int[]{R.attr.addChildIconMaleSelected}));
                mGenderBoyLayout.setBackground(ThemeUtility.getDrawable(mActivity, new int[]{R.attr.addChildGenderCircle}));
                mGenderGirlLayout.setBackgroundResource(0);
                break;

            case AgeGendernClassPresenter.GENDER_FEMALE:
                mGenderGirlImg.setImageDrawable(ThemeUtility.getDrawable(mActivity, new int[]{R.attr.addChildIconFemaleSelected}));
                mGenderGirlLayout.setBackground(ThemeUtility.getDrawable(mActivity, new int[]{R.attr.addChildGenderCircle}));
                mGenderBoyLayout.setBackgroundResource(0);
                break;
        }
    }

    @Override
    public void showEmptyAgeAnimation() {
        org.ekstep.genie.util.AnimationUtils.showShakeAnimation(getView().findViewById(R.id.rl_age));
    }

    @Override
    public void showEmptyGenderAnimation() {
        org.ekstep.genie.util.AnimationUtils.showShakeAnimation(getView().findViewById(R.id.layout_gender));
    }

    @Override
    public void showEmptyClassAnimation() {
        org.ekstep.genie.util.AnimationUtils.showShakeAnimation(getView().findViewById(R.id.rl_class));
    }

    @Override
    public void showNextButtonAnimation() {
        if (!BuildConfig.FLAVOR.equalsIgnoreCase("integrationTest")) {
            ((AddChildActivity) getActivity()).mPresenter.handleNextButtonAnimation();
        }
    }

}

