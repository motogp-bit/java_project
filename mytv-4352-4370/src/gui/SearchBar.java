package gui;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

import api.Content;
import api.Movie;
import necessities.Errors;
import necessities.Lists;

import static java.lang.Integer.parseInt;

public class SearchBar extends JFrame{

public SearchBar () {
    this.setBounds(0,0,Toolkit.getDefaultToolkit().getScreenSize().width,Toolkit.getDefaultToolkit().getScreenSize().height/2);
    setTitle("Search Bar");
    setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    setLayout(new GridLayout(0, 2, 10, 10));
    initialize();
    setLocationRelativeTo(null);
    setVisible(true);
}
    private void initialize() {
    final ArrayList<String[]> contentParam = new ArrayList<>();
        JPanel titlePanel = new JPanel();
        JLabel title = new JLabel("Title");
        JTextField titleField = new JTextField();
        titleField.setColumns(25);
        titlePanel.add(title);
        titlePanel.add(titleField);

        JPanel typePanel = new JPanel();
        JLabel type = new JLabel("Type");
        JCheckBox movieCheck = new JCheckBox("Movie");
        JCheckBox seriesCheck = new JCheckBox("Series");
        movieCheck.setSelected(true);
        typePanel.add(type);
        typePanel.add(movieCheck);
        typePanel.add(seriesCheck);

        ArrayList<String> protagonistsList = new ArrayList<>();

        JPanel protagonistPanel = new JPanel();
        JLabel protagonistLabel = new JLabel("Protagonists");
        JLabel listProtagonists = new JLabel();
        JTextField protagonistField = new JTextField();
        protagonistField.setColumns(30);
        JButton addProtagonist = new JButton("Add");
        addProtagonist.addActionListener(e -> {
            String protagonist = protagonistField.getText();
            if (!protagonist.isEmpty()) {
                protagonistsList.add(protagonist);
                protagonistField.setText("");
                if (listProtagonists.getText().isEmpty()){
                    listProtagonists.setText(protagonist);
                } else {
                    listProtagonists.setText(listProtagonists.getText() + ',' + protagonist);
                }
            }
        });
        JButton resetProtagonists = new JButton("Reset Protagonists");
        resetProtagonists.addActionListener(e -> {
            protagonistsList.clear();
            listProtagonists.setText("");
        });
        protagonistPanel.add(protagonistLabel);
        protagonistPanel.add(protagonistField);
        protagonistPanel.add(addProtagonist);
        protagonistPanel.add(resetProtagonists);
        protagonistPanel.add(listProtagonists);

        JPanel ageRatingPanel = new JPanel();
        ageRatingPanel.setLayout(new BoxLayout(ageRatingPanel,BoxLayout.X_AXIS));
        JLabel ageRating = new JLabel("Age Rating");
        JCheckBox ageRatingCheck = new JCheckBox("18+");
        ageRatingPanel.add(ageRating);
        ageRatingPanel.add(ageRatingCheck);

        JLabel categoryLabel = new JLabel("Category");
        JPanel categoryPanel = new JPanel(new GridLayout(6, 1));
        categoryPanel.add(categoryLabel);
        ArrayList<JCheckBox> categoryChecks = new ArrayList<>();
        String[] categories = {"Horror", "Sci-Fi", "Comedy", "Romance", "Drama"};
        for (String category : categories) {
            JCheckBox categoryCheck = new JCheckBox(category);
            categoryChecks.add(categoryCheck);
        }
        for (JCheckBox c: categoryChecks) {
            categoryPanel.add(c);
        }

        JPanel minRatingPanel = new JPanel();
        JLabel minRating = new JLabel("Minimum rating");
        JTextField minRatingField = new JTextField(1);
        minRatingPanel.add(minRating);
        minRatingPanel.add(minRatingField);

        JButton searchButton = new JButton("Search");
        searchButton.addActionListener(e -> {
            String title1 = titleField.getText();
            String type1 = "";
            boolean ch = false;
            if (movieCheck.isSelected()) {
              type1 = "Movie";
                ch = true;
            }
            if (seriesCheck.isSelected()) {
                type1 = "Series";
                if (ch) {type1 = "Both";}
            }
            boolean over18 = ageRatingCheck.isSelected();

            ArrayList<String> protagonistsReturn = new ArrayList<>(protagonistsList);

            ArrayList<String> categoriesReturn = new ArrayList<>();
            for (JCheckBox categoryCheck : categoryChecks) {
                if (categoryCheck.isSelected()) {
                    categoriesReturn.add(categoryCheck.getText());
                }
            }
            int minRating1;
            try {
                minRating1 = parseInt(minRatingField.getText());
            } catch(NumberFormatException err) {
                minRating1 = 0;
            }

            // Call searchContent method with the parameters
            try {
                ArrayList<String[]> tempArray = necessities.Functions.searchContent(title1, type1,  protagonistsReturn, over18, categoriesReturn, minRating1);
                contentParam.addAll(tempArray);
                SwingUtilities.invokeLater(() -> {
                    ContentMatches Screen = new ContentMatches(contentParam);
                });
            } catch (Errors.customException err) {
                JOptionPane.showMessageDialog(new JFrame(),err);
                ArrayList<String[]> allContent = new ArrayList<>();
                String[] tempString;
                for (Content content : Lists.contentList) {
                    tempString = new String[2];
                    tempString[0] = content.getTitle();
                    tempString[1] = content instanceof Movie ? "Movie" : "Series";
                    allContent.add(tempString);
                }
                final ArrayList<String[]> tempAllContent = allContent;
                SwingUtilities.invokeLater(() -> {
                    ContentMatches Screen = new ContentMatches(tempAllContent);
                });
            }
            this.setVisible(false);
            this.dispose();
        });

        this.add(titlePanel);
        this.add(typePanel);
        this.add(protagonistPanel);
        this.add(ageRatingPanel);
        this.add(categoryPanel);
        this.add(minRatingPanel);
        this.add(searchButton);
    }


}

