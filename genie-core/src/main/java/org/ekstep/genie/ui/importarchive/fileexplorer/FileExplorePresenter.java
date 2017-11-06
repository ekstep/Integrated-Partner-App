package org.ekstep.genie.ui.importarchive.fileexplorer;

import android.content.Context;

import org.ekstep.genie.base.BaseView;

/**
 * Created on 12/22/2016.
 *
 * @author Kannappan
 */
public class FileExplorePresenter implements FileExploreContract.Presenter {

    private Context mContext;

    private FileExploreContract.View mImportView;

    public FileExplorePresenter() {

    }


    @Override
    public void onBackButtonPressed() {
        mImportView.onBackButtonPressed();
    }

    @Override
    public void bindView(BaseView view, Context context) {
        mImportView = (FileExploreContract.View) view;
        mContext = context;
    }

    @Override
    public void unbindView() {
        mImportView = null;
        mContext = null;
    }
}
