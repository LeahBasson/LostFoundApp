package za.ac.cput.lostfoundapp;

import javax.swing.*;
import java.awt.*;

public class ReportItemPanel extends JPanel {
    private String type;
    public ReportItemPanel(String type){
        this.type = type;
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);
        initForm();
    }
    private void initForm(){
        JPanel form = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8,10,8,10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx=0; gbc.gridy=0; gbc.gridwidth=2;
        JLabel header = new JLabel(type.equals("LOST") ? "Report a Lost Item" : "Report a Found Item");
        header.setFont(new Font("SansSerif", Font.BOLD, 20));
        form.add(header, gbc);
        
        gbc.gridwidth=1; gbc.gridy++;
        form.add(new JLabel("Category *"), gbc);
        gbc.gridx=1;
        JComboBox<String> cat = new JComboBox<>(new String[]{"Select category","Electronics","Books","Clothing","ID Card","Other"});
        form.add(cat, gbc);
        
        gbc.gridx=0; gbc.gridy++;
        form.add(new JLabel("Item Title *"), gbc);
        gbc.gridx=1;
        form.add(new JTextField(20), gbc);
        
        gbc.gridx=0; gbc.gridy++;
        form.add(new JLabel("Description *"), gbc);
        gbc.gridx=1;
        form.add(new JTextArea(3,20), gbc);
        
        gbc.gridx=0; gbc.gridy++;
        form.add(new JLabel("Color"), gbc);
        gbc.gridx=1;
        form.add(new JTextField(15), gbc);
        
        gbc.gridx=0; gbc.gridy++;
        form.add(new JLabel("Location *"), gbc);
        gbc.gridx=1;
        JComboBox<String> loc = new JComboBox<>(new String[]{"Select location","Bellville Campus","District Six","Mowbray","Granger Bay"});
        form.add(loc, gbc);
        
        gbc.gridx=0; gbc.gridy++;
        form.add(new JLabel("Date/Time Event"), gbc);
        gbc.gridx=1;
        form.add(new JTextField("yyyy/mm/dd --:--"), gbc);
        
        gbc.gridx=0; gbc.gridy++;
        form.add(new JLabel("Visibility"), gbc);
        gbc.gridx=1;
        JPanel vis = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JRadioButton r1 = new JRadioButton("Students Only");
        JRadioButton r2 = new JRadioButton("Staff Only");
        JRadioButton r3 = new JRadioButton("Everyone", true);
        ButtonGroup bg = new ButtonGroup(); bg.add(r1); bg.add(r2); bg.add(r3);
        vis.add(r1); vis.add(r2); vis.add(r3);
        form.add(vis, gbc);
        
        gbc.gridx=0; gbc.gridy++; gbc.gridwidth=2;
        JPanel btns = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton submit = new JButton(type.equals("LOST")?"Submit Report":"Submit Found Report");
        submit.setBackground(new Color(37,99,235)); submit.setForeground(Color.WHITE);
        JButton cancel = new JButton("Cancel");
        btns.add(cancel); btns.add(submit);
        form.add(btns, gbc);
        
        add(new JScrollPane(form), BorderLayout.CENTER);
        
        submit.addActionListener(e -> 
            JOptionPane.showMessageDialog(this, type+" Item Reported! (Will connect to DB in part B)")
        );
    }
}