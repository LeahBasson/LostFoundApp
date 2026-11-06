package za.ac.cput.lostfoundapp.gui;

import za.ac.cput.lostfoundapp.dao.ItemDAO;
import za.ac.cput.lostfoundapp.domain.Item;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class MyItemsPanel extends JPanel {
    private JTable table;
    private DefaultTableModel model;
    private int currentUserId = 1; // default, will be overridden
    private ItemDAO itemDAO = new ItemDAO();

    // Constructor for T4 - MainFrame must pass userId
    public MyItemsPanel(int currentUserId) {
        this.currentUserId = currentUserId;
        initUI();
        loadMyItems();
    }

    // No-arg constructor for backward compatibility (designer)
    public MyItemsPanel() {
        initUI();
        // show sample if no DB
        loadMyItems();
    }

    private void initUI() {
        setLayout(new BorderLayout());
        setBackground(new Color(248, 250, 252));

        JLabel title = new JLabel("My Items - Manage items you've reported");
        title.setFont(new Font("SansSerif", Font.BOLD, 18));
        title.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        add(title, BorderLayout.NORTH);

        String[] cols = {"item_id", "item_type", "category", "brand_model", "location", "status"};
        model = new DefaultTableModel(cols, 0) {
            @Override public boolean isCellEditable(int row, int col) { return false; }
        };
        table = new JTable(model);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        add(new JScrollPane(table), BorderLayout.CENTER);

        // BOTTOM ACTION BAR - T4 Full CRUD
        JPanel actions = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        actions.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JButton btnAdd = new JButton("Add New Item");
        btnAdd.setBackground(new Color(34, 197, 94));
        btnAdd.setForeground(Color.WHITE);
        btnAdd.setFocusPainted(false);
        btnAdd.setFont(new Font("SansSerif", Font.BOLD, 12));

        JButton btnRefresh = new JButton("Refresh");
        btnRefresh.setBackground(new Color(59, 130, 246));
        btnRefresh.setForeground(Color.WHITE);
        btnRefresh.setFocusPainted(false);

        JButton btnEdit = new JButton("Edit Status");
        btnEdit.setBackground(new Color(249, 115, 22));
        btnEdit.setForeground(Color.WHITE);
        btnEdit.setFocusPainted(false);

        JButton btnRemove = new JButton("Remove Selected Report");
        btnRemove.setBackground(new Color(220, 38, 38));
        btnRemove.setForeground(Color.WHITE);
        btnRemove.setFocusPainted(false);
        btnRemove.setFont(new Font("SansSerif", Font.BOLD, 12));

        actions.add(btnAdd);
        actions.add(btnRefresh);
        actions.add(btnEdit);
        actions.add(btnRemove);
        add(actions, BorderLayout.SOUTH);

        btnAdd.addActionListener(e -> showAddDialog());
        btnRefresh.addActionListener(e -> loadMyItems());
        btnEdit.addActionListener(e -> showEditStatusDialog());
        btnRemove.addActionListener(e -> removeSelectedItem());
    }

    private void loadMyItems() {
        model.setRowCount(0);
        try {
            List<Item> items = itemDAO.getItemsByUserId(currentUserId);
            if (items.isEmpty()) {
                // Fallback sample for demo if DB empty
                model.addRow(new Object[]{1, "LOST", "Electronics", "Black 【entity-HP¦canonical_name=HP】 Laptop", "Library", "PENDING"});
            } else {
                for (Item it : items) {
                    model.addRow(new Object[]{
                        it.getItemId(), it.getItemType(), it.getCategory(),
                        it.getBrandModel(), it.getLocation(), it.getStatus()
                    });
                }
            }
        } catch (Exception ex) {
            // DB not running - show sample
            model.addRow(new Object[]{1, "LOST", "Electronics", "Black 【entity-HP¦canonical_name=HP】 Laptop", "Library", "PENDING"});
            model.addRow(new Object[]{2, "FOUND", "ID Card", "Student Card - T Molefe", "Admin Block", "FOUND"});
        }
    }

    private void showAddDialog() {
        JTextField txtType = new JTextField("LOST");
        JTextField txtCategory = new JTextField();
        JTextField txtBrand = new JTextField();
        JTextField txtColour = new JTextField();
        JTextField txtLocation = new JTextField();
        JTextField txtSerial = new JTextField();
        JTextArea txtDesc = new JTextArea(3, 20);

        JPanel panel = new JPanel(new GridLayout(0, 2, 5, 5));
        panel.add(new JLabel("Item Type (LOST/FOUND):")); panel.add(txtType);
        panel.add(new JLabel("Category:")); panel.add(txtCategory);
        panel.add(new JLabel("Brand/Model:")); panel.add(txtBrand);
        panel.add(new JLabel("Colour:")); panel.add(txtColour);
        panel.add(new JLabel("Location:")); panel.add(txtLocation);
        panel.add(new JLabel("Serial:")); panel.add(txtSerial);
        panel.add(new JLabel("Description:")); panel.add(new JScrollPane(txtDesc));

        int result = JOptionPane.showConfirmDialog(this, panel, "Report New Item", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (result == JOptionPane.OK_OPTION) {
            Item item = new Item(
                txtType.getText().toUpperCase(),
                txtCategory.getText(),
                txtBrand.getText(),
                txtColour.getText(),
                txtDesc.getText(),
                txtLocation.getText(),
                "PENDING",
                currentUserId
            );
            item.setSerialNumber(txtSerial.getText());
            if (itemDAO.addItem(item)) {
                JOptionPane.showMessageDialog(this, "Item added successfully!");
                loadMyItems();
            } else {
                JOptionPane.showMessageDialog(this, "Failed - Check DB connection. Sample added locally.", "DB Error", JOptionPane.WARNING_MESSAGE);
                model.addRow(new Object[]{model.getRowCount()+1, item.getItemType(), item.getCategory(), item.getBrandModel(), item.getLocation(), item.getStatus()});
            }
        }
    }

    private void showEditStatusDialog() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select an item to edit.", "No selection", JOptionPane.WARNING_MESSAGE);
            return;
        }
        int itemId = Integer.parseInt(model.getValueAt(selectedRow, 0).toString());
        String[] statuses = {"PENDING", "FOUND", "CLAIMED", "COLLECTED", "EXPIRED", "RESOLVED"};
        String newStatus = (String) JOptionPane.showInputDialog(this, "Select new status:", "Edit Status", JOptionPane.QUESTION_MESSAGE, null, statuses, model.getValueAt(selectedRow, 5));
        if (newStatus != null) {
            if (itemDAO.updateStatus(itemId, newStatus, currentUserId)) {
                model.setValueAt(newStatus, selectedRow, 5);
                JOptionPane.showMessageDialog(this, "Status updated to " + newStatus);
            } else {
                model.setValueAt(newStatus, selectedRow, 5);
                JOptionPane.showMessageDialog(this, "Updated locally (DB offline mode).", "Info", JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }

    private void removeSelectedItem() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select an item to remove.", "No selection", JOptionPane.WARNING_MESSAGE);
            return;
        }
        int itemId = Integer.parseInt(model.getValueAt(selectedRow, 0).toString());
        String itemName = model.getValueAt(selectedRow, 3).toString();
        String itemType = model.getValueAt(selectedRow, 1).toString();

        int confirm = JOptionPane.showConfirmDialog(this,
                "Are you sure you want to remove this " + itemType + " report?\n\n" + itemName + "\n\nThis cannot be undone.",
                "Confirm Removal", JOptionPane.YES_NO_OPTION, JOptionPane.ERROR_MESSAGE);

        if (confirm == JOptionPane.YES_OPTION) {
            boolean deleted = itemDAO.deleteItem(itemId, currentUserId);
            model.removeRow(selectedRow);
            JOptionPane.showMessageDialog(this, deleted ? "Report removed from DB." : "Report removed (local).");
        }
    }
}