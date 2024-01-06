package api;
import java.util.ArrayList;
import necessities.*;

public class User {

    private String username;
    private String password;
    private boolean adminPerms;

    private boolean loggedIn = false;
    public User (String username, String password, boolean adminPerms) {
        this.username = username;
        this.password = password;
        this.adminPerms = adminPerms;
    }

    public boolean getLoginStatus() {return this.loggedIn;}
    public void setLoginStatus (boolean status) {
        this.loggedIn = status;
    }
    public String getUsername() {return this.username;}

    public String getPassword(){return this.password;}

    public boolean hasAdminPerms() {return this.adminPerms;}
    //Tests if
    public void testValidFields(String type, String title, String desc, String[] protagonists, int year, int duration, ArrayList<Season> seasonList) throws Errors.customException{
        if (title == null) throw new Errors.customException("No title");
        if (desc == null) throw new Errors.customException("No description");
        if (protagonists == null) throw new Errors.customException("No protagonists");
        if (type.equals("Movie")) {
            if (year < 0) throw new Errors.customException("Invalid year");
            if (duration <= 0) throw new Errors.customException("Invalid duration");
        } else if (type.equals("Series")){
            if (seasonList == null) throw new Errors.customException("No seasons");
        }
    }
}
