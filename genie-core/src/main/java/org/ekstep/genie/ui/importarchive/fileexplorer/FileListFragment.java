package org.ekstep.genie.ui.importarchive.fileexplorer;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import org.ekstep.genie.R;
import org.ekstep.genie.base.BasePresenter;
import org.ekstep.genie.base.BaseView;
import org.ekstep.genie.base.IPresenterFactory;
import org.ekstep.genie.callback.IMenuItemClick;
import org.ekstep.genie.fragment.BaseFragment;
import org.ekstep.genie.model.FileItem;
import org.ekstep.genie.util.Constant;
import org.ekstep.genie.util.ShowProgressDialog;

import java.util.List;


public class FileListFragment extends BaseFragment implements AdapterView.OnItemClickListener, FileListContract.View {

    private TextView mFolderNameTextView;
    private RecyclerView mListView;
    private FileListContract.Presenter mPresenter;

    public static FileListFragment newInstance(Bundle bundle) {
        FileListFragment fragment = new FileListFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    public static FileListFragment newInstance(String filePath, boolean isProfile) {
        Bundle bundle = new Bundle();
        bundle.putString(Constant.BUNDLE_KEY_STORAGE_FILE_PATH, filePath);
        bundle.putBoolean(Constant.BUNDLE_KEY_IMPORT_PROFILE, isProfile);
        FileListFragment fragment = new FileListFragment();
        fragment.setArguments(bundle);
        return fragment;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = (FileListPresenter) presenter;
        mPresenter.setBundle(getArguments());
    }

    @Override
    public android.view.View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_file_list, container, false);
        return view;
    }

    @Override
    public void onViewCreated(android.view.View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mListView = (RecyclerView) view.findViewById(R.id.rv_files);
        mListView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mListView.setHasFixedSize(true);

        mPresenter.loadFiles();

    }

    @Override
    protected IPresenterFactory getPresenterFactory() {
        return new IPresenterFactory() {
            @Override
            public BasePresenter create() {
                return new FileListPresenter();
            }
        };
    }

    @Override
    protected String getTAG() {
        return this.toString();
    }

    @Override
    protected BaseView getBaseView() {
        return this;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, android.view.View view, int position, long id) {
        mPresenter.handleItemClick(position);
    }

    @Override
    public void updateAdapter(List<FileItem> items) {
        mListView.setAdapter(new FileAdapter(items, new IMenuItemClick() {
            @Override
            public void onMenuItemClick(int position) {
                mPresenter.handleItemClick(position);
            }
        }));
    }

    @Override
    public void showProgressDialog(String message) {
        ShowProgressDialog.showProgressDialog(getActivity(), message);
    }

}
