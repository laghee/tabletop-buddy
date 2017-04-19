package edu.mills.tabletopbuddy;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SQLiteMyLibraryDatabaseHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "mylibrarydatabase"; // the name of our database
    private static final int DB_VERSION = 1; // the version of the database
    private static final Integer FAVE_INIT = 0; // set favorite to 0 on initial add to database

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
                    + "IMAGE TEXT NOT NULL, "
                    + "NAME TEXT NOT NULL, "
                    + "DESCRIPTION TEXT NOT NULL,"
                    + "THEME TEXT NOT NULL, "
                    + "YEAR_PUBLISHED INTEGER NOT NULL, "
                    + "MY_RATING DOUBLE NOT NULL, "
                    + "BGG_RATING DOUBLE NOT NULL, "
                    + "MIN_PLAYERS INTEGER NOT NULL, "
                    + "MAX_PLAYERS INTEGER NOT NULL, "
                    + "MIN_TIME INTEGER NOT NULL, "
                    + "MAX_TIME INTEGER NOT NULL, "
                    + "MIN_AGE INTEGER NOT NULL, "
                    + "FAVORITE INTEGER NOT NULL);");
        }

    }

    private static void insertGame(SQLiteDatabase db, String image, String name, String description, String theme, Integer pubdate,
                                   Double myrating, Double bggrating, Integer minplayers, Integer maxplayers,
                                   Integer mintime, Integer maxtime, Integer age, FAVE_INIT) {
        ContentValues gameValues = new ContentValues();
        gameValues.put("IMAGE", image);
        gameValues.put("NAME", name);
        gameValues.put("DESCRIPTION", description);
        gameValues.put("THEME", theme);
        gameValues.put("YEAR_PUBLISHED", pubdate);
        gameValues.put("MY_RATING", myrating);
        gameValues.put("BGG_RATING", bggrating);
        gameValues.put("MIN_PLAYERS", minplayers);
        gameValues.put("MAX_PLAYERS", maxplayers);
        gameValues.put("MIN_TIME", mintime);
        gameValues.put("MAX_PLAYERS", maxtime);
        gameValues.put("MIN_AGE", age);
        gameValues.put("FAVORITE", FAVE_INIT);
        db.insert("LIBRARY", null, gameValues);
    }

}
