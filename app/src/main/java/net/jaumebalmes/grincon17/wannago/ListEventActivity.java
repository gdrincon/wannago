package net.jaumebalmes.grincon17.wannago;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;

public class ListEventActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_event);

        ListView listItems = findViewById(R.id.listViewEvents);

        String json = "";
        try {
            InputStream stream = getAssets().open("events.json");
            int size = stream.available();
            byte[] buffer = new byte[size];
            stream.read(buffer);
            json  = new String(buffer);
            stream.close();
        } catch (Exception e) {
            System.err.println(e);
        }

        ArrayList<Event> eventArrayList = new ArrayList<>(Arrays.asList(new Gson().fromJson(json, Event[].class)));
        AdapterItemEvent item = new AdapterItemEvent(this, eventArrayList);
        listItems.setAdapter(item);

        FloatingActionButton fab = findViewById(R.id.addEventFab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ListEventActivity.this, AddEventActivity.class));
            }
        });
    }
}
