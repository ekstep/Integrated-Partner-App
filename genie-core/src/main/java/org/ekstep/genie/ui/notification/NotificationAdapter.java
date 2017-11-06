package org.ekstep.genie.ui.notification;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import org.ekstep.genie.CoreApplication;
import org.ekstep.genie.R;
import org.ekstep.genie.ui.splash.SplashActivity;
import org.ekstep.genie.util.Constant;
import org.ekstep.genie.util.Util;
import org.ekstep.genieservices.GenieService;
import org.ekstep.genieservices.async.NotificationService;
import org.ekstep.genieservices.commons.IResponseHandler;
import org.ekstep.genieservices.commons.bean.GenieResponse;
import org.ekstep.genieservices.commons.bean.Notification;

import java.util.ArrayList;
import java.util.List;

/**
 * Adapter for the notification screen.
 * <p>
 * Created by Sneha on 1/30/2017.
 */

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.ViewHolder> {
    private static final String TAG = NotificationAdapter.class.getSimpleName();
    private Context mContext;
    private List<Notification> mValues = new ArrayList<Notification>();
    private NotificationService mNotificationService;
    private NotificationClickCallback mCallback = null;

    public NotificationAdapter(Context context, NotificationClickCallback callback) {
        mContext = context;
        mCallback = callback;
        mNotificationService = GenieService.getAsyncService().getNotificationService();
    }

    public void setData(List<Notification> values) {
        mValues.clear();
        if (values != null) {

            mValues.addAll(values);
        }
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemLayoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_notification_item, null);
        ViewHolder viewHolder = new ViewHolder(itemLayoutView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, final int position) {
        final Notification data = mValues.get(position);
//        LogUtil.d(TAG, "data " + data.getTitle() + ", " + data.getTime() + ", " + data.getDisplayTime());

//        LogUtil.d(TAG, "data.getMsg() " + data.getMsg());

        viewHolder.mNotificationHeader.setText(data.getTitle());
        viewHolder.mNotificationBody.setText(data.getMsg());
        final int msgId = data.getMsgid();

        if (data.isRead() == 1) {
            viewHolder.mNotificationHeader.setTypeface(null, Typeface.NORMAL);
        }

        viewHolder.mDeleteNotificationImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mNotificationService.deleteNotification(msgId, new IResponseHandler<Void>() {
                    @Override
                    public void onSuccess(GenieResponse<Void> genieResponse) {
                        mValues.remove(position);
                        notifyItemRemoved(position);
                        notifyItemRangeChanged(position, mValues.size());
                        notifyDataSetChanged();
                        if (mValues != null && mValues.size() == 0) {
                            LocalBroadcastManager.getInstance(CoreApplication.getInstance()).sendBroadcast(Util.getRefreshNotificationsIntent());
                        }
                    }

                    @Override
                    public void onError(GenieResponse<Void> genieResponse) {
                        Util.showCustomToast(R.string.error_notification_delete_failed);
                    }
                });
            }
        });

        viewHolder.mParent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, SplashActivity.class);
                intent.putExtra(Constant.BUNDLE_KEY_NOTIFICATION_DATA_MODEL, data);
                intent.putExtra(Constant.BUNDLE_FROM_NOTIFICATION_LIST, true);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                mContext.startActivity(intent);

                if (mCallback != null)
                    mCallback.onNotificationClicked();
            }
        });

    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public interface NotificationClickCallback {
        void onNotificationClicked();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView mNotificationHeader;
        private TextView mNotificationBody;
        private ImageView mDeleteNotificationImg;
        private CardView mParent;

        public ViewHolder(View view) {
            super(view);
            mNotificationHeader = (TextView) view.findViewById(R.id.tv_notification_header);
            mNotificationBody = (TextView) view.findViewById(R.id.tv_notification_body);
            mDeleteNotificationImg = (ImageView) view.findViewById(R.id.delete_img);
            mParent = (CardView) view.findViewById(R.id.parent);
        }
    }

}
