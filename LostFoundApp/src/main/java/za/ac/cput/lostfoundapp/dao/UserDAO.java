package za.ac.cput.lostfoundapp.dao;

import java.sql.*;
import java.util.*;
import javax.swing.*;
import za.ac.cput.lostfoundapp.connection.DBConnection;
import za.ac.cput.lostfoundapp.domain.*;

public class UserDAO {

    private Connection con;
    private PreparedStatement pstmt;

    public UserDAO() {
        try {
            this.con = DBConnection.derbyConnection();
        } catch (SQLException exception) {
            JOptionPane.showMessageDialog(null, exception.getMessage());
        }
    }

    public void register(User user) {
        try {
            pstmt = this.con.prepareStatement(
                    "INSERT INTO \"ADMINISTRATOR\".USERS "
                    + "(student_staff_number, email, full_name, campus, "
                    + "contact_number, role, password_hash, language) "
                    + "VALUES (?, ?, ?, ?, ?, ?, ?, ?)"
            );
            pstmt.setString(1, user.getStudent_staff_number());
            pstmt.setString(2, user.getEmail());
            pstmt.setString(3, user.getFull_name());
            pstmt.setString(4, user.getCampus());
            pstmt.setString(5, user.getContact_number());
            pstmt.setString(6, user.getRole());
            pstmt.setString(7, user.getPassword_hash());
            pstmt.setString(8, user.getLanguage());
            pstmt.executeUpdate();
        } catch (SQLException err) {
            System.out.println("ERROR: " + err);
        }
    }

    public boolean emailCheck(String email) {
        try {
            pstmt = this.con.prepareStatement(
                    "SELECT email FROM \"ADMINISTRATOR\".USERS WHERE email = ?"
            );
            pstmt.setString(1, email);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return true;
            }
        } catch (SQLException err) {
            System.out.println("ERROR: " + err);
        }
        return false;
    }

    public boolean loginCheck(String email, String password_hash) {
        try {
            pstmt = this.con.prepareStatement(
                    "SELECT * FROM \"ADMINISTRATOR\".USERS "
                    + "WHERE email = ? AND password_hash = ?"
            );
            pstmt.setString(1, email);
            pstmt.setString(2, password_hash);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return true;
            }
        } catch (SQLException err) {
            System.out.println("ERROR: " + err);
        }
        return false;
    }

    public ArrayList<User> selectUser(String emailAddress) {
        ArrayList<User> users = new ArrayList<>();
        try {
            pstmt = this.con.prepareStatement(
                    "SELECT user_id, full_name, contact_number, campus, "
                    + "language, email, student_staff_number, role, "
                    + "password_hash "
                    + "FROM \"ADMINISTRATOR\".USERS WHERE email = ?"
            );
            pstmt.setString(1, emailAddress);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                int user_id = rs.getInt("user_id");
                String full_name = rs.getString("full_name");
                String contact_number = rs.getString("contact_number");
                String campus = rs.getString("campus");
                String language = rs.getString("language");
                String email = rs.getString("email");
                String student_staff_number = rs.getString("student_staff_number");
                String role = rs.getString("role");
                String password_hash = rs.getString("password_hash");
                User user = new User(user_id, full_name, contact_number, campus, language,
                        email, student_staff_number, role, password_hash
                );
                users.add(user);
            }
        } catch (SQLException err) {
            System.out.println("ERROR: " + err);
        }
        return users;
    }

    public void updateLanguageContact(String student_staff_number, String contact, String language) {
        try {
            pstmt = this.con.prepareStatement("UPDATE \"ADMINISTRATOR\".USERS SET contact_number = ?, language = ?"
                    + " WHERE student_staff_number = ?");
            pstmt.setString(1, contact);
            pstmt.setString(2, language);
            pstmt.setString(3, student_staff_number);
            pstmt.executeUpdate();
        } catch (SQLException err) {
            System.out.println("ERROR: " + err);
        }
    }

    public boolean checkUser(String studStaffNum) {
        boolean found = false;
        try {
            pstmt = con.prepareStatement(
                    "SELECT * FROM \"ADMINISTRATOR\".USERS WHERE student_staff_number = ?"
            );
            pstmt.setString(1, studStaffNum);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                found = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return found;
    }

    public boolean isSamePassword(String studStaffNum, String newPassword) {
        try {
            pstmt = con.prepareStatement(
                    "SELECT password_hash FROM \"ADMINISTRATOR\".USERS WHERE student_staff_number = ?"
            );
            pstmt.setString(1, studStaffNum);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                String oldPassword = rs.getString("password_hash");
                if (oldPassword.equals(newPassword)) {
                    return true;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void updatePassword(String student_staff_number, String password_hash) {
        try {
            pstmt = this.con.prepareStatement("UPDATE \"ADMINISTRATOR\".USERS SET password_hash = ?"
                    + " WHERE student_staff_number = ?");
            pstmt.setString(1, password_hash);
            pstmt.setString(2, student_staff_number);
            pstmt.executeUpdate();
        } catch (SQLException err) {
            System.out.println("ERROR: " + err);
        }
    }

    public void deleteUser(int userId) {
        try {
            pstmt = this.con.prepareStatement("DELETE FROM \"ADMINISTRATOR\".USERS"
                    + " WHERE user_id = ?");
            pstmt.setInt(1, userId);
            pstmt.executeUpdate();
        } catch (SQLException err) {
            System.out.println("ERROR: " + err);
        }
    }

}