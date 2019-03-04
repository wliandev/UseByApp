package watermelons.myfridge;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class GroceryAdapter extends ArrayAdapter<Food> {

    private Context mycontext;
    private List<Food> myGroceryList;

    public GroceryAdapter(Context context, ArrayList<Food> groceryList) {

        super(context, 0, groceryList);
        mycontext = context;
        myGroceryList= groceryList;
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        View foodItem = convertView;
        if (foodItem == null) {
            foodItem = LayoutInflater.from(mycontext).inflate(R.layout.grocery_item, parent, false);
        }

        Food currentFood = myGroceryList.get(position);

        TextView description = foodItem.findViewById(R.id.textView_id);
        description.setText(currentFood.getName());

        return foodItem;
    }

}