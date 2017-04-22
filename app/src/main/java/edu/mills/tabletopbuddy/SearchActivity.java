package edu.mills.tabletopbuddy;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class SearchActivity extends Activity {
    String[] results = new String[] { "EvilFairy", "Monopoly", "Another card game variant",
            "Another board game variant", "blahblah", "Settlers of Catan", "Pandemic", "CandyLand",
            "7 Wonders", "Spy" };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
    }

    public void onClickSearch(View view){
        Intent searchResultsIntent = new Intent(this, SearchResultsActivity.class);
        searchResultsIntent.putExtra("results", results);
        startActivity(searchResultsIntent);
    }
}
