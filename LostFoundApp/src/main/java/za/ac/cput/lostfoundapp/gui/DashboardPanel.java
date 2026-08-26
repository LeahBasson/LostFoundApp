package za.ac.cput.lostfoundapp.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.table.DefaultTableModel;

public class DashboardPanel extends JPanel {

    // table
    DefaultTableModel tableModel;
    JTable table;

    // panels
    private JPanel pnlContainer;
    private JPanel pnlWrapper;
    private JPanel pnlHeader;
    private JPanel pnlStats;
    private JPanel pnlReport;
    private JPanel pnlRecentActivity;
    private JPanel pnlAchievements;
    private JPanel pnlBadges;

    public DashboardPanel() {

        setLayout(new BorderLayout());
        setBackground(new Color(248, 250, 252));

        // container panel
        pnlContainer = new JPanel();
        pnlContainer.setLayout(new GridBagLayout());
        pnlContainer.setBackground(new Color(248, 250, 252));

        GridBagConstraints gbc = new GridBagConstraints();

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        gbc.weighty = 0.0;
        gbc.insets = new Insets(2, 0, 0, 0);

        // header panel
        pnlHeader = new JPanel(new BorderLayout());
        pnlHeader.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        pnlHeader.setOpaque(false); // the background has no fill

        JLabel title = new JLabel("Dashboard");
        title.setFont(new Font("SansSerif", Font.BOLD, 24));
        JLabel sub = new JLabel("Welcome back, Thabo Molefe - Belleville Campus");
        sub.setForeground(Color.GRAY);

        JPanel tPanel = new JPanel(new GridLayout(2, 1));
        tPanel.setOpaque(false);
        tPanel.add(title);
        tPanel.add(sub);

        pnlHeader.add(tPanel, BorderLayout.WEST);

        // stats panel
        pnlStats = new JPanel(new GridLayout(1, 3, 15, 15));
        pnlStats.setBorder(BorderFactory.createEmptyBorder(0, 20, 20, 20));
        pnlStats.setOpaque(false);
        pnlStats.add(createCard("Total Reports", "1"));
        pnlStats.add(createCard("Lost Items", "0"));
        pnlStats.add(createCard("Found Items", "1"));

        // report panel
        pnlReport = new JPanel(new GridLayout(1, 2, 15, 15));
        pnlReport.setBorder(BorderFactory.createEmptyBorder(0, 20, 20, 20));
        pnlReport.setOpaque(false);

        pnlReport.add(createReportCards("Lost something? Create a detailed report to help others find it.", "Report a Lost Item", e -> {
            MainFrame frame = (MainFrame) SwingUtilities.getWindowAncestor(this);
            frame.showPanel("Report Lost Item");
        }));
        pnlReport.add(createReportCards("Found something? Help a fellow student get their item back.", "Report a Found Item", e -> {
            MainFrame frame = (MainFrame) SwingUtilities.getWindowAncestor(this);
            frame.showPanel("Report Found Item");
        }));

        pnlRecentActivity = new JPanel(new GridLayout(1, 1, 15, 15));
        pnlRecentActivity.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 20));
        pnlRecentActivity.setOpaque(false);
        JLabel lblRecent = new JLabel("Recent Activity");
        lblRecent.setFont(new Font("SansSerif", Font.BOLD, 16));
        pnlRecentActivity.add(lblRecent);

        // table
        tableModel = new DefaultTableModel();
        table = new JTable(tableModel);

        setGui();

        JScrollPane tableScrollPane = new JScrollPane(table);
        tableScrollPane.setPreferredSize(new Dimension(870, 200));

        // achievements panel
        pnlAchievements = new JPanel(new FlowLayout(FlowLayout.LEFT));
        pnlAchievements.setOpaque(false);
        JLabel lblAchievementHeading = new JLabel("Your Achievements");
        lblAchievementHeading.setFont(new Font("SansSerif", Font.BOLD, 16));
        pnlAchievements.add(lblAchievementHeading);

        // badges panel
        pnlBadges = new JPanel(new FlowLayout(FlowLayout.LEFT));
        pnlBadges.setOpaque(false);
        pnlBadges.add(createBadges("🏆 First Find"));
        pnlBadges.add(createBadges("🏆 Good Samaritan"));
        pnlBadges.add(createBadges("🏆 Campus Hero"));

        // add to container panel
        pnlContainer.add(pnlHeader, gbc);

        gbc.gridy = 1;
        gbc.insets = new Insets(0, 0, 0, 0); // (top, left, bottom, right)
        pnlContainer.add(pnlStats, gbc);

        gbc.gridy = 2;
        gbc.insets = new Insets(2, 0, 0, 0);
        pnlContainer.add(pnlReport, gbc);

        gbc.gridy = 3;
        gbc.insets = new Insets(0, 0, 18, 0);
        pnlContainer.add(pnlRecentActivity, gbc);

        gbc.gridy = 4;
        gbc.insets = new Insets(2, 0, 0, 0);
        gbc.fill = GridBagConstraints.NONE; // dont stretch the table
        gbc.weightx = 0; // don't give the table the extra horizontal space.
        pnlContainer.add(tableScrollPane, gbc);

        gbc.gridy = 5;
        gbc.insets = new Insets(15, 16, 0, 20);
        gbc.fill = GridBagConstraints.HORIZONTAL; // stretch panel
        gbc.weightx = 1.0; // giving back full horizontal width
        pnlContainer.add(pnlAchievements, gbc);

        gbc.gridy = 6;
        gbc.insets = new Insets(10, 16, 0, 20);
        pnlContainer.add(pnlBadges, gbc);

        // wrapper panel
        pnlWrapper = new JPanel(new BorderLayout());
        pnlWrapper.setBackground(new Color(248, 250, 252));

        // put container at NORTH so it keeps its preferred height instead of stretching vertically
        pnlWrapper.add(pnlContainer, BorderLayout.NORTH);

        // scroll bar
        JScrollPane scrollPane = new JScrollPane(pnlWrapper);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setBorder(null);

        add(scrollPane, BorderLayout.CENTER);
    }

    private JPanel createCard(String label, String value) {

        JPanel p = new JPanel(new BorderLayout());

        p.setBackground(Color.WHITE);

        p.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(226, 232, 240)),
                BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));

        JLabel v = new JLabel(value);
        v.setFont(new Font("SansSerif", Font.BOLD, 28));

        JLabel l = new JLabel(label);
        l.setForeground(Color.GRAY);

        p.add(v, BorderLayout.NORTH);
        p.add(l, BorderLayout.SOUTH);

        return p;
    }

    private JPanel createReportCards(String label, String button, ActionListener action) {

        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));

        card.setBackground(new Color(13, 102, 255));

        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(226, 232, 240), 1),
                BorderFactory.createEmptyBorder(5, 15, 15, 15)
        ));

        JLabel reportLabels = new JLabel(label);
        reportLabels.setAlignmentX(Component.LEFT_ALIGNMENT);
        reportLabels.setForeground(Color.WHITE);

        JButton reportButtons = new JButton(button);
        reportButtons.setAlignmentX(Component.LEFT_ALIGNMENT);

        reportButtons.addActionListener(action);

        card.add(reportLabels);
        card.add(Box.createRigidArea(new Dimension(0, 15)));
        card.add(reportButtons);

        return card;
    }

    private JPanel createBadges(String label) {
        JPanel badges = new JPanel();

        badges.setLayout(new FlowLayout());

        badges.setBorder(BorderFactory.createLineBorder(new Color(13, 102, 255), 1)
        );

        JLabel lblbadges = new JLabel(label);
        lblbadges.setAlignmentX(Component.LEFT_ALIGNMENT);

        badges.add(lblbadges);

        return badges;
    }

    public void setGui() {

        tableModel.addColumn("item_id");
        tableModel.addColumn("item_type");
        tableModel.addColumn("category");
        tableModel.addColumn("brand_model");
        tableModel.addColumn("location");
        tableModel.addColumn("status");

    }
}
