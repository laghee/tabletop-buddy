/**
 * Implementation of the Tabletop Buddy application. Created for Mills
 * CS 115: Mobile Application Development, Spring 2017.
 *
 * @author Kristen Cutler, Jennifer Diaz, Arianne Agogino Gieringer,
 * Kate Manning, Erin Walter
 */
package edu.mills.tabletopbuddy;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

/**
 * The top-level activity for Tabletop Buddy. The accompanying view enables users to
 * launch {@link SearchResultsActivity}, {@link MyLibraryActivity}, and {@link RandomGameActivity}.
 */
public class MainActivity extends Activity {
    private static final String searchToast = "This will link to the Search Activity :)";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
// Kate
//        makeDummyDatabase();
        setContentView(R.layout.activity_main);

    }

    /**
     * Enables launch of {@link SearchResultsActivity}.
     *
     * @param view the search view
     */
    public void onSearch(View view){
        Intent searchIntent = new Intent(this, SearchResultsActivity.class);
        startActivity(searchIntent);

    }

    /**
     * Enables launch of {@link MyLibraryActivity}.
     *
     * @param view my library view
     */
    public void onViewLibrary(View view){
        Intent intent = new Intent(this, MyLibraryActivity.class);
        startActivity(intent);
    }

    /**
     * Enables launch of {@link RandomGameActivity}.
     *
     * @param v the view of random game generator
     */
    public void onRandomGenerator(View v){
        Intent randintent = new Intent(this, RandomGameActivity.class);
        startActivity(randintent);
    }

//    private void makeDummyDatabase() {
//        String mCSVfile = "mylibrary_collection_slim.csv";
//
//        AssetManager manager = this.getApplicationContext().getAssets();
//        SQLiteOpenHelper myLibraryDatabaseHelper = new SQLiteMyLibraryDatabaseHelper(this);
//        SQLiteDatabase db = myLibraryDatabaseHelper.getWritableDatabase();
//        InputStream inStream = null;
//        try {
//            inStream = manager.open(mCSVfile);
//        } catch (IOException e) {
//            Log.d("DummyDBpop", "Couldn't open csv file: " + e);
//        }
//
//        BufferedReader buffer = new BufferedReader(new InputStreamReader(inStream));
//
//        String line = "";
//        db.beginTransaction();
//        int count = 0;
//        try {
//            while ((line = buffer.readLine()) != null) {
//                String[] columns = line.split(",");
//                if (columns.length != 10) {
//                    Log.d("CSVParser", "Skipping Bad CSV Row");
//                    continue;
//                }
//                count++;
//                ContentValues gameValues = new ContentValues();
////                gameValues.put("IMAGE", columns[0].trim());
//                gameValues.put("NAME", columns[0].trim());
////                gameValues.put("DESCRIPTION", columns[2].trim());
////                gameValues.put("THEME", columns[3].trim());
//                gameValues.put("YEAR_PUBLISHED", columns[1].trim());
//                gameValues.put("MY_RATING", columns[2].trim());
//                gameValues.put("BGG_RATING", columns[3].trim());
//                gameValues.put("BGG_ID", columns[4].trim());
//                gameValues.put("MIN_PLAYERS", columns[5].trim());
//                gameValues.put("MAX_PLAYERS", columns[6].trim());
//                gameValues.put("MIN_TIME", columns[7].trim());
//                gameValues.put("MAX_TIME", columns[8].trim());
//                gameValues.put("MIN_AGE", columns[9].trim());
////                gameValues.put("FAVORITE", columns[10].trim());
//                db.insert("LIBRARY", null, gameValues);
//                Log.d("DummyDBpop", "Row populated: " + gameValues);
//                Log.d("DummyDBpop", "count: " + count);
//            }
//        } catch (IOException e) {
//            Log.d("DummyDBpop", "Couldn't populate db: " + e);
//        }
//        db.setTransactionSuccessful();
//        db.endTransaction();
//        Log.d("DummyDBpop", "dummyDB populated");
//    }

}
