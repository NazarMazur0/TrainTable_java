package com.example.traintable_java;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class StopAdapterR extends RecyclerView.Adapter<StopAdapterR.ViewHolder> {

    private ArrayList<Stop> stops;

    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView city;
        private final TextView time;

        public ViewHolder(View view) {
            super(view);
            city = (TextView) view.findViewById(R.id.start);
            time = (TextView) view.findViewById(R.id.startTime);
        }

        public TextView getCity() {
            return city;
        }
        public TextView getTime() {
            return time;
        }
    }

    /**
     * Initialize the dataset of the Adapter.
     *
     * @param dataSet String[] containing the data to populate views to be used
     * by RecyclerView.
     */
    public StopAdapterR(ArrayList<Stop> stops) {
        this.stops = stops;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.stop, viewGroup, false);

        return new ViewHolder(view);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {


        viewHolder.getCity().setText(stops.get(position).getCity());
        viewHolder.getTime().setText(stops.get(position).getTime());
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return stops.size();
    }
}