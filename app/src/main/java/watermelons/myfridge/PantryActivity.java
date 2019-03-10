package watermelons.myfridge;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Collections;

public class PantryActivity extends AppCompatActivity {

    private ArrayList<Food> pantryList;
    private ArrayAdapter<Food> pantryAdapter;
    private ListView pantryListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantry);

        pantryList = new ArrayList<Food>();
        pantryListView = findViewById(R.id.pantry_list);

        //custom adapter
        pantryAdapter = new PantryAdapter(this, pantryList);
        pantryListView.setAdapter(pantryAdapter);

        pantryList.add(new Food("Butter", 7));
        pantryList.add(new Food( "Carrots", 10));
        pantryList.add(new Food("Apples", 1));
        pantryList.add(new Food("Eggs", 2));

        Collections.sort(pantryList);

        //custom action bar
        Toolbar myToolbar = (Toolbar) findViewById(R.id.pantry_toolbar);
        setSupportActionBar(myToolbar);

    }

    public void toGroceryActivity(View view){
        Intent intent = new Intent(this, GroceryActivity.class);
        startActivity(intent);
    }

}