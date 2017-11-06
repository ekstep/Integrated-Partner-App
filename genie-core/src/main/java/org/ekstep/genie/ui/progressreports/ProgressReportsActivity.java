package org.ekstep.genie.ui.progressreports;

import android.os.Bundle;

import org.ekstep.genie.R;
import org.ekstep.genie.activity.BaseActivity;
import org.ekstep.genie.base.BaseView;
import org.ekstep.genie.base.IPresenterFactory;
import org.ekstep.genie.base.PresenterManager;

/**
 * Created on 12/23/2016.
 *
 * @author anil
 */
public class ProgressReportsActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_progress_reports);

        ProgressReportsFragment progressReportsFragment = new ProgressReportsFragment();
        progressReportsFragment.setArguments(getIntent().getExtras());
        setFragment(progressReportsFragment, false);
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
