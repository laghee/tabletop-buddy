package edu.mills.tabletopbuddy;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SQLiteMyLibraryDatabaseHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "mylibrarydatabase"; // the name of our database
    private static final int DB_VERSION = 1; // the version of the database

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
                    + "NAME TEXT NOT NULL, "
                    + "THEME TEXT NOT NULL, "
                    + "YEAR_PUBLISHED INTEGER NOT NULL, "
                    + "RATING DOUBLE NOT NULL, "
                    + "MIN_PLAYERS INTEGER NOT NULL, "
                    + "MAX_PLAYERS INTEGER NOT NULL, "
                    + "MIN_TIME INTEGER NOT NULL, "
                    + "MAX_TIME INTEGER NOT NULL, "
                    + "MIN_AGE INTEGER NOT NULL);");
        }

    }

    private static void insertGame(SQLiteDatabase db, String name, String theme, Integer pubdate,
                                   Double rating, Integer minplayers, Integer maxplayers,
                                   Integer mintime, Integer maxtime, Integer age) {
        ContentValues gameValues = new ContentValues();
        gameValues.put("NAME", name);
//        drinkValues.put("DESCRIPTION", description);
        gameValues.put("THEME", theme);
        gameValues.put("YEAR_PUBLISHED", pubdate);
        gameValues.put("RATING", rating);
        gameValues.put("MIN_PLAYERS", minplayers);
        gameValues.put("MAX_PLAYERS", maxplayers);
        gameValues.put("MIN_TIME", mintime);
        gameValues.put("MAX_PLAYERS", maxtime);
        gameValues.put("MIN_AGE", age);
        db.insert("LIBRARY", null, gameValues);
    }

}
