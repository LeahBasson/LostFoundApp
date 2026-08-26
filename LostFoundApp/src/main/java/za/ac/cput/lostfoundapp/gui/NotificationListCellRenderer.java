package za.ac.cput.lostfoundapp.gui;

import java.awt.*;
import javax.swing.*;

public class NotificationListCellRenderer extends JPanel implements ListCellRenderer<NotificationPanel.NotificationItem> {

    private final JLabel lblTitle = new JLabel();
    private final JLabel lblMessage = new JLabel();
    private final JLabel lblTime = new JLabel();

    public NotificationListCellRenderer() {
        setLayout(new BorderLayout(5, 5));
        setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(226, 232, 240)),
                BorderFactory.createEmptyBorder(10, 15, 10, 15)
        ));

        lblTitle.setFont(new Font("SansSerif", Font.BOLD, 14));
        lblTime.setFont(new Font("SansSerif", Font.PLAIN, 11));
        lblTime.setForeground(Color.GRAY);
        lblMessage.setFont(new Font("SansSerif", Font.PLAIN, 12));
        lblMessage.setForeground(new Color(71, 85, 105));

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setOpaque(false);
        topPanel.add(lblTitle, BorderLayout.WEST);
        topPanel.add(lblTime, BorderLayout.EAST);

        add(topPanel, BorderLayout.NORTH);
        add(lblMessage, BorderLayout.CENTER);
    }

    @Override
    public Component getListCellRendererComponent(JList<? extends NotificationPanel.NotificationItem> list,
            NotificationPanel.NotificationItem item,
            int index,
            boolean isSelected,
            boolean cellHasFocus) {
        lblTitle.setText(item.getTitle() + (item.isRead() ? "" : "  • (Unread)"));
        lblMessage.setText(item.getMessage());
        lblTime.setText(item.getTimestamp());

        if (isSelected) {
            setBackground(new Color(239, 246, 255));
        } else if (!item.isRead()) {
            setBackground(Color.WHITE);
            lblTitle.setForeground(new Color(37, 99, 235));
        } else {
            setBackground(new Color(248, 250, 252));
            lblTitle.setForeground(Color.DARK_GRAY);
        }

        return this;
    }
}
