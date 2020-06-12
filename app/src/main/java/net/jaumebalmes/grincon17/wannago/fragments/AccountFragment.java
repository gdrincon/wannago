package net.jaumebalmes.grincon17.wannago.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;

import net.jaumebalmes.grincon17.wannago.R;
import net.jaumebalmes.grincon17.wannago.adapters.MyEventRecyclerViewAdapter;
import net.jaumebalmes.grincon17.wannago.interfaces.OnEventListInteractionListener;
import net.jaumebalmes.grincon17.wannago.models.Event;
import net.jaumebalmes.grincon17.wannago.preferences.Image;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;


public class AccountFragment extends Fragment {
    private List<Event> eventList;
    private OnEventListInteractionListener mListener;
    private static final int GALLERY_CODE = 0;
    private static final int CAMERA_CODE = 1;
    private String imageFilePath;
    private Uri filePath;
    private ImageView userProfileImg;
    private Image image;


    public AccountFragment() {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        image = new Image(getContext(), this);
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
        userProfileImg = view.findViewById(R.id.imageViewUserName);
        userProfileImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                image.chooseCameraOrGallery();

            }
        });

        RecyclerView recyclerView = view.findViewById(R.id.list);
        recyclerView.setAdapter((new MyEventRecyclerViewAdapter(getActivity(), eventList, mListener)));
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case CAMERA_CODE:
                    imageFilePath = image.getFilePath();
                    filePath = Uri.fromFile(new File(imageFilePath));
                    userProfileImg.setImageURI(filePath);
                    break;
                case GALLERY_CODE:
                    if (data != null) {
                        filePath = data.getData();
                        userProfileImg.setImageURI(filePath);
                        userProfileImg.setTag(String.valueOf(filePath));
                    }
                    break;
            }
        }
    }


}
