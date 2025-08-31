package hotel;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class OnlineDataViewer extends JFrame {
    
    private JTable bookingsTable;
    private JTable ordersTable;
    private JLabel statusLabel;
    private ApiClient apiClient;
    private Timer autoRefreshTimer;
    
    public OnlineDataViewer() {
        apiClient = new ApiClient();
        initComponents();
        loadData();
        startAutoRefresh();
    }
    
    private void initComponents() {
        setTitle("Online Bookings & Orders - Manager View");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(1200, 700);
        setLocationRelativeTo(null);
        
        // Create tabbed pane
        JTabbedPane tabbedPane = new JTabbedPane();
        
        // Bookings tab
        bookingsTable = new JTable();
        bookingsTable.setRowHeight(25);
        bookingsTable.setFont(new Font("Arial", Font.PLAIN, 12));
        JScrollPane bookingsScroll = new JScrollPane(bookingsTable);
        tabbedPane.addTab("Room Bookings", bookingsScroll);
        
        // Orders tab
        ordersTable = new JTable();
        ordersTable.setRowHeight(25);
        ordersTable.setFont(new Font("Arial", Font.PLAIN, 12));
        JScrollPane ordersScroll = new JScrollPane(ordersTable);
        tabbedPane.addTab(" Food Orders", ordersScroll);
        
        // Control panel
        JPanel controlPanel = new JPanel(new FlowLayout());
        controlPanel.setBackground(new Color(0, 153, 204));
        
        JButton refreshButton = new JButton("Refresh Now");
        refreshButton.addActionListener(e -> loadData());
        refreshButton.setFont(new Font("Arial", Font.BOLD, 12));
        
        statusLabel = new JLabel("Loading data...");
        statusLabel.setForeground(Color.WHITE);
        statusLabel.setFont(new Font("Arial", Font.BOLD, 12));
        
        controlPanel.add(refreshButton);
        controlPanel.add(Box.createHorizontalStrut(20));
        controlPanel.add(statusLabel);
        
        // Layout
        setLayout(new BorderLayout());
        add(controlPanel, BorderLayout.NORTH);
        add(tabbedPane, BorderLayout.CENTER);
        
        // Bottom info panel
        JPanel infoPanel = new JPanel();
        infoPanel.setBackground(new Color(240, 240, 240));
        JLabel infoLabel = new JLabel("Connected to Spring Boot Server (localhost:8080) - Auto-refresh every 30 seconds");
        infoLabel.setFont(new Font("Arial", Font.ITALIC, 11));
        infoPanel.add(infoLabel);
        add(infoPanel, BorderLayout.SOUTH);
    }
    
    private void loadData() {
        SwingUtilities.invokeLater(() -> {
            statusLabel.setText("Loading data...");
        });
        
        // Load in background thread
        new Thread(() -> {
            try {
                List<OnlineBooking> bookings = apiClient.getAllBookings();
                List<OnlineFoodOrder> orders = apiClient.getAllFoodOrders();
                
                SwingUtilities.invokeLater(() -> {
                    updateBookingsTable(bookings);
                    updateOrdersTable(orders);
                    statusLabel.setText("Updated: " + java.time.LocalTime.now().toString().substring(0, 8) + 
                                      " | Bookings: " + bookings.size() + " | Orders: " + orders.size());
                });
                
            } catch (Exception e) {
                SwingUtilities.invokeLater(() -> {
                    statusLabel.setText("Connection Error - Check if Spring Boot server is running");
                });
                e.printStackTrace();
            }
        }).start();
    }
    
    private void updateBookingsTable(List<OnlineBooking> bookings) {
        String[] columns = {"ID", "Customer", "Email", "Room", "Check-in", "Check-out", "Total (₹)", "Status", "Date"};
        Object[][] data = new Object[bookings.size()][9];
        
        for (int i = 0; i < bookings.size(); i++) {
            OnlineBooking b = bookings.get(i);
            data[i][0] = b.id;
            data[i][1] = b.customerName;
            data[i][2] = b.customerEmail;
            data[i][3] = "Room " + b.roomId;
            data[i][4] = b.checkIn;
            data[i][5] = b.checkOut;
            data[i][6] = "₹" + String.format("%.2f", b.totalPrice);
            data[i][7] = b.status;
            data[i][8] = b.bookingDate;
        }
        
        DefaultTableModel model = new DefaultTableModel(data, columns);
        bookingsTable.setModel(model);
    }
    
    private void updateOrdersTable(List<OnlineFoodOrder> orders) {
        String[] columns = {"ID", "Customer", "Email", "Food Item", "Qty", "Total (₹)", "Status", "Date", "Instructions"};
        Object[][] data = new Object[orders.size()][9];
        
        for (int i = 0; i < orders.size(); i++) {
            OnlineFoodOrder o = orders.get(i);
            data[i][0] = o.id;
            data[i][1] = o.customerName;
            data[i][2] = o.customerEmail;
            data[i][3] = o.foodName;
            data[i][4] = o.quantity;
            data[i][5] = "₹" + String.format("%.2f", o.totalPrice);
            data[i][6] = o.status;
            data[i][7] = o.orderDate.substring(0, 16); // Show only date and time
            data[i][8] = o.specialInstructions != null ? o.specialInstructions : "";
        }
        
        DefaultTableModel model = new DefaultTableModel(data, columns);
        ordersTable.setModel(model);
    }
    
    private void startAutoRefresh() {
        autoRefreshTimer = new Timer(true);
        autoRefreshTimer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                loadData();
            }
        }, 30000, 30000); // Refresh every 30 seconds
    }
    
    @Override
    public void dispose() {
        if (autoRefreshTimer != null) {
            autoRefreshTimer.cancel();
        }
        super.dispose();
    }
}
