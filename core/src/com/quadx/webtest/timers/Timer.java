package com.quadx.webtest.timers;

import java.text.DecimalFormat;
import java.text.NumberFormat;

/**
 * Created by Chris Cavazos on 7/17/2016.
 */
class Timer {
    private final NumberFormat formatter = new DecimalFormat("#0.0000000");
    private long start=0;
    private long end=0;
    private String name="";
    public Timer(String n){
        name=n;
    }

    public Timer() {

    }

    public void start(){
        start=System.nanoTime();
    }
    public void end(){
        end=System.nanoTime();
    }
    private String getElapsed(){
        return formatter.format(getElapsedD());
    }
    private double getElapsedD(){
        return (end-start)/( 1000000000.0);
    }

    private String runtime(){

        return name+": "+getElapsed()+" sec";
    }


    public void print() {
        System.out.println(runtime());
    }
    public void printS() {
        System.out.println(getElapsed());
    }
}
