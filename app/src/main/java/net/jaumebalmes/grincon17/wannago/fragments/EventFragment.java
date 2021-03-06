package net.jaumebalmes.grincon17.wannago.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;

import net.jaumebalmes.grincon17.wannago.adapters.MyEventRecyclerViewAdapter;
import net.jaumebalmes.grincon17.wannago.R;
import net.jaumebalmes.grincon17.wannago.interfaces.OnEventListInteractionListener;
import net.jaumebalmes.grincon17.wannago.models.Event;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class EventFragment extends Fragment {

    private List<Event> eventList;
    private OnEventListInteractionListener mListener;

    public EventFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        eventList = new ArrayList<>();
        try {
            InputStream stream = Objects.requireNonNull(getActivity()).getAssets().open("events.json");
            int size = stream.available();
            byte[] buffer = new byte[size];
            stream.read(buffer);
            String json  = new String(buffer);
            eventList = Arrays.asList(new Gson().fromJson(json, Event[].class));
            stream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_event_list, container, false);
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;
            recyclerView.setLayoutManager(new LinearLayoutManager(context));
            recyclerView.setAdapter(new MyEventRecyclerViewAdapter(getActivity(), eventList, mListener));
        }
        return view;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof OnEventListInteractionListener) {
            mListener = (OnEventListInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnEventListInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }
}
