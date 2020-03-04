package net.jaumebalmes.grincon17.wannago;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import java.util.ArrayList;

class AdapterItemEvent extends BaseAdapter {
    private Activity activity;
    private ArrayList<Event> eventArrayList;

    public AdapterItemEvent(Activity activity, ArrayList<Event> eventArrayList) {
        this.activity = activity;
        this.eventArrayList = eventArrayList;
    }

    @Override
    public int getCount() {
        return eventArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return eventArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View customView = inflater.inflate(R.layout.list_event_item, null);
        Event event = eventArrayList.get(position);

        ImageView poster = customView.findViewById(R.id.imageViewPoster);
        TextView name = customView.findViewById(R.id.textViewName);
        TextView location = customView.findViewById(R.id.textViewLocation);
        TextView date = customView.findViewById(R.id.textViewDate);
        TextView time = customView.findViewById(R.id.textViewTime);

        name.setText(event.getName());
        name.setTextColor(ContextCompat.getColor(activity.getApplicationContext(), R.color.colorPrimaryDark));
        location.setText(event.getLocation());
        date.setText(event.getDate());
        time.setText(event.getTime());
        poster.setImageResource(R.drawable.wannago_bone);
        return customView;
    }
}
