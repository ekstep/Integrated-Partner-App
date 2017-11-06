package org.ekstep.ipa.ui.addchild.childfilter;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import org.ekstep.genie.base.BasePresenter;
import org.ekstep.genie.base.BaseView;
import org.ekstep.genie.base.IPresenterFactory;
import org.ekstep.genie.fragment.BaseFragment;
import org.ekstep.ipa.R;
import org.ekstep.ipa.ui.addchild.AddChildCallback;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author vinayagasundar
 */

public class ChildFilterFragment extends BaseFragment
        implements ChildFilterContract.View, AdapterView.OnItemSelectedListener,
        View.OnClickListener {


    private ChildFilterPresenter mPresenter;


    private Spinner mDistrictSpinner;
    private Spinner mClusterSpinner;
    private Spinner mBlockSpinner;
    private Spinner mSchoolSpinner;
    private Spinner mClassSpinner;

    private Button mSearchButton;

    private List<String> mSchoolIds;

    private AddChildCallback mCallback;


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if (context instanceof AddChildCallback) {
            mCallback = (AddChildCallback) context;
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mPresenter = (ChildFilterPresenter) presenter;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_child_filter, container, false);
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mDistrictSpinner = (Spinner) view.findViewById(R.id.spinner_district);
        mClusterSpinner = (Spinner) view.findViewById(R.id.spinner_cluster);
        mBlockSpinner = (Spinner) view.findViewById(R.id.spinner_block);
        mSchoolSpinner = (Spinner) view.findViewById(R.id.spinner_school);
        mClassSpinner = (Spinner) view.findViewById(R.id.spinner_class);

        mSearchButton = (Button) view.findViewById(R.id.btn_search_child);


        mDistrictSpinner.setOnItemSelectedListener(this);
        mClusterSpinner.setOnItemSelectedListener(this);
        mBlockSpinner.setOnItemSelectedListener(this);
        mSchoolSpinner.setOnItemSelectedListener(this);
        mClassSpinner.setOnItemSelectedListener(this);

        mSearchButton.setOnClickListener(this);

        mPresenter.getAllDistrict();

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String selectedValue = parent.getSelectedItem().toString();

        int i = parent.getId();
        if (i == R.id.spinner_district) {
            mPresenter.getAllBlockForDistrict(selectedValue);

        } else if (i == R.id.spinner_cluster) {
            mPresenter.getAllSchoolForCluster(selectedValue);

        } else if (i == R.id.spinner_block) {
            mPresenter.getAllClusterForBlock(selectedValue);

        } else if (i == R.id.spinner_school) {
            if (mSchoolIds != null && mSchoolIds.size() > 0) {
                String schoolId = mSchoolIds.get(position);
                mPresenter.getClassForSchool(schoolId, parent.getSelectedItem().toString());
            }

        } else if (i == R.id.spinner_class) {
            mPresenter.selectedClass(selectedValue);

        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_search_child) {
            mPresenter.handleSearchClick();
        }
    }

    @Override
    protected IPresenterFactory getPresenterFactory() {
        return new IPresenterFactory() {
            @Override
            public BasePresenter create() {
                return new ChildFilterPresenter();
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


    @Override
    public void showDistrictData(List<String> districtData, String selectedValue) {
        setUpSpinner(mDistrictSpinner, districtData, selectedValue);
    }

    @Override
    public void showBlockData(List<String> blockData, String selectedValue) {
        setUpSpinner(mBlockSpinner, blockData, selectedValue);
    }

    @Override
    public void showClusterData(List<String> clusterData, String selectedValue) {
        setUpSpinner(mClusterSpinner, clusterData, selectedValue);
    }

    @Override
    public void showSchoolData(HashMap<String, String> schoolData, String selectedId) {
        if (schoolData != null) {
            List<String> schoolNames = new ArrayList<>();
            schoolNames.add("Please select School");
            schoolNames.addAll(schoolData.values());
            mSchoolIds = new ArrayList<>();
            mSchoolIds.add("0000");
            mSchoolIds.addAll(schoolData.keySet());

            String selectedValue = null;

            if (selectedId != null) {
                int position = mSchoolIds.indexOf(selectedId);
                if (position >= 0) {
                    selectedValue = schoolNames.get(position);
                }
            }

            setUpSpinner(mSchoolSpinner, schoolNames, selectedValue);
        }
    }

    @Override
    public void showClassData(List<String> classData, String selectedValue) {
        setUpSpinner(mClassSpinner, classData, selectedValue);
    }

    @Override
    public void enableSearchButton() {
        mSearchButton.setEnabled(true);
    }

    @Override
    public void disableSearchButton() {
        mSearchButton.setEnabled(false);
    }

    @Override
    public void showChildSearchScreen(String schoolId, String klass) {
        if (mCallback != null) {
            mCallback.onSelectedFilter(schoolId, klass);
        }
    }

    private void setUpSpinner(Spinner spinner, List<String> data, String defaultValue) {
       if (data != null && data.size() > 0) {
           ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(getActivity(),
                   android.R.layout.simple_spinner_item, data);

           arrayAdapter.setDropDownViewResource(android.R.layout.simple_list_item_1);
           spinner.setAdapter(arrayAdapter);

           if (defaultValue != null) {
               int position = data.indexOf(defaultValue);
               spinner.setSelection(position);
           }
       }
    }
}
