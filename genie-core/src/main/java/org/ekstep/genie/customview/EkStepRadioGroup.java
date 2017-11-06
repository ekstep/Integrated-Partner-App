package org.ekstep.genie.customview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.support.v7.widget.AppCompatRadioButton;
import android.util.AttributeSet;

import org.ekstep.genie.util.FontUtil;

public class EkStepRadioGroup extends AppCompatRadioButton
{
    private OnCheckedChangeListener mListener;

    public EkStepRadioGroup(Context context, AttributeSet attrs)
	{
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

    @Override
    public void setOnCheckedChangeListener(final OnCheckedChangeListener listener) {
        mListener = listener;
        super.setOnCheckedChangeListener(listener);
    }

    public void setChecked(final boolean checked, final boolean alsoNotify) {
        if (!alsoNotify) {
            super.setOnCheckedChangeListener(null);
            super.setChecked(checked);
            super.setOnCheckedChangeListener(mListener);
            return;
        }
        super.setChecked(checked);
    }

    public void toggle(boolean alsoNotify) {
        if (!alsoNotify) {
            super.setOnCheckedChangeListener(null);
            super.toggle();
            super.setOnCheckedChangeListener(mListener);
        }
        super.toggle();
    }
}
