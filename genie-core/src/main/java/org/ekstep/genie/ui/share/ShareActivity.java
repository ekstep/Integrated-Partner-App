package org.ekstep.genie.ui.share;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import org.ekstep.genie.R;
import org.ekstep.genie.activity.BaseActivity;
import org.ekstep.genie.base.BasePresenter;
import org.ekstep.genie.base.BaseView;
import org.ekstep.genie.base.IPresenterFactory;
import org.ekstep.genie.customview.MaxHeightLinearLayout;
import org.ekstep.genie.model.DisplayResolveInfo;
import org.ekstep.genie.util.DeviceUtility;
import org.ekstep.genie.util.ShareUtil;

import java.util.List;
import java.util.Map;

/**
 * This activity controls all type of Sharing in Genie
 */
public class ShareActivity extends BaseActivity implements
        ShareContract.View, ShareResolverAdapter.OnItemClickedListener,
        View.OnClickListener {

    public static final int REQUEST_CODE_SHARE = 97;
    public static final int REQUEST_CODE_SHARE_PROFILE = 98;
    public static final int REQUEST_CODE_SHARE_TELEMETRY = 99;
    public static final int REQUEST_CODE_SHARE_GENIE = 100;
    public static final int REQUEST_CODE_SHARE_GENIE_CONFIGURATIONS = 101;
    private RecyclerView mFileRecyclerView = null;
    private RecyclerView mLinkRecyclerView = null;
    private TextView mTextViewShareAsTextOrLink;
    private TextView mTvTitle;
    private ShareContract.Presenter mPresenter;

    /**
     * Starts ShareActivity to share content
     *
     * @param context        Context.
     * @param isEcarNLink    whether the intent type is both file and  text or not.
     * @param values         HashMap containing Contnet info.
     * @param identifierList whether the file is epar or not.
     * @return void.
     */
    public static void startContetExportIntent(Context context, boolean isEcarNLink, Map<String, String> values, List<String> identifierList) {
        ShareUtil.startContetExportIntent((Activity) context, isEcarNLink, values, identifierList);
    }

    /**
     * Starts ShareActivity to share content Link
     *
     * @param context        Context.
     * @param isLink         whether the intent type  is text or not.
     * @param values         HashMap containing Contnet info.
     * @param identifierList whether the file is epar or not.
     * @return void.
     */
    public static void startContentLinkIntent(Context context, boolean isLink, Map<String, String> values, List<String> identifierList) {
        ShareUtil.startContentLinkIntent((Activity) context, isLink, values, identifierList);
    }

    /**
     * Starts ShareActivity to share profile
     *
     * @param context Context.
     * @param uidList List of uids of profiles
     * @param values  HashMap containing Content info.
     * @return void.
     */
    public static void startProfileExportIntent(Context context, List<String> uidList, Map<String, String> values) {
        ShareUtil.startProfileExportIntent((Activity) context, uidList, values);
    }

    /**
     * Starts ShareActivity to share Telemetry(.gsa file)
     *
     * @param context Context.
     * @return void.
     */
    public static void startTelemetryExportIntent(Context context) {
        ShareUtil.startTelemetryExportIntent((Activity) context);
    }

    /**
     * Starts ShareActivity to share Genie
     *
     * @param context Context.
     * @return void.
     */
    public static void startGenieShareIntent(Context context) {
        ShareUtil.startGenieShareIntent((Activity) context);
    }

    /**
     * Starts ShareActivity to share Genie Configurations
     *
     * @param context Context.
     * @return void.
     */
    public static void startGenieConfigurationsShareIntent(Context context) {
        ShareUtil.startGenieConfigurationsShareIntent((Activity) context);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share);
        mPresenter = (SharePresenter) presenter;
        initView();
        mTvTitle = ((TextView) findViewById(R.id.tv_share_title));
        mTextViewShareAsTextOrLink = (TextView) findViewById(R.id.tv_share_as_text_or_link);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            mPresenter.handleAndPopulateShareOptions(bundle);
        }
    }

    @Override
    public void displayFileShareView(List<String> identifierList, String contentName, String screenName) {
        updateAdapterData(mFileRecyclerView, mPresenter.getResolverListForShareIntent(
                this, mPresenter.getEcarShareIntent(identifierList,
                        contentName, screenName)));
        hideShareLinkLayout();
        setTitleText(getString(R.string.title_share_lesson));
    }

    @Override
    public void displayTelemetryShareView(List<String> identifierList, String contentName, String screenName) {
        updateAdapterData(mFileRecyclerView, mPresenter.getResolverListForShareIntent(
                this, mPresenter.getShareIntent(identifierList, contentName, screenName)));
        hideShareLinkLayout();
        setTitleText(getString(R.string.title_share_telemetry));
    }

    @Override
    public void displayProfileShareView(List<String> identifierList, String contentName, String screenName) {
        updateAdapterData(mFileRecyclerView, mPresenter.getResolverListForShareIntent(
                this, mPresenter.getProfileShareIntent(identifierList, contentName, screenName)));
        hideShareLinkLayout();
        setTitleText(getString(R.string.title_share_profile));
    }

    @Override
    public void displayGenieConfigShareView() {
        updateAdapterData(mFileRecyclerView, mPresenter.getResolverListForShareIntent(
                this, mPresenter.getGenieConfigurationsShareIntent(false)));
        updateAdapterData(mLinkRecyclerView, mPresenter.getResolverListForShareIntent(
                this, mPresenter.getGenieConfigurationsShareIntent(true)));
        setTextToShareAsTV(getString(R.string.label_share_text));
        setTitleText(getString(R.string.title_share_details));
    }

    @Override
    public void displayGenieShareView() {
        updateAdapterData(mFileRecyclerView, mPresenter.getResolverListForShareIntent(
                this, mPresenter.getGenieShareIntent(false)));
        updateAdapterData(mLinkRecyclerView, mPresenter.getResolverListForShareIntent(
                this, mPresenter.getGenieShareIntent(true)));
        setTitleText(getString(R.string.label_nav_share_genie));
    }

    @Override
    public void displayLinkShareView(List<String> identifierList, String contentName, String screenName) {
        updateAdapterData(mLinkRecyclerView, mPresenter.getResolverListForShareIntent(
                this, mPresenter.getLinkShareIntent(identifierList, contentName, screenName)));
        hideShareFileLayout();
        setTitleText(getString(R.string.title_share_lesson));
    }

    @Override
    public void displayEcarLinkShareView(List<String> identifierList, String contentName, String screenName) {
        updateAdapterData(mFileRecyclerView, mPresenter.getResolverListForShareIntent(this,
                mPresenter.getEcarShareIntent(identifierList, contentName, screenName)));
        updateAdapterData(mLinkRecyclerView, mPresenter.getResolverListForShareIntent(this,
                mPresenter.getLinkShareIntent(identifierList, contentName, screenName)));
        setTextToShareAsTV(getString(R.string.label_share_link));
        setTitleText(getString(R.string.title_share_lesson));
    }

    @Override
    protected IPresenterFactory getPresenterFactory() {
        return new IPresenterFactory() {
            @Override
            public BasePresenter create() {
                return new SharePresenter();
            }
        };
    }

    @Override
    protected String getTAG() {
        return getLocalClassName();
    }

    @Override
    protected BaseView getBaseView() {
        return this;
    }

    private void setTitleText(String title) {
        mTvTitle.setText(title);
    }

    private void setTextToShareAsTV(String title) {
        mTextViewShareAsTextOrLink.setText(title);
    }

    private void hideShareFileLayout() {
        findViewById(R.id.layout_share_file).setVisibility(View.GONE);
    }

    private void hideShareLinkLayout() {
        findViewById(R.id.layout_share_link).setVisibility(View.GONE);
    }

    private void updateAdapterData(RecyclerView recyclerView, List<DisplayResolveInfo> resolveInfoList) {
        recyclerView.setAdapter(new ShareResolverAdapter(this, resolveInfoList, this));
    }

    /**
     * Initializes all views
     *
     * @return void.
     */
    private void initView() {
        findViewById(R.id.transparent_view).setOnClickListener(this);
        mFileRecyclerView = (RecyclerView) findViewById(R.id.resolver_recyclerview_file);
        mFileRecyclerView.setHasFixedSize(true);
        mFileRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        mLinkRecyclerView = (RecyclerView) findViewById(R.id.resolver_recyclerview_link);
        mLinkRecyclerView.setHasFixedSize(true);
        mLinkRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        MaxHeightLinearLayout recyclerViewlayout = (MaxHeightLinearLayout) findViewById(R.id.layout_recyclerView);
        recyclerViewlayout.setMaxHeight((int) (DeviceUtility.getDeviceWidth(this) / 2.5));
    }

    @Override
    public void onItemClicked(Intent intent, ResolveInfo resolveInfo) {
        setResult(RESULT_OK, intent);
        finish();
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.transparent_view) {
            onBackButtonPressed();

        }
    }

    @Override
    public void onBackPressed() {
        onBackButtonPressed();
    }

    private void onBackButtonPressed() {
        finish();
        overridePendingTransition(R.anim.push_down_in, R.anim.push_down_out);
    }
}
