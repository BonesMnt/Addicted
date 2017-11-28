package com.mnt.bones.addicted.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.mnt.bones.addicted.data.AddictedContract.MoviesEntry;

/**
 * Created by fabio.a on 22/11/17.
 */

public class AddictedDBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "addicted.db";

    private static final int DATABASE_VERSION = 1;

    public AddictedDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {

        final String SQL_CREATE_MOVIE_TABLE =
                "CREATE TABLE " + MoviesEntry.TABLE_NAME +" (" +
                        MoviesEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        MoviesEntry.COLUMN_MOVIE_ID + " TEXT NOT NULL, " +
                        MoviesEntry.COLUMN_MOVIE_TITLE + " TEXT NOT NULL, " +
                        MoviesEntry.COLUMN_MOVIE_POSTER + " TEXT NOT NULL, " +
                        MoviesEntry.COLUMN_MOVIE_OVERVIEW + " TEXT NOT NULL, " +
                        MoviesEntry.COLUMN_MOVIE_RATING + " TEXT NOT NULL, " +
                        MoviesEntry.COLUMN_MOVIE_RELEASE + " TEXT NOT NULL" + ");";

        db.execSQL(SQL_CREATE_MOVIE_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + MoviesEntry.TABLE_NAME);
    }
}
