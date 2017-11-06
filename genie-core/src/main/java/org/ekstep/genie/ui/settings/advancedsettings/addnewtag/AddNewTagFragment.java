package org.ekstep.genie.ui.settings.advancedsettings.addnewtag;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import org.ekstep.genie.R;
import org.ekstep.genie.base.BasePresenter;
import org.ekstep.genie.base.BaseView;
import org.ekstep.genie.base.IPresenterFactory;
import org.ekstep.genie.customview.DatePickerDialogFragment;
import org.ekstep.genie.fragment.BaseFragment;
import org.ekstep.genie.util.DeviceUtility;
import org.ekstep.genie.util.KeyboardUtil;
import org.ekstep.genie.util.TagValidator;
import org.ekstep.genie.util.Util;
import org.ekstep.genieservices.commons.bean.Tag;
import org.ekstep.genieservices.commons.utils.StringUtil;

public class AddNewTagFragment extends BaseFragment implements AddNewTagContract.View, OnClickListener, KeyboardUtil.IKeyboardListener {

    public static final int ID_DIALOG_START_DATE = 1;
    public static final int ID_DIALOG_END_DATE = 2;
    private static final String KEY_TAG_DATA = "tag_data";
    private TextView mStartDateTv = null;
    private TextView mEndDateTv = null;
    private EditText mProgramCodeEt = null;
    private EditText mDescriptionEt = null;
    private AddNewTagPresenter mPresenter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = (AddNewTagPresenter) presenter;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_add_new_tag, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews(view);
        KeyboardUtil.setKeyboardListener(getActivity(), this);

        if (getArguments() != null) {
            Tag tag = (Tag) getArguments().getSerializable(KEY_TAG_DATA);
            setData(tag);
        }
    }

    private void setData(Tag tag) {
        mProgramCodeEt.setKeyListener(null);
        mProgramCodeEt.setText(tag.getName());
        mDescriptionEt.setText(tag.getDescription());
        mStartDateTv.setText(tag.getStartDate());
        mEndDateTv.setText(tag.getEndDate());
    }

    @Override
    protected IPresenterFactory getPresenterFactory() {
        return new IPresenterFactory() {
            @Override
            public BasePresenter create() {
                return new AddNewTagPresenter();
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
        view.findViewById(R.id.layout_start_date).setOnClickListener(this);
        view.findViewById(R.id.layout_end_date).setOnClickListener(this);
        view.findViewById(R.id.layout_save).setOnClickListener(this);

        mStartDateTv = (TextView) view.findViewById(R.id.txt_start_date);
        mEndDateTv = (TextView) view.findViewById(R.id.txt_end_date);
        mDescriptionEt = (EditText) view.findViewById(R.id.edt_tag_description);
        mProgramCodeEt = (EditText) view.findViewById(R.id.edt_tag_name);

        TextView txt_header = (TextView) getActivity().findViewById(R.id.txt_header);
        txt_header.setText(getResources().getText(R.string.title_tag_add_new));
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.layout_start_date) {
            showDialog(ID_DIALOG_START_DATE);

        } else if (i == R.id.layout_end_date) {
            showDialog(ID_DIALOG_END_DATE);

        } else if (i == R.id.layout_save) {
            String startDate = mStartDateTv.getText().toString();
            String endDate = mEndDateTv.getText().toString();

            Tag tag = new Tag(mProgramCodeEt.getText().toString(), mDescriptionEt.getText().toString(),
                    TextUtils.isEmpty(startDate) ? null : startDate,
                    (TextUtils.isEmpty(endDate)) ? null : endDate);

            mPresenter.handleTagOperations(getActivity(), getArguments(), tag);


        } else {
        }
    }


    public boolean validateTagData() {
        if (TextUtils.isEmpty(mProgramCodeEt.getText().toString())) {
            mProgramCodeEt.requestFocus();
            mProgramCodeEt.setError(getResources().getString(R.string.error_tag_cant_be_empty));
        } else if (mProgramCodeEt.getText().toString().length() < 6
                || (mProgramCodeEt.getText().toString().length() > 12)) {
            mProgramCodeEt.requestFocus();
            mProgramCodeEt.setError(getResources().getString(R.string.error_tag_limit));
        } else if (!Util.isAlphaNumeric(mProgramCodeEt.getText().toString())) {
            mProgramCodeEt.requestFocus();
            mProgramCodeEt.setError(getResources().getString(R.string.error_tag_should_be_alpha_numeric));
        } else if (StringUtil.isNullOrEmpty(mStartDateTv.getText().toString())) {   // Start date
            mStartDateTv.requestFocus();
            mStartDateTv.setError(getResources().getString(R.string.error_tag_start_date_empty));
            Util.showCustomToast(R.string.error_tag_start_date_empty);
        } else if (StringUtil.isNullOrEmpty(mEndDateTv.getText().toString())) { // End date
            mEndDateTv.requestFocus();
            mEndDateTv.setError(getResources().getString(R.string.error_tag_end_date_empty));
            Util.showCustomToast(R.string.error_tag_end_date_empty);
        } else if (TagValidator.checkStartEndDate(mStartDateTv.getText().toString(), mEndDateTv.getText().toString())) {
            Util.showCustomToast(R.string.error_tag_invalid_date_range);
        } else {
            return true;
        }
        return false;
    }

    @Override
    public void finishActivity() {
        getActivity().finish();
    }

    /**
     * Show date picker dialog
     *
     * @param id
     */
    private void showDialog(final int id) {
        DatePickerDialogFragment datePickerDialog = new DatePickerDialogFragment();
        datePickerDialog.setDatePickerListener(new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                if (id == ID_DIALOG_START_DATE) {
                    setStartDate(year, monthOfYear, dayOfMonth);
                } else if (id == ID_DIALOG_END_DATE) {
                    setEndDate(year, monthOfYear, dayOfMonth);
                }
            }
        });
        datePickerDialog.show(getActivity().getSupportFragmentManager(),
                DatePickerDialogFragment.class.getSimpleName());
    }

    public void setStartDate(int year, int month, int day) {
        month = month + 1;
        if (month < 10) {
            mStartDateTv.setText(year + "-0" + (month) + "-" + day);
        } else {
            mStartDateTv.setText(year + "-" + (month) + "-" + day);
        }

        mStartDateTv.setError(null);
    }

    public void setEndDate(int year, int month, int day) {
        month = month + 1;
        if (month < 10) {
            mEndDateTv.setText(year + "-0" + (month) + "-" + day);
        } else {
            mEndDateTv.setText(year + "-" + (month) + "-" + day);
        }

        mEndDateTv.setError(null);
    }

    @Override
    public void onKeyboardVisible() {
        // Do nothing.
    }

    @Override
    public void onKeyboardHide() {
        if (getActivity() != null) {
            DeviceUtility.displayFullScreenAfterCancelEditKey(getActivity());
        }
    }

}
