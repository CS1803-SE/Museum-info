package com.example.museuminfo.Utils;


public class TimeCount {
    private long time;
    private static com.example.museuminfo.Utils.TimeCount timeCount;

    private TimeCount() {
    }

    public static com.example.museuminfo.Utils.TimeCount getInstance() {
        if (timeCount == null) {
            timeCount = new com.example.museuminfo.Utils.TimeCount();
        }
        return timeCount;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }
}
