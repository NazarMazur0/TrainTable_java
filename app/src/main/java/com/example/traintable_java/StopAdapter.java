package com.example.traintable_java;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class StopAdapter extends RecyclerView.Adapter<StopAdapter.ViewHolder> {

    private ArrayList<Stop> stops;


    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView city;
        private final TextView arrivalTime;
        private final TextView departmentTime;



        public ViewHolder(View view) {
            super(view);
            city = view.findViewById(R.id.city) ;
            arrivalTime = view.findViewById(R.id.arrivalTime);
            departmentTime = view.findViewById(R.id.departmentTime);
        }

        public TextView getCity() {
            return city;
        }

        public TextView getArrivalTime() {
            return arrivalTime;
        }

        public TextView getDepartmentTime() {
            return departmentTime;
        }
    }


    public StopAdapter(ArrayList<Stop> stops) {
        this.stops = stops;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.stop, viewGroup, false);

        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {


        viewHolder.getCity().setText(stops.get(position).getCity());
        viewHolder.getArrivalTime().setText(stops.get(position).getArrivalTime());
        viewHolder.getDepartmentTime().setText(stops.get(position).getDepartmentTime());
    }


    @Override
    public int getItemCount() {
        return stops.size();
    }
}