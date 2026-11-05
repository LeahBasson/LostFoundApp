package za.ac.cput.lostfoundapp.gui;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.DefaultTableModel;
import za.ac.cput.lostfoundapp.dao.AchievementDAO;
import za.ac.cput.lostfoundapp.domain.Achievement;
import za.ac.cput.lostfoundapp.domain.User;

public class ProfilePanel extends JPanel implements ActionListener {

    // panels
    private JPanel pnlContainer;
    private JPanel pnlWrapper;
    private JPanel pnlHeader;
    private JPanel pnlOverview;
    private JPanel pnlOverviewBadges;
    private JPanel pnlStats;
    private JPanel pnlAccInfoHeader;
    private JPanel pnlAccInfo;
    private JPanel pnlEdit;
    private JPanel pnlAchievementHeader;
    private JPanel pnlAchievements;

    // labels
    private JLabel lblTitle;
    private JLabel lblSub;
    private JLabel lblOverviewName;
    private JLabel lblSummary;
    private JLabel lblAccInfo;
    private JLabel lblAchievementHeader;

    // table
    DefaultTableModel tableModel;
    JTable table;

    // button
    private JButton btnEdit;

    // object
    private User loggedInUser;
    private AchievementDAO achievementDB;

    public ProfilePanel(User loggedInUser) {
        this.loggedInUser = loggedInUser;
        achievementDB = new AchievementDAO();

        setLayout(new BorderLayout());

        // panels
        pnlContainer = new JPanel();
        pnlContainer.setLayout(new GridBagLayout());
        pnlWrapper = new JPanel();
        pnlWrapper.setLayout(new BorderLayout());

        // so that every panel can have its own height
        GridBagConstraints gbc = new GridBagConstraints(); // setup
        gbc.gridx = 0; // place header in the first column
        gbc.gridy = 0; // place header in the first row
        gbc.fill = GridBagConstraints.HORIZONTAL; // stretches sideways
        gbc.weightx = 1.0; // column can take any extra horizontal space
        gbc.weighty = 0.0; // Dynamic height allocation prevents stretching
        gbc.insets = new Insets(0, 0, 18, 0); // padding: (top, left, bottom, right)

        pnlContainer.setBorder(new EmptyBorder(0, 20, 20, 20));

        // header panel
        pnlHeader = new JPanel();
        // use this layout so that the rows dont stretch;
        pnlHeader.setLayout(new BoxLayout(pnlHeader, BoxLayout.Y_AXIS));
        // move the text from the border
        pnlHeader.setBorder(new EmptyBorder(20, 0, 0, 20)); // (top, left, bottom, right)

        // stud overview panel
        pnlOverview = new JPanel();
        pnlOverview.setLayout(new BoxLayout(pnlOverview, BoxLayout.Y_AXIS));
        pnlOverview.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(new Color(238, 238, 238), 1, true), // Rounded grey border line
                new EmptyBorder(15, 20, 15, 20) // Inside padding for text
        ));
        pnlOverview.setBackground(new Color(15, 23, 42));

        // overview badges panel inside of stud overview panel
        pnlOverviewBadges = new JPanel();
        pnlOverviewBadges.setLayout(new FlowLayout(FlowLayout.LEFT)); // 15px of horizontal gap
        pnlOverviewBadges.setAlignmentX(Component.LEFT_ALIGNMENT);
        pnlOverviewBadges.setOpaque(false); // Keeps the parent's dark blue background

        // acc info header panel
        pnlAccInfoHeader = new JPanel();
        pnlAccInfoHeader.setLayout(new FlowLayout(FlowLayout.LEFT));

        // acc info panel
        pnlAccInfo = new JPanel();
        pnlAccInfo.setLayout(new BorderLayout());

        pnlEdit = new JPanel();
        pnlEdit.setLayout(new FlowLayout(FlowLayout.LEFT));

        // achievements header panl
        pnlAchievementHeader = new JPanel();
        pnlAchievementHeader.setLayout(new FlowLayout(FlowLayout.LEFT));

        // earned achievements panel
        pnlAchievements = new JPanel();
        pnlAchievements = new JPanel(new GridLayout(0, 3, 15, 15));

        ArrayList<Achievement> achievements = achievementDB.getUserAchievements(loggedInUser.getUser_id());

        int earned_count = 0;
        // to display earned cards first.
        for (Achievement achievement : achievements) {
            if ("EARNED".equalsIgnoreCase(achievement.getStatus())) {
                pnlAchievements.add(createAchievementCard(achievement.getAchievement_name(), achievement.getAchievement_description(), achievement.getStatus()));
                pnlOverviewBadges.add(createOverviewBadge(achievement.getAchievement_name()));
                earned_count++;
            }

        }

        // locked cards
        for (Achievement achievement : achievements) {
            if (!"EARNED".equalsIgnoreCase(achievement.getStatus())) {
                pnlAchievements.add(createAchievementCard(achievement.getAchievement_name(), achievement.getAchievement_description(), achievement.getStatus()));
            }
        }

        // stats panel
        pnlStats = new JPanel();
        pnlStats.setLayout(new GridLayout(1, 4, 15, 0));
        
        String points = Integer.toString(achievementDB.getTotalPoints(loggedInUser.getUser_id()));
        
        JPanel card1 = createStatCard("📈", "5", "Items Found");
        JPanel card2 = createStatCard("🏆", "3", "Items Returned");
        JPanel card3 = createStatCard("⭐",points , "Total Points");
        JPanel card4 = createStatCard("🏅", Integer.toString(earned_count), "Achievements");

        // labels
        lblTitle = new JLabel("My Profile");
        lblTitle.setFont(new Font("SansSerif", Font.BOLD, 24));
        lblSub = new JLabel("Manage your account details and view your community contributions");
        lblSub.setForeground(Color.GRAY);
        lblOverviewName = new JLabel(loggedInUser.getFull_name());
        lblOverviewName.setFont(new Font("SansSerif", Font.BOLD, 18));
        lblOverviewName.setForeground(Color.WHITE);
        lblSummary = new JLabel(loggedInUser.getRole() + " • " + loggedInUser.getCampus() + " • " + points + " points");
        lblSummary.setForeground(Color.WHITE);
        lblOverviewName.setAlignmentX(Component.LEFT_ALIGNMENT);
        lblSummary.setAlignmentX(Component.LEFT_ALIGNMENT);

        lblAccInfo = new JLabel("Account Information");
        lblAccInfo.setFont(new Font("SansSerif", Font.BOLD, 16));

        lblAchievementHeader = new JLabel("Achievements & Badges");
        lblAchievementHeader.setFont(new Font("SansSerif", Font.BOLD, 16));

        // table
        tableModel = new DefaultTableModel();
        table = new JTable(tableModel);

        // scroll bar
        JScrollPane scrollPane = new JScrollPane(pnlWrapper);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        // button
        btnEdit = new JButton("Edit Profile");
        btnEdit.addActionListener(this);

        // add to panels
        pnlHeader.add(lblTitle);
        pnlHeader.add(Box.createVerticalStrut(5)); // keeps the space between UI elements
        pnlHeader.add(lblSub);
        pnlOverview.add(lblOverviewName);
        pnlOverview.add(Box.createVerticalStrut(5));
        pnlOverview.add(lblSummary);
        pnlOverview.add(Box.createVerticalStrut(10));
        pnlOverview.add(pnlOverviewBadges);

        pnlStats.add(card1);
        pnlStats.add(card2);
        pnlStats.add(card3);
        pnlStats.add(card4);

        pnlAccInfoHeader.add(lblAccInfo);
        pnlAccInfo.add(new JScrollPane(table), BorderLayout.CENTER);
        pnlAccInfo.setPreferredSize(new Dimension(500, 38));

        pnlEdit.add(btnEdit);

        pnlAchievementHeader.add(lblAchievementHeader);

        // add to container
        pnlContainer.add(pnlHeader, gbc);
        gbc.gridy = 1; // studOverview to row 2
        gbc.insets = new Insets(0, 0, 0, 0); // resets outer margins to the new rows.
        pnlContainer.add(pnlOverview, gbc);

        gbc.gridy = 2;
        gbc.insets = new Insets(18, 0, 0, 0);
        pnlContainer.add(pnlStats, gbc);

        gbc.gridy = 3;
        gbc.insets = new Insets(18, 0, 0, 0);
        pnlContainer.add(pnlAccInfoHeader, gbc);

        gbc.gridy = 4;
        gbc.insets = new Insets(18, 0, 0, 0);
        pnlContainer.add(pnlAccInfo, gbc);

        gbc.gridy = 5;
        gbc.insets = new Insets(18, 0, 0, 0);
        pnlContainer.add(pnlEdit, gbc);

        gbc.gridy = 6;
        gbc.insets = new Insets(18, 0, 0, 0);
        pnlContainer.add(pnlAchievementHeader, gbc);

        gbc.gridy = 7;
        gbc.insets = new Insets(18, 0, 0, 0);
        pnlContainer.add(pnlAchievements, gbc);

        pnlWrapper.add(pnlContainer, BorderLayout.NORTH);

        add(scrollPane, BorderLayout.CENTER);

        setGui();

    }

    // method for the card instead of repeating code
    private JPanel createStatCard(String iconText, String number, String description) {
        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBackground(Color.WHITE);

        // Add a simple border and 15px inside padding
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(230, 230, 230), 1),
                BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));

        // 1. Icon Label
        JLabel lblIcon = new JLabel(iconText);
        lblIcon.setFont(new Font("SansSerif", Font.PLAIN, 18));
        lblIcon.setAlignmentX(Component.LEFT_ALIGNMENT);

        // 2. Number Label
        JLabel lblNum = new JLabel(number);
        lblNum.setFont(new Font("SansSerif", Font.BOLD, 22));
        lblNum.setAlignmentX(Component.LEFT_ALIGNMENT);

        // 3. Description Label
        JLabel lblDesc = new JLabel(description);
        lblDesc.setFont(new Font("SansSerif", Font.PLAIN, 12));
        lblDesc.setForeground(Color.GRAY);
        lblDesc.setAlignmentX(Component.LEFT_ALIGNMENT);

        // Assemble the card with spacing gaps
        card.add(lblIcon);
        card.add(Box.createVerticalStrut(10));
        card.add(lblNum);
        card.add(Box.createVerticalStrut(4));
        card.add(lblDesc);

        return card;
    }

    private JPanel createAchievementCard(String achievement, String description, String status) {
        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBackground(Color.WHITE);

        // Add a simple border and 15px inside padding
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(230, 230, 230), 1),
                BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));

        // 1. Achievement Label
        JLabel lblAchievement = new JLabel(achievement);
        lblAchievement.setFont(new Font("SansSerif", Font.BOLD, 22));
        lblAchievement.setAlignmentX(Component.LEFT_ALIGNMENT);

        // 2. Description Label
        JLabel lblDesc = new JLabel(description);
        lblDesc.setFont(new Font("SansSerif", Font.PLAIN, 12));
        lblDesc.setForeground(Color.GRAY);
        lblDesc.setAlignmentX(Component.LEFT_ALIGNMENT);

        // 3. Status Label
        JLabel lblStatus = new JLabel(status);
        lblStatus.setFont(new Font("SansSerif", Font.PLAIN, 12));
        lblStatus.setForeground(Color.GRAY);
        lblStatus.setAlignmentX(Component.LEFT_ALIGNMENT);

        // Assemble the card with spacing gaps
        card.add(lblAchievement);
        card.add(Box.createVerticalStrut(4));
        card.add(lblDesc);
        card.add(Box.createVerticalStrut(4));
        card.add(lblStatus);

        return card;
    }

    private JLabel createOverviewBadge(String text) {
        JLabel badge = new JLabel(text);
        badge.setForeground(Color.WHITE);
        badge.setOpaque(true);
        badge.setBackground(new Color(31, 48, 86));
        badge.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        return badge;
    }

    public void setGui() {
        tableModel.addColumn("Full Name");
        tableModel.addColumn("Contact Number");
        tableModel.addColumn("Campus");
        tableModel.addColumn("Language");
        tableModel.addColumn("Email Address");
        tableModel.addColumn("Student Number");
        tableModel.addColumn("Role");

        tableModel.addRow(new Object[]{
            loggedInUser.getFull_name(),
            loggedInUser.getContact_number(),
            loggedInUser.getCampus(),
            loggedInUser.getLanguage(),
            loggedInUser.getEmail(),
            loggedInUser.getStudent_staff_number(),
            loggedInUser.getRole()
        });
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        EditProfileDialog dialog = new EditProfileDialog(loggedInUser);

        dialog.setVisible(true);
    }

} // end of class
