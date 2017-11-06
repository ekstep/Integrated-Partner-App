package org.ekstep.ipa.ui.managechild;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import org.ekstep.genie.activity.BaseActivity;
import org.ekstep.genie.base.BasePresenter;
import org.ekstep.genie.base.BaseView;
import org.ekstep.genie.base.IPresenterFactory;
import org.ekstep.genie.customview.DatePickerDialogFragment;
import org.ekstep.genie.ui.landing.LandingActivity;
import org.ekstep.genie.util.DeviceUtility;
import org.ekstep.genie.util.ThemeUtility;
import org.ekstep.ipa.R;
import org.ekstep.ipa.model.Student;

/**
 * @author vinayagasundar
 */

public class ManageChildActivity extends BaseActivity implements ManageChildContract.View,
        View.OnClickListener {

    private EditText mNameText;
    private EditText mStudentIdText;
    private EditText mDobText;
    private EditText mFatherNameText;
    private EditText mMotherNameText;

    private Button mSaveButton;

    private Spinner mGenderSpinner;

    private ManageChildContract.Presenter mPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_managa_child);

        final View decorView = getWindow().getDecorView();
        decorView.setOnSystemUiVisibilityChangeListener(new View.OnSystemUiVisibilityChangeListener() {
            @Override
            public void onSystemUiVisibilityChange(int visibility) {
                DeviceUtility.displayFullScreen(ManageChildActivity.this);
            }
        });

        decorView.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                DeviceUtility.displayFullScreen(ManageChildActivity.this);
            }
        });

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        if (toolbar != null) {
            setSupportActionBar(toolbar);

            if (getSupportActionBar() != null) {
                getSupportActionBar().setTitle("Child Details");

                Drawable backIcon = ThemeUtility.getDrawable(this, new int[]{R.attr.menuBackIcon});

                getSupportActionBar().setHomeAsUpIndicator(backIcon);
            }
        }


        mPresenter = (ManageChildContract.Presenter) presenter;
        mPresenter.start();

        initViews();
    }

    @Override
    protected IPresenterFactory getPresenterFactory() {
        return new IPresenterFactory() {
            @Override
            public BasePresenter create() {
                return new ManageChildPresenter();
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
    public void showCalendar(DatePickerDialog.OnDateSetListener onDateSetListener) {
        DatePickerDialogFragment datePickerDialogFragment = new DatePickerDialogFragment();
        datePickerDialogFragment.show(getSupportFragmentManager(), "date-picker");
        datePickerDialogFragment.setDatePickerListener(onDateSetListener);
    }

    @Override
    public void showGenieHomeScreen() {
        Intent intent = new Intent(this, LandingActivity.class);
        startActivity(intent);
    }

    @Override
    public void displayDob(String date) {
        mDobText.setText(date);
    }

    @Override
    public void enableSaveButton() {
        mSaveButton.setEnabled(true);
    }

    @Override
    public void disableSaveButton() {
        mSaveButton.setEnabled(false);
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.btn_save_child) {
            if (!isValid()) {
                Toast.makeText(this, "Please fill all the value", Toast.LENGTH_SHORT).show();
                return;
            }

            Student student = new Student();
            student.setStudentId(mStudentIdText.getText().toString());
            student.setName(mNameText.getText().toString());
            student.setGender(mGenderSpinner.getSelectedItem().toString());
            student.setFatherName(mFatherNameText.getText().toString());
            student.setMotherName(mMotherNameText.getText().toString());

            mPresenter.saveChild(student);

        } else if (i == R.id.dob_txt) {
            mPresenter.handleCalenderIconClick();

        }
    }

    private boolean isValid() {
        return !TextUtils.isEmpty(mStudentIdText.getText())
                && !TextUtils.isEmpty(mNameText.getText())
                && !TextUtils.isEmpty(mGenderSpinner.getSelectedItem().toString())
                && !TextUtils.isEmpty(mFatherNameText.getText())
                && !TextUtils.isEmpty(mMotherNameText.getText());

    }

    private void initViews() {
        mNameText = (EditText) findViewById(R.id.name_txt);
        mStudentIdText = (EditText) findViewById(R.id.student_id_txt);
        mDobText = (EditText) findViewById(R.id.dob_txt);
        mFatherNameText = (EditText) findViewById(R.id.father_name_txt);
        mMotherNameText = (EditText) findViewById(R.id.mother_name_txt);

        mGenderSpinner = (Spinner) findViewById(R.id.gender_spinner);

        mSaveButton = (Button) findViewById(R.id.btn_save_child);

        if (mSaveButton != null) {
            mSaveButton.setOnClickListener(this);
        }

        mDobText.setOnClickListener(this);

        enableSaveButton();
    }
}
