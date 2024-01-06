package api;
import necessities.*;
import java.util.ArrayList;
import java.util.Arrays;

import static necessities.Lists.contentList;

public class Admin extends User {
    public Admin (String username,String password) {super(username,password,true);
    }

    public void addContent (String title, String desc, Boolean over18, String category, String[] protagonists,String[] related,int year, int duration,ArrayList<Season> seasonList,String type) throws Errors.customException{
        ArrayList<String> returnedList = new ArrayList<>(Arrays.asList(related));

            //The second if is unnecessary,only added for extension purposes (if,say,someone wanted to add another type of content).
            if (type.equals("Movie")) {
                Movie object = new Movie(title,desc,over18,category,protagonists,year,duration);
                //This forces the following 2 lines to be inside the block,else compiler throws error because it's not sure object will be initialized.
                for (String item : returnedList) {
                    object.addRelated(item);
                }
                contentList.add(object);
            } else if (type.equals("Series")) {
                Series object = new Series(title,desc,over18,category,protagonists, seasonList);
                //same here
                for (String item : returnedList) {
                    object.addRelated(item);
                }
                contentList.add(object);
            }
        }
    public void contentEdit(Content content, Content newContent) throws Errors.customException {
        if (!(contentList.contains(content))) throw new Errors.customException("This content does not exist.");
        boolean check = false;
        for (Content item : contentList) {
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
        contentList.remove(content);
        for (User user: Lists.userList) {
            if (!(user.hasAdminPerms())){
                    ((Customer) user).favoriteList.remove(content);
                    break;
                }
            }
    }

}

