package gui;

public class User {
    private String username;
    private String password;
    private boolean adminPerms;

    public User () {
    }
    public User (String username, String password, boolean adminPerms) {
        this.username = username;
        this.password = password;
        this.adminPerms = adminPerms;
    }
}
