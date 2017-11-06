package org.ekstep.genie.ui.addchild.nickname;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import org.ekstep.genie.R;
import org.ekstep.genie.base.BasePresenter;
import org.ekstep.genie.base.BaseView;
import org.ekstep.genie.base.IPresenterFactory;
import org.ekstep.genie.customview.EkStepCustomEditText;
import org.ekstep.genie.fragment.BaseFragment;
import org.ekstep.genie.ui.addchild.AddChildActivity;
import org.ekstep.genie.util.Constant;
import org.ekstep.genie.util.DeviceUtility;
import org.ekstep.genie.util.KeyboardUtil;
import org.ekstep.genie.util.LogUtil;
import org.ekstep.genie.util.Util;
import org.ekstep.genieservices.commons.bean.Profile;

/**
 * Created by swayangjit on 22/12/16.
 */

public class NickNameFragment extends BaseFragment implements
        NickNameContract.View, TextView.OnEditorActionListener,
        View.OnFocusChangeListener, KeyboardUtil.IKeyboardListener,
        EkStepCustomEditText.KeyImeChange, View.OnClickListener {
    public static final String TAG = NickNameFragment.class.getName();

    @NonNull
    private NickNameContract.Presenter mNickNamePresenter;

    private TextView mTxt_Random_Name = null;
    private EkStepCustomEditText mEdt_NickName = null;
    private TextView mTxt_type_nick_name = null;
    private TextView mTxt_Do_You = null;
    private ScrollView scrollView = null;
    private RelativeLayout.LayoutParams layoutParams = null;

    public static NickNameFragment newInstance(Profile profile, boolean mode, boolean isEditMode) {
        NickNameFragment fragment = new NickNameFragment();
        Bundle args = new Bundle();
        args.putSerializable(Constant.BUNDLE_UPDATED_PROFILE, profile);
        args.putBoolean(Constant.BUNDLE_KEY_MODE, mode);
        args.putBoolean(Constant.BUNDLE_EDIT_MODE, isEditMode);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mNickNamePresenter = (NickNamePresenter)presenter;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_nick_name, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews(view);
        mNickNamePresenter.fetchBundleExtras(getArguments());
        mNickNamePresenter.openNickNameLayout();
    }

    @Override
    protected IPresenterFactory getPresenterFactory() {
        return new IPresenterFactory() {
            @Override
            public BasePresenter create() {
                return new NickNamePresenter();
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
        mTxt_Random_Name = (TextView) view.findViewById(R.id.txt_random_name);
        mEdt_NickName = (EkStepCustomEditText) view.findViewById(R.id.edt_nickname);
        mEdt_NickName.setOnEditorActionListener(this);
        mEdt_NickName.setOnFocusChangeListener(this);
        mEdt_NickName.setKeyImeChangeListener(this);
        KeyboardUtil.setKeyboardListener(getActivity(), this);

        scrollView = (ScrollView) view.findViewById(R.id.scrollview);
        layoutParams = (RelativeLayout.LayoutParams) scrollView.getLayoutParams();
        mTxt_type_nick_name = ((TextView) view.findViewById(R.id.txt_type_nick_name));
        view.findViewById(R.id.txt_do_you);

        view.findViewById(R.id.layout_random_name).setOnClickListener(this);
    }

    @Override
    public void setRandomName(String name) {
        mTxt_Random_Name.setText(name);
    }

    @Override
    public String getNickName() {
        return mEdt_NickName.getText().toString();
    }

    @Override
    public void showBlankNickNameMessage() {
        Util.showCustomToast(getResources().getString(R.string.error_addchild_blank_nick_name));
    }

    @Override
    public void setName(String name) {
        mEdt_NickName.setText(name);
    }

    @Override
    public void adjustUiForTelugu() {
        mTxt_Random_Name.setPadding(0, 0, 0, 0);
        mTxt_type_nick_name.setPadding(0, 0, 0, 0);
    }

    @Override
    public void adjustUiForOthers() {
        mTxt_Random_Name.setPadding(0, 5, 0, 0);
        mTxt_type_nick_name.setTextSize(15);
    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        layoutParams.bottomMargin = 0;
        layoutParams.setMargins(0, 0, 0, 0);
        scrollView.setLayoutParams(layoutParams);

        DeviceUtility.displayFullScreenAfterCancelEditKey(getActivity());
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mTxt_type_nick_name.setFocusableInTouchMode(true);
                mTxt_type_nick_name.setFocusable(true);
                mTxt_type_nick_name.requestFocus();
            }
        }, 100);
        return false;
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        if (!((AddChildActivity) getActivity()).isBackPressed()) {
            if (hasFocus) {
                int bottomMargin = (int) DeviceUtility.getItemHeight(getActivity());
                layoutParams.bottomMargin = bottomMargin;
                layoutParams.setMargins(0, 0, 0, bottomMargin);
                scrollView.setLayoutParams(layoutParams);
            }
        }
    }

    @Override
    public void onKeyIme(int keyCode, KeyEvent event) {
        LogUtil.i(TAG, "onKeyIme---------");
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (layoutParams != null) {
                layoutParams.bottomMargin = 0;
                layoutParams.setMargins(0, 0, 0, 0);
                scrollView.setLayoutParams(layoutParams);

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mTxt_type_nick_name.setFocusableInTouchMode(true);
                        mTxt_type_nick_name.setFocusable(true);
                        mTxt_type_nick_name.requestFocus();
                    }
                }, 100);
            }
        }
    }

    @Override
    public void onKeyboardVisible() {
        LogUtil.i(TAG, "onKeyboardVisible");
    }

    @Override
    public void onKeyboardHide() {
        if (getActivity() != null) {
            DeviceUtility.displayFullScreenAfterCancelEditKey(getActivity());
        }
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.layout_random_name) {
            mNickNamePresenter.handleNavigationToAgeClassGenderFragment(true);

        }
    }
}
