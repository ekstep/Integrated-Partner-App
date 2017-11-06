package org.ekstep.genie.activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.ekstep.genie.R;
import org.ekstep.genie.base.BasePresenter;
import org.ekstep.genie.base.BaseView;
import org.ekstep.genie.base.IPresenterFactory;
import org.ekstep.genie.base.PresenterManager;
import org.ekstep.genie.telemetry.TelemetryAction;
import org.ekstep.genie.telemetry.TelemetryBuilder;
import org.ekstep.genie.telemetry.TelemetryHandler;
import org.ekstep.genie.ui.share.ShareActivity;
import org.ekstep.genie.util.Constant;
import org.ekstep.genie.util.DeviceUtility;
import org.ekstep.genie.util.FontUtil;
import org.ekstep.genie.util.LogUtil;
import org.ekstep.genie.util.ShareUtil;
import org.ekstep.genie.util.ThemeUtility;
import org.ekstep.genie.util.Util;
import org.ekstep.genie.util.geniesdk.ImportExportUtil;
import org.ekstep.genieservices.commons.bean.enums.InteractionType;

import java.util.List;
import java.util.Map;

import static org.ekstep.genie.ui.share.ShareActivity.REQUEST_CODE_SHARE;
import static org.ekstep.genie.ui.share.ShareActivity.REQUEST_CODE_SHARE_GENIE;
import static org.ekstep.genie.ui.share.ShareActivity.REQUEST_CODE_SHARE_GENIE_CONFIGURATIONS;
import static org.ekstep.genie.ui.share.ShareActivity.REQUEST_CODE_SHARE_PROFILE;
import static org.ekstep.genie.ui.share.ShareActivity.REQUEST_CODE_SHARE_TELEMETRY;

/**
 * Created on 01-06-2016.
 *
 * @author GoodWorkLabs
 */
public abstract class BaseActivity extends RuntimePermissionsActivity {

    private FragmentManager mFragmentManager = null;
    protected BasePresenter presenter;
    private boolean isDestroyedBySystem;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        if (!ShareActivity.class.getCanonicalName().equalsIgnoreCase(getComponentName().getClassName())){
            ThemeUtility.changeToTheme(0);
            ThemeUtility.onActivityCreateSetTheme(this);
        }

        super.onCreate(savedInstanceState);

        presenter = PresenterManager.getInstance()
                .getPresenter(getTAG(), getPresenterFactory());

        if (presenter != null)
            presenter.bindView(getBaseView(), this);

        FontUtil.getInstance().changeLocale();
        mFragmentManager = getSupportFragmentManager();
        DeviceUtility.displayFullScreen(this);
    }

    @Override
    public void onSaveInstanceState(Bundle outState,
                                    PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        isDestroyedBySystem = true;
    }

    @Override
    public void onPermissionsGranted(int requestCode) {

    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> deniedPermissions) {

    }

    public void setFragment(Fragment fragment) {
        setFragment(fragment, true);
    }

    public void setFragment(Fragment fragment, Boolean addToBackStack) {
        setFragment(fragment, addToBackStack, R.id.fragment_container);
    }

    public void setFragment(Fragment fragment, Boolean addToBackStack, int fragmentContainer) {
        FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
        fragmentTransaction.replace(fragmentContainer, fragment, fragment.getClass().getSimpleName());

        if (addToBackStack) {
            fragmentTransaction.addToBackStack(null);
        }

        fragmentTransaction.commitAllowingStateLoss();

        mFragmentManager.executePendingTransactions();
        DeviceUtility.displayFullScreen(this);
    }

    public void setFragment(Fragment fragment, Boolean addToBackStack, int fragmentContainer, boolean isAnim) {
        FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();

        if (isAnim) {
            fragmentTransaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim.enter_from_left, R.anim.exit_to_right);
        }
        fragmentTransaction.replace(fragmentContainer, fragment, fragment.getClass().getSimpleName());

        if (addToBackStack) {
            fragmentTransaction.addToBackStack(null);
        }

        fragmentTransaction.commitAllowingStateLoss();

        mFragmentManager.executePendingTransactions();
        DeviceUtility.displayFullScreen(this);
    }

    public Fragment getRunningFragment() {
        return mFragmentManager.findFragmentById(R.id.fragment_container);
    }

    public void popBackStack() {
        int stackCount = mFragmentManager.getBackStackEntryCount();
        if (stackCount > 1) {
            mFragmentManager.popBackStack();
        } else {
            finish();
        }
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            DeviceUtility.displayFullScreenPopUp(this);
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        FontUtil.getInstance().changeLocale();
    }

    @Override
    protected void onResume() {
        super.onResume();
        FontUtil.getInstance().changeLocale();
        isDestroyedBySystem = false;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (presenter != null) {
            if (!isDestroyedBySystem) {
                PresenterManager.getInstance()
                        .removePresenter(getTAG());
            } else {
                presenter.unbindView();
            }
        }
    }

    protected void showSyncDialog(String message) {
        final Dialog dialog = new Dialog(BaseActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setContentView(R.layout.dialog_sync_data);
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;

        LinearLayout layout_ok = (LinearLayout) dialog.findViewById(R.id.layout_ok);
        TextView txt_dialog = (TextView) dialog.findViewById(R.id.txt_dialog);
        txt_dialog.setText(message);

        layout_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        DeviceUtility.displayFullScreenDialog(dialog, BaseActivity.this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case REQUEST_CODE_SHARE:
                    if (data.getBooleanExtra(Constant.IS_ECAR, false)) {
                        ImportExportUtil.doExportContent(data, this);
                    } else {
                        List<String> identifierList = data.getExtras().getStringArrayList(Constant.SHARE_IDENTIFIER);
                        String screenName = data.getStringExtra(Constant.SHARE_SCREEN_NAME);
                        if (identifierList != null) {
                            TelemetryHandler.saveTelemetry(TelemetryBuilder.buildGEInteract(InteractionType.TOUCH, screenName, TelemetryAction.SHARE_CONTENT_LINK, identifierList.get(0)));
                        }

                        startActivity(data);
                    }
                    break;

                case REQUEST_CODE_SHARE_PROFILE:
                    ImportExportUtil.doExportProfiles(data, this);
                    break;

                case REQUEST_CODE_SHARE_TELEMETRY:
                    ImportExportUtil.doExportTelemetry(data, this);
                    break;

                case REQUEST_CODE_SHARE_GENIE:
                    ImportExportUtil.doShareGenie(data, this);
                    break;

                case REQUEST_CODE_SHARE_GENIE_CONFIGURATIONS:
                    ImportExportUtil.doShareGenieConfigurations(data, this);
                    break;

                default:
                    break;
            }
        }
    }

    protected abstract IPresenterFactory getPresenterFactory();

    protected abstract String getTAG();

    protected abstract BaseView getBaseView();
}
