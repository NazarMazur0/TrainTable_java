package com.example.traintable_java;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class HeaderMatchAdapter extends RecyclerView.Adapter<HeaderMatchAdapter.ViewHolder> {

    private ArrayList<Match> matches;


    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView code;
        private final TextView start;
        private final TextView startTime;
        private final TextView end;
        private final TextView endTime;

        public ViewHolder(View view) {
            super(view);
            code =  view.findViewById(R.id.TextView1);
            start =  view.findViewById(R.id.TextView2);
            startTime =  view.findViewById(R.id.TextView3);
            end = view.findViewById(R.id.TextView4);
            endTime = view.findViewById(R.id.TextView5);
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


    public HeaderMatchAdapter(ArrayList<Match> matches) {
        this.matches = matches;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.list_header_route, viewGroup, false);

        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {


        viewHolder.getCode().setText(matches.get(position).getCode());
        viewHolder.getStart().setText(matches.get(position).getStart());
        viewHolder.getEnd().setText(matches.get(position).getEnd());
        viewHolder.getStartTime().setText(matches.get(position).getStartTime());
        viewHolder.getEndTime().setText(matches.get(position).getEndTime());
    }


    @Override
    public int getItemCount() {
        return matches.size();
    }
}