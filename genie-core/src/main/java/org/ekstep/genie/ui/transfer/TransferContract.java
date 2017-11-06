package org.ekstep.genie.ui.transfer;

import android.content.Context;
import android.content.Intent;

import org.ekstep.genie.base.BasePresenter;
import org.ekstep.genie.base.BaseView;
import org.ekstep.genieservices.commons.bean.Content;
import org.ekstep.genieservices.commons.bean.Profile;

import java.util.List;
import java.util.Map;

/**
 * Created on 12/23/2016.
 *
 * @author Kannappan
 */
public interface TransferContract {

    interface View extends BaseView {

        void hideContentRecyclerView();

        void checkContentText(boolean isChecked);

        void checkTelemetryText(boolean isChecked);

        void checkProfileText(boolean isChecked);

        void hideProfileRecyclerView();

        boolean isTelemetryTextChecked();

        boolean isContentChecked();

        boolean isProfileChecked();

        void showContentRecyclerView();

        void showContentList(List<Content> gameList);

        void profileToggleSelected(String uid);

        void contentToggleSelected(String identifier);

        boolean isProfileItemsSelected();

        void startProfileExport(Map<String, String> values);

        boolean isContentItemsSelected();

        List<String> getContentSelectedItems();

        void showProfileRecyclerView();

        void updateProfileAdapter(List<Profile> profileList);

        void showCustomToast(String message);

        void startContentExportIntent(Map<String, String> values, List<String> identifierList);

        void clearContentAdapter();

        void clearProfileAdapter();
    }

    interface Presenter extends BasePresenter {

        void export();

        void selectTelemetry();

        void selectContent();

        void selectProfile();

        void onItemClicked(int index);

        void onReceiveBroadCast(Context context, Intent intent);

        void processItemClick(int position);

        void manageContentExportSuccess(String exportSuccess);

    }
}
