package api;

import java.util.ArrayList;

public class Series extends Content {
    ArrayList<Season> seasonList;
    public Series (String title, String desc, boolean over18, String category, String protagonists, ArrayList<Season> seasonlist) {
        super(title,desc,over18,category,protagonists);
        this.seasonList = seasonlist;
    }

}
