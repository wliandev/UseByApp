package watermelons.myfridge;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class GroceryActivity extends AppCompatActivity {

    private ArrayList<Food> groceryList;
    private ArrayAdapter<Food> groceryAdapter;
    private ListView groceryListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grocery);

        groceryList = new ArrayList<Food>();
        groceryListView = findViewById(R.id.grocery_list);

        //custom adapter
        groceryAdapter = new GroceryAdapter(this, groceryList);
        groceryListView.setAdapter(groceryAdapter);

        groceryList.add(new Food("Bread"));
        groceryList.add(new Food("Milk"));

        //custom action bar
        Toolbar myToolbar = (Toolbar) findViewById(R.id.grocery_toolbar);
        setSupportActionBar(myToolbar);

    }

    public void toPantryActivity(View view){
        Intent intent = new Intent(this, PantryActivity.class);
        startActivity(intent);
    }

}