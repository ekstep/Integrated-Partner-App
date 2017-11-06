package org.ekstep.genie.ui.importarchive.fileexplorer;

import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.ekstep.genie.R;
import org.ekstep.genie.base.BasePresenter;
import org.ekstep.genie.base.BaseView;
import org.ekstep.genie.base.IPresenterFactory;
import org.ekstep.genie.fragment.BaseFragment;
import org.ekstep.genie.util.Constant;
import org.ekstep.genie.util.FileHandler;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by swayangjit on 20/9/17.
 */

public class FileExplorerFragment extends BaseFragment implements FileExploreContract.View {

    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    private FilePagerAdapter mFilePagerAdapter;
    private FileExploreContract.Presenter mPresenter;

    public static FileExplorerFragment newInstance(Bundle bundle) {
        FileExplorerFragment fileExplorer = new FileExplorerFragment();
        fileExplorer.setArguments(bundle);
        return fileExplorer;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = (FileExplorePresenter) presenter;

    }

    @Override
    public android.view.View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_file_explorer, container, false);
        return view;
    }

    @Override
    public void onViewCreated(android.view.View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mPresenter = (FileExploreContract.Presenter) presenter;

        //Initializing the tab layout
        mTabLayout = (TabLayout) view.findViewById(R.id.tabLayout);
        mTabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        mViewPager = (ViewPager) view.findViewById(R.id.pager);

        boolean isProfile = getArguments().getBoolean(Constant.BUNDLE_KEY_IMPORT_PROFILE);
        List<Fragment> fragments = new ArrayList<>();
        //Primary storage
        fragments.add(getFileListFragment(Environment.getExternalStorageDirectory().getAbsolutePath(), isProfile));
        //Secondary storage
        fragments.add(getFileListFragment(FileHandler.getSecondaryStorageFilePath(), isProfile));

        List<String> tabNameList = new ArrayList<>();
        tabNameList.add(getString(R.string.title_fileexplorer_device_storage));
        tabNameList.add(FileHandler.isSecondaryStorageAvailable() ? getString(R.string.title_fileexplorer_external_storage) : getString(R.string.title_fileexplorer_root));

        FilePagerAdapter adapter = new FilePagerAdapter(getChildFragmentManager());
        adapter.setTitles(tabNameList);
        adapter.setFragments(fragments);

        mViewPager.setAdapter(adapter);
        mTabLayout.setupWithViewPager(mViewPager);

    }

    private FileListFragment getFileListFragment(String path, boolean isProfile) {
        return FileListFragment.newInstance(path, isProfile);
    }


    @Override
    protected IPresenterFactory getPresenterFactory() {
        return new IPresenterFactory() {
            @Override
            public BasePresenter create() {
                return new FileExplorePresenter();
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
    public void onBackButtonPressed() {

    }
}
