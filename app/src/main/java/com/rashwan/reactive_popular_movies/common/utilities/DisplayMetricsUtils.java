package com.rashwan.reactive_popular_movies.common.utilities;

import android.content.res.Configuration;
import android.content.res.Resources;

/**
 * Created by rashwan on 7/13/16.
 */

public final class DisplayMetricsUtils {

    public static Boolean isScreenSW(int smallestWidthDp){
        Configuration config = Resources.getSystem().getConfiguration();
        return config.smallestScreenWidthDp >= smallestWidthDp;
    }
}
