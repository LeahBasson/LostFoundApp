package za.ac.cput.lostfoundapp.gui;

import javax.swing.*;
import java.awt.*;

public class MessagesPanel extends JPanel {

    private JPanel messagesPanel;

    public MessagesPanel() {

        setLayout(new BorderLayout());
        setBackground(new Color(248, 250, 252));

        // Create the heading and New Message button
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(new Color(248, 250, 252));
        headerPanel.setBorder(
                BorderFactory.createEmptyBorder(15, 15, 15, 15)
        );

        JLabel title = new JLabel("Messages");
        title.setFont(new Font("SansSerif", Font.BOLD, 24));

        JButton newMessageButton = new JButton("New Message");

        // Opens the new message window
        newMessageButton.addActionListener(e -> openNewMessageWindow());

        headerPanel.add(title, BorderLayout.WEST);
        headerPanel.add(newMessageButton, BorderLayout.EAST);

        add(headerPanel, BorderLayout.NORTH);

        // Panel containing the messages
        messagesPanel = new JPanel();
        messagesPanel.setLayout(
                new BoxLayout(messagesPanel, BoxLayout.Y_AXIS)
        );
        messagesPanel.setBackground(
                new Color(248, 250, 252)
        );

        // Existing sample messages
        addMessage(
                "Item Match Found",
                "A possible match has been found for your lost black HP laptop.",
                "09 August 2026, 14:30",
                false
        );

        addMessage(
                "Claim Update",
                "Your claim for the Student Card has been received.",
                "08 August 2026, 10:15",
                true
        );

        addMessage(
                "Welcome to FindMyItem",
                "You can use this section to communicate about lost and found items.",
                "07 August 2026, 09:00",
                true
        );

        JScrollPane scrollPane =
                new JScrollPane(messagesPanel);

        add(scrollPane, BorderLayout.CENTER);
    }

    /*
     * Adds a message to the Messages section.
     */
    private void addMessage(
            String title,
            String message,
            String timestamp,
            boolean read) {

        JPanel messagePanel = createMessage(
                title,
                message,
                timestamp,
                read
        );

        messagesPanel.add(messagePanel);

        messagesPanel.add(
                Box.createVerticalStrut(10)
        );
    }

    /*
     * Opens the window where the user can create a message.
     */
    private void openNewMessageWindow() {

        JDialog dialog = new JDialog();

        dialog.setTitle("New Message");
        dialog.setSize(450, 350);
        dialog.setLocationRelativeTo(this);
        dialog.setLayout(new BorderLayout());

        JPanel formPanel = new JPanel(
                new GridBagLayout()
        );

        formPanel.setBorder(
                BorderFactory.createEmptyBorder(
                        15, 15, 15, 15
                )
        );

        GridBagConstraints constraints =
                new GridBagConstraints();

        constraints.insets =
                new Insets(5, 5, 5, 5);

        constraints.fill =
                GridBagConstraints.HORIZONTAL;

        // Recipient
        JLabel recipientLabel =
                new JLabel("Recipient:");

        JTextField recipientField =
                new JTextField();

        // Message title
        JLabel titleLabel =
                new JLabel("Title:");

        JTextField titleField =
                new JTextField();

        // Message
        JLabel messageLabel =
                new JLabel("Message:");

        JTextArea messageArea =
                new JTextArea(8, 25);

        messageArea.setLineWrap(true);
        messageArea.setWrapStyleWord(true);

        JScrollPane messageScrollPane =
                new JScrollPane(messageArea);

        // Recipient row
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.weightx = 0;

        formPanel.add(
                recipientLabel,
                constraints
        );

        constraints.gridx = 1;
        constraints.weightx = 1;

        formPanel.add(
                recipientField,
                constraints
        );

        // Title row
        constraints.gridx = 0;
        constraints.gridy = 1;
        constraints.weightx = 0;

        formPanel.add(
                titleLabel,
                constraints
        );

        constraints.gridx = 1;
        constraints.weightx = 1;

        formPanel.add(
                titleField,
                constraints
        );

        // Message row
        constraints.gridx = 0;
        constraints.gridy = 2;
        constraints.weightx = 0;
        constraints.anchor =
                GridBagConstraints.NORTH;

        formPanel.add(
                messageLabel,
                constraints
        );

        constraints.gridx = 1;
        constraints.weightx = 1;
        constraints.weighty = 1;
        constraints.fill =
                GridBagConstraints.BOTH;

        formPanel.add(
                messageScrollPane,
                constraints
        );

        // Send button
        JButton sendButton =
                new JButton("Send Message");

        sendButton.addActionListener(e -> {

            String recipient =
                    recipientField.getText().trim();

            String title =
                    titleField.getText().trim();

            String message =
                    messageArea.getText().trim();

            // Check that all fields have been completed
            if (recipient.isEmpty()
                    || title.isEmpty()
                    || message.isEmpty()) {

                JOptionPane.showMessageDialog(
                        dialog,
                        "Please complete all fields.",
                        "Missing Information",
                        JOptionPane.WARNING_MESSAGE
                );

                return;
            }

            // Add the new message to the Messages section
            addMessage(
                    title,
                    "To: " + recipient + " - " + message,
                    "Just now",
                    true
            );

            // Refresh the Messages section
            messagesPanel.revalidate();
            messagesPanel.repaint();

            // Close the New Message window
            dialog.dispose();

            JOptionPane.showMessageDialog(
                    this,
                    "Message sent successfully.",
                    "Message Sent",
                    JOptionPane.INFORMATION_MESSAGE
            );
        });

        JPanel buttonPanel = new JPanel();

        buttonPanel.add(sendButton);

        dialog.add(
                formPanel,
                BorderLayout.CENTER
        );

        dialog.add(
                buttonPanel,
                BorderLayout.SOUTH
        );

        dialog.setVisible(true);
    }

    /*
     * Creates the visual panel for an individual message.
     */
    private JPanel createMessage(
            String title,
            String message,
            String timestamp,
            boolean read) {

        JPanel panel =
                new JPanel(new BorderLayout());

        panel.setBackground(Color.WHITE);

        panel.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(
                                new Color(226, 232, 240)
                        ),
                        BorderFactory.createEmptyBorder(
                                15, 15, 15, 15
                        )
                )
        );

        panel.setMaximumSize(
                new Dimension(
                        Integer.MAX_VALUE,
                        120
                )
        );

        // Message title
        JLabel messageTitle =
                new JLabel(title);

        messageTitle.setFont(
                new Font(
                        "SansSerif",
                        Font.BOLD,
                        16
                )
        );

        // Message text
        JLabel messageText =
                new JLabel(
                        "<html>" + message + "</html>"
                );

        // Date and time
        JLabel messageTime =
                new JLabel(timestamp);

        messageTime.setForeground(Color.GRAY);

        // Read status
        JLabel status =
                new JLabel(
                        read ? "READ" : "UNREAD"
                );

        status.setFont(
                new Font(
                        "SansSerif",
                        Font.BOLD,
                        10
                )
        );

        if (read) {

            status.setForeground(
                    new Color(22, 163, 74)
            );

        } else {

            status.setForeground(
                    new Color(220, 38, 38)
            );
        }

        // Top section
        JPanel topPanel =
                new JPanel(new BorderLayout());

        topPanel.setBackground(Color.WHITE);

        topPanel.add(
                messageTitle,
                BorderLayout.WEST
        );

        topPanel.add(
                status,
                BorderLayout.EAST
        );

        // Add components to message panel
        panel.add(
                topPanel,
                BorderLayout.NORTH
        );

        panel.add(
                messageText,
                BorderLayout.CENTER
        );

        panel.add(
                messageTime,
                BorderLayout.SOUTH
        );

        return panel;
    }
    
   // private User currentUser;

    //public MessagesPanel(User user) {
    //this.currentUser = user;
    //initComponents();
//}
}