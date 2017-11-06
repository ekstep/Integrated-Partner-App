package org.ekstep.genie.customview;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.support.annotation.UiThread;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.ekstep.genie.R;
import org.ekstep.genie.util.preference.PreferenceKey;
import org.ekstep.genie.util.preference.PreferenceUtil;
import org.ekstep.genieservices.commons.bean.MoveContentProgress;

/**
 * Created by swayangjit on 23/9/17.
 */

public class EkStepGenericDialog extends Dialog implements View.OnClickListener, DialogInterface.OnDismissListener {

    private TextView mBtnPositive;
    private TextView mBtnNegative;
    private LinearLayout mLayout_Parent;
    private LinearLayout mLayout_CustomView;
    private Builder builder;
    private CheckBox mCheckBox;
    private ProgressBar mProgressBar;
    private TextView mTxtProgress;

    public EkStepGenericDialog(@NonNull Context context) {
        super(context);
    }

    protected EkStepGenericDialog(Builder builder) {
        super(builder.context, R.style.TransparentDialogTheme);
        this.builder = builder;
        WindowManager.LayoutParams lpWindow = new WindowManager.LayoutParams();
        if (getWindow() != null) {
            getWindow().setAttributes(lpWindow);
        }
        setLayout(this);
    }

    @UiThread
    private void setLayout(final EkStepGenericDialog dialog) {
        final EkStepGenericDialog.Builder builder = dialog.builder;
        setContentView(R.layout.dialog_generic);
        mLayout_Parent = (LinearLayout) findViewById(R.id.layout_parent);
        mLayout_CustomView = (LinearLayout) findViewById(R.id.layout_custom_view);
        mBtnPositive = (TextView) mLayout_Parent.findViewById(R.id.txt_positive);
        mBtnNegative = (TextView) mLayout_Parent.findViewById(R.id.txt_negative);
        mCheckBox = (CheckBox) mLayout_Parent.findViewById(R.id.checkbox);
        mProgressBar = (ProgressBar) mLayout_Parent.findViewById(R.id.progress_bar);
        mTxtProgress = (TextView) mLayout_Parent.findViewById(R.id.txt_progress);

        if (builder.showProgressBar) {
            mProgressBar.setVisibility(View.VISIBLE);
            mTxtProgress.setVisibility(View.VISIBLE);
        }
        mProgressBar.setMax(builder.progressLimit);

//        if (builder.showCheckBox) {
//            mCheckBox.setVisibility(View.VISIBLE);
//        }

        mCheckBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mCheckBox.isChecked()) {
                    PreferenceUtil.getPreferenceWrapper().
                            putBoolean(PreferenceKey.KEY_HIDE_MEMORY_CARD_DIALOG, true);
                } else {
                    PreferenceUtil.getPreferenceWrapper().
                            putBoolean(PreferenceKey.KEY_HIDE_MEMORY_CARD_DIALOG, false);
                }
            }
        });

        if (builder.txtTitle != 0) {
            TextView txtTitle = (TextView) mLayout_Parent.findViewById(R.id.txt_title);
            txtTitle.setVisibility(View.VISIBLE);
            txtTitle.setText(builder.txtTitle);
        }

        if (builder.txtDesc != 0) {
            TextView txtDesc = (TextView) mLayout_Parent.findViewById(R.id.txt_desc);
            txtDesc.setVisibility(View.VISIBLE);
            txtDesc.setText(builder.txtDesc);
        } else if (builder.strDesc != null) {
            TextView txtDesc = (TextView) mLayout_Parent.findViewById(R.id.txt_desc);
            txtDesc.setVisibility(View.VISIBLE);
            txtDesc.setText(builder.strDesc);
        }

        if (builder.customView != null) {
            mLayout_CustomView.setVisibility(View.VISIBLE);
            mLayout_Parent.findViewById(R.id.txt_desc).setVisibility(View.GONE);
            mLayout_CustomView.removeAllViews();
            mLayout_CustomView.addView(builder.customView);

        }


        if (builder.txtPositive != 0) {
            mBtnPositive.setVisibility(View.VISIBLE);
            mBtnPositive.setText(builder.txtPositive);
            mBtnPositive.setTag(Action.POSITIVE);
            mBtnPositive.setOnClickListener(this);
        }

        if (builder.txtNegative != 0) {
            mBtnNegative.setVisibility(View.VISIBLE);
            mBtnNegative.setText(builder.txtNegative);
            mBtnNegative.setTag(Action.NEGATIVE);
            mBtnNegative.setOnClickListener(this);
        }

        dialog.setOnDismissListener(this);


        dialog.setCancelable(true);


    }

    @Override
    public final void onClick(View v) {
        Action tag = (Action) v.getTag();
        switch (tag) {
            case POSITIVE:

                if (builder.onPositiveCallback != null) {
                    builder.onPositiveCallback.onClick(this, tag);
                }
                break;

            case NEGATIVE:
                if (builder.onNegativeCallback != null) {
                    builder.onNegativeCallback.onClick(this, tag);
                }
                break;

        }

    }

    public View getCustomView() {
        return builder.customView;
    }


    @Override
    public void dismiss() {
        if (this.isShowing())
            super.dismiss();
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        if (builder.onDismissCallback != null) {
            builder.onDismissCallback.onDismiss();
        }
    }

    public void makeProgress(MoveContentProgress moveContentProgress) {
        if (mProgressBar != null) {
            mProgressBar.setProgress(moveContentProgress.getCurrentCount());
            mTxtProgress.setText(String.valueOf((moveContentProgress.getCurrentCount() * 100) / moveContentProgress.getTotalCount()) + "%");
        }
    }

    public enum Action {
        POSITIVE,
        NEGATIVE,
        DISMISS
    }

    public interface Callback {
        void onClick(@NonNull EkStepGenericDialog dialog, @NonNull Action action);
    }

    public interface DismissCallback {
        void onDismiss();
    }

    public static class Builder {
        protected final Context context;
        protected int txtTitle;
        protected int txtDesc;
        protected String strDesc;
        protected int txtPositive;
        protected int txtNegative;
        protected Boolean showCheckBox;
        protected View customView = null;
        protected Callback onPositiveCallback = null;
        protected Callback onNegativeCallback = null;
        protected DismissCallback onDismissCallback = null;
        protected int progressLimit;
        protected boolean showProgressBar;

        public Builder(@NonNull Context context) {
            this.context = context;
        }

        public final Context getContext() {
            return context;
        }

        public Builder setCustomView(@LayoutRes int layoutRes) {
            LayoutInflater mInflator = LayoutInflater.from(this.context);
            return setCustomView(mInflator.inflate(layoutRes, null));
        }

        public Builder setCustomView(@NonNull View view) {
            if (view.getParent() != null && view.getParent() instanceof ViewGroup) {
                ((ViewGroup) view.getParent()).removeView(view);
            }

            this.customView = view;
            return this;
        }

        public Builder setTitle(@StringRes int message) {
            this.txtTitle = message;
            return this;
        }

        public Builder setDescription(@StringRes int desc) {
            this.txtDesc = desc;
            return this;
        }

        public Builder setDescription(String desc) {
            this.strDesc = desc;
            return this;
        }

        public Builder setPositiveButtonText(@StringRes int message) {
            this.txtPositive = message;

            return this;
        }

        public Builder setNegativeButtonText(@StringRes int message) {
            this.txtNegative = message;
            return this;
        }

        public Builder setCheckBox(boolean showMsg) {
            this.showCheckBox = showMsg;
            return this;
        }

        public Builder onPositiveClick(@NonNull Callback callback) {
            this.onPositiveCallback = callback;
            return this;
        }

        public Builder onNegativeClick(@NonNull Callback callback) {
            this.onNegativeCallback = callback;
            return this;
        }

        public Builder onDismiss(@NonNull DismissCallback callback) {
            this.onDismissCallback = callback;
            return this;
        }

        public Builder setProgressBar(boolean showProgressBar) {
            this.showProgressBar = showProgressBar;
            return this;
        }

        public Builder setProgressLimit(int progressLimit) {
            this.progressLimit = progressLimit;
            return this;
        }

        @UiThread
        public EkStepGenericDialog build() {
            return new EkStepGenericDialog(this);
        }

        @UiThread
        public EkStepGenericDialog show() {
            return showDialog();
        }

        @UiThread
        private EkStepGenericDialog showDialog() {
            EkStepGenericDialog dialog = build();
            dialog.show();
            return dialog;
        }

    }
}
