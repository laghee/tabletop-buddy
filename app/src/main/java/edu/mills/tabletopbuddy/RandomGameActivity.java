package edu.mills.tabletopbuddy;

import android.app.Activity;
import android.content.Intent;
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
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

public class RandomGameActivity extends Activity {
    String selectedMinTime;
    String selectedMinPlayer;
    String selectedMaxPlayer;
    String selectedMaxTime;
    Spinner minTimeSpinner;
    Spinner minPlayerSpinner;
    Spinner maxTimeSpinner;
    Spinner maxPlayerSpinner;
    SQLiteDatabase db;
    Cursor cursor;
    Integer gamenumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_random_game);
    }


    public void onSubmit(View view){
        minTimeSpinner = (Spinner) findViewById(R.id.min_time_spinner);
        selectedMinTime = String.valueOf(minTimeSpinner.getSelectedItem());
        minPlayerSpinner = (Spinner) findViewById(R.id.min_player_spinner);
        selectedMinPlayer =String.valueOf(minPlayerSpinner.getSelectedItem());
        maxTimeSpinner = (Spinner) findViewById(R.id.max_time_spinner);
        selectedMaxTime = String.valueOf(maxTimeSpinner.getSelectedItem());
        maxPlayerSpinner = (Spinner) findViewById(R.id.max_players_spinner);
        selectedMaxPlayer =String.valueOf(maxPlayerSpinner.getSelectedItem());
//        resultToast = category + selectedMinPlayer + selectedMinTime + selectedMinAge;
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
            String[] selectArgs = new String[]{selectedMinTime, selectedMinPlayer, selectedMaxTime, selectedMaxPlayer};

            try {
                db = SQLiteMyLibraryDatabaseHelper.getReadableDatabase();
//                cursor = db.query("LIBRARY", new String[]{"_id", "MIN_AGE"},
//                        "MIN_AGE ==" +selectedMinAge,null, null, null, null, null);
                cursor = db.rawQuery("SELECT _id FROM LIBRARY WHERE MIN_TIME >=? AND MIN_PLAYERS>=? AND MAX_TIME<=? AND MAX_PLAYERS<=?", selectArgs);
                return cursor;
            } catch (SQLiteException e) {
                return null;
            }
        }

        @Override
        protected void onPostExecute(Cursor cursor) {
            if (cursor.moveToFirst()) {
                int max = cursor.getCount() - 1;
                //int position = (0 + max)/2 ;


                int position = (int) (Math.random() * max);
                cursor.moveToPosition(position);
                gamenumber = cursor.getInt(0);
                Intent intent = new Intent(RandomGameActivity.this, GameDetailActivity.class);
                intent.putExtra(GameDetailActivity.EXTRA_GAMENO, gamenumber);
                startActivity(intent);

//                Toast toast = Toast.makeText(RandomGameActivity.this, String.valueOf(max)+1 +" games match your criteria", Toast.LENGTH_SHORT);
//                toast.show();
            }
            else{
                Toast toast = Toast.makeText(RandomGameActivity.this, "sorry, no games match your criteria :(", Toast.LENGTH_SHORT);
                toast.show();
            }
            db.close();
            cursor.close();

        }


    }

//    //Generates a random number to query the database
//    public int generateRandomNumber(int max) {
//
//        //if the game is selected within personal collection
////        if (collection) {
////            //personal collection size
////            max = 100;
////        }
////        else {
////            max = 3333;
////        }
//        return (int)(Math.random() * max);
//    }
}

