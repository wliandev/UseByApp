package watermelons.myfridge;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;

public class GroceryActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    private ArrayList<Food> groceryList;
    private ArrayAdapter<Food> groceryAdapter;
    private ListView groceryListView;
    private ArrayList<Food> pantryList;
    private String current;
    private View temp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grocery);

        groceryList = new ArrayList<Food>();
        readGrocery();
        readPantry();
        groceryListView = findViewById(R.id.grocery_list);

        //custom adapter
        groceryAdapter = new GroceryAdapter(this, groceryList);
        groceryListView.setAdapter(groceryAdapter);

        //custom action bar
        Toolbar myToolbar = (Toolbar) findViewById(R.id.grocery_toolbar);
        setSupportActionBar(myToolbar);
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth){
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month);
        c.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        Date expdate = c.getTime();
        Food f = new Food(current);
        f.setDateBought();
        f.setDateExpires(expdate);
        pantryList.add(f);
        //String test = DateFormat.getDateInstance(DateFormat.FULL).format(expdate);
        //Toast.makeText(getBaseContext(), current, Toast.LENGTH_LONG).show();
        writePantry();
        deleteGrocery(temp);
    }


    public void addFoodDialog(View view){
        final View view1 = LayoutInflater.from(this).inflate(R.layout.dialog_layout, null);
        final EditText nameText = view1.findViewById(R.id.editText);

        AlertDialog.Builder addDialog = new AlertDialog.Builder(this);
        addDialog.setTitle("Add Food to Grocery List");
        addDialog.setMessage("What item do you want to buy?");
        addDialog.setView(view1);
        addDialog.setPositiveButton("Add", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                groceryList.add(new Food(String.valueOf(nameText.getText())));
                writeGrocery();
                groceryAdapter.notifyDataSetChanged();
            }
        });
        addDialog.create();
        addDialog.show();
    }


    public void itemClicked(View view){
        CheckBox checkBox = (CheckBox)view;
        View parent = (View) view.getParent();
        if (checkBox.isChecked()){
            TextView textView = parent.findViewById(R.id.textView_id);
            current = String.valueOf(textView.getText());
            DialogFragment datePicker = new DatePickerFragment();
            datePicker.show(getSupportFragmentManager(), "Date Picker");
            temp = view;
        }
        checkBox.setChecked(false);
    }

    public void toPantryActivity(View view){
        Intent intent = new Intent(this, PantryActivity.class);
        startActivity(intent);
    }

    public void writeGrocery(){
        try {
            FileOutputStream fos = openFileOutput("GroceryList.txt", MODE_PRIVATE);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            for (Food f: groceryList){
                oos.writeObject(f);
            }
            oos.close();
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public void readGrocery(){
        File dir = this.getFilesDir();
        File toDo = new File(dir, "GroceryList.txt");
        try {
            FileInputStream fis = new FileInputStream(toDo);
            ObjectInputStream ois = new ObjectInputStream(fis);
            groceryList = new ArrayList<Food>();
            try{
                while(true) {
                    Food f = (Food) ois.readObject();
                    groceryList.add(f);
                }
            } catch (EOFException e) { } // Streams DO NOT return null after end, so just catch EOF.
        } catch (IOException e) {
            e.printStackTrace();
            groceryList = new ArrayList<Food>();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }


    public void writePantry(){
        try {
            FileOutputStream fos = openFileOutput("PantryList.txt", MODE_PRIVATE);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            for (Food f: pantryList) {
                oos.writeObject(f);
            }
            oos.close();
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public void readPantry(){
        File dir = this.getFilesDir();
        File toDo = new File(dir, "PantryList.txt");
        try {
            FileInputStream fis = new FileInputStream(toDo);
            ObjectInputStream ois = new ObjectInputStream(fis);
            pantryList = new ArrayList<Food>();
            try{
                while(true) {
                    Food f = (Food) ois.readObject();
                    f.checkExpired();
                    pantryList.add(f);
                }
            } catch (EOFException e) { } // Streams DO NOT return null after end, so just catch EOF.
        } catch (IOException e) {
            e.printStackTrace();
            pantryList = new ArrayList<Food>();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void deleteGrocery(View view){
        View parent = (View)view.getParent();
        TextView taskView = parent.findViewById(R.id.textView_id);
        String description = String.valueOf(taskView.getText());
        groceryList.remove(searchFood(description)); // remove from array using a search function (below)
        groceryAdapter.notifyDataSetChanged();
        writeGrocery();
    }

    public Food searchFood(String string) {
        for (Food f : groceryList){
            if (f.getName().equals(string)){
                return f;
            }
        }
        return null;
    }

}