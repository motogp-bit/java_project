package api;
import java.util.ArrayList;
public class Lists {
    ArrayList<Movie> movieList;

    ArrayList<Series> seriesList;

    public void initializeLists () {
        this.movieList = new ArrayList<>();
        this.seriesList = new ArrayList<>();
    }
}
