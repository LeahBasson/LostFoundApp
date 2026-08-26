package za.ac.cput.lostfoundapp.gui;

import java.awt.*;
import javax.swing.*;

public class EditProfileDialog extends JDialog {

    public EditProfileDialog() {

        setTitle("Edit Profile");
        setSize(500, 400);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(8, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel lblStudNum = new JLabel("Student number:");
        JTextField txtStudNum = new JTextField();

        JLabel lblName = new JLabel("Full Name:");
        JTextField txtName = new JTextField();

        JLabel lblContact = new JLabel("Contact Number:");
        JTextField txtContact = new JTextField();

        JLabel lblLanguage = new JLabel("Language:");
        JTextField txtLanguage = new JTextField();

        JLabel lblEmail = new JLabel("Email Address:");
        JTextField txtEmail = new JTextField();

        JButton btnUpdate = new JButton("Update");
        JButton btnExit = new JButton("Exit");

        panel.add(lblStudNum);
        panel.add(txtStudNum);

        panel.add(lblName);
        panel.add(txtName);

        panel.add(lblContact);
        panel.add(txtContact);

        panel.add(lblLanguage);
        panel.add(txtLanguage);

        panel.add(lblEmail);
        panel.add(txtEmail);

        panel.add(btnExit);
        panel.add(btnUpdate);

        add(panel);

        btnExit.addActionListener(e -> {
            dispose();
        });

        btnUpdate.addActionListener(e -> {

            dispose();
        });
    }
}
