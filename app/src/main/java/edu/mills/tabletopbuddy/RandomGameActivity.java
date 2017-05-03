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

public class RandomGameActivity extends Activity {
//    String selectedMinTime;
    String selectedMinPlayer ="0";
    String selectedMaxPlayer="1000";
    String selectedPlayTime="1000";
//    Spinner minTimeSpinner;
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
        maxTimeSpinner = (Spinner) findViewById(R.id.max_time_spinner);
        selectedPlayTime = String.valueOf(maxTimeSpinner.getSelectedItem());
        minPlayerSpinner = (Spinner) findViewById(R.id.min_player_spinner);
        selectedMinPlayer =String.valueOf(minPlayerSpinner.getSelectedItem());
//        maxTimeSpinner = (Spinner) findViewById(R.id.max_time_spinner);
//        selectedMaxTime = String.valueOf(maxTimeSpinner.getSelectedItem());
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
//            String[] selectArgs = new String[]{selectedPlayTime, selectedMinPlayer, selectedMaxPlayer};
            String[] selectArgs = new String[]{selectedMinPlayer, selectedMaxPlayer};

            try {
                db = SQLiteMyLibraryDatabaseHelper.getReadableDatabase();
//                cursor = db.query("LIBRARY", new String[]{"_id", "MIN_AGE"},
//                        "MIN_AGE ==" +selectedMinAge,null, null, null, null, null);
//                cursor = db.rawQuery("SELECT _id FROM LIBRARY WHERE PLAY_TIME<=? AND MIN_PLAYERS>=? AND MAX_PLAYERS<=?", selectArgs);
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
                //int position = (0 + max)/2 ;

                int position = (int) (Math.random() * max);
                cursor.moveToPosition(position);
                gamenumber = cursor.getInt(0);
                Intent intent = new Intent(RandomGameActivity.this, GameDetailActivity.class);
                intent.putExtra(GameDetailActivity.EXTRA_GAMENO, gamenumber);
                intent.putExtra(GameDetailActivity.EXTRA_CLASSNAME, "RandomGameActivity");
                startActivity(intent);
            } else{
                Toast toast = Toast.makeText(RandomGameActivity.this, "sorry, no games match your criteria :(", Toast.LENGTH_SHORT);
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

