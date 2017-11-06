package org.ekstep.genie.fragment;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import org.ekstep.genie.R;
import org.ekstep.genie.base.BasePresenter;
import org.ekstep.genie.base.BaseView;
import org.ekstep.genie.base.IPresenterFactory;
import org.ekstep.genie.base.PresenterManager;
import org.ekstep.genie.util.DeviceUtility;
import org.ekstep.genie.util.FontConstants;
import org.ekstep.genie.util.preference.PreferenceUtil;

/**
 * Created on 18-05-2016.
 *
 * @author anil
 */
public abstract class BaseFragment extends Fragment {

    protected Activity mActivity;
    protected BasePresenter presenter;
    private boolean isDestroyedBySystem;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = PresenterManager.getInstance()
                .getPresenter(getTAG(), getPresenterFactory());
        if (presenter != null)
            presenter.bindView(getBaseView(), getActivity());
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        isDestroyedBySystem = true;
    }

    @Override
    public void onResume() {
        super.onResume();
        isDestroyedBySystem = false;
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        if (!isDestroyedBySystem && presenter != null) {
//            presenter.unbindView();
            PresenterManager.getInstance()
                    .removePresenter(getTAG());
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.mActivity = (Activity) context;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        TextView txt_header = (TextView) view.findViewById(R.id.txt_header);
        if (txt_header != null && PreferenceUtil.getLanguage().equalsIgnoreCase(FontConstants.LANG_ENGLISH)) {
            txt_header.setTypeface(txt_header.getTypeface(), Typeface.BOLD);
        }
    }

    protected void showSyncDialog(String message) {
        final Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setContentView(R.layout.dialog_sync_data);
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;

        TextView txt_dialog = (TextView) dialog.findViewById(R.id.txt_dialog);
        txt_dialog.setText(message);

        dialog.findViewById(R.id.layout_ok).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        DeviceUtility.displayFullScreenDialog(dialog, getActivity());
    }

    public BasePresenter getPresenter() {
        return presenter;
    }

    protected abstract IPresenterFactory getPresenterFactory();

    protected abstract String getTAG();

    protected abstract BaseView getBaseView();

}
