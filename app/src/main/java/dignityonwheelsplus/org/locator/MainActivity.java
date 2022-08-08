package dignityonwheelsplus.org.locator;

import android.content.res.AssetManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;



public class MainActivity extends AppCompatActivity {
    public static AssetManager assets;
    public static LayoutInflater inflater;

    public static HomeFragment home;
    public static ScheduleFragment schedule;
    public static AboutFragment about;
    public static AdminSignInFragment admin;

    private Toolbar topBar;
    private BottomNavigationView navBar;

    private TextView ui_hot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        assets = getAssets();

        inflater = getLayoutInflater();

        home = new HomeFragment();
        home.setButtonOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(view.getId() == R.id.schedule_btn)
                    navBar.setSelectedItemId(R.id.nav_schedule);
            }
        });

        schedule = new ScheduleFragment();

        about = new AboutFragment();

        admin = new AdminSignInFragment();

        //The top bar of the application.
        topBar = (Toolbar) findViewById(R.id.toolbar);
        topBar.setLogo(R.drawable.resizedlogo);
        setSupportActionBar(topBar);

        navBar = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        navBar.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment selectedFragment = null;

                switch (item.getItemId()) {
                    case R.id.nav_home:
                        selectedFragment = home;
                        break;
                    case R.id.nav_schedule:
                        selectedFragment = schedule;
                        break;
                    case R.id.nav_about:
                        selectedFragment = about;
                        break;
                    case R.id.nav_admin:
                        selectedFragment = admin;
                        break;
                }

                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container, selectedFragment)
                        .commit();

                return true;
            }
        });

        navBar.setSelectedItemId(R.id.nav_home);
    }

//     public boolean onCreateOptionsMenu(Menu menu) {
//         MenuInflater inflater = getMenuInflater();
//         inflater.inflate(R.menu.menu_actionbar,menu);


//         return true;
//     }



}
