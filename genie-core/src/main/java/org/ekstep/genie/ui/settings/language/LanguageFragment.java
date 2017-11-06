package org.ekstep.genie.ui.settings.language;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;

import org.ekstep.genie.CoreApplication;
import org.ekstep.genie.R;
import org.ekstep.genie.base.BasePresenter;
import org.ekstep.genie.base.BaseView;
import org.ekstep.genie.base.IPresenterFactory;
import org.ekstep.genie.customview.EkStepCustomTextView;
import org.ekstep.genie.fragment.BaseFragment;
import org.ekstep.genie.util.FontUtil;
import org.ekstep.genie.util.preference.PreferenceUtil;
import org.ekstep.genieservices.async.ContentService;

/**
 * Created by Sneha on 9/12/2017.
 */

public class LanguageFragment extends BaseFragment implements LanguageContract.View {
    private static final String TAG = LanguageFragment.class.getSimpleName();
    private RadioGroup mLanguageGroup;
    private ContentService mContentService;

    private LanguagePresenter mPresenter;
    private EkStepCustomTextView mApplyLanguage;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_change_language, container, false);

        mPresenter = (LanguagePresenter) presenter;

        mApplyLanguage = (EkStepCustomTextView) view.findViewById(R.id.txt_apply_language);
        mLanguageGroup = (RadioGroup) view.findViewById(R.id.radio_group_language);

        displaySelectedLanguage();

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mContentService = CoreApplication.getGenieAsyncService().getContentService();

        mApplyLanguage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int index = mLanguageGroup.indexOfChild(getView().findViewById(mLanguageGroup.getCheckedRadioButtonId()));
                mPresenter.handleLanguageChange(index);
            }
        });
    }

    @Override
    protected IPresenterFactory getPresenterFactory() {
        return new IPresenterFactory() {
            @Override
            public BasePresenter create() {
                return new LanguagePresenter();
            }
        };
    }

    @Override
    protected String getTAG() {
        return getClass().getName();
    }

    @Override
    protected BaseView getBaseView() {
        return this;
    }

    @Override
    public void displaySelectedLanguage() {
        if (PreferenceUtil.isFirstTime().equalsIgnoreCase("true")) {
            mLanguageGroup.check(-1);
        } else {
            int id = mLanguageGroup.getChildAt(FontUtil.getInstance().getPositionForSelectedLocale()).getId();
            mLanguageGroup.check(id);
        }
    }
}
