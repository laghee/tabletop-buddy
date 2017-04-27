package edu.mills.tabletopbuddy;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Arrays;

import edu.mills.tabletopbuddy.bggclient.BGG;
import edu.mills.tabletopbuddy.bggclient.common.ThingType;
import edu.mills.tabletopbuddy.bggclient.fetch.FetchException;
import edu.mills.tabletopbuddy.bggclient.fetch.domain.FetchItem;

public class GameDetailActivity extends Activity {
    public static final String EXTRA_GAMENO = "gameNo";
    public static final String EXTRA_CLASSNAME = "class";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_detail);

        //Get the game from the intent
        int gameNo = (Integer) getIntent().getExtras().get(EXTRA_GAMENO);
        String className = (String) getIntent().getExtras().get(EXTRA_CLASSNAME);

        if (className.equals("MyLibraryActivity")) {
            new LibraryGameDetailTask().execute(gameNo);
        } else if (className.equals("SearchResultsActivity")){
            new FetchBGGTask().execute(gameNo);
        } else {
            Toast toast = Toast.makeText(this, "Something broke -- no class", Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    private class FetchBGGTask extends AsyncTask<Integer, Void, FetchItem> {
        @Override
        protected FetchItem doInBackground(Integer... params) {
            int gameId = params[0];
            try {
                FetchItem fetchedItem = BGG.fetch(Arrays.asList(gameId), ThingType.BOARDGAME).iterator().next();
                return fetchedItem;

            } catch (FetchException e) {
                e.printStackTrace();
                Log.d("Fetch Exception: ", e.getMessage());
                return null;
            }
        }

        @Override
        protected void onPostExecute (FetchItem fetchedItem) {
            //Populate the game name
            TextView name = (TextView)findViewById(R.id.game_name);
            name.setText(fetchedItem.getName());

            //Populate the game min and max players
            TextView players = (TextView)findViewById(R.id.players);
            String playerNum = fetchedItem.getMinPlayers().getValue() + "-" + fetchedItem.getMaxPlayers().getValue() + " players";
            players.setText(playerNum);

            //Populate the game min and max time
            TextView time = (TextView)findViewById(R.id.time);
            String timeNum = fetchedItem.getPlayingTime().getValue() + " mins";
            time.setText(timeNum);

            //Populate the game min age
            TextView minAge = (TextView)findViewById(R.id.ages);
            String ageNum =  "ages: " + fetchedItem.getMinAge().getValue() + "+";
            minAge.setText(ageNum);

        }
    }

    private class LibraryGameDetailTask extends AsyncTask<Integer, Void, Cursor> {
        @Override
        protected Cursor doInBackground(Integer... params) {
            int gameId = params[0];
            Log.d("GAME_NO:", Integer.toString(gameId));

            try {
                SQLiteOpenHelper libraryDatabaseHelper = new SQLiteMyLibraryDatabaseHelper(GameDetailActivity.this);
                SQLiteDatabase db = libraryDatabaseHelper.getReadableDatabase();
                Cursor cursor = db.query("LIBRARY",
                        new String[]{"NAME", "MIN_PLAYERS", "MAX_PLAYERS", "MIN_TIME",
                                "MAX_TIME", "MIN_AGE", "BGG_ID"},
                        "_id = ?",
                        new String[]{Integer.toString(gameId)},
                        null, null, null);
                return cursor;
            } catch (SQLiteException e) {
                Log.d("SQLite Exception: ", e.getMessage());
                return null;
            }
        }

        @Override
        protected void onPostExecute(Cursor cursor) {
            if(cursor != null) {
                if (cursor.moveToFirst()) {
                    //Get the game details from the cursor
                    String nameText = cursor.getString(0);
                    int minPlayerNo = cursor.getInt(1);
                    int maxPlayerNo = cursor.getInt(2);
                    int minTimeNo = cursor.getInt(3);
                    int maxTimeNo = cursor.getInt(4);
                    int minAgeNo = cursor.getInt(5);
                    int bggId = cursor.getInt(6);

                    //Populate the game name
                    TextView name = (TextView) findViewById(R.id.game_name);
                    name.setText(nameText);

                    //Populate the game min and max players
                    TextView players = (TextView) findViewById(R.id.players);
                    String playerNum = minPlayerNo + "-" + maxPlayerNo + " players";
                    players.setText(playerNum);

                    //Populate the game min and max time
                    TextView time = (TextView) findViewById(R.id.time);
                    String timeNum = minTimeNo + "-" + maxTimeNo + " mins";
                    time.setText(timeNum);

                    //Populate the game min age
                    TextView minAge = (TextView) findViewById(R.id.ages);
                    String ageNum = "ages: " + minAgeNo + "+";
                    minAge.setText(ageNum);

                }
            } else {
                Toast toast = Toast.makeText(GameDetailActivity.this,
                        "Cursor error", Toast.LENGTH_SHORT);
                toast.show();
            }
        }
    }


    //Update the database when the checkbox is clicked
    public void onFavoriteClicked(View view){
        int gameNo = (Integer)getIntent().getExtras().get("gameNo");
        CheckBox favorite = (CheckBox)findViewById(R.id.favorite);
        ContentValues gameValues = new ContentValues();
        gameValues.put("FAVORITE", favorite.isChecked());

        Object[] taskArray = new Object[2];
        taskArray[0] = gameNo;
        taskArray[1] = gameValues;

        new UpdateLibraryTask().execute(taskArray);
    }

    //Inner class to update the drink.
    private class UpdateLibraryTask extends AsyncTask<Object, Void, Boolean> {

        @Override
        protected Boolean doInBackground(Object... games) {
            int gameNo = (int) games[0];
            ContentValues gameValues = (ContentValues) games[1];

            SQLiteOpenHelper starbuzzDatabaseHelper = new SQLiteMyLibraryDatabaseHelper(GameDetailActivity.this);
            try {
                SQLiteDatabase db = starbuzzDatabaseHelper.getWritableDatabase();
                db.update("LIBRARY", gameValues,
                        "_id = ?", new String[] {Integer.toString(gameNo)});
                db.close();
                return true;
            } catch(SQLiteException e) {
                return false;
            }
        }

        @Override
        protected void onPostExecute(Boolean success) {
            if (!success) {
                Toast toast = Toast.makeText(GameDetailActivity.this,
                        "Database unavailable", Toast.LENGTH_SHORT);
                toast.show();
            }
        }
    }

//    @Override
//    public void onDestroy() {
//        super.onDestroy();
//        cursor.close();
//        db.close();
//    }
}
