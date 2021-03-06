/*
 * Implementation of the Tabletop Buddy application. Created for Mills
 * CS 115: Mobile Application Development, Spring 2017.
 *
 * @author Kristen Cutler, Jennifer Diaz, Arianne Agogino Gieringer,
 * Kate Manning, Erin Walter
 */
package edu.mills.tabletopbuddy;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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
import static edu.mills.tabletopbuddy.LibraryDBUtilities.*;

/**
 * Activity for viewing game details. Provides an interface for adding or removing games
 * from a local library by clicking a checkbox. If a user removes a game,
 * {@link MyLibraryActivity} is launched and they are taken back to their library.
 */
public class GameDetailActivity extends Activity {
    private boolean bgg = true;
    private SQLiteDatabase db;
    private String gameImageUrl;
    private String gameName;
    private String gameDescription;
    private String gameThemes;
    private String playerNum;
    private String timeNum;
    private String ageNum;

    /**
     * Label for game's id number. Called by {@link MyLibraryActivity},
     * {@link GameDetailActivity} and {@link RandomGameActivity}.
     */
    public static final String EXTRA_GAMENO = "gameNo";

    /**
     * Label for activity name. Called by {@link MyLibraryActivity},
     * {@link GameDetailActivity} and {@link RandomGameActivity}.
     */
    public static final String EXTRA_CLASSNAME = "class";

    private static final String HTTPS = "https:";
    private static final String SEARCH_ACTIVITY = "SearchResultsActivity";
    private static final String LIBRARY_ACTIVITY = "MyLibraryActivity";
    private static final String RANDOM_ACTIVITY = "RandomGameActivity";
    private static final String PLAYERS = " players";
    private static final String MINS = " mins";
    private static final String AGES = "Ages: ";
    private static final String ERROR_RETRIEVE_GAME = "Error retrieving game.";
    private static final String DB_NOTAVAIL =  "Database unavailable.";
    private static final String LIBRARYID = "LIBRARYID";
    private static final String ERROR_SAVING_GAME = "Error saving game.";
    private static final String GAME_SAVED = "Game saved!";
    private static final String GAME_REMOVED = "Game removed.";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_detail);

        //Get the game from the intent
        int gameNo = (Integer) getIntent().getExtras().get(EXTRA_GAMENO);
        String className = (String) getIntent().getExtras().get(EXTRA_CLASSNAME);

        switch (className) {
            case LIBRARY_ACTIVITY:
                bgg = false;
                new LibraryGameDetailTask().execute(gameNo);
                break;
            case SEARCH_ACTIVITY:
                new CheckLibraryForGameTask().execute(gameNo);
                break;
            case RANDOM_ACTIVITY:
                bgg = false;
                new LibraryGameDetailTask().execute(gameNo);
                break;
            default:
                Log.d("GameDetail", "Error, class not found.");
        }
    }

    private class CheckLibraryForGameTask extends AsyncTask<Integer, Void, Integer[]> {
        @Override
        protected Integer[] doInBackground(Integer... params) {
            int gameId = params[0];
            Integer[] gameInfo = {0, gameId};
            try {
                SQLiteOpenHelper libraryDatabaseHelper =
                        new SQLiteMyLibraryDatabaseHelper(GameDetailActivity.this);
                db = libraryDatabaseHelper.getReadableDatabase();
                Integer libraryId = getLibraryIdIfExists(db, gameId);
                if (libraryId > 0) {
                    gameInfo[0] = libraryId;
                    return gameInfo;
                }
            } catch (SQLiteException e) {
                Log.d("LibraryGameDetail: ", "Caught SQLite Exception" + e.getMessage());
            }
            return gameInfo;
        }

        @Override
        protected void onPostExecute(Integer[] gameInfo) {
            if (gameInfo[0] > 0) {
                new LibraryGameDetailTask().execute(gameInfo[0]);
            } else {
                new FetchBGGTask().execute(gameInfo[1]);
            }
        }
    }

    private class FetchBGGTask extends AsyncTask<Integer, Void, FetchItem> {
        @Override
        protected FetchItem doInBackground(Integer... params) {
            int gameId = params[0];
            try {
                FetchItem fetchedItem = BGG.fetch(Arrays.asList(gameId), ThingType.BOARDGAME,
                        ThingType.BOARDGAME_EXPANSION).iterator().next();
                return fetchedItem;
            } catch (FetchException e) {
                Log.d("FetchBGGTask", "Fetch Exception: " + e.getMessage());
                return null;
            }
        }

        @Override
        protected void onPostExecute(FetchItem fetchedItem) {

            if (fetchedItem != null) {
                //Populate the game image
                ImageView photo = (ImageView) findViewById(R.id.photo);
                gameImageUrl = HTTPS + fetchedItem.getImageUrl();
                Picasso.with(GameDetailActivity.this).load(gameImageUrl).into(photo);

                //Populate the game name
                TextView name = (TextView) findViewById(R.id.game_name);
                gameName = fetchedItem.getName();
                name.setText(gameName);

                //Populate the game description
                TextView description = (TextView) findViewById(R.id.description);
                gameDescription = XmlEscape.unescapeXml(fetchedItem.getDescription());
                gameDescription = HtmlEscape.unescapeHtml(gameDescription);
                description.setText(gameDescription);

                //Populate the game theme
                TextView theme = (TextView) findViewById(R.id.theme);
                List<String> themeList = fetchedItem.getCategories();
                gameThemes = join(", ", themeList);
                theme.setText(gameThemes);

                //Populate the game min and max players
                TextView players = (TextView) findViewById(R.id.players);
                playerNum = fetchedItem.getMinPlayers().getValue() + " - " +
                        fetchedItem.getMaxPlayers().getValue() + PLAYERS;
                players.setText(playerNum);

                //Populate the game min and max time
                TextView time = (TextView) findViewById(R.id.time);
                timeNum = fetchedItem.getPlayingTime().getValue() + MINS;
                time.setText(timeNum);

                //Populate the game min age
                TextView minAge = (TextView) findViewById(R.id.ages);
                ageNum = AGES + fetchedItem.getMinAge().getValue();
                minAge.setText(ageNum);
            } else {
                Toast toast = Toast.makeText(GameDetailActivity.this,
                        ERROR_RETRIEVE_GAME, Toast.LENGTH_SHORT);
                toast.show();
            }
        }
    }


    private class LibraryGameDetailTask extends AsyncTask<Integer, Void, Game> {
        @Override
        protected Game doInBackground(Integer... params) {
            int gameId = params[0];

            try {
                SQLiteOpenHelper libraryDatabaseHelper =
                        new SQLiteMyLibraryDatabaseHelper(GameDetailActivity.this);
                db = libraryDatabaseHelper.getReadableDatabase();
                Game game = getGame(db, gameId);
                return game;
            } catch (SQLiteException e) {
                Log.d("LibraryGameDetail: ", "Caught SQLite Exception" + e.getMessage());
                return null;
            }
        }

        @Override
        protected void onPostExecute(Game game) {
            if (game != null) {
                //Populate the game image
                ImageView photo = (ImageView) findViewById(R.id.photo);
                Picasso.with(GameDetailActivity.this).load(game.getImage()).into(photo);

                //Populate the game name
                TextView name = (TextView) findViewById(R.id.game_name);
                name.setText(game.getName());

                //Populate the game description
                TextView description = (TextView) findViewById(R.id.description);
                description.setText(game.getDescription());

                //Populate the game themes
                TextView theme = (TextView) findViewById(R.id.theme);
                theme.setText(game.getTheme());

                //Populate the game min and max players
                TextView players = (TextView) findViewById(R.id.players);
                players.setText(game.getMinplayers() + " - " + game.getMaxplayers() + PLAYERS);

                //Populate the game min and max time
                TextView time = (TextView) findViewById(R.id.time);
                time.setText(game.getPlaytime() + MINS);

                //Populate the game min age
                TextView minAge = (TextView) findViewById(R.id.ages);
                minAge.setText(AGES + game.getAge());
                
                //Populate the library checkbox		
                CheckBox addToLibrary = (CheckBox) findViewById(R.id.addToLibrary);
                addToLibrary.setChecked(true);
            } else {
                Toast toast = Toast.makeText(GameDetailActivity.this, DB_NOTAVAIL, Toast.LENGTH_SHORT);
                toast.show();
            }
        }
    }

    /**
     * Adds and removes a game to the local library.
     *
     * @param view the view to add to a library
     */
    public void onAddToLibraryClicked(View view) {
        int gameNo = (Integer) getIntent().getExtras().get(EXTRA_GAMENO);

        CheckBox addToLibrary = (CheckBox) findViewById(R.id.addToLibrary);

        if (addToLibrary.isChecked()) {
            String[] playersMinMax = playerNum.split(" - ");

            int minPlayers = Integer.valueOf(playersMinMax[0]);

            String maxplayers = playersMinMax[1];
            int maxPlayers = Integer.valueOf(maxplayers.substring(0, maxplayers.length() - 8));
            int time = Integer.valueOf(timeNum.substring(0, timeNum.length() - 5));

            ageNum = ageNum.substring(6, ageNum.length());

            Game game = new Game(gameImageUrl, gameName, gameDescription, gameThemes, gameNo,
                    minPlayers, maxPlayers, time, ageNum);
            new AddGameToLibraryTask().execute(game);
        } else {
            ContentValues gameNum = new ContentValues();
            gameNum.put(LIBRARYID, gameNo);
            new RemoveGameFromLibraryTask().execute(gameNum);
        }
    }

    //Inner class to add the game to MyLibrary
    private class AddGameToLibraryTask extends AsyncTask<Game, Void, Boolean> {

        @Override
        protected Boolean doInBackground(Game... game) {
            Game newGame = game[0];
            try {
                SQLiteOpenHelper libraryDatabaseHelper =
                        new SQLiteMyLibraryDatabaseHelper(GameDetailActivity.this);
                db = libraryDatabaseHelper.getWritableDatabase();
                insertGame(db, newGame);
                db.close();
                return true;
            } catch (SQLiteException e) {
                Log.d("GameDetailActivity", "SQLite Exception caught");
                return false;
            }
        }

        @Override
        protected void onPostExecute(Boolean success) {
            if (!success) {
                Toast.makeText(GameDetailActivity.this, ERROR_SAVING_GAME, Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(GameDetailActivity.this, GAME_SAVED, Toast.LENGTH_SHORT).show();
            }
        }
    }

    //Inner class to remove the game from the MyLibrary
    private class RemoveGameFromLibraryTask extends AsyncTask<ContentValues, Void, Integer> {

        @Override
        protected Integer doInBackground(ContentValues... games) {
            Integer gameId = games[0].getAsInteger(LIBRARYID);
            SQLiteOpenHelper myLibraryDatabaseHelper = new SQLiteMyLibraryDatabaseHelper(GameDetailActivity.this);
            try {
                db = myLibraryDatabaseHelper.getWritableDatabase();
                if (bgg) {
                    removeGameByBGGId(db, gameId);
                } else {
                    removeGameByLibraryId(db, gameId);
                }
                db.close();
                return gameId;
            } catch (SQLiteException e) {
                Log.d("GameDetailActivity", "SQLite Exception caught while removing game from db");
                return null;
            }
        }

        @Override
        protected void onPostExecute(Integer libraryId) {
            if (libraryId == null) {
                Toast toast = Toast.makeText(GameDetailActivity.this,
                        DB_NOTAVAIL, Toast.LENGTH_SHORT);
                toast.show();
            } else {
                Toast toast = Toast.makeText(GameDetailActivity.this,
                        GAME_REMOVED, Toast.LENGTH_SHORT);
                toast.show();
                Intent intent = new Intent(GameDetailActivity.this, MyLibraryActivity.class);
                intent.putExtra(GameDetailActivity.EXTRA_GAMENO, libraryId);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (db != null) {
            db.close();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
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
