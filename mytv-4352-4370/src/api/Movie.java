package api;

import java.util.ArrayList;

public class Movie extends Content{
    private int year;
    private int duration;
    public Movie(String title,String desc,boolean over18,String category,String protagonists,int year,int duration) {
        super(title,desc,over18,category,protagonists);
        this.year = year;
        this.duration = duration;
    }


}
