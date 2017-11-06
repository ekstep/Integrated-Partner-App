package org.ekstep.genie.asynctask;

import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.widget.TextView;

import org.ekstep.genie.model.DisplayResolveInfo;

import java.lang.ref.WeakReference;

/**
 * Created by swayangjit_gwl on 9/8/2016.
 */
public class IconLoaderTask extends AsyncTask<Void, Void, Drawable> {

    private final WeakReference<TextView> mTxtView;
    private final DisplayResolveInfo mDisplayResolveInfo;
    private int mIconSize;
    private PackageManager mPackageManager = null;
    private int mOrientation;

    public IconLoaderTask(DisplayResolveInfo info, TextView textView) {
        this.mTxtView = new WeakReference<>(textView);
        this.mDisplayResolveInfo = info;
    }

    public void setPackabeManager(PackageManager packageManager) {
        this.mPackageManager = packageManager;
    }

    public void setIconSize(int iconSize) {
        this.mIconSize = iconSize;
    }

    public void setOrientation(int orienation) {
        mOrientation = orienation;
    }

    @Override
    protected Drawable doInBackground(Void... params) {
        Drawable icon = mDisplayResolveInfo.getIcon(mPackageManager);
        icon.setBounds(0, 0, mIconSize, (int) (((float) icon.getIntrinsicHeight()) / icon.getIntrinsicWidth() * mIconSize));
        return icon;
    }

    @Override
    protected void onPostExecute(Drawable icon) {
        super.onPostExecute(icon);
        TextView textView = this.mTxtView.get();
        if (textView != null) {
            if (mOrientation == 1) {
                textView.setCompoundDrawables(null, icon, null, null);
            } else {
                textView.setCompoundDrawables(icon, null, null, null);
            }

        }
    }
}
