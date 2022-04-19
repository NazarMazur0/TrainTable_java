package com.example.traintable_java;

import android.util.Log;

import java.util.*;

public class Route {
    private final int  id;
    private final String code;
    private final ArrayList<ArrayList<String>> stops;
     Route(int id, String number, String startPoint, String endPoint, String[][] stops) {
        this.id = id;
        this.code = number;
        ArrayList<ArrayList<String>> arstops= new ArrayList<>();
         for (String[] strings : stops) {
             ArrayList<String> stop = new ArrayList<>();
             Collections.addAll(stop, strings);
             arstops.add(stop);
         }
        this.stops = arstops;
    }



    public int getId() {
        return id;
    }

    public String getCode() {
        return code;
    }

    public ArrayList<ArrayList<String>> getStops() {
        return stops;
    }
    TreeMap<String,String> getMapOfStops(){
        TreeMap<String,String> stops = new TreeMap<>();
        ArrayList<ArrayList<String>> routestops=this.getStops();
        for (ArrayList<String> routestop : routestops) {
            stops.put(routestop.get(0), routestop.get(1));
        }
        return stops;
    }
    String[] getStopsNames(){

        ArrayList<ArrayList<String>> routestops=this.getStops();
        String[] stopsNames= new String[routestops.size()];

        for (int i = 0; i < stopsNames.length; i++) {
            stopsNames[i]=routestops.get(i).get(0);
        }
        return stopsNames;
    }
    ArrayList<Stop> getStopsList(){
        ArrayList<Stop> s = new ArrayList<>();
        getStops().forEach(i->{
            s.add(new Stop(i.get(0),i.get(1)));
        });
        return s;
    }


    @Override
    public String toString() {
        return "Route{" +
                "id=" + id +
                ", code='" + code + '\''  +
                ", stops=" + stops +
                '}';
    }
}
