package edu.mills.tabletopbuddy;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class MainActivity extends Activity {
    private static final String searchToast = "This will link to the Search Activity :)";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        makeDummyDatabase();
    }

    public void onSearch(View view){
        Toast toast = Toast.makeText(this, searchToast, Toast.LENGTH_LONG);
        toast.setGravity(Gravity.TOP| Gravity.START, 0, 500);
        toast.show();
    }
//    public void onViewLibrary(View view){
//        Intent intent = new Intent(this, MyLibraryActivity.class);
//        startActivity(intent);
//    }

    public void onRandomGenerator(View view){
        Intent intent = new Intent(this, RandomGameActivity.class);
        startActivity(intent);
    }

    private void makeDummyDatabase() {

        String mCSVfile = "mylibrary_collectionRRRG.csv";
        AssetManager manager = this.getApplicationContext().getAssets();
        SQLiteOpenHelper myLibraryDatabaseHelper = new SQLiteMyLibraryDatabaseHelper(this);
        SQLiteDatabase db = myLibraryDatabaseHelper.getWritableDatabase();
        InputStream inStream = null;
        try {
            inStream = manager.open(mCSVfile);
        } catch (IOException e) {
            Log.d("DummyDBpop", "Couldn't open csv file: " + e);
        }

        BufferedReader buffer = new BufferedReader(new InputStreamReader(inStream));

        String line = "";
        db.beginTransaction();
        try {
            while ((line = buffer.readLine()) != null) {
                String[] colums = line.split(",");
                if (colums.length != 13) {
                    Log.d("CSVParser", "Skipping Bad CSV Row");
                    continue;
                }
                ContentValues gameValues = new ContentValues();
                gameValues.put("IMAGE", colums[0].trim());
                gameValues.put("NAME", colums[1].trim());
                gameValues.put("DESCRIPTION", colums[2].trim());
                gameValues.put("THEME", colums[3].trim());
                gameValues.put("YEAR_PUBLISHED", colums[4].trim());
                gameValues.put("MY_RATING", colums[5].trim());
                gameValues.put("BGG_RATING", colums[6].trim());
                gameValues.put("BGG_ID", colums[7].trim());
                gameValues.put("MIN_PLAYERS", colums[8].trim());
                gameValues.put("MAX_PLAYERS", colums[9].trim());
                gameValues.put("MIN_TIME", colums[10].trim());
                gameValues.put("MAX_TIME", colums[11].trim());
                gameValues.put("MIN_AGE", colums[12].trim());
                gameValues.put("FAVORITE", 0);
                db.insert("LIBRARY", null, gameValues);
                Log.d("DummyDBpop", "Row populated: " + gameValues);
            }
        } catch (IOException e) {
            Log.d("DummyDBpop", "Couldn't populate db: " + e);
        }
        db.setTransactionSuccessful();
        db.endTransaction();
        Log.d("DummyDBpop", "dummyDB populated");
    }

}
