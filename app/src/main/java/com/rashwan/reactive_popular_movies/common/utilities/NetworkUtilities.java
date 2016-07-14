package com.rashwan.reactive_popular_movies.common.utilities;

import android.app.Application;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by rashwan on 7/14/16.
 */

public final class NetworkUtilities {
    public static Boolean isNetworkAvailable(Application application){
        ConnectivityManager connectivityManager  = (ConnectivityManager)
                application.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}
