package watermelons.myfridge;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

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

        pantryList.add(new Food("Eggs"));
        pantryList.add(new Food("Butter"));

        //custom action bar
        Toolbar myToolbar = (Toolbar) findViewById(R.id.pantry_toolbar);
        setSupportActionBar(myToolbar);

    }

    public void toGroceryActivity(View view){
        Intent intent = new Intent(this, GroceryActivity.class);
        startActivity(intent);
    }

}