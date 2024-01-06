import api.*;
import necessities.*;
import gui.*;

import javax.swing.*;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.io.File;
import java.util.ArrayList;
import static java.lang.Integer.parseInt;

/**
 * Το πρόγραμμά σας πρέπει να έχει μόνο μία main, η οποία πρέπει να είναι η παρακάτω.
 * <p>
 * <p>
 * ************* ΜΗ ΣΒΗΣΕΤΕ ΑΥΤΗ ΤΗΝ ΚΛΑΣΗ ************
 */
public class Main {
    //ENDS ON LOGOUT
    public static void main(String[] args) {
        //Files are preinitialized inside the project,initializing them through the program is tedious
        File Customers,Admins,Favorites,Ratings,Movies,Series;
        File Related = null;
        Scanner customers = null,admins = null,favorites = null,ratings = null,movies = null,series = null,related = null;
        String file,currentDirectory,fullPath;
            try {
                currentDirectory = System.getProperty("user.dir");
                file = "customers.txt";
                fullPath = currentDirectory + File.separator + file;
                Customers = new File(fullPath);
                customers = new Scanner(Customers);

                file = "admins.txt";
                fullPath = currentDirectory + File.separator + file;
                Admins = new File(fullPath);
                admins = new Scanner(Admins);

                file = "Favorites.txt";
                fullPath = currentDirectory + File.separator + file;
                Favorites = new File(fullPath);
                favorites = new Scanner(Favorites);

                file = "Ratings.txt";
                fullPath = currentDirectory + File.separator + file;
                Ratings = new File(fullPath);
                ratings = new Scanner(Ratings);

                file = "Movies.txt";
                fullPath = currentDirectory + File.separator + file;
                Movies = new File(fullPath);
                movies = new Scanner(Movies);

                file = "Series.txt";
                fullPath = currentDirectory + File.separator + file;
                Series = new File(fullPath);
                series = new Scanner(Series);

                file = "Related.txt";
                fullPath = currentDirectory + File.separator + file;
                Related = new File(fullPath);
                if (Related.exists()) related = new Scanner(Related);

                Lists lists = new Lists();
            } catch (FileNotFoundException e) {
                System.out.println("Something went wrong.");
            }
        while (true) {
            assert admins != null;
            if (!admins.hasNextLine()) break;
            String data = admins.nextLine();
            String[] temp = data.split(":");
            Lists.userPasswords.put(temp[0],temp[1]);
            Lists.userList.add(new Admin(temp[0],temp[1]));
        }
        admins.close();
        while (customers.hasNextLine()) {
            String data = customers.nextLine();
            String[] temp = data.split(":");
            Lists.userPasswords.put(temp[0],temp[1]);
            Lists.userList.add(new Customer(temp[0], temp[1],temp[2],temp[3]));
        }
        customers.close();
        while(true) {
            assert movies != null;
            if (!movies.hasNextLine()) break;
            String data = movies.nextLine();
            String[] temp = data.split(":");

            Lists.contentList.add(new Movie(temp[0],temp[1],temp[2].equals("Yes"),temp[3],temp[4].split(","),parseInt(temp[5]),parseInt(temp[6])));
        }
        movies.close();
        while(true) {
            assert series != null;
            if (!series.hasNextLine()) break;
            String data = series.nextLine();
            String[] temp = data.split(":");
            ArrayList<Season> tempArr = new ArrayList<>();
            for(String item : temp[5].split("-")){
                tempArr.add(new Season(parseInt(item.split(",")[0]),parseInt(item.split(",")[1]), parseInt(temp[6])));
            }
            Lists.contentList.add(new Series(temp[0],temp[1],temp[2].equals("Yes"),temp[3],temp[4].split(","),
                    tempArr));
        }
        series.close();
        //FIX SERIES,CONTENT CONSTRUCTORS + THIS
        while(favorites.hasNextLine()) {
            String data = favorites.nextLine();
            String[] temp = data.split(":");
            for (User user:Lists.userList) {
                if (temp[0].equals(user.getUsername())) {
                    for (Content content :Lists.contentList) {
                        if (content.getTitle().equals(temp[1])){
                            ((Customer) user).addToFavorite(content);
                            break;
                        }
                    }
                    break;
                }
            }
        }
        favorites.close();
        while(ratings.hasNextLine()){
            String data = ratings.nextLine();
            String[] temp = data.split(":");
            for (Content content:Lists.contentList) {
                if (temp[1].equals(content.getTitle())) {
                    content.addRating(new api.Content.Rating(temp[2],parseInt(temp[3]),temp[0]));
                    content.updateRatings();
                    break;
                }
        }
    }
        if (Related.exists()) {
            assert related != null;
            while (true) {
                if (!related.hasNextLine()) break;
                String data = related.nextLine();
                String[] temp1 = data.split(":");
                for (Content content:Lists.contentList) {
                    if (content.getTitle().equals(temp1[0])){
                        String[] temp = temp1[1].split(",");
                        for (String element : temp) {
                            content.addRelated(element);
                        }
                        break;
                    }
                }
            }
            related.close();
        }
        SwingUtilities.invokeLater(LoginScreen::new);
}
}