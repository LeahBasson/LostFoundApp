package za.ac.cput.lostfoundapp.gui;

import java.awt.*;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import za.ac.cput.lostfoundapp.dao.ClaimDAO;
import za.ac.cput.lostfoundapp.domain.Claim;
import za.ac.cput.lostfoundapp.domain.User;

public class ClaimsPanel extends JPanel {

    // same colours used across the rest of the app
    private static final Color BG = new Color(248, 250, 252);
    private static final Color BLUE = new Color(37, 99, 235);
    private static final Color BORDER = new Color(226, 232, 240);
    private static final Color LABEL = new Color(71, 85, 105);
    private static final Color RED = new Color(220, 38, 38);
    private static final Color SELECTED = new Color(239, 246, 255);

    // objects
    private final ClaimDAO claimDAO = new ClaimDAO();
    private final User loggedInUser;
    private List<Claim> claims = new ArrayList<>();
    private final Map<Integer, String> names = new HashMap<>();

    // add
    private JTextField txtItemId;
    private JTextField txtClaimantId;
    private JTextField txtClaimDate;
    private JTextArea txtProof;
    private JButton btnSubmit;
    private JLabel lblAddResult;

    // retrieve
    private DefaultTableModel tableModel;
    private JTable table;

    // update
    private JLabel lblUpdateSelected;
    private JTextField txtCurrentStatus;
    private JComboBox<String> cboNewStatus;
    private JTextField txtHandoverDate;
    private JButton btnSave;
    private JLabel lblUpdateResult;

    // delete
    private JLabel lblDeleteSelected;
    private JLabel lblDeleteWarning;
    private JButton btnDelete;
    private JButton btnCancel;
    private JLabel lblDeleteResult;

    public ClaimsPanel(User loggedInUser) {
        this.loggedInUser = loggedInUser;

        setLayout(new BorderLayout());
        setBackground(BG);

        JPanel pnlContainer = new JPanel(new GridBagLayout());
        pnlContainer.setBackground(BG);
        pnlContainer.setBorder(new EmptyBorder(20, 20, 20, 20));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        gbc.weighty = 0.0;
        gbc.insets = new Insets(0, 0, 18, 0);

        pnlContainer.add(buildAddSection(), gbc);
        gbc.gridy = 1;
        pnlContainer.add(buildRetrieveSection(), gbc);
        gbc.gridy = 2;
        pnlContainer.add(buildUpdateSection(), gbc);
        gbc.gridy = 3;
        gbc.insets = new Insets(0, 0, 0, 0);
        pnlContainer.add(buildDeleteSection(), gbc);

        // wrapper keeps every section at its own preferred height
        JPanel pnlWrapper = new JPanel(new BorderLayout());
        pnlWrapper.setBackground(BG);
        pnlWrapper.add(pnlContainer, BorderLayout.NORTH);

        JScrollPane scrollPane = new JScrollPane(pnlWrapper);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        scrollPane.setBorder(null);

        add(scrollPane, BorderLayout.CENTER);

        reloadTable(-1);
    }

    // ---------------------------------------------------------------- ADD
    private JPanel buildAddSection() {
        JPanel card = sectionCard("Add claim", "(ClaimDAO.addClaim)", "Submit a claim on a found item");

        txtItemId = new JTextField();
        txtClaimantId = new JTextField(String.valueOf(loggedInUser.getUser_id()));
        txtClaimDate = new JTextField(LocalDate.now().toString());
        txtProof = new JTextArea(3, 20);
        txtProof.setLineWrap(true);
        txtProof.setWrapStyleWord(true);
        txtProof.setFont(txtItemId.getFont());

        JPanel ids = new JPanel(new GridLayout(1, 2, 15, 0));
        ids.setOpaque(false);
        ids.add(labeled("Item ID", txtItemId));
        ids.add(labeled("Claimant user ID", txtClaimantId));

        btnSubmit = blueButton("+ Submit claim");
        btnSubmit.addActionListener(e -> submitClaim());

        lblAddResult = resultLabel();

        JPanel buttonRow = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        buttonRow.setOpaque(false);
        buttonRow.add(btnSubmit);

        JPanel body = new JPanel();
        body.setOpaque(false);
        body.setLayout(new BoxLayout(body, BoxLayout.Y_AXIS));
        body.add(left(ids));
        body.add(Box.createVerticalStrut(10));
        body.add(left(labeled("Claim date", txtClaimDate)));
        body.add(Box.createVerticalStrut(10));
        body.add(left(labeled("Proof description", new JScrollPane(txtProof))));
        body.add(Box.createVerticalStrut(12));
        body.add(left(buttonRow));
        body.add(Box.createVerticalStrut(8));
        body.add(left(lblAddResult));

        card.add(body, BorderLayout.CENTER);
        return card;
    }

    private void submitClaim() {
        try {
            int itemId = Integer.parseInt(txtItemId.getText().trim());
            int claimantId = Integer.parseInt(txtClaimantId.getText().trim());
            Date claimDate = Date.valueOf(txtClaimDate.getText().trim());
            String proof = txtProof.getText().trim();

            if (proof.isEmpty()) {
                JOptionPane.showMessageDialog(this,
                        "Please fill in the proof description.",
                        "Missing Information",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }

            Claim claim = new Claim(0, itemId, claimantId, claimDate, proof, "Pending", null);
            int newId = claimDAO.addClaim(claim);

            if (newId > 0) {
                showResult(lblAddResult, "Claim added with claim_id = " + newId, true);
                txtItemId.setText("");
                txtProof.setText("");
                reloadTable(newId);
            } else {
                showResult(lblAddResult, "Could not add the claim. Check the item ID and user ID exist.", false);
            }

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this,
                    "Item ID and Claimant user ID must be numbers.",
                    "Invalid Input",
                    JOptionPane.WARNING_MESSAGE);
        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(this,
                    "Claim date must be in the format yyyy-MM-dd.",
                    "Invalid Date",
                    JOptionPane.WARNING_MESSAGE);
        }
    }

    // ----------------------------------------------------------- RETRIEVE
    private JPanel buildRetrieveSection() {
        JPanel card = sectionCard("Retrieve claims", "(ClaimDAO.getAllClaims)",
                "All claims loaded from the CLAIMS table");

        String[] cols = {"Claim ID", "Item ID", "Claimant", "Claim date", "Status"};
        tableModel = new DefaultTableModel(cols, 0) {
            @Override
            public boolean isCellEditable(int row, int col) {
                return false; // read-only table
            }
        };

        table = new JTable(tableModel);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.setRowHeight(32);
        table.setShowVerticalLines(false);
        table.setGridColor(BORDER);
        table.setSelectionBackground(SELECTED);
        table.setSelectionForeground(Color.BLACK);
        table.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 12));
        table.getTableHeader().setReorderingAllowed(false);

        // claimant column is bold, status column is a coloured pill
        table.getColumnModel().getColumn(2).setCellRenderer(new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable t, Object v, boolean sel,
                    boolean foc, int r, int c) {
                Component comp = super.getTableCellRendererComponent(t, v, sel, foc, r, c);
                comp.setFont(new Font("SansSerif", Font.BOLD, 12));
                return comp;
            }
        });
        table.getColumnModel().getColumn(4).setCellRenderer(new StatusRenderer());

        table.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                refreshSelection();
            }
        });

        JScrollPane sp = new JScrollPane(table);
        sp.setBorder(BorderFactory.createLineBorder(BORDER));
        sp.getViewport().setBackground(Color.WHITE);
        sp.setPreferredSize(new Dimension(100, 150));

        card.add(sp, BorderLayout.CENTER);
        return card;
    }

    private void reloadTable(int selectClaimId) {
        claims = claimDAO.getAllClaims();
        names.clear();

        tableModel.setRowCount(0);
        int rowToSelect = -1;

        for (int i = 0; i < claims.size(); i++) {
            Claim c = claims.get(i);
            String name = claimDAO.getClaimantName(c.getClaimantUserId());
            names.put(c.getClaimId(), name);

            tableModel.addRow(new Object[]{
                c.getClaimId(),
                c.getItemId(),
                name,
                c.getClaimDate(),
                c.getStatus()
            });

            if (c.getClaimId() == selectClaimId) {
                rowToSelect = i;
            }
        }

        if (rowToSelect == -1 && !claims.isEmpty() && selectClaimId == -1) {
            rowToSelect = 0; // select the first claim like the screenshots
        }

        if (rowToSelect >= 0) {
            table.setRowSelectionInterval(rowToSelect, rowToSelect);
        } else {
            table.clearSelection();
        }
        refreshSelection();
    }

    // updates the update + delete sections to follow the selected table row
    private void refreshSelection() {
        int row = table.getSelectedRow();

        if (row < 0 || row >= claims.size()) {
            lblUpdateSelected.setText("Select a claim from the table above.");
            txtCurrentStatus.setText("");
            txtHandoverDate.setText("");
            btnSave.setEnabled(false);
            lblDeleteSelected.setText("Select a claim from the table above.");
            lblDeleteWarning.setText("No claim selected.");
            btnDelete.setEnabled(false);
            return;
        }

        Claim c = claims.get(row);
        String name = names.get(c.getClaimId());

        lblUpdateSelected.setText("Selected claim: #" + c.getClaimId() + " (item " + c.getItemId() + ", " + name + ")");
        txtCurrentStatus.setText(c.getStatus());
        cboNewStatus.setSelectedItem(c.getStatus());
        txtHandoverDate.setText(c.getHandoverDate() != null ? c.getHandoverDate().toString()
                : LocalDate.now().toString());
        btnSave.setEnabled(true);

        lblDeleteSelected.setText("Selected claim: #" + c.getClaimId() + " (item " + c.getItemId() + ", "
                + name + ", " + c.getStatus() + ")");
        lblDeleteWarning.setText("This will permanently remove claim #" + c.getClaimId() + " from the CLAIMS table.");
        btnDelete.setEnabled(true);
    }

    // ------------------------------------------------------------- UPDATE
    private JPanel buildUpdateSection() {
        JPanel card = sectionCard("Update claim status", "(ClaimDAO.updateClaimStatus)", null);

        lblUpdateSelected = new JLabel();
        lblUpdateSelected.setForeground(Color.GRAY);
        lblUpdateSelected.setFont(new Font("SansSerif", Font.PLAIN, 12));

        txtCurrentStatus = new JTextField();
        txtCurrentStatus.setEditable(false);

        cboNewStatus = new JComboBox<>(new String[]{"Pending", "Approved", "Rejected"});

        txtHandoverDate = new JTextField();

        btnSave = blueButton("Save status");
        btnSave.addActionListener(e -> saveStatus());

        lblUpdateResult = resultLabel();

        JPanel buttonRow = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        buttonRow.setOpaque(false);
        buttonRow.add(btnSave);

        JPanel body = new JPanel();
        body.setOpaque(false);
        body.setLayout(new BoxLayout(body, BoxLayout.Y_AXIS));
        body.add(left(lblUpdateSelected));
        body.add(Box.createVerticalStrut(10));
        body.add(left(labeled("Current status", txtCurrentStatus)));
        body.add(Box.createVerticalStrut(10));
        body.add(left(labeled("New status", cboNewStatus)));
        body.add(Box.createVerticalStrut(10));
        body.add(left(labeled("Handover date", txtHandoverDate)));
        body.add(Box.createVerticalStrut(12));
        body.add(left(buttonRow));
        body.add(Box.createVerticalStrut(8));
        body.add(left(lblUpdateResult));

        card.add(body, BorderLayout.CENTER);
        return card;
    }

    private void saveStatus() {
        int row = table.getSelectedRow();
        if (row < 0 || row >= claims.size()) {
            return;
        }

        Claim c = claims.get(row);
        String newStatus = (String) cboNewStatus.getSelectedItem();
        String handover = txtHandoverDate.getText().trim();

        try {
            Date handoverDate = handover.isEmpty() ? null : Date.valueOf(handover);

            boolean updated = claimDAO.updateClaimStatus(c.getClaimId(), newStatus, handoverDate);

            if (updated) {
                reloadTable(c.getClaimId());
                showResult(lblUpdateResult, "1 claim(s) updated to status: " + newStatus, true);
            } else {
                showResult(lblUpdateResult, "0 claim(s) updated.", false);
            }

        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(this,
                    "Handover date must be in the format yyyy-MM-dd.",
                    "Invalid Date",
                    JOptionPane.WARNING_MESSAGE);
        }
    }

    // ------------------------------------------------------------- DELETE
    private JPanel buildDeleteSection() {
        JPanel card = sectionCard("Delete claim", "(ClaimDAO.deleteClaim)", null);

        lblDeleteSelected = new JLabel();
        lblDeleteSelected.setForeground(Color.GRAY);
        lblDeleteSelected.setFont(new Font("SansSerif", Font.PLAIN, 12));

        lblDeleteWarning = new JLabel();
        lblDeleteWarning.setForeground(new Color(153, 27, 27));
        lblDeleteWarning.setFont(new Font("SansSerif", Font.PLAIN, 12));

        btnDelete = new JButton("🗑 Delete claim");
        btnDelete.setBackground(RED);
        btnDelete.setForeground(Color.WHITE);
        btnDelete.setFocusPainted(false);
        btnDelete.setFont(new Font("SansSerif", Font.BOLD, 12));
        btnDelete.addActionListener(e -> deleteSelected());

        btnCancel = new JButton("Cancel");
        btnCancel.setBackground(Color.WHITE);
        btnCancel.setForeground(LABEL);
        btnCancel.setFocusPainted(false);
        btnCancel.setFont(new Font("SansSerif", Font.PLAIN, 12));
        btnCancel.addActionListener(e -> {
            table.clearSelection();
            lblDeleteResult.setText(" ");
        });

        JPanel buttons = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 0));
        buttons.setOpaque(false);
        buttons.add(btnDelete);
        buttons.add(btnCancel);

        // red warning box
        JPanel warningBox = new JPanel();
        warningBox.setLayout(new BoxLayout(warningBox, BoxLayout.Y_AXIS));
        warningBox.setBackground(new Color(254, 226, 226));
        warningBox.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(252, 165, 165), 1),
                BorderFactory.createEmptyBorder(12, 15, 12, 15)
        ));
        warningBox.add(left(lblDeleteWarning));
        warningBox.add(Box.createVerticalStrut(10));
        warningBox.add(left(buttons));

        lblDeleteResult = resultLabel();

        JPanel body = new JPanel();
        body.setOpaque(false);
        body.setLayout(new BoxLayout(body, BoxLayout.Y_AXIS));
        body.add(left(lblDeleteSelected));
        body.add(Box.createVerticalStrut(10));
        body.add(left(warningBox));
        body.add(Box.createVerticalStrut(8));
        body.add(left(lblDeleteResult));

        card.add(body, BorderLayout.CENTER);
        return card;
    }

    private void deleteSelected() {
        int row = table.getSelectedRow();
        if (row < 0 || row >= claims.size()) {
            return;
        }

        Claim c = claims.get(row);

        if (claimDAO.deleteClaim(c.getClaimId())) {
            reloadTable(-2); // -2 = reload without auto-selecting a row
            showResult(lblDeleteResult, "1 claim(s) deleted.", true);
        } else {
            showResult(lblDeleteResult, "0 claim(s) deleted.", false);
        }
    }

    // ------------------------------------------------------------ HELPERS
    // white card with a border, same look as the cards on the Dashboard
    private JPanel sectionCard(String title, String daoRef, String subtitle) {
        JPanel card = new JPanel(new BorderLayout(0, 12));
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDER),
                BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));

        JLabel lblTitle = new JLabel(title);
        lblTitle.setFont(new Font("SansSerif", Font.BOLD, 18));

        JPanel titleRow = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        titleRow.setOpaque(false);
        titleRow.add(lblTitle);

        JPanel header = new JPanel();
        header.setOpaque(false);
        header.setLayout(new BoxLayout(header, BoxLayout.Y_AXIS));
        header.add(left(titleRow));

        if (subtitle != null) {
            JLabel sub = new JLabel(subtitle);
            sub.setForeground(Color.GRAY);
            sub.setFont(new Font("SansSerif", Font.PLAIN, 12));
            header.add(Box.createVerticalStrut(3));
            header.add(left(sub));
        }

        card.add(header, BorderLayout.NORTH);
        return card;
    }

    private JPanel labeled(String label, JComponent field) {
        JPanel p = new JPanel(new BorderLayout(0, 4));
        p.setOpaque(false);
        JLabel l = new JLabel(label);
        l.setFont(new Font("SansSerif", Font.PLAIN, 12));
        l.setForeground(LABEL);
        p.add(l, BorderLayout.NORTH);
        p.add(field, BorderLayout.CENTER);
        return p;
    }

    // stretches a component across the card and keeps it left aligned in a BoxLayout
    private JComponent left(JComponent c) {
        c.setAlignmentX(Component.LEFT_ALIGNMENT);
        if (!(c instanceof JLabel)) {
            c.setMaximumSize(new Dimension(Integer.MAX_VALUE, c.getPreferredSize().height));
        }
        return c;
    }

    private JButton blueButton(String text) {
        JButton b = new JButton(text);
        b.setBackground(BLUE);
        b.setForeground(Color.WHITE);
        b.setFocusPainted(false);
        b.setFont(new Font("SansSerif", Font.BOLD, 12));
        return b;
    }

    private JLabel resultLabel() {
        JLabel l = new JLabel(" ");
        l.setFont(new Font("SansSerif", Font.PLAIN, 12));
        return l;
    }

    private void showResult(JLabel label, String text, boolean success) {
        label.setForeground(success ? new Color(22, 101, 52) : RED);
        label.setText(text);
    }

    // coloured status pill used in the table
    private class StatusRenderer extends DefaultTableCellRenderer {

        @Override
        public Component getTableCellRendererComponent(JTable t, Object v, boolean sel,
                boolean foc, int r, int c) {
            String status = v == null ? "" : v.toString();

            JPanel wrap = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 5));
            wrap.setBackground(sel ? SELECTED : Color.WHITE);

            JLabel pill = new JLabel(status);
            pill.setOpaque(true);
            pill.setFont(new Font("SansSerif", Font.PLAIN, 11));
            pill.setBorder(BorderFactory.createEmptyBorder(2, 8, 2, 8));

            if (status.equalsIgnoreCase("Approved")) {
                pill.setBackground(new Color(220, 252, 231));
                pill.setForeground(new Color(22, 101, 52));
            } else if (status.equalsIgnoreCase("Rejected")) {
                pill.setBackground(new Color(254, 226, 226));
                pill.setForeground(new Color(153, 27, 27));
            } else {
                pill.setBackground(new Color(254, 243, 199));
                pill.setForeground(new Color(146, 64, 14));
            }

            wrap.add(pill);
            return wrap;
        }
    }

} // end of class
