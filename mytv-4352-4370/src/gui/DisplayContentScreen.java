package gui;
import javax.swing.*;
import javax.swing.JOptionPane;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import api.*;

import static java.lang.Integer.parseInt;
import static necessities.Functions.currentUser;


import necessities.Errors;
import necessities.Lists;


public class DisplayContentScreen extends JFrame{
    public DisplayContentScreen frame;
    public DisplayContentScreen(Content content){
        frame = DisplayContentScreen.this;
        setTitle(content.getTitle());
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new GridLayout(18,1));
        initialize(content);
        setLocationRelativeTo(null);
        setVisible(true);
    }
    public String repeatString(int times) {
        char ch = ' ';
        return String.valueOf(ch).repeat(Math.max(0, times));
    }
    public void initialize(Content content) {
        this.setBounds(0,0,Toolkit.getDefaultToolkit().getScreenSize().width,Toolkit.getDefaultToolkit().getScreenSize().height);
        JLabel title = new JLabel("Title:" + repeatString(30 - "Title:".length()) + content.getTitle());
        JLabel desc = new JLabel("Description:" + repeatString(30 - "Description:".length()) + content.getDesc());
        JLabel over18 = new JLabel(content.isOver18() ? "18+" : "For all ages");
        JLabel category = new JLabel("Category:" + repeatString(30 - "Category:".length()) + content.getCategory());
        StringBuilder sb1 = new StringBuilder();
        sb1.append("Protagonists:");
        sb1.append(repeatString(30 - "Protagonists:".length()));
        for (String item : content.getProtagonists()) {
            sb1.append(item);
            sb1.append(',');
        }
        if (!sb1.isEmpty()) sb1.setLength(sb1.length() - 1);
        JLabel protagonists = new JLabel(sb1.toString());
        StringBuilder sb2 = new StringBuilder();
        for (String item : content.getRelated()){
            sb2.append(item);
            sb2.append(',');
        }
        if (!sb2.isEmpty()) sb2.setLength(sb2.length() - 1);
        JLabel related = new JLabel(sb2.toString());
        add(title);
        add(desc);
        add(over18);
        add(category);
        add(protagonists);
        add(related);
        if (content instanceof Movie) {
            JLabel year = new JLabel("Year:" + repeatString(30 - "Year:".length()) + ((Movie) content).getYear());
            JLabel duration = new JLabel("Duration:" + repeatString(30 - "Duration:".length()) + ((Movie) content).getDuration());
            add(year);
            add(duration);
        } else if (content instanceof Series) {
            StringBuilder sb3 = new StringBuilder();
            int length = 0;
            for (Season item : ((Series)content).getSeasonList()) {
                sb3.append("Season ");
                sb3.append(item.getNumber());
                sb3.append(':');
                sb3.append(item.getYear());
                sb3.append('\n');
                length = item.getEpisodeLength();
            }
            if (!sb3.isEmpty()) sb3.setLength(sb3.length() - 1);
            JLabel seasons = new JLabel(sb3.toString());
            add(seasons);
            JLabel episodeLength = new JLabel("Episode Length:" + repeatString(30 - "Episode Length:".length()) + length);
            add(episodeLength);
        }
        JLabel numOfRatings = new JLabel("Number of ratings:" + repeatString(30 - "Number of ratings:".length()) + content.getNumOfRatings());
        JLabel averageRating = new JLabel("Average Rating:" + repeatString(30 - "Average Rating:".length()) + content.getAverageRating());
        JPanel ratings = new JPanel();
        ratings.setLayout(new BoxLayout(ratings,BoxLayout.X_AXIS));
        JLabel tempLabel = new JLabel();
        content.updateRatings();
        for (Content.Rating rating : content.getRatings()) {
            StringBuilder sb4 = new StringBuilder();
            sb4.append("Username:");
            sb4.append(rating.getUsername());
            sb4.append('\n');
            sb4.append("Rating:");
            sb4.append(rating.getRating());
            sb4.append('\n');
            sb4.append(rating.getDescription());
            tempLabel.setText(sb4.toString());
            tempLabel.add(ratings);
        }
        add(numOfRatings);
        add(averageRating);
        add(ratings);
        if (currentUser.hasAdminPerms()) {
            JButton deleteButton = new JButton("Delete content");
            deleteButton.addActionListener(e -> {
                try {
                    for (User user: Lists.userList) {
                        if (!(user.hasAdminPerms())){
                            if (((Customer) user).favoriteList.contains(content)){
                                ((Customer) user).favoriteList.remove(content);
                                break;
                            }
                        }
                    ((Admin) currentUser).deleteContent(content);
                    }
                    setVisible(false);
                    frame.dispose();
                    JOptionPane.showMessageDialog(new JFrame(),"Content deleted successfully.");
                } catch(Errors.customException err) {
                    JOptionPane.showMessageDialog(new JFrame(),err);
                }
            });
            JButton editButton = new JButton("Edit content");
            editButton.addActionListener(e -> {
                SwingUtilities.invokeLater((() -> {
                    ContentEditScreen editFrame = new ContentEditScreen(content,frame);
                    final Content[] newContent = new Content[1];
                    editFrame.addWindowListener(new WindowAdapter() {
                        @Override
                        public void windowClosed(WindowEvent e) {
                            newContent[0] = editFrame.getCreatedContent();
                            super.windowClosed(e);
                            try {
                                ((Admin) currentUser).contentEdit(content, newContent[0]);
                            } catch(Errors.customException err) {
                                JOptionPane.showMessageDialog(new JFrame(),err);
                            }
                        }
                    });
                }));
            });
            add(deleteButton);
            add(editButton);
        } else {
            JTextField reviewField = new JTextField("Add a review");
            boolean c = false;
            String r = "";
            for (Content.Rating rating : content.getRatings()) {
                if (rating.getUsername().equals(currentUser.getUsername())){
                    r = rating.getDescription();
                    c = true;
                    break;
                }
            }
            final boolean check;
            //lamda expression needs final variable
            check = c;
            JButton deleteButton = new JButton();
            deleteButton.setVisible(false);
            JButton submitReview = new JButton("Add review");
            deleteButton.addActionListener(e-> {
                for (Content.Rating rating : content.getRatings()) {
                    if (rating.getUsername().equals(currentUser.getUsername())) {
                        content.deleteRating(rating);
                        content.updateRatings();
                        submitReview.setText("Add review");
                        break;
                    }
                }
                deleteButton.setVisible(false);
                JOptionPane.showMessageDialog(new JFrame(),"Review successfully deleted");
            });
            if (check) {
                submitReview.setText("Edit review");
                reviewField.setText(r);
                deleteButton.setVisible(true);
                deleteButton.setText("Delete review");

            }
            int[] clickedCheckBox = {0};
            submitReview.addActionListener(e-> {
                String review = reviewField.getText();
                if (clickedCheckBox[0] == 0) {
                    JOptionPane.showMessageDialog(new JFrame(), "Error:You need to add a rating for your review.");
                } else {

                    try {
                        if (check) {
                            for (Content.Rating rating1 : content.getRatings()) {
                                if (rating1.getUsername().equals(currentUser.getUsername())) {
                                    content.deleteRating(rating1);
                                    break;
                                }
                            }
                        }
                        ((Customer) currentUser).addRating(review, clickedCheckBox[0], content.getTitle());
                    } catch (Errors.customException exception) {
                        JOptionPane.showMessageDialog(new JFrame(), exception);
                    }
                    JOptionPane.showMessageDialog(new JFrame(), "Rating added successfully.");
                    content.updateRatings();
                    reviewField.setText(review);
                    deleteButton.setVisible(true);
                    deleteButton.setText("Delete review");
                    submitReview.setText("Edit Review");
                }
            });
                JPanel reviewPanel = new JPanel(new FlowLayout());
                String[] numbers = {"1", "2", "3", "4", "5"};
                ButtonGroup buttonGroup = new ButtonGroup();
                for (String number : numbers) {
                  JCheckBox numberBox = new JCheckBox(number);
                  numberBox.setName(number);
                  numberBox.addActionListener(e1 -> clickedCheckBox[0] =  parseInt(numberBox.getName()));
                  buttonGroup.add(numberBox);
                  reviewPanel.add(numberBox);
               }
            JButton favoriteAdd = new JButton("Add to favorites.");
            JButton favoriteDelete = new JButton("Remove from favorites");
            favoriteAdd.addActionListener(e -> {
                ((Customer) currentUser).addToFavorite(content);
                JOptionPane.showMessageDialog(new JFrame(),"Content successfully added to favorites.");
                favoriteAdd.setVisible(false);
                favoriteDelete.setVisible(true);
            });
            favoriteDelete.setText("Remove from favorites");
            favoriteDelete.addActionListener(e -> {
                ((Customer) currentUser).deleteFromFavorite(content);
                JOptionPane.showMessageDialog(new JFrame(),"Content successfully deleted from favorites.");
                favoriteDelete.setVisible(false);
                favoriteAdd.setVisible(true);
            });
            if (((Customer) currentUser).favoriteList.contains(content)) {
                favoriteAdd.setVisible(false);
            } else {
                favoriteDelete.setVisible(false);
            }


            add(reviewField);
            reviewPanel.setPreferredSize(new Dimension(20,40));
            add(reviewPanel);
            add(deleteButton);
            add(submitReview);
            add(favoriteAdd);
            add(favoriteDelete);
        }


    }
}
