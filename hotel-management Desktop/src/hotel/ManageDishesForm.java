package hotel;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.*;

public class ManageDishesForm extends JFrame {
    
    private JPanel contentPane;
    private JTable table;
    private JTextField txtDishName, txtDishPrice;
    private JComboBox<String> comboDishType;
    private JButton btnAdd, btnUpdate, btnDelete, btnRefresh, btnBack, btnBulkAdd;
    private DefaultTableModel tableModel;

    public ManageDishesForm() {
        setTitle("Hotel RAAR - Manage Restaurant Menu");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setResizable(true);
        
        initializeUI();
        loadDishesData();
    }
    
    private void initializeUI() {
        // Main panel with professional gradient
        contentPane = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
                
                GradientPaint gp = new GradientPaint(0, 0, new Color(255, 250, 240), 0, getHeight(), new Color(250, 240, 230));
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        contentPane.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        setContentPane(contentPane);
        contentPane.setLayout(null);
        
        // Header Panel
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(new Color(255, 140, 0));
        headerPanel.setBounds(0, 0, 1920, 100);
        headerPanel.setLayout(null);
        contentPane.add(headerPanel);
        
        JLabel lblTitle = new JLabel(" RESTAURANT MENU MANAGEMENT");
        lblTitle.setFont(new Font("Serif", Font.BOLD, 36));
        lblTitle.setForeground(Color.WHITE);
        lblTitle.setBounds(50, 20, 700, 45);
        headerPanel.add(lblTitle);
        
        JLabel lblSubtitle = new JLabel("Add, Update, Delete & Manage Restaurant Items");
        lblSubtitle.setFont(new Font("Arial", Font.ITALIC, 18));
        lblSubtitle.setForeground(new Color(255, 248, 220));
        lblSubtitle.setBounds(50, 65, 500, 25);
        headerPanel.add(lblSubtitle);
        
        // Form Panel
        JPanel formPanel = new JPanel();
        formPanel.setBackground(Color.WHITE);
        formPanel.setBounds(50, 130, 500, 300);
        formPanel.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(new Color(255, 140, 0), 2),
            " Item Details",
            javax.swing.border.TitledBorder.LEFT,
            javax.swing.border.TitledBorder.TOP,
            new Font("Arial", Font.BOLD, 16),
            new Color(255, 140, 0)
        ));
        formPanel.setLayout(null);
        contentPane.add(formPanel);
        
        // Form Fields
        JLabel lblDishName = new JLabel("Item Name:");
        lblDishName.setFont(new Font("Arial", Font.BOLD, 14));
        lblDishName.setForeground(new Color(255, 140, 0));
        lblDishName.setBounds(30, 40, 120, 25);
        formPanel.add(lblDishName);
        
        txtDishName = new JTextField();
        txtDishName.setBounds(160, 40, 300, 35);
        txtDishName.setFont(new Font("Arial", Font.PLAIN, 14));
        txtDishName.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(255, 140, 0), 1),
            BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
        formPanel.add(txtDishName);
        
        JLabel lblDishType = new JLabel("Item Type:");
        lblDishType.setFont(new Font("Arial", Font.BOLD, 14));
        lblDishType.setForeground(new Color(255, 140, 0));
        lblDishType.setBounds(30, 90, 120, 25);
        formPanel.add(lblDishType);
        
        comboDishType = new JComboBox<>(new String[]{"MEAL", "DRINK", "APPETIZER", "DESSERT", "SNACK"});
        comboDishType.setBounds(160, 90, 300, 35);
        comboDishType.setFont(new Font("Arial", Font.PLAIN, 14));
        comboDishType.setBackground(Color.WHITE);
        formPanel.add(comboDishType);
        
        JLabel lblPrice = new JLabel("Price (₹):");
        lblPrice.setFont(new Font("Arial", Font.BOLD, 14));
        lblPrice.setForeground(new Color(255, 140, 0));
        lblPrice.setBounds(30, 140, 120, 25);
        formPanel.add(lblPrice);
        
        txtDishPrice = new JTextField();
        txtDishPrice.setBounds(160, 140, 300, 35);
        txtDishPrice.setFont(new Font("Arial", Font.PLAIN, 14));
        txtDishPrice.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(255, 140, 0), 1),
            BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
        formPanel.add(txtDishPrice);
        
        // Quick Add Buttons
        JLabel lblSuggestions = new JLabel("Quick Add:");
        lblSuggestions.setFont(new Font("Arial", Font.BOLD, 12));
        lblSuggestions.setForeground(new Color(255, 140, 0));
        lblSuggestions.setBounds(30, 190, 100, 20);
        formPanel.add(lblSuggestions);
        
        JButton btnQuickMeal = new JButton("+ Meal");
        btnQuickMeal.setBounds(30, 215, 80, 30);
        btnQuickMeal.setFont(new Font("Arial", Font.PLAIN, 11));
        btnQuickMeal.setBackground(new Color(34, 139, 34));
        btnQuickMeal.setForeground(Color.WHITE);
        btnQuickMeal.addActionListener(e -> {
            comboDishType.setSelectedItem("MEAL");
            txtDishName.requestFocus();
        });
        formPanel.add(btnQuickMeal);
        
        JButton btnQuickDrink = new JButton("+ Drink");
        btnQuickDrink.setBounds(120, 215, 80, 30);
        btnQuickDrink.setFont(new Font("Arial", Font.PLAIN, 11));
        btnQuickDrink.setBackground(new Color(70, 130, 180));
        btnQuickDrink.setForeground(Color.WHITE);
        btnQuickDrink.addActionListener(e -> {
            comboDishType.setSelectedItem("DRINK");
            txtDishName.requestFocus();
        });
        formPanel.add(btnQuickDrink);
        
        // Button Panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(Color.WHITE);
        buttonPanel.setBounds(50, 450, 500, 100);
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 15));
        contentPane.add(buttonPanel);
        
        btnAdd = createStyledButton("ADD ITEM", new Color(34, 139, 34));
        btnUpdate = createStyledButton("UPDATE", new Color(255, 140, 0));
        btnDelete = createStyledButton(" DELETE", new Color(220, 20, 60));
        btnBulkAdd = createStyledButton(" BULK ADD", new Color(138, 43, 226));
        btnRefresh = createStyledButton("REFRESH", new Color(70, 130, 180));
        btnBack = createStyledButton("BACK", new Color(128, 128, 128));
        
        buttonPanel.add(btnAdd);
        buttonPanel.add(btnUpdate);
        buttonPanel.add(btnDelete);
        buttonPanel.add(btnBulkAdd);
        buttonPanel.add(btnRefresh);
        buttonPanel.add(btnBack);
        
        // Table Panel
        JPanel tablePanel = new JPanel();
        tablePanel.setBackground(Color.WHITE);
        tablePanel.setBounds(580, 130, 1280, 700);
        tablePanel.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(new Color(255, 140, 0), 2),
            "Menu Database",
            javax.swing.border.TitledBorder.LEFT,
            javax.swing.border.TitledBorder.TOP,
            new Font("Arial", Font.BOLD, 16),
            new Color(255, 140, 0)
        ));
        tablePanel.setLayout(new BorderLayout(10, 10));
        contentPane.add(tablePanel);
        
        // Table Setup
        String[] columnNames = {"Item No", "Item Name", "Type", "Price (₹)"};
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
        table.getTableHeader().setBackground(new Color(255, 140, 0));
        table.getTableHeader().setForeground(Color.WHITE);
        table.setSelectionBackground(new Color(255, 228, 181));
        table.setGridColor(new Color(255, 140, 0));
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        
        // Table click listener
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int selectedRow = table.getSelectedRow();
                if (selectedRow >= 0) {
                    txtDishName.setText(table.getValueAt(selectedRow, 1).toString());
                    comboDishType.setSelectedItem(table.getValueAt(selectedRow, 2).toString());
                    String priceStr = table.getValueAt(selectedRow, 3).toString().replace("₹", "");
                    txtDishPrice.setText(priceStr);
                }
            }
        });
        
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.getViewport().setBackground(Color.WHITE);
        tablePanel.add(scrollPane, BorderLayout.CENTER);
        
        // Button Actions
        btnAdd.addActionListener(e -> addDish());
        btnUpdate.addActionListener(e -> updateDish());
        btnDelete.addActionListener(e -> deleteDish());
        btnBulkAdd.addActionListener(e -> showBulkAddDialog());
        btnRefresh.addActionListener(e -> {
            loadDishesData();
            clearFields();
        });
        btnBack.addActionListener(e -> dispose());
    }
    
    // ✅ BULK ADD FEATURE
    private void showBulkAddDialog() {
        JDialog bulkDialog = new JDialog(this, "Bulk Add Menu Items", true);
        bulkDialog.setSize(600, 400);
        bulkDialog.setLocationRelativeTo(this);
        
        JPanel panel = new JPanel(new BorderLayout());
        
        JTextArea textArea = new JTextArea();
        textArea.setText("Enter items in format: Name,Type,Price (one per line)\n\n" +
                        "Examples:\n" +
                        "Butter Chicken,MEAL,280\n" +
                        "Fresh Juice,DRINK,50\n" +
                        "Chocolate Cake,DESSERT,120");
        textArea.setFont(new Font("Courier New", Font.PLAIN, 12));
        
        JScrollPane scrollPane = new JScrollPane(textArea);
        panel.add(scrollPane, BorderLayout.CENTER);
        
        JPanel buttonPanel = new JPanel();
        JButton btnAdd = new JButton("Add All Items");
        btnAdd.addActionListener(e -> {
            processBulkAdd(textArea.getText());
            bulkDialog.dispose();
        });
        JButton btnCancel = new JButton("Cancel");
        btnCancel.addActionListener(e -> bulkDialog.dispose());
        
        buttonPanel.add(btnAdd);
        buttonPanel.add(btnCancel);
        panel.add(buttonPanel, BorderLayout.SOUTH);
        
        bulkDialog.add(panel);
        bulkDialog.setVisible(true);
    }
    
    private void processBulkAdd(String text) {
        String[] lines = text.split("\n");
        int successCount = 0;
        int errorCount = 0;
        
        for (String line : lines) {
            line = line.trim();
            if (line.isEmpty() || line.startsWith("Enter") || line.startsWith("Examples")) continue;
            
            String[] parts = line.split(",");
            if (parts.length == 3) {
                try {
                    String name = parts[0].trim();
                    String type = parts[1].trim();
                    String price = parts[2].trim();
                    
                    if (addDishToDatabase(name, type, price)) {
                        successCount++;
                    } else {
                        errorCount++;
                    }
                } catch (Exception e) {
                    errorCount++;
                }
            }
        }
        
        JOptionPane.showMessageDialog(this, 
            "✅ Bulk Add Complete!\n" +
            "Successfully added: " + successCount + " items\n" +
            "Errors: " + errorCount + " items");
        
        loadDishesData();
    }
    
    private boolean addDishToDatabase(String name, String type, String price) {
        Connection conn = null;
        PreparedStatement ps = null;
        
        try {
            GetConnection connect = new GetConnection();
            conn = connect.getConnection();
            
            // ✅ FIXED: Transaction management for data consistency
            conn.setAutoCommit(false);
            
            String query = "INSERT INTO restaurant (itemName, Type, Price) VALUES (?, ?, ?)";
            ps = conn.prepareStatement(query);
            ps.setString(1, name);
            ps.setString(2, type);
            ps.setInt(3, Integer.parseInt(price));
            
            int result = ps.executeUpdate();
            
            if (result > 0) {
                conn.commit();
                System.out.println("Desktop: Dish '" + name + "' added and committed to database");
                return true;
            } else {
                conn.rollback();
                return false;
            }
            
        } catch (Exception e) {
            try {
                if (conn != null) {
                    conn.rollback();
                }
            } catch (SQLException rollbackEx) {
                rollbackEx.printStackTrace();
            }
            System.out.println("Desktop: Error adding dish - " + e.getMessage());
            return false;
        } finally {
            try {
                if (ps != null) ps.close();
                if (conn != null) {
                    conn.setAutoCommit(true);
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    
    private void loadDishesData() {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        try {
            tableModel.setRowCount(0);
            
            GetConnection connect = new GetConnection();
            conn = connect.getConnection();
            
            String query = "SELECT itemNo, itemName, Type, Price FROM restaurant ORDER BY Type, itemName";
            ps = conn.prepareStatement(query);
            rs = ps.executeQuery();
            
            while (rs.next()) {
                Object[] row = {
                    rs.getInt("itemNo"),
                    rs.getString("itemName"),
                    rs.getString("Type"),
                    "₹" + rs.getInt("Price")
                };
                tableModel.addRow(row);
            }
            
            System.out.println("Desktop: Loaded " + table.getRowCount() + " dishes from database");
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, 
                " Error loading data: " + e.getMessage(), 
                "Database Error", 
                JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (ps != null) ps.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    
    private void addDish() {
        if (!validateFields()) return;
        
        Connection conn = null;
        PreparedStatement ps = null;
        
        try {
            String name = txtDishName.getText().trim();
            String type = comboDishType.getSelectedItem().toString();
            String price = txtDishPrice.getText().trim();
            
            GetConnection connect = new GetConnection();
            conn = connect.getConnection();
            
            // ✅ FIXED: Transaction management
            conn.setAutoCommit(false);
            
            String query = "INSERT INTO restaurant (itemName, Type, Price) VALUES (?, ?, ?)";
            ps = conn.prepareStatement(query);
            ps.setString(1, name);
            ps.setString(2, type);
            ps.setInt(3, Integer.parseInt(price));
            
            int result = ps.executeUpdate();
            
            if (result > 0) {
                conn.commit();
                System.out.println("Desktop: Dish '" + name + "' added and committed to database");
                
                JOptionPane.showMessageDialog(this, 
                    "Item '" + name + "' added successfully!", 
                    "Success", 
                    JOptionPane.INFORMATION_MESSAGE);
                    
                loadDishesData();
                clearFields();
            } else {
                conn.rollback();
                JOptionPane.showMessageDialog(this, 
                    "Failed to add item. Please try again.", 
                    "Error", 
                    JOptionPane.ERROR_MESSAGE);
            }
            
        } catch (Exception e) {
            try {
                if (conn != null) {
                    conn.rollback();
                }
            } catch (SQLException rollbackEx) {
                rollbackEx.printStackTrace();
            }
            
            JOptionPane.showMessageDialog(this, 
                "Error adding item: " + e.getMessage(), 
                "Database Error", 
                JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        } finally {
            try {
                if (ps != null) ps.close();
                if (conn != null) {
                    conn.setAutoCommit(true);
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    
    private void updateDish() {
        if (!validateFields()) return;
        
        int selectedRow = table.getSelectedRow();
        if (selectedRow < 0) {
            JOptionPane.showMessageDialog(this, "Please select an item to update.", "No Selection", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        Connection conn = null;
        PreparedStatement ps = null;
        
        try {
            int itemNo = (Integer) table.getValueAt(selectedRow, 0);
            
            GetConnection connect = new GetConnection();
            conn = connect.getConnection();
            conn.setAutoCommit(false);
            
            String query = "UPDATE restaurant SET itemName = ?, Type = ?, Price = ? WHERE itemNo = ?";
            ps = conn.prepareStatement(query);
            ps.setString(1, txtDishName.getText().trim());
            ps.setString(2, comboDishType.getSelectedItem().toString());
            ps.setInt(3, Integer.parseInt(txtDishPrice.getText().trim()));
            ps.setInt(4, itemNo);
            
            int result = ps.executeUpdate();
            
            if (result > 0) {
                conn.commit();
                JOptionPane.showMessageDialog(this, "Item updated successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                loadDishesData();
                clearFields();
            } else {
                conn.rollback();
            }
            
        } catch (Exception e) {
            try {
                if (conn != null) conn.rollback();
            } catch (SQLException rollbackEx) {
                rollbackEx.printStackTrace();
            }
            JOptionPane.showMessageDialog(this, "Error updating item: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        } finally {
            try {
                if (ps != null) ps.close();
                if (conn != null) {
                    conn.setAutoCommit(true);
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    
    private void deleteDish() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow < 0) {
            JOptionPane.showMessageDialog(this, "Please select an item to delete.", "No Selection", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        int itemNo = (Integer) table.getValueAt(selectedRow, 0);
        String itemName = table.getValueAt(selectedRow, 1).toString();
        
        int option = JOptionPane.showConfirmDialog(this, 
            "Are you sure you want to delete '" + itemName + "'?\nThis action cannot be undone.", 
            "Confirm Deletion", 
            JOptionPane.YES_NO_OPTION);
        
        if (option == JOptionPane.YES_OPTION) {
            Connection conn = null;
            PreparedStatement ps = null;
            
            try {
                GetConnection connect = new GetConnection();
                conn = connect.getConnection();
                conn.setAutoCommit(false);
                
                String query = "DELETE FROM restaurant WHERE itemNo = ?";
                ps = conn.prepareStatement(query);
                ps.setInt(1, itemNo);
                
                int result = ps.executeUpdate();
                
                if (result > 0) {
                    conn.commit();
                    JOptionPane.showMessageDialog(this, "Item deleted successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                    loadDishesData();
                    clearFields();
                } else {
                    conn.rollback();
                }
                
            } catch (Exception e) {
                try {
                    if (conn != null) conn.rollback();
                } catch (SQLException rollbackEx) {
                    rollbackEx.printStackTrace();
                }
                JOptionPane.showMessageDialog(this, "Error deleting item: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            } finally {
                try {
                    if (ps != null) ps.close();
                    if (conn != null) {
                        conn.setAutoCommit(true);
                        conn.close();
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    
    private JButton createStyledButton(String text, Color bgColor) {
        JButton button = new JButton(text);
        button.setPreferredSize(new Dimension(110, 40));
        button.setFont(new Font("Arial", Font.BOLD, 11));
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
    
    private boolean validateFields() {
        if (txtDishName.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Item Name cannot be empty.", "Validation Error", JOptionPane.WARNING_MESSAGE);
            txtDishName.requestFocus();
            return false;
        }
        if (txtDishPrice.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Price cannot be empty.", "Validation Error", JOptionPane.WARNING_MESSAGE);
            txtDishPrice.requestFocus();
            return false;
        }
        try {
            Integer.parseInt(txtDishPrice.getText().trim());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Price must be a valid number.", "Validation Error", JOptionPane.WARNING_MESSAGE);
            txtDishPrice.requestFocus();
            return false;
        }
        return true;
    }
    
    private void clearFields() {
        txtDishName.setText("");
        txtDishPrice.setText("");
        comboDishType.setSelectedIndex(0);
        table.clearSelection();
    }
}
