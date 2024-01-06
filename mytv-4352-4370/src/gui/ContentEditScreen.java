package gui;
import javax.swing.*;
import java.util.ArrayList;
import java.awt.*;
import api.*;
import necessities.Errors;

import static java.lang.Integer.parseInt;
import static java.lang.String.valueOf;
import static necessities.Functions.currentUser;


public class ContentEditScreen extends JDialog{

    private Content newContent;
    public ContentEditScreen(Content content, Frame parent) {
        super(parent, "Object Input Dialog", true);
        setTitle(content.getTitle());
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new GridLayout(10,1));
        initialize(content);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public void initialize(Content content) {
        ContentEditScreen frame = ContentEditScreen.this;
        frame.setBounds(0,0,Toolkit.getDefaultToolkit().getScreenSize().width,Toolkit.getDefaultToolkit().getScreenSize().height);
        JPanel title = new JPanel();
        JLabel titleLabel = new JLabel("New Title:");
        JTextField titleField = new JTextField(content.getTitle());
        titleField.setColumns(25);
        title.add(titleLabel);
        title.add(titleField);
        JPanel desc = new JPanel();
        JLabel descLabel = new JLabel("New Description:");
        JTextField descField = new JTextField(content.getDesc());
        descField.setColumns(60);
        desc.add(descLabel);
        desc.add(descField);
        JPanel over18 = new JPanel(new BorderLayout());
        JLabel over18Label = new JLabel("Over 18:");
        JCheckBox over18Box = new JCheckBox();
        over18Box.setSelected(content.isOver18());
        over18.add(over18Label,BorderLayout.WEST);
        over18.add(over18Box,BorderLayout.CENTER);
        JPanel protagonists = new JPanel();
        JLabel protLabel = new JLabel("New Protagonists: (Separate by commas)");
        StringBuilder sb = new StringBuilder();
        for (String item : content.getProtagonists()){
            sb.append(item);
            sb.append(",");
        }
        sb.setLength(sb.length() - 1);
        JTextField protField = new JTextField(sb.toString());
        protField.setColumns(45);
        protagonists.add(protLabel);
        protagonists.add(protField);
        JPanel category = new JPanel(new GridLayout(7,1));
        JLabel categoryLabel = new JLabel("New category:");
        category.add(categoryLabel);
        ButtonGroup catButtonGroup = new ButtonGroup();
        ArrayList<JCheckBox> categoryChecks = new ArrayList<>();
        String[] categories = {"Horror", "Sci-Fi", "Comedy", "Romance", "Drama"};
        for (String cat : categories) {
            JCheckBox categoryBox = new JCheckBox(cat);
            catButtonGroup.add(categoryBox);
            categoryChecks.add(categoryBox);
        }
        for (JCheckBox object : categoryChecks) {
            category.add(object);
        }
        for (JCheckBox checkbox : categoryChecks) {
            if (checkbox.getText().equals(content.getCategory())) {
                checkbox.setSelected(true);
            }
        }
        JPanel relatedContent = new JPanel();
        relatedContent.setLayout(new BoxLayout(relatedContent,BoxLayout.X_AXIS));
        StringBuilder sb2 = new StringBuilder();
        for (String element : content.getRelated()) {
            sb2.append(element);
            sb2.append(",");
        }
        if (!sb2.isEmpty()) sb2.setLength(sb2.length() - 1);
        JLabel label = new JLabel("New related content: (Separate by commas");
        JTextField related = new JTextField(sb2.toString());
        relatedContent.add(label);
        relatedContent.add(related);



        //
        add(title);
        add(desc);
        add(over18);
        add(protagonists);
        add(category);
        JTextField yearField = new JTextField();
        JTextField durationField = new JTextField();
        JTextField seasonField = new JTextField();
        JTextField episodeDurationField = new JTextField();
        episodeDurationField.setColumns(4);
        if (content instanceof Movie) {
            yearField.setText(valueOf(((Movie) content).getYear()));
            JPanel year = new JPanel();
            JLabel yearLabel = new JLabel("New year:");
            year.add(yearLabel);
            year.add(yearField);
            durationField.setText(valueOf(((Movie) content).getDuration()));
            JPanel duration = new JPanel();
            JLabel durationLabel = new JLabel("New duration:");
            duration.add(durationLabel);
            duration.add(durationField);
            add(year);
            add(duration);
        } else if (content instanceof Series) {
            StringBuilder ssn = new StringBuilder();
            int dur = 0;
            for (Season season : ((Series) content).getSeasonList()){
                ssn.append(season.getNumber());
                ssn.append(',');
                ssn.append(season.getYear());
                ssn.append('-');
                dur = season.getEpisodeLength();
            }
            ssn.deleteCharAt(ssn.length() - 1);
            JPanel season = new JPanel();
            JLabel seasonLabel = new JLabel("New seasons: (Format is number,year.To add multiple seasons,use - inbetween them.)");
            seasonField.setText(ssn.toString());
            season.add(seasonLabel);
            season.add(seasonField);
            JPanel episodeDuration = new JPanel();
            JLabel episodeDurationLabel = new JLabel("New episode duration: (To edit this or the season one,you need to fill in both of them,else it won't work.)");
            episodeDurationField.setText(valueOf(dur));
            episodeDuration.add(episodeDurationLabel);
            episodeDuration.add(episodeDurationField);
            add(season);
            add(episodeDuration);
        }
        add(relatedContent);
        JButton editButton = new JButton("Edit");
        editButton.addActionListener(e -> {
            String titleText = titleField.getText();
            if (titleText.isEmpty()) titleText = content.getTitle();
            String descText = descField.getText();
            if (descText.isEmpty()) descText = content.getDesc();
            boolean over18Text = over18Box.isSelected();
            String protagonistText = protField.getText();
            String[] protagonistArray;
            if (protagonistText.isEmpty()) {
                protagonistArray = content.getProtagonists();
            } else {
                protagonistArray = protagonistText.split(",");
            }
            String categoryText = "";
            String yearText = "";
            String durationText = "";
            ArrayList<Season> seasonList = new ArrayList<>();
            for (JCheckBox object : categoryChecks) {
                if (object.isSelected()) {
                    categoryText = object.getText();
                }
            }
            if (content instanceof Movie) {
                yearText = yearField.getText();
                if (yearText.isEmpty()) yearText = valueOf(((Movie) content).getYear());
                durationText = durationField.getText();
                if (durationText.isEmpty()) durationText = valueOf(((Movie) content).getDuration());
            } else if (content instanceof Series) {
                String seasonText = seasonField.getText();
                String episodeDurationText = episodeDurationField.getText();
                if (seasonText.isEmpty() || episodeDurationText.isEmpty()) {
                    seasonList = ((Series) content).getSeasonList();
                } else {
                    String[] seasonArray = seasonText.split("-");
                    Season tempSeason = null;
                    String[] tempString;
                    for (String s : seasonArray) {
                        tempString = s.split(",");
                        tempSeason = new Season(parseInt(tempString[0]), parseInt(tempString[1]), parseInt(episodeDurationText));
                        seasonList.add(tempSeason);
                    }
                }
            }
            if (content instanceof Movie) {
                newContent = new Movie(titleText, descText, over18Text, categoryText, protagonistArray,parseInt(yearText),parseInt(durationText));
            } else {
                newContent = new Series(titleText, descText, over18Text, categoryText, protagonistArray,seasonList);
            }
            for (String string : related.getText().split(",")) {
                newContent.addRelated(string);
            }
            try {
                ((Admin) currentUser).contentEdit(content, newContent);
                JOptionPane.showMessageDialog(new JFrame(),"Content edited successfully.");
            } catch(Errors.customException err) {
                JOptionPane.showMessageDialog(new JFrame(),err);
            }
            setVisible(false);
            dispose();

        });
        frame.add(editButton);

    }
}
