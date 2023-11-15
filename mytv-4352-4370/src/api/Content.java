package api;

import java.util.ArrayList;

public class Content {

    private String title;
    private String desc;
    private boolean over18;
    private String category;
    private String protagonists;

    private ArrayList<String> related;
    public Content(String title,String desc,boolean over18,String category,String protagonists) {
        this.title = title;
        this.desc = desc;
        this.over18 = over18;
        this.category = category;
        this.protagonists = protagonists;
    }

    public String getTitle() {
        return this.title;
    }
    public String getDesc() {
        return this.desc;
    }
    public boolean isOver18() {
        return this.over18;
    }
    public String getCategory() {
        return this.category;
    }
    public String getProtagonists() {
        return this.protagonists;
    }
    public ArrayList<String> getRelated() {
        return this.related;
    }
    public void setRelated(ArrayList<String> relatedList) {
        this.related = relatedList;
    }
}
