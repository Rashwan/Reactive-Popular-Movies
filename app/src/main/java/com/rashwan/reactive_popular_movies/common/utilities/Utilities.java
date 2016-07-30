package com.rashwan.reactive_popular_movies.common.utilities;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v4.app.ShareCompat;

/**
 * Created by rashwan on 7/23/16.
 */

public final class Utilities {
    /**
     * Create a share intent for a movie using its title and a trailer.
     * @param activity The activity to start the intent chooser from.
     * @param title The title of the movie to be shared.
     * @param trailerUrl The Youtube url for a trailer of the movie to be shared.
     */
    public static void createShareIntent(Activity activity, String title, String trailerUrl) {
        ShareCompat.IntentBuilder builder = ShareCompat.IntentBuilder.from(activity)
                .setType("text/plain")
                .setText("check out the trailer for the movie " + title  + ", at : " + trailerUrl);
        activity.startActivity(Intent.createChooser(builder.getIntent(), "Share Movie!"));
    }
    public static Boolean isScreenSW(int smallestWidthDp){
        Configuration config = Resources.getSystem().getConfiguration();
        return config.smallestScreenWidthDp >= smallestWidthDp;
    }
    public static Boolean isNetworkAvailable(Application application){
        ConnectivityManager connectivityManager  = (ConnectivityManager)
                application.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}
