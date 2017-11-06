package org.ekstep.genie.util;

import android.app.Activity;
import android.content.Context;
import android.graphics.Rect;
import android.os.Handler;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import org.ekstep.genie.R;

/**
 * Created on 5/6/2016.
 *
 * @author swayangjit_gwl
 */
public class KeyboardUtil {

    public static void hideSoftKeyboard(Context context, EditText edtText) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(edtText.getWindowToken(), 0);
    }

    public static void showSoftKeyboard(final Context context, final EditText editText) {
        editText.postDelayed(new Runnable() {
            @Override
            public void run() {
                editText.setFocusable(true);
                InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
            }
        }, 500);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                DeviceUtility.displayFullScreenPopUp((Activity) context);
            }
        }, 1000);
    }

    public static void setKeyboardListener(Activity activity, final IKeyboardListener iKeyboardListener) {
        final View activityRootView = activity.findViewById(R.id.fragment_container);
        activityRootView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                Rect r = new Rect();
                activityRootView.getWindowVisibleDisplayFrame(r);
                int screenHeight = activityRootView.getRootView().getHeight();
                int keypadHeight = screenHeight - r.bottom;
                if (keypadHeight > screenHeight * 0.15) { // 0.15 ratio is perhaps enough to determine keypad height.
                    iKeyboardListener.onKeyboardVisible();
                } else {
                    iKeyboardListener.onKeyboardHide();
                }
            }
        });
    }

    public interface IKeyboardListener {
        void onKeyboardVisible();

        void onKeyboardHide();
    }
}
