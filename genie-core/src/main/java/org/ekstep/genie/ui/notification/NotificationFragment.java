package org.ekstep.genie.ui.notification;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import org.ekstep.genie.CoreApplication;
import org.ekstep.genie.R;
import org.ekstep.genie.base.BasePresenter;
import org.ekstep.genie.base.BaseView;
import org.ekstep.genie.base.IPresenterFactory;
import org.ekstep.genie.fragment.BaseFragment;
import org.ekstep.genie.ui.landing.LandingActivity;
import org.ekstep.genie.util.LogUtil;
import org.ekstep.genie.util.Util;
import org.ekstep.genieservices.GenieService;
import org.ekstep.genieservices.async.NotificationService;
import org.ekstep.genieservices.commons.IResponseHandler;
import org.ekstep.genieservices.commons.bean.GenieResponse;
import org.ekstep.genieservices.commons.bean.Notification;

import java.util.List;


/**
 * Created by shriharsh on 9/1/17.
 */

public class NotificationFragment extends BaseFragment implements
        NotificationContract.View, NotificationAdapter.NotificationClickCallback {

    private static String TAG = NotificationFragment.class.getSimpleName();
    public NotificationContract.Presenter mPresenter;
    public NotificationAdapter mNotificationAdapter;
    private RecyclerView mNotificationRecyclerView;
    private View mLayout_Empty_Notification;
    private List<Notification> mNotifications;

    private NotificationService mNotificationService;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mNotificationService = GenieService.getAsyncService().getNotificationService();
        mPresenter = (NotificationContract.Presenter)presenter;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_notification, container, false);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initViews(view);

        ((LandingActivity) mActivity).setNotificationListener(new LandingActivity.INotify() {

            @Override
            public void onNotificationReceived() {
                mNotifications = mPresenter.getAllNotificationList();
            }
        });

        mNotifications = mPresenter.getAllNotificationList();
    }

    @Override
    protected IPresenterFactory getPresenterFactory() {
        return new IPresenterFactory() {
            @Override
            public BasePresenter create() {
                return new NotificationPresenter();
            }
        };
    }

    @Override
    protected String getTAG() {
        return TAG;
    }

    @Override
    protected BaseView getBaseView() {
        return this;
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    private void updateAllNotificationsStatusAsRead() {
        if (mNotifications != null && mNotifications.size() > 0) {
            for (Notification notification : mNotifications) {
                notification.setMsgid(-1);
                mNotificationService.updateNotification(notification, new IResponseHandler<Notification>() {
                    @Override
                    public void onSuccess(GenieResponse<Notification> genieResponse) {
                        LogUtil.e(TAG, "notification read");
                    }

                    @Override
                    public void onError(GenieResponse<Notification> genieResponse) {
                        LogUtil.e(TAG, "error: " + genieResponse.getErrorMessages().get(0));
                    }
                });
            }
            LocalBroadcastManager.getInstance(CoreApplication.getInstance()).sendBroadcast(Util.getRefreshNotificationsIntent());
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        ((LandingActivity) mActivity).setNotificationListener(null);
        updateAllNotificationsStatusAsRead();
//        if (mNotifications != null && mNotifications.size() > 0) {
//            for (Notification notification : mNotifications) {
//                notification.setMsgid(-1);
//                mNotificationService.updateNotification(notification, new IResponseHandler<Notification>() {
//                    @Override
//                    public void onSuccess(GenieResponse<Notification> genieResponse) {
//                        LogUtil.e(TAG, "notification read");
//                    }
//
//                    @Override
//                    public void onError(GenieResponse<Notification> genieResponse) {
//                        LogUtil.e(TAG, "error: " + genieResponse.getErrorMessages().get(0));
//                    }
//                });
//            }
//            LocalBroadcastManager.getInstance(CoreApplication.getInstance()).sendBroadcast(Util.getRefreshNotificationsIntent());
//        }
    }

    private void initViews(View view) {

        ImageView menuImage = (ImageView) view.findViewById(R.id.back_btn);
        menuImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((LandingActivity) mActivity).showHome(false);
            }
        });

        mLayout_Empty_Notification = view.findViewById(R.id.layout_no_notification);

        mNotificationRecyclerView = (RecyclerView) view.findViewById(R.id.rv_notification);
        mNotificationRecyclerView.setHasFixedSize(true);

        //To display the notification in vertical orientation
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        mNotificationRecyclerView.setLayoutManager(llm);
    }

    @Override
    public void setNotificationAdapter() {
        mNotificationRecyclerView.setAdapter(mNotificationAdapter);
    }

    @Override
    public void initAdapter() {
        mNotificationAdapter = new NotificationAdapter(getActivity(), this);
    }

    @Override
    public void setNotificationAdapterData(List<Notification> notificationList) {
        mNotificationAdapter.setData(notificationList);
    }

    @Override
    public void notifyAdapterDataSetChanged() {
        mNotificationAdapter.notifyDataSetChanged();
    }

    @Override
    public void hideEmptyNotificationLayout() {
        mLayout_Empty_Notification.setVisibility(View.GONE);
    }

    @Override
    public void showEmptyNotificationLayout() {
        mLayout_Empty_Notification.setVisibility(View.VISIBLE);
    }

    @Override
    public void onNotificationClicked() {
        updateAllNotificationsStatusAsRead();
    }

}
