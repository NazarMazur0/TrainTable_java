package com.example.traintable_java;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class PeriodicAdapter extends RecyclerView.Adapter<PeriodicAdapter.ViewHolder> {

    private ArrayList<Match> matches;


    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView periodic;

        public ViewHolder(View view) {
            super(view);
            periodic=view.findViewById(R.id.periodic);
        }

        public TextView getPeriodic() {
            return periodic;
        }
    }


    public PeriodicAdapter(ArrayList<Match> matches) {
        this.matches = matches;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.periodic, viewGroup, false);

        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {


        viewHolder.getPeriodic().setText(matches.get(position).getPeriodic());
    }


    @Override
    public int getItemCount() {
        return matches.size();
    }
}