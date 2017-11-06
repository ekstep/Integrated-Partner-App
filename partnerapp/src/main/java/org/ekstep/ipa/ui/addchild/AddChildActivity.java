package org.ekstep.ipa.ui.addchild;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import org.ekstep.genie.activity.BaseActivity;
import org.ekstep.genie.base.BasePresenter;
import org.ekstep.genie.base.BaseView;
import org.ekstep.genie.base.IPresenterFactory;
import org.ekstep.genie.util.DeviceUtility;
import org.ekstep.genie.util.ThemeUtility;
import org.ekstep.ipa.R;
import org.ekstep.ipa.ui.addchild.childfilter.ChildFilterFragment;
import org.ekstep.ipa.ui.addchild.searchchild.SearchChildFragment;

/**
 * @author vinayagasundar
 */

public class AddChildActivity extends BaseActivity
        implements AddChildContract.View, AddChildCallback {

    private AddChildContract.Presenter mPresenter;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_partner_add_child);

        final View decorView = getWindow().getDecorView();
        decorView.setOnSystemUiVisibilityChangeListener(new View.OnSystemUiVisibilityChangeListener() {
            @Override
            public void onSystemUiVisibilityChange(int visibility) {
                DeviceUtility.displayFullScreen(AddChildActivity.this);
            }
        });

        decorView.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                DeviceUtility.displayFullScreen(AddChildActivity.this);
            }
        });

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        if (toolbar != null) {
            setSupportActionBar(toolbar);

            Drawable backIcon = ThemeUtility.getDrawable(this, new int[]{R.attr.menuBackIcon});

            if (getSupportActionBar() != null) {
                getSupportActionBar().setHomeAsUpIndicator(backIcon);
            }
        }

        mPresenter = (AddChildContract.Presenter) presenter;
        mPresenter.start();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            mPresenter.handleNavBackClick();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected IPresenterFactory getPresenterFactory() {
        return new IPresenterFactory() {
            @Override
            public BasePresenter create() {
                return new AddChildPresenter();
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


    @Override
    public void setToolbarTitle(String title) {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(title);
        }
    }

    @Override
    public void setUpBackNavigation() {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public void showChildFilterPage() {
        Fragment fragment = new ChildFilterFragment();
        setFragment(fragment, false, R.id.fragment_container);
    }

    @Override
    public void showSearchChildScreen(String schoolId, String klass) {
        Fragment fragment = SearchChildFragment.newInstance(schoolId, klass);
        setFragment(fragment, true, R.id.fragment_container);
    }

    @Override
    public void handleBackPress() {
        onBackPressed();
    }

    @Override
    public void onSelectedFilter(String schoolId, String klass) {
        mPresenter.showSearchChild(schoolId, klass);
    }

}
