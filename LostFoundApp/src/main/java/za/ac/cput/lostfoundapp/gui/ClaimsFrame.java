package za.ac.cput.lostfoundapp.gui;

import java.awt.*;
import javax.swing.*;
import za.ac.cput.lostfoundapp.domain.User;

/**
 * Runs the My Claims module inside the exact same frame set-up as MainFrame
 * (same size, sidebar, colours and nav buttons) without editing MainFrame.
 */
public class ClaimsFrame extends JFrame {

    private static final Color SIDEBAR = new Color(15, 23, 42);
    private static final Color SIDEBAR_SELECTED = new Color(30, 41, 59);

    private JPanel contentPanel;
    private CardLayout cardLayout;
    private User loggedInUser;
    private JButton[] navButtons;

    public ClaimsFrame(User loggedInUser) {
        this.loggedInUser = loggedInUser;
        setTitle("FindMyItem - CPUT Lost & Found | " + loggedInUser.getStudent_staff_number() + " - "
                + loggedInUser.getFull_name());
        setSize(1150, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        initUI();
    }

    private void initUI() {
        JPanel sidebar = new JPanel();
        sidebar.setBackground(SIDEBAR);
        sidebar.setPreferredSize(new Dimension(210, 0));
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));

        JLabel logo = new JLabel("  FindMyItem");
        logo.setFont(new Font("SansSerif", Font.BOLD, 20));
        logo.setForeground(Color.WHITE);
        logo.setBorder(BorderFactory.createEmptyBorder(20, 10, 10, 10));

        JLabel userLabel = new JLabel("   " + loggedInUser.getFull_name());
        userLabel.setForeground(new Color(148, 163, 184));
        userLabel.setBorder(BorderFactory.createEmptyBorder(0, 10, 20, 10));

        String[] names = {"Dashboard", "Report Lost Item", "Report Found Item",
            "My Items", "My Claims", "Messages", "Profile"};

        cardLayout = new CardLayout();
        contentPanel = new JPanel(cardLayout);
        contentPanel.add(new SimplePanel("Dashboard"), "Dashboard");
        contentPanel.add(new ReportItemPanel("LOST"), "Report Lost Item");
        contentPanel.add(new ReportItemPanel("FOUND"), "Report Found Item");
        contentPanel.add(new MyItemsPanel(), "My Items");
        contentPanel.add(new ClaimsPanel(loggedInUser), "My Claims");
        contentPanel.add(new SimplePanel("Messages Module - Secure chat"), "Messages");
        contentPanel.add(new ProfilePanel(loggedInUser), "Profile");

        sidebar.add(logo);
        sidebar.add(userLabel);

        navButtons = new JButton[names.length];
        for (int i = 0; i < names.length; i++) {
            final int index = i;
            navButtons[i] = createNavButton(names[i]);
            navButtons[i].addActionListener(e -> select(index, names[index]));
            sidebar.add(navButtons[i]);
        }

        setLayout(new BorderLayout());
        add(sidebar, BorderLayout.WEST);
        add(contentPanel, BorderLayout.CENTER);

        select(4, "My Claims"); // open on My Claims
    }

    private void select(int index, String panelName) {
        for (int i = 0; i < navButtons.length; i++) {
            navButtons[i].setBackground(i == index ? SIDEBAR_SELECTED : SIDEBAR);
        }
        cardLayout.show(contentPanel, panelName);
    }

    // identical to MainFrame.createNavButton
    private JButton createNavButton(String text) {
        JButton b = new JButton(text);
        b.setAlignmentX(Component.LEFT_ALIGNMENT);
        b.setMaximumSize(new Dimension(210, 45));
        b.setBackground(SIDEBAR);
        b.setForeground(Color.WHITE);
        b.setFocusPainted(false);
        b.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 10));
        b.setHorizontalAlignment(SwingConstants.LEFT);
        b.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return b;
    }

    // run just this module (no login needed); the user_id must exist in the Users table
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            User demo = new User(1, "Thabo Molefe", "0821234567", "Bellville", "English",
                    "thabo@mycput.ac.za", "12345678", "Student", "pass1234");
            new ClaimsFrame(demo).setVisible(true);
        });
    }
}
