package za.ac.cput.lostfoundapp.gui;

import javax.swing.*;
import java.awt.*;
import javax.swing.table.DefaultTableModel;

public class MyItemsPanel extends JPanel {
    private JTable table;
    private DefaultTableModel model;

    public MyItemsPanel(){
        setLayout(new BorderLayout());
        setBackground(new Color(248,250,252));

        JLabel title = new JLabel("My Items - Manage items you've reported");
        title.setFont(new Font("SansSerif", Font.BOLD, 18));
        title.setBorder(BorderFactory.createEmptyBorder(15,15,15,15));
        add(title, BorderLayout.NORTH);
        
        String[] cols = {"item_id","item_type","category","brand_model","location","status"};
        Object[][] data = {
            {1,"LOST","Electronics","Black HP Laptop","Library","PENDING"},
            {2,"FOUND","ID Card","Student Card - T Molefe","Admin Block","FOUND"}
        };
        
        model = new DefaultTableModel(data, cols){
            @Override public boolean isCellEditable(int row, int col){ return false; } // make table read-only
        };
        table = new JTable(model);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        add(new JScrollPane(table), BorderLayout.CENTER);

        // BOTTOM ACTION BAR
        JPanel actions = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        actions.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        JButton btnRemove = new JButton("Remove Selected Report");
        btnRemove.setBackground(new Color(220, 38, 38));
        btnRemove.setForeground(Color.WHITE);
        btnRemove.setFocusPainted(false);
        btnRemove.setFont(new Font("SansSerif", Font.BOLD, 12));
        actions.add(btnRemove);
        add(actions, BorderLayout.SOUTH);

        btnRemove.addActionListener(e -> removeSelectedItem());
    }

    private void removeSelectedItem(){
        int selectedRow = table.getSelectedRow();
        if(selectedRow == -1){
            JOptionPane.showMessageDialog(this, 
                "Please select an item to remove.", 
                "No selection", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String itemName = model.getValueAt(selectedRow, 3).toString();
        String itemType = model.getValueAt(selectedRow, 1).toString();

        int confirm = JOptionPane.showConfirmDialog(this,
                "Are you sure you want to remove this " + itemType + " report?\n\n" + itemName + "\n\nThis cannot be undone.",
                "Confirm Removal",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.ERROR_MESSAGE);

        if(confirm == JOptionPane.YES_OPTION){
            // In future, you would also delete from database here
            // e.g. DatabaseHelper.deleteItem(itemId);
            
            model.removeRow(selectedRow);
            JOptionPane.showMessageDialog(this, 
                "Report removed successfully.", 
                "Removed", JOptionPane.INFORMATION_MESSAGE);
        }
    }
}