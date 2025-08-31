package hotel;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GradientPaint;
import java.awt.RenderingHints;
import javax.swing.JTextArea;
import javax.swing.ImageIcon;
import java.awt.Color;
import java.awt.Cursor;
import javax.swing.BorderFactory;
import javax.swing.SwingConstants;

public class CustomerRestaurant extends JFrame {

    private JPanel contentPane;
    private JTextField name;
    private JTextField addr;
    private JTextField phone;
    private JTextField roomtype;
    private JTextField bedtype;
    private JTextField price;
    private JTable table;
    private JScrollPane scrollPane;
    private JButton btnGenerateReceipt;
    private JButton btnBack;
    private JButton btnCheckout;
    private JTextField days;
    private JLabel lblTotal;
    private JTextField tot;
    private JTextArea textArea;
    int flag = 0;
    private JLabel a1;
    private JLabel a2;
    private JLabel a3;
    private JLabel lblRoomNo;
    private JTextField rno;
    SimpleDateFormat sf = new SimpleDateFormat("yy/MM/dd HH:mm:ss");
    Date dt = new Date();


    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    CustomerRestaurant frame = new CustomerRestaurant();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public CustomerRestaurant() {
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowOpened(WindowEvent e) {
                displayRooms();
                a1.setVisible(false);
                a2.setVisible(false);
                a3.setVisible(false);
            }
        });
        
        setTitle("Hotel Crescent - Room Booking Service");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(0, 0, 1400, 800);
        setLocationRelativeTo(null);
        
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
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);
        
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(new Color(139, 69, 19));
        headerPanel.setBounds(0, 0, 1400, 120);
        headerPanel.setLayout(null);
        contentPane.add(headerPanel);
        
        JLabel lblPageTitle = new JLabel("🏨 LUXURY ROOM BOOKING SERVICE");
        lblPageTitle.setFont(new Font("Serif", Font.BOLD, 32));
        lblPageTitle.setForeground(Color.WHITE);
        lblPageTitle.setBounds(50, 25, 600, 45);
        headerPanel.add(lblPageTitle);
        
        JLabel lblSubtitle = new JLabel("Experience Ultimate Comfort & Elegance");
        lblSubtitle.setFont(new Font("Arial", Font.ITALIC, 18));
        lblSubtitle.setForeground(new Color(255, 248, 220));
        lblSubtitle.setBounds(50, 70, 400, 30);
        headerPanel.add(lblSubtitle);
        
        JPanel customerPanel = new JPanel();
        customerPanel.setBackground(Color.WHITE);
        customerPanel.setBounds(50, 150, 450, 420);
        customerPanel.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(new Color(139, 69, 19), 2),
            "CUSTOMER INFORMATION",
            javax.swing.border.TitledBorder.CENTER,
            javax.swing.border.TitledBorder.TOP,
            new Font("Arial", Font.BOLD, 16),
            new Color(139, 69, 19)
        ));
        customerPanel.setLayout(null);
        contentPane.add(customerPanel);
        
        JLabel lblName = new JLabel("Full Name:");
        lblName.setFont(new Font("Arial", Font.BOLD, 16));
        lblName.setForeground(new Color(139, 69, 19));
        lblName.setBounds(30, 40, 120, 25);
        customerPanel.add(lblName);
        
        name = new JTextField();
        name.setFont(new Font("Arial", Font.PLAIN, 14));
        name.setBounds(30, 65, 380, 35);
        name.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(139, 69, 19), 1),
            BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
        customerPanel.add(name);
        
        JLabel lblAddress = new JLabel("Address:");
        lblAddress.setFont(new Font("Arial", Font.BOLD, 16));
        lblAddress.setForeground(new Color(139, 69, 19));
        lblAddress.setBounds(30, 110, 120, 25);
        customerPanel.add(lblAddress);
        
        addr = new JTextField();
        addr.setFont(new Font("Arial", Font.PLAIN, 14));
        addr.setBounds(30, 135, 380, 35);
        addr.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(139, 69, 19), 1),
            BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
        customerPanel.add(addr);
        
        JLabel lblPhoneNum = new JLabel("Phone Number:");
        lblPhoneNum.setFont(new Font("Arial", Font.BOLD, 16));
        lblPhoneNum.setForeground(new Color(139, 69, 19));
        lblPhoneNum.setBounds(30, 180, 150, 25);
        customerPanel.add(lblPhoneNum);
        
        phone = new JTextField();
        phone.setFont(new Font("Arial", Font.PLAIN, 14));
        phone.setBounds(30, 205, 380, 35);
        phone.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(139, 69, 19), 1),
            BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
        customerPanel.add(phone);
        
        lblRoomNo = new JLabel("Room Number:");
        lblRoomNo.setFont(new Font("Arial", Font.BOLD, 16));
        lblRoomNo.setForeground(new Color(139, 69, 19));
        lblRoomNo.setBounds(30, 250, 150, 25);
        customerPanel.add(lblRoomNo);
        
        rno = new JTextField();
        rno.setFont(new Font("Arial", Font.PLAIN, 14));
        rno.setBounds(30, 275, 180, 35);
        rno.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(139, 69, 19), 1),
            BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
        rno.setEditable(false);
        customerPanel.add(rno);
        
        JLabel lblNoOfDays = new JLabel("Number of Days:");
        lblNoOfDays.setFont(new Font("Arial", Font.BOLD, 16));
        lblNoOfDays.setForeground(new Color(139, 69, 19));
        lblNoOfDays.setBounds(230, 250, 150, 25);
        customerPanel.add(lblNoOfDays);
        
        days = new JTextField();
        days.setFont(new Font("Arial", Font.PLAIN, 14));
        days.setBounds(230, 275, 180, 35);
        days.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(139, 69, 19), 1),
            BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
        customerPanel.add(days);
        
        JLabel lblRoomType = new JLabel("Room Type:");
        lblRoomType.setFont(new Font("Arial", Font.BOLD, 16));
        lblRoomType.setForeground(new Color(139, 69, 19));
        lblRoomType.setBounds(30, 320, 120, 25);
        customerPanel.add(lblRoomType);
        
        roomtype = new JTextField();
        roomtype.setFont(new Font("Arial", Font.PLAIN, 14));
        roomtype.setBounds(150, 320, 100, 30);
        roomtype.setEditable(false);
        customerPanel.add(roomtype);
        
        JLabel lblBedType = new JLabel("Bed Type:");
        lblBedType.setFont(new Font("Arial", Font.BOLD, 16));
        lblBedType.setForeground(new Color(139, 69, 19));
        lblBedType.setBounds(270, 320, 100, 25);
        customerPanel.add(lblBedType);
        
        bedtype = new JTextField();
        bedtype.setFont(new Font("Arial", Font.PLAIN, 14));
        bedtype.setBounds(350, 320, 100, 30);
        bedtype.setEditable(false);
        customerPanel.add(bedtype);
        
        JLabel lblPrice = new JLabel("Price per Night:");
        lblPrice.setFont(new Font("Arial", Font.BOLD, 16));
        lblPrice.setForeground(new Color(139, 69, 19));
        lblPrice.setBounds(30, 360, 150, 25);
        customerPanel.add(lblPrice);
        
        price = new JTextField();
        price.setFont(new Font("Arial", Font.PLAIN, 14));
        price.setBounds(150, 360, 100, 30);
        price.setEditable(false);
        customerPanel.add(price);
        
        lblTotal = new JLabel("Total Amount:");
        lblTotal.setFont(new Font("Arial", Font.BOLD, 16));
        lblTotal.setForeground(new Color(139, 69, 19));
        lblTotal.setBounds(270, 360, 120, 25);
        customerPanel.add(lblTotal);
        
        tot = new JTextField();
        tot.setFont(new Font("Arial", Font.BOLD, 14));
        tot.setBounds(350, 360, 100, 30);
        tot.setEditable(false);
        tot.setBackground(new Color(255, 248, 220));
        customerPanel.add(tot);
        
        JPanel roomsPanel = new JPanel();
        roomsPanel.setBackground(Color.WHITE);
        roomsPanel.setBounds(520, 150, 500, 420);
        roomsPanel.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(new Color(139, 69, 19), 2),
            "AVAILABLE ROOMS",
            javax.swing.border.TitledBorder.CENTER,
            javax.swing.border.TitledBorder.TOP,
            new Font("Arial", Font.BOLD, 16),
            new Color(139, 69, 19)
        ));
        roomsPanel.setLayout(null);
        contentPane.add(roomsPanel);
        
        scrollPane = new JScrollPane();
        scrollPane.setBounds(20, 30, 460, 370);
        roomsPanel.add(scrollPane);
        
        table = new JTable();
        table.setFont(new Font("Arial", Font.PLAIN, 12));
        table.setRowHeight(25);
        table.setSelectionBackground(new Color(255, 248, 220));
        scrollPane.setViewportView(table);

        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int i = table.getSelectedRow();
                if (i >= 0) {
                    DefaultTableModel model = (DefaultTableModel) table.getModel();
                    rno.setText(model.getValueAt(i, 0).toString());
                    roomtype.setText(model.getValueAt(i, 1).toString());
                    bedtype.setText(model.getValueAt(i, 2).toString());
                    price.setText(model.getValueAt(i, 3).toString());
                }
            }
        });

        JPanel receiptPanel = new JPanel();
        receiptPanel.setBackground(Color.WHITE);
        receiptPanel.setBounds(1040, 150, 320, 420);
        receiptPanel.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(new Color(139, 69, 19), 2),
            "BOOKING RECEIPT",
            javax.swing.border.TitledBorder.CENTER,
            javax.swing.border.TitledBorder.TOP,
            new Font("Arial", Font.BOLD, 16),
            new Color(139, 69, 19)
        ));
        receiptPanel.setLayout(null);
        contentPane.add(receiptPanel);
        
        textArea = new JTextArea();
        textArea.setFont(new Font("Courier New", Font.PLAIN, 12));
        textArea.setBackground(new Color(255, 255, 240));
        textArea.setBounds(15, 30, 290, 370);
        textArea.setBorder(BorderFactory.createLoweredBevelBorder());
        receiptPanel.add(textArea);
        
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(new Color(250, 248, 240));
        buttonPanel.setBounds(50, 590, 1310, 80);
        buttonPanel.setLayout(null);
        contentPane.add(buttonPanel);
        
        JButton btnTotal = createStyledButton("CALCULATE TOTAL", new Color(70, 130, 180), 50, 20, 200, 40);
        btnTotal.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                 if (name.getText().isEmpty() || addr.getText().isEmpty() || phone.getText().isEmpty()) {
                    JOptionPane.showMessageDialog(CustomerRestaurant.this, "Please fill all customer details first.", "Missing Information", JOptionPane.WARNING_MESSAGE);
                } else {
                    calculateTotal();
                }
            }
        });
        buttonPanel.add(btnTotal);
        
        btnGenerateReceipt = createStyledButton("GENERATE RECEIPT", new Color(34, 139, 34), 270, 20, 200, 40);
        btnGenerateReceipt.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (name.getText().isEmpty() || addr.getText().isEmpty() || phone.getText().isEmpty() || tot.getText().isEmpty()) {
                    JOptionPane.showMessageDialog(CustomerRestaurant.this, "Please fill all details and calculate the total first.", "Missing Information", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                
                calculateTotal();
                textArea.setText("************************************************\n");
                textArea.setText(textArea.getText() + "****** HOTEL CRESCENT BOOKING RECEIPT ******\n");
                textArea.setText(textArea.getText() + "************************************************\n\n");
                textArea.setText(textArea.getText() + "Date & Time: " + sf.format(dt) + "\n\n");
                textArea.setText(textArea.getText() + "Guest Name: " + name.getText() + "\n\n");
                textArea.setText(textArea.getText() + "Address: " + addr.getText() + "\n\n");
                textArea.setText(textArea.getText() + "Phone: " + phone.getText() + "\n\n");
                textArea.setText(textArea.getText() + "Room Number: " + rno.getText() + "\n\n");
                textArea.setText(textArea.getText() + "Room Type: " + roomtype.getText() + "\n\n");
                textArea.setText(textArea.getText() + "Bed Type: " + bedtype.getText() + "\n\n");
                textArea.setText(textArea.getText() + "Days: " + days.getText() + "\n\n");
                textArea.setText(textArea.getText() + "TOTAL AMOUNT: ₹" + tot.getText() + "\n\n");
                textArea.setText(textArea.getText() + "Thank you for choosing Hotel Crescent!\n");
                addToDatabase();
                flag = 1;
            }
        });
        buttonPanel.add(btnGenerateReceipt);
        
        btnCheckout = createStyledButton("CHECKOUT", new Color(255, 140, 0), 490, 20, 200, 40);
        btnCheckout.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                checkOut();
            }
        });
        buttonPanel.add(btnCheckout);
        
        btnBack = createStyledButton("BACK TO MENU", new Color(220, 20, 60), 1060, 20, 200, 40);
        btnBack.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                SecondPage sp = new SecondPage();
                sp.setVisible(true);
                sp.pack();
                sp.setLocationRelativeTo(null);
                sp.setBounds(100, 100, 1015, 574);
                setVisible(false);
            }
        });
        buttonPanel.add(btnBack);
        
        a1 = new JLabel("*");
        a1.setFont(new Font("Arial", Font.BOLD, 20));
        a1.setForeground(Color.RED);
        a1.setBounds(415, 65, 22, 25);
        customerPanel.add(a1);
        
        a2 = new JLabel("*");
        a2.setFont(new Font("Arial", Font.BOLD, 20));
        a2.setForeground(Color.RED);
        a2.setBounds(415, 135, 22, 25);
        customerPanel.add(a2);
        
        a3 = new JLabel("*");
        a3.setForeground(Color.RED);
        a3.setFont(new Font("Arial", Font.BOLD, 20));
        a3.setBounds(415, 205, 22, 25);
        customerPanel.add(a3);
        
        JPanel statusBar = new JPanel();
        statusBar.setBackground(new Color(139, 69, 19));
        statusBar.setBounds(0, 740, 1400, 30);
        statusBar.setLayout(null);
        contentPane.add(statusBar);
        
        JLabel lblStatus = new JLabel("Hotel Crescent Room Booking System | Select a room from the table to view details");
        lblStatus.setForeground(Color.WHITE);
        lblStatus.setFont(new Font("Arial", Font.BOLD, 12));
        lblStatus.setBounds(20, 5, 800, 20);
        statusBar.add(lblStatus);
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
    
    public void displayRooms() {
        GetConnection connect = new GetConnection();
        Connection conn = connect.getConnection();
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("ROOM NO");
        model.addColumn("ROOM TYPE");
        model.addColumn("BED TYPE");
        model.addColumn("PRICE");
        try {
            String query = "SELECT * FROM room";
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(query);
            while (rs.next()) {
                model.addRow(new Object[]{
                    rs.getString("roomNo"),
                    rs.getString("roomType"),
                    rs.getString("bedType"),
                    rs.getString("Price")
                });
            }
            rs.close();
            st.close();
            conn.close();
            table.setModel(model);
            table.setAutoResizeMode(0);
            table.getColumnModel().getColumn(0).setPreferredWidth(80);
            table.getColumnModel().getColumn(1).setPreferredWidth(140);
            table.getColumnModel().getColumn(2).setPreferredWidth(120);
            table.getColumnModel().getColumn(3).setPreferredWidth(90);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void calculateTotal() {
        String daysText = days.getText();
        String priceText = price.getText();

        if (daysText.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter the number of days.", "Input Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (priceText.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please select a room from the table first.", "Input Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            int day = Integer.parseInt(daysText);
            int pr = Integer.parseInt(priceText);
            
            int total = day * pr;
            tot.setText(Integer.toString(total));

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Please enter a valid number for days.", "Invalid Input", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public void addToDatabase() {
        String custName = name.getText();
        String custAddr = addr.getText();
        String custPhone = phone.getText();

        Connection conn = null;
        PreparedStatement psCheck = null;
        PreparedStatement psInsertCustomer = null;
        PreparedStatement psInsertBooking = null;
        ResultSet rs = null;

        try {
            conn = new GetConnection().getConnection();
            conn.setAutoCommit(false);

            String checkSql = "SELECT COUNT(*) FROM customer WHERE Name = ?";
            psCheck = conn.prepareStatement(checkSql);
            psCheck.setString(1, custName);
            rs = psCheck.executeQuery();
            int customerCount = 0;
            if (rs.next()) {
                customerCount = rs.getInt(1);
            }

            if (customerCount == 0) {
                String customerSql = "INSERT INTO customer (Name, Address, Phone, Type) VALUES (?, ?, ?, ?)";
                psInsertCustomer = conn.prepareStatement(customerSql);
                psInsertCustomer.setString(1, custName);
                psInsertCustomer.setString(2, custAddr);
                psInsertCustomer.setString(3, custPhone);
                psInsertCustomer.setString(4, "Room");
                psInsertCustomer.executeUpdate();
                System.out.println("New customer '" + custName + "' added to the main customer table.");
            }

            // ✅ THIS IS THE LINE WITH THE FINAL FIX
            String bookingSql = "INSERT INTO roomcutomer (name, address, phone, roomtype, bedtype, price, status, roomno) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            psInsertBooking = conn.prepareStatement(bookingSql);
            psInsertBooking.setString(1, custName);
            psInsertBooking.setString(2, custAddr);
            psInsertBooking.setString(3, custPhone);
            psInsertBooking.setString(4, roomtype.getText());
            psInsertBooking.setString(5, bedtype.getText());
            psInsertBooking.setString(6, tot.getText());
            psInsertBooking.setString(7, "0"); 
            psInsertBooking.setString(8, rno.getText());
            
            int bookingSuccess = psInsertBooking.executeUpdate();

            if (bookingSuccess > 0) {
                conn.commit();
                JOptionPane.showMessageDialog(null, "✅ Room booking confirmed! Welcome to Hotel Crescent.");
            } else {
                conn.rollback();
                JOptionPane.showMessageDialog(null, "Booking failed. Please try again.", "Error", JOptionPane.ERROR_MESSAGE);
            }

        } catch (SQLException ex) {
            try {
                if (conn != null) conn.rollback();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "A database error occurred. The booking was not saved.", "Database Error", JOptionPane.ERROR_MESSAGE);
        } finally {
            try {
                if (rs != null) rs.close();
                if (psCheck != null) psCheck.close();
                if (psInsertCustomer != null) psInsertCustomer.close();
                if (psInsertBooking != null) psInsertBooking.close();
                if (conn != null) {
                    conn.setAutoCommit(true);
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    
    public void checkOut() {
        PreparedStatement ps = null;
        try {
            GetConnection connect = new GetConnection();
            Connection conn = connect.getConnection();
            ps = conn.prepareStatement("UPDATE roomcutomer SET status = 1 where name=?");
            ps.setString(1, name.getText());
            if (ps.executeUpdate() > 0) {
                JOptionPane.showMessageDialog(null, "✅ Checked out successfully! Thank you for staying with us.");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}