package gui;
import api.Lists;
import api.Movie;
import api.Season;
import api.Series;
import java.util.ArrayList;

public class Admin extends User {
    public Admin (String username,String password) {
        super(username,password,true);
    }

    private void addMovie (String title, String desc, boolean over18, int year, int duration, String category, String protagonists,String[] related,ArrayList<Movie> movieList){
        ArrayList<String> returnedList = new ArrayList<>();
        movie = new Movie(title,desc,over18,category,protagonists,year,duration);
        for (Movie item : movieList) {
            for (String relatedMovie : related) {
                if (item.getTitle().equals(relatedMovie)) {
                    returnedList.add(item.getTitle());
                }
            }
        }
        movie.setRelated(returnedList);
        // should research pass by value,this probably doesnt work
        movieList.add(movie);

    }
    private void addSeries(String title, String desc, boolean over18, String category, String protagonists, ArrayList<Season> seasonlist,String[] related,ArrayList<Series> seriesList){
        series = new Series(title,desc,over18,category, protagonists, seasonlist);
        for (Series item : seriesList) {
            for (String relatedSeries : related) {
                if (item.getTitle().equals(relatedSeries)) {
                    returnedList.add(item.getTitle());
                }
            }
        }
        series.setRelated(returnedList);
        //same as above
        seriesList.add(series);
    }
}

