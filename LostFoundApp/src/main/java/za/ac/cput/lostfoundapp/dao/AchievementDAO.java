package za.ac.cput.lostfoundapp.dao;

import java.sql.*;
import javax.swing.JOptionPane;
import za.ac.cput.lostfoundapp.connection.DBConnection;

public class AchievementDAO {

    private Connection con;
    private PreparedStatement pstmt;

    public AchievementDAO() {
        try {
            this.con = DBConnection.derbyConnection();
        } catch (SQLException exception) {
            JOptionPane.showMessageDialog(null, exception.getMessage());
        }
    }

    public void addAchievement(int userId) {
        try {
            pstmt = this.con.prepareStatement("INSERT INTO Achievement (user_id, achievement_name, "
                    + "achievement_description, points)" + "VALUES (?, ?, ?, ?)");

            // First achievement
            pstmt.setInt(1, userId);
            pstmt.setString(2, "🎯 First Find");
            pstmt.setString(3, "Found your first item");
            pstmt.setInt(4, 500);
            pstmt.executeUpdate();

            // Second achievement
            pstmt.setInt(1, userId);
            pstmt.setString(2, "❤️ Good Samaritan");
            pstmt.setString(3, "Helped 3 people recover their items");
            pstmt.setInt(4, 1000);
            pstmt.executeUpdate();

            // Third achievement
            pstmt.setInt(1, userId);
            pstmt.setString(2, "🎖️ Campus Hero");
            pstmt.setString(3, "Found 5 items");
            pstmt.setInt(4, 1500);
            pstmt.executeUpdate();

            // Forth achievement
            pstmt.setInt(1, userId);
            pstmt.setString(2, "🏗️ Community Builder");
            pstmt.setString(3, "Earned 1000 points");
            pstmt.setInt(4, 0);
            pstmt.executeUpdate();

            // Fifth achievement
            pstmt.setInt(1, userId);
            pstmt.setString(2, "😇️ Guardian Angel");
            pstmt.setString(3, "Found 10 items");
            pstmt.setInt(4, 2000);
            pstmt.executeUpdate();

            // Sixth achievement
            pstmt.setInt(1, userId);
            pstmt.setString(2, "⭐️ Legend");
            pstmt.setString(3, "Earned 5000 points");
            pstmt.setInt(4, 0);
            pstmt.executeUpdate();

        } catch (SQLException err) {
            System.out.println("ERROR: " + err);
        }
    }

    public void checkFirstFind(int userId) {
        try {
            pstmt = this.con.prepareStatement("SELECT COUNT(*) FROM Items WHERE found_user_id = ? "
                    + "AND status = 'FOUND' ");

            pstmt.setInt(1, userId);

            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                int count = rs.getInt(1);

                if (count >= 1) { // 1 found item gets the achievement, > so user can still keep achievement. 
                    pstmt = this.con.prepareStatement("UPDATE Achievement SET status = 'EARNED' WHERE user_id = ? "
                            + "AND achievement_name = '🎯 First Find'");
                    // use the name not the id because its auto-generated.

                    pstmt.setInt(1, userId);
                    pstmt.executeUpdate();

                }
            }

        } catch (SQLException err) {
            System.out.println("ERROR: " + err);
        }
    }

    public void checkGoodSamaritan(int userId) {
        try {
            pstmt = this.con.prepareStatement("SELECT COUNT(DISTINCT owner_user_id) FROM Items WHERE found_user_id = ? "
                    + "AND status = 'FOUND' ");

            pstmt.setInt(1, userId);

            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                int count = rs.getInt(1);

                if (count >= 3) { // 3 different people and found item gets the achievement, > so user can still keep achievement.
                    pstmt = this.con.prepareStatement("UPDATE Achievement SET status = 'EARNED' WHERE user_id = ? "
                            + "AND achievement_name = '❤️ Good Samaritan'");

                    pstmt.setInt(1, userId);
                    pstmt.executeUpdate();
                }
            }

        } catch (SQLException err) {
            System.out.println("ERROR: " + err);
        }
    }

    public void checkCampusHero(int userId) {
        try {
            pstmt = this.con.prepareStatement("SELECT COUNT(*) FROM Items WHERE found_user_id = ? "
                    + "AND status = 'FOUND'");

            pstmt.setInt(1, userId);

            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                int count = rs.getInt(1);

                if (count >= 5) {
                    pstmt = this.con.prepareStatement("UPDATE Achievement SET status = 'EARNED' WHERE user_id = ? "
                            + "AND achievement_name = '🎖️ Campus Hero'");

                    pstmt.setInt(1, userId);
                    pstmt.executeUpdate();
                }
            }

        } catch (SQLException err) {
            System.out.println("ERROR: " + err);
        }
    }

    public void checkCommunityBuilder(int userId) {
        int totalPoints = getTotalPoints(userId);

        if (totalPoints >= 1000) {
            try {

                pstmt = this.con.prepareStatement("UPDATE Achievement SET status = 'EARNED' WHERE user_id = ? "
                        + "AND achievement_name = '🏗️ Community Builder'");

                pstmt.setInt(1, userId);
                pstmt.executeUpdate();
            } catch (SQLException err) {
                System.out.println("ERROR: " + err);
            }
        }

    }

    public void checkGuardianAngel(int userId) {
        try {
            pstmt = this.con.prepareStatement("SELECT COUNT(*) FROM Items WHERE found_user_id = ? "
                    + "AND status = 'FOUND' ");

            pstmt.setInt(1, userId);

            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                int count = rs.getInt(1);

                if (count >= 10) {
                    pstmt = this.con.prepareStatement("UPDATE Achievement SET status = 'EARNED' WHERE user_id = ? "
                            + "AND achievement_name = '😇️ Guardian Angel'");

                    pstmt.setInt(1, userId);
                    pstmt.executeUpdate();

                }
            }

        } catch (SQLException err) {
            System.out.println("ERROR: " + err);
        }
    }

    public void checkLegend(int userId) {
        int totalPoints = getTotalPoints(userId);

        if (totalPoints >= 5000) {
            try {
                pstmt = this.con.prepareStatement(
                        "UPDATE Achievement SET status = 'EARNED' "
                        + "WHERE user_id = ? "
                        + "AND achievement_name = '⭐️ Legend'");

                pstmt.setInt(1, userId);
                pstmt.executeUpdate();

            } catch (SQLException err) {
                System.out.println("ERROR: " + err);
            }
        }
    }

    public int getTotalPoints(int userId) {
        int totalPoints = 0;
        try {
            pstmt = this.con.prepareStatement("SELECT SUM(points) FROM Achievement WHERE user_id = ? "
                    + "AND status = 'EARNED'");

            pstmt.setInt(1, userId);

            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                totalPoints = rs.getInt(1);
            }

        } catch (SQLException err) {
            System.out.println("ERROR: " + err);
        }
        return totalPoints;
    }

    public int getEarnedAchievements(int userId) {
        int count = 0;

        try {
            pstmt = this.con.prepareStatement("SELECT COUNT(*) FROM Achievement WHERE user_id = ? "
                    + "AND status = 'EARNED'");

            pstmt.setInt(1, userId);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                count = rs.getInt(1);
            }

        } catch (SQLException err) {
            System.out.println("ERROR: " + err);
        }

        return count;
    }

}// end of class
