package edu.mills.tabletopbuddy;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.content.res.AssetManager;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class MainActivity extends Activity {
    private static final String searchToast = "This will link to the Search Activity :)";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
// Kate
//      setContentView(R.layout.activity_main);
        makeDummyDatabase();
        setContentView(R.layout.activity_main);

    }

    public void onSearch(View view){
        Intent searchIntent = new Intent(this, SearchResultsActivity.class);
        startActivity(searchIntent);

    }
    public void onViewLibrary(View view){
        Intent intent = new Intent(this, MyLibraryActivity.class);
        startActivity(intent);
    }

    public void onRandomGenerator(View v){
        Intent randintent = new Intent(this, RandomGameActivity.class);
        startActivity(randintent);
    }

    private void makeDummyDatabase() {
        String mCSVfile = "mylibrary_collection_slim.csv";

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
        int count = 0;
        try {
            while ((line = buffer.readLine()) != null) {
                String[] columns = line.split(",");
                if (columns.length != 10) {
                    Log.d("CSVParser", "Skipping Bad CSV Row");
                    continue;
                }
                count++;
                ContentValues gameValues = new ContentValues();
//                gameValues.put("IMAGE", columns[0].trim());
                gameValues.put("NAME", columns[0].trim());
//                gameValues.put("DESCRIPTION", columns[2].trim());
//                gameValues.put("THEME", columns[3].trim());
                gameValues.put("YEAR_PUBLISHED", columns[1].trim());
                gameValues.put("MY_RATING", columns[2].trim());
                gameValues.put("BGG_RATING", columns[3].trim());
                gameValues.put("BGG_ID", columns[4].trim());
                gameValues.put("MIN_PLAYERS", columns[5].trim());
                gameValues.put("MAX_PLAYERS", columns[6].trim());
                gameValues.put("MIN_TIME", columns[7].trim());
                gameValues.put("MAX_TIME", columns[8].trim());
                gameValues.put("MIN_AGE", columns[9].trim());
//                gameValues.put("FAVORITE", columns[10].trim());
                db.insert("LIBRARY", null, gameValues);
                Log.d("DummyDBpop", "Row populated: " + gameValues);
                Log.d("DummyDBpop", "count: " + count);
            }
        } catch (IOException e) {
            Log.d("DummyDBpop", "Couldn't populate db: " + e);
        }
        db.setTransactionSuccessful();
        db.endTransaction();
        Log.d("DummyDBpop", "dummyDB populated");
    }

}
