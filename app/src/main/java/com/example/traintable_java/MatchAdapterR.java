package com.example.traintable_java;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MatchAdapterR extends RecyclerView.Adapter<MatchAdapterR.ViewHolder> {

    private ArrayList<Match> matches;

    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView code;
        private final TextView start;
        private final TextView startTime;
        private final TextView end;
        private final TextView endTime;

        public ViewHolder(View view) {
            super(view);
            code = (TextView) view.findViewById(R.id.code);
            start = (TextView) view.findViewById(R.id.start);
            startTime = (TextView) view.findViewById(R.id.startTime);
            end = (TextView) view.findViewById(R.id.end);
            endTime = (TextView) view.findViewById(R.id.endTime);
        }

        public TextView getCode() {
            return code;
        }

        public TextView getStart() {
            return start;
        }

        public TextView getStartTime() {
            return startTime;
        }

        public TextView getEnd() {
            return end;
        }

        public TextView getEndTime() {
            return endTime;
        }
    }

    /**
     * Initialize the dataset of the Adapter.
     *
     * @param dataSet String[] containing the data to populate views to be used
     * by RecyclerView.
     */
    public MatchAdapterR(ArrayList<Match> matches) {
        this.matches = matches;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.route, viewGroup, false);

        return new ViewHolder(view);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {


        viewHolder.getCode().setText(matches.get(position).getCode());
        viewHolder.getStart().setText(matches.get(position).getStart());
        viewHolder.getEnd().setText(matches.get(position).getEnd());
        viewHolder.getStartTime().setText(matches.get(position).getStartTime());
        viewHolder.getEndTime().setText(matches.get(position).getEndTime());
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return matches.size();
    }
}