package org.ekstep.genie.util;

import android.content.Context;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by swayangjit on 1/6/17.
 */

public class RawUtil {

    private static final String TAG = RawUtil.class.getSimpleName();

    public static String readRawFile(Context context, int rawId) {
        InputStream inputStream = context.getResources().openRawResource(rawId);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        byte buf[] = new byte[1024];
        int len;

        try {
            while ((len = inputStream.read(buf)) != -1) {
                outputStream.write(buf, 0, len);
            }
            outputStream.close();
            inputStream.close();
        } catch (IOException e) {
            LogUtil.e(TAG, e.getMessage());
        }

        return outputStream.toString();
    }
}
