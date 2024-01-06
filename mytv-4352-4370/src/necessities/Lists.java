package necessities;
import api.User;
import api.Content;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.Arrays;
public final class Lists {

    public static ArrayList<Content> contentList;

    public static ArrayList<String> categoryList;

    public static HashMap<String,String> userPasswords;

    public static ArrayList<User> userList;
    public Lists() {
        contentList = new ArrayList<>();
        //for some reason it doesnt work a lot of the time,implementations usually redeclare this as String[]
        categoryList = new ArrayList<>(Arrays.asList("Horror","Drama","SciFi","Comedy","Action"));
        userPasswords = new HashMap<>();
        userList = new ArrayList<>();
        }
}
