package com.example.traintable_java;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class MatchAdapter extends ArrayAdapter<Match> {
    public MatchAdapter(Context context, ArrayList<Match> matchs) {
        super(context, 0, matchs);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Match match = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.route, parent, false);
        }
        // Lookup view for data population
        TextView number =  convertView.findViewById(R.id.code);
        TextView start =  convertView.findViewById(R.id.start);
        TextView end =  convertView.findViewById(R.id.startTime);
        TextView startTime =  convertView.findViewById(R.id.end);
        TextView endTime =  convertView.findViewById(R.id.endTime);
        number.setText(match.getCode());
        start.setText(match.getStart());
        startTime.setText(match.getStartTime());
        end.setText(match.getEnd());
        endTime.setText(match.getEndTime());
        convertView.setEnabled(true);
        convertView.setClickable(true);
        convertView.setFocusable(true);
        return convertView;
    }
}