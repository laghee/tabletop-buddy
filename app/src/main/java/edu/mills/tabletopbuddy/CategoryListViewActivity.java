package edu.mills.tabletopbuddy;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

public class CategoryListViewActivity extends Activity {
    //Class for list view of categories
    //Possibly going to be added to RandomGameActivity
    //http://android-coding.blogspot.in/2011/09/listview-with-multiple-choice.html
    ListView categoryListView;
    Button getChoices;
    ArrayList<String> selectedCategories = new ArrayList<String>();
    String[] listCategories = {"Adventure", "Dice", "Mafia", "Medieval", "Pirates", "Science", "War",
            "Economic", "Farming", "Maze", "Zombies", "Travel"};

    @Override
    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_random_game);
//        categoryListView = (ListView)findViewById(R.id.categorylist);
//        getChoices = (Button)findViewById(R.id.getChoices);
//        ArrayAdapter<String> categoryAdapter = new ArrayAdapter<String>(this,
//                android.R.layout.simple_list_item_multiple_choice, listCategories);
//        categoryListView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
//        categoryListView.setAdapter(categoryAdapter);
//        getChoices.setOnClickListener(new Button.OnClickListener(){
//
//            @Override
//            public void onClick(View v) {
//                String selected = "";
//                int countChoice = categoryListView.getCount();
//                SparseBooleanArray sparseBooleanArray = categoryListView.getCheckedItemPositions();
//                for(int i = 0; i < countChoice; i++){
//                    if(sparseBooleanArray.get(i)) {
//                        selectedCategories.add(categoryListView.getItemAtPosition(i).toString());
//                    }
//                }
//                Toast.makeText(CategoryListViewActivity.this, selected, Toast.LENGTH_LONG).show();
//            }});
    }
}
