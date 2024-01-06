package gui;
import necessities.Functions;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import static necessities.Functions.currentUser;


public class AppScreen extends JFrame{


    public AppScreen() {
        AppScreen frame = AppScreen.this;
        frame.setBounds(0,0,Toolkit.getDefaultToolkit().getScreenSize().width,Toolkit.getDefaultToolkit().getScreenSize().height/2);
        JLabel label= new JLabel("App Page");
        setLayout(new FlowLayout());
        setLocationRelativeTo(null);
        add(label);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                Functions.logoutUser();
                frame.dispose();
                SwingUtilities.invokeLater(LoginScreen::new);
            }
        });
        JButton searchContentButton = new JButton("Search Content");
        searchContentButton.addActionListener(e-> SwingUtilities.invokeLater(SearchBar::new));
        this.add(searchContentButton);
        if (currentUser.hasAdminPerms()) {
            JButton registerUserButton = new JButton("Register user");
            registerUserButton.addActionListener(e-> SwingUtilities.invokeLater(RegisterUserScreen::new));
            this.add(registerUserButton);
            JButton addNewContentButton = new JButton("Add new content");
            addNewContentButton.addActionListener(e-> SwingUtilities.invokeLater(AddNewContentScreen::new));
            this.add(addNewContentButton);
        }
        JButton logoutButton = new JButton("Logout");
        logoutButton.addActionListener(e-> {
            Functions.logoutUser();
            frame.dispose();
            SwingUtilities.invokeLater(LoginScreen::new);
        });
        add(logoutButton);
        setVisible(true);
    }
}