package dignityonwheelsplus.org.locator;

import android.content.Intent;
import android.content.res.AssetManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

public class Admin extends AppCompatActivity{

    public static AssetManager assets;
    public static LayoutInflater inflater;
    public static ScheduleFragment schedule;

    private Toolbar topBar;
    private Button logout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        assets = getAssets();

        inflater = getLayoutInflater();

        schedule = new ScheduleFragment();
        schedule.setAdmin(true);



        logout = (Button) findViewById(R.id.logout_button);

        logout.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick (View v){
                Intent intent = new Intent (Admin.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        topBar = (Toolbar) findViewById(R.id.admintoolbar);
        setSupportActionBar(topBar);

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, schedule)
                .commit();

    }

}


