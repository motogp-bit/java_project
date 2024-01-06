package api;

import java.util.ArrayList;

public class Series extends Content {
    private ArrayList<Season> seasonList;
    public Series (String title, String desc, Boolean over18, String category, String[] protagonists, ArrayList<Season> seasonList) {
        super(title,desc,over18,category,protagonists);
        this.seasonList = seasonList;
    }

    public ArrayList<Season> getSeasonList() {
        return this.seasonList;
    }

    public void replaceExistingFieldsSeries(Series series) {
        this.replaceExistingFields(series);
        if (series.getSeasonList() != null) {this.seasonList = series.getSeasonList();};
    }

}
