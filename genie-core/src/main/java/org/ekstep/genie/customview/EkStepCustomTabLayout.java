package org.ekstep.genie.customview;

import android.content.Context;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.ekstep.genie.R;
import org.ekstep.genie.util.FontUtil;

/**
 * Created by GoodWorkLabs on 26-07-2016.
 */
public class EkStepCustomTabLayout extends TabLayout {

    private Typeface mTypeface;


    public EkStepCustomTabLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }
    private void init() {
        mTypeface = FontUtil.getInstance().getLatoTypeFace(getContext());
    }
    @Override
    public void addTab(@NonNull Tab tab, boolean setSelected) {
        super.addTab(tab, setSelected);

        ViewGroup mainView = (ViewGroup) getChildAt(0);
        ViewGroup tabView = (ViewGroup) mainView.getChildAt(tab.getPosition());

        int tabChildCount = tabView.getChildCount();
        for (int i = 0; i < tabChildCount; i++) {
            View tabViewChild = tabView.getChildAt(i);
            if (tabViewChild instanceof TextView) {
                ((TextView) tabViewChild).setTypeface(mTypeface);
                ((TextView) tabViewChild).setTextSize(getResources().getDimension(R.dimen.sp_15));
            }
        }
    }
}
