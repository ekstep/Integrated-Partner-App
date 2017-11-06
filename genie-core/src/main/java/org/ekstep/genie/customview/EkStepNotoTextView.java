package org.ekstep.genie.customview;


import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;

import org.ekstep.genie.R;
import org.ekstep.genie.util.FontUtil;

public class EkStepNotoTextView extends AppCompatTextView {
    public EkStepNotoTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public void init(AttributeSet attrs) {
        if(attrs!=null){
            TypedArray ta = getContext().obtainStyledAttributes(attrs, R.styleable.EkstepNotoTextView);
            Typeface font = FontUtil.getInstance().getNotoTypeFace(getContext());
            setTypeface(font);
            if (ta.hasValue(R.styleable.EkstepNotoTextView_fontstyle)) {
                setTypeface(getTypeface(), Typeface.BOLD);
            }
            ta.recycle();
        }

    }

}
