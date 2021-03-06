package com.rashwan.reactive_popular_movies.data.local;

import android.app.Application;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.rashwan.reactive_popular_movies.data.model.MovieDB;

import javax.inject.Inject;

/**
 * Created by rashwan on 7/16/16.
 */

public class MovieDatabaseHelper extends SQLiteOpenHelper{

    private static final String DATABASE_NAME = "moviesDatabase";
    private static final int DATABASE_VERSION = 1;

    @Inject
    public MovieDatabaseHelper(Application context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(MovieDB.CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + MovieDB.TABLE_NAME);
        onCreate(db);
    }

}
