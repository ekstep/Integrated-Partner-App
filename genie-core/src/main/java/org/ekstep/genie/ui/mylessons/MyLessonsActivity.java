package org.ekstep.genie.ui.mylessons;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import org.ekstep.genie.R;
import org.ekstep.genie.activity.BaseActivity;
import org.ekstep.genie.base.BasePresenter;
import org.ekstep.genie.base.BaseView;
import org.ekstep.genie.base.IPresenterFactory;
import org.ekstep.genie.util.Constant;
import org.ekstep.genieservices.commons.bean.Content;
import org.ekstep.genieservices.commons.bean.Profile;

import java.util.List;

/**
 * Created by swayangjit on 14/9/17.
 */

public class MyLessonsActivity extends BaseActivity implements View.OnClickListener, MyLessonsContract.View {


    private MyLessonsPresenter mPresenter;

    private RecyclerView mRv_My_Lessons;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_lessons);
        mPresenter = (MyLessonsPresenter) presenter;
        initViews();
        extractBundle(getIntent());
    }

    private void initViews() {
        mRv_My_Lessons = (RecyclerView) findViewById(R.id.rv_my_lessons);
        mRv_My_Lessons.setLayoutManager(new GridAutofitLayoutManager(this, (int) getResources().getDimension(R.dimen.section_each_item_width)));
        findViewById(R.id.btn_back).setOnClickListener(this);
    }

    private void extractBundle(Intent intent) {
        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            String lessonType = bundle.getString(Constant.BundleKey.BUNDLE_KEY_LESSON_TYPE);
            Profile profile = (Profile) bundle.getSerializable(Constant.BundleKey.BUNDLE_KEY_CURRENT_PROFILE);
            boolean isNotAnonymousProfile = bundle.getBoolean(Constant.BundleKey.BUNDLE_KEY_IS_NOT_ANONYMOUS);
            if (lessonType.equalsIgnoreCase(Constant.CONTENT_TYPE_TEXTBOOK)) {
                ((TextView) findViewById(R.id.txt_header)).setText(R.string.title_home_my_text_books);
            }
            mPresenter.fetchLocalLessons(lessonType, profile, isNotAnonymousProfile);
        }

    }


    @Override
    protected IPresenterFactory getPresenterFactory() {
        return new IPresenterFactory() {
            @Override
            public BasePresenter create() {
                return new MyLessonsPresenter();
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
    public void onClick(View v) {

        int i = v.getId();
        if (i == R.id.btn_back) {
            finish();

        }
    }

    @Override
    public void showMyLessonsGrid(Profile profile, boolean isNotAnonymousProfile, List<Content> contentList) {
        MyLessonsAdapter lessonsAdapter = new MyLessonsAdapter(this, profile, isNotAnonymousProfile, mPresenter);
        lessonsAdapter.setData(contentList);
        mRv_My_Lessons.setAdapter(lessonsAdapter);
    }
}
