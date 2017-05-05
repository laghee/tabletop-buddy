/*
 * Implementation of the Tabletop Buddy application. Created for Mills
 * CS 115: Mobile Application Development, Spring 2017.
 *
 * @author Kristen Cutler, Jennifer Diaz, Arianne Agogino Gieringer,
 * Kate Manning, Erin Walter
 */
package edu.mills.tabletopbuddy;

import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

/**
 * Fetches games added to local database and populates the view of a personal library in
 * alphabetical order. Users can view the game details after clicking a game title,
 * linking to {@link GameDetailActivity}, where the user can add to or remove from their
 * personal library.
 */
public class MyLibraryActivity extends ListActivity {
    private SQLiteDatabase db;
    private Cursor cursor;
    private CursorAdapter gameCursorAdapter;

    private static final String ERROR_FROM_DATABASE = "Error from database.";
    private static final String LIBRARY_ACTIVITY = "MyLibraryActivity";
    private static final String RAW_QUERY_BY_NAME = "SELECT * FROM LIBRARY ORDER BY NAME";
    private static final String NAME_COL = "NAME";

    //view saved games
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new GameLibraryTask().execute();
    }

    private class GameLibraryTask extends AsyncTask<Void, Void, Cursor> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Cursor doInBackground(Void... games) {
            SQLiteOpenHelper gameLibraryHelper = new SQLiteMyLibraryDatabaseHelper(MyLibraryActivity.this);
            db = gameLibraryHelper.getReadableDatabase();

            try {
                cursor = db.rawQuery(RAW_QUERY_BY_NAME, null);
            } catch (SQLiteException e) {
                return null;
            }
            return cursor;
        }

        @Override
        protected void onPostExecute(Cursor cursor) {
            super.onPostExecute(cursor);

            ListView listGames = getListView();
            if (cursor != null) {
                gameCursorAdapter = new SimpleCursorAdapter(MyLibraryActivity.this,
                        android.R.layout.simple_list_item_1,
                        cursor,
                        new String[]{NAME_COL},
                        new int[]{android.R.id.text1},
                        0);
                listGames.setAdapter(gameCursorAdapter);
            } else {
                Toast toast = Toast.makeText(MyLibraryActivity.this, ERROR_FROM_DATABASE, Toast.LENGTH_SHORT);
                toast.show();
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (gameCursorAdapter != null) {
            gameCursorAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onDestroy() {
        if (gameCursorAdapter != null) {
            gameCursorAdapter.notifyDataSetChanged();
        }
        super.onDestroy();
        if (cursor != null) {
            cursor.close();
            db.close();
        }
    }

    @Override
    public void onListItemClick(ListView listView,
                                View itemView,
                                int position,
                                long id) {
        Intent intent = new Intent(MyLibraryActivity.this, GameDetailActivity.class);
        intent.putExtra(GameDetailActivity.EXTRA_GAMENO, (int) id);
        intent.putExtra(GameDetailActivity.EXTRA_CLASSNAME, LIBRARY_ACTIVITY);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.navigation_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.to_search:
                startActivity(new Intent(this, SearchResultsActivity.class));
                return true;
            case R.id.main:
                startActivity(new Intent(this, MainActivity.class));
                return true;
            case R.id.random:
                startActivity(new Intent(this, RandomGameActivity.class));
                return true;
            case R.id.library:
                startActivity(new Intent(this, MyLibraryActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}