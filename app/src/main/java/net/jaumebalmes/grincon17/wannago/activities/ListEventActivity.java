package net.jaumebalmes.grincon17.wannago.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;

import net.jaumebalmes.grincon17.wannago.R;
import net.jaumebalmes.grincon17.wannago.interfaces.OnEventListInteractionListener;
import net.jaumebalmes.grincon17.wannago.models.Event;

public class ListEventActivity extends AppCompatActivity implements OnEventListInteractionListener {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_event);



        FloatingActionButton fab = findViewById(R.id.addEventFab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ListEventActivity.this, AddEventActivity.class));
            }
        });
    }

    @Override
    public void onEventClickListener(Event event) {
        String json = new Gson().toJson(event);
        Intent sendEventToActivity = new Intent(ListEventActivity.this, ViewEventDetailActivity.class);
        sendEventToActivity.putExtra("JSON_EVENT", json);
        startActivity(sendEventToActivity);
    }
}
