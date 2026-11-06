package za.ac.cput.lostfoundapp.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import za.ac.cput.lostfoundapp.gui.NotificationPanel.NotificationItem;

public class NotificationDAO {

    private static final String URL
            = "jdbc:derby://localhost:1527/University";
    private static final String USER = "app";
    private static final String PASSWORD = "app";

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    // CREATE
    public boolean addNotification(String studentStaffNumber,
            String title, String message, String timestamp) {

        String sql = "INSERT INTO NOTIFICATIONS "
                + "(STUDENT_STAFF_NUMBER, TITLE, MESSAGE, TIMESTAMP, IS_READ) "
                + "VALUES (?, ?, ?, ?, FALSE)";

        try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, studentStaffNumber);
            ps.setString(2, title);
            ps.setString(3, message);
            ps.setString(4, timestamp);

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // READ
    public List<NotificationItem> getNotifications(String studentStaffNumber) {

        List<NotificationItem> notifications = new ArrayList<>();

        String sql = "SELECT * FROM NOTIFICATIONS "
                + "WHERE STUDENT_STAFF_NUMBER = ? "
                + "ORDER BY NOTIFICATION_ID DESC";

        try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, studentStaffNumber);

            try (ResultSet rs = ps.executeQuery()) {

                while (rs.next()) {

                    NotificationItem item = new NotificationItem(
                            rs.getInt("NOTIFICATION_ID"),
                            rs.getString("TITLE"),
                            rs.getString("MESSAGE"),
                            rs.getString("TIMESTAMP"),
                            rs.getBoolean("IS_READ")
                    );

                    notifications.add(item);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return notifications;
    }

    // UPDATE
    public boolean markAsRead(int notificationId,
            String studentStaffNumber) {

        String sql = "UPDATE NOTIFICATIONS SET IS_READ = TRUE "
                + "WHERE NOTIFICATION_ID = ? "
                + "AND STUDENT_STAFF_NUMBER = ?";

        try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, notificationId);
            ps.setString(2, studentStaffNumber);

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // DELETE
    public boolean deleteNotification(int notificationId,
            String studentStaffNumber) {

        String sql = "DELETE FROM NOTIFICATIONS "
                + "WHERE NOTIFICATION_ID = ? "
                + "AND STUDENT_STAFF_NUMBER = ?";

        try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, notificationId);
            ps.setString(2, studentStaffNumber);

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
