package gui;
import necessities.Errors;
import necessities.Functions;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;

import static java.lang.String.valueOf;
import static javax.swing.JOptionPane.showMessageDialog;

public class LoginScreen extends JFrame{

    public LoginScreen() {
        LoginScreen frame = LoginScreen.this;
        setLayout(new FlowLayout());
        setTitle("Login Page.");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setBounds(0,0,Toolkit.getDefaultToolkit().getScreenSize().width,Toolkit.getDefaultToolkit().getScreenSize().height/2);
        JLabel username = new JLabel("Username");
        username.setBounds(100,10,60,20);
        add(username);
        JTextField usernameTextField = new JTextField();
        usernameTextField.setBounds(100,30,200,30);
        usernameTextField.setColumns(20);
        add(usernameTextField);
        JLabel password = new JLabel("Password");
        password.setBounds(100, 55, 60, 20);
        add(password);
        JPasswordField passwordTextField = new JPasswordField();
        passwordTextField.setBounds(100,75,200,30);
        passwordTextField.setColumns(20);
        add(passwordTextField);
        JButton loginButton = new JButton("Login");
        loginButton.setBounds(100, 120, 70, 20);
        loginButton.setBackground(Color.BLACK);
        loginButton.setForeground(Color.GREEN);
        loginButton.addActionListener(e -> {
                try {
                    Functions.loginUser(usernameTextField.getText(), valueOf(passwordTextField.getPassword()));
                    SwingUtilities.invokeLater(AppScreen::new);
                    frame.dispose();
                } catch (Errors.customException err) {
                    showMessageDialog(new JFrame(),err);
                }

            });
        add(loginButton);
        JButton registerButton = new JButton("Register");
        registerButton.setBounds(100,120,70,20);
        registerButton.setBackground(Color.BLACK);
        registerButton.setForeground(Color.CYAN);
        registerButton.addActionListener(e-> SwingUtilities.invokeLater(RegisterUserScreen::new));
        add(registerButton);
        JButton exitButton = new JButton("Exit");
        exitButton.addActionListener(e->{
            try {
                Functions.storeData();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
            System.exit(0);
        });
        add(exitButton);
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                try {
                    Functions.storeData();
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
                System.exit(0);
                super.windowClosing(e);
            }
        });
        this.setVisible(true);
    }
    //Exit and logout button are functionally the same
    }

