package net.jaumebalmes.grincon17.wannago.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.gson.Gson;

import net.jaumebalmes.grincon17.wannago.R;
import net.jaumebalmes.grincon17.wannago.fragments.AccountFragment;
import net.jaumebalmes.grincon17.wannago.fragments.EventFragment;
import net.jaumebalmes.grincon17.wannago.interfaces.OnEventListInteractionListener;
import net.jaumebalmes.grincon17.wannago.models.Event;

public class BottomNavActivity extends AppCompatActivity implements OnEventListInteractionListener,
        BottomNavigationView.OnNavigationItemSelectedListener{


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_event);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.app_name);
        setSupportActionBar(toolbar);
        BottomNavigationView navigationView = findViewById(R.id.bottom_nav_view);
        navigationView.setOnNavigationItemSelectedListener(this);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.toolbar_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.search_icon :
                Toast.makeText(getApplicationContext(), "Search", Toast.LENGTH_LONG).show();
                break;
            case R.id.add_event :
                startActivity(new Intent(BottomNavActivity.this, AddEventActivity.class));
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onEventClickListener(Event event) {
        String json = new Gson().toJson(event);
        Intent sendEventToActivity = new Intent(BottomNavActivity.this, ViewEventDetailActivity.class);
        sendEventToActivity.putExtra("JSON_EVENT", json);
        startActivity(sendEventToActivity);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.navigation_events :
                Fragment currentFragment = new EventFragment();
                getSupportFragmentManager().beginTransaction().replace(R.id.fragmentEvents, currentFragment).commit();
                break;
            case R.id.navigation_user :
                currentFragment = new AccountFragment();
                getSupportFragmentManager().beginTransaction().replace(R.id.fragmentEvents, currentFragment).commit();
                break;
        }
        return true;
    }
}
