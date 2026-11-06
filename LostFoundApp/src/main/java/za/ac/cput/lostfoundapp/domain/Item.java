/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package za.ac.cput.lostfoundapp.domain;
import java.sql.Timestamp;

/**
 *
 * @author davin
 */
public class Item {
    private int itemId;
    private String itemType; // LOST or FOUND
    private String category;
    private String brandModel;
    private String colour;
    private String description;
    private String serialNumber;
    private String location;
    private Timestamp dateTimeEvent;
    private String status; // PENDING, FOUND, CLAIMED, COLLECTED, EXPIRED, RESOLVED
    private int reportedByUserId;
    public Item() {}
    public Item(String itemType, String category, String brandModel, String colour,
                String description, String location, String status, int reportedByUserId) {
        this.itemType = itemType;
        this.category = category;
        this.brandModel = brandModel;
        this.colour = colour;
        this.description = description;
        this.location = location;
        this.status = status;
        this.reportedByUserId = reportedByUserId;
    }
    // Getters and Setters
    public int getItemId() { return itemId; }
    public void setItemId(int itemId) { this.itemId = itemId; }
    public String getItemType() { return itemType; }
    public void setItemType(String itemType) { this.itemType = itemType; }
    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }
    public String getBrandModel() { return brandModel; }
    public void setBrandModel(String brandModel) { this.brandModel = brandModel; }
    public String getColour() { return colour; }
    public void setColour(String colour) { this.colour = colour; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public String getSerialNumber() { return serialNumber; }
    public void setSerialNumber(String serialNumber) { this.serialNumber = serialNumber; }
    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }
    public Timestamp getDateTimeEvent() { return dateTimeEvent; }
    public void setDateTimeEvent(Timestamp dateTimeEvent) { this.dateTimeEvent = dateTimeEvent; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public int getReportedByUserId() { return reportedByUserId; }
    public void setReportedByUserId(int reportedByUserId) { this.reportedByUserId = reportedByUserId; }
}