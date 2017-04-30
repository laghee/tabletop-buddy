
package edu.mills.tabletopbuddy;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.unbescape.html.HtmlEscape;
import org.unbescape.xml.XmlEscape;

import java.util.Arrays;
import java.util.List;

import edu.mills.tabletopbuddy.bggclient.BGG;
import edu.mills.tabletopbuddy.bggclient.common.ThingType;
import edu.mills.tabletopbuddy.bggclient.fetch.FetchException;
import edu.mills.tabletopbuddy.bggclient.fetch.domain.FetchItem;

import static android.text.TextUtils.join;

public class GameDetailActivity extends Activity {
    public static final String EXTRA_GAMENO = "gameNo";
    public static final String EXTRA_CLASSNAME = "class";
    //    private Cursor cursor;
//    private SQLiteDatabase db;
    private String gameImageUrl;
    private String gameName;
    private String gameDescription;
    private String gameThemes;
    private String gamePubYr;
    private String playerNum;
    private String timeNum;
    private String ageNum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_detail);

        //Get the game from the intent
        int gameNo = (Integer) getIntent().getExtras().get(EXTRA_GAMENO);
        String className = (String) getIntent().getExtras().get(EXTRA_CLASSNAME);

        if (className.equals("MyLibraryActivity")) {
            new LibraryGameDetailTask().execute(gameNo);
        } else if (className.equals("SearchResultsActivity")) {
            new FetchBGGTask().execute(gameNo);
        } else if (className.equals("RandomGameActivity")) {
            new LibraryGameDetailTask().execute(gameNo);
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
                FetchItem fetchedItem = BGG.fetch(Arrays.asList(gameId), ThingType.BOARDGAME, ThingType.BOARDGAME_EXPANSION).iterator().next();
                return fetchedItem;

            } catch (FetchException e) {
                Log.d("BGGdetail", "Fetch Exception: " + e.getMessage());
                return null;
            }
        }

        @Override
        protected void onPostExecute(FetchItem fetchedItem) {

            if (fetchedItem != null) {
                //Populate the game image
                ImageView photo = (ImageView) findViewById(R.id.photo);
                Log.d("Image: ", fetchedItem.getImageUrl());
                gameImageUrl = "https:" + fetchedItem.getImageUrl();
//            gameImageUrl = gameImageUrl.substring(0, gameImageUrl.length() - 4);
//            gameImageUrl = gameImageUrl.concat("_md.jpg");
                Log.d("Image: ", gameImageUrl);
                Picasso.with(GameDetailActivity.this).load(gameImageUrl).into(photo);

                //Populate the game name
                TextView name = (TextView)findViewById(R.id.game_name);
                gameName = fetchedItem.getName();
                Log.d("GameDetail", "BGG detail, name: " + gameName);
                name.setText(gameName);

//                //Populate the game description
                TextView description = (TextView) findViewById(R.id.description);
                gameDescription = XmlEscape.unescapeXml(fetchedItem.getDescription());
                gameDescription = HtmlEscape.unescapeHtml(gameDescription);
                description.setText(gameDescription);

                //Populate the game theme
                TextView theme = (TextView) findViewById(R.id.theme);
                List<String> themeList = fetchedItem.getCategories();
                gameThemes = join(", ", themeList);
                Log.d("GameDetail", "BGG detail, themes: " + gameThemes);
                theme.setText(gameThemes);

                //Populate the game pub year
//            TextView year = (TextView)findViewById(R.id.year);
//            String year = fetchedItem.getYear();
//            year.setText(year);

                //Populate the game min and max players
                TextView players = (TextView)findViewById(R.id.players);
                playerNum = fetchedItem.getMinPlayers().getValue() + " - " +
                        fetchedItem.getMaxPlayers().getValue() + " players";
                Log.d("GameDetail", "BGG detail, players: " + playerNum);
                players.setText(playerNum);

                //Populate the game min and max time
                TextView time = (TextView)findViewById(R.id.time);
                timeNum = fetchedItem.getPlayingTime().getValue() + " mins";
                Log.d("GameDetail", "BGG detail, time: " + timeNum);
                time.setText(timeNum);

                //Populate the game min age
                TextView minAge = (TextView)findViewById(R.id.ages);
                ageNum =  "ages: " + fetchedItem.getMinAge().getValue() + "+";
                Log.d("GameDetail", "BGG detail, ages: " + ageNum);
                minAge.setText(ageNum);
            } else {
                Toast toast = Toast.makeText(GameDetailActivity.this,
                        "Error retrieving game", Toast.LENGTH_SHORT);
                toast.show();
            }
        }
    }


    private class LibraryGameDetailTask extends AsyncTask<Integer, Void, Cursor> {
        @Override
        protected Cursor doInBackground(Integer... params) {
            int gameId = params[0];
            Log.d("GAME_NO:", Integer.toString(gameId));

            try {
                SQLiteOpenHelper libraryDatabaseHelper =
                        new SQLiteMyLibraryDatabaseHelper(GameDetailActivity.this);
                SQLiteDatabase db = libraryDatabaseHelper.getReadableDatabase();
                Cursor cursor = db.query("LIBRARY",
                        new String[]{"IMAGE", "NAME", "DESCRIPTION", "THEME", "MIN_PLAYERS",
                                "MAX_PLAYERS", "PLAY_TIME", "MIN_AGE"}, "_id = ?",
                        new String[]{Integer.toString(gameId)},
                        null, null, null);
                return cursor;
            } catch (SQLiteException e) {
                Log.d("LibraryGameDetail: ", "Caught SQLite Exception" + e.getMessage());
                return null;
            }

        }

        @Override
        protected void onPostExecute(Cursor cursor) {
            if (cursor != null) {
                if (cursor.moveToFirst()) {
                    //Get the game details from the cursor
                    gameImageUrl = cursor.getString(0);
                    gameName = cursor.getString(1);
                    gameDescription = cursor.getString(2);
                    gameThemes = cursor.getString(3);
//                    gamePubYr = cursor.getString(x);
//              String bggId = cursor.getString(x);
                    playerNum = cursor.getString(4) + " - " + cursor.getString(5);
                    timeNum = cursor.getString(6);
                    ageNum = cursor.getString(7);

                    //Populate the game image
                    ImageView photo = (ImageView) findViewById(R.id.photo);
                    Log.d("GameDetail", "Library detail, Image: " + gameImageUrl);
                    Picasso.with(GameDetailActivity.this).load(gameImageUrl).into(photo);

                    //Populate the game name
                    TextView name = (TextView) findViewById(R.id.game_name);
                    Log.d("GameDetail", "Library detail, Name: " + gameName);
                    name.setText(gameName);

                    //Populate the game description
                    TextView description = (TextView) findViewById(R.id.description);
                    description.setText(gameDescription);

                    //Populate the game themes
                    TextView theme = (TextView) findViewById(R.id.theme);
                    Log.d("GameDetail", "Library detail, Themes: " + gameThemes);
                    theme.setText(gameThemes);

//                //Populate the game pub year
//                TextView year = (TextView)findViewById(R.id.year);
//                year.setText(gamePubYr);

                    //Populate the game min and max players
                    TextView players = (TextView) findViewById(R.id.players);
                    Log.d("GameDetail", "Library detail, Players: " + playerNum);
                    players.setText(playerNum);

                    //Populate the game min and max time
                    TextView time = (TextView) findViewById(R.id.time);
                    Log.d("GameDetail", "Library detail, Time: " + timeNum);
                    time.setText(timeNum);

                    //Populate the game min age
                    TextView minAge = (TextView) findViewById(R.id.ages);
                    Log.d("GameDetail", "Library detail, Ages: " + ageNum);
                    minAge.setText(ageNum);

                    //Populate the favorite checkbox
                    CheckBox addToLibrary = (CheckBox)findViewById(R.id.addToLibrary);
                    addToLibrary.setChecked(true);
                } else {
                    Toast toast = Toast.makeText(GameDetailActivity.this, "Database unavailable", Toast.LENGTH_SHORT);
                    toast.show();
                }
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
//        cursor.close();
//        db.close();
    }


    //Update the database when the checkbox is clicked
    public void onAddToLibraryClicked(View view){
        int gameNo = (Integer)getIntent().getExtras().get("gameNo");
        Log.d("GameDetail: ", "Add2LibraryClick, gameNUM: " + gameNo);
        CheckBox addToLibrary = (CheckBox)findViewById(R.id.addToLibrary);

        if (addToLibrary.isChecked()) {
            String[] playersMinMax = playerNum.split(" - ");
            String minplayers = playersMinMax[0];
            String maxplayers = playersMinMax[1];
            maxplayers = maxplayers.substring(0, maxplayers.length() - 8);
            timeNum = timeNum.substring(0, timeNum.length() - 5);

            ContentValues gameValues = new ContentValues();
            gameValues.put("IMAGE", gameImageUrl);
            gameValues.put("NAME", gameName);
            gameValues.put("DESCRIPTION", gameDescription);
            gameValues.put("THEME", gameThemes);
//        gameValues.put("YEAR_PUBLISHED", gamePubYr);
            gameValues.put("BGG_ID", gameNo);
            gameValues.put("MIN_PLAYERS", minplayers);
            gameValues.put("MAX_PLAYERS", maxplayers);
            gameValues.put("PLAY_TIME", timeNum);
            gameValues.put("MIN_AGE", ageNum);
            Log.d("GameDetail", "Calling AddGameAsyncTask to add gameNUM " + gameNo);
            new AddGameToLibraryTask().execute(gameValues);
        } else {
            ContentValues gameNum = new ContentValues();
            gameNum.put("LIBRARYID", gameNo);
            Log.d("GameDetail", "Calling RemoveGameAsyncTask to remove gameNUM " + gameNo);
            new RemoveGameFromLibraryTask().execute(gameNum);
        }
    }


    //Inner class to add the game to MyLibrary
    private class AddGameToLibraryTask extends AsyncTask<ContentValues, Void, Integer> {

        @Override
        protected Integer doInBackground(ContentValues... games) {
            Log.d("GameDetailActivity", "Entering doInBackground");
            ContentValues gameValues = games[0];
            SQLiteOpenHelper myLibraryDatabaseHelper = new SQLiteMyLibraryDatabaseHelper(GameDetailActivity.this);
            try {
                SQLiteDatabase db = myLibraryDatabaseHelper.getWritableDatabase();
                db.insert("LIBRARY", null, gameValues);
                db.close();
                Log.d("GameDetailActivity", "Successfully wrote to db");
                return gameValues.getAsInteger("BGG_ID");
            } catch(SQLiteException e) {
                Log.d("GameDetailActivity", "SQLite Exception caught");
                return null;
            }
        }

        @Override
        protected void onPostExecute(Integer gameId) {
            if (gameId == null) {
                Toast toast = Toast.makeText(GameDetailActivity.this,
                        "Database unavailable", Toast.LENGTH_SHORT);
                toast.show();
            } else {
                Intent intent = new Intent(GameDetailActivity.this, MyLibraryActivity.class);
                intent.putExtra(GameDetailActivity.EXTRA_GAMENO, gameId);
                startActivity(intent);
            }
        }
    }

    //Inner class to remove the game from the MyLibrary
    private class RemoveGameFromLibraryTask extends AsyncTask<ContentValues, Void, Integer> {

        @Override
        protected Integer doInBackground(ContentValues... games) {
            Log.d("GameDetailActivity", "Entering doInBackground");
            Integer gameId = games[0].getAsInteger("LIBRARYID");
            SQLiteOpenHelper myLibraryDatabaseHelper = new SQLiteMyLibraryDatabaseHelper(GameDetailActivity.this);
            try {
                SQLiteDatabase db = myLibraryDatabaseHelper.getWritableDatabase();
                db.delete("LIBRARY", "_id = ?", new String[] {gameId.toString()});
                db.close();
                Log.d("GameDetailActivity", "Successfully removed game from db");
                return gameId;
            } catch(SQLiteException e) {
                Log.d("GameDetailActivity", "SQLite Exception caught while removing game from db");
                return null;
            }
        }

        @Override
        protected void onPostExecute(Integer libraryId) {
            if (libraryId == null) {
                Toast toast = Toast.makeText(GameDetailActivity.this,
                        "Database unavailable", Toast.LENGTH_SHORT);
                toast.show();
            } else {
                Intent intent = new Intent(GameDetailActivity.this, MyLibraryActivity.class);
                intent.putExtra(GameDetailActivity.EXTRA_GAMENO, libraryId);
                startActivity(intent);
            }
        }
    }

}
