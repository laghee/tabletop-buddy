package edu.mills.tabletopbuddy;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SQLiteMyLibraryDatabaseHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "mylibrarydatabase"; // the name of our database
    private static final int DB_VERSION = 2; // the version of the database
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
                    + "DESCRIPTION TEXT,"
//                    + "THEME TEXT NOT NULL, "
//                    + "YEAR_PUBLISHED INTEGER, "
//                    + "MY_RATING DOUBLE NOT NULL, "
//                    + "BGG_RATING DOUBLE NOT NULL, "
                    + "BGG_ID INTEGER NOT NULL, "
                    + "MIN_PLAYERS INTEGER NOT NULL, "
                    + "MAX_PLAYERS INTEGER NOT NULL, "
                    + "PLAY_TIME INTEGER NOT NULL, "
                    + "MIN_AGE INTEGER NOT NULL);");
        }
//        if (oldVersion < 2) {
//            db.execSQL("ALTER TABLE LIBRARY ADD COLUMN IMAGE TEXT;");
////            db.execSQL("ALTER TABLE LIBRARY ADD COLUMN DESCRIPTION TEXT;");
////            db.execSQL("ALTER TABLE LIBRARY ADD COLUMN THEME TEXT;");
////            insertGame(db, "Tequila Sunrise", "Premium tequila with grenadine and OJ", R.drawable.sunrise, 200 );
//            db.execSQL("UPDATE LIBRARY SET IMAGE= 'http://cf.geekdo-images.com/images/pic860217.jpg' WHERE NAME='7 Wonders';");
//            db.execSQL("UPDATE LIBRARY SET IMAGE= 'http://cf.geekdo-images.com/images/pic259085_t.jpg' WHERE NAME='Agricola';");
//            db.execSQL("UPDATE LIBRARY SET IMAGE= 'http://cf.geekdo-images.com/images/pic1324609.jpg' WHERE NAME='Android: Netrunner';");
////            db.execSQL("UPDATE DRINK SET CALORIES= 120 WHERE NAME='Rossini';");
////            db.execSQL("UPDATE DRINK SET CALORIES= 90 WHERE NAME='Shakerato';");
//        }

    }



    private static void insertGame(SQLiteDatabase db, String image, String name, String description, String theme, Integer pubdate,
                                   Double myrating, Double bggrating, Integer bggid, Integer minplayers, Integer maxplayers,
                                   Integer playtime, Integer age) {
        ContentValues gameValues = new ContentValues();
        gameValues.put("IMAGE", image);
        gameValues.put("NAME", name);
        gameValues.put("DESCRIPTION", description);
        gameValues.put("THEME", theme);
        gameValues.put("YEAR_PUBLISHED", pubdate);
        gameValues.put("MY_RATING", myrating);
        gameValues.put("BGG_RATING", bggrating);
        gameValues.put("BGG_ID", bggid);
        gameValues.put("MIN_PLAYERS", minplayers);
        gameValues.put("MAX_PLAYERS", maxplayers);
        gameValues.put("PLAY_TIME", playtime);
        gameValues.put("MIN_AGE", age);
        db.insert("LIBRARY", null, gameValues);
    }

}
