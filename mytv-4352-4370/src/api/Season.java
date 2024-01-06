package api;
import java.util.ArrayList;
public class Season {
    private int episodeLength;
    private int number;
    private int year;

    public Season(int number,int year,int episodeLength) {
        this.number = number;
        this.year = year;
        this.episodeLength = episodeLength;
    }
    public int getNumber() {
       return this.number;
    }
    public int getYear() {return this.year;}
    public int getEpisodeLength() {return this.episodeLength;}
}
