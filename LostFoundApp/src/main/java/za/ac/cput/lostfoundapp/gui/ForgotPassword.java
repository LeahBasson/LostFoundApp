package za.ac.cput.lostfoundapp.gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.*;
import javax.swing.*;
import za.ac.cput.lostfoundapp.dao.*;

public class ForgotPassword extends JFrame implements ActionListener {

    // textfield
    private JTextField txtStudStaffNum;
    private JPasswordField txtNewPwd;
    private JPasswordField txtConfirmPwd;

    // button
    private JButton btnReset;
    private JButton btnBack;

    // object 
    private UserDAO userDB;

    public ForgotPassword() {

        userDB = new UserDAO();

        setTitle("FindMyItem - Reset password");
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
                "Create new password",
                SwingConstants.CENTER
        );

        title.setFont(new Font("SansSerif", Font.BOLD, 20));
        p.add(title, gbc);

        gbc.gridy++;
        gbc.gridwidth = 1;
        gbc.gridx = 0;

        p.add(new JLabel("Student/Staff Number:"), gbc);

        gbc.gridx = 1;

        txtStudStaffNum = new JTextField(18);
        p.add(txtStudStaffNum, gbc);

        gbc.gridx = 0;
        gbc.gridy++;

        p.add(new JLabel("New password:"), gbc);

        gbc.gridx = 1;

        txtNewPwd = new JPasswordField(18);
        p.add(txtNewPwd, gbc);

        gbc.gridx = 0;
        gbc.gridy++;

        p.add(new JLabel("Confirm password:"), gbc);

        gbc.gridx = 1;

        txtConfirmPwd = new JPasswordField(18);
        p.add(txtConfirmPwd, gbc);

        gbc.gridx = 0;
        gbc.gridy++;

        gbc.gridwidth = 2;

        btnReset = new JButton("Reset password");

        btnReset.setBackground(new Color(37, 99, 235));
        btnReset.setForeground(Color.WHITE);
        btnReset.setFocusPainted(false);
        btnReset.setFont(new Font("SansSerif", Font.BOLD, 12));

        p.add(btnReset, gbc);
        gbc.gridy++;

        btnBack = new JButton("Back to Login");

        btnBack.setBackground(Color.WHITE);
        btnBack.setForeground(new Color(37, 99, 235));
        btnBack.setFocusPainted(false);

        p.add(btnBack, gbc);

        add(p);

        btnReset.addActionListener(this);
        btnBack.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnReset) {
            String studStaffNum = txtStudStaffNum.getText().trim();
            char[] passwordChars = txtNewPwd.getPassword();
            String newPwd = new String(passwordChars).trim();
            char[] confirmPwdChars = txtConfirmPwd.getPassword();
            String confirmPwd = new String(confirmPwdChars).trim();

            if (studStaffNum.isEmpty() || newPwd.isEmpty() || confirmPwd.isEmpty()) {
                JOptionPane.showMessageDialog(this,
                        "Please fill in all fields.",
                        "Missing Information",
                        JOptionPane.WARNING_MESSAGE);
            } else if (!userDB.checkUser(studStaffNum)) {
                JOptionPane.showMessageDialog(
                        this,
                        "This student or staff number does not exist. Please Register.",
                        "Invalid student or staff number",
                        JOptionPane.WARNING_MESSAGE
                );
            } else if (!newPwd.equals(confirmPwd)) {
                JOptionPane.showMessageDialog(this,
                        "Passwords do not match.",
                        "Password Error",
                        JOptionPane.WARNING_MESSAGE);
            } else if (userDB.isSamePassword(studStaffNum, newPwd)) {
                JOptionPane.showMessageDialog(this,
                        "Your new password cannot be the same as your old password.",
                        "Password Error",
                        JOptionPane.WARNING_MESSAGE);
            } else {

                userDB.updatePassword(studStaffNum, newPwd);

                JOptionPane.showMessageDialog(this,
                        "Your password has been successfully updated!",
                        "Success",
                        JOptionPane.INFORMATION_MESSAGE);

            }

        }

        if (e.getSource() == btnBack) {
            new LoginFrame().setVisible(true);
            dispose();
        }
    }
}// end of class
