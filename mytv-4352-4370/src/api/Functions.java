package api;
import necessities.*;
public class Functions {
    public void createCustomer(String username,String password,String name,String surname) throws Errors.customException {
        if (username.isEmpty() || password.isEmpty() || name.isEmpty() || surname.isEmpty()) throw new Errors.customException("You must complete all fields.");
        if (necessities.Lists.userPasswords.containsKey(username)) throw new Errors.customException("Username already in use.Pick a new username.");
        Customer customer = new Customer (username,password,name,surname);
        necessities.Lists.userList.add(customer);
    }
    public static void loginUser(String username,String password) throws Errors.customException {
        if (!Lists.userPasswords.containsKey(username)) throw new Errors.customException("This username isn't a valid username.");
        if (!(necessities.Lists.userPasswords.get(username)).equals(password)) throw new Errors.customException("Wrong password.");
        for (User user : Lists.userList) {
            if (user.getUsername().equals(username)) {
                if (user.getLoginStatus()) {
                    user.setLoginStatus(true);
                } else {
                    throw new Errors.customException("User is already logged in.");
                }
            }
        }
        throw new Errors.customException("User not found.");
    }
    public void logoutUser (String username,String password) throws Errors.customException {
        for (User user : Lists.userList) {
            if (user.getUsername().equals(username)) {
                if (user.getLoginStatus()) {
                    user.setLoginStatus(false);
                } else {
                    throw new Errors.customException("User is already logged out.");
                }
            }
        }
        throw new Errors.customException("User not found.");
    }
}
