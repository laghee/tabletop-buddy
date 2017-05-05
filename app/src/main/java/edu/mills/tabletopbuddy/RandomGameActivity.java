/**
 * Implementation of the Tabletop Buddy application. Created for Mills
 * CS 115: Mobile Application Development, Spring 2017.
 *
 * @author Kristen Cutler, Jennifer Diaz, Arianne Agogino Gieringer,
 * Kate Manning, Erin Walter
 */
package edu.mills.tabletopbuddy;

import android.app.Activity;
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
import android.widget.Spinner;
import android.widget.Toast;

/**
 * Enables a user to randomly select a game from their local library, by choosing constraints.
 * A random game will be selected matching the constraints. After submission, the game's details
 * will appear when {@link GameDetailActivity} is launched.
 */
public class RandomGameActivity extends Activity {
    private String selectedMinPlayer ="0";
    private String selectedMaxPlayer="1000";
    private String selectedPlayTime="1000";
    private Spinner minPlayerSpinner;
    private Spinner maxTimeSpinner;
    private Spinner maxPlayerSpinner;
    private SQLiteDatabase db;
    private Cursor cursor;
    private Integer gamenumber;

    private static final String NO_GAME = "Sorry, no games match your criteria :(";
    private static final String RANDOM_ACTIVITY = "RandomGameActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_random_game);
    }

    /**
     * Users submit their specific constraints, triggering the retrieval of a game.
     *
     * @param view the view
     */
    public void onSubmit(View view){
        maxTimeSpinner = (Spinner) findViewById(R.id.max_time_spinner);
        selectedPlayTime = String.valueOf(maxTimeSpinner.getSelectedItem());
        minPlayerSpinner = (Spinner) findViewById(R.id.min_player_spinner);
        selectedMinPlayer =String.valueOf(minPlayerSpinner.getSelectedItem());
        maxPlayerSpinner = (Spinner) findViewById(R.id.max_players_spinner);
        selectedMaxPlayer =String.valueOf(maxPlayerSpinner.getSelectedItem());

        new RetrieveGamesTask().execute();
    }

    //Inner class to get games
    private class RetrieveGamesTask extends AsyncTask<Void, Void, Cursor> {

        @Override
        protected Cursor doInBackground(Void... values) {
            SQLiteOpenHelper SQLiteMyLibraryDatabaseHelper =
                    new SQLiteMyLibraryDatabaseHelper(RandomGameActivity.this);
            String[] selectArgs = new String[]{selectedMinPlayer, selectedMaxPlayer};

            try {
                db = SQLiteMyLibraryDatabaseHelper.getReadableDatabase();

                cursor = db.rawQuery("SELECT _id FROM LIBRARY WHERE MIN_PLAYERS>=? AND MAX_PLAYERS<=?", selectArgs);
                return cursor;
            } catch (SQLiteException e) {
                return null;
            }
        }

        @Override
        protected void onPostExecute(Cursor cursor) {
            if (cursor.moveToFirst()) {
                int max = cursor.getCount() ;

                int position = (int) (Math.random() * max);
                cursor.moveToPosition(position);
                gamenumber = cursor.getInt(0);
                Intent intent = new Intent(RandomGameActivity.this, GameDetailActivity.class);
                intent.putExtra(GameDetailActivity.EXTRA_GAMENO, gamenumber);
                intent.putExtra(GameDetailActivity.EXTRA_CLASSNAME, RANDOM_ACTIVITY);
                startActivity(intent);
            } else{
                Toast toast = Toast.makeText(RandomGameActivity.this, NO_GAME, Toast.LENGTH_SHORT);
                toast.show();
            }
            db.close();
            cursor.close();
        }
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

