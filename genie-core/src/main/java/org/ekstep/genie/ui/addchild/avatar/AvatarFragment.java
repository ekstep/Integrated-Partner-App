package org.ekstep.genie.ui.addchild.avatar;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import org.ekstep.genie.R;
import org.ekstep.genie.base.BasePresenter;
import org.ekstep.genie.base.BaseView;
import org.ekstep.genie.base.IPresenterFactory;
import org.ekstep.genie.base.PresenterManager;
import org.ekstep.genie.customview.FancyCoverFlow;
import org.ekstep.genie.fragment.BaseFragment;
import org.ekstep.genie.util.Constant;
import org.ekstep.genieservices.commons.bean.Profile;

/**
 * Created by swayangjit on 22/12/16.
 */

public class AvatarFragment extends BaseFragment implements AvatarContract.View {

    private FancyCoverFlow mFancyCoverFlow;
    private TextView mTxt_Pick_Avatar = null;
    private View mView_Dummy = null;

    private AvatarContract.Presenter mAvatarPresenter = null;

    public static AvatarFragment newInstance(Profile profile, boolean isEditMode) {
        AvatarFragment fragment = new AvatarFragment();
        Bundle args = new Bundle();
        args.putSerializable(Constant.BUNDLE_UPDATED_PROFILE, profile);
        args.putBoolean(Constant.BUNDLE_EDIT_MODE, isEditMode);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);

        mAvatarPresenter = (AvatarPresenter)presenter;
        mAvatarPresenter.fetchBundleExtras(getArguments());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_avatar, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews(view);
        mAvatarPresenter.openAvatarLayout();
    }

    @Override
    protected IPresenterFactory getPresenterFactory() {
        return new IPresenterFactory() {
            @Override
            public BasePresenter create() {
                return new AvatarPresenter();
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
        mFancyCoverFlow = (FancyCoverFlow) view.findViewById(R.id.coverflow_avatar);
        mFancyCoverFlow.setUnselectedAlpha(1.0f);
        mFancyCoverFlow.setUnselectedSaturation(0.0f);
        mFancyCoverFlow.setUnselectedScale(0.5f);
        mFancyCoverFlow.setSpacing(getResources().getInteger(R.integer.avatar_spacing));
        mFancyCoverFlow.setMaxRotation(0);
        mFancyCoverFlow.setActionDistance(FancyCoverFlow.ACTION_DISTANCE_AUTO);

        mFancyCoverFlow.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mAvatarPresenter.handleOnAvatarClick(position);
            }
        });

        mFancyCoverFlow.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mAvatarPresenter.handleAvatarSelection(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        mTxt_Pick_Avatar = ((TextView) view.findViewById(R.id.txt_pick));
        mView_Dummy = view.findViewById(R.id.layout_dummy_invisible);
        mView_Dummy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @Override
    public void showAvatarList(AvatarCoverflowAdapter avatarCoverflowAdapter) {
        mFancyCoverFlow.setAdapter(avatarCoverflowAdapter);
    }

    @Override
    public void setSelectedAvatar(int position) {
        mFancyCoverFlow.setSelection(position, true);
    }

    @Override
    public void adjustUiForTelugu() {

    }

    @Override
    public void adjustUiForOthers() {
        mTxt_Pick_Avatar.setTextSize(20);
    }

    @Override
    public void showInvisibleLayout() {
        mView_Dummy.setVisibility(View.VISIBLE);
    }


}
