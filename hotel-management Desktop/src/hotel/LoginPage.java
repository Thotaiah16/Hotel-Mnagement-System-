package hotel;

import org.mindrot.jbcrypt.BCrypt;
import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GradientPaint;
import java.awt.RenderingHints;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.awt.event.ActionEvent;
import java.awt.Color;
import java.awt.Cursor;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.BorderFactory;
import javax.swing.SwingConstants;

public class LoginPage {

    private JFrame frame;
    private JTextField tfusername;
    private JPasswordField tfpwd;
    JLabel Ustar = new JLabel("*");
    JLabel Pstar = new JLabel("*");

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            Hotel hotel = Hotel.getInstance();
            public void run() {
                try {
                    LoginPage window = new LoginPage();
                    window.frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public LoginPage() {
        initialize();
        Ustar.setVisible(false);
        Pstar.setVisible(false);
    }

    private void initialize() {
        frame = new JFrame();
        frame.setTitle("Hotel Raar - Management Portal");
        frame.setBounds(50, 50, 600, 700);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        
        
        JPanel mainPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
                
                
                GradientPaint gp = new GradientPaint(0, 0, new Color(0, 153, 204), 0, getHeight(), new Color(135, 206, 250));
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        mainPanel.setLayout(null);
        frame.setContentPane(mainPanel);
        
        
        JLabel lblLogo = new JLabel();
        lblLogo.setBounds(225, 30, 150, 100);
        lblLogo.setHorizontalAlignment(SwingConstants.CENTER);
        try {
            ImageIcon hotelIcon = new ImageIcon("images\\hotel-logo.png");
            lblLogo.setIcon(hotelIcon);
        } catch (Exception e) {
            lblLogo.setText("");
            lblLogo.setFont(new Font("Arial", Font.BOLD, 64));
            lblLogo.setForeground(Color.WHITE);
        }
        mainPanel.add(lblLogo);
        
        
        JLabel lblHotelTitle = new JLabel("HOTEL RAAR");
        lblHotelTitle.setFont(new Font("Serif", Font.BOLD, 32));
        lblHotelTitle.setForeground(Color.WHITE);
        lblHotelTitle.setBounds(150, 140, 300, 40);
        lblHotelTitle.setHorizontalAlignment(SwingConstants.CENTER);
        mainPanel.add(lblHotelTitle);
        
        JLabel lblSubtitle = new JLabel("MANAGEMENT SYSTEM");
        lblSubtitle.setFont(new Font("Arial", Font.BOLD, 18));
        lblSubtitle.setForeground(new Color(240, 248, 255));
        lblSubtitle.setBounds(150, 180, 300, 30);
        lblSubtitle.setHorizontalAlignment(SwingConstants.CENTER);
        mainPanel.add(lblSubtitle);
        
        
        JPanel loginPanel = new JPanel();
        loginPanel.setBackground(Color.WHITE);
        loginPanel.setBounds(100, 240, 400, 350);
        loginPanel.setLayout(null);
        loginPanel.setBorder(BorderFactory.createRaisedBevelBorder());
        mainPanel.add(loginPanel);
        
        
        JLabel lblLogin = new JLabel("SECURE LOGIN");
        lblLogin.setFont(new Font("Arial", Font.BOLD, 24));
        lblLogin.setForeground(new Color(0, 153, 204));
        lblLogin.setBounds(130, 30, 200, 35);
        loginPanel.add(lblLogin);
        
        
        JLabel lblUsername = new JLabel(" USERNAME");
        lblUsername.setFont(new Font("Arial", Font.BOLD, 16));
        lblUsername.setForeground(new Color(70, 70, 70));
        lblUsername.setBounds(50, 90, 150, 25);
        loginPanel.add(lblUsername);
        
        tfusername = new JTextField();
        tfusername.setFont(new Font("Arial", Font.PLAIN, 16));
        tfusername.setBounds(50, 115, 300, 40);
        tfusername.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(0, 153, 204), 2),
            BorderFactory.createEmptyBorder(5, 15, 5, 15)
        ));
        loginPanel.add(tfusername);
        
        
        JLabel lblPassword = new JLabel(" PASSWORD");
        lblPassword.setFont(new Font("Arial", Font.BOLD, 16));
        lblPassword.setForeground(new Color(70, 70, 70));
        lblPassword.setBounds(50, 170, 150, 25);
        loginPanel.add(lblPassword);
        
        tfpwd = new JPasswordField();
        tfpwd.setFont(new Font("Arial", Font.PLAIN, 16));
        tfpwd.setBounds(50, 195, 300, 40);
        tfpwd.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(0, 153, 204), 2),
            BorderFactory.createEmptyBorder(5, 15, 5, 15)
        ));
        loginPanel.add(tfpwd);
        
        
        JButton btnLogin = new JButton(" LOGIN");
        btnLogin.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Ustar.setVisible(false);
                Pstar.setVisible(false);
                
                if (tfusername.getText().equals("")) {
                    Ustar.setVisible(true);
                }
                if (String.valueOf(tfpwd.getPassword()).equals("")) {
                    Pstar.setVisible(true);
                } else {
                    GetConnection connect = new GetConnection();
                    Connection conn = connect.getConnection();
                    try {
                        String sql = "SELECT password_hash FROM users WHERE username = ? AND is_active = TRUE";
                        PreparedStatement ps = conn.prepareStatement(sql);
                        ps.setString(1, tfusername.getText());
                        ResultSet rs = ps.executeQuery();

                        if (rs.next()) {
                            String storedHash = rs.getString("password_hash");
                            String plainPassword = new String(tfpwd.getPassword());

                            if (BCrypt.checkpw(plainPassword, storedHash)) {
                                SecondPage sp = new SecondPage();
                                sp.setVisible(true);
                                sp.pack();
                                sp.setLocationRelativeTo(null);
                                sp.setBounds(50, 50, 1015, 574);
                                frame.setVisible(false);
                            } else {
                                JOptionPane.showMessageDialog(null, " Invalid username or password.", "Login Error", JOptionPane.ERROR_MESSAGE);
                            }
                        } else {
                            JOptionPane.showMessageDialog(null, "Invalid username or password.", "Login Error", JOptionPane.ERROR_MESSAGE);
                        }
                    } catch (SQLException p) {
                        p.printStackTrace();
                        JOptionPane.showMessageDialog(null, "Database error occurred.", "Error", JOptionPane.ERROR_MESSAGE);
                    } finally {
                        try {
                            if (conn != null) conn.close();
                        } catch (SQLException ex) {
                            ex.printStackTrace();
                        }
                    }
                }
            }
        });
        btnLogin.setFont(new Font("Arial", Font.BOLD, 18));
        btnLogin.setBounds(50, 260, 300, 50);
        btnLogin.setBackground(new Color(0, 153, 204));
        btnLogin.setForeground(Color.WHITE);
        btnLogin.setFocusPainted(false);
        btnLogin.setBorder(BorderFactory.createRaisedBevelBorder());
        btnLogin.setCursor(new Cursor(Cursor.HAND_CURSOR));
        loginPanel.add(btnLogin);
        
        
        JButton btnCancel = new JButton("CANCEL");
        btnCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        btnCancel.setFont(new Font("Arial", Font.BOLD, 16));
        btnCancel.setBounds(250, 620, 150, 40);
        btnCancel.setBackground(new Color(220, 20, 60));
        btnCancel.setForeground(Color.WHITE);
        btnCancel.setFocusPainted(false);
        btnCancel.setBorder(BorderFactory.createRaisedBevelBorder());
        btnCancel.setCursor(new Cursor(Cursor.HAND_CURSOR));
        mainPanel.add(btnCancel);
        
        
        Ustar.setForeground(Color.RED);
        Ustar.setFont(new Font("Arial", Font.BOLD, 20));
        Ustar.setBounds(355, 125, 30, 30);
        loginPanel.add(Ustar);
        
        Pstar.setForeground(Color.RED);
        Pstar.setFont(new Font("Arial", Font.BOLD, 20));
        Pstar.setBounds(355, 205, 30, 30);
        loginPanel.add(Pstar);
        
        // Footer info
        JLabel lblFooter = new JLabel("Secure Access Portal | Hotel Crescent Management System v2.0");
        lblFooter.setForeground(Color.WHITE);
        lblFooter.setFont(new Font("Arial", Font.ITALIC, 12));
        lblFooter.setBounds(50, 650, 500, 20);
        mainPanel.add(lblFooter);
    }
}
