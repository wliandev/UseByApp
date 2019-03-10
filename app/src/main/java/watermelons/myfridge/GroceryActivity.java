package watermelons.myfridge;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Calendar;

public class GroceryActivity extends AppCompatActivity {

    private ArrayList<Food> groceryList;
    private ArrayAdapter<Food> groceryAdapter;
    private ListView groceryListView;
    private DatePickerDialog datePickerDialog;
    private Calendar calendar;
    private Calendar expdate;

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

    public Food searchItem(String string) {
        for (Food f : groceryList){
            if (f.getName().equals(string)){
                return f;
            }
        }
        return null;
    }

    public void itemClicked(View view){
        CheckBox checkBox = (CheckBox)view;
        if (checkBox.isChecked()){
            final int month,day_of_month,year;
            calendar = calendar.getInstance();
            year = calendar.get(Calendar.YEAR);
            month = calendar.get(Calendar.MONTH);
            day_of_month = calendar.get(Calendar.DAY_OF_MONTH);
            datePickerDialog = new DatePickerDialog(GroceryActivity.this,
                    new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                            expdate.set(year,month,dayOfMonth);
                        }
                    }, year, month, day_of_month);
            datePickerDialog.show();
        }
    }

    public void toPantryActivity(View view){
        Intent intent = new Intent(this, PantryActivity.class);
        startActivity(intent);
    }

}