package edu.mills.tabletopbuddy;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends Activity {
    private static final String searchToast = "This will link to the Search Activity :)";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_detail);
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

}
