/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package za.ac.cput.lostfoundapp.dao;
import za.ac.cput.lostfoundapp.connection.DBConnection;
import za.ac.cput.lostfoundapp.domain.Item;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author davin
 */
    public class ItemDAO {
    public List<Item> getItemsByUserId(int userId) {
        List<Item> items = new ArrayList<>();
        String sql = "SELECT * FROM ADMINISTRATOR.ITEMS WHERE reported_by_user_id = ? ORDER BY item_id DESC";
        try (Connection con = DBConnection.derbyConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Item item = new Item();
                item.setItemId(rs.getInt("item_id"));
                item.setItemType(rs.getString("item_type"));
                item.setCategory(rs.getString("category"));
                item.setBrandModel(rs.getString("brand_model"));
                item.setColour(rs.getString("colour"));
                item.setDescription(rs.getString("description"));
                item.setSerialNumber(rs.getString("serial_number"));
                item.setLocation(rs.getString("location"));
                item.setStatus(rs.getString("status"));
                item.setReportedByUserId(rs.getInt("reported_by_user_id"));
                items.add(item);
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return items;
    }
    public boolean addItem(Item item) {
        String sql = "INSERT INTO ADMINISTRATOR.ITEMS (item_type, category, brand_model, colour, description, serial_number, location, status, reported_by_user_id, date_time_event) VALUES (?,?,?,?,?,?,?,?,?, CURRENT_TIMESTAMP)";
        try (Connection con = DBConnection.derbyConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, item.getItemType());
            ps.setString(2, item.getCategory());
            ps.setString(3, item.getBrandModel());
            ps.setString(4, item.getColour());
            ps.setString(5, item.getDescription());
            ps.setString(6, item.getSerialNumber());
            ps.setString(7, item.getLocation());
            ps.setString(8, item.getStatus());
            ps.setInt(9, item.getReportedByUserId());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) { e.printStackTrace(); return false; }
    }
    public boolean updateStatus(int itemId, String newStatus, int userId) {
        String sql = "UPDATE ADMINISTRATOR.ITEMS SET status = ? WHERE item_id = ? AND reported_by_user_id = ?";
        try (Connection con = DBConnection.derbyConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, newStatus);
            ps.setInt(2, itemId);
            ps.setInt(3, userId);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) { e.printStackTrace(); return false; }
    }
    public boolean deleteItem(int itemId, int userId) {
        String sql = "DELETE FROM ADMINISTRATOR.ITEMS WHERE item_id = ? AND reported_by_user_id = ?";
        try (Connection con = DBConnection.derbyConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, itemId);
            ps.setInt(2, userId);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) { e.printStackTrace(); return false; }
    }
}