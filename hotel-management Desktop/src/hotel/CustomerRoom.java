package hotel;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GradientPaint;
import java.awt.RenderingHints;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JTextArea;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Color;
import java.awt.Cursor;
import java.util.Date;
import javax.swing.ImageIcon;
import javax.swing.BorderFactory;
import javax.swing.SwingConstants;

public class CustomerRoom extends JFrame {

    private JPanel contentPane;
    private JTextField name;
    private JTextField addr;
    private JTextField phone;
    private JTextField meal;
    private JTextField drink;
    JComboBox comboBox_Dish = new JComboBox();
    JComboBox comboBox_Drink = new JComboBox();
    private JTable table;
    private JTable table_1;
    JTextArea area = new JTextArea();
    private JTextField totalA;
    JLabel a1 = new JLabel("*");
    JLabel a2 = new JLabel("*");
    JLabel a3 = new JLabel("*");
    SimpleDateFormat sf = new SimpleDateFormat("yy/MM/dd HH:mm:ss");
    Date dt = new Date();
    int flag = 0;

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    CustomerRoom frame = new CustomerRoom();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public CustomerRoom() {
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowOpened(WindowEvent e) {
                FillDishes();
                FillDrinks();
                a1.setVisible(false);
                a2.setVisible(false);
                a3.setVisible(false);
            }
        });
        
        FillCombo();
        setTitle("Hotel Crescent - Restaurant & Dining Service");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(0, 0, 1400, 800);
        setLocationRelativeTo(null);
        
        // Main panel with restaurant gradient
        contentPane = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
                
                // Elegant restaurant gradient
                GradientPaint gp = new GradientPaint(0, 0, new Color(255, 250, 240), 0, getHeight(), new Color(250, 240, 230));
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);
        
        // Header Panel
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(new Color(178, 34, 34));
        headerPanel.setBounds(0, 0, 1400, 120);
        headerPanel.setLayout(null);
        contentPane.add(headerPanel);
        
        JLabel lblPageTitle = new JLabel("GOURMET RESTAURANT & DINING");
        lblPageTitle.setFont(new Font("Serif", Font.BOLD, 32));
        lblPageTitle.setForeground(Color.WHITE);
        lblPageTitle.setBounds(50, 25, 600, 45);
        headerPanel.add(lblPageTitle);
        
        JLabel lblSubtitle = new JLabel("Exquisite Cuisine & Premium Beverages");
        lblSubtitle.setFont(new Font("Arial", Font.ITALIC, 18));
        lblSubtitle.setForeground(new Color(255, 248, 220));
        lblSubtitle.setBounds(50, 70, 400, 30);
        headerPanel.add(lblSubtitle);
        
        // Customer Information Panel
        JPanel customerPanel = new JPanel();
        customerPanel.setBackground(Color.WHITE);
        customerPanel.setBounds(50, 150, 420, 320);
        customerPanel.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(new Color(178, 34, 34), 2),
            "CUSTOMER DETAILS",
            javax.swing.border.TitledBorder.CENTER,
            javax.swing.border.TitledBorder.TOP,
            new Font("Arial", Font.BOLD, 16),
            new Color(178, 34, 34)
        ));
        customerPanel.setLayout(null);
        contentPane.add(customerPanel);
        
        JLabel lblName = new JLabel("Full Name:");
        lblName.setFont(new Font("Arial", Font.BOLD, 16));
        lblName.setForeground(new Color(178, 34, 34));
        lblName.setBounds(30, 40, 120, 25);
        customerPanel.add(lblName);
        
        name = new JTextField();
        name.setFont(new Font("Arial", Font.PLAIN, 14));
        name.setBounds(30, 65, 350, 35);
        name.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(178, 34, 34), 1),
            BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
        customerPanel.add(name);
        
        JLabel lblNewLabel = new JLabel("Address:");
        lblNewLabel.setFont(new Font("Arial", Font.BOLD, 16));
        lblNewLabel.setForeground(new Color(178, 34, 34));
        lblNewLabel.setBounds(30, 110, 120, 25);
        customerPanel.add(lblNewLabel);
        
        addr = new JTextField();
        addr.setFont(new Font("Arial", Font.PLAIN, 14));
        addr.setBounds(30, 135, 350, 35);
        addr.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(178, 34, 34), 1),
            BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
        customerPanel.add(addr);
        
        JLabel lblEspresso = new JLabel("Phone Number:");
        lblEspresso.setFont(new Font("Arial", Font.BOLD, 16));
        lblEspresso.setForeground(new Color(178, 34, 34));
        lblEspresso.setBounds(30, 180, 150, 25);
        customerPanel.add(lblEspresso);
        
        phone = new JTextField();
        phone.setFont(new Font("Arial", Font.PLAIN, 14));
        phone.setBounds(30, 205, 350, 35);
        phone.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(178, 34, 34), 1),
            BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
        customerPanel.add(phone);
        
        // Menu Selection Panel
        JPanel menuPanel = new JPanel();
        menuPanel.setBackground(Color.WHITE);
        menuPanel.setBounds(50, 490, 420, 180);
        menuPanel.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(new Color(178, 34, 34), 2),
            "MENU SELECTION",
            javax.swing.border.TitledBorder.CENTER,
            javax.swing.border.TitledBorder.TOP,
            new Font("Arial", Font.BOLD, 16),
            new Color(178, 34, 34)
        ));
        menuPanel.setLayout(null);
        contentPane.add(menuPanel);
        
        JLabel lblSelectDish = new JLabel("Select Main Dish:");
        lblSelectDish.setForeground(new Color(178, 34, 34));
        lblSelectDish.setFont(new Font("Arial", Font.BOLD, 14));
        lblSelectDish.setBounds(30, 35, 150, 25);
        menuPanel.add(lblSelectDish);
        
        comboBox_Dish.setFont(new Font("Arial", Font.PLAIN, 14));
        comboBox_Dish.setBounds(30, 60, 350, 35);
        comboBox_Dish.setBackground(Color.WHITE);
        menuPanel.add(comboBox_Dish);
        
        JLabel lblPinaColada = new JLabel("Select Beverage:");
        lblPinaColada.setFont(new Font("Arial", Font.BOLD, 14));
        lblPinaColada.setForeground(new Color(178, 34, 34));
        lblPinaColada.setBounds(30, 105, 150, 25);
        menuPanel.add(lblPinaColada);
        
        comboBox_Drink.setFont(new Font("Arial", Font.PLAIN, 14));
        comboBox_Drink.setBounds(30, 130, 350, 35);
        comboBox_Drink.setBackground(Color.WHITE);
        menuPanel.add(comboBox_Drink);
        
        // Menu Display Panel
        JPanel dishesPanel = new JPanel();
        dishesPanel.setBackground(Color.WHITE);
        dishesPanel.setBounds(490, 150, 350, 250);
        dishesPanel.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(new Color(178, 34, 34), 2),
            "MAIN DISHES",
            javax.swing.border.TitledBorder.CENTER,
            javax.swing.border.TitledBorder.TOP,
            new Font("Arial", Font.BOLD, 14),
            new Color(178, 34, 34)
        ));
        dishesPanel.setLayout(null);
        contentPane.add(dishesPanel);
        
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(15, 30, 320, 200);
        dishesPanel.add(scrollPane);
        
        table = new JTable();
        table.setFont(new Font("Arial", Font.PLAIN, 12));
        table.setRowHeight(25);
        scrollPane.setViewportView(table);
        
        JPanel drinksPanel = new JPanel();
        drinksPanel.setBackground(Color.WHITE);
        drinksPanel.setBounds(490, 420, 350, 250);
        drinksPanel.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(new Color(178, 34, 34), 2),
            "BEVERAGES",
            javax.swing.border.TitledBorder.CENTER,
            javax.swing.border.TitledBorder.TOP,
            new Font("Arial", Font.BOLD, 14),
            new Color(178, 34, 34)
        ));
        drinksPanel.setLayout(null);
        contentPane.add(drinksPanel);
        
        JScrollPane scrollPane_1 = new JScrollPane();
        scrollPane_1.setBounds(15, 30, 320, 200);
        drinksPanel.add(scrollPane_1);
        
        table_1 = new JTable();
        table_1.setFont(new Font("Arial", Font.PLAIN, 12));
        table_1.setRowHeight(25);
        scrollPane_1.setViewportView(table_1);
        
        // Order Summary Panel
        JPanel summaryPanel = new JPanel();
        summaryPanel.setBackground(Color.WHITE);
        summaryPanel.setBounds(860, 150, 350, 180);
        summaryPanel.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(new Color(178, 34, 34), 2),
            "ORDER SUMMARY",
            javax.swing.border.TitledBorder.CENTER,
            javax.swing.border.TitledBorder.TOP,
            new Font("Arial", Font.BOLD, 14),
            new Color(178, 34, 34)
        ));
        summaryPanel.setLayout(null);
        contentPane.add(summaryPanel);
        
        JLabel lblTotal = new JLabel("Dish Cost:");
        lblTotal.setFont(new Font("Arial", Font.BOLD, 14));
        lblTotal.setForeground(new Color(178, 34, 34));
        lblTotal.setBounds(30, 40, 100, 25);
        summaryPanel.add(lblTotal);
        
        meal = new JTextField();
        meal.setFont(new Font("Arial", Font.PLAIN, 12));
        meal.setBounds(140, 40, 100, 25);
        meal.setEditable(false);
        summaryPanel.add(meal);
        
        JLabel lblCostOfDrinks = new JLabel("Beverage Cost:");
        lblCostOfDrinks.setFont(new Font("Arial", Font.BOLD, 14));
        lblCostOfDrinks.setForeground(new Color(178, 34, 34));
        lblCostOfDrinks.setBounds(30, 75, 120, 25);
        summaryPanel.add(lblCostOfDrinks);
        
        drink = new JTextField();
        drink.setFont(new Font("Arial", Font.PLAIN, 12));
        drink.setBounds(140, 75, 100, 25);
        drink.setEditable(false);
        summaryPanel.add(drink);
        
        JLabel lblTotalAmount = new JLabel("TOTAL:");
        lblTotalAmount.setFont(new Font("Arial", Font.BOLD, 16));
        lblTotalAmount.setForeground(new Color(178, 34, 34));
        lblTotalAmount.setBounds(30, 115, 100, 25);
        summaryPanel.add(lblTotalAmount);
        
        totalA = new JTextField();
        totalA.setFont(new Font("Arial", Font.BOLD, 16));
        totalA.setBounds(140, 115, 100, 30);
        totalA.setBackground(new Color(255, 248, 220));
        totalA.setEditable(false);
        summaryPanel.add(totalA);
        
        // Receipt Panel
        JPanel receiptPanel = new JPanel();
        receiptPanel.setBackground(Color.WHITE);
        receiptPanel.setBounds(860, 350, 350, 320);
        receiptPanel.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(new Color(178, 34, 34), 2),
            "DINING RECEIPT",
            javax.swing.border.TitledBorder.CENTER,
            javax.swing.border.TitledBorder.TOP,
            new Font("Arial", Font.BOLD, 14),
            new Color(178, 34, 34)
        ));
        receiptPanel.setLayout(null);
        contentPane.add(receiptPanel);
        
        area.setFont(new Font("Courier New", Font.PLAIN, 11));
        area.setBackground(new Color(255, 255, 240));
        area.setBounds(15, 30, 320, 270);
        area.setBorder(BorderFactory.createLoweredBevelBorder());
        receiptPanel.add(area);
        
        // Action Buttons Panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(new Color(255, 250, 240));
        buttonPanel.setBounds(50, 690, 1310, 70);
        buttonPanel.setLayout(null);
        contentPane.add(buttonPanel);
        
        JButton btnTotal = createStyledButton("CALCULATE TOTAL", new Color(70, 130, 180), 50, 15, 200, 40);
        btnTotal.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    if (name.getText().equals("")) {
                        a1.setVisible(true);
                        throw new MyException("Exception: Please fill all the details");
                    } else if (addr.getText().equals("")) {
                        a2.setVisible(true);
                        throw new MyException("Exception: Please fill all the details");
                    } else if (phone.getText().equals("")) {
                        a3.setVisible(true);
                        throw new MyException("Exception: Please fill all the details");
                    } else {
                        calculateAmount();
                    }
                } catch (MyException r) {
                    JOptionPane.showMessageDialog(CustomerRoom.this, r);
                }
            }
        });
        buttonPanel.add(btnTotal);
        
        JButton btnGenerateReceipt = createStyledButton("GENERATE RECEIPT", new Color(34, 139, 34), 270, 15, 200, 40);
        btnGenerateReceipt.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (name.getText().equals("")) {
                    a1.setVisible(true);
                }
                if (addr.getText().equals("")) {
                    a2.setVisible(true);
                }
                if (phone.getText().equals("")) {
                    a3.setVisible(true);
                } else {
                    sf.format(dt);
                    calculateAmount();
                    area.setText("********************************************\n");
                    area.setText(area.getText() + "*** HOTEL CRESCENT RESTAURANT ***\n");
                    area.setText(area.getText() + "********************************************\n\n");
                    area.setText(area.getText() + "TIME: " + sf.format(dt) + "\n\n");
                    area.setText(area.getText() + "GUEST: " + name.getText() + "\n\n");
                    area.setText(area.getText() + "ADDRESS: " + addr.getText() + "\n\n");
                    area.setText(area.getText() + "PHONE: " + phone.getText() + "\n\n");
                    area.setText(area.getText() + "MAIN DISH: " + comboBox_Dish.getSelectedItem() + "\n\n");
                    area.setText(area.getText() + "BEVERAGE: " + comboBox_Drink.getSelectedItem() + "\n\n");
                    area.setText(area.getText() + "TOTAL AMOUNT: ₹" + totalA.getText() + "\n\n");
                    area.setText(area.getText() + "Thank you for dining with us!\n");
                    addToDatabase();
                    flag = 1;
                }
            }
        });
        buttonPanel.add(btnGenerateReceipt);
        
        JButton btnCheckout = createStyledButton("CHECKOUT", new Color(255, 140, 0), 490, 15, 200, 40);
        btnCheckout.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                checkOut();
            }
        });
        buttonPanel.add(btnCheckout);
        
        // UPDATED BACK BUTTON - REMOVED "generate receipt first" RESTRICTION
        JButton btnBack = createStyledButton("BACK TO MENU", new Color(220, 20, 60), 1060, 15, 200, 40);
        btnBack.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // REMOVED the flag check - users can go back anytime
                SecondPage sp = new SecondPage();
                sp.setVisible(true);
                sp.pack();
                sp.setLocationRelativeTo(null);
                sp.setBounds(100, 100, 1015, 574);
                setVisible(false);
            }
        });
        buttonPanel.add(btnBack);
        
        // Error indicators
        a1.setForeground(Color.RED);
        a1.setFont(new Font("Arial", Font.BOLD, 20));
        a1.setBounds(385, 65, 21, 21);
        customerPanel.add(a1);
        
        a2.setFont(new Font("Arial", Font.BOLD, 20));
        a2.setForeground(Color.RED);
        a2.setBounds(385, 135, 21, 21);
        customerPanel.add(a2);
        
        a3.setForeground(Color.RED);
        a3.setFont(new Font("Arial", Font.BOLD, 20));
        a3.setBounds(385, 205, 21, 16);
        customerPanel.add(a3);
    }
    
    private JButton createStyledButton(String text, Color bgColor, int x, int y, int width, int height) {
        JButton button = new JButton(text);
        button.setBounds(x, y, width, height);
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setBackground(bgColor);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createRaisedBevelBorder());
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        // Hover effect
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
    
    // Keep all your existing methods unchanged (FillCombo, FillDishes, FillDrinks, calculateAmount, addToDatabase, checkOut)
    // Just paste them here as they were in your original file...
    
    private void FillCombo() {
        PreparedStatement ps = null;
        ResultSet result = null;
        PreparedStatement ps1 = null;
        ResultSet result1 = null;
        try {
            GetConnection connect = new GetConnection();
            Connection conn = connect.getConnection();
            String sql = "SELECT * FROM restaurant where Type='MEAL' ORDER BY itemName ASC ";
            ps = conn.prepareStatement(sql);
            result = ps.executeQuery();
            while (result.next()) {
                String name = result.getString("itemName");
                comboBox_Dish.addItem(name);
            }
            String sql2 = "SELECT * FROM restaurant where Type='DRINK' ORDER BY itemName ASC";
            ps1 = conn.prepareStatement(sql2);
            result1 = ps1.executeQuery();
            while (result1.next()) {
                String Dname = result1.getString("itemName");
                comboBox_Drink.addItem(Dname);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    // Add all your other existing methods here (FillDishes, FillDrinks, calculateAmount, addToDatabase, checkOut)
    // They remain exactly the same as in your original code
    
    public void FillDishes() {
        GetConnection connect = new GetConnection();
        Connection conn = connect.getConnection();
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("DISH NAME");
        model.addColumn("PRICE");
        try {
            String query = "SELECT * FROM restaurant where Type='MEAL'";
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(query);
            while (rs.next()) {
                model.addRow(new Object[]{
                    rs.getString("itemName"),
                    rs.getString("Price"),
                });
            }
            rs.close();
            st.close();
            conn.close();
            table.setModel(model);
            table.setAutoResizeMode(0);
            table.getColumnModel().getColumn(0).setPreferredWidth(200);
            table.getColumnModel().getColumn(1).setPreferredWidth(100);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void FillDrinks() {
        GetConnection connect = new GetConnection();
        Connection conn = connect.getConnection();
        DefaultTableModel model2 = new DefaultTableModel();
        model2.addColumn("DRINK NAME");
        model2.addColumn("PRICE");
        try {
            String query = "SELECT * FROM restaurant where Type='DRINK'";
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(query);
            while (rs.next()) {
                model2.addRow(new Object[]{
                    rs.getString("itemName"),
                    rs.getString("Price"),
                });
            }
            rs.close();
            st.close();
            conn.close();
            table_1.setModel(model2);
            table_1.setAutoResizeMode(0);
            table_1.getColumnModel().getColumn(0).setPreferredWidth(200);
            table_1.getColumnModel().getColumn(1).setPreferredWidth(100);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void calculateAmount() {
        GetConnection connect = new GetConnection();
        Connection conn = connect.getConnection();
        PreparedStatement ps = null;
        ResultSet result = null;
        PreparedStatement ps1 = null;
        ResultSet result1 = null;
        String mealAmount = null;
        String drinkAmount = null;
        String di = (String) comboBox_Dish.getSelectedItem();
        String dr = (String) comboBox_Drink.getSelectedItem();
        try {
            String sql = "SELECT Price FROM restaurant WHERE itemName = ?";
            ps = conn.prepareStatement(sql);
            ps.setString(1, di);
            result = ps.executeQuery();
            if (result.next())
                mealAmount = result.getString(1);
            meal.setText(mealAmount);
            ps1 = conn.prepareStatement(sql);
            ps1.setString(1, dr);
            result1 = ps1.executeQuery();
            if (result1.next())
                drinkAmount = result1.getString(1);
            drink.setText(drinkAmount);
            int total = Integer.parseInt(mealAmount) + Integer.parseInt(drinkAmount);
            totalA.setText(Integer.toString(total));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void addToDatabase() {
        PreparedStatement ps = null;
        ResultSet result = null;
        try {
            GetConnection connect = new GetConnection();
            Connection conn = connect.getConnection();
            ps = conn.prepareStatement("INSERT INTO restaurantcustomer (custName,custAddr,Phone,Meal,Drink,Price,status) VALUES (?,?,?,?,?,?,?)");
            ps.setString(1, name.getText());
            ps.setString(2, addr.getText());
            ps.setString(3, phone.getText());
            ps.setString(4, comboBox_Dish.getSelectedItem().toString());
            ps.setString(5, comboBox_Drink.getSelectedItem().toString());
            ps.setString(6, totalA.getText());
            ps.setString(7, "0");
            if (ps.executeUpdate() > 0) {
                JOptionPane.showMessageDialog(null, "✅ Order confirmed! Enjoy your meal at Hotel Crescent.");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    public void checkOut() {
        PreparedStatement ps = null;
        ResultSet result = null;
        try {
            GetConnection connect = new GetConnection();
            Connection conn = connect.getConnection();
            ps = conn.prepareStatement("UPDATE restaurantcustomer SET status = 1 where custName=?");
            ps.setString(1, name.getText());
            if (ps.executeUpdate() > 0) {
                JOptionPane.showMessageDialog(null, "✅ Thank you for dining with us! Payment completed successfully.");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
