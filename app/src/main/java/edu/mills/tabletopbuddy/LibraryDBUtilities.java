/**
 * Implementation of the Tabletop Buddy application. Created for Mills
 * CS 115: Mobile Application Development, Spring 2017.
 *
 * @author Kristen Cutler, Jennifer Diaz, Arianne Agogino Gieringer,
 * Kate Manning, Erin Walter
 */
package edu.mills.tabletopbuddy;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import static edu.mills.tabletopbuddy.SQLiteMyLibraryDatabaseHelper.*;

/**
 * Allows for methods to query the database for a game, and add or remove a game
 * without directly calling the database.
 */
public class LibraryDBUtilities {

    private LibraryDBUtilities() {}

    static Game getGame(SQLiteDatabase db, int gameId) {
        Cursor cursor = db.query(LIBRARY_TABLE,
                new String[]{IMAGE_COL, NAME_COL, DESC_COL, THEME_COL, BGGID_COL, MINPLAYERS_COL,
                        MAXPLAYERS_COL, PLAYTIME_COL, MINAGE_COL}, "_id = ?",
                new String[]{Integer.toString(gameId)},
                null, null, null);

        Game game = null;
        if (cursor.moveToFirst()) {
            game = new Game(cursor.getString(0), cursor.getString(1), cursor.getString(2),
                    cursor.getString(3), cursor.getInt(4), cursor.getInt(5),
                    cursor.getInt(6), cursor.getInt(7), cursor.getString(8));
        }
        return game;
    }

    /**
     * Checks whether the game is inserted into the database.
     *
     * @param db the SQLite database
     * @param game the game
     * @return true when the game is successfully inserted into the
     * local database.
     */
    public static boolean insertGame(SQLiteDatabase db, Game game) {
        ContentValues gameValues = new ContentValues();
        gameValues.put(IMAGE_COL, game.getImage());
        gameValues.put(NAME_COL, game.getName());
        gameValues.put(DESC_COL, game.getDescription());
        gameValues.put(THEME_COL, game.getTheme());
        gameValues.put(BGGID_COL, game.getBggId());
        gameValues.put(MINPLAYERS_COL, game.getMinplayers());
        gameValues.put(MAXPLAYERS_COL, game.getMaxplayers());
        gameValues.put(PLAYTIME_COL, game.getPlaytime());
        gameValues.put(MINAGE_COL, game.getAge());
//        gameValues.put("YEAR_PUBLISHED", pubdate);

        db.insert(LIBRARY_TABLE, null, gameValues);
        return true;
    }
    /**
     * Removes a game from the local database by the id of the row.
     *
     * @param db the local database
     * @param libraryId the id in the library
     */
    public static void removeGameByLibraryId(SQLiteDatabase db, Integer libraryId) {
        db.delete(LIBRARY_TABLE, "_id = ?", new String[]{libraryId.toString()});
    }
    
    /**
     * Removes a game from the local database.
     *
     * @param db the local database
     * @param bggId the id from BGG
     */

    public static void removeGameByBGGId(SQLiteDatabase db, Integer bggId) {
        db.delete(LIBRARY_TABLE, BGGID_COL + " = ?", new String[] {bggId.toString()});
    }

     /**
     * Checks for a game in the local database and, if it exists, retrieves its local id number.
     *
     * @param db the local database
     * @param bggId the id from BGG
     * @return libraryId the library id
     */
    public static int getLibraryIdIfExists(SQLiteDatabase db, int bggId) {

        int libraryId = -1;
        Cursor cursor = db.rawQuery("SELECT _id FROM " + LIBRARY_TABLE + " WHERE " + BGGID_COL +
                " = " + bggId + " LIMIT 1", null);
        if (cursor.moveToFirst()) {
            libraryId = cursor.getInt(0);
        }
        return libraryId;
    }

}
