package org.ekstep.genie.ui.importarchive;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import org.ekstep.genie.R;
import org.ekstep.genie.activity.BaseActivity;
import org.ekstep.genie.base.BasePresenter;
import org.ekstep.genie.base.BaseView;
import org.ekstep.genie.base.IPresenterFactory;
import org.ekstep.genie.ui.importarchive.fileexplorer.FileExplorerFragment;
import org.ekstep.genie.util.ShowProgressDialog;

import java.io.File;
import java.util.List;

public class ImportArchiveActivity extends BaseActivity implements ImportArchiveContract.View, View.OnClickListener {

    private ImportArchivePresenter mImportArchivePresenter;
    private RecyclerView mRV_Importable_Files;
    private TextView mTxt_Header;
    private View mLayout_Importable_Files;
    private View mLayout_FileExplorer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_import_archive);

        mImportArchivePresenter = (ImportArchivePresenter) presenter;
        initView();
        setFragment(FileExplorerFragment.newInstance(getIntent().getExtras()), false);
    }

    private void initView() {
        mImportArchivePresenter.fetchBundleExtras(getIntent().getExtras());

        findViewById(R.id.btn_back).setOnClickListener(this);
        findViewById(R.id.txt_expand).setOnClickListener(this);
        mLayout_Importable_Files = findViewById(R.id.layout_importable_files);
        mLayout_FileExplorer = findViewById(R.id.fragment_container);

        mRV_Importable_Files = (RecyclerView) findViewById(R.id.rv_importable_files);
        mRV_Importable_Files.setLayoutManager(new LinearLayoutManager(this));
        mRV_Importable_Files.setHasFixedSize(true);

        mTxt_Header = (TextView) findViewById(R.id.txt_header);
        mImportArchivePresenter.setHeaderTitle();
        mImportArchivePresenter.fetchImportableFiles();
    }


    @Override
    protected IPresenterFactory getPresenterFactory() {
        return new IPresenterFactory() {
            @Override
            public BasePresenter create() {
                return new ImportArchivePresenter();
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
    public void showImportableFilesList(List<File> files) {
        mRV_Importable_Files.setAdapter(new ImportFileAdapter(files, mImportArchivePresenter));
    }

    @Override
    public void setHeaderText(int headerText) {
        mTxt_Header.setText(headerText);
    }


    @Override
    public void finishActivity() {
        finish();
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.btn_back) {
            mImportArchivePresenter.handleBackClick();

        } else if (i == R.id.txt_expand) {
            mLayout_Importable_Files.setVisibility(View.GONE);

        } else {
        }
    }


    @Override
    public void showProgressDialog(String message) {
        ShowProgressDialog.showProgressDialog(this, message);
    }

    @Override
    public void showImportableFilesList() {
        mRV_Importable_Files.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideImportableFilesList() {
        mRV_Importable_Files.setVisibility(View.GONE);
    }

    @Override
    public void showNoFileLayout() {
        findViewById(R.id.layout_no_files).setVisibility(View.VISIBLE);
    }

    @Override
    public void showNoFileMessage(int message) {
        ((TextView) findViewById(R.id.txt_no_files)).setText(message);
    }

}