package com.mnt.bones.addicted.data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * Created by fabio.a on 23/11/17.
 */

public class AddictedContentProvider extends ContentProvider {

    private final String TAG = AddictedContentProvider.class.getSimpleName();

    //To use DB
    private AddictedDBHelper mDbHelper;

    //directories to query
    public static final int MOVIES = 100;
    public static final int MOVIES_WITH_ID = 101;

    private static final UriMatcher sUriMatcher = buildUriMatcher();

    /**
           Initialize a new matcher object without any matches,
           then use .addURI(String authority, String path, int match) to add matches
           */
    public static UriMatcher buildUriMatcher(){

        UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

        uriMatcher.addURI(AddictedContract.AUTHORITY, AddictedContract.PATH_MOVIES, MOVIES);
        uriMatcher.addURI(AddictedContract.AUTHORITY, AddictedContract.PATH_MOVIES + "/#", MOVIES_WITH_ID);

        return uriMatcher;
    }


    @Override
    public boolean onCreate() {

        Log.d(TAG, "onCreate()");
        Context context = getContext();
        mDbHelper = new AddictedDBHelper(context);

        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {

        Log.d(TAG, "query()");
        final SQLiteDatabase db = mDbHelper.getReadableDatabase();
        int match = sUriMatcher.match(uri);

        Cursor returnCursor;

        switch (match){
            case MOVIES:
                Log.d(TAG, "match: MOVIES");

                returnCursor = db.query(AddictedContract.MoviesEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder);

                break;

            case MOVIES_WITH_ID:
                Log.d(TAG, "match: MOVIE WITH ID");

                String id = uri.getPathSegments().get(1);

//                String mSelection = "_id=?";
                String mSelection = AddictedContract.MoviesEntry.COLUMN_MOVIE_ID + "=?";
                String[] mSelectionArgs = new String[]{id};

                returnCursor = db.query(AddictedContract.MoviesEntry.TABLE_NAME,
                        projection,
                        mSelection,
                        mSelectionArgs,
                        null,
                        null,
                        sortOrder);

                break;

            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        returnCursor.setNotificationUri(getContext().getContentResolver(), uri);

        return returnCursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {

        Log.d(TAG, "getType()");

        int match = sUriMatcher.match(uri);

        switch (match){
            case MOVIES:
                Log.d(TAG, "match: MOVIES");

                return "vnd.android.cursor.dir" + "/" + AddictedContract.AUTHORITY + "/" + AddictedContract.PATH_MOVIES;

            case MOVIES_WITH_ID:
                Log.d(TAG, "match: MOVIE WITH ID");

                return "vnd.android.cursor.item" + "/" + AddictedContract.AUTHORITY + "/" + AddictedContract.PATH_MOVIES;

            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {

        Log.d(TAG, "insert()");
        final SQLiteDatabase db = mDbHelper.getWritableDatabase();
        int match = sUriMatcher.match(uri);

        Uri returnUri;

        switch (match){
            case MOVIES:
                Log.d(TAG, "match: MOVIES");
                long id = db.insert(AddictedContract.MoviesEntry.TABLE_NAME, null, values);
                if (id > 0){
                    returnUri = ContentUris.withAppendedId(AddictedContract.MoviesEntry.CONTENT_URI, id);
                } else{
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                }
                break;

            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        getContext().getContentResolver().notifyChange(uri, null);

        return returnUri;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {

        Log.d(TAG, "delete()");
        final SQLiteDatabase db = mDbHelper.getWritableDatabase();

        int match = sUriMatcher.match(uri);

        int returnDeleted;

        switch (match){
            case MOVIES_WITH_ID:
                Log.d(TAG, "match: match: MOVIE WITH ID");

                String id = uri.getPathSegments().get(1);

                //String mSelection = "_id=?";
                String mSelection = AddictedContract.MoviesEntry.COLUMN_MOVIE_ID + "=?";
                String[] mSelectionArgs = new String[]{id};

                returnDeleted = db.delete(AddictedContract.MoviesEntry.TABLE_NAME,
                        mSelection,
                        mSelectionArgs);

                break;

            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        if (returnDeleted != 0){
            getContext().getContentResolver().notifyChange(uri, null);
        }

        return returnDeleted;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {

        Log.d(TAG, "update()");

        final SQLiteDatabase db = mDbHelper.getWritableDatabase();
        int returnUpdated;

        int match = sUriMatcher.match(uri);

        switch (match){
            case MOVIES_WITH_ID:
                String id = uri.getPathSegments().get(1);
                //String mSelection = "_id=?";
                String mSelection = AddictedContract.MoviesEntry.COLUMN_MOVIE_ID + "=?";
                String[] mSelectionArgs = new String[]{id};

                returnUpdated = db.update(AddictedContract.MoviesEntry.TABLE_NAME,
                        values,
                        mSelection,
                        mSelectionArgs);
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        if (returnUpdated != 0){
            getContext().getContentResolver().notifyChange(uri, null);
        }

        return returnUpdated;
    }


}
