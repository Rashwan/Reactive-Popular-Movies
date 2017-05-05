package com.rashwan.reactive_popular_movies.data.model;

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
    public abstract String biography();
    public abstract String birthday();
    @Json(name = "place_of_birth") public abstract String placeOfBirth();
    @Json(name = "profile_path") public abstract String profilePath();


    public static JsonAdapter<CastDetails> jsonAdapter(Moshi moshi){
        return AutoValue_CastDetails.jsonAdapter(moshi);
    }
    public String getFormattedBirthday(){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        SimpleDateFormat newDateFormat = new SimpleDateFormat("MMMM dd, yyyy", Locale.getDefault());

        try {
            Date date = dateFormat.parse(this.birthday());
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            return newDateFormat.format(calendar.getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return this.birthday();
    }
    public String getAge(){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        try {
            Date birthDate = dateFormat.parse(this.birthday());
            Calendar today = Calendar.getInstance();
            Calendar birthDay = Calendar.getInstance();
            birthDay.setTime(birthDate);
            int age = today.get(Calendar.YEAR) - birthDay.get(Calendar.YEAR);
            if (today.get(Calendar.DAY_OF_YEAR) < birthDay.get(Calendar.DAY_OF_YEAR)){
                age --;
            }
            return String.valueOf(age);
        } catch (ParseException e) {
            e.printStackTrace();
            return "";
        }

    }
}
