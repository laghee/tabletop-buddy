package edu.mills.tabletopbuddy;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
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
    private static String resultToast = "";
    Integer gamenumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_random_game);

        //Category Spinner
        Spinner categorySpinner = (Spinner) findViewById(R.id.categoryspinner);
        category = String.valueOf(categorySpinner.getSelectedItem());
        //Min Player Spinner
        Spinner minPlayerSpinner = (Spinner) findViewById(R.id.playerspinner);
        selectedMinPlayer = String.valueOf(minPlayerSpinner.getSelectedItem());

        //Min Age Spinner
        Spinner minAgeSpinner = (Spinner) findViewById(R.id.agespinner);
        selectedMinAge = String.valueOf(minAgeSpinner.getSelectedItem());
        //String beerType = String.valueOf(color.getSelectedItem());

        //Min Time Spinner
        Spinner minTimeSpinner = (Spinner) findViewById(R.id.timespinner);
        selectedMinTime = String.valueOf(minTimeSpinner.getSelectedItem());


    }


    public void onSubmit(View view){
//        resultToast += category + selectedMinPlayer + selectedMinTime + selectedMinAge;
//        Toast toast = Toast.makeText(this, resultToast, Toast.LENGTH_LONG);
//        toast.setGravity(Gravity.TOP| Gravity.START, 0, 500);
//        toast.show();
        new RetrieveGamesTask().execute();
    }

    //Inner class to get games
    private class RetrieveGamesTask extends AsyncTask<Void, Void, Cursor> {
        @Override
        protected Cursor doInBackground(Void... values) {
            SQLiteOpenHelper SQLiteMyLibraryDatabaseHelper =
                    new SQLiteMyLibraryDatabaseHelper(RandomGameActivity.this);
            try {
                db = SQLiteMyLibraryDatabaseHelper.getReadableDatabase();
                cursor = db.query("LIBRARY", new String[]{"_id"},
                        null, null, null, null, null, null);
                return cursor;
            } catch (SQLiteException e) {
                return null;
            }
        }

        @Override
        protected void onPostExecute(Cursor cursor) {
            int max = cursor.getCount()-1;
            //int position = (0 + max)/2 ;
            cursor.moveToPosition(max);
            gamenumber = cursor.getInt(0);
            Toast toast = Toast.makeText(RandomGameActivity.this, String.valueOf(gamenumber), Toast.LENGTH_SHORT);
            toast.show();
                db.close();
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
