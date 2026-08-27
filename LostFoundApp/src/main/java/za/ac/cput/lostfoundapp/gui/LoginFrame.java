package za.ac.cput.lostfoundapp.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import za.ac.cput.lostfoundapp.dao.*;
import za.ac.cput.lostfoundapp.domain.*;

public class LoginFrame extends JFrame implements ActionListener {

    // button
    private JButton btnLogin;

    // textfield
    private JTextField txtEmail;
    private JPasswordField txtPassword;

    // object
    private UserDAO userDB;

    public LoginFrame() {

        // object
        userDB = new UserDAO();

        setTitle("FindMyItem - Login");
        setSize(450, 450);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel p = new JPanel(new GridBagLayout());
        p.setBackground(Color.WHITE);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;

        JLabel l = new JLabel("CPUT Lost & Found Login", SwingConstants.CENTER);
        l.setFont(new Font("SansSerif", Font.BOLD, 20));
        p.add(l, gbc);

        gbc.gridy++;

        JLabel sub = new JLabel(
                "FindMyItem - Bellville Campus",
                SwingConstants.CENTER
        );
        sub.setForeground(Color.GRAY);
        p.add(sub, gbc);

        gbc.gridy++;
        gbc.gridwidth = 1;
        gbc.gridx = 0;

        p.add(new JLabel("CPUT Email:"), gbc);

        gbc.gridx = 1;

        txtEmail = new JTextField(18);
        p.add(txtEmail, gbc);

        gbc.gridx = 0;
        gbc.gridy++;

        p.add(new JLabel("Password:"), gbc);

        gbc.gridx = 1;

        txtPassword = new JPasswordField(18);
        p.add(txtPassword, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 2;

        btnLogin = new JButton("Login with CPUT Email + OTP");
        btnLogin.setBackground(new Color(37, 99, 235));
        btnLogin.setForeground(Color.WHITE);
        btnLogin.setFocusPainted(false);
        btnLogin.setFont(new Font("SansSerif", Font.BOLD, 12));

        p.add(btnLogin, gbc);

        gbc.gridy++;

        JButton btnSignUp = new JButton("Sign Up");
        btnSignUp.setBackground(Color.WHITE);
        btnSignUp.setForeground(new Color(37, 99, 235));
        btnSignUp.setFocusPainted(false);
        btnSignUp.setFont(new Font("SansSerif", Font.BOLD, 12));

        p.add(btnSignUp, gbc);

        gbc.gridy++;

        JLabel note = new JLabel(
                "Demo: Click login to enter main app",
                SwingConstants.CENTER
        );
        note.setFont(new Font("SansSerif", Font.ITALIC, 11));
        note.setForeground(Color.GRAY);

        p.add(note, gbc);

        btnLogin.addActionListener(this);

        btnSignUp.addActionListener(e -> {
            new SignUpFrame().setVisible(true);
            dispose();
        });

        add(p);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnLogin) {

            String email = txtEmail.getText();
            char[] passwordChars = txtPassword.getPassword();
            String password = new String(passwordChars);
            User loggedInUser;

            if (email.isEmpty() || passwordChars.length == 0) {
                JOptionPane.showMessageDialog(this,
                        "Please fill in all fields.",
                        "Missing Information",
                        JOptionPane.WARNING_MESSAGE);
                return;
            } else if (userDB.emailCheck(email) == false) {
                JOptionPane.showMessageDialog(
                        this,
                        "This email does not exist. Please Register.",
                        "Invalid Email",
                        JOptionPane.WARNING_MESSAGE
                );
                return;
            } else if (userDB.loginCheck(email, password) == false) {
                JOptionPane.showMessageDialog(this,
                        "Email exists but password doesn't match",
                        "Invalid Password",
                        JOptionPane.WARNING_MESSAGE);
                return;
            } else{
                ArrayList<User> users = userDB.selectUser(email);  
                loggedInUser = users.get(0); // saving selected users information to you in other panels
            }

            new MainFrame(loggedInUser).setVisible(true);
            dispose();

        }
    }
}
