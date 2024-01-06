package api;
import necessities.*;
import java.util.ArrayList;

public class Admin extends User {
    public Admin (String username,String password) {super(username,password,true);
    }

    public void addContent (String title, String desc, Boolean over18, String category, String[] protagonists,String[] related,int year, int duration,ArrayList<Season> seasonList,String type) throws Errors.customException{
        try {
            this.testValidFields(type, title, desc, over18, category, protagonists, year, duration, seasonList);
        } catch(Errors.customException e) {
            throw new Errors.customException(e.getMessage());
        }
            ArrayList<String> returnedList = new ArrayList<>();
            for (Content content : Lists.contentList) {
                for (String relatedContent : related) {
                    if (content.getTitle().equals(relatedContent)) {
                        returnedList.add(content.getTitle());
                    }
                }

            //The second if is unnecessary,only added for extension purposes (if,say,someone wanted to add another type of content).
            if (type.equals("Movie")) {
                Movie object = new Movie(title,desc,over18,category,protagonists,year,duration);
                //This forces the following 2 lines to be inside the block,else compiler throws error because it's not sure object will be initialized.
                for (String item : returnedList) {
                    object.addRelated(item);
                }
                Lists.contentList.add(object);
            } else if (type.equals("Series")) {
                Series object = new Series(title,desc,over18,category,protagonists, seasonList);
                //same here
                for (String item : returnedList) {
                    object.addRelated(item);
                }
                Lists.contentList.add(object);
            }
        }
    }
    //WIP
    public void contentEdit(Content content, Content newContent) throws Errors.customException {
        if (!(Lists.contentList.contains(content))) throw new Errors.customException("This content does not exist.");
        boolean check = false;
        for (Content item : Lists.contentList) {
            if (Functions.contentEquals(item,content)) {
                if (item instanceof Series) {
                    ((Series) item).replaceExistingFieldsSeries((Series) newContent);
                } else if (content instanceof Movie){
                    ((Movie) item).replaceExistingFieldsMovie((Movie) newContent);
                }
                check = true;
                break;
            }
        }
        if (!check) throw new Errors.customException("This content does not exist.");
    }

    public void deleteContent (Content content) throws Errors.customException  {
        //dummy initialization to make compiler happy,line won't be reached anyway if it remains uninitialized
        int index = 0;
        boolean check = false;
        for (Content item : Lists.contentList) {
            if (item.equals(content)) {
                index = Lists.contentList.indexOf(item);
                check = true;
                break;
            }
        }
        if (!check) throw new Errors.customException("This content does not exist.");
        Lists.contentList.remove(index);
        for (User user: Lists.userList) {
            if (user instanceof Customer) {
                for (Content content1 : ((Customer) user).favoriteList) {
                    if (Functions.contentEquals(content1,content)) {
                        ((Customer) user).favoriteList.remove(content1);
                        break;
                    }
                }
            }
        }
    }
    public void setRelated(Content content, String[] titles) {
        for (String title : titles) {
            content.addRelated(title);
        }
    }

}

