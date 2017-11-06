package org.ekstep.genie.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.Transformation;
import com.bumptech.glide.load.engine.Resource;
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapResource;

/**
 * Created by swayangjit_gwl on 5/25/2016.
 */
public class RoundedCornersTransformation implements Transformation<Bitmap> {

    public enum CornerType {
        ALL,
        TOP_LEFT, TOP_RIGHT, BOTTOM_LEFT, BOTTOM_RIGHT,
        TOP, BOTTOM, LEFT, RIGHT,
        OTHER_TOP_LEFT, OTHER_TOP_RIGHT, OTHER_BOTTOM_LEFT, OTHER_BOTTOM_RIGHT,
        DIAGONAL_FROM_TOP_LEFT, DIAGONAL_FROM_TOP_RIGHT
    }

    private BitmapPool mBitmapPool;
    private int mRadius;
    private int mDiameter;
    private int mMargin;
    private CornerType mCornerType;

    public RoundedCornersTransformation(Context context, int radius, int margin) {
        this(context, radius, margin, CornerType.ALL);
    }

    public RoundedCornersTransformation(BitmapPool pool, int radius, int margin) {
        this(pool, radius, margin, CornerType.ALL);
    }

    public RoundedCornersTransformation(Context context, int radius, int margin,
                                        CornerType cornerType) {
        this(Glide.get(context).getBitmapPool(), radius, margin, cornerType);
    }

    public RoundedCornersTransformation(BitmapPool pool, int radius, int margin,
                                        CornerType cornerType) {
        mBitmapPool = pool;
        mRadius = radius;
        mDiameter = mRadius * 2;
        mMargin = margin;
        mCornerType = cornerType;
    }

    @Override
    public Resource<Bitmap> transform(Resource<Bitmap> resource, int outWidth, int outHeight) {
        Bitmap source = resource.get();

        int width = source.getWidth();
        int height = source.getHeight();

        Bitmap bitmap = mBitmapPool.get(width, height, Bitmap.Config.ARGB_8888);
        if (bitmap == null) {
            bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        }

        Canvas canvas = new Canvas(bitmap);
        Paint paint = new Paint();
        paint.setColor(Color.parseColor("#b74302"));
        paint.setAntiAlias(true);
        paint.setShader(new BitmapShader(source, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP));
        drawRoundRect(canvas, paint, width, height);
        return BitmapResource.obtain(bitmap, mBitmapPool);
    }

    private void drawRoundRect(Canvas canvas, Paint paint, float width, float height) {
        float right = width - mMargin;
        float bottom = height - mMargin;

        switch (mCornerType) {
            case ALL:
                canvas.drawRoundRect(new RectF(mMargin, mMargin, right, bottom), mRadius, mRadius, paint);
                break;

        }
    }



    @Override public String getId() {
        return "RoundedTransformation(radius=" + mRadius + ", margin=" + mMargin + ", diameter="
                + mDiameter + ", cornerType=" + mCornerType.name() + ")";
    }
}
