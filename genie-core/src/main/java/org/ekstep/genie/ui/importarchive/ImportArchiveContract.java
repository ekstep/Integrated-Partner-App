package org.ekstep.genie.ui.importarchive;

import android.os.Bundle;

import org.ekstep.genie.base.BasePresenter;
import org.ekstep.genie.base.BaseView;

import java.io.File;
import java.util.List;

/**
 * Created on 12/22/2016.
 *
 * @author Kannappan
 */
public interface ImportArchiveContract {

    interface View extends BaseView {

        void showImportableFilesList(List<File> files);

        void setHeaderText(int header);

        void finishActivity();

        void showProgressDialog(String message);

        void showImportableFilesList();

        void hideImportableFilesList();

        void showNoFileLayout();

        void showNoFileMessage(int message);
    }

    interface Presenter extends BasePresenter {

        void fetchImportableFiles();

        void setHeaderTitle();

        void handleBackClick();

        void handleFileExplorerClick();

        void importFile(String filePath);

        boolean isProfile();

        void fetchBundleExtras(Bundle arguments);
    }

}
