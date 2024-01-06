package gui;
import necessities.*;

import javax.swing.*;
import java.awt.*;

import static java.lang.String.valueOf;

public class RegisterUserScreen extends JFrame{

    public RegisterUserScreen() {
        setTitle("Register User");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new GridLayout(5,1));
        initialize();
        setLocationRelativeTo(null);
        setVisible(true);
    }
    public void initialize() {
        RegisterUserScreen frame = RegisterUserScreen.this;
        frame.setBounds(0,0,Toolkit.getDefaultToolkit().getScreenSize().width,Toolkit.getDefaultToolkit().getScreenSize().height/2);
        JPanel username = new JPanel();
        JLabel usernameLabel = new JLabel("Username");
        JTextField usernameField = new JTextField();
        usernameField.setColumns(20);
        username.add(usernameLabel);
        username.add(usernameField);
        JPanel password = new JPanel();
        JLabel passwordLabel = new JLabel("Password");
        JPasswordField passwordField = new JPasswordField();
        passwordField.setColumns(20);
        password.add(passwordLabel);
        password.add(passwordField);
        add(username);
        add(password);
        JPanel name = new JPanel();
        JPanel surname = new JPanel();
        JLabel nameLabel = new JLabel("Name");
        JLabel surnameLabel = new JLabel("Surname");
        JTextField nameField = new JTextField();
        nameField.setColumns(30);
        JTextField surnameField = new JTextField();
        surnameField.setColumns(30);
        name.add(nameLabel);
        name.add(nameField);
        surname.add(surnameLabel);
        surname.add(surnameField);
        add(name);
        add(surname);
        JButton registerButton = new JButton("Register User");
        registerButton.addActionListener(e-> {
            try {
                Functions.createCustomer(usernameField.getText(), valueOf(passwordField.getPassword()),nameField.getText(),surnameField.getText());
                JOptionPane.showMessageDialog(new JFrame(),"Register successful.");
                frame.dispose();
            } catch (Errors.customException err) {
                JOptionPane.showMessageDialog(new JFrame(),err);
            }
        });
        add(registerButton);
    }
}
