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

    public void register(User user) { // add a user
        try {
            pstmt = this.con.prepareStatement("INSERT INTO Users (student_staff_number, email, "
                    + "full_name, campus, contact_number, role, password_hash, language) "
                    + "VALUES (?, ?, ?, ?, ?, ?, ?, ?)");

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

    public boolean emailCheck(String email) { // register: check if users email doesn't already exist OR login: check if users email exists
        try {
            pstmt = this.con.prepareStatement("SELECT email FROM Users WHERE email = ?");

            pstmt.setString(1, email);

            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return true; // email exists
            }

        } catch (SQLException err) {
            System.out.println("ERROR: " + err);
        }
        return false; // email doesn't exist
    }

    public ArrayList<User> selectUser(String emailAddress) { 
        ArrayList<User> users = new ArrayList<>();

        try {
            pstmt = this.con.prepareStatement("SELECT full_name, contact_number, campus, "
                    + "language, email, student_staff_number, role FROM Users WHERE email = ?");
            
            pstmt.setString(1, emailAddress);
            
            ResultSet rs = pstmt.executeQuery();
            
            while(rs.next()){
                String full_name = rs.getString("full_name");
                String contact_number = rs.getString("contact_number");
                String campus = rs.getString("campus");
                String language = rs.getString("language");
                String email = rs.getString("email");
                String student_staff_number = rs.getString("student_staff_number");
                String role = rs.getString("role");
                
                User user = new User(full_name, contact_number, campus, language, email, student_staff_number, role);
                
                users.add(user);
            }
            
        } catch (SQLException err) {
            System.out.println("ERROR: " + err);
        }
        return users;
    }
    
    

}// end of class
