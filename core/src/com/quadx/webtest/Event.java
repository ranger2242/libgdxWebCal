package com.quadx.webtest;

/**
 * Created by Chris Cavazos on 8/27/2018.
 */
public class Event {
    int day;
    int month;
    int year;
    int time;
    String title;
    String text;

    public Event(String date, String tit, String det) {
        String[] d = date.split("-");
        month = Integer.parseInt(d[0]);
        day = Integer.parseInt(d[1]);
        year = Integer.parseInt(d[2]);
        title=tit;
        text=det;

    }
}
