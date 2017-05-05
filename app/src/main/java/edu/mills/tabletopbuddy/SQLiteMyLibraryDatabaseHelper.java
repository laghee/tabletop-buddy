/**
 * Implementation of the Tabletop Buddy application. Created for Mills
 * CS 115: Mobile Application Development, Spring 2017.
 *
 * @author Kristen Cutler, Jennifer Diaz, Arianne Agogino Gieringer,
 * Kate Manning, Erin Walter
 */
package edu.mills.tabletopbuddy;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * A local SQLite database for inserting into and deleting games from a user's local library.
 */
public class SQLiteMyLibraryDatabaseHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "mylibrarydatabase"; // the name of our database
    private static final int DB_VERSION = 1; // the version of the database

    /**
     * Name of database table keeping track of all saved games in personal library.
     */
    static final String LIBRARY_TABLE = "LIBRARY";

    /**
     * Name of column in {@link #LIBRARY_TABLE} with image URL.
     */
    static final String IMAGE_COL = "IMAGE";

    /**
     * Name of column in {@link #LIBRARY_TABLE} with title of game.
     */
    static final String NAME_COL = "NAME";

    /**
     * Name of column in {@link #LIBRARY_TABLE} with game description.
     */
    static final String DESC_COL = "DESCRIPTION";

    /**
     * Name of column in {@link #LIBRARY_TABLE} with game themes/categories.
     */
    static final String THEME_COL = "THEME";

    /**
     * Name of column in {@link #LIBRARY_TABLE} with unique id from BGG API.
     */
    static final String BGGID_COL = "BGG_ID";

    /**
     * Name of column in {@link #LIBRARY_TABLE} with game's suggested minimum players.
     */
    static final String MINPLAYERS_COL = "MIN_PLAYERS";

    /**
     * Name of column in {@link #LIBRARY_TABLE} with game's suggested maximum players.
     */
    static final String MAXPLAYERS_COL = "MAX_PLAYERS";

    /**
     * Name of column in {@link #LIBRARY_TABLE} with game's suggested play time.
     */
    static final String PLAYTIME_COL = "PLAY_TIME";

    /**
     * Name of column in {@link #LIBRARY_TABLE} with game's suggested minimum age of players.
     */
    static final String MINAGE_COL = "MIN_AGE";

    SQLiteMyLibraryDatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        updateMyDatabase(db, 0, DB_VERSION);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        updateMyDatabase(db, oldVersion, newVersion);
    }

    private void updateMyDatabase(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion < 1) {
            db.execSQL("CREATE TABLE LIBRARY (_id INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + IMAGE_COL + " TEXT, "
                    + NAME_COL + " TEXT NOT NULL, "
                    + DESC_COL + " TEXT,"
                    + THEME_COL + " TEXT, "
                    + BGGID_COL + " INTEGER NOT NULL, "
                    + MINPLAYERS_COL + " INTEGER, "
                    + MAXPLAYERS_COL + " INTEGER, "
                    + PLAYTIME_COL + "  INTEGER, "
                    + MINAGE_COL + " TEXT);");
        }
    }
}
