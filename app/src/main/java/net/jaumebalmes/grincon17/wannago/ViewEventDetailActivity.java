package net.jaumebalmes.grincon17.wannago;

import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;

public class ViewEventDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_event);

        ImageView img = findViewById(R.id.imageViewDetailEvent);
        TextView name = findViewById(R.id.textViewDetailName);
        TextView date = findViewById(R.id.textViewDetailDate);
        TextView time = findViewById(R.id.textViewDetailTime);
        TextView location = findViewById(R.id.textViewDetailLocation);
        TextView description = findViewById(R.id.textViewDetailDescription);


        Gson gson = new Gson();
        Event event = gson.fromJson(getIntent().getStringExtra("JSON_EVENT"), Event.class);
        img.setImageURI(Uri.parse(event.getImg()));
        name.setText(event.getName());
        date.setText(event.getDate());
        time.setText(event.getTime());
        location.setText(event.getLocation());
        description.setText(event.getDescription());
    }
}
