package api;

import java.util.ArrayList;
import static necessities.Lists.categoryList;

public class Content {

    private String title;
    private String desc;
    private Boolean over18;
    private String category;
    private String[] protagonists;

    private float totalRating;
    private int numOfRatings;

    private ArrayList<String> related;
    public static class Rating {
        private String description;
        private int rating;
        private String username;
        public Rating(String description,int rating,String username) {
            this.description = description;
            this.rating = rating;
            this.username = username;
        }
        public String getDescription() {return this.description;}
        public int getRating() {return this.rating;}
        public String getUsername() {return this.username;}
    }
    private ArrayList<Rating> ratingsList;
    public Content(){}
    public Content(String title,String desc,Boolean over18,String category,String[] protagonists) {
        this.title = title;
        this.desc = desc;
        this.over18 = over18;
        this.category = category;
        this.protagonists = protagonists;
        this.totalRating = 0;
        this.ratingsList = new ArrayList<>();
        this.related = new ArrayList<>();
    }

    public String getTitle() {
        return this.title;
    }
    public String getDesc() {
        return this.desc;
    }
    public Boolean isOver18() {
        return this.over18;
    }
    public String getCategory() {
        return this.category;
    }
    public String[] getProtagonists() {
        return this.protagonists;
    }
    public ArrayList<String> getRelated() {
        return this.related;
    }
    public ArrayList<Rating> getRatings() {
        return this.ratingsList;
    }
    public void addRating(Rating rating) {
        this.ratingsList.add(rating);
    }

    public void deleteRating(Rating rating) {this.ratingsList.remove(rating);}
    public float getAverageRating() {
        if (this.numOfRatings > 0) {
            return Math.round(this.totalRating / this.numOfRatings);
        }
        return 0;
    }
    public void updateRatings() {
        int count = 0;
        int temp = 0;
        for (Rating item :this.ratingsList) {
            temp += item.rating;
            count+=1;
        }
        this.totalRating = temp;
        this.numOfRatings = count;
    }
    public int getNumOfRatings() {return this.numOfRatings;}
    public void addRelated(String title) {
        this.related.add(title);
    }

    public void replaceExistingFields (Content content) {
        if (content.getTitle() != null) { this.title = content.getTitle();}
        if (content.getProtagonists() != null) {this.protagonists = content.getProtagonists();}
        if (content.getDesc() != null) {this.desc = content.getDesc();}
        if (content.getCategory() != null && categoryList.contains(content.getCategory())) {this.category = content.getCategory();}
        if (content.getRelated() != null) {this.related = content.getRelated();}
    }
}
