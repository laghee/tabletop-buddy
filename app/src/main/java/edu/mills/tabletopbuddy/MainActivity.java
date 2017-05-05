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
}
