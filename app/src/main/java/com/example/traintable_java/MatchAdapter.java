package com.example.traintable_java;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MatchAdapter extends RecyclerView.Adapter<MatchAdapter.ViewHolder> {

    private ArrayList<Match> matches;


    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView code;
        private final TextView start;
        private final TextView startTime;
        private final TextView end;
        private final TextView endTime;
        private final TextView periodic;

        public ViewHolder(View view) {
            super(view);
            code =  view.findViewById(R.id.code);
            start =  view.findViewById(R.id.departmentCity);
            startTime =  view.findViewById(R.id.arrivalCity);
            end =  view.findViewById(R.id.arrivalCity);
            endTime =  view.findViewById(R.id.arrivalCity);
            periodic =  view.findViewById(R.id.periodic);
        }

        public TextView getPeriodic() {
            return periodic;
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


    public MatchAdapter(ArrayList<Match> matches) {
        this.matches = matches;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.route, viewGroup, false);

        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {


        viewHolder.getCode().setText(matches.get(position).getCode());
        viewHolder.getStart().setText(matches.get(position).getStart());
        viewHolder.getEnd().setText(matches.get(position).getEnd());
        viewHolder.getStartTime().setText(matches.get(position).getStartTime());
        viewHolder.getEndTime().setText(matches.get(position).getEndTime());
        viewHolder.getPeriodic().setText(matches.get(position).getPeriodic());
    }


    @Override
    public int getItemCount() {
        return matches.size();
    }
}