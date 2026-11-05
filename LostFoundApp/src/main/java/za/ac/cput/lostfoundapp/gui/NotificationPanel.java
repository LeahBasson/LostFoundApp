
package za.ac.cput.lostfoundapp.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.swing.BorderFactory;
import javax.swing.DefaultListCellRenderer;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;

import za.ac.cput.lostfoundapp.dao.NotificationDAO;

public class NotificationPanel extends JPanel {

    public static class NotificationItem {

        private final int id;
        private final String title;
        private final String message;
        private final String timestamp;
        private boolean isRead;

        public NotificationItem(int id, String title,
                String message, String timestamp, boolean isRead) {

            this.id = id;
            this.title = title;
            this.message = message;
            this.timestamp = timestamp;
            this.isRead = isRead;
        }

        public int getId() {
            return id;
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
            isRead = read;
        }
    }

    private final DefaultListModel<NotificationItem> listModel =
            new DefaultListModel<>();

    private final JList<NotificationItem> notificationList =
            new JList<>(listModel);

    private final NotificationDAO notificationDAO =
            new NotificationDAO();

    private final String loggedInUserId;

    public NotificationPanel(String loggedInUserId) {

        this.loggedInUserId = loggedInUserId;

        setLayout(new BorderLayout(10, 10));
        setBackground(Color.WHITE);

        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(Color.WHITE);

        JLabel title = new JLabel("Notifications");
        header.add(title, BorderLayout.WEST);

        JPanel actions = new JPanel(
                new FlowLayout(FlowLayout.RIGHT));

        actions.setBackground(Color.WHITE);

        JButton btnAdd =
                new JButton("Send Test Notification");

        JButton btnDelete =
                new JButton("Delete Selected");

        actions.add(btnAdd);
        actions.add(btnDelete);

        header.add(actions, BorderLayout.EAST);
        add(header, BorderLayout.NORTH);

        notificationList.setCellRenderer(
                new NotificationListCellRenderer());

        notificationList.addListSelectionListener(event -> {

            if (event.getValueIsAdjusting()) {
                return;
            }

            NotificationItem selected =
                    notificationList.getSelectedValue();

            if (selected != null && !selected.isRead()) {

                boolean updated = notificationDAO.markAsRead(
                        selected.getId(), loggedInUserId);

                if (updated) {
                    selected.setRead(true);
                    notificationList.repaint();
                }
            }
        });

        JScrollPane scroll =
                new JScrollPane(notificationList);

        scroll.setBorder(
                BorderFactory.createEmptyBorder(10, 10, 10, 10));

        add(scroll, BorderLayout.CENTER);

        btnAdd.addActionListener(
                event -> addTestNotification());

        btnDelete.addActionListener(
                event -> deleteSelectedNotification());

        loadNotificationsFromDB();
    }

    // READ
    private void loadNotificationsFromDB() {

        listModel.clear();

        for (NotificationItem item :
                notificationDAO.getNotifications(loggedInUserId)) {

            listModel.addElement(item);
        }
    }

    // CREATE
    private void addTestNotification() {

        String timestamp = LocalDateTime.now().format(
                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

        boolean added = notificationDAO.addNotification(
                loggedInUserId,
                "New Item Alert",
                "A new item matching your search was reported!",
                timestamp
        );

        if (added) {

            loadNotificationsFromDB();

            JOptionPane.showMessageDialog(
                    this,
                    "Notification inserted into Database!");

        } else {

            JOptionPane.showMessageDialog(
                    this,
                    "Could not insert notification.");
        }
    }

    // DELETE
    private void deleteSelectedNotification() {

        NotificationItem selected =
                notificationList.getSelectedValue();

        if (selected == null) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please select a notification to delete.");

            return;
        }

        boolean deleted = notificationDAO.deleteNotification(
                selected.getId(), loggedInUserId);

        if (deleted) {

            loadNotificationsFromDB();

            JOptionPane.showMessageDialog(
                    this,
                    "Notification deleted from Database!");

        } else {

            JOptionPane.showMessageDialog(
                    this,
                    "Could not delete notification.");
        }
    }

    private static class NotificationListCellRenderer
            extends DefaultListCellRenderer {

        @Override
        public Component getListCellRendererComponent(
                JList<?> list,
                Object value,
                int index,
                boolean selected,
                boolean focused) {

            JLabel label = (JLabel)
                    super.getListCellRendererComponent(
                            list, value, index, selected, focused);

            if (value instanceof NotificationItem) {

                NotificationItem item = (NotificationItem) value;

                label.setText(
                        "<html><b>" + escape(item.getTitle()) + "</b>"
                        + (item.isRead() ? "" : " - (Unread)")
                        + "<br>" + escape(item.getMessage())
                        + "<br><small>"
                        + escape(item.getTimestamp())
                        + "</small></html>"
                );
            }

            return label;
        }

        private static String escape(String text) {

            if (text == null) {
                return "";
            }

            return text.replace("&", "&amp;")
                    .replace("<", "&lt;")
                    .replace(">", "&gt;");
        }
    }

    // Run independently for testing
    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {

            JFrame frame =
                    new JFrame("FindMyItem - Notifications");

            frame.setDefaultCloseOperation(
                    JFrame.EXIT_ON_CLOSE);

            frame.setContentPane(
                    new NotificationPanel("DEMO_USER"));

            frame.setSize(750, 450);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
}
