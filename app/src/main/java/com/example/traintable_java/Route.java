package com.example.traintable_java;

import java.util.*;

public class Route {
    private final int  id;
    private final String code;
    private final String periodic;
    private final ArrayList<ArrayList<String>> stops;
     Route(int id, String number, String anotation, String[][] stops) {
        this.id = id;
        this.code = number;
        this.periodic =anotation;
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
    public String getPeriodic(){
         return periodic;
     }
    public ArrayList<ArrayList<String>> getStops() {
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
        getStops().forEach(i-> s.add( new Stop(i.get(0),
                    i.get(1),
                    i.get(2)
            ))
        );
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
