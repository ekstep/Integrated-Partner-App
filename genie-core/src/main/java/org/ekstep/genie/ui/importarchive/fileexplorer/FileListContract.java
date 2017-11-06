package org.ekstep.genie.ui.importarchive.fileexplorer;

import android.os.Bundle;

import org.ekstep.genie.base.BasePresenter;
import org.ekstep.genie.base.BaseView;
import org.ekstep.genie.model.FileItem;

import java.util.List;

/**
 * Created by Kannappan on 12/22/2016.
 */
public interface FileListContract {

    interface View extends BaseView {

        void updateAdapter(List<FileItem> items);

        void showProgressDialog(String message);

    }

    interface Presenter extends BasePresenter {
        void setBundle(Bundle bundle);

        void loadFiles();

        void handleItemClick(int position);

    }
}
