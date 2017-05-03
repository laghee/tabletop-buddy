package edu.mills.tabletopbuddy;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class LibraryDBUtilities {

    private LibraryDBUtilities() {}


    static Game getGame(SQLiteDatabase db, int gameId) {
        Cursor cursor = db.query(SQLiteMyLibraryDatabaseHelper.LIBRARY_TABLE,
                new String[]{"IMAGE", "NAME", "DESCRIPTION", "THEME", "MIN_AGE", "MIN_PLAYERS",
                        "MAX_PLAYERS", "BGG_ID", "PLAY_TIME", }, "_id = ?",
                new String[]{Integer.toString(gameId)},
                null, null, null);

        Game game = null;
        if (cursor.moveToFirst()) {
            game = new Game(cursor.getString(0), cursor.getString(1), cursor.getString(2),
                    cursor.getString(3), cursor.getString(4), cursor.getInt(5),
                    cursor.getInt(6), cursor.getInt(7), cursor.getInt(8));
        }
        return game;
    }
    public static boolean insertGame(SQLiteDatabase db, Game game) {
        ContentValues gameValues = new ContentValues();
        gameValues.put("IMAGE", game.getImage());
        gameValues.put("NAME", game.getName());
        gameValues.put("DESCRIPTION", game.getDescription());
        gameValues.put("THEME", game.getTheme());
        gameValues.put("BGG_ID", game.getBggId());
        gameValues.put("MIN_PLAYERS", game.getMinplayers());
        gameValues.put("MAX_PLAYERS", game.getMaxplayers());
        gameValues.put("PLAY_TIME", game.getPlaytime());
        gameValues.put("MIN_AGE", game.getAge());
//        gameValues.put("YEAR_PUBLISHED", pubdate);

        db.insert("LIBRARY", null, gameValues);
        Log.d("DatabaseUtils", "Successfully wrote" + game.getName() + "to db");
        return true;
    }

    public static void removeGame(SQLiteDatabase db, Integer libraryId) {
        db.delete("LIBRARY", "_id = ?", new String[] {libraryId.toString()});
    }

//    public static boolean gameInLibrary(Integer bggid) {
//        return gamesLibrary.contains(bggid);
//    }



}
