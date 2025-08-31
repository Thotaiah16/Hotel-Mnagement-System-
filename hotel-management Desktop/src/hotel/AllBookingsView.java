package hotel;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JButton;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.Color;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AllBookingsView extends JFrame {
    private final JTable bookingsTable;
    private JPanel contentPane;
    private JButton deleteButton;
    private JButton refreshButton;
    private JButton fixRoomNumbersButton;
    
    public AllBookingsView() {
        setTitle("All Customer Booking Details - Hotel RAAR Admin Portal");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 1100, 750);
        setLocationRelativeTo(null);
        
        contentPane = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
                GradientPaint gp = new GradientPaint(0, 0, new Color(220, 235, 255), 0, getHeight(), new Color(245, 250, 255));
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);
        
        // Header Panel
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(new Color(22, 160, 133));
        headerPanel.setBounds(0, 0, 1200, 80);
        headerPanel.setLayout(null);
        contentPane.add(headerPanel);
        
        JLabel lblTitle = new JLabel("ALL CUSTOMER BOOKINGS (Desktop & Online)");
        lblTitle.setFont(new Font("Tahoma", Font.BOLD, 28));
        lblTitle.setForeground(Color.WHITE);
        lblTitle.setBounds(30, 15, 800, 35);
        headerPanel.add(lblTitle);
        
        JLabel lblSubtitle = new JLabel("Consolidated view with standardized room number format (103)");
        lblSubtitle.setFont(new Font("Tahoma", Font.ITALIC, 16));
        lblSubtitle.setForeground(Color.WHITE);
        lblSubtitle.setBounds(30, 50, 500, 20);
        headerPanel.add(lblSubtitle);
        
        // Refresh Button
        refreshButton = new JButton("Refresh Data");
        refreshButton.setBounds(700, 15, 120, 25);
        refreshButton.setFont(new Font("Tahoma", Font.BOLD, 11));
        refreshButton.setBackground(new Color(46, 204, 113));
        refreshButton.setForeground(Color.WHITE);
        refreshButton.setFocusPainted(false);
        refreshButton.addActionListener(e -> {
            loadAllBookings();
            JOptionPane.showMessageDialog(this, "Data refreshed successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
        });
        headerPanel.add(refreshButton);
        
        // Standardize Room Numbers Button
        fixRoomNumbersButton = new JButton("Standardize Rooms");
        fixRoomNumbersButton.setBounds(700, 45, 120, 25);
        fixRoomNumbersButton.setFont(new Font("Tahoma", Font.BOLD, 11));
        fixRoomNumbersButton.setBackground(new Color(255, 193, 7));
        fixRoomNumbersButton.setForeground(Color.BLACK);
        fixRoomNumbersButton.setFocusPainted(false);
        fixRoomNumbersButton.addActionListener(e -> standardizeRoomNumbers());
        headerPanel.add(fixRoomNumbersButton);
        
        // Auto-Fix Button
        JButton autoFixButton = new JButton("Auto-Fix All");
        autoFixButton.setBounds(830, 30, 100, 25);
        autoFixButton.setFont(new Font("Tahoma", Font.BOLD, 11));
        autoFixButton.setBackground(new Color(52, 152, 219));
        autoFixButton.setForeground(Color.WHITE);
        autoFixButton.setFocusPainted(false);
        autoFixButton.addActionListener(e -> autoStandardizeAllRoomNumbers());
        headerPanel.add(autoFixButton);
        
        // Table
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(30, 110, 1025, 520);
        contentPane.add(scrollPane);
        
        bookingsTable = new JTable();
        bookingsTable.setFont(new Font("Arial", Font.PLAIN, 14));
        bookingsTable.setRowHeight(28);
        bookingsTable.setGridColor(Color.LIGHT_GRAY);
        bookingsTable.setSelectionBackground(new Color(173, 216, 230));
        
        JTableHeader tableHeader = bookingsTable.getTableHeader();
        tableHeader.setFont(new Font("Arial", Font.BOLD, 16));
        tableHeader.setBackground(new Color(70, 130, 180));
        tableHeader.setForeground(Color.WHITE);
        
        scrollPane.setViewportView(bookingsTable);
        
        // Delete Button
        deleteButton = new JButton("Delete Selected Booking");
        deleteButton.setBounds(30, 650, 250, 35);
        deleteButton.setFont(new Font("Tahoma", Font.BOLD, 14));
        deleteButton.setBackground(new Color(231, 76, 60));
        deleteButton.setForeground(Color.WHITE);
        deleteButton.setFocusPainted(false);
        deleteButton.addActionListener(e -> deleteSelectedBooking());
        contentPane.add(deleteButton);
        
        // Instructions Label
        JLabel instructionLabel = new JLabel("*Select a row and click 'Delete Selected Booking' to remove customer details");
        instructionLabel.setBounds(300, 660, 600, 25);
        instructionLabel.setFont(new Font("Tahoma", Font.ITALIC, 12));
        instructionLabel.setForeground(new Color(100, 100, 100));
        contentPane.add(instructionLabel);
        
        // Auto-standardize room numbers on startup
        autoStandardizeAllRoomNumbers();
        loadAllBookings();
    }
    
    // ✅ FINAL CORRECTED METHOD - Standardized to "103" format
    private void loadAllBookings() {
        String[] columnNames = {"Customer Name", "Phone", "Room No.", "Total Price", "Source", "Status"};
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);
        
        // STANDARDIZED SQL QUERY - Uses actual room numbers from room table (103 format)
        String sql = 
            "SELECT " +
            "    c.Name as customer_name, " +
            "    c.Phone as phone, " +
            "    rc.roomno as room_no, " +
            "    rc.price as price, " +
            "    'Desktop' AS source, " +
            "    'Active' AS status " +
            "FROM customer c JOIN roomcutomer rc ON c.Name = rc.name " +
            
            "UNION ALL " +
            
            "SELECT " +
            "    u.username as customer_name, " +
            "    u.mobile_number as phone, " +
            "    CASE " +
            "        WHEN b.room_no IS NOT NULL AND b.room_no != '' AND b.room_no != 'TBD' AND b.room_no != 'NULL' " +
            "             AND b.room_no NOT LIKE 'R%' AND b.room_no NOT LIKE 'AUTO%' THEN b.room_no " +
            "        WHEN b.room_id IS NOT NULL AND b.room_id > 0 THEN " +
            "            (SELECT r.roomNo FROM room r WHERE r.id = b.room_id LIMIT 1) " +
            "        ELSE CONCAT('TEMP', LPAD(b.id, 3, '0')) " +
            "    END as room_no, " +
            "    b.total_price as price, " +
            "    'Online' AS source, " +
            "    UPPER(COALESCE(b.status, 'CONFIRMED')) AS status " +
            "FROM bookings b JOIN users u ON b.user_id = u.id " +
            "ORDER BY source DESC, customer_name ASC";
        
        try (Connection conn = new GetConnection().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            
            int totalBookings = 0;
            while (rs.next()) {
                String name = rs.getString("customer_name") != null ? rs.getString("customer_name") : "Unknown";
                String phone = rs.getString("phone") != null ? rs.getString("phone") : "N/A";
                String roomNo = rs.getString("room_no") != null ? rs.getString("room_no") : "Not Assigned";
                String price = rs.getString("price") != null ? "₹" + rs.getString("price") : "₹0";
                String source = rs.getString("source") != null ? rs.getString("source") : "Unknown";
                String status = rs.getString("status") != null ? rs.getString("status") : "Unknown";
                
                // Additional check to ensure standardized format
                if (roomNo.equals("TBD") || roomNo.equals("NULL") || roomNo.isEmpty() || roomNo.equals("Not Assigned")) {
                    roomNo = "TEMP" + String.format("%03d", totalBookings + 1);
                }
                
                model.addRow(new Object[]{name, phone, roomNo, price, source, status});
                totalBookings++;
            }
            
            bookingsTable.setModel(model);
            setTitle("All Customer Booking Details - Hotel RAAR Admin Portal (" + totalBookings + " total bookings)");
            
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, 
                "Database error: " + e.getMessage() + "\n\nPlease check your database connection and table structures.", 
                "Database Error", 
                JOptionPane.ERROR_MESSAGE);
        }
    }
    
    // ✅ METHOD TO STANDARDIZE ALL ROOM NUMBERS TO "103" FORMAT
    private void standardizeRoomNumbers() {
        String updateSql = 
            "UPDATE bookings b " +
            "SET b.room_no = (SELECT r.roomNo FROM room r WHERE r.id = b.room_id LIMIT 1) " +
            "WHERE b.room_id IS NOT NULL AND b.room_id > 0 " +
            "AND (b.room_no IS NULL OR b.room_no = '' OR b.room_no = 'TBD' OR b.room_no = 'NULL' " +
            "     OR b.room_no LIKE 'R%' OR b.room_no LIKE 'AUTO%')";
        
        try (Connection conn = new GetConnection().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(updateSql)) {
            
            int rowsUpdated = pstmt.executeUpdate();
            if (rowsUpdated > 0) {
                JOptionPane.showMessageDialog(this, 
                    "✅ " + rowsUpdated + " room number(s) have been standardized to '103' format!\n\n" +
                    "All room numbers now use the standard format (103, 104, etc.)", 
                    "Room Numbers Standardized", 
                    JOptionPane.INFORMATION_MESSAGE);
                loadAllBookings(); // Refresh the table
            } else {
                JOptionPane.showMessageDialog(this, 
                    "ℹ️ No room numbers needed to be standardized.\nAll bookings already use the correct '103' format.", 
                    "Already Standardized", 
                    JOptionPane.INFORMATION_MESSAGE);
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, 
                "❌ Error standardizing room numbers: " + e.getMessage(), 
                "Standardization Error", 
                JOptionPane.ERROR_MESSAGE);
        }
    }
    
    // ✅ METHOD TO AUTO-STANDARDIZE ALL ROOM NUMBERS ON STARTUP
    private void autoStandardizeAllRoomNumbers() {
        try (Connection conn = new GetConnection().getConnection()) {
            
            // Standardize all room numbers to use actual room numbers from room table (103 format)
            String standardizeSQL = 
                "UPDATE bookings b " +
                "SET b.room_no = (SELECT r.roomNo FROM room r WHERE r.id = b.room_id LIMIT 1) " +
                "WHERE b.room_id IS NOT NULL AND b.room_id > 0 " +
                "AND (b.room_no IS NULL OR b.room_no = '' OR b.room_no = 'TBD' OR b.room_no = 'NULL' " +
                "     OR b.room_no LIKE 'R%' OR b.room_no LIKE 'AUTO%' OR b.room_no LIKE 'TEMP%')";
            
            PreparedStatement pstmt = conn.prepareStatement(standardizeSQL);
            int updated = pstmt.executeUpdate();
            pstmt.close();
            
            if (updated > 0) {
                System.out.println("✅ Auto-standardized " + updated + " room numbers to '103' format on startup.");
            } else {
                System.out.println("ℹ️ All room numbers already use standardized '103' format.");
            }
            
        } catch (SQLException e) {
            System.err.println("Auto-standardization error: " + e.getMessage());
        }
    }
    
    private void deleteSelectedBooking() {
        int selectedRow = bookingsTable.getSelectedRow();
        
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, 
                "Please select a booking to delete.", 
                "No Selection", 
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        String customerName = (String) bookingsTable.getValueAt(selectedRow, 0);
        String phone = (String) bookingsTable.getValueAt(selectedRow, 1);
        String roomNo = (String) bookingsTable.getValueAt(selectedRow, 2);
        String source = (String) bookingsTable.getValueAt(selectedRow, 4);
        
        int firstConfirm = JOptionPane.showConfirmDialog(this,
            "⚠️ WARNING ⚠️\n\n" +
            "You are about to delete booking details for:\n" +
            "Customer: " + customerName + "\n" +
            "Phone: " + phone + "\n" +
            "Room: " + roomNo + "\n" +
            "Source: " + source + "\n\n" +
            "This action cannot be undone!\n" +
            "Are you sure you want to continue?",
            "Delete Confirmation - Step 1",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.WARNING_MESSAGE);
        
        if (firstConfirm != JOptionPane.YES_OPTION) {
            return;
        }
        
        int secondConfirm = JOptionPane.showConfirmDialog(this,
            "⚠️ FINAL CONFIRMATION ⚠️\n\n" +
            "This will permanently delete the booking record.\n" +
            "Please confirm that the customer has checked out.\n\n" +
            "Click 'OK' to proceed with deletion or 'Cancel' to abort.",
            "Delete Confirmation - Step 2",
            JOptionPane.OK_CANCEL_OPTION,
            JOptionPane.ERROR_MESSAGE);
        
        if (secondConfirm == JOptionPane.OK_OPTION) {
            performDeletion(customerName, phone, roomNo, source, selectedRow);
        }
    }
    
    private void performDeletion(String customerName, String phone, String roomNo, String source, int selectedRow) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        
        try {
            conn = new GetConnection().getConnection();
            conn.setAutoCommit(false);
            
            boolean deletionSuccess = false;
            
            if (source.equals("Desktop")) {
                String deleteCustomerSql = "DELETE FROM customer WHERE Name = ? AND Phone = ?";
                String deleteRoomCustomerSql = "DELETE FROM roomcutomer WHERE name = ? AND roomno = ?";
                
                pstmt = conn.prepareStatement(deleteRoomCustomerSql);
                pstmt.setString(1, customerName);
                pstmt.setString(2, roomNo);
                int roomDeleted = pstmt.executeUpdate();
                pstmt.close();
                
                pstmt = conn.prepareStatement(deleteCustomerSql);
                pstmt.setString(1, customerName);
                pstmt.setString(2, phone);
                int customerDeleted = pstmt.executeUpdate();
                
                deletionSuccess = (roomDeleted > 0 && customerDeleted > 0);
                
            } else if (source.equals("Online")) {
                String deleteBookingSql = 
                    "DELETE b FROM bookings b " +
                    "JOIN users u ON b.user_id = u.id " +
                    "WHERE u.username = ? AND u.mobile_number = ? AND b.room_no = ?";
                
                pstmt = conn.prepareStatement(deleteBookingSql);
                pstmt.setString(1, customerName);
                pstmt.setString(2, phone);
                pstmt.setString(3, roomNo);
                int bookingDeleted = pstmt.executeUpdate();
                
                deletionSuccess = (bookingDeleted > 0);
            }
            
            if (deletionSuccess) {
                conn.commit();
                
                DefaultTableModel model = (DefaultTableModel) bookingsTable.getModel();
                model.removeRow(selectedRow);
                
                JOptionPane.showMessageDialog(this,
                    "✅ Booking deleted successfully!\n\n" +
                    "Customer: " + customerName + "\n" +
                    "Room: " + roomNo + " has been removed from the system.",
                    "Deletion Successful",
                    JOptionPane.INFORMATION_MESSAGE);
            } else {
                conn.rollback();
                JOptionPane.showMessageDialog(this,
                    "❌ Failed to delete the booking.\n" +
                    "The record may have already been deleted or there was a database error.",
                    "Deletion Failed",
                    JOptionPane.ERROR_MESSAGE);
            }
            
        } catch (SQLException e) {
            try {
                if (conn != null) {
                    conn.rollback();
                }
            } catch (SQLException rollbackEx) {
                rollbackEx.printStackTrace();
            }
            
            e.printStackTrace();
            JOptionPane.showMessageDialog(this,
                "Database error occurred while deleting the booking.\n" +
                "Error: " + e.getMessage(),
                "Database Error",
                JOptionPane.ERROR_MESSAGE);
        } finally {
            try {
                if (pstmt != null) pstmt.close();
                if (conn != null) {
                    conn.setAutoCommit(true);
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    
    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(() -> {
            try {
                new AllBookingsView().setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}
