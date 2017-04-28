
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
import java.util.List;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.unbescape.xml.XmlEscape;
import org.w3c.dom.Text;

import java.util.Arrays;

import edu.mills.tabletopbuddy.bggclient.BGG;
import edu.mills.tabletopbuddy.bggclient.common.ThingType;
import edu.mills.tabletopbuddy.bggclient.fetch.FetchException;
import edu.mills.tabletopbuddy.bggclient.fetch.domain.FetchItem;

public class GameDetailActivity extends Activity {
    public static final String EXTRA_GAMENO = "gameNo";
    public static final String EXTRA_CLASSNAME = "class";
    //    private Cursor cursor;
//    private SQLiteDatabase db;
    String gameImageUrl;
    String gameName;
    String gameDescription;
    String gameThemes;
    String gamePubYr;
    String playerNum;
    String timeNum;
    String ageNum;

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
                e.printStackTrace();
                Log.d("Fetch Exception: ", e.getMessage());
                return null;
            }
        }

        @Override
        protected void onPostExecute(FetchItem fetchedItem) {

            String unescapedDes;

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
                TextView name = (TextView) findViewById(R.id.game_name);
                gameName = fetchedItem.getName();
                name.setText(gameName);

//                //Populate the game description
//                TextView description = (TextView) findViewById(R.id.description);
//                gameDescription = fetchedItem.getDescription();
//                description.setText(gameDescription);

                //Populate the game categories
//            theme = (TextView)findViewById(R.id.theme);
//            String themeText = fetchedItem.getCategories();
//                theme.setText(themeText);
                //Populate the game pub year
//            TextView year = (TextView)findViewById(R.id.year);
//            String year = fetchedItem.getYear();
//            year.setText(year);

                //Populate the game min and max players
                TextView players = (TextView) findViewById(R.id.players);
                playerNum = fetchedItem.getMinPlayers().getValue() + " - " +
                        fetchedItem.getMaxPlayers().getValue() + " players";
                players.setText(playerNum);

                //Populate the game min and max time
                TextView time = (TextView) findViewById(R.id.time);
                timeNum = fetchedItem.getPlayingTime().getValue() + " mins";
                time.setText(timeNum);

                //Populate the game min age
                TextView minAge = (TextView) findViewById(R.id.ages);
                ageNum = "ages: " + fetchedItem.getMinAge().getValue() + "+";
                minAge.setText(ageNum);

                //Populate the game theme
                TextView theme = (TextView) findViewById(R.id.theme);
                List<String> themeList = fetchedItem.getCategories();
                theme.setText(themeList.toString());

                //Populate the game description
                TextView description = (TextView) findViewById(R.id.description);
                unescapedDes = XmlEscape.unescapeXml(fetchedItem.getDescription());
                description.setText(unescapedDes);
                description.setText(unescapedDes);
            } else

            {
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
                        new String[]{"IMAGE", "NAME", "DESCRIPTION", "MIN_PLAYERS",
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
//                    gameThemes = cursor.getString(3);
//                    gamePubYr = cursor.getString(4);
//              String bggId = cursor.getString(x);
                    playerNum = cursor.getString(3) + " - " + cursor.getString(4);
                    timeNum = cursor.getString(5);
                    ageNum = cursor.getString(6);


                    //Populate the game image
                    ImageView photo = (ImageView) findViewById(R.id.photo);
                    Log.d("Image: ", gameImageUrl);
                    Picasso.with(GameDetailActivity.this).load(gameImageUrl).into(photo);

                    //Populate the game name
                    TextView name = (TextView) findViewById(R.id.game_name);
                    name.setText(gameName);

                    //Populate the game description
                    TextView description = (TextView) findViewById(R.id.description);
                    description.setText(gameDescription);

                    //Populate the game categories
                    TextView theme = (TextView) findViewById(R.id.theme);
                    theme.setText(gameThemes);

//                //Populate the game pub year
//                TextView year = (TextView)findViewById(R.id.year);
//                year.setText(gamePubYr);

                    //Populate the game min and max players
                    TextView players = (TextView) findViewById(R.id.players);
                    players.setText(playerNum);

                    //Populate the game min and max time
                    TextView time = (TextView) findViewById(R.id.time);
                    time.setText(timeNum);

                    //Populate the game min age
                    TextView minAge = (TextView) findViewById(R.id.ages);
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
//        String playerValues = String.valueOf(players.getText());
        String[] playersMinMax = playerNum.split(" - ");
        String minplayers = playersMinMax[0];
        String maxplayers = playersMinMax[1];

        ContentValues gameValues = new ContentValues();
        gameValues.put("IMAGE", gameImageUrl);
        gameValues.put("NAME", gameName);
        gameValues.put("DESCRIPTION", gameDescription);
//        gameValues.put("THEME", themeText);
//        gameValues.put("YEAR_PUBLISHED", year);
//        gameValues.put("BGG_RATING", String.valueOf(bggrating.getText()));
        gameValues.put("BGG_ID", gameNo);
        gameValues.put("MIN_PLAYERS", minplayers);
        gameValues.put("MAX_PLAYERS", maxplayers);
        gameValues.put("PLAY_TIME", timeNum);
        gameValues.put("MIN_AGE", ageNum);

        Log.d("GameDetailActivity", "About to call execute AddGameAsyncTask");
        new AddGameToLibraryTask().execute(gameValues);
    }

    //Inner class to add the game
    private class AddGameToLibraryTask extends AsyncTask<ContentValues, Void, Boolean> {

        @Override
        protected Boolean doInBackground(ContentValues... games) {
            Log.d("GameDetailActivity", "Entering doInBackground");
            ContentValues gameValues = games[0];
            SQLiteOpenHelper myLibraryDatabaseHelper = new SQLiteMyLibraryDatabaseHelper(GameDetailActivity.this);
            try {
                SQLiteDatabase db = myLibraryDatabaseHelper.getWritableDatabase();
                db.insert("LIBRARY", null, gameValues);
                db.close();
                Log.d("GameDetailActivity", "Successfully wrote to db");

                return true;
            } catch(SQLiteException e) {
                Log.d("GameDetailActivity", "SQLite Exception caught");

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
}
