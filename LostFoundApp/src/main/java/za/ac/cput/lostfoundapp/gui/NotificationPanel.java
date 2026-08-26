/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package za.ac.cput.lostfoundapp.gui;

import java.awt.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import javax.swing.*;

/**
 *
 * @author LiewellenBaartman
 */
public class NotificationPanel extends JPanel {

    public static class NotificationItem {

        private final String title;
        private final String message;
        private final String timestamp;
        private boolean isRead;

        public NotificationItem(String title, String message, String timestamp) {
            this.title = title;
            this.message = message;
            this.timestamp = timestamp;
            this.isRead = false;
        }

        public String getTitle() {
            return title;
        }

        public String getMessage() {
            return message;
        }

        public String getTimestamp() {
            return timestamp;
        }

        public boolean isRead() {
            return isRead;
        }

        public void setRead(boolean read) {
            this.isRead = read;
        }
    }

    private DefaultListModel<NotificationItem> listModel;
    private JList<NotificationItem> notificationList;

    public NotificationPanel() {
        setLayout(new BorderLayout());
        setBackground(new Color(248, 250, 252));

        JLabel headerLabel = new JLabel("Notifications");
        headerLabel.setFont(new Font("SansSerif", Font.BOLD, 22));
        headerLabel.setBorder(BorderFactory.createEmptyBorder(20, 20, 10, 20));
        add(headerLabel, BorderLayout.NORTH);

        listModel = new DefaultListModel<>();
        listModel.addElement(new NotificationItem("Potential Item Match!",
                "A Black HP Laptop matching your report was reported found at Library.",
                LocalDateTime.now().minusHours(2).format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"))));

        listModel.addElement(new NotificationItem("Claim Status Update",
                "Your claim for 'Student Card - T Molefe' has been approved by Admin.",
                LocalDateTime.now().minusDays(1).format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"))));

        notificationList = new JList<>(listModel);
        notificationList.setCellRenderer((ListCellRenderer<? super NotificationItem>) new NotificationListCellRenderer());
        notificationList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        notificationList.setFixedCellHeight(75);

        
        notificationList.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                NotificationItem selected = notificationList.getSelectedValue();
                if (selected != null && !selected.isRead()) {
                    selected.setRead(true);
                    notificationList.repaint();
                }
            }
        });

        JScrollPane scrollPane = new JScrollPane(notificationList);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(0, 20, 20, 20));
        add(scrollPane, BorderLayout.CENTER);
    }

    public void addNotification(String title, String message) {
        String now = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        listModel.add(0, new NotificationItem(title, message, now));
    }
}
