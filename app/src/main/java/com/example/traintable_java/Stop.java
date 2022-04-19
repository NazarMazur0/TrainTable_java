package com.example.traintable_java;

public class Stop {
    private String city;
    private String time;

    public Stop(String city, String time) {
        this.city = city;
        this.time = time;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "Stop{" +
                "city='" + city + '\'' +
                ", time='" + time + '\'' +
                '}';
    }
}
