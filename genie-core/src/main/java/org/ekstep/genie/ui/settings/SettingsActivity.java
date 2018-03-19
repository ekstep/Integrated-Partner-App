package org.ekstep.genie.ui.settings;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import org.ekstep.genie.R;
import org.ekstep.genie.activity.BaseActivity;
import org.ekstep.genie.base.BasePresenter;
import org.ekstep.genie.base.BaseView;
import org.ekstep.genie.base.IPresenterFactory;
import org.ekstep.genie.fragment.BaseFragment;
import org.ekstep.genie.telemetry.EnvironmentId;
import org.ekstep.genie.telemetry.TelemetryBuilder;
import org.ekstep.genie.telemetry.TelemetryHandler;
import org.ekstep.genie.telemetry.TelemetryStageId;
import org.ekstep.genie.telemetry.enums.ImpressionType;
import org.ekstep.genie.ui.settings.about.AboutFragment;
import org.ekstep.genie.ui.settings.advancedsettings.AdvancedSettingsFragment;
import org.ekstep.genie.ui.settings.datasettings.DataSettingsFragment;
import org.ekstep.genie.ui.settings.language.LanguageFragment;
import org.ekstep.genie.ui.settings.storagesettings.StorageSettingsFragment;
import org.ekstep.genie.ui.share.ShareActivity;
import org.ekstep.genie.util.Constant;
import org.ekstep.genie.util.ThemeUtility;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by swayangjit on 14/9/17.
 */

public class SettingsActivity extends BaseActivity implements SettingsContract.View, View.OnClickListener {

    private static final String DATA_SETTINGS = "data_settings";
    private int[] mSettingsName = new int[]{R.string.label_language, R.string.title_sync_data_setting,
            R.string.title_advanced_settings, R.string.title_storage_settings,
            R.string.title_about};
    private int[] mSettingsIconAttributes = new int[]{R.attr.settingsIconLanguage, R.attr.settingsIconDataSettings,
            R.attr.settingsIconAdvancedDataSettings, R.attr.settingsIconStorageSettings, R.attr.settingsIconAbout};
    private SettingsPresenter mPresenter;
    private RecyclerView mRv_Settings;
    private TextView mTxt_Header;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        mPresenter = (SettingsPresenter) presenter;
        initViews();
        extractBundle(getIntent());
    }

    private void initViews() {
        mRv_Settings = (RecyclerView) findViewById(R.id.rv_settings_master);
        mRv_Settings.setLayoutManager(new LinearLayoutManager(this));
        mRv_Settings.setHasFixedSize(true);

        mTxt_Header = (TextView) findViewById(R.id.txt_header);
        findViewById(R.id.btn_settings_share).setOnClickListener(this);

        findViewById(R.id.btn_back).setOnClickListener(this);

        TelemetryHandler.saveTelemetry(TelemetryBuilder.buildImpressionEvent(EnvironmentId.SETTINGS, TelemetryStageId.SETTINGS_HOME, ImpressionType.VIEW));
    }

    private void extractBundle(Intent intent) {
        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            int position = bundle.getInt(Constant.BundleKey.BUNDLE_KEY_SETTINGS_POSITION);
            showSettingsList(position);
            navigateTo(position);
        } else {
            showSettingsList(0);
            navigateTo(0);
        }
    }


    @Override
    protected IPresenterFactory getPresenterFactory() {
        return new IPresenterFactory() {
            @Override
            public BasePresenter create() {
                return new SettingsPresenter();
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

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.btn_back) {
            finish();

        } else if (i == R.id.btn_settings_share) {
            ShareActivity.startGenieConfigurationsShareIntent(this);

        } else {
        }
    }

    @Override
    public void showSettingsList(int selectedPosition) {
        SettingsAdapter settingsAdapter = new SettingsAdapter(this, mPresenter);
        List<Drawable> mSettingsIcons = new ArrayList<>();
        for (int i = 0; i < mSettingsIconAttributes.length; i++) {
            mSettingsIcons.add(i, ThemeUtility.getDrawable(this, new int[]{mSettingsIconAttributes[i]}));
        }
        settingsAdapter.setData(mSettingsName, mSettingsIcons);
        settingsAdapter.setSelection(selectedPosition);
        mRv_Settings.setAdapter(settingsAdapter);

    }

    @Override
    public void navigateTo(int position) {
        mTxt_Header.setText(mSettingsName[position]);
        BaseFragment fragment;
        switch (position) {
            case 0:
                fragment = new LanguageFragment();
                break;
            case 1:
                fragment = new DataSettingsFragment();
                break;
            case 2:
                fragment = new AdvancedSettingsFragment();
                break;
            case 3:
                fragment = new StorageSettingsFragment();
                break;
            case 4:
                fragment = new AboutFragment();
                break;
            default:
                fragment = new LanguageFragment();
                break;
        }
        setFragment(fragment);
    }

    @Override
    public void onBackPressed() {
        Fragment fragment = getSupportFragmentManager().findFragmentByTag("AddNewTagFragment");
        if (fragment != null && fragment.isVisible()) {
            super.onBackPressed();
        } else {
            finish();
        }
    }
}
