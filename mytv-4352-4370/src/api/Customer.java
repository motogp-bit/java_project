package api;
import necessities.*;
import java.util.ArrayList;

public class Customer extends User {

    public ArrayList<Content> favoriteList;

    private final String name;
    private final String surname;
    public Customer (String username,String password,String name,String surname) {
        super(username,password,false);
        this.favoriteList = new ArrayList<>();
        Lists.userPasswords.put(username,password);
        this.name = name;
        this.surname = surname;
    }
    public void addToFavorite (Content content) {
        this.favoriteList.add(content);
    }
    public void deleteFromFavorite (Content content) {
        this.favoriteList.remove(content);
    }

    public String getName() {return this.name;}
    public String getSurname(){return this.surname;}

    public void addRating(String desc, Integer rating, String title) throws Errors.customException {

        if (desc.length() > 500) throw new Errors.customException("Description is too long");
        for (Content content : Lists.contentList) {
            if (title.equals(content.getTitle())) {
                content.addRating(new Content.Rating(desc,rating,this.getUsername()));
            }
        }
    } 

}
