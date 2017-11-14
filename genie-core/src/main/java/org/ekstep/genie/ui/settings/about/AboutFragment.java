package org.ekstep.genie.ui.settings.about;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import org.ekstep.genie.CoreApplication;
import org.ekstep.genie.R;
import org.ekstep.genie.base.BaseView;
import org.ekstep.genie.base.IPresenterFactory;
import org.ekstep.genie.fragment.BaseFragment;
import org.ekstep.genie.fragment.DummyLanguageTestFragment;
import org.ekstep.genie.ui.settings.SettingsActivity;
import org.ekstep.genie.util.Constant;
import org.ekstep.genie.util.FontConstants;
import org.ekstep.genie.util.preference.PreferenceUtil;
import org.ekstep.genieservices.utils.BuildConfigUtil;

public class AboutFragment extends BaseFragment implements OnClickListener {

    private static final String ABOUT_US = "about_us";
    private static final String PRIVACY_POLICY = "privacy_policy";
    private static final String TERMS_CONDITION = "terms_condition";

    private Toast mToast = null;
    private int mCount = 1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_about, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        TextView txt_device_id = (TextView) view.findViewById(R.id.txt_device_id);
        txt_device_id.setText(PreferenceUtil.getUniqueDeviceId());

        TextView txt_genie_version = (TextView) view.findViewById(R.id.txt_genie_version);
        String packageName = CoreApplication.getInstance().getClientPackageName();
        String versionName = BuildConfigUtil.getBuildConfigValue(packageName, Constant.BuildConfigKey.VERSION_NAME);
        int versionCode = Integer.valueOf(BuildConfigUtil.getBuildConfigValue(packageName, Constant.BuildConfigKey.VERSION_CODE).toString());
        txt_genie_version.setText("Genie v" + versionName + "." + versionCode);

        if (PreferenceUtil.getLanguage().equalsIgnoreCase(FontConstants.LANG_ENGLISH)) {
            txt_genie_version.setTypeface(txt_genie_version.getTypeface(), Typeface.BOLD);
            txt_device_id.setTypeface(txt_genie_version.getTypeface(), Typeface.BOLD);
        }

        initViews(view);
    }

    @Override
    protected IPresenterFactory getPresenterFactory() {
        return null;
    }

    @Override
    protected String getTAG() {
        return getClass().getName();
    }

    @Override
    protected BaseView getBaseView() {
        return null;
    }

    private void initViews(View view) {
        view.findViewById(R.id.layout_about_us).setOnClickListener(this);
        view.findViewById(R.id.layout_privacy_policy).setOnClickListener(this);
        view.findViewById(R.id.layout_terms_condition).setOnClickListener(this);
        view.findViewById(R.id.layout_genie_version).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(getActivity(), AboutActivity.class);
        int i = v.getId();
        if (i == R.id.layout_about_us) {
            intent.putExtra(Constant.EXTRA_LINK_ABOUT_US, ABOUT_US);

        } else if (i == R.id.layout_privacy_policy) {
            intent.putExtra(Constant.EXTRA_LINK_PRIVACY_POLICY, PRIVACY_POLICY);

        } else if (i == R.id.layout_terms_condition) {
            intent.putExtra(Constant.EXTRA_LINK_TERMS_CONDITION, TERMS_CONDITION);

        } else if (i == R.id.layout_genie_version) {
            intent = null;
//                navigateToDummyFragment();

        } else {
        }

        if (intent != null) {
            startActivity(intent);
        }
    }

    private void navigateToDummyFragment() {
        String navigateSuccessMsg = getResources().getString(R.string.msg_dummy_language_test_success);
        String navigateStepsMsg = getActivity().getResources().getString(R.string.label_dummy_you_are) + " " + (5 - mCount) + " " + getActivity().getResources().getString(R.string.label_dummy_steps_away);
        if (mCount == 5) {
            if (mToast == null) {
                mToast = Toast.makeText(getActivity(), navigateSuccessMsg, Toast.LENGTH_SHORT);
            } else {
                mToast.setText(navigateSuccessMsg);
            }
            mToast.show();
            ((SettingsActivity) this.getActivity()).setFragment(new DummyLanguageTestFragment(), false);
        } else {
            if (mToast == null) {
                mToast = Toast.makeText(getActivity(), navigateStepsMsg, Toast.LENGTH_SHORT);
            } else {
                mToast.setText(navigateStepsMsg);
            }
            mToast.show();
            mCount++;
        }
    }

}
