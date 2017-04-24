package edu.mills.tabletopbuddy;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

public class RandomGameActivity extends Activity {
    String selectedMinTime;
    String selectedMinPlayer;
    String selectedMinAge;
    String category;
    SQLiteDatabase db;
    Cursor cursor;
    ArrayList<String> matchedGames = new ArrayList<String>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_random_game);

        //Category Spinner
        Spinner categorySpinner = (Spinner) findViewById(R.id.categoryspinner);
        category = categorySpinner.getSelectedItem().toString();

        //Min Player Spinner
        Spinner minPlayerSpinner = (Spinner) findViewById(R.id.playerspinner);
        selectedMinPlayer = minPlayerSpinner.getSelectedItem().toString();

        //Min Age Spinner
        Spinner minAgeSpinner = (Spinner) findViewById(R.id.agespinner);
        selectedMinAge = minAgeSpinner.getSelectedItem().toString();

        //Min Time Spinner
        Spinner minTimeSpinner = (Spinner) findViewById(R.id.timespinner);
        selectedMinTime = minTimeSpinner.getSelectedItem().toString();

        new RetrieveGamesTask().execute();
    }

    //Inner class to get miles
    private class RetrieveGamesTask extends AsyncTask<Void, Void, Cursor> {
        @Override
        protected Cursor doInBackground(Void... values) {
            SQLiteOpenHelper SQLiteMyLibraryDatabaseHelper =
                    new SQLiteMyLibraryDatabaseHelper(RandomGameActivity.this);
            try {
                db = SQLiteMyLibraryDatabaseHelper.getReadableDatabase();
                cursor = db.query("LIBRARY", new String[]{""},
                        null, null, "", null, "", "");
                return cursor;
            } catch (SQLiteException e) {
                return null;
            }
        }

        @Override
        protected void onPostExecute(Cursor cursor) {
            if (cursor.moveToFirst()) {
                //record values from new table }
                Log.d("RandomGameActivity", "Retreived games: ");
                db.close();
            }
        }
    }


    //Generates a random number to query the database
    public int generateRandomNumber(Boolean collection) {
        int min = 0;
        int max;

        //if the game is selected within personal collection
        if (collection) {
            //personal collection size
            max = 100;
        }
        else {
            max = 3333;
        }
        return min + (int)(Math.random() * max);
    }

}
