package org.ekstep.genie.customview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.widget.EditText;

import org.ekstep.genie.util.FontUtil;

/**
 *
 */
public class EkStepCustomEditText extends EditText {
    private KeyImeChange keyImeChangeListener;

    public EkStepCustomEditText(Context context, AttributeSet attrs) {
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

    public void setKeyImeChangeListener(KeyImeChange listener) {
        keyImeChangeListener = listener;
    }

    @Override
    public boolean onKeyPreIme(int keyCode, KeyEvent event) {
        if (keyImeChangeListener != null) {
            keyImeChangeListener.onKeyIme(keyCode, event);
        }
        return false;
    }

    public interface KeyImeChange {
        void onKeyIme(int keyCode, KeyEvent event);
    }

}
