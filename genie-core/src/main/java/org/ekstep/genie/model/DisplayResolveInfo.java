package org.ekstep.genie.model;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.drawable.Drawable;

/**
 * Created by swayangjit_gwl on 9/8/2016.
 */
public class DisplayResolveInfo {
    public final Intent intent;
    public ResolveInfo resolveInfo;
    public Drawable icon;
    private CharSequence label;

    public DisplayResolveInfo(ResolveInfo resolveInfo, Intent intent) {
        this.resolveInfo = resolveInfo;
        this.intent = intent;
    }

    public CharSequence getLabel(PackageManager pm) {
        if (label == null) {
            label = resolveInfo.loadLabel(pm);
        }
        return label;
    }

    public Drawable getIcon(PackageManager pm) {
        if (icon == null) {
            icon = resolveInfo.loadIcon(pm);
        }
        return icon;
    }

    public Intent getIntent() {
        return intent;
    }

}
