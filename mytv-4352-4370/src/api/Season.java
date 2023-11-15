package api;
import java.util.ArrayList;
public class Season {
    public class Episode {
        private int duration;

        public Episode(int duration) {
            this.duration = duration;
        }

        public int getDuration() {
            return this.duration;
        }
    }
    private int number;
    private int year;

    private ArrayList<Episode> episodelist;

    public Season(int number,int year,ArrayList<Episode> episodelist) {
        this.number = number;
        this.year = year;
        this.episodelist = episodelist;
    }
    public int getNumber() {
       return this.number;
    }
    public int getYear() {
        return this.year;
    }
    public ArrayList<Episode> getEpisodelist() {
        return this.episodelist;
    }
}
