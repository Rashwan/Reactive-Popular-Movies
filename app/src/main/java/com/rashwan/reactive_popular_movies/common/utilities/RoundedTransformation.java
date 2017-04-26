package com.rashwan.reactive_popular_movies.common.utilities;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Shader;

// enables hardware accelerated rounded corners
public class RoundedTransformation implements com.squareup.picasso.Transformation {
    private final int color;
    private String key;

    // radius is corner radii in dp
    // margin is the board in dp
    public RoundedTransformation(int color) {

        this.color = color;
        key =  "rounded " + String.valueOf(Math.random());
    }

    @Override
    public Bitmap transform(final Bitmap source) {
        int borderWidth = 5;

        int width = source.getWidth() + borderWidth;
        int height = source.getHeight() + borderWidth;

        float radius = width > height ? ((float) height) / 2f : ((float) width) / 2f;


        Paint paint = new Paint();
        paint.setAntiAlias(true);
        Shader shader = new BitmapShader(source, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
        paint.setShader(shader);

        Bitmap output = Bitmap.createBitmap(width , height, Config.ARGB_8888);
        Canvas canvas = new Canvas(output);
        canvas.drawCircle(width / 2,height /2 ,radius, paint);

        //border code
        paint.setShader(null);
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(color);
        paint.setStrokeWidth(5);

        canvas.drawCircle(width/2,
                height /2 , radius - borderWidth / 2 ,paint);

        if (source != output) {
            source.recycle();
        }

        return output;
    }

    @Override
    public String key() {
        return key;
    }
}