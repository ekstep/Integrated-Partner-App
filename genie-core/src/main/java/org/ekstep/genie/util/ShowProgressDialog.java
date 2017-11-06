package org.ekstep.genie.util;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Window;
import android.widget.TextView;

import org.ekstep.genie.R;

/**
 * Created by swayangjit_gwl on 3/20/2016.
 */
public class ShowProgressDialog {

    private static Dialog mDialog = null;

    public static Dialog showProgressDialog(Context context, String message) {
        mDialog = new Dialog(context);
        mDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        mDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        mDialog.setContentView(R.layout.dialog_progress);

        TextView txt_Message = (TextView) mDialog.findViewById(R.id.txt_message);
        txt_Message.setText(message);

        mDialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        mDialog.setCancelable(false);
        DeviceUtility.displayFullScreenDialog(mDialog,(Activity)context);

        return mDialog;
    }

    public static void dismissDialog() {
        try {
            if (mDialog != null && mDialog.isShowing()) {
                mDialog.dismiss();
                mDialog = null;
            }
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
    }

    public static void setMsg(String msg) {
        if (mDialog != null) {
            TextView txt_Message = (TextView) mDialog.findViewById(R.id.txt_message);
            txt_Message.setText(msg);
        }
    }

}
