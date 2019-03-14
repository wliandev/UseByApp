package watermelons.myfridge;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
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

        long expDays = currentFood.getDays_until_expired();

        TextView expiration = foodItem.findViewById(R.id.expiration);
        ImageView clock = foodItem.findViewById(R.id.clock);
        expiration.setText(Long.toString(expDays) + " days");

        if (expDays > 3 && expDays <= 7) {
            expiration.setTextColor(Color.parseColor("#FFB900"));
            clock.setColorFilter(Color.parseColor("#FFB900"));
        } else if (expDays > 1 && expDays <= 3){
            expiration.setTextColor(Color.parseColor("#F78200"));
            clock.setColorFilter(Color.parseColor("#F78200"));
        } else if (expDays <= 1){
            expiration.setTextColor(Color.parseColor("#E23838"));
            clock.setColorFilter(Color.parseColor("#E23838"));
        } else {
            expiration.setTextColor(Color.parseColor("#B2B2B2"));
            clock.setColorFilter(Color.parseColor("#B2B2B2"));
        }

        return foodItem;
    }

}