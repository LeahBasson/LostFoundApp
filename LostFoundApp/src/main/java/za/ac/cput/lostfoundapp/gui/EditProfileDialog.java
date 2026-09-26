package za.ac.cput.lostfoundapp.gui;

import java.awt.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import za.ac.cput.lostfoundapp.domain.User;
import za.ac.cput.lostfoundapp.dao.UserDAO;

public class EditProfileDialog extends JDialog implements ActionListener{
    // object
    private User loggedInUser;
    private UserDAO userDB;

    //  buttons
    private JButton btnExit;
    private JButton btnUpdate;

    // textfields
    private JTextField txtPassword;
    private JTextField txtConfirmPassword;
    private JTextField txtStudNum;
    private JTextField txtContact;

    // combo box
    private JComboBox cboLanguage;
    
    public EditProfileDialog(User loggedInUser) {
        this.loggedInUser = loggedInUser;
        userDB = new UserDAO();

        setTitle("Edit Profile");
        setSize(500, 500);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(10, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel lblStudNum = new JLabel("Student number:");
        txtStudNum = new JTextField(loggedInUser.getStudent_staff_number());
        txtStudNum.setEditable(false);

        JLabel lblName = new JLabel("Full Name:");
        JTextField txtName = new JTextField(loggedInUser.getFull_name());
        txtName.setEditable(false);

        JLabel lblCampus = new JLabel("Campus:");
        JTextField txtCampus = new JTextField(loggedInUser.getCampus());
        txtCampus.setEditable(false);

        JLabel lblContact = new JLabel("Contact Number:");
        txtContact = new JTextField(loggedInUser.getContact_number());

        JLabel lblRole = new JLabel("Role");
        JTextField txtRole = new JTextField(loggedInUser.getRole());
        txtRole.setEditable(false);

        JLabel lblLanguage = new JLabel("Language:");
        cboLanguage = new JComboBox();

        cboLanguage.addItem("English");
        cboLanguage.addItem("Afrikaans");
        cboLanguage.addItem("isiXhosa");

        JLabel lblEmail = new JLabel("Email Address:");
        JTextField txtEmail = new JTextField(loggedInUser.getEmail());
        txtEmail.setEditable(false);

        JLabel lblPassword = new JLabel("Password:");
        txtPassword = new JTextField();

        JLabel lblConfirmPassword = new JLabel("Confirm password:");
        txtConfirmPassword = new JTextField();

        btnUpdate = new JButton("Update");
        btnExit = new JButton("Exit");

        panel.add(lblStudNum);
        panel.add(txtStudNum);

        panel.add(lblName);
        panel.add(txtName);

        panel.add(lblCampus);
        panel.add(txtCampus);

        panel.add(lblContact);
        panel.add(txtContact);

        panel.add(lblRole);
        panel.add(txtRole);

        panel.add(lblLanguage);
        panel.add(cboLanguage);

        panel.add(lblEmail);
        panel.add(txtEmail);

        panel.add(lblPassword);
        panel.add(txtPassword);

        panel.add(lblConfirmPassword);
        panel.add(txtConfirmPassword);

        panel.add(btnExit);
        panel.add(btnUpdate);

        btnExit.addActionListener(this);
        btnUpdate.addActionListener(this);

        add(panel);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnExit) {
            dispose();
        }

        if (e.getSource() == btnUpdate) {
            String contact = txtContact.getText().trim();
            String language = (String) cboLanguage.getSelectedItem();
            String password = txtPassword.getText().trim();
            String confirmPassword = txtConfirmPassword.getText().trim();
            String studNum = txtStudNum.getText();

            if (password.isEmpty() && confirmPassword.isEmpty()) {
                if (contact.isEmpty()) {
                    JOptionPane.showMessageDialog(this,
                            "Please fill in contact.",
                            "Missing Information",
                            JOptionPane.WARNING_MESSAGE);
                } else if (contact.equals(loggedInUser.getContact_number()) && language.equals(loggedInUser.getLanguage())) {
                    JOptionPane.showMessageDialog(this,
                            "Information already exists.",
                            "Information not changed",
                            JOptionPane.WARNING_MESSAGE);
                } else {
                    userDB.updateLanguageContact(studNum, contact, language);
                    loggedInUser.setContact_number(contact);
                    loggedInUser.setLanguage(language);
                    
                    JOptionPane.showMessageDialog(this, "Update was succuessful");
                }

            } else {
                if (password.length() < 8) {
                    JOptionPane.showMessageDialog(this,
                            "Password must be at least 8 characters long.",
                            "Password Too Short",
                            JOptionPane.WARNING_MESSAGE);
                } else if (password.length() > 8) {
                    JOptionPane.showMessageDialog(this,
                            "Password must be at least 8 characters long.",
                            "Password Too Long",
                            JOptionPane.WARNING_MESSAGE);
                } else if (password.equals(loggedInUser.getPassword_hash())) {
                    JOptionPane.showMessageDialog(this,
                            "Password already exists.",
                            "Password not changed",
                            JOptionPane.WARNING_MESSAGE);
                } else if (!confirmPassword.equals(password)) {
                    JOptionPane.showMessageDialog(this,
                            "Passwords do not match.Please try again.",
                            "Password Mismatch",
                            JOptionPane.WARNING_MESSAGE);
                } else {
                    userDB.updatePassword(studNum, password);
                    loggedInUser.setPassword_hash(password);
                    JOptionPane.showMessageDialog(this, "Update was succuessful");
                }

            }

        } // end of update button
    }
}
