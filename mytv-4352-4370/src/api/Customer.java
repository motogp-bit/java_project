package api;
import necessities.*;
import java.util.ArrayList;

public class Customer extends User {

    public ArrayList<Content> favoriteList;


    public Customer (String username,String password) {
        super(username,password,false);
        this.favoriteList = new ArrayList<>();
        Lists.userPasswords.put(username,password);
    }
    public void addToFavorite (Content content) {
        this.favoriteList.add(content);
    }
    public void deleteFromFavorite (Content content) {
        this.favoriteList.remove(content);
    }


    public void addRating(String desc, Integer rating, String title) throws Errors.customException {

        if (desc.length() > 500) throw new Errors.customException("Description is too long");
        for (Content content : Lists.contentList) {
            if (title.equals(content.getTitle())) {
                content.addRating(new Content.Rating(desc,rating,this.getUsername()));
            }
        }
    } 

}
