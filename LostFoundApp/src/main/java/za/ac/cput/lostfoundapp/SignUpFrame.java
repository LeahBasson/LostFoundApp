package za.ac.cput.lostfoundapp;

import javax.swing.*;
import java.awt.*;

public class SignUpFrame extends JFrame {

    public SignUpFrame() {

        setTitle("FindMyItem - Sign Up");
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

        JLabel title = new JLabel(
                "Create FindMyItem Account",
                SwingConstants.CENTER
        );
        title.setFont(new Font("SansSerif", Font.BOLD, 20));
        p.add(title, gbc);

        gbc.gridy++;
        gbc.gridwidth = 1;
        gbc.gridx = 0;

        p.add(new JLabel("Full Name:"), gbc);

        gbc.gridx = 1;

        JTextField txtName = new JTextField(18);
        p.add(txtName, gbc);

        gbc.gridx = 0;
        gbc.gridy++;

        p.add(new JLabel("CPUT Email:"), gbc);

        gbc.gridx = 1;

        JTextField txtEmail = new JTextField(18);
        p.add(txtEmail, gbc);

        gbc.gridx = 0;
        gbc.gridy++;

        p.add(new JLabel("Password:"), gbc);

        gbc.gridx = 1;

        JPasswordField txtPassword = new JPasswordField(18);
        p.add(txtPassword, gbc);

        gbc.gridx = 0;
        gbc.gridy++;

        p.add(new JLabel("Confirm Password:"), gbc);

        gbc.gridx = 1;

        JPasswordField txtConfirmPassword = new JPasswordField(18);
        p.add(txtConfirmPassword, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 2;

        JButton btnSignUp = new JButton("Create Account");
        btnSignUp.setBackground(new Color(37, 99, 235));
        btnSignUp.setForeground(Color.WHITE);
        btnSignUp.setFocusPainted(false);
        btnSignUp.setFont(new Font("SansSerif", Font.BOLD, 12));

        p.add(btnSignUp, gbc);

        gbc.gridy++;

        JButton btnBack = new JButton("Back to Login");
        btnBack.setBackground(Color.WHITE);
        btnBack.setForeground(new Color(37, 99, 235));
        btnBack.setFocusPainted(false);

        p.add(btnBack, gbc);

        btnSignUp.addActionListener(e -> {

            String name = txtName.getText();
            String email = txtEmail.getText();

            String password = new String(txtPassword.getPassword());
            String confirmPassword
                    = new String(txtConfirmPassword.getPassword());

            if (name.isEmpty() || email.isEmpty()
                    || password.isEmpty()
                    || confirmPassword.isEmpty()) {

                JOptionPane.showMessageDialog(
                        this,
                        "Please complete all fields.",
                        "Missing Information",
                        JOptionPane.WARNING_MESSAGE
                );

            } else if (!email.endsWith("@mycput.ac.za")) {

                JOptionPane.showMessageDialog(
                        this,
                        "Please use a valid CPUT email address.",
                        "Invalid Email",
                        JOptionPane.WARNING_MESSAGE
                );

            } else if (!password.equals(confirmPassword)) {

                JOptionPane.showMessageDialog(
                        this,
                        "Passwords do not match.",
                        "Password Error",
                        JOptionPane.WARNING_MESSAGE
                );

            } else {

                JOptionPane.showMessageDialog(
                        this,
                        "Account created successfully!"
                );

                new LoginFrame().setVisible(true);
                dispose();
            }
        });

        btnBack.addActionListener(e -> {
            new LoginFrame().setVisible(true);
            dispose();
        });

        add(p);
    }
}
