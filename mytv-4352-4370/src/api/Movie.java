package api;

import java.util.ArrayList;

public class Movie extends Content{
    private int year;
    private int duration;
    public Movie(String title,String desc,Boolean over18,String category,String[] protagonists,int year,int duration) {
        super(title,desc,over18,category,protagonists);
        this.year = year;
        this.duration = duration;
    }
//    public void displayMovieContent (){
//        this.displayContent();
//        System.out.printf("Year %d",this.year);
//        System.out.printf("Duration %d",this.duration);
//        this.displayRelated();
//    }
    public int getYear() {
        return this.year;
    }
    public int getDuration() {
        return this.duration;
    }
    public void replaceExistingFieldsMovie(Movie movie) {
        this.replaceExistingFields(movie);
        if (movie.getYear() != 0) {this.year = movie.getYear();}
        if (movie.getDuration() != 0) {this.duration = movie.getDuration();}
    }
}
