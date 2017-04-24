package edu.mills.tabletopbuddy;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.ArrayList;

public class RandomGameActivity extends Activity implements AdapterView.OnItemSelectedListener {
    String[] minAges = {"7", "8", "9", "10", "11", "12", "13"};
    String[] minTimes = {"0.5", "1", "1.5", "2", "2.5", "3", "3.5", "4"};
    String[] minPlayers = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10"};
    String selectedMinAge;
    String selectedMinTime;
    String selectedMinPlayer;
    ArrayList<String> selectedCategories = new ArrayList<String>();

    //References
    //http://stackoverflow.com/questions/12594207/using-multiple-spinners-in-the-same-layout
    //http://stackoverflow.com/questions/13909109/two-spinner-in-one-activity
    //https://www.tutorialspoint.com/android/android_spinner_control.htm
    //http://stackoverflow.com/questions/363681/how-to-generate-random-integers-within-a-specific-range-in-java

    //Made changes to strings.xml and activity_random_game.xml
    //No changes made to AndroidManifest.xml

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_random_game);

//        //Min Player Spinner
//        Spinner minPlayerSpinner = (Spinner) findViewById(R.id.minplayerspinner);
//        ArrayAdapter<String> minPlayerAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, minPlayers);
//        minPlayerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        minPlayerSpinner.setAdapter(minPlayerAdapter);
//        minPlayerSpinner.setOnItemClickListener((AdapterView.OnItemClickListener) this);
//
//        //Min Age Spinner
//        Spinner minAgeSpinner = (Spinner) findViewById(R.id.minagespinner);
//        ArrayAdapter<String> minAgeAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, minAges);
//        minAgeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        minAgeSpinner.setAdapter(minAgeAdapter);
//        minAgeSpinner.setOnItemClickListener((AdapterView.OnItemClickListener) this);
//
//        //Min Time Spinner
//        Spinner minTimeSpinner = (Spinner) findViewById(R.id.mintimespinner);
//        ArrayAdapter<String> minTimeAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, minTimes);
//        minTimeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        minTimeSpinner.setAdapter(minTimeAdapter);
//        minTimeSpinner.setOnItemClickListener((AdapterView.OnItemClickListener) this);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Spinner minPlayerSpinner = (Spinner) parent;
        Spinner minAgeSpinner = (Spinner) parent;
        Spinner minTimeSpinner = (Spinner) parent;

        if(minTimeSpinner.getId() == R.id.mintimespinner) {
            selectedMinTime = parent.getItemAtPosition(position).toString(); }
        if (minPlayerSpinner.getId() == R.id.minplayerspinner) {
            selectedMinPlayer = parent.getItemAtPosition(position).toString(); }
        if (minAgeSpinner.getId() == R.id.minagespinner) {
            selectedMinAge = parent.getItemAtPosition(position).toString(); }
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
