package org.ekstep.genie.util;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import org.ekstep.genie.R;

import java.io.File;

/**
 * Created by swayangjit_gwl on 7/19/2016.
 */
public class GlideImageUtil {

    public static void loadImageUrl(Context context, String url, ImageView imageView) {
        if (context == null) {
            return;
        }
        Glide.with(context).load(url).bitmapTransform(new RoundedCornersTransformation(context, 15, 2,
                RoundedCornersTransformation.CornerType.ALL)).crossFade().placeholder(R.drawable.ic_default_book).into(imageView);
    }

    public static void loadImageUrl(Context context, File file, ImageView imageView) {
        if (context == null) {
            return;
        }
        Glide.with(context).load(file).bitmapTransform(new RoundedCornersTransformation(context, 15, 2,
                RoundedCornersTransformation.CornerType.ALL)).crossFade().placeholder(R.drawable.ic_default_book).into(imageView);
    }

    public static void loadImageUrl(Context context, ImageView imageView) {
        Glide.with(context).load(R.drawable.ic_default_book).bitmapTransform(new RoundedCornersTransformation(context, 15, 2,
                RoundedCornersTransformation.CornerType.ALL)).crossFade().placeholder(R.drawable.ic_default_book).into(imageView);
    }

    public static void loadRectangularImage(Context context, String url, ImageView imageView) {
        Glide.with(context).load(url).crossFade().placeholder(R.drawable.ic_default_book).into(imageView);
    }
}
