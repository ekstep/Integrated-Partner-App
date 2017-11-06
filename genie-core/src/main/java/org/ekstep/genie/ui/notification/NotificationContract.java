package org.ekstep.genie.ui.notification;

import org.ekstep.genie.base.BasePresenter;
import org.ekstep.genie.base.BaseView;
import org.ekstep.genieservices.commons.bean.Notification;

import java.util.List;

/**
 * Created by shriharsh on 9/1/17.
 */

public class NotificationContract {

    public interface View extends BaseView {

        void initAdapter();

        void setNotificationAdapter();

        void setNotificationAdapterData(List<Notification> notifications);

        void notifyAdapterDataSetChanged();

        void hideEmptyNotificationLayout();

        void showEmptyNotificationLayout();

    }

    public interface Presenter extends BasePresenter {

        List<Notification> getAllNotificationList();
    }
}
