package com.example.traintable_java;

public class Match {
    private String code;
    private String start;
    private String end;
    private String startTime;
    private String endTime;
    private String periodic;

    public String getPeriodic() {
        return periodic;
    }

    public String getCode() {
        return code;
    }

    public String getStart() {
        return start;
    }

    public String getEnd() {
        return end;
    }

    public String getStartTime() {
        return startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public Match(String code, String start, String end, String startTime, String endTime , String periodic) {
        this.code = code;
        this.start = start;
        this.end = end;
        this.startTime = startTime;
        this.endTime = endTime;
        this.periodic=periodic;
    }

    @Override
    public String toString() {
        return "Match{" +
                "number='" + code + '\'' +
                ", start='" + start + '\'' +
                ", end='" + end + '\'' +
                ", startTime='" + startTime + '\'' +
                ", endTime='" + endTime + '\'' +
                '}';
    }
}
