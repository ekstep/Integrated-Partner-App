package org.ekstep.genie.customview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.support.v7.widget.AppCompatCheckedTextView;
import android.util.AttributeSet;

import org.ekstep.genie.util.FontUtil;

public class EkStepBalooCheckedTextView extends AppCompatCheckedTextView {

    public EkStepBalooCheckedTextView(Context context, AttributeSet attrs) {
        super(context, attrs);

        init(attrs);
    }

    public void init(AttributeSet attrs) {
        int[] attrsArray = {android.R.attr.textStyle};
        TypedArray ta = getContext().obtainStyledAttributes(attrs, attrsArray);
        Typeface font = FontUtil.getInstance().getLatoTypeFace(getContext());
        setTypeface(font);
        ta.recycle();
    }

}
