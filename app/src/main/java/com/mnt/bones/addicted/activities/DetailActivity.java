package com.mnt.bones.addicted.activities;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.mnt.bones.addicted.Movie;
import com.mnt.bones.addicted.R;
import com.mnt.bones.addicted.data.AddictedContract;
import com.mnt.bones.addicted.fragments.DetailFragment;

public class DetailActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor>{

    private static final String TAG = DetailActivity.class.getSimpleName();

    private static final int FAVORITE_LOADER_ID = 0;

    private Movie mMovie;
    private Cursor mCursor;
    private boolean isFavorite = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Intent intent = getIntent();

        if (intent != null && intent.hasExtra(Movie.MOVIE_KEY)) {
            mMovie = intent.getParcelableExtra(Movie.MOVIE_KEY);
            Log.d(TAG, "mMovie: "+ mMovie.toString());
        }
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new DetailFragment())
                    .commit();
        }

        getSupportLoaderManager().initLoader(FAVORITE_LOADER_ID, null, this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_detail, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {

        MenuItem item = menu.findItem(R.id.action_favorite);

        //if count > 0 we have it on DB
        if (mCursor != null && mCursor.getCount() > 0){
            item.setIcon(android.R.drawable.star_on);
            isFavorite = true;
        }

        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        switch (id){
            case R.id.action_settings:
                startActivity(new Intent(this, SettingsActivity.class));
                return true;

            case R.id.action_favorite:
                OnClickFavorite(item);
                return true;

            default:
                return super.onOptionsItemSelected(item);

        }

    }

    private void OnClickFavorite(final MenuItem item) {
        Log.d(TAG, "Handle Favorite click event");
        if (item != null) {
            if (!isFavorite) {
                Log.d(TAG, "icon is off");
                item.setIcon(android.R.drawable.star_on);
                onClickAddMovie();
                isFavorite = true;
            } else{
                item.setIcon(android.R.drawable.star_off);
                onClickRemoveMovie();
                isFavorite = false;
            }
        }
    }

    private void onClickRemoveMovie() {

        Uri uri = AddictedContract.MoviesEntry.CONTENT_URI;
        uri = uri.buildUpon().appendPath(mMovie.getMovieId()).build();

        getContentResolver().delete(uri, null, null);

        getSupportLoaderManager().restartLoader(FAVORITE_LOADER_ID, null, DetailActivity.this);

    }

    private void onClickAddMovie() {

        ContentValues contentValues = new ContentValues();

        contentValues.put(AddictedContract.MoviesEntry.COLUMN_MOVIE_ID,
                mMovie.getMovieId());
        contentValues.put(AddictedContract.MoviesEntry.COLUMN_MOVIE_TITLE,
                mMovie.getOriginalTitle());
        contentValues.put(AddictedContract.MoviesEntry.COLUMN_MOVIE_POSTER,
                mMovie.getPoster());
        contentValues.put(AddictedContract.MoviesEntry.COLUMN_MOVIE_OVERVIEW,
                mMovie.getOverview());
        contentValues.put(AddictedContract.MoviesEntry.COLUMN_MOVIE_RATING,
                mMovie.getRating());
        contentValues.put(AddictedContract.MoviesEntry.COLUMN_MOVIE_RELEASE,
                mMovie.getReleaseDate());

        Uri uri = getContentResolver().insert(AddictedContract.MoviesEntry.CONTENT_URI,
                contentValues);

        if (uri != null){
            Toast.makeText(getBaseContext(), uri.toString(), Toast.LENGTH_LONG).show();
        }

    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
//        return new AsyncTaskLoader<Cursor>(this) {
//            @Override
//            public Cursor loadInBackground() {
//
//                String[] movieProjection = new String[]{mMovie.getMovieId()};
//                Log.d(TAG, "loadInBackground");
//
//                try {
//                    return getContentResolver().query(AddictedContract.MoviesEntry.CONTENT_URI,
//                            null,
//                            AddictedContract.MoviesEntry.COLUMN_MOVIE_ID,
//                            movieProjection,
//                            null);
//                }catch (Exception e){
//                    Log.e(TAG, "Failed to load data");
//                    return null;
//                }
//            }
//        };

        Uri uri = AddictedContract.MoviesEntry.CONTENT_URI;
        uri = uri.buildUpon().appendPath(mMovie.getMovieId()).build();

        return new CursorLoader(this, uri, null, null, null, null);

    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        Log.d(TAG, "onLoadFinished");
        mCursor = data;
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }
}
