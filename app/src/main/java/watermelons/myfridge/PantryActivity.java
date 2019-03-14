package watermelons.myfridge;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;

import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
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
        readPantry();
        Collections.sort(pantryList);
        pantryListView = findViewById(R.id.pantry_list);

        //custom adapter
        pantryAdapter = new PantryAdapter(this, pantryList);
        pantryListView.setAdapter(pantryAdapter);

        Collections.sort(pantryList);

        //custom action bar
        Toolbar myToolbar = (Toolbar) findViewById(R.id.pantry_toolbar);
        setSupportActionBar(myToolbar);

    }

    public void itemClickedDelete(final View view){
        final CheckBox checkBox = (CheckBox)view;
        if (checkBox.isChecked()){
            AlertDialog.Builder addDialog = new AlertDialog.Builder(this);
            addDialog.setCancelable(true);
            addDialog.setTitle("Delete Item");
            addDialog.setMessage("Are You Sure You Want To Delete This Item?");
            addDialog.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    deletePantry(view);
                    checkBox.setChecked(false);
                }
            });
            addDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    checkBox.setChecked(false);
                }
            });
            addDialog.create();
            addDialog.show();
        }
    }

    public void toGroceryActivity(View view){
        Intent intent = new Intent(this, GroceryActivity.class);
        startActivity(intent);
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

    public void deletePantry(View view){
        View parent = (View)view.getParent();
        TextView taskView = parent.findViewById(R.id.name);
        TextView expView = parent.findViewById(R.id.expiration);
        String description = String.valueOf(taskView.getText());
        String exp = String.valueOf(expView.getText());
        pantryList.remove(searchFood(description, exp)); // remove from array using a search function (below)
        pantryAdapter.notifyDataSetChanged();
        writePantry();
    }

    public Food searchFood(String name, String exp) {
        for (Food f : pantryList){
            String formatted = String.valueOf(f.getDays_until_expired()) + " days";
            if (f.getName().equals(name) && formatted.equals(exp)){
                return f;
            }
        }
        return null;
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



}