package org.ekstep.genie.ui.addchild;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.ekstep.genie.R;
import org.ekstep.genie.activity.BaseActivity;
import org.ekstep.genie.base.BasePresenter;
import org.ekstep.genie.base.BaseView;
import org.ekstep.genie.base.IPresenterFactory;
import org.ekstep.genie.customview.EkstepStepperProgress;
import org.ekstep.genie.ui.addchild.agegendernclass.AgeGenderClassFragment;
import org.ekstep.genie.ui.addchild.agegendernclass.AgeGendernClassPresenter;
import org.ekstep.genie.ui.addchild.avatar.AvatarFragment;
import org.ekstep.genie.ui.addchild.avatar.AvatarPresenter;
import org.ekstep.genie.ui.addchild.mediumnboard.MediumnBoardFragment;
import org.ekstep.genie.ui.addchild.mediumnboard.MediumnBoardPresenter;
import org.ekstep.genie.ui.addchild.nickname.NickNameFragment;
import org.ekstep.genie.ui.addchild.nickname.NickNamePresenter;
import org.ekstep.genie.ui.addchild.welcomeaboard.WelcomeAboardFragment;
import org.ekstep.genie.util.AnimationUtils;
import org.ekstep.genie.util.geniesdk.ContentUtil;
import org.ekstep.genieservices.commons.bean.Profile;
import org.ekstep.genieservices.commons.utils.StringUtil;

/**
 * Created on 22/12/16.
 *
 * @author swayangjit
 */
public class AddChildActivity extends BaseActivity
        implements AddChildContract.View, View.OnClickListener {

    private static final String TAG = AddChildActivity.class.getSimpleName();

    public AddChildContract.Presenter mPresenter = null;

    private AvatarFragment avatarFragment;
    private NickNameFragment nickNameFragment;
    private AgeGenderClassFragment ageFragment;
    private MediumnBoardFragment classGenderFragment;

    private TextView mTxt_Add_Child = null;

    private ImageView mImg_Add_group = null;
    private ImageView mImg_Add_child = null;
    private ImageView mImg_Skip = null;
    private ImageView mImg_Next = null, mImg_back = null;

    private View mLayout_Switch = null;
    private View mLayout_Header;
    private RelativeLayout mLayout_Transparent_Bg = null;
    private EkstepStepperProgress mStepperProgress = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_add_child);

        mPresenter = (AddChildContract.Presenter) presenter;

        initViews();

        mPresenter.fetchBundleExtras(getIntent().getExtras());
        mPresenter.openAddChildLayout();
    }

    @Override
    protected IPresenterFactory getPresenterFactory() {
        return new IPresenterFactory() {
            @Override
            public BasePresenter create() {
                return new AddChildPresenter();
            }
        };
    }

    @Override
    protected String getTAG() {
        return getLocalClassName();
    }

    @Override
    protected BaseView getBaseView() {
        return this;
    }

    private void initViews() {
        mTxt_Add_Child = (TextView) findViewById(R.id.txt_add_child);
        mImg_Add_group = (ImageView) findViewById(R.id.img_add_group);
        mImg_Add_child = (ImageView) findViewById(R.id.img_add_child);

        mImg_Skip = (ImageView) findViewById(R.id.img_skip);
        mImg_Skip.setOnClickListener(this);

        mImg_Next = (ImageView) findViewById(R.id.img_next);
        mImg_Next.setOnClickListener(this);

        mImg_back = (ImageView) findViewById(R.id.back_btn);
        mImg_back.setOnClickListener(this);

        mLayout_Switch = findViewById(R.id.layout_toggle);
        mLayout_Switch.setOnClickListener(this);

        mLayout_Header = findViewById(R.id.layout_header);

        mLayout_Transparent_Bg = (RelativeLayout) findViewById(R.id.layout_transparent_bg);
        mLayout_Transparent_Bg.setOnClickListener(this);

        mStepperProgress = new EkstepStepperProgress(this);
    }

    @Override
    public void showTitle(int message) {
        mTxt_Add_Child.setText(getResources().getString(message));
    }

    @Override
    public void showAddChildIcon() {
        mImg_Add_child.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideAddChildIcon() {
        mImg_Add_child.setVisibility(View.GONE);
    }

    @Override
    public void showAddGroupIcon() {
        mImg_Add_group.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideAddGroupIcon() {
        mImg_Add_group.setVisibility(View.INVISIBLE);
    }

    @Override
    public void showAvatarFragment(Profile profile) {
        avatarFragment = AvatarFragment.newInstance(profile, mPresenter.isEditMode());
        setFragment(avatarFragment, true, R.id.fragment_container, true);
    }

    @Override
    public void showNickNameFragment(Profile profile, boolean mode) {
        nickNameFragment = NickNameFragment.newInstance(profile, mode, mPresenter.isEditMode());
        if (!StringUtil.isNullOrEmpty(profile.getHandle()) && !mPresenter.isEditMode()) {
            setFragment(nickNameFragment, true, R.id.fragment_container, true);
        } else {
            setFragment(nickNameFragment, true, R.id.fragment_container);
        }
    }

    @Override
    public void showAgeGenderAndClassFragment(Profile profile) {
        ageFragment = AgeGenderClassFragment.newInstance(profile, mPresenter.isEditMode());
        setFragment(ageFragment, true, R.id.fragment_container, true);
    }

    @Override
    public void showMediumAndBoardFragment(Profile profile) {
        classGenderFragment = MediumnBoardFragment.newInstance(profile, mPresenter.isEditMode());
        setFragment(classGenderFragment, true, R.id.fragment_container, true);
    }

    @Override
    public void showWelcomeAboardFragment(Profile profile) {
        WelcomeAboardFragment welcomeAboardFragment = WelcomeAboardFragment.newInstance(profile);
        setFragment(welcomeAboardFragment, true, R.id.fragment_container, true);
    }

    @Override
    public void showSkip() {
        mImg_Skip.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideSkip() {
        mImg_Skip.setVisibility(View.INVISIBLE);
    }

    @Override
    public void showNext() {
        mImg_Next.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideNext() {
        mImg_Next.setVisibility(View.INVISIBLE);
    }

    @Override
    public void showBack() {
        mImg_back.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideBack() {
        mImg_back.setVisibility(View.INVISIBLE);
    }

    @Override
    public void hideNavigationHeader() {
        mLayout_Header.setVisibility(View.INVISIBLE);
    }

    @Override
    public void hideToggle() {
        mLayout_Switch.setVisibility(View.GONE);
    }

    @Override
    public void showToggle() {
        mLayout_Switch.setVisibility(View.VISIBLE);
    }

    @Override
    public Profile getAvatarData() {
        return ((AvatarPresenter) avatarFragment.getPresenter()).getProfile();
    }

    @Override
    public Profile getNickNameData() {
        return ((NickNamePresenter) nickNameFragment.getPresenter()).getName();
    }

    @Override
    public Profile getAgeGenderAndClassData() {
        return ((AgeGendernClassPresenter) ageFragment.getPresenter()).getAgeGendernClass();
    }

    @Override
    public Profile getMediumAndBoardData() {
        return ((MediumnBoardPresenter) classGenderFragment.getPresenter()).getMediumnBoard();
    }

    @Override
    public boolean isValidNickName() {
        return ((NickNamePresenter) nickNameFragment.getPresenter()).isValidNickName();
    }

    @Override
    public boolean isBackPressed() {
        return mPresenter.isBackPressed();
    }

    @Override
    public void finishActivity() {
        finish();
    }

    @Override
    public void waitAndFinishActivity() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                ContentUtil.publishRefreshProfileEvent();
                finish();
            }
        }, 1000);
    }

    @Override
    public void showAgeNotAvailableError() {
        ((AgeGendernClassPresenter) ageFragment.getPresenter()).handleAgeNotAvailable();
    }

    @Override
    public void showGenderNotAvailableError() {
        ((AgeGendernClassPresenter) ageFragment.getPresenter()).handleGenderNotAvailable();
    }

    @Override
    public void showClassNotAvailableError() {
        ((AgeGendernClassPresenter) ageFragment.getPresenter()).handleClassNotAvailable();
    }

    @Override
    public void showMediumNotAvailableError() {
        ((MediumnBoardPresenter) classGenderFragment.getPresenter()).handleEmptyMedium();
    }

    @Override
    public void showBoardNotAvailableError() {
        ((MediumnBoardPresenter) classGenderFragment.getPresenter()).handleEmptyBoard();
    }

    @Override
    public void showAddGroupProgress() {
        mStepperProgress.setMaxStateNumber(2);
    }

    @Override
    public void showAddChildProgress() {
        mStepperProgress.setMaxStateNumber(4);
    }

    @Override
    public void hideProgress() {
        mStepperProgress.setVisibility(View.GONE);
    }

    @Override
    public void setProgressBarState(int state) {
        mStepperProgress.setCurrentStateNumber(state);
    }

    @Override
    public void showNextButtonAnimation() {
        AnimationUtils.showSlowShakeAnimation(mImg_Next);
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.back_btn || i == R.id.img_skip) {
            AnimationUtils.stopShakeAnimation(mImg_Next);
            mPresenter.handleGenieBackButtonClick();

        } else if (i == R.id.img_next) {
            AnimationUtils.stopShakeAnimation(mImg_Next);
            mPresenter.handleNextButtonClick();

        } else if (i == R.id.layout_toggle) {
            mPresenter.handleToggleProfileMode();
            ((NickNamePresenter) nickNameFragment.getPresenter()).toggleMode();

        } else if (i == R.id.layout_transparent_bg) {
            mLayout_Transparent_Bg.setVisibility(View.GONE);

        }
    }

    @Override
    public void onBackPressed() {
        mPresenter.handleGenieBackButtonClick();
    }

}
