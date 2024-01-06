package gui;
import necessities.*;
import javax.swing.*;
import java.util.ArrayList;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;


public class ContentMatches extends JFrame{
    public ContentMatches frame = ContentMatches.this;
    public ContentMatches(ArrayList<String[]> matchingContent){
        frame.setBounds(0,0,Toolkit.getDefaultToolkit().getScreenSize().width,Toolkit.getDefaultToolkit().getScreenSize().height/2);
        setTitle("Matches");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new GridLayout(matchingContent.size(), 2));
        initialize(matchingContent);
        setLocationRelativeTo(null);
        setVisible(true);
    }
    class ContentPanel extends JPanel {
        String info1,info2 = "";
        static String temp1,temp2;
        public ContentPanel() {
            setPreferredSize(new Dimension(10, 10));
            setBackground(Color.LIGHT_GRAY);

            addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    if (e.getClickCount() == 1) {
                        setBackground(Color.BLUE);
                    }
                    if (e.getClickCount() == 2) {
                        temp1 = info1;
                        temp2 = info2;

                        SwingUtilities.invokeLater(() -> {
                            DisplayContentScreen screen = new DisplayContentScreen(Functions.displayContentGUI(temp1,temp2));
                            frame.dispose();
                        });
                    }
                }
            });
        }
        public void setInfo(String s1,String s2) {
            this.info1 = s1;
            this.info2 = s2;
        }
    }
    public void initialize(ArrayList<String[]> matchingContent) {
        JLabel textLabel;
        ContentPanel contentPanel;
        for (String[] item : matchingContent) {
            contentPanel = new ContentPanel();
            textLabel = new JLabel();
            textLabel.setText(String.format("%s                    %s",item[0],item[1]));
            contentPanel.add(textLabel);
            contentPanel.setInfo(item[0],item[1]);
            add(contentPanel);
        }
        }
    }
