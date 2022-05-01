package com.example.traintable_java;

public class Stop {
     private String city;
     private String arrivalTime;
     private String departmentTime;

    public Stop(String city, String arrivalTime, String departmentTime) {
        this.city = city;
        this.arrivalTime = arrivalTime;
        this.departmentTime = departmentTime;
    }

    public String getCity() {
        return city;
    }


    public String getArrivalTime() {
        return arrivalTime;
    }



    public String getDepartmentTime() {
        return departmentTime;
    }


    @Override
    public String toString() {
        return "Stop{" +
                "city='" + city + '\'' +
                ", arrivalTime='" + arrivalTime + '\'' +
                ", departmentTime='" + departmentTime + '\'' +
                '}';
    }
}
