package watermelons.myfridge;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class PantryAdapter extends ArrayAdapter<Food> {

    private Context mycontext;
    private List<Food> myPantryList;

    public PantryAdapter(Context context, ArrayList<Food> pantryList) {

        super(context, 0, pantryList);
        mycontext = context;
        myPantryList= pantryList;
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        View foodItem = convertView;
        if (foodItem == null) {
            foodItem = LayoutInflater.from(mycontext).inflate(R.layout.pantry_item, parent, false);
        }

        Food currentFood = myPantryList.get(position);

        TextView name = foodItem.findViewById(R.id.name);
        name.setText(currentFood.getName());

        TextView expiration = foodItem.findViewById(R.id.expiration);
        expiration.setText(Long.toString(currentFood.getDays_until_expired()) + " days");

        return foodItem;
    }

}