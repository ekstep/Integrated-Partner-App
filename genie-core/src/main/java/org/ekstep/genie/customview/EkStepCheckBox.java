package org.ekstep.genie.customview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.support.v7.widget.AppCompatCheckBox;
import android.util.AttributeSet;

import org.ekstep.genie.util.FontUtil;

/**
 * Created on 5/19/2016.
 *
 * @author anil
 */
public class EkStepCheckBox extends AppCompatCheckBox {

    public EkStepCheckBox(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public void init(AttributeSet attrs) {
        int[] attrsArray = {android.R.attr.textStyle};
        TypedArray ta = getContext().obtainStyledAttributes(attrs, attrsArray);
        Typeface font = FontUtil.getInstance().getNotoTypeFace(getContext());
        setTypeface(font);
        ta.recycle();
    }
}
