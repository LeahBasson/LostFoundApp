package za.ac.cput.lostfoundapp.gui;

import za.ac.cput.lostfoundapp.gui.DashboardPanel;
import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {

    private JPanel contentPanel;
    private CardLayout cardLayout;

    public MainFrame() {
        setTitle("FindMyItem - CPUT Lost & Found | Your Student Nr - Your Name");
        setSize(1150, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        initUI();
    }

    private void initUI() {
        JPanel sidebar = new JPanel();
        sidebar.setBackground(new Color(15, 23, 42));
        sidebar.setPreferredSize(new Dimension(210, 0));
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));

        JLabel logo = new JLabel("  FindMyItem");
        logo.setFont(new Font("SansSerif", Font.BOLD, 20));
        logo.setForeground(Color.WHITE);
        logo.setBorder(BorderFactory.createEmptyBorder(20, 10, 10, 10));

        JLabel userLabel = new JLabel("  Your Name");
        userLabel.setForeground(new Color(148, 163, 184));
        userLabel.setBorder(BorderFactory.createEmptyBorder(0, 10, 20, 10));

        JButton btnDashboard = createNavButton("Dashboard");
        JButton btnReportLost = createNavButton("Report Lost Item");
        JButton btnReportFound = createNavButton("Report Found Item");
        JButton btnMyItems = createNavButton("My Items");
        JButton btnClaims = createNavButton("My Claims");
        JButton btnMessages = createNavButton("Messages");
        JButton btnProfile = createNavButton("Profile");

        sidebar.add(logo);
        sidebar.add(userLabel);
        sidebar.add(btnDashboard);
        sidebar.add(btnReportLost);
        sidebar.add(btnReportFound);
        sidebar.add(btnMyItems);
        sidebar.add(btnClaims);
        sidebar.add(btnMessages);
        sidebar.add(btnProfile);

        cardLayout = new CardLayout();
        contentPanel = new JPanel(cardLayout);
        contentPanel.add(new DashboardPanel(), "Dashboard");
        contentPanel.add(new ReportItemPanel("LOST"), "Report Lost Item");
        contentPanel.add(new ReportItemPanel("FOUND"), "Report Found Item");
        contentPanel.add(new MyItemsPanel(), "My Items");
        contentPanel.add(new SimplePanel("My Claims Module - Claim ownership of items"), "My Claims");
        contentPanel.add(new SimplePanel("Messages Module - Secure chat"), "Messages");
        contentPanel.add(new ProfilePanel(), "Profile");

        btnDashboard.addActionListener(e -> cardLayout.show(contentPanel, "Dashboard"));
        btnReportLost.addActionListener(e -> cardLayout.show(contentPanel, "Report Lost Item"));
        btnReportFound.addActionListener(e -> cardLayout.show(contentPanel, "Report Found Item"));
        btnMyItems.addActionListener(e -> cardLayout.show(contentPanel, "My Items"));
        btnClaims.addActionListener(e -> cardLayout.show(contentPanel, "My Claims"));
        btnMessages.addActionListener(e -> cardLayout.show(contentPanel, "Messages"));
        btnProfile.addActionListener(e -> cardLayout.show(contentPanel, "Profile"));

        setLayout(new BorderLayout());
        add(sidebar, BorderLayout.WEST);
        add(contentPanel, BorderLayout.CENTER);
    }

    private JButton createNavButton(String text) {
        JButton b = new JButton(text);
        b.setAlignmentX(Component.LEFT_ALIGNMENT);
        b.setMaximumSize(new Dimension(210, 45));
        b.setBackground(new Color(15, 23, 42));
        b.setForeground(Color.WHITE);
        b.setFocusPainted(false);
        b.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 10));
        b.setHorizontalAlignment(SwingConstants.LEFT);
        b.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return b;
    }

    public void showPanel(String panelName) {
        cardLayout.show(contentPanel, panelName);
    }
}

class SimplePanel extends JPanel {

    public SimplePanel(String title) {
        setLayout(new BorderLayout());
        setBackground(new Color(248, 250, 252));
        JLabel l = new JLabel(title, SwingConstants.CENTER);
        l.setFont(new Font("SansSerif", Font.BOLD, 18));
        add(l, BorderLayout.CENTER);
    }
}
