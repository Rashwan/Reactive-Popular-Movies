package com.rashwan.reactive_popular_movies.data.model;

import android.support.annotation.Nullable;

import com.google.auto.value.AutoValue;
import com.squareup.moshi.Json;
import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by rashwan on 4/26/17.
 */
@AutoValue
public abstract class CastDetails {
    public abstract long id();
    public abstract String name();
    @Nullable public abstract String biography();
    @Nullable public abstract String birthday();
    @Nullable public abstract String deathday();
    @Nullable @Json(name = "place_of_birth") public abstract String placeOfBirth();
    @Nullable @Json(name = "profile_path") public abstract String profilePath();


    public static JsonAdapter<CastDetails> jsonAdapter(Moshi moshi){
        return new AutoValue_CastDetails.MoshiJsonAdapter(moshi);
    }
    public String getAge(){
        if (birthday() != null) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
            try {
                Date birthDate = dateFormat.parse(this.birthday());
                Calendar today = Calendar.getInstance();
                Calendar birthDay = Calendar.getInstance();
                birthDay.setTime(birthDate);
                int age = today.get(Calendar.YEAR) - birthDay.get(Calendar.YEAR);
                if (today.get(Calendar.DAY_OF_YEAR) < birthDay.get(Calendar.DAY_OF_YEAR)) {
                    age--;
                }
                return String.valueOf(age);
            } catch (ParseException e) {
                e.printStackTrace();
                return "";
            }
        }
        return "";
    }
}
