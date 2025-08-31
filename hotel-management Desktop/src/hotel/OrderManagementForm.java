package hotel;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class OrderManagementForm extends JFrame {
    
    private JPanel contentPane;
    private JTable menuTable, orderTable;
    private JTextField txtCustomerName, txtPhone, txtTableNumber, txtQuantity;
    private JTextArea txtInstructions;
    private JLabel lblTotal;
    private DefaultTableModel menuModel, orderModel;
    private double totalAmount = 0.0;

    public OrderManagementForm() {
        setTitle("Hotel RAAR - Order Management System");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setResizable(true);
        
        initializeUI();
        loadMenuItems();
    }
    
    private void initializeUI() {
        contentPane = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
                
                GradientPaint gp = new GradientPaint(0, 0, new Color(240, 255, 240), 0, getHeight(), new Color(220, 255, 220));
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
        
        JLabel lblTitle = new JLabel("🍽️ RESTAURANT ORDER MANAGEMENT");
        lblTitle.setFont(new Font("Serif", Font.BOLD, 36));
        lblTitle.setForeground(Color.WHITE);
        lblTitle.setBounds(50, 20, 700, 45);
        headerPanel.add(lblTitle);
        
        JLabel lblSubtitle = new JLabel("Take Orders • Manage Kitchen • Serve Customers");
        lblSubtitle.setFont(new Font("Arial", Font.ITALIC, 18));
        lblSubtitle.setForeground(new Color(240, 255, 240));
        lblSubtitle.setBounds(50, 65, 500, 25);
        headerPanel.add(lblSubtitle);
        
        // Customer Info Panel
        JPanel customerPanel = new JPanel();
        customerPanel.setBackground(Color.WHITE);
        customerPanel.setBounds(50, 130, 400, 200);
        customerPanel.setBorder(BorderFactory.createTitledBorder("👤 Customer Information"));
        customerPanel.setLayout(null);
        contentPane.add(customerPanel);
        
        JLabel lblName = new JLabel("Customer Name:");
        lblName.setBounds(20, 30, 120, 25);
        customerPanel.add(lblName);
        
        txtCustomerName = new JTextField();
        txtCustomerName.setBounds(140, 30, 220, 25);
        customerPanel.add(txtCustomerName);
        
        JLabel lblPhone = new JLabel("Phone:");
        lblPhone.setBounds(20, 65, 120, 25);
        customerPanel.add(lblPhone);
        
        txtPhone = new JTextField();
        txtPhone.setBounds(140, 65, 220, 25);
        customerPanel.add(txtPhone);
        
        JLabel lblTable = new JLabel("Table Number:");
        lblTable.setBounds(20, 100, 120, 25);
        customerPanel.add(lblTable);
        
        txtTableNumber = new JTextField();
        txtTableNumber.setBounds(140, 100, 220, 25);
        customerPanel.add(txtTableNumber);
        
        JLabel lblInstructions = new JLabel("Instructions:");
        lblInstructions.setBounds(20, 135, 120, 25);
        customerPanel.add(lblInstructions);
        
        txtInstructions = new JTextArea();
        JScrollPane instrScrollPane = new JScrollPane(txtInstructions);
        instrScrollPane.setBounds(140, 135, 220, 50);
        customerPanel.add(instrScrollPane);
        
        // Menu Panel
        JPanel menuPanel = new JPanel();
        menuPanel.setBackground(Color.WHITE);
        menuPanel.setBounds(470, 130, 600, 400);
        menuPanel.setBorder(BorderFactory.createTitledBorder("🍽️ Menu Items"));
        menuPanel.setLayout(new BorderLayout());
        contentPane.add(menuPanel);
        
        String[] menuColumns = {"Item", "Type", "Price"};
        menuModel = new DefaultTableModel(menuColumns, 0);
        menuTable = new JTable(menuModel);
        menuTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        
        JScrollPane menuScrollPane = new JScrollPane(menuTable);
        menuPanel.add(menuScrollPane, BorderLayout.CENTER);
        
        // Add to order panel
        JPanel addPanel = new JPanel();
        addPanel.setBounds(50, 350, 400, 180);
        addPanel.setBackground(Color.WHITE);
        addPanel.setBorder(BorderFactory.createTitledBorder("➕ Add to Order"));
        addPanel.setLayout(null);
        contentPane.add(addPanel);
        
        JLabel lblQuantity = new JLabel("Quantity:");
        lblQuantity.setBounds(20, 30, 80, 25);
        addPanel.add(lblQuantity);
        
        txtQuantity = new JTextField("1");
        txtQuantity.setBounds(100, 30, 60, 25);
        addPanel.add(txtQuantity);
        
        JButton btnAddToOrder = new JButton("➕ Add to Order");
        btnAddToOrder.setBounds(180, 30, 150, 25);
        btnAddToOrder.setBackground(new Color(34, 139, 34));
        btnAddToOrder.setForeground(Color.WHITE);
        btnAddToOrder.addActionListener(e -> addToOrder());
        addPanel.add(btnAddToOrder);
        
        JLabel lblTotalLabel = new JLabel("Total Amount:");
        lblTotalLabel.setBounds(20, 70, 100, 25);
        lblTotalLabel.setFont(new Font("Arial", Font.BOLD, 14));
        addPanel.add(lblTotalLabel);
        
        lblTotal = new JLabel("₹0.00");
        lblTotal.setBounds(130, 70, 100, 25);
        lblTotal.setFont(new Font("Arial", Font.BOLD, 16));
        lblTotal.setForeground(new Color(34, 139, 34));
        addPanel.add(lblTotal);
        
        JButton btnPlaceOrder = new JButton("🎯 PLACE ORDER");
        btnPlaceOrder.setBounds(20, 110, 150, 40);
        btnPlaceOrder.setBackground(new Color(255, 140, 0));
        btnPlaceOrder.setForeground(Color.WHITE);
        btnPlaceOrder.setFont(new Font("Arial", Font.BOLD, 14));
        btnPlaceOrder.addActionListener(e -> placeOrder());
        addPanel.add(btnPlaceOrder);
        
        JButton btnClearOrder = new JButton("🗑️ CLEAR");
        btnClearOrder.setBounds(180, 110, 100, 40);
        btnClearOrder.setBackground(new Color(220, 20, 60));
        btnClearOrder.setForeground(Color.WHITE);
        btnClearOrder.addActionListener(e -> clearOrder());
        addPanel.add(btnClearOrder);
        
        // Current Order Panel
        JPanel orderPanel = new JPanel();
        orderPanel.setBackground(Color.WHITE);
        orderPanel.setBounds(1100, 130, 760, 700);
        orderPanel.setBorder(BorderFactory.createTitledBorder("🛒 Current Order"));
        orderPanel.setLayout(new BorderLayout());
        contentPane.add(orderPanel);
        
        String[] orderColumns = {"Item", "Type", "Price", "Qty", "Subtotal"};
        orderModel = new DefaultTableModel(orderColumns, 0);
        orderTable = new JTable(orderModel);
        
        JScrollPane orderScrollPane = new JScrollPane(orderTable);
        orderPanel.add(orderScrollPane, BorderLayout.CENTER);
        
        // Back button
        JButton btnBack = new JButton("🔙 BACK TO ADMIN");
        btnBack.setBounds(50, 750, 200, 40);
        btnBack.setBackground(new Color(128, 128, 128));
        btnBack.setForeground(Color.WHITE);
        btnBack.setFont(new Font("Arial", Font.BOLD, 14));
        btnBack.addActionListener(e -> dispose());
        contentPane.add(btnBack);
    }
    
    private void loadMenuItems() {
        try {
            menuModel.setRowCount(0);
            
            GetConnection connect = new GetConnection();
            Connection conn = connect.getConnection();
            
            String query = "SELECT itemName, Type, Price FROM restaurant ORDER BY Type, itemName";
            PreparedStatement ps = conn.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            
            while (rs.next()) {
                Object[] row = {
                    rs.getString("itemName"),
                    rs.getString("Type"),
                    rs.getInt("Price")
                };
                menuModel.addRow(row);
            }
            
            rs.close();
            ps.close();
            conn.close();
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error loading menu: " + e.getMessage());
        }
    }
    
    private void addToOrder() {
        int selectedRow = menuTable.getSelectedRow();
        if (selectedRow < 0) {
            JOptionPane.showMessageDialog(this, "Please select a menu item first.");
            return;
        }
        
        try {
            String itemName = menuModel.getValueAt(selectedRow, 0).toString();
            String itemType = menuModel.getValueAt(selectedRow, 1).toString();
            double price = Double.parseDouble(menuModel.getValueAt(selectedRow, 2).toString());
            int quantity = Integer.parseInt(txtQuantity.getText().trim());
            
            if (quantity <= 0) {
                JOptionPane.showMessageDialog(this, "Quantity must be greater than 0.");
                return;
            }
            
            // Check if item already exists in order
            boolean found = false;
            for (int i = 0; i < orderModel.getRowCount(); i++) {
                if (orderModel.getValueAt(i, 0).equals(itemName)) {
                    // Update existing item
                    int existingQty = Integer.parseInt(orderModel.getValueAt(i, 3).toString());
                    int newQty = existingQty + quantity;
                    double newSubtotal = price * newQty;
                    
                    orderModel.setValueAt(newQty, i, 3);
                    orderModel.setValueAt(String.format("₹%.2f", newSubtotal), i, 4);
                    found = true;
                    break;
                }
            }
            
            if (!found) {
                // Add new item
                double subtotal = price * quantity;
                Object[] row = {
                    itemName,
                    itemType,
                    String.format("₹%.2f", price),
                    quantity,
                    String.format("₹%.2f", subtotal)
                };
                orderModel.addRow(row);
            }
            
            calculateTotal();
            txtQuantity.setText("1");
            
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Please enter a valid quantity.");
        }
    }
    
    private void calculateTotal() {
        totalAmount = 0.0;
        for (int i = 0; i < orderModel.getRowCount(); i++) {
            String subtotalStr = orderModel.getValueAt(i, 4).toString().replace("₹", "");
            totalAmount += Double.parseDouble(subtotalStr);
        }
        lblTotal.setText(String.format("₹%.2f", totalAmount));
    }
    
    private void placeOrder() {
        if (orderModel.getRowCount() == 0) {
            JOptionPane.showMessageDialog(this, "Please add items to the order first.");
            return;
        }
        
        if (txtCustomerName.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter customer name.");
            txtCustomerName.requestFocus();
            return;
        }
        
        try {
            GetConnection connect = new GetConnection();
            Connection conn = connect.getConnection();
            
            // Insert order
            String orderQuery = "INSERT INTO orders (customer_name, customer_phone, table_number, special_instructions, total_amount, order_date, status) VALUES (?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement orderPs = conn.prepareStatement(orderQuery, Statement.RETURN_GENERATED_KEYS);
            orderPs.setString(1, txtCustomerName.getText().trim());
            orderPs.setString(2, txtPhone.getText().trim());
            orderPs.setString(3, txtTableNumber.getText().trim());
            orderPs.setString(4, txtInstructions.getText().trim());
            orderPs.setDouble(5, totalAmount);
            orderPs.setTimestamp(6, Timestamp.valueOf(LocalDateTime.now()));
            orderPs.setString(7, "PENDING");
            
            orderPs.executeUpdate();
            ResultSet generatedKeys = orderPs.getGeneratedKeys();
            
            if (generatedKeys.next()) {
                long orderId = generatedKeys.getLong(1);
                
                // Insert order items
                String itemQuery = "INSERT INTO order_items (order_id, item_name, item_type, price, quantity, subtotal) VALUES (?, ?, ?, ?, ?, ?)";
                PreparedStatement itemPs = conn.prepareStatement(itemQuery);
                
                for (int i = 0; i < orderModel.getRowCount(); i++) {
                    String itemName = orderModel.getValueAt(i, 0).toString();
                    String itemType = orderModel.getValueAt(i, 1).toString();
                    double price = Double.parseDouble(orderModel.getValueAt(i, 2).toString().replace("₹", ""));
                    int quantity = Integer.parseInt(orderModel.getValueAt(i, 3).toString());
                    double subtotal = Double.parseDouble(orderModel.getValueAt(i, 4).toString().replace("₹", ""));
                    
                    itemPs.setLong(1, orderId);
                    itemPs.setString(2, itemName);
                    itemPs.setString(3, itemType);
                    itemPs.setDouble(4, price);
                    itemPs.setInt(5, quantity);
                    itemPs.setDouble(6, subtotal);
                    itemPs.addBatch();
                }
                
                itemPs.executeBatch();
                itemPs.close();
                
                JOptionPane.showMessageDialog(this, 
                    "✅ Order #" + orderId + " placed successfully!\n" +
                    "Customer: " + txtCustomerName.getText() + "\n" +
                    "Total: ₹" + String.format("%.2f", totalAmount),
                    "Order Confirmed",
                    JOptionPane.INFORMATION_MESSAGE);
                
                clearOrder();
            }
            
            orderPs.close();
            conn.close();
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error placing order: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    private void clearOrder() {
        orderModel.setRowCount(0);
        txtCustomerName.setText("");
        txtPhone.setText("");
        txtTableNumber.setText("");
        txtInstructions.setText("");
        txtQuantity.setText("1");
        totalAmount = 0.0;
        lblTotal.setText("₹0.00");
    }
}
