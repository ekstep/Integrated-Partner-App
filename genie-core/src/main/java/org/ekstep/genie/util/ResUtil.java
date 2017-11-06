package org.ekstep.genie.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;

/**
 * Created on 15/6/17.
 * shriharsh
 */

public class ResUtil {

    public static void readAndSaveDrawables(Context context, Class<?> clz, String destinationPath) {
        final Field[] fields = clz.getDeclaredFields();
        for (Field field : fields) {
            final int drawableId;
            try {
                drawableId = field.getInt(clz);
            } catch (Exception e) {
                continue;
            }

            /* make use of drawableId for accessing Drawables here */
            String drawableName = context.getResources().getResourceEntryName(drawableId);

            if (drawableName.contains("avatar_normal") || drawableName.contains("avatar_anonymous") || drawableName.contains("img_badge")) {
                try {
                    saveImage(context, drawableId, destinationPath, drawableName);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
    }

    private static void saveImage(Context context, int drawableId, String destinationPath, String fileName) throws IOException {
        Bitmap bm = BitmapFactory.decodeResource(context.getResources(), drawableId);

        File file = new File(destinationPath, fileName + ".png");
        FileOutputStream outStream = new FileOutputStream(file);
        bm.compress(Bitmap.CompressFormat.PNG, 100, outStream);
        outStream.flush();
        outStream.close();
    }
}
