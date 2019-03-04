package watermelons.myfridge;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

public class GroceryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grocery);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.grocery_toolbar);
        setSupportActionBar(myToolbar);

    }

    public void toPantryActivity(View view){
        Intent intent = new Intent(this, PantryActivity.class);
        startActivity(intent);
    }

}
// this is a test