package necessities;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.stream.Stream;
import java.io.FileWriter;

import api.*;


import static necessities.Lists.contentList;
import static necessities.Lists.userList;

public final class Functions {
    public static User currentUser;
    public static void checkNulls (String type,String title,String description,String[] protArray,String year,String duration,ArrayList<Season> seasonList) throws Errors.customException{
        if (title.isEmpty()) throw new Errors.customException("Empty title");
        if (description.isEmpty()) throw new Errors.customException("Empty description");
        if (protArray.length == 0) throw new Errors.customException("No protagonists");
        if (type.equals("Movie")) {
            if (year.isEmpty()) throw new Errors.customException("Empty year");
            if (duration.isEmpty()) throw new Errors.customException("No duration");
        } else if (type.equals("Series")) {
            if (seasonList.isEmpty()) throw new Errors.customException("No seasons");
        }
    }
    public static void createCustomer(String username,String password) throws Errors.customException {
        if (username.isEmpty() || password.isEmpty()) throw new Errors.customException("You must complete all fields.");
        if (necessities.Lists.userPasswords.containsKey(username)) throw new Errors.customException("Username already in use.Pick a new username.");
        necessities.Lists.userPasswords.put(username,password);
        Customer customer = new Customer (username,password);
        necessities.Lists.userList.add(customer);
    }
    public static void loginUser(String username,String password) throws Errors.customException {
        if (!Lists.userPasswords.containsKey(username)) throw new Errors.customException("This username isn't a valid username.");
        if (!(necessities.Lists.userPasswords.get(username)).equals(password)) throw new Errors.customException("Wrong password.");
        for (User user : Lists.userList) {
            if (user.getUsername().equals(username)) {
                if (!(user.getLoginStatus())) {
                    user.setLoginStatus(true);
                    currentUser = user;
                    break;
                } else {
                    throw new Errors.customException("User is already logged in.");
                }
            }
        }
    }
    public static void logoutUser () {
        currentUser.setLoginStatus(false);
        currentUser = null;
    }

    public static boolean contentEquals(Content content1,Content content2) {
        if ((content1 instanceof Movie && !(content2 instanceof Movie)) || (content1 instanceof Series && !(content2 instanceof Series))) return false;
        if (!(content1.getTitle().equals(content2.getTitle()))) return false;
        if (!(content1.isOver18() == content2.isOver18())) return false;
        if (!(content1.getCategory().equals(content2.getCategory()))) return false;
        if (!(content1.getDesc().equals(content2.getDesc()))) return false;
        return content1.getRelated().equals(content2.getRelated());
    }

    public static ArrayList<String[]> searchContent(String title, String type, ArrayList<String> protagonists, Boolean over18, ArrayList<String> categories, int rating) throws Errors.customException{
        ArrayList<String[]> tempList = new ArrayList<>();
        String[] tempString;
        for (Content content : contentList) {
            if (!(title.isEmpty())){
                if (!(content.getTitle().equals(title))) continue;
            }
            if (!(type.isEmpty())){
                if ((type.equals("Movie") && content instanceof Series) || (type.equals("Series") && content instanceof Movie)) continue;
            }
            boolean c;
            if (!(protagonists.isEmpty())) {
                c = true;
                for (String protagonist : protagonists) {
                    Stream<String> stream = Stream.of(content.getProtagonists());
                    c = stream.anyMatch(str->str.equals(protagonist));
                    if (!c) break;
                }
                if (!c) continue;
            }
            if (!(categories.isEmpty())) {
                boolean check = false;
                for (String cat : categories) {
                    if (cat.equals(content.getCategory())) {
                        check = true;
                        break;
                    }
                }
                if (!check) continue;
            }
            if (content.getAverageRating() < rating) continue;
            if (over18 != null) {
                if (content.isOver18() != over18) continue;
            }
            tempString = new String[2];
            tempString[0] = content.getTitle();
            if (content instanceof Movie) {
                tempString[1] = "Movie";
            } else {
                tempString[1] = "Series";
            }
            tempList.add(tempString);
        }
        if (!(tempList.isEmpty())){
            return tempList;
        } else {
            throw new Errors.customException("No matches found,printing all content.");
        }
    }
    public static Content displayContentGUI(String title,String type) {
        for (Content content : contentList) {
            if ((type.equals("Movie") && content instanceof Movie) || type.equals("Series") && content instanceof Series){
                if (content.getTitle().equals(title)) {
                    return content;
                }
            }
        }
        return new Content();
    }
    public static void storeData() throws IOException {
        File customers, Favorites, Movies, Ratings,Related,customersd,Favoritesd,Moviesd,Ratingsd,Relatedd;
        String currentDirectory = System.getProperty("user.dir");
        customersd = new File(currentDirectory + File.separator + "customers.txt");
        customersd.delete();
        Favoritesd = new File(currentDirectory + File.separator + "Favorites.txt");
        Favoritesd.delete();
        Moviesd = new File(currentDirectory + File.separator + "Movies.txt");
        Moviesd.delete();
        Ratingsd = new File(currentDirectory + File.separator + "Ratings.txt");
        Ratingsd.delete();
        Relatedd = new File(currentDirectory + File.separator + "Related.txt");
        if (Relatedd.exists()) Relatedd.delete();
        Related = new File(currentDirectory + File.separator + "Related.txt");
        Favorites = new File(currentDirectory + File.separator + "Favorites.txt");
        customers = new File(currentDirectory + File.separator + "customers.txt");
        Movies = new File(currentDirectory + File.separator + "Movies.txt");
        Ratings = new File(currentDirectory + File.separator + "Ratings.txt");
        try {
            customers.createNewFile();
            Favorites.createNewFile();
            Movies.createNewFile();
            Ratings.createNewFile();
            Related.createNewFile();
        } catch (IOException ignored){}
        FileWriter myWriter1 = new FileWriter(currentDirectory + File.separator + "customers.txt");
        for (User object : userList) {
            if (object instanceof Customer) {
                myWriter1.write(object.getUsername() + ':' + object.getPassword() + '\n');
            }
        }
        myWriter1.close();
        FileWriter myWriter2 = new FileWriter(currentDirectory + File.separator + "Favorites.txt");
        for (User object : userList) {
            if (object instanceof Customer) {
                for (Content content : ((Customer) object).favoriteList) {
                    myWriter2.write(object.getUsername() + ':' + content.getTitle() + '\n');
                }
            }
        }
        myWriter2.close();
        FileWriter myWriter3 = new FileWriter(currentDirectory + File.separator + "Movies.txt");
        FileWriter myWriter4 = new FileWriter(currentDirectory + File.separator + "Series.txt");
        FileWriter myWriter5 = new FileWriter(currentDirectory + File.separator + "Ratings.txt");
        FileWriter myWriter6 = new FileWriter(currentDirectory + File.separator + "Related.txt");
        for (Content content : contentList) {
            StringBuilder sb = new StringBuilder();
            for (String object : content.getProtagonists()) {
                sb.append(object);
                sb.append(',');
            }
            sb.setLength(sb.length() - 1);
            if (content instanceof Movie) {
                myWriter3.write(content.getTitle() + ':' + content.getDesc() + ':' +  (content.isOver18() ? "Yes" : "No") + ':' + content.getCategory() + ':' + sb + ':' + ((Movie) content).getYear() + ':' + ((Movie)content).getDuration() + '\n');
            } else if (content instanceof Series) {
                StringBuilder sb1 = new StringBuilder();
                int length = 0;
                for (Season season : (((Series) content).getSeasonList())){
                    sb1.append(season.getNumber());
                    sb1.append(',');
                    sb1.append(season.getYear());
                    sb1.append('-');
                    length = season.getEpisodeLength();
                }
                sb1.deleteCharAt(sb1.length() - 1);
                myWriter4.write(content.getTitle() + ':' + content.getDesc() + ':' + (content.isOver18() ? "Yes" : "No") + ':' + content.getCategory() + ':' + sb + ':' + sb1 + ':' + length + '\n');
            }
            for (Content.Rating rating :content.getRatings()) {
                myWriter5.write(rating.getUsername() + ':' + content.getTitle() + ':' + rating.getDescription() + ':' + rating.getRating() + '\n');
            }
            if (!(content.getRelated().isEmpty())) {
                StringBuilder temp = new StringBuilder();
                temp.append(content.getTitle());
                temp.append(':');
                for (String related : content.getRelated()) {
                    temp.append(related);
                    temp.append(',');
                }
                sb.deleteCharAt(sb.length() - 1);
                sb.append('\n');
                myWriter6.write(temp.toString());
            }
        }
        myWriter3.close();
        myWriter4.close();
        myWriter5.close();
        myWriter6.close();
    }

    }
