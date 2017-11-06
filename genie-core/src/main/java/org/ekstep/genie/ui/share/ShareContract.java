package org.ekstep.genie.ui.share;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import org.ekstep.genie.base.BasePresenter;
import org.ekstep.genie.base.BaseView;
import org.ekstep.genie.model.DisplayResolveInfo;

import java.util.List;

/**
 * Created by Kannappan on 12/26/2016.
 */

public interface ShareContract {
    interface View extends BaseView {
        void displayFileShareView(List<String> identifierList, String contentName, String screenName);

        void displayTelemetryShareView(List<String> identifierList, String contentName, String screenName);

        void displayProfileShareView(List<String> identifierList, String contentName, String screenName);

        void displayGenieConfigShareView();

        void displayGenieShareView();

        void displayLinkShareView(List<String> identifierList, String contentName, String screenName);

        void displayEcarLinkShareView(List<String> identifierList, String contentName, String screenName);
    }

    interface Presenter extends BasePresenter {
        Intent getEcarShareIntent(List<String> identifierList, String contentName, String screenName);

        Intent getLinkShareIntent(List<String> identifierList, String contentName, String screenName);

        Intent getProfileShareIntent(List<String> identifierList, String contentName, String screenName);

        Intent getShareIntent(List<String> identifierList, String contentName, String screenName);

        Intent getGenieConfigurationsShareIntent(boolean isText);

        Intent getGenieShareIntent(boolean isLink);

        List<DisplayResolveInfo> getResolverListForShareIntent(Context context, Intent shareIntent);

        void handleAndPopulateShareOptions(Bundle bundle);
    }
}