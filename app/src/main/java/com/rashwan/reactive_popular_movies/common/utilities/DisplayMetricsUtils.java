package com.rashwan.reactive_popular_movies.common.utilities;

import android.content.res.Resources;
import android.util.DisplayMetrics;

/**
 * Created by rashwan on 7/13/16.
 */

public final class DisplayMetricsUtils {
    public static Boolean isScreenSW(int smallestWidthDp){
        DisplayMetrics displayMetrics = Resources.getSystem().getDisplayMetrics();
        float widthDp = displayMetrics.widthPixels / displayMetrics.density;
        float heightDp = displayMetrics.heightPixels / displayMetrics.density;
        float screenSw = Math.min(widthDp,heightDp);
        return screenSw >= smallestWidthDp;
    }
}
