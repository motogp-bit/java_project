package gui;

import api.Season;
import necessities.Errors;
import necessities.Functions;
import api.Admin;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.util.ArrayList;

import static java.lang.Integer.parseInt;
import static necessities.Functions.checkNulls;

public class AddNewContentScreen extends JFrame {
    public AddNewContentScreen() {
        setTitle("Add new content");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new GridLayout(11,1));
        initialize();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public void initialize() {
        AddNewContentScreen frame = AddNewContentScreen.this;
        frame.setBounds(0,0,Toolkit.getDefaultToolkit().getScreenSize().width,Toolkit.getDefaultToolkit().getScreenSize().height);
        JPanel titlePanel = new JPanel(new BorderLayout());
        JLabel title = new JLabel("Title");
        JTextField titleField = new JTextField();
        titlePanel.add(title,BorderLayout.WEST);
        titlePanel.add(titleField,BorderLayout.CENTER);

        JPanel descriptionPanel = new JPanel(new BorderLayout());
        JLabel description = new JLabel("Description");
        JTextField descriptionField = new JTextField();
        descriptionPanel.add(description,BorderLayout.NORTH);
        descriptionPanel.add(descriptionField,BorderLayout.CENTER);

        JPanel typePanel = new JPanel(new BorderLayout());
        JLabel type = new JLabel("Type");
        ButtonGroup buttonGroup1 = new ButtonGroup();
        JCheckBox movieCheck = new JCheckBox("Movie");
        movieCheck.setSelected(true);
        JCheckBox seriesCheck = new JCheckBox("Series");
        buttonGroup1.add(movieCheck);
        buttonGroup1.add(seriesCheck);

        JPanel moviePanel = new JPanel();
        moviePanel.setLayout(new GridLayout(2,1));

        JPanel seriesPanel = new JPanel();
        seriesPanel.setLayout(new BorderLayout());

        moviePanel.setVisible(true);
        seriesPanel.setVisible(false);


        JPanel yearPanel = new JPanel();
        yearPanel.setLayout(new BorderLayout());
        JLabel yearLabel = new JLabel("Year");
        JTextField yearField = new JTextField();
        yearPanel.add(yearLabel,BorderLayout.WEST);
        yearPanel.add(yearField,BorderLayout.CENTER);

        JPanel durationPanel = new JPanel();
        durationPanel.setLayout(new BorderLayout());
        JLabel durationLabel = new JLabel("Duration");
        JTextField durationField = new JTextField();
        durationPanel.add(durationLabel,BorderLayout.WEST);
        durationPanel.add(durationField,BorderLayout.CENTER);

        moviePanel.add(yearPanel);
        moviePanel.add(durationPanel);
        movieCheck.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                moviePanel.setVisible(true);
                seriesPanel.setVisible(false);
            }
        });
        JPanel seasonPanel = new JPanel();
        seasonPanel.setLayout(new BorderLayout());
        JLabel seasonLabel = new JLabel("Seasons: (Format is number,year.To add multiple seasons,use - inbetween them.)");
        JTextField seasonField = new JTextField();
        seasonPanel.add(seasonLabel,BorderLayout.WEST);
        seasonPanel.add(seasonField,BorderLayout.CENTER);
        JPanel episodeDurationPanel = new JPanel();
        episodeDurationPanel.setLayout(new BorderLayout());
        JLabel episodeDurationLabel = new JLabel("Episode duration");
        JTextField episodeDurationField = new JTextField();
        episodeDurationField.setColumns(4);
        episodeDurationPanel.add(episodeDurationLabel,BorderLayout.WEST);
        episodeDurationPanel.add(episodeDurationField,BorderLayout.CENTER);
        seriesPanel.add(seasonPanel,BorderLayout.CENTER);
        seriesPanel.add(episodeDurationPanel,BorderLayout.SOUTH);

        seriesCheck.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED){
                moviePanel.setVisible(false);
                seriesPanel.setVisible(true);
            }
        });

        typePanel.add(type,BorderLayout.NORTH);
        typePanel.add(movieCheck,BorderLayout.WEST);
        typePanel.add(seriesCheck,BorderLayout.CENTER);


        ArrayList<String> protagonistsList = new ArrayList<>();

        JPanel protagonistPanel = new JPanel(new BorderLayout());
        JLabel protagonistLabel = new JLabel("Protagonists");
        JLabel listProtagonists = new JLabel();
        JTextField protagonistField = new JTextField();
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
        protagonistPanel.add(protagonistLabel,BorderLayout.EAST);
        protagonistPanel.add(protagonistField,BorderLayout.CENTER);
        protagonistPanel.add(addProtagonist,BorderLayout.NORTH);
        protagonistPanel.add(resetProtagonists,BorderLayout.SOUTH);
        protagonistPanel.add(listProtagonists,BorderLayout.WEST);

        JPanel ageRatingPanel = new JPanel();
        ageRatingPanel.setLayout(new BorderLayout());
        JLabel ageRating = new JLabel("Age Rating");
        JCheckBox ageRatingCheck = new JCheckBox("18+");
        ageRatingPanel.add(ageRating,BorderLayout.NORTH);
        ageRatingPanel.add(ageRatingCheck,BorderLayout.CENTER);

        JLabel categoryLabel = new JLabel("Category");
        JPanel categoryPanel = new JPanel(new GridLayout(7, 1));
        categoryPanel.add(categoryLabel);
        ButtonGroup buttonGroup2 = new ButtonGroup();
        ArrayList<JCheckBox> categoryChecks = new ArrayList<>();
        String[] categories = {"Horror", "Sci-Fi", "Comedy", "Romance", "Drama"};
        for (String category : categories) {
            JCheckBox categoryCheck = new JCheckBox(category);
            categoryChecks.add(categoryCheck);
        }
        for (JCheckBox c : categoryChecks) {
            buttonGroup2.add(c);
            categoryPanel.add(c);
        }
        for (JCheckBox c : categoryChecks) {
            if (c.getText().equals("Drama")) {
                c.setSelected(true);
            }
        }
        JPanel relatedContent = new JPanel();
        relatedContent.setLayout(new BorderLayout());
        JLabel label = new JLabel("Related content: (Separate by commas");
        JTextField related = new JTextField();
        relatedContent.add(label,BorderLayout.WEST);
        relatedContent.add(related,BorderLayout.CENTER);

        JButton addContentButton = new JButton("Add content");

        addContentButton.addActionListener(e->{
            String ctext = "";
            for (JCheckBox c : categoryChecks) {
                if (c.isSelected()) {
                    ctext = c.getText();
                    break;
                }
            }
            String[] protArray = protagonistsList.toArray(new String[0]);
            String[] relatedArray = related.getText().split(",");
            ArrayList<Season> seasonList = new ArrayList<>();


            if (movieCheck.isSelected()) {
                try {
                    checkNulls("Movie",title.getText(),descriptionField.getText(),protArray,yearField.getText(),durationField.getText(),seasonList);
                    ((Admin) Functions.currentUser).addContent(titleField.getText(),descriptionField.getText(),ageRatingCheck.isSelected(),ctext,protArray,relatedArray,parseInt(yearField.getText()),parseInt(durationField.getText()),seasonList,"Movie");
                    JOptionPane.showMessageDialog(new JFrame(),"Content added successfully.");
                    frame.dispose();
                } catch (Errors.customException err){
                    JOptionPane.showMessageDialog(new JFrame(),err);
                }
            } else if (seriesCheck.isSelected()) {
                try {
                    for (String item : seasonField.getText().split("-")) {
                        String[] tempItem = item.split(",");
                        seasonList.add(new Season(parseInt(tempItem[0]),parseInt(tempItem[1]),parseInt(episodeDurationField.getText())));
                    }
                    checkNulls("Series",title.getText(),descriptionField.getText(),protArray,yearField.getText(),durationField.getText(),seasonList);
                    ((Admin) Functions.currentUser).addContent(titleField.getText(),descriptionField.getText(),ageRatingCheck.isSelected(),ctext,protArray,relatedArray,parseInt(yearField.getText()),parseInt(durationField.getText()),seasonList,"Series");
                    JOptionPane.showMessageDialog(new JFrame(),"Content added successfully.");
                    frame.dispose();
                } catch (Errors.customException err){
                    JOptionPane.showMessageDialog(new JFrame(),err);
                }
            }
        });


        this.add(titlePanel);
        this.add(typePanel);
        this.add(descriptionPanel);
        this.add(protagonistPanel);
        this.add(ageRatingPanel);
        this.add(categoryPanel);
        this.add(relatedContent);
        this.add(moviePanel);
        this.add(seriesPanel);
        this.add(addContentButton);
    }
}
