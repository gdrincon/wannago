package net.jaumebalmes.grincon17.wannago.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;

import net.jaumebalmes.grincon17.wannago.R;
import net.jaumebalmes.grincon17.wannago.adapters.MyEventRecyclerViewAdapter;
import net.jaumebalmes.grincon17.wannago.interfaces.OnEventListInteractionListener;
import net.jaumebalmes.grincon17.wannago.models.Event;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;


public class AccountFragment extends Fragment {
    private List<Event> eventList;
    private OnEventListInteractionListener mListener;

    public AccountFragment() {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
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

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_scrolling, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.list);
        recyclerView.setAdapter((new MyEventRecyclerViewAdapter(getActivity(), eventList, mListener)));
        return view;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
}
