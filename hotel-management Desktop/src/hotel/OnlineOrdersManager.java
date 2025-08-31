package hotel;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.ActionListener;

public class OnlineOrdersManager extends JFrame {
    private JTable onlineBookingsTable;
    private JPanel contentPane;
    private JButton refreshButton;
    private JButton fixRoomNumbersButton;
    private JLabel statusLabel;
    
    public OnlineOrdersManager() {
        System.out.println("OnlineOrdersManager created successfully!");
        initializeUI();
        fixNullRoomNumbers(); 
        loadOnlineBookingsWithRooms();
    }
    
    public void initialize() {
        System.out.println("Controller initialized");
    }
    
    private void initializeUI() {
        setTitle("Online Bookings Manager - Hotel RAAR");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 1300, 750);
        setLocationRelativeTo(null);
        
        contentPane = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
                GradientPaint gp = new GradientPaint(0, 0, new Color(135, 206, 235), 0, getHeight(), new Color(240, 248, 255));
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        contentPane.setLayout(null);
        setContentPane(contentPane);
        
        
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(new Color(30, 144, 255));
        headerPanel.setBounds(0, 0, 1300, 80);
        headerPanel.setLayout(null);
        contentPane.add(headerPanel);
        
        JLabel lblTitle = new JLabel("ONLINE BOOKINGS MANAGER");
        lblTitle.setFont(new Font("Arial", Font.BOLD, 28));
        lblTitle.setForeground(Color.WHITE);
        lblTitle.setBounds(30, 15, 500, 35);
        headerPanel.add(lblTitle);
        
        JLabel lblSubtitle = new JLabel("Complete management with automatic room number assignment");
        lblSubtitle.setFont(new Font("Arial", Font.ITALIC, 16));
        lblSubtitle.setForeground(Color.WHITE);
        lblSubtitle.setBounds(30, 50, 600, 20);
        headerPanel.add(lblSubtitle);
        
        // Refresh Button
        refreshButton = new JButton("Refresh Data");
        refreshButton.setBounds(900, 15, 150, 25);
        refreshButton.setFont(new Font("Arial", Font.BOLD, 12));
        refreshButton.setBackground(new Color(46, 204, 113));
        refreshButton.setForeground(Color.WHITE);
        refreshButton.setFocusPainted(false);
        refreshButton.addActionListener(e -> {
            loadOnlineBookingsWithRooms();
            JOptionPane.showMessageDialog(this, "Data refreshed successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
        });
        headerPanel.add(refreshButton);
        
        // Fix Room Numbers Button
        fixRoomNumbersButton = new JButton("Fix Room Numbers");
        fixRoomNumbersButton.setBounds(900, 45, 150, 25);
        fixRoomNumbersButton.setFont(new Font("Arial", Font.BOLD, 12));
        fixRoomNumbersButton.setBackground(new Color(255, 193, 7));
        fixRoomNumbersButton.setForeground(Color.BLACK);
        fixRoomNumbersButton.setFocusPainted(false);
        fixRoomNumbersButton.addActionListener(e -> fixNullRoomNumbers());
        headerPanel.add(fixRoomNumbersButton);
        
        // Auto-Assign Button
        JButton autoAssignButton = new JButton("Auto-Assign Rooms");
        autoAssignButton.setBounds(1070, 30, 150, 25);
        autoAssignButton.setFont(new Font("Arial", Font.BOLD, 12));
        autoAssignButton.setBackground(new Color(52, 152, 219));
        autoAssignButton.setForeground(Color.WHITE);
        autoAssignButton.setFocusPainted(false);
        autoAssignButton.addActionListener(e -> autoAssignRoomNumbers());
        headerPanel.add(autoAssignButton);
        
        // Table
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(30, 110, 1230, 550);
        contentPane.add(scrollPane);
        
        onlineBookingsTable = new JTable();
        onlineBookingsTable.setFont(new Font("Arial", Font.PLAIN, 12));
        onlineBookingsTable.setRowHeight(30);
        onlineBookingsTable.setGridColor(Color.LIGHT_GRAY);
        onlineBookingsTable.setSelectionBackground(new Color(173, 216, 230));
        
        JTableHeader tableHeader = onlineBookingsTable.getTableHeader();
        tableHeader.setFont(new Font("Arial", Font.BOLD, 14));
        tableHeader.setBackground(new Color(70, 130, 180));
        tableHeader.setForeground(Color.WHITE);
        
        scrollPane.setViewportView(onlineBookingsTable);
        
        // Status Label
        statusLabel = new JLabel("Loading online bookings...");
        statusLabel.setBounds(30, 680, 800, 25);
        statusLabel.setFont(new Font("Arial", Font.BOLD, 16));
        statusLabel.setForeground(new Color(70, 130, 180));
        contentPane.add(statusLabel);
    }
    
    
    public List<OnlineBooking> getAllOnlineBookingsWithRoomNumbers() {
        List<OnlineBooking> bookings = new ArrayList<>();
        
        String sql = "SELECT b.id, b.user_id, b.room_id, b.check_in, b.check_out, " +
                    "b.booking_date, b.status, b.total_price, " +
                    "u.username as customer_name, u.email as customer_email, " +
                    "u.mobile_number as customer_phone, " +
                    "COALESCE(b.room_no, CONCAT('R', LPAD(b.room_id, 3, '0')), 'Not Assigned') as room_number " +
                    "FROM bookings b " +
                    "JOIN users u ON b.user_id = u.id " +
                    "ORDER BY b.booking_date DESC";
        
        try (Connection conn = new GetConnection().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            
            while (rs.next()) {
                OnlineBooking booking = new OnlineBooking();
                booking.id = rs.getLong("id");
                booking.userId = rs.getInt("user_id");
                booking.roomId = rs.getInt("room_id");
                booking.checkIn = rs.getString("check_in");
                booking.checkOut = rs.getString("check_out");
                booking.bookingDate = rs.getString("booking_date");
                booking.status = rs.getString("status");
                booking.totalPrice = rs.getDouble("total_price");
                booking.customerName = rs.getString("customer_name");
                booking.customerEmail = rs.getString("customer_email");
                booking.customerPhone = rs.getString("customer_phone");
                booking.roomNumber = rs.getString("room_number");
                
                bookings.add(booking);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Database error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
        
        return bookings;
    }
    
    private void loadOnlineBookingsWithRooms() {
        String[] columnNames = {
            "ID", "Customer Name", "Phone", "Email", "Room Number", 
            "Check-In", "Check-Out", "Booking Date", "Status", "Total Price"
        };
        
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);
        List<OnlineBooking> bookings = getAllOnlineBookingsWithRoomNumbers();
        
        for (OnlineBooking booking : bookings) {
            Object[] rowData = {
                booking.id,
                booking.customerName != null ? booking.customerName : "N/A",
                booking.customerPhone != null ? booking.customerPhone : "N/A",
                booking.customerEmail != null ? booking.customerEmail : "N/A",
                booking.roomNumber != null ? booking.roomNumber : "Not Assigned",
                booking.checkIn != null ? booking.checkIn : "N/A",
                booking.checkOut != null ? booking.checkOut : "N/A",
                booking.bookingDate != null ? booking.bookingDate : "N/A",
                booking.status != null ? booking.status.toUpperCase() : "UNKNOWN",
                booking.totalPrice != null ? "₹" + booking.totalPrice : "₹0"
            };
            model.addRow(rowData);
        }
        
        onlineBookingsTable.setModel(model);
        statusLabel.setText("Total Online Bookings: " + bookings.size() + " | All room numbers mapped successfully!");
    }
    
    private void fixNullRoomNumbers() {
        String updateSql = "UPDATE bookings SET room_no = CONCAT('R', LPAD(room_id, 3, '0')) " +
                          "WHERE (room_no IS NULL OR room_no = '') AND room_id IS NOT NULL";
        
        try (Connection conn = new GetConnection().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(updateSql)) {
            
            int rowsUpdated = pstmt.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Fixed " + rowsUpdated + " room number mappings automatically.");
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    private void autoAssignRoomNumbers() {
        String updateSql = "UPDATE bookings b " +
                          "SET room_no = CASE " +
                          "    WHEN b.room_id IS NOT NULL THEN CONCAT('R', LPAD(b.room_id, 3, '0')) " +
                          "    ELSE CONCAT('AUTO', LPAD(b.id, 3, '0')) " +
                          "END " +
                          "WHERE room_no IS NULL OR room_no = '' OR room_no = 'Not Assigned'";
        
        try (Connection conn = new GetConnection().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(updateSql)) {
            
            int rowsUpdated = pstmt.executeUpdate();
            JOptionPane.showMessageDialog(this, 
                "🏨 " + rowsUpdated + " bookings have been auto-assigned room numbers!", 
                "Auto-Assignment Complete", 
                JOptionPane.INFORMATION_MESSAGE);
            loadOnlineBookingsWithRooms();
            
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, 
                "❌ Error during auto-assignment: " + e.getMessage(), 
                "Auto-Assignment Error", 
                JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                new OnlineOrdersManager().setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}
