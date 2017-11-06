package org.ekstep.genie.customview;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;

import org.ekstep.genie.util.FontUtil;

/**
 * Created on 12/15/2015.
 *
 * @author swayangjit_gwl
 */
public class EkStepCustomTextView extends EkStepNotoTextView {

    public EkStepCustomTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public void init(AttributeSet attrs) {
        if (attrs != null) {
            Typeface font = FontUtil.getInstance().getLatoTypeFace(getContext());
            setTypeface(font);
        }
    }

}
