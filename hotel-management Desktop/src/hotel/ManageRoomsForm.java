package hotel;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.*;

public class ManageRoomsForm extends JFrame {
    
    private JPanel contentPane;
    private JTable table;
    private JTextField txtRoomNo, txtRoomType, txtBedType, txtPrice;
    private JButton btnAdd, btnUpdate, btnDelete, btnRefresh, btnBack;
    private DefaultTableModel tableModel;

    public ManageRoomsForm() {
        setTitle("Hotel RAAR - Manage Rooms");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setResizable(true);
        
        initializeUI();
        loadRoomsData();
    }
    
    private void initializeUI() {
        contentPane = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
                
                GradientPaint gp = new GradientPaint(0, 0, new Color(250, 248, 240), 0, getHeight(), new Color(245, 238, 220));
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        contentPane.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        setContentPane(contentPane);
        contentPane.setLayout(null);
        
        // Header Panel
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(new Color(34, 139, 34));
        headerPanel.setBounds(0, 0, 1920, 100);
        headerPanel.setLayout(null);
        contentPane.add(headerPanel);
        
        JLabel lblTitle = new JLabel("ROOM MANAGEMENT SYSTEM");
        lblTitle.setFont(new Font("Serif", Font.BOLD, 36));
        lblTitle.setForeground(Color.WHITE);
        lblTitle.setBounds(50, 20, 600, 45);
        headerPanel.add(lblTitle);
        
        JLabel lblSubtitle = new JLabel("Add, Update, and Delete Hotel Rooms");
        lblSubtitle.setFont(new Font("Arial", Font.ITALIC, 18));
        lblSubtitle.setForeground(new Color(240, 255, 240));
        lblSubtitle.setBounds(50, 65, 400, 25);
        headerPanel.add(lblSubtitle);
        
        // Form Panel for Room Details
        JPanel formPanel = new JPanel();
        formPanel.setBackground(Color.WHITE);
        formPanel.setBounds(50, 130, 500, 300);
        formPanel.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(new Color(34, 139, 34), 2),
            "Room Details",
            javax.swing.border.TitledBorder.LEFT,
            javax.swing.border.TitledBorder.TOP,
            new Font("Arial", Font.BOLD, 16),
            new Color(34, 139, 34)
        ));
        formPanel.setLayout(null);
        contentPane.add(formPanel);
        
        // Form Fields
        JLabel lblRoomNo = new JLabel("Room Number:");
        lblRoomNo.setFont(new Font("Arial", Font.BOLD, 14));
        lblRoomNo.setBounds(30, 40, 120, 25);
        formPanel.add(lblRoomNo);
        
        txtRoomNo = new JTextField();
        txtRoomNo.setBounds(160, 40, 300, 30);
        txtRoomNo.setFont(new Font("Arial", Font.PLAIN, 14));
        formPanel.add(txtRoomNo);
        
        JLabel lblRoomType = new JLabel("Room Type:");
        lblRoomType.setFont(new Font("Arial", Font.BOLD, 14));
        lblRoomType.setBounds(30, 90, 120, 25);
        formPanel.add(lblRoomType);
        
        txtRoomType = new JTextField();
        txtRoomType.setBounds(160, 90, 300, 30);
        txtRoomType.setFont(new Font("Arial", Font.PLAIN, 14));
        formPanel.add(txtRoomType);
        
        JLabel lblBedType = new JLabel("Bed Type:");
        lblBedType.setFont(new Font("Arial", Font.BOLD, 14));
        lblBedType.setBounds(30, 140, 120, 25);
        formPanel.add(lblBedType);
        
        txtBedType = new JTextField();
        txtBedType.setBounds(160, 140, 300, 30);
        txtBedType.setFont(new Font("Arial", Font.PLAIN, 14));
        formPanel.add(txtBedType);
        
        JLabel lblPrice = new JLabel("Price (₹):");
        lblPrice.setFont(new Font("Arial", Font.BOLD, 14));
        lblPrice.setBounds(30, 190, 120, 25);
        formPanel.add(lblPrice);
        
        txtPrice = new JTextField();
        txtPrice.setBounds(160, 190, 300, 30);
        txtPrice.setFont(new Font("Arial", Font.PLAIN, 14));
        formPanel.add(txtPrice);
        
        // Button Panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(Color.WHITE);
        buttonPanel.setBounds(50, 450, 500, 80);
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 15, 20));
        contentPane.add(buttonPanel);
        
        btnAdd = createStyledButton("ADD ROOM", new Color(34, 139, 34));
        btnUpdate = createStyledButton(" UPDATE", new Color(255, 140, 0));
        btnDelete = createStyledButton("️ DELETE", new Color(220, 20, 60));
        btnRefresh = createStyledButton(" REFRESH", new Color(70, 130, 180));
        btnBack = createStyledButton("BACK", new Color(128, 128, 128));
        
        buttonPanel.add(btnAdd);
        buttonPanel.add(btnUpdate);
        buttonPanel.add(btnDelete);
        buttonPanel.add(btnRefresh);
        buttonPanel.add(btnBack);
        
        // Table Panel
        JPanel tablePanel = new JPanel();
        tablePanel.setBackground(Color.WHITE);
        tablePanel.setBounds(580, 130, 1280, 700);
        tablePanel.setBorder(BorderFactory.createTitledBorder("Room Database"));
        tablePanel.setLayout(new BorderLayout(10, 10));
        contentPane.add(tablePanel);
        
        String[] columnNames = {"Room No", "Room Type", "Bed Type", "Price (₹)", "Status"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        table = new JTable(tableModel);
        table.setFont(new Font("Arial", Font.PLAIN, 14));
        table.setRowHeight(30);
        table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14));
        table.getTableHeader().setBackground(new Color(34, 139, 34));
        table.getTableHeader().setForeground(Color.WHITE);
        
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int selectedRow = table.getSelectedRow();
                if (selectedRow >= 0) {
                    txtRoomNo.setText(table.getValueAt(selectedRow, 0).toString());
                    txtRoomType.setText(table.getValueAt(selectedRow, 1).toString());
                    txtBedType.setText(table.getValueAt(selectedRow, 2).toString());
                    String priceStr = table.getValueAt(selectedRow, 3).toString().replace("₹", "");
                    txtPrice.setText(priceStr);
                    txtRoomNo.setEditable(false);
                }
            }
        });
        
        JScrollPane scrollPane = new JScrollPane(table);
        tablePanel.add(scrollPane, BorderLayout.CENTER);
        
        // Button Actions
        btnAdd.addActionListener(e -> addRoom());
        btnUpdate.addActionListener(e -> updateRoom());
        btnDelete.addActionListener(e -> deleteRoom());
        btnRefresh.addActionListener(e -> {
            loadRoomsData();
            clearFields();
        });
        btnBack.addActionListener(e -> dispose());
    }
    
    private JButton createStyledButton(String text, Color bgColor) {
        JButton button = new JButton(text);
        button.setPreferredSize(new Dimension(140, 40));
        button.setFont(new Font("Arial", Font.BOLD, 12));
        button.setBackground(bgColor);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createRaisedBevelBorder());
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(bgColor.brighter());
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(bgColor);
            }
        });
        
        return button;
    }
    
    private void loadRoomsData() {
        try {
            tableModel.setRowCount(0);
            
            GetConnection connect = new GetConnection();
            Connection conn = connect.getConnection();
            
            // Check which table name you have - 'room' or 'rooms'
            String query = "SELECT roomNo, roomType, bedType, price FROM room ORDER BY roomNo";
            PreparedStatement ps = conn.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            
            while (rs.next()) {
                Object[] row = {
                    rs.getString("roomNo"),
                    rs.getString("roomType"),
                    rs.getString("bedType"),
                    "₹" + rs.getString("price"),
                    "AVAILABLE"  // You can add logic to check booking status
                };
                tableModel.addRow(row);
            }
            
            rs.close();
            ps.close();
            conn.close();
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, 
                "Error loading rooms: " + e.getMessage(), 
                "Database Error", 
                JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }
    
    private void addRoom() {
        if (!validateFields()) return;
        
        try {
            GetConnection connect = new GetConnection();
            Connection conn = connect.getConnection();
            
            String query = "INSERT INTO room (roomNo, roomType, bedType, price) VALUES (?, ?, ?, ?)";
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, txtRoomNo.getText().trim());
            ps.setString(2, txtRoomType.getText().trim());
            ps.setString(3, txtBedType.getText().trim());
            ps.setString(4, txtPrice.getText().trim());
            
            int result = ps.executeUpdate();
            
            if (result > 0) {
                JOptionPane.showMessageDialog(this, "Room added successfully!");
                loadRoomsData();
                clearFields();
            }
            
            ps.close();
            conn.close();
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error adding room: " + e.getMessage());
        }
    }
    
    private void updateRoom() {
        if (!validateFields()) return;
        
        int selectedRow = table.getSelectedRow();
        if (selectedRow < 0) {
            JOptionPane.showMessageDialog(this, "Please select a room to update.");
            return;
        }
        
        try {
            GetConnection connect = new GetConnection();
            Connection conn = connect.getConnection();
            
            String query = "UPDATE room SET roomType = ?, bedType = ?, price = ? WHERE roomNo = ?";
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, txtRoomType.getText().trim());
            ps.setString(2, txtBedType.getText().trim());
            ps.setString(3, txtPrice.getText().trim());
            ps.setString(4, txtRoomNo.getText().trim());
            
            int result = ps.executeUpdate();
            
            if (result > 0) {
                JOptionPane.showMessageDialog(this, "Room updated successfully!");
                loadRoomsData();
                clearFields();
            }
            
            ps.close();
            conn.close();
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error updating room: " + e.getMessage());
        }
    }
    
    private void deleteRoom() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow < 0) {
            JOptionPane.showMessageDialog(this, "Please select a room to delete.");
            return;
        }
        
        String roomNo = table.getValueAt(selectedRow, 0).toString();
        
        int option = JOptionPane.showConfirmDialog(this, 
            "Are you sure you want to delete Room " + roomNo + "?");
        
        if (option == JOptionPane.YES_OPTION) {
            try {
                GetConnection connect = new GetConnection();
                Connection conn = connect.getConnection();
                
                String query = "DELETE FROM room WHERE roomNo = ?";
                PreparedStatement ps = conn.prepareStatement(query);
                ps.setString(1, roomNo);
                
                int result = ps.executeUpdate();
                
                if (result > 0) {
                    JOptionPane.showMessageDialog(this, "Room deleted successfully!");
                    loadRoomsData();
                    clearFields();
                }
                
                ps.close();
                conn.close();
                
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Error deleting room: " + e.getMessage());
            }
        }
    }
    
    private boolean validateFields() {
        if (txtRoomNo.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Room Number cannot be empty.");
            return false;
        }
        if (txtRoomType.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Room Type cannot be empty.");
            return false;
        }
        if (txtBedType.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Bed Type cannot be empty.");
            return false;
        }
        if (txtPrice.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Price cannot be empty.");
            return false;
        }
        return true;
    }
    
    private void clearFields() {
        txtRoomNo.setText("");
        txtRoomType.setText("");
        txtBedType.setText("");
        txtPrice.setText("");
        txtRoomNo.setEditable(true);
        table.clearSelection();
    }
}
