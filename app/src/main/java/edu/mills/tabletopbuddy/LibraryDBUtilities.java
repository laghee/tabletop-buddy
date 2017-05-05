package edu.mills.tabletopbuddy;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import static edu.mills.tabletopbuddy.SQLiteMyLibraryDatabaseHelper.BGGID_COL;
import static edu.mills.tabletopbuddy.SQLiteMyLibraryDatabaseHelper.DESC_COL;
import static edu.mills.tabletopbuddy.SQLiteMyLibraryDatabaseHelper.IMAGE_COL;
import static edu.mills.tabletopbuddy.SQLiteMyLibraryDatabaseHelper.LIBRARY_TABLE;
import static edu.mills.tabletopbuddy.SQLiteMyLibraryDatabaseHelper.MAXPLAYERS_COL;
import static edu.mills.tabletopbuddy.SQLiteMyLibraryDatabaseHelper.MINAGE_COL;
import static edu.mills.tabletopbuddy.SQLiteMyLibraryDatabaseHelper.MINPLAYERS_COL;
import static edu.mills.tabletopbuddy.SQLiteMyLibraryDatabaseHelper.NAME_COL;
import static edu.mills.tabletopbuddy.SQLiteMyLibraryDatabaseHelper.PLAYTIME_COL;
import static edu.mills.tabletopbuddy.SQLiteMyLibraryDatabaseHelper.THEME_COL;

public class LibraryDBUtilities {

    private LibraryDBUtilities() {}

    static Game getGame(SQLiteDatabase db, int gameId) {
        Cursor cursor = db.query(LIBRARY_TABLE,
                new String[]{IMAGE_COL, NAME_COL, DESC_COL, THEME_COL, BGGID_COL, MINPLAYERS_COL,
                        MAXPLAYERS_COL, PLAYTIME_COL, MINPLAYERS_COL}, "_id = ?",
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

    public static void removeGameByLibraryId(SQLiteDatabase db, Integer libraryId) {
        db.delete(LIBRARY_TABLE, "_id = ?", new String[] {libraryId.toString()});
    }

    public static void removeGameByBGGId(SQLiteDatabase db, Integer bggId) {
        db.delete(LIBRARY_TABLE, BGGID_COL + " = ?", new String[] {bggId.toString()});
    }

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
