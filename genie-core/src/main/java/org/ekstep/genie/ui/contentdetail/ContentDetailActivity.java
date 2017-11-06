package org.ekstep.genie.ui.contentdetail;

import android.os.Bundle;

import org.ekstep.genie.R;
import org.ekstep.genie.activity.BaseActivity;
import org.ekstep.genie.base.BaseView;
import org.ekstep.genie.base.IPresenterFactory;
import org.ekstep.genie.base.PresenterManager;

/**
 * Created on 4/21/2016.
 *
 * @author swayangjit_gwl
 */
public class ContentDetailActivity extends BaseActivity {

    private ContentDetailFragment contentDetailFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content_detail);
        contentDetailFragment = new ContentDetailFragment();
        contentDetailFragment.setArguments(getIntent().getExtras());
        setFragment(contentDetailFragment, false);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        ((ContentDetailPresenter)contentDetailFragment.getPresenter()).handleBackButtonClick();
    }

    @Override
    public void onPermissionsGranted(int requestCode) {
        super.onPermissionsGranted(requestCode);
        switch (requestCode) {
            case REQUEST_PERMISSION:
                ((ContentDetailPresenter)contentDetailFragment.getPresenter()).initDownload();
                break;
        }
    }

    @Override
    protected IPresenterFactory getPresenterFactory() {
        return null;
    }

    @Override
    protected String getTAG() {
        return null;
    }

    @Override
    protected BaseView getBaseView() {
        return null;
    }

}