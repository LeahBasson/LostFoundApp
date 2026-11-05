package za.ac.cput.lostfoundapp.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import za.ac.cput.lostfoundapp.connection.DBConnection;
import za.ac.cput.lostfoundapp.domain.Claim;

public class ClaimDAO {

    
    public int addClaim(Claim claim) {
        String sql = "INSERT INTO CLAIMS (item_id, claimant_user_id, claim_date, "
                + "proof_description, status) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DBConnection.derbyConnection();
                PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setInt(1, claim.getItemId());
            ps.setInt(2, claim.getClaimantUserId());
            ps.setDate(3, claim.getClaimDate());
            ps.setString(4, claim.getProofDescription());
            ps.setString(5, claim.getStatus());

            int rowsInserted = ps.executeUpdate();

            if (rowsInserted > 0) {
                try (ResultSet keys = ps.getGeneratedKeys()) {
                    if (keys.next()) {
                        int newId = keys.getInt(1);
                        System.out.println("Claim added with claim_id = " + newId);
                        return newId;
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    
    public List<Claim> getAllClaims() {
        List<Claim> claims = new ArrayList<>();
        String sql = "SELECT * FROM CLAIMS";

        try (Connection conn = DBConnection.derbyConnection();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                claims.add(mapRow(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return claims;
    }

    
    public boolean updateClaimStatus(int claimId, String newStatus, Date handoverDate) {
        String sql = "UPDATE CLAIMS SET status = ?, handover_date = ? WHERE claim_id = ?";

        try (Connection conn = DBConnection.derbyConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, newStatus);
            ps.setDate(2, handoverDate);
            ps.setInt(3, claimId);

            int rowsUpdated = ps.executeUpdate();
            System.out.println(rowsUpdated + " claim(s) updated to status: " + newStatus);
            return rowsUpdated > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    
    public boolean deleteClaim(int claimId) {
        String sql = "DELETE FROM CLAIMS WHERE claim_id = ?";

        try (Connection conn = DBConnection.derbyConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, claimId);
            int rowsDeleted = ps.executeUpdate();
            System.out.println(rowsDeleted + " claim(s) deleted.");
            return rowsDeleted > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    
    private Claim mapRow(ResultSet rs) throws SQLException {
        return new Claim(
                rs.getInt("claim_id"),
                rs.getInt("item_id"),
                rs.getInt("claimant_user_id"),
                rs.getDate("claim_date"),
                rs.getString("proof_description"),
                rs.getString("status"),
                rs.getDate("handover_date")
        );
    }

    
    public String getClaimantName(int userId) {
        String sql = "SELECT full_name FROM Users WHERE user_id = ?";

        try (Connection conn = DBConnection.derbyConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, userId);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("full_name");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "User #" + userId;
    }

}
