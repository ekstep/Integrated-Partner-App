package org.ekstep.genie.ui.addchild.welcomeaboard;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import org.ekstep.genie.R;
import org.ekstep.genie.base.BasePresenter;
import org.ekstep.genie.base.BaseView;
import org.ekstep.genie.base.IPresenterFactory;
import org.ekstep.genie.base.PresenterManager;
import org.ekstep.genie.fragment.BaseFragment;
import org.ekstep.genie.util.Constant;
import org.ekstep.genieservices.commons.bean.Profile;

/**
 * Created by swayangjit on 23/12/16.
 */

public class WelcomeAboardFragment extends BaseFragment
        implements WelcomeAboardContract.View {
    private static final String TAG = WelcomeAboardFragment.class.getName();

    private ImageView mImg_Welcome_Avatar = null;

    private TextView mTxt_Welcome_NickName = null;

    @NonNull
    private WelcomeAboardContract.Presenter mWelcomePresenter;

    public static WelcomeAboardFragment newInstance(Profile profile) {
        WelcomeAboardFragment fragment = new WelcomeAboardFragment();
        Bundle args = new Bundle();
        args.putSerializable(Constant.BUNDLE_UPDATED_PROFILE, profile);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mWelcomePresenter = (WelcomeAboardPresenter)presenter;
        mWelcomePresenter.fetchBundleExtras(getArguments());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_welcome_aboard, container, false);
        view.setFocusableInTouchMode(true);
        view.requestFocus();
        view.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN) {
                    if (keyCode == KeyEvent.KEYCODE_BACK) {
                        return true;
                    }
                }
                return false;
            }
        });
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews(view);
        mWelcomePresenter.openWelcomeLayout();
    }

    @Override
    protected IPresenterFactory getPresenterFactory() {
        return new IPresenterFactory() {
            @Override
            public BasePresenter create() {
                return new WelcomeAboardPresenter();
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
        mTxt_Welcome_NickName = (TextView) view.findViewById(R.id.txt_welcome_name);
        mImg_Welcome_Avatar = (ImageView) view.findViewById(R.id.img_welcome_avatar);
    }



    @Override
    public void showSelectedName(String name) {
        mTxt_Welcome_NickName.setText(name);
    }

    @Override
    public void showWelcomeAvatar(int avatar) {
        mImg_Welcome_Avatar.setImageResource(avatar);
    }
}
