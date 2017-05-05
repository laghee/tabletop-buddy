/**
 * Implementation of the Tabletop Buddy application. Created for Mills
 * CS 115: Mobile Application Development, Spring 2017.
 *
 * @author Kristen Cutler, Jennifer Diaz, Arianne Agogino Gieringer,
 * Kate Manning, Erin Walter
 */
package edu.mills.tabletopbuddy;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * A local SQLite database for inserting into and deleting games from a user's local library.
 */
public class SQLiteMyLibraryDatabaseHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "mylibrarydatabase"; // the name of our database
    private static final int DB_VERSION = 1; // the version of the database

    // package-private table and column names
    static final String LIBRARY_TABLE = "LIBRARY";
    static final String IMAGE_COL = "IMAGE";
    static final String NAME_COL = "NAME";
    static final String DESC_COL = "DESCRIPTION";
    static final String THEME_COL = "THEME";
    static final String BGGID_COL = "BGG_ID";
    static final String MINPLAYERS_COL = "MIN_PLAYERS";
    static final String MAXPLAYERS_COL = "MAX_PLAYERS";
    static final String PLAYTIME_COL = "PLAY_TIME";
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
//                    + "YEAR_PUBLISHED TEXT, "
//                    + "MY_RATING DOUBLE NOT NULL, "
//                    + "BGG_RATING DOUBLE NOT NULL, "
                    + BGGID_COL + " INTEGER NOT NULL, "
                    + MINPLAYERS_COL + " INTEGER, "
                    + MAXPLAYERS_COL + " INTEGER, "
                    + PLAYTIME_COL + "  INTEGER, "
                    + MINAGE_COL + " TEXT);");
        }
    }

    private static void insertGame(SQLiteDatabase db, String image, String name, String description,
                                   String theme, Integer bggid, String minplayers,
                                   String maxplayers, String playtime, String age) {
        ContentValues gameValues = new ContentValues();
        gameValues.put(IMAGE_COL, image);
        gameValues.put(NAME_COL, name);
        gameValues.put(DESC_COL, description);
        gameValues.put(THEME_COL, theme);
//         gameValues.put("YEAR_PUBLISHED", pubdate);
        gameValues.put(BGGID_COL, bggid);
        gameValues.put(MINPLAYERS_COL, minplayers);
        gameValues.put(MAXPLAYERS_COL, maxplayers);
        gameValues.put(PLAYTIME_COL, playtime);
        gameValues.put(MINAGE_COL, age);
        db.insert(LIBRARY_TABLE, null, gameValues);
    }

    private static void deleteGame(SQLiteDatabase db, String bggid) {
        db.delete(LIBRARY_TABLE, "ID = ?", new String[] {bggid.toString()});
    }

}
