package watermelons.myfridge;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

public class PantryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantry);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.pantry_toolbar);
        setSupportActionBar(myToolbar);

    }

    public void toGroceryActivity(View view){
        Intent intent = new Intent(this, GroceryActivity.class);
        startActivity(intent);
    }

}