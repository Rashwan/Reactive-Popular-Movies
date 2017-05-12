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

import com.rashwan.reactive_popular_movies.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import rx.Observable;
import rx.Observable.Transformer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by rashwan on 7/23/16.
 */

public final class Utilities {
    public static final String QUALITY_LOW = "w342";
    public static final String QUALITY_MEDIUM = "w500";
    public static final String QUALITY_HIGH = "w780";
    public static final String MONTH_YEAR_DATE_FORMAT = "MMMM yyyy";
    public static final String DAY_MONTH_YEAR_DATE_FORMAT = "MMMM dd, yyyy";

    private static Observable.Transformer schedulerTransformer;

    //To prevent instantiating the class
    private Utilities() {
    }

    static {
        schedulerTransformer = createSchedulerTransformer();
    }

    private static <T> Transformer<T,T> createSchedulerTransformer() {
        return tObservable -> tObservable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

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
    @SuppressWarnings("unchecked")
    public static <T> Transformer<T,T> applySchedulers(){
        return schedulerTransformer;
    }

    public static String getFullPosterPath(Context context, String posterPath, String quality){
        String baseUrl = context.getString(R.string.poster_base_url);
        return baseUrl + quality + posterPath;
    }
    public static String getFormattedDate(String releaseDate, String dateFormatString){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        SimpleDateFormat newDateFormat = new SimpleDateFormat(dateFormatString, Locale.getDefault());

        try {
            Date date = dateFormat.parse(releaseDate);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            return newDateFormat.format(calendar.getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return releaseDate;
    }


}
