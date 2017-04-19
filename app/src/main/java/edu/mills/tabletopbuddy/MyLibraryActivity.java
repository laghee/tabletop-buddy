package edu.mills.tabletopbuddy;

import android.app.Activity;

// Correctly queries games added to MyGameDatabase and populates view
// of personal library.
// Users are able to add and delete games from their local library.
public class MyLibraryActivity extends Activity {

//    //view saved games
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_my_library);
//
//        //get instance of local database
//        SQLiteOpenHelper gameHelper = SQLiteMyLibraryDatabaseHelper.getInstance(this);
//
//        //Add game
//        gameHelper.addGame(sampleGame);
//
//    }
//
//
//    //Return games in local database
//    public getAllGames() {
//
//        String GAMES_QUERY =
//                String.format("SELECT * FROM %s LEFT OUTER JOIN %s ON %s.%s = %s.%s",
//                        TABLE_VALUE,
//                        TABLE_VALUE,
//                        TABLE_VALUE, ID,
//                        TABLE_VALUE, ID);
//
//        SQLiteDatabase db = getReadableDatabase();
//        Cursor cursor = db.query(null, null, null);
//        try {
//            if (cursor.moveToFirst()) {
//                do {
//                    Game newGame = new Game(GameDetailActivity);
//                    newGame.gameTitle = cursor.getString(cursor.getColumnIndex(KEY_GAME_NAME));
//                    newGame.gamePicture = cursor.getString(cursor.getColumnIndex(KEY_GAME_PICTURE));
//
//                    Game newGame = new Game();
//                    newPost.text = cursor.getString(cursor.getColumnIndex(KEY_POST_TEXT));
//
//                    games.add(newGame);
//                } while (cursor.moveToNext());
//            }
//        } catch (Exception e) {
//            Log.d(TAG, "Error while deleting game from database");
//        } finally {
//            if (cursor != null && !cursor.isClosed()) {
//                cursor.close();
//            }
//        }
//        return games;
//    }
//
//    //delete a game from your library
//    public void deleteGame(Game game) {
//
//        SQLiteDatabase db = getWriteableDatabase();
//
//        db.beginTransaction();
//        try {
//
//            ContentValues values = new ContentValues();
//            values.put(INSERT_VARIABLE_HERE, gameId);
//            values.put(INSERT_VARIABLE_HERE, postID);
//
//            db.update();
//            db.setTransactionSuccessful();
//        } catch (Exception e) {
//            Log.d(TAG, "Error while adding game to database");
//        } finally {
//            db.endTransaction();
//        }
//
//    }

}