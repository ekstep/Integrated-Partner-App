package org.ekstep.ipa.ui.addchild.searchchild;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import org.ekstep.genie.base.BasePresenter;
import org.ekstep.genie.base.BaseView;
import org.ekstep.genie.base.IPresenterFactory;
import org.ekstep.genie.customview.SimpleDividerItemDecoration;
import org.ekstep.genie.fragment.BaseFragment;
import org.ekstep.ipa.R;
import org.ekstep.ipa.adapter.ChildrenAdapter;
import org.ekstep.ipa.model.Student;
import org.ekstep.ipa.ui.managechild.ManageChildActivity;

import java.util.List;


/**
 * @author vinayagasundar
 */

public class SearchChildFragment extends BaseFragment
        implements SearchChildContract.View {


    private static final String BUNDLE_SCHOOL_ID = "school_id";
    private static final String BUNDLE_CLASS = "class";


    private SearchChildContract.Presenter mPresenter;


    private RecyclerView mSearchResultRecyclerView;
    private Button mBrowseButton;
    private Button mCreateChildButton;

    private ChildrenAdapter mAdapter;



    public static Fragment newInstance(String schoolId, String klass) {
        Fragment fragment = new SearchChildFragment();

        Bundle args = new Bundle();
        args.putString(BUNDLE_SCHOOL_ID, schoolId);
        args.putString(BUNDLE_CLASS, klass);

        fragment.setArguments(args);

        return fragment;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mPresenter = (SearchChildContract.Presenter) presenter;

        if (getArguments() != null) {
            String schoolId = getArguments().getString(BUNDLE_SCHOOL_ID, null);
            String klass = getArguments().getString(BUNDLE_CLASS, null);

            mPresenter.init(schoolId, klass);
        }
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_search_child, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mSearchResultRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_search);
        mBrowseButton = (Button) view.findViewById(R.id.btn_browse_lesson);

        mBrowseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.handleAddChild();
            }
        });

        mCreateChildButton = (Button) view.findViewById(R.id.create_child_btn);
        mCreateChildButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.showCreateChild();
            }
        });

        SearchView searchText = (SearchView) view.findViewById(R.id.txt_search);
        searchText.setIconifiedByDefault(false);
        searchText.setIconified(false);

        searchText.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if (query.length() >= 3) {
                    mPresenter.searchOnChildren(query);
                } else if (query.length() == 0) {
                    mPresenter.getAllChildren();
                }
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (newText.length() >= 3) {
                    mPresenter.searchOnChildren(newText);
                } else if (newText.length() == 0) {
                    mPresenter.getAllChildren();
                }
                return true;
            }
        });

        mPresenter.getAllChildren();

    }

    @Override
    protected IPresenterFactory getPresenterFactory() {
        return new IPresenterFactory() {
            @Override
            public BasePresenter create() {
                return new SearchChildPresenter();
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
    public void showAllChildren(List<Student> studentList,
                                ChildrenAdapter.OnSelectedChildListener onSelectedChildListener) {
        if (mAdapter == null) {
            mAdapter = new ChildrenAdapter(studentList);

            mSearchResultRecyclerView.setAdapter(mAdapter);
            mSearchResultRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
            mSearchResultRecyclerView
                    .addItemDecoration(new SimpleDividerItemDecoration(getActivity()));


            mAdapter.setListener(onSelectedChildListener);
            mAdapter.enableMultiSelect(true);
        } else {
            mAdapter.updateStudentList(studentList);
        }
    }

    @Override
    public void updateChildren(List<Student> studentList) {
        if (mAdapter != null) {
            mAdapter.updateStudentList(studentList);
        }
    }

    @Override
    public void enableAddChild() {
        mBrowseButton.setEnabled(true);
    }

    @Override
    public void disableAddChild() {
        mBrowseButton.setEnabled(false);
    }

    @Override
    public void showCreateChild() {
        mCreateChildButton.setVisibility(View.VISIBLE);
        mSearchResultRecyclerView.setVisibility(View.GONE);
    }

    @Override
    public void hideCreateChild() {
        mCreateChildButton.setVisibility(View.GONE);
        mSearchResultRecyclerView.setVisibility(View.VISIBLE);
    }

    @Override
    public void showCreateChildScreen() {
        Intent intent = new Intent(getActivity(), ManageChildActivity.class);
        startActivity(intent);
    }
}
