package org.ekstep.genie.base;

import android.content.Context;

import org.ekstep.genie.util.FileHandler;
import org.ekstep.genieservices.commons.IProfileConfig;

/**
 * Created on 15/6/17.
 * shriharsh
 */
public class ProfileConfig implements IProfileConfig {

    @Override
    public String getProfilePath(Context context) {
        String absoluteProfilePath = FileHandler.getProfileDirFilePath(FileHandler.getExternalFilesDir(context), "profiles");
        return absoluteProfilePath;
    }
}
