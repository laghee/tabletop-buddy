package edu.mills.tabletopbuddy;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

public class GameDetailActivity extends Activity {
    public static final String EXTRA_GAMENO = "gameNo";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_detail);

        //Get the drink from the intent
        int gameNo = (Integer)getIntent().getExtras().get(EXTRA_GAMENO);

        //Create a cursor
        try {
            SQLiteOpenHelper libraryDatabaseHelper = new SQLiteMyLibraryDatabaseHelper(this);
            SQLiteDatabase db = libraryDatabaseHelper.getReadableDatabase();
            Cursor cursor = db.query ("LIBRARY",
                    new String[] {"NAME", "MIN_PLAYERS", "MAX_PLAYERS", "MIN_TIME",
                            "MAX_TIME", "MIN_AGE"},
                    "_id = ?",
                    new String[] {Integer.toString(gameNo)},
                    null, null,null);

            //Move to the first record in the Cursor
            if (cursor.moveToFirst()) {

                //Get the game details from the cursor
                String nameText = cursor.getString(0);
//                String themeText = cursor.getString(1);
//                double bggRatingNo = cursor.getDouble(1);
                int minPlayerNo = cursor.getInt(1);
                int maxPlayerNo = cursor.getInt(2);
                int minTimeNo = cursor.getInt(3);
                int maxTimeNo = cursor.getInt(4);
                int minAgeNo = cursor.getInt(5);

//                int photoId = cursor.getInt(8);
//                boolean isFavorite = (cursor.getInt(9) == 1);

                //Populate the game name
                TextView name = (TextView)findViewById(R.id.game_name);
                name.setText(nameText);

                //Populate the game name
//                TextView theme = (TextView)findViewById(R.id.theme);
//                theme.setText(themeText);

                //Populate the game rating
//                TextView rating = (TextView)findViewById(R.id.rating);
//                rating.setText((int) bggRatingNo);

                //Populate the game min and max players
                TextView players = (TextView)findViewById(R.id.players);
                String playerNum = minPlayerNo + " - " + maxPlayerNo + " players";
                players.setText(playerNum);

                //Populate the game min and max time
                TextView time = (TextView)findViewById(R.id.time);
                String timeNum = minTimeNo + " - " + maxTimeNo + " mins";
                time.setText(timeNum);

                //Populate the game min age
                TextView minAge = (TextView)findViewById(R.id.ages);
                String ageNum =  "ages: " + minAgeNo + "+";
                minAge.setText(ageNum);

//                //Populate the drink image
//                ImageView photo = (ImageView)findViewById(R.id.photo);
//                photo.setImageResource(photoId);
//                photo.setContentDescription(nameText);
//
//                //Populate the favorite checkbox
//                CheckBox favorite = (CheckBox)findViewById(R.id.favorite);
//                favorite.setChecked(isFavorite);
            }
            cursor.close();
            db.close();
        } catch(SQLiteException e) {
            Toast toast = Toast.makeText(this, "Database unavailable", Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    //Update the database when the checkbox is clicked
    public void onFavoriteClicked(View view){
        int gameNo = (Integer)getIntent().getExtras().get("gameNo");
        CheckBox favorite = (CheckBox)findViewById(R.id.favorite);
        ContentValues gameValues = new ContentValues();
        gameValues.put("FAVORITE", favorite.isChecked());

        Object[] taskArray = new Object[2];
        taskArray[0] = gameNo;
        taskArray[1] = gameValues;

        new UpdateLibraryTask().execute(taskArray);
    }

    //Inner class to update the drink.
    private class UpdateLibraryTask extends AsyncTask<Object, Void, Boolean> {

        @Override
        protected Boolean doInBackground(Object... games) {
            int gameNo = (int) games[0];
            ContentValues gameValues = (ContentValues) games[1];

            SQLiteOpenHelper starbuzzDatabaseHelper = new SQLiteMyLibraryDatabaseHelper(GameDetailActivity.this);
            try {
                SQLiteDatabase db = starbuzzDatabaseHelper.getWritableDatabase();
                db.update("LIBRARY", gameValues,
                        "_id = ?", new String[] {Integer.toString(gameNo)});
                db.close();
                return true;
            } catch(SQLiteException e) {
                return false;
            }
        }

        @Override
        protected void onPostExecute(Boolean success) {
            if (!success) {
                Toast toast = Toast.makeText(GameDetailActivity.this,
                        "Database unavailable", Toast.LENGTH_SHORT);
                toast.show();
            }
        }
    }
}
