package net.jaumebalmes.grincon17.wannago.adapters;

import android.app.Activity;
import android.content.Context;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;

import androidx.annotation.NonNull;

import net.jaumebalmes.grincon17.wannago.models.PlaceApi;

import java.util.ArrayList;

public class PlaceAutocompleteAdapter extends ArrayAdapter implements Filterable {
    private ArrayList<String> results;
    private Context context;
    private int resource;
    private PlaceApi placeApi = new PlaceApi();
    private Activity activity;

    public PlaceAutocompleteAdapter(@NonNull Context context, int resource) {
        super(context, resource);
        this.context = context;
        this.resource = resource;
        this.activity = activity;
    }

    @Override
    public int getCount() {
        return results.size();
    }

    @Override
    public String getItem(int pos) {
        return results.get(pos);
    }

    @Override
    public Filter getFilter() {
        Filter filter = new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults filterResults = new FilterResults();
                if(constraint != null) {
                    results = placeApi.autoComplete(constraint.toString(), context);
                    filterResults.values= results;
                    filterResults.count= results.size();
                }
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                if (results != null && results.count > 0) {
                    notifyDataSetChanged();
                } else {
                    notifyDataSetInvalidated();
                }
            }
        };
        return filter;
    }
}
