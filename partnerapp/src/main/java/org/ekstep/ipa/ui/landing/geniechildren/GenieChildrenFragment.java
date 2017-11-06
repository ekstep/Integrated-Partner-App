package org.ekstep.ipa.ui.landing.geniechildren;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;

import org.ekstep.genie.base.BasePresenter;
import org.ekstep.genie.base.BaseView;
import org.ekstep.genie.base.IPresenterFactory;
import org.ekstep.genie.customview.SimpleDividerItemDecoration;
import org.ekstep.genie.fragment.BaseFragment;
import org.ekstep.genie.ui.landing.LandingActivity;
import org.ekstep.ipa.R;
import org.ekstep.ipa.adapter.ChildrenAdapter;
import org.ekstep.ipa.model.Student;

import java.util.List;


/**
 * @author vinayagasundar
 */

public class GenieChildrenFragment extends BaseFragment
        implements GenieChildrenContract.View {

    private GenieChildrenContract.Presenter mPresenter;


    private ProgressBar mLoadingProgressBar;

    private RecyclerView mChildrenRecyclerView;

    private Button mBrowseButton;

    private View mEmptyView;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mPresenter = (GenieChildrenContract.Presenter) presenter;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_genie_children, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mLoadingProgressBar = (ProgressBar) view.findViewById(R.id.loading_progress_bar);
        mChildrenRecyclerView = (RecyclerView) view.findViewById(R.id.genie_children_recycler_view);
        mBrowseButton = (Button) view.findViewById(R.id.btn_browse_lesson);
        mEmptyView = view.findViewById(R.id.empty_message_view);


        mBrowseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.launchGenieForSelectedStudent();
            }
        });

    }

    @Override
    public void onStart() {
        super.onStart();

        mPresenter.getAllGenieChildren();
    }

    @Override
    protected IPresenterFactory getPresenterFactory() {
        return new IPresenterFactory() {
            @Override
            public BasePresenter create() {
                return new GenieChildrenPresenter();
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
    public void setGenieChildListAdapter(List<Student> studentList,
                                         ChildrenAdapter.OnSelectedChildListener onSelectedChildListener) {
        mEmptyView.setVisibility(View.VISIBLE);

        ChildrenAdapter childrenAdapter = new ChildrenAdapter(studentList);
        childrenAdapter.setListener(onSelectedChildListener);
        mChildrenRecyclerView.setAdapter(childrenAdapter);
        mChildrenRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mChildrenRecyclerView.addItemDecoration(new SimpleDividerItemDecoration(getActivity()));
    }

    @Override
    public void showLoader() {
        mLoadingProgressBar.setVisibility(View.VISIBLE);
        mChildrenRecyclerView.setVisibility(View.GONE);
        mBrowseButton.setVisibility(View.GONE);
    }

    @Override
    public void hideLoader() {
        mLoadingProgressBar.setVisibility(View.GONE);
        mChildrenRecyclerView.setVisibility(View.VISIBLE);
        mBrowseButton.setVisibility(View.VISIBLE);
    }

    @Override
    public void showEmptyView() {
        mLoadingProgressBar.setVisibility(View.GONE);
        mChildrenRecyclerView.setVisibility(View.GONE);
        mBrowseButton.setVisibility(View.GONE);
        mEmptyView.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideEmptyView() {
        mEmptyView.setVisibility(View.GONE);
    }

    @Override
    public void enableBrowseLesson() {
        mBrowseButton.setEnabled(true);
    }

    @Override
    public void disableBrowseLesson() {
        mBrowseButton.setEnabled(false);
    }


    @Override
    public void showGenieHomeScreen() {
        Intent intent = new Intent(getActivity(), LandingActivity.class);
        startActivity(intent);
        getActivity().finish();
        // Activity animation from right to left.
        getActivity().overridePendingTransition(org.ekstep.genie.R.anim.slide_in_right, org.ekstep.genie.R.anim.slide_out_left);
    }
}
