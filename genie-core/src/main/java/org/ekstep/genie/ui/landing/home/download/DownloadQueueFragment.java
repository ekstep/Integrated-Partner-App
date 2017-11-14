package org.ekstep.genie.ui.landing.home.download;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;

import org.ekstep.genie.R;
import org.ekstep.genie.model.DownloadQueueItem;
import org.ekstep.genieservices.commons.bean.ContentImportResponse;
import org.ekstep.genieservices.commons.bean.DownloadProgress;
import org.ekstep.genieservices.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;
import java.util.Map;

/**
 * Created by Indraja Machani on 8/18/2017.
 */

public class DownloadQueueFragment extends DialogFragment implements DownloadQueueContract.View, View.OnClickListener {
    private static String TAG = DownloadQueueFragment.class.getSimpleName();
    private RecyclerView mRvDownloadQueue;
    private LinearLayoutManager mLinearLayoutManager;
    private DownloadQueueContract.Presenter mPresenter;
    private DownloadQueueAdapter mDownloadQueueAdapter;
    private TextView mTvPauseDownload;
    private OnDismissListener onDismissListener;
    private boolean mIsDownloadCancelled;

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onDownloadProgress(DownloadProgress downloadProgress) throws InterruptedException {
        mPresenter.refreshAdapterAndUpdatePB(downloadProgress);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onContentImportResponse(ContentImportResponse contentImportResponse) throws InterruptedException {
        mPresenter.manageImportResponse(contentImportResponse);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = new DownloadQueuePresenter(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.dialog_download_queue, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.registerSubscriber(this);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        // request a window without the title
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);

        //Set the dialog to immersive
        dialog.getWindow().getDecorView().setSystemUiVisibility(getActivity().getWindow().getDecorView().getSystemUiVisibility());
        return dialog;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getDialog().getWindow().setLayout(RecyclerView.LayoutParams.MATCH_PARENT, RecyclerView.LayoutParams.MATCH_PARENT);
        initViews(view);
        mPresenter.fetchDownloadQueue();
    }

    private void initViews(View view) {
        mRvDownloadQueue = (RecyclerView) view.findViewById(R.id.rv_download_queue);
        mLinearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        mRvDownloadQueue.setLayoutManager(mLinearLayoutManager);
        mTvPauseDownload = (TextView) view.findViewById(R.id.tv_pause_download);
        mTvPauseDownload.setOnClickListener(this);
        view.findViewById(R.id.tv_download_close).setOnClickListener(this);
    }

    @Override
    public void setDownloadQueueAdapter(List<DownloadQueueItem> downloadQueueItemList) {
        if (mDownloadQueueAdapter != null) {
            mDownloadQueueAdapter = null;
        }
        mDownloadQueueAdapter = new DownloadQueueAdapter(getActivity(), downloadQueueItemList, mPresenter);
        mRvDownloadQueue.setAdapter(mDownloadQueueAdapter);
    }

    @Override
    public void refreshAdapter(List<DownloadQueueItem> downloadQueueItemList) {
        mDownloadQueueAdapter.refresh(downloadQueueItemList);
    }

    @Override
    public void refreshAdapter(DownloadQueueItem downloadQueueItem, int position) {
        mDownloadQueueAdapter.refresh(downloadQueueItem, position);
    }

    @Override
    public Map<String, Integer> getDownloadRequestIdentifiers() {
        return mDownloadQueueAdapter.getIdentifiers();
    }

    @Override
    public DownloadQueueItem getCurrentDownloadQueueItem(String identifier) {
        return mDownloadQueueAdapter.getCurrentDownloadQueueItem(identifier);
    }

    @Override
    public void setDownloadPauseText() {
        mTvPauseDownload.setText(getString(R.string.pause_download));
        mTvPauseDownload.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_pause_download, 0, 0, 0);
    }

    @Override
    public void setDownloadResumeText() {
        mTvPauseDownload.setText(getString(R.string.resume_download));
        mTvPauseDownload.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_resume_download, 0, 0, 0);
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.tv_download_close) {
            dismiss();

        } else if (i == R.id.tv_pause_download) {
            mPresenter.handlePauseDownload();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.unregisterSubscriber(this);
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
        if (onDismissListener != null) {
            onDismissListener.onDismiss(this, mIsDownloadCancelled);
            setDownloadCancelled(false);
        }
    }

    public void setOnDismissListener(OnDismissListener onDismissListener) {
        this.onDismissListener = onDismissListener;
    }

    @Override
    public void setDownloadCancelled(boolean isDownloadCancelled) {
        mIsDownloadCancelled = isDownloadCancelled;
    }

    public interface OnDismissListener {
        void onDismiss(DownloadQueueFragment downloadQueueFragment, boolean isDownloadCancelled);
    }

//    public boolean getIsDownloadCancelled(){
//        return mIsDownloadCancelled;
//    }
}
