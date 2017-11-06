package org.ekstep.genie.ui.addchild.mediumnboard;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
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
import org.ekstep.genie.util.AnimationUtils;
import org.ekstep.genie.util.Constant;
import org.ekstep.genie.util.DeviceUtility;
import org.ekstep.genie.util.PopUpWindowUtil;
import org.ekstep.genieservices.commons.bean.Profile;

import java.util.List;

/**
 * Created by swayangjit on 22/12/16.
 */

public class MediumnBoardFragment extends BaseFragment
        implements MediumnBoardContract.View, View.OnClickListener {

    @NonNull
    private MediumnBoardContract.Presenter mMediumBoardPresenter;

    private TextView mTxt_Medium = null;
    private TextView mTxt_Board = null;
    private PopupWindow mPopupWindow = null;
    private View mLayout_Transparent_Bg = null;
    private RelativeLayout mRelativeLayoutMedium;
    private RelativeLayout mRelativeLayoutBoard;

    public static MediumnBoardFragment newInstance(Profile profile, boolean isEditMode) {
        MediumnBoardFragment fragment = new MediumnBoardFragment();
        Bundle args = new Bundle();
        args.putSerializable(Constant.BUNDLE_UPDATED_PROFILE, profile);
        args.putBoolean(Constant.BUNDLE_EDIT_MODE, isEditMode);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mMediumBoardPresenter = (MediumnBoardPresenter)presenter;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_medium_board, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initViews(view);

        mMediumBoardPresenter.fetchBundleExtras(getArguments());

        mMediumBoardPresenter.openMediumnBoard();

    }

    @Override
    protected IPresenterFactory getPresenterFactory() {
        return new IPresenterFactory() {
            @Override
            public BasePresenter create() {
                return new MediumnBoardPresenter();
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
        mTxt_Medium = (TextView) view.findViewById(R.id.txt_medium);

        mTxt_Board = (TextView) view.findViewById(R.id.txt_board);

        mLayout_Transparent_Bg = getActivity().findViewById(R.id.layout_transparent_bg);

        mRelativeLayoutMedium = (RelativeLayout) getActivity().findViewById(R.id.rl_medium);
        mRelativeLayoutMedium.setOnClickListener(this);

        mRelativeLayoutBoard = (RelativeLayout) getActivity().findViewById(R.id.rl_board);
        mRelativeLayoutBoard.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.rl_medium) {
            mLayout_Transparent_Bg.setVisibility(View.VISIBLE);
            mMediumBoardPresenter.onMediumClick(v);

        } else if (i == R.id.rl_board) {
            mLayout_Transparent_Bg.setVisibility(View.VISIBLE);
            mMediumBoardPresenter.onBoardClick(v);

        }
    }

    @Override
    public void showMediumListPopup(List<String> mediumList, View view) {
        PopUpWindowUtil.PopupWindowArgBuilder popupWindowArgBuilder = new PopUpWindowUtil.PopupWindowArgBuilder().
                setAnchor(mRelativeLayoutMedium).
                setContext(getActivity()).
                setAdapter(new PopUpWindowAdapter(mediumList, new IMenuItemClick() {
                    @Override
                    public void onMenuItemClick(int position) {
                        DeviceUtility.displayFullScreen(getActivity());
                        mLayout_Transparent_Bg.setVisibility(View.GONE);
                        PopUpWindowUtil.dismissPopUpWindow();
                        mMediumBoardPresenter.onMediumItemClick(position);

                    }
                })).
                setDrawable(R.drawable.layer_dialog_with_border).
                setId(R.id.recycler_view_medium).
                setTransparentView(mLayout_Transparent_Bg);
        PopUpWindowUtil.showPopUpWindow(popupWindowArgBuilder);
    }

    @Override
    public void showBoardListPopup(List<String> boardList, View view) {

        PopUpWindowUtil.PopupWindowArgBuilder popupWindowArgBuilder = new PopUpWindowUtil.PopupWindowArgBuilder().
                setAnchor(mRelativeLayoutBoard).
                setContext(getActivity()).
                setAdapter(new PopUpWindowAdapter(boardList, new IMenuItemClick() {
                    @Override
                    public void onMenuItemClick(int position) {
                        DeviceUtility.displayFullScreen(getActivity());
                        mLayout_Transparent_Bg.setVisibility(View.GONE);
                        PopUpWindowUtil.dismissPopUpWindow();
                        mMediumBoardPresenter.onBoardItemClick(position);

                    }
                })).
                setDrawable(R.drawable.layer_dialog_with_border).
                setId(R.id.recycler_view_board).
                setTransparentView(mLayout_Transparent_Bg);
        PopUpWindowUtil.showPopUpWindow(popupWindowArgBuilder);

    }

    @Override
    public void showMedium(String medium) {
        mTxt_Medium.setText(medium);
    }

    @Override
    public void showBoard(String board) {
        mTxt_Board.setText(board);
    }

    @Override
    public void showEmptyMediumAnimation() {
        AnimationUtils.showShakeAnimation(getView().findViewById(R.id.rl_medium));
    }

    @Override
    public void showEmptyBoardAnimation() {
        AnimationUtils.showShakeAnimation(getView().findViewById(R.id.rl_board));
    }

    @Override
    public void animateNextButton() {
        if (!BuildConfig.FLAVOR.equalsIgnoreCase("integrationTest")) {
            ((AddChildActivity) getActivity()).mPresenter.handleNextButtonAnimation();
        }

    }
}
