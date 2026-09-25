package za.ac.cput.lostfoundapp.gui;

import javax.swing.*;
import java.awt.*;
import za.ac.cput.lostfoundapp.dao.UserDAO;
import za.ac.cput.lostfoundapp.domain.User;

public class SignUpFrame extends JFrame {

    public SignUpFrame() {

        setTitle("FindMyItem - Sign Up");
        setSize(500, 650);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel p = new JPanel(new GridBagLayout());
        p.setBackground(Color.WHITE);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 10, 8, 10);
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

        p.add(new JLabel("Student/Staff Number:"), gbc);

        gbc.gridx = 1;

        JTextField txtStudentStaffNumber = new JTextField(18);
        p.add(txtStudentStaffNumber, gbc);

        gbc.gridx = 0;
        gbc.gridy++;

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

        p.add(new JLabel("Campus:"), gbc);

        gbc.gridx = 1;

        String[] campuses = {
            "Bellville",
            "District Six",
            "Mowbray",
            "Wellington"
        };

        JComboBox<String> cmbCampus = new JComboBox<>(campuses);
        p.add(cmbCampus, gbc);

        gbc.gridx = 0;
        gbc.gridy++;

        p.add(new JLabel("Contact Number:"), gbc);

        gbc.gridx = 1;

        JTextField txtContactNumber = new JTextField(18);
        p.add(txtContactNumber, gbc);

        gbc.gridx = 0;
        gbc.gridy++;

        p.add(new JLabel("Role:"), gbc);

        gbc.gridx = 1;

        String[] roles = {
            "Student",
            "Staff",
            "Lecturer"
        };

        JComboBox<String> cmbRole = new JComboBox<>(roles);
        p.add(cmbRole, gbc);

        gbc.gridx = 0;
        gbc.gridy++;

        p.add(new JLabel("Language:"), gbc);

        gbc.gridx = 1;

        String[] languages = {
            "English",
            "Afrikaans",
            "isiXhosa"
        };

        JComboBox<String> cmbLanguage = new JComboBox<>(languages);
        p.add(cmbLanguage, gbc);

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

        JPasswordField txtConfirmPassword =
                new JPasswordField(18);

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

            String studentStaffNumber =
                    txtStudentStaffNumber.getText().trim();

            String name =
                    txtName.getText().trim();

            String email =
                    txtEmail.getText().trim();

            String campus =
                    cmbCampus.getSelectedItem().toString();

            String contactNumber =
                    txtContactNumber.getText().trim();

            String role =
                    cmbRole.getSelectedItem().toString();

            String language =
                    cmbLanguage.getSelectedItem().toString();

            String password =
                    new String(txtPassword.getPassword());

            String confirmPassword =
                    new String(txtConfirmPassword.getPassword());

            if (studentStaffNumber.isEmpty()
                    || name.isEmpty()
                    || email.isEmpty()
                    || contactNumber.isEmpty()
                    || password.isEmpty()
                    || confirmPassword.isEmpty()) {

                JOptionPane.showMessageDialog(
                        this,
                        "Please complete all fields.",
                        "Missing Information",
                        JOptionPane.WARNING_MESSAGE
                );

            } else if (studentStaffNumber.length() != 8) {

                JOptionPane.showMessageDialog(
                        this,
                        "Student/Staff number must be 8 digits.",
                        "Invalid Number",
                        JOptionPane.WARNING_MESSAGE
                );

            } else if (!email.endsWith("@mycput.ac.za")) {

                JOptionPane.showMessageDialog(
                        this,
                        "Please use a valid CPUT email address.",
                        "Invalid Email",
                        JOptionPane.WARNING_MESSAGE
                );

            } else if (contactNumber.length() != 10) {

                JOptionPane.showMessageDialog(
                        this,
                        "Contact number must be 10 digits.",
                        "Invalid Contact Number",
                        JOptionPane.WARNING_MESSAGE
                );

            } else if (password.length() > 8) {

                JOptionPane.showMessageDialog(
                        this,
                        "Password must be 8 characters or less.",
                        "Invalid Password",
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

                UserDAO userDAO = new UserDAO();

                if (userDAO.emailCheck(email)) {

                    JOptionPane.showMessageDialog(
                            this,
                            "This email is already registered.",
                            "Email Exists",
                            JOptionPane.WARNING_MESSAGE
                    );

                } else {

                    User user = new User(
                            0,
                            name,
                            contactNumber,
                            campus,
                            language,
                            email,
                            studentStaffNumber,
                            role);

                    userDAO.register(user);

                    JOptionPane.showMessageDialog(
                            this,
                            "Account created successfully!",
                            "Sign Up",
                            JOptionPane.INFORMATION_MESSAGE
                    );

                    new LoginFrame().setVisible(true);
                    dispose();
                }
            }
        });

        btnBack.addActionListener(e -> {

            new LoginFrame().setVisible(true);
            dispose();

        });

        add(p);
    }
}