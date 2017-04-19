package edu.mills.tabletopbuddy;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class RandomGameActivity extends Activity implements AdapterView.OnItemSelectedListener {
    //eventually the two arrays could be grabbed from BGG API
    String[] themes = {};
    String[] types = {};
    //what should be the max players?
    String[] players = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10"};

    //References
    //http://stackoverflow.com/questions/13909109/two-spinner-in-one-activity
    //https://www.tutorialspoint.com/android/android_spinner_control.htm
    //http://stackoverflow.com/questions/363681/how-to-generate-random-integers-within-a-specific-range-in-java

    //Made changes to strings.xml and activity_random_game.xml
    //No changes made to AndroidManifest.xml

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_random_game);

        // Theme Spinner
        Spinner themeSpinner = (Spinner) findViewById(R.id.themespinner);
        ArrayAdapter<String> themeAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, themes);
        //there was an error with 'this' so it suggested to cast this - not sure if this
        //is the best way to handle
        themeSpinner.setOnItemClickListener((AdapterView.OnItemClickListener) this);
        themeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        themeSpinner.setAdapter(themeAdapter);

        // Game Type Spinner
        Spinner typeSpinner = (Spinner) findViewById(R.id.typespinner);
        ArrayAdapter<String> typeAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, types);
        typeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        typeSpinner.setAdapter(typeAdapter);
        typeSpinner.setOnItemClickListener((AdapterView.OnItemClickListener) this);

        //Min Player Spinner
        Spinner minPlayerSpinner = (Spinner) findViewById(R.id.minplayerspinner);
        //change to int
        ArrayAdapter<String> minPlayerAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, players);
        minPlayerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        minPlayerSpinner.setAdapter(typeAdapter);
        minPlayerSpinner.setOnItemClickListener((AdapterView.OnItemClickListener) this);

        //Max Player Spinner
        Spinner maxPlayerSpinner = (Spinner) findViewById(R.id.maxplayerspinner);
        //change to int
        ArrayAdapter<String> maxPlayerAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, players);
        maxPlayerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        maxPlayerSpinner.setAdapter(typeAdapter);
        maxPlayerSpinner.setOnItemClickListener((AdapterView.OnItemClickListener) this);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Spinner themeSpinner = (Spinner) parent;
        Spinner typeSpinner = (Spinner) parent;
        Spinner minPlayerSpinner = (Spinner) parent;
        Spinner maxPlayerSpinner = (Spinner) parent;

        if(themeSpinner.getId() == R.id.themespinner)
        {
            //a theme was chosen - save theme
            String theme = parent.getItemAtPosition(position).toString();
        }
        if(typeSpinner.getId() == R.id.typespinner)
        {
            //a theme was chosen - save theme
            String type = parent.getItemAtPosition(position).toString();
        }
        if (minPlayerSpinner.getId() == R.id.minplayerspinner) {
            //max player was chosen - save theme
            String minPlayers = parent.getItemAtPosition(position).toString();
        }
        if (maxPlayerSpinner.getId() == R.id.maxplayerspinner) {
            //max player was chosen - save theme
            String maxPlayers = parent.getItemAtPosition(position).toString();
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    //Generates a random number to query the database
    public int generateRandomNumber(Boolean collection) {
        int min = 0;
        int max;

        //if the game is selected within personal collection
        if (collection) {
            //personal collection size
            max = 100;
        }
        else {
            max = 3333;
        }
        return min + (int)(Math.random() * max);
    }

    //Call GameDetailActivity with Spinner Results
}
