package org.ekstep.genie.ui.settings.advancedsettings.addnewtag;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;

import org.ekstep.genie.R;
import org.ekstep.genie.activity.BaseActivity;
import org.ekstep.genie.base.BaseView;
import org.ekstep.genie.base.IPresenterFactory;
import org.ekstep.genie.fragment.BaseFragment;

/**
 * Created by Sneha on 9/21/2017.
 */

public class AddNewTagActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_tag);

        ImageView imageView = (ImageView) findViewById(R.id.btn_back);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        BaseFragment fragment = new AddNewTagFragment();
        fragment.setArguments(getIntent().getExtras());
        setFragment(fragment);
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
