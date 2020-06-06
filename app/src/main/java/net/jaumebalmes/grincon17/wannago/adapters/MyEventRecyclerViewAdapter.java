package net.jaumebalmes.grincon17.wannago.adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import net.jaumebalmes.grincon17.wannago.R;
import net.jaumebalmes.grincon17.wannago.models.Event;
import net.jaumebalmes.grincon17.wannago.interfaces.OnEventListInteractionListener;
import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link Event} and makes a call to the
 * specified {@link OnEventListInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class MyEventRecyclerViewAdapter extends RecyclerView.Adapter<MyEventRecyclerViewAdapter.ViewHolder> {

    private final List<Event> mValues;
    private final OnEventListInteractionListener mListener;
    private final Context mContext;

    public MyEventRecyclerViewAdapter(Context context, List<Event> items, OnEventListInteractionListener listener) {
        mContext = context;
        mValues = items;
        mListener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_event, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.textViewName.setText(holder.mItem.getName());
        holder.textViewDate.setText(holder.mItem.getDate());
        holder.textViewTime.setText(holder.mItem.getTime());
        holder.textViewLocation.setText(holder.mItem.getLocation());
        Glide.with(mContext).load(holder.mItem.getImg()).centerCrop().into(holder.imageViewEvent);


        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    mListener.onEventClickListener(holder.mItem);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final ImageView imageViewEvent;
        public final TextView textViewDate;
        public final TextView textViewTime;
        public final TextView textViewLocation;
        public final TextView textViewName;
        public Event mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            imageViewEvent = view.findViewById(R.id.imageViewPoster);
            textViewDate = view.findViewById(R.id.textViewDate);
            textViewTime = view.findViewById(R.id.textViewTime);
            textViewLocation = view.findViewById(R.id.textViewLocation);
            textViewName = view.findViewById(R.id.textViewName);
        }

    }
}
