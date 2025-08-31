package hotel;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import javax.swing.JOptionPane;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GradientPaint;
import java.awt.RenderingHints;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import java.awt.Color;
import java.awt.Cursor;
import javax.swing.SwingConstants;
import javax.swing.BorderFactory;

public class AdminForm extends JFrame {

    private JPanel contentPane;

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    AdminForm frame = new AdminForm();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public AdminForm() {
        setTitle("Hotel RAAR - Admin Dashboard");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setResizable(true);
        
        // Main panel with professional gradient
        contentPane = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
                
                GradientPaint gp = new GradientPaint(0, 0, new Color(240, 248, 255), 0, getHeight(), new Color(176, 196, 222));
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);
        
        // Header panel
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(new Color(0, 153, 204));
        headerPanel.setBounds(0, 0, 1920, 120);
        headerPanel.setLayout(null);
        contentPane.add(headerPanel);
        
        // Header title
        JLabel lblTitle = new JLabel("🏨 HOTEL RAAR ADMIN PORTAL");
        lblTitle.setFont(new Font("Serif", Font.BOLD, 36));
        lblTitle.setForeground(Color.WHITE);
        lblTitle.setBounds(50, 25, 700, 45);
        headerPanel.add(lblTitle);
        
        JLabel lblSubtitle = new JLabel("Professional Hotel Management System - Administrator Dashboard");
        lblSubtitle.setFont(new Font("Arial", Font.ITALIC, 16));
        lblSubtitle.setForeground(new Color(240, 248, 255));
        lblSubtitle.setBounds(50, 70, 600, 25);
        headerPanel.add(lblSubtitle);
        
        // Welcome label
        JLabel lblWelcome = new JLabel("Welcome, Administrator");
        lblWelcome.setFont(new Font("Arial", Font.BOLD, 18));
        lblWelcome.setForeground(Color.WHITE);
        lblWelcome.setBounds(1400, 40, 300, 30);
        lblWelcome.setHorizontalAlignment(SwingConstants.RIGHT);
        headerPanel.add(lblWelcome);
        
        
        
        // Manage Rooms button
        JButton btnManageRooms = createStyledButton("MANAGE ROOMS", "Manage hotel rooms and availability", 
            new Color(34, 139, 34), 200, 160, 320, 140);
        btnManageRooms.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    ManageRoomsForm roomsForm = new ManageRoomsForm();
                    roomsForm.setVisible(true);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(AdminForm.this, 
                        "Error opening Room Management: " + ex.getMessage(),
                        "Error", JOptionPane.ERROR_MESSAGE);
                    ex.printStackTrace();
                }
            }
        });
        contentPane.add(btnManageRooms);
        
        // Manage Dishes button
        JButton btnManageDishes = createStyledButton("MANAGE DISHES", "Manage restaurant menu and dishes", 
            new Color(255, 140, 0), 540, 160, 320, 140);
        btnManageDishes.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    ManageDishesForm dishesForm = new ManageDishesForm();
                    dishesForm.setVisible(true);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(AdminForm.this, 
                        "Error opening Dish Management: " + ex.getMessage(),
                        "Error", JOptionPane.ERROR_MESSAGE);
                    ex.printStackTrace();
                }
            }
        });
        contentPane.add(btnManageDishes);
        
        // Online Orders button
        JButton btnOnlineOrders = createStyledButton("ONLINE ORDERS", "View real-time web bookings & orders", 
            new Color(220, 20, 60), 880, 160, 320, 140); // Repositioned
        btnOnlineOrders.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    showOnlineOrders();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(AdminForm.this, 
                        "❌ Error opening online orders:\n" + ex.getMessage() + 
                        "\n\nPlease ensure:\n" +
                        "✓ Spring Boot application is running (localhost:8080)\n" +
                        "✓ All API classes are properly created\n" +
                        "✓ Network connection is available", 
                        "Connection Error", 
                        JOptionPane.ERROR_MESSAGE);
                    ex.printStackTrace();
                }
            }
        });
        contentPane.add(btnOnlineOrders);
        
        // Professional action buttons - ROW 2 (RE-ALIGNED)
        
        // Settings button - MOVED to the left
        JButton btnSettings = createStyledButton(" SETTINGS", "System configuration & preferences", 
            new Color(255, 165, 0), 200, 320, 320, 140);
        btnSettings.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                showSettingsDialog();
            }
        });
        contentPane.add(btnSettings);
        
        // Customer Management button 
        JButton btnCustomers = createStyledButton("CUSTOMERS", "View all customer booking details", 
            new Color(46, 125, 50), 540, 320, 320, 140);
        btnCustomers.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                
                AllBookingsView bookingsView = new AllBookingsView();
                bookingsView.setVisible(true);
            }
        });
        contentPane.add(btnCustomers);
        
        
        JButton btnLogout = createStyledButton("LOGOUT", "Return to login screen", 
            new Color(244, 67, 54), 880, 320, 320, 140);
        btnLogout.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int option = JOptionPane.showConfirmDialog(AdminForm.this, 
                    "Are you sure you want to logout and return to the main menu?", 
                    "Logout Confirmation", 
                    JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                if (option == JOptionPane.YES_OPTION) {
                    try {
                        SecondPage sp = new SecondPage();
                        sp.setVisible(true);
                        setVisible(false);
                    } catch (Exception ex) {
                        System.exit(0);
                    }
                }
            }
        });
        contentPane.add(btnLogout);

       
        JPanel infoPanel = new JPanel();
        infoPanel.setBackground(new Color(255, 255, 255, 200));
        infoPanel.setBounds(200, 500, 1340, 120);
        infoPanel.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(new Color(0, 153, 204), 2),
            "System Information",
            javax.swing.border.TitledBorder.LEFT,
            javax.swing.border.TitledBorder.TOP,
            new Font("Arial", Font.BOLD, 16),
            new Color(0, 153, 204)
        ));
        infoPanel.setLayout(null);
        contentPane.add(infoPanel);
        
       
        JLabel lblSystemInfo = new JLabel("🖥️ System Status: Online | Database: Connected | Hotel Management System v2.0");
        lblSystemInfo.setFont(new Font("Arial", Font.BOLD, 14));
        lblSystemInfo.setForeground(new Color(0, 153, 204));
        lblSystemInfo.setBounds(30, 30, 800, 25);
        infoPanel.add(lblSystemInfo);
        
        JLabel lblDateTime = new JLabel("Current Date & Time: " + java.time.LocalDateTime.now().toString().substring(0, 16).replace("T", " "));
        lblDateTime.setFont(new Font("Arial", Font.PLAIN, 14));
        lblDateTime.setForeground(new Color(100, 100, 100));
        lblDateTime.setBounds(30, 55, 400, 25);
        infoPanel.add(lblDateTime);
        
        JLabel lblVersion = new JLabel("⚡ Version: 2.0.1 | Last Updated: August 2025");
        lblVersion.setFont(new Font("Arial", Font.PLAIN, 14));
        lblVersion.setForeground(new Color(100, 100, 100));
        lblVersion.setBounds(30, 80, 400, 25);
        infoPanel.add(lblVersion);
        
        
        JLabel lblQuickStats = new JLabel("Today's Quick Stats");
        lblQuickStats.setFont(new Font("Arial", Font.BOLD, 14));
        lblQuickStats.setForeground(new Color(0, 153, 204));
        lblQuickStats.setBounds(900, 30, 200, 25);
        infoPanel.add(lblQuickStats);
        
        JLabel lblStats = new JLabel("<html>🛏️ Rooms Available: Check System<br/>Menu Items: Check Database<br/>Orders Today: View Reports</html>");
        lblStats.setFont(new Font("Arial", Font.PLAIN, 12));
        lblStats.setForeground(new Color(100, 100, 100));
        lblStats.setBounds(900, 50, 300, 60);
        infoPanel.add(lblStats);
        
        
        JPanel statusBar = new JPanel();
        statusBar.setBackground(new Color(0, 153, 204));
        statusBar.setBounds(0, 950, 1920, 35);
        statusBar.setLayout(null);
        contentPane.add(statusBar);
        
        JLabel lblStatus = new JLabel("System Online | Database Connected | Hotel Management System v2.0");
        lblStatus.setForeground(Color.WHITE);
        lblStatus.setFont(new Font("Arial", Font.BOLD, 14));
        lblStatus.setBounds(20, 5, 800, 25);
        statusBar.add(lblStatus);
        
        JLabel lblTime = new JLabel(" " + java.time.LocalDateTime.now().toString().substring(0, 16));
        lblTime.setForeground(Color.WHITE);
        lblTime.setFont(new Font("Arial", Font.BOLD, 14));
        lblTime.setBounds(1400, 5, 200, 25);
        statusBar.add(lblTime);
    }
    
    private JButton createStyledButton(String text, String tooltip, Color bgColor, int x, int y, int width, int height) {
        JButton button = new JButton("<html><center>" + text + "</center></html>");
        button.setBounds(x, y, width, height);
        button.setFont(new Font("Arial", Font.BOLD, 18));
        button.setBackground(bgColor);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createRaisedBevelBorder());
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setToolTipText(tooltip);
        
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(bgColor.brighter());
                button.setBorder(BorderFactory.createLoweredBevelBorder());
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(bgColor);
                button.setBorder(BorderFactory.createRaisedBevelBorder());
            }
        });
        
        return button;
    }
    
    private void showOnlineOrders() {
        try {
            System.out.println("Opening Online Orders Viewer...");
            
            Class<?> viewerClass = Class.forName("hotel.OnlineDataViewer");
            JFrame viewer = (JFrame) viewerClass.getDeclaredConstructor().newInstance();
            viewer.setVisible(true);
            
        } catch (ClassNotFoundException e) {
            JOptionPane.showMessageDialog(this, 
                "📱 ONLINE ORDERS INTEGRATION\n\n" +
                "This feature connects to your web application to show:\n" +
                "• Real-time room bookings from website\n" +
                "• Online food orders\n" +
                "• Customer web registrations\n" +
                "• Payment transactions\n\n" +
                "To enable this feature:\n" +
                "1. Ensure your Spring Boot web app is running\n" +
                "2. Configure API endpoints\n" +
                "3. Test network connectivity", 
                "Online Orders", 
                JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, 
                "❌ Error opening online orders:\n" + ex.getMessage() + 
                "\n\nPlease ensure:\n" +
                "✓ Spring Boot application is running (localhost:8080)\n" +
                "✓ All API classes are properly created\n" +
                "✓ Network connection is available", 
                "Connection Error", 
                JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }
    
    private void showSettingsDialog() {
        JOptionPane.showMessageDialog(this, 
            "⚙️ SYSTEM CONFIGURATION\n\n" +
            "Configuration Options:\n" +
            "• User Account Management\n" +
            "•  Hotel Information & Branding\n" +
            "•  Payment Gateway Settings\n" +
            "•  Email & SMS Notifications\n" +
            "• ️ Database Configuration\n" +
            "•  Security & Access Control\n" +
            "•  Website Integration Settings\n" +
            "•  Mobile App Configuration\n" +
            "•  Backup & Recovery Options\n" +
            "•  UI Theme & Customization", 
            "Settings Panel", 
            JOptionPane.INFORMATION_MESSAGE);
    }
}