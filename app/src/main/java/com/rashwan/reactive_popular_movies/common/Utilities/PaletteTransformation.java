package com.rashwan.reactive_popular_movies.common.Utilities;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.support.v7.graphics.Palette;
import android.widget.ImageView;

import com.squareup.picasso.Transformation;

import java.util.Map;
import java.util.WeakHashMap;

/**
 * Created by rashwan on 7/1/16.
 */

public class PaletteTransformation implements Transformation{
    private static final Map<Bitmap,Palette> CACHE = new WeakHashMap<>();
    private final int numOfColors;


    public PaletteTransformation(int numOfColors) {
        this.numOfColors = numOfColors;
    }

    public PaletteTransformation() {
       this(0);
    }

    @Override
    public Bitmap transform(Bitmap source) {
        if (!CACHE.containsKey(source)){
            final Palette palette = numOfColors > 0 ?
                    new Palette.Builder(source).maximumColorCount(numOfColors).generate():
                    new Palette.Builder(source).generate();
            CACHE.put(source,palette);
        }
        return source;
    }

    @Override
    public String key() {
        return getClass ().getCanonicalName () + ":" + numOfColors;
    }

    public abstract static class Callback implements com.squareup.picasso.Callback {
        private final ImageView target;

        public Callback(ImageView target) {
            this.target = target;
        }

        @Override
        public void onSuccess() {
            onPalette(CACHE.get(((BitmapDrawable)target.getDrawable()).getBitmap()));
        }

        @Override
        public void onError() {
            onPalette(null);
        }
        public abstract void onPalette(Palette palette);
    }
}
