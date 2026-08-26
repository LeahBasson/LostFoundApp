package za.ac.cput.lostfoundapp.gui;

import javax.swing.*;
import java.awt.*;

public class LoginFrame extends JFrame {

    public LoginFrame() {

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

        JTextField email = new JTextField(18);
        email.setText("219065721@mycput.ac.za");
        p.add(email, gbc);

        
        gbc.gridx = 0;
        gbc.gridy++;

        p.add(new JLabel("Password:"), gbc);

        gbc.gridx = 1;

        JPasswordField password = new JPasswordField(18);
        p.add(password, gbc);

        
        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 2;

        JButton btnLogin = new JButton("Login with CPUT Email + OTP");
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

        
        btnLogin.addActionListener(e -> {
            new MainFrame().setVisible(true);
            dispose();
        });

        
        btnSignUp.addActionListener(e -> {
            new SignUpFrame().setVisible(true);
            dispose();
        });

        add(p);
    }
}
