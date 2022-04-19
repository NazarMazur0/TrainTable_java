package com.example.traintable_java;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class StopAdapter extends ArrayAdapter<Stop> {
    public StopAdapter(Context context, ArrayList<Stop> matchs) {
        super(context, 0, matchs);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Stop match = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.stop, parent, false);
        }
        TextView city =  convertView.findViewById(R.id.start);
        TextView time =  convertView.findViewById(R.id.startTime);
        city.setText(match.getCity());
        time.setText(match.getTime());
        return convertView;
    }
}