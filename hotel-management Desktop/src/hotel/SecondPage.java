package hotel;

import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

public class SecondPage extends JFrame {

    private JPanel contentPane;

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    SecondPage frame = new SecondPage();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public SecondPage() {
        setTitle("Hotel RAAR - Desktop Manager");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setResizable(true);
        
        
        contentPane = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
                
                GradientPaint gp = new GradientPaint(0, 0, new Color(255, 248, 220), 0, getHeight(), new Color(250, 235, 215));
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        
        
        contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));
        
        
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(new Color(255, 140, 0));
        headerPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 120));
        headerPanel.setLayout(null);
        contentPane.add(headerPanel);
        
        
        JLabel lblWelcome = new JLabel(" WELCOME TO HOTEL RAAR");
        lblWelcome.setFont(new Font("Serif", Font.BOLD, 48));
        lblWelcome.setForeground(Color.WHITE);
        lblWelcome.setBounds(0, 15, 1920, 60);
        lblWelcome.setHorizontalAlignment(SwingConstants.CENTER);
        headerPanel.add(lblWelcome);
        
        JLabel lblTagline = new JLabel("Your Comfort, Our Priority - Luxury Redefined");
        lblTagline.setFont(new Font("Arial", Font.ITALIC, 24));
        lblTagline.setForeground(new Color(255, 248, 220));
        lblTagline.setBounds(0, 75, 1920, 35);
        lblTagline.setHorizontalAlignment(SwingConstants.CENTER);
        headerPanel.add(lblTagline);
        
        
        contentPane.add(Box.createVerticalGlue());
        
        
        JLabel lblManagerDesktop = new JLabel("MANAGER DESKTOP");
        lblManagerDesktop.setFont(new Font("Serif", Font.BOLD, 64));
        lblManagerDesktop.setForeground(new Color(0, 102, 153));
        lblManagerDesktop.setAlignmentX(Component.CENTER_ALIGNMENT); 
        contentPane.add(lblManagerDesktop);
        
        
        contentPane.add(Box.createVerticalStrut(50));
        
        
        JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 50, 0));
        buttonsPanel.setOpaque(false); 
        buttonsPanel.setAlignmentX(Component.CENTER_ALIGNMENT); 
        
        
        Dimension buttonSize = new Dimension(230, 80);
        
        
        JButton btnRoomBooking = createCenteredButton("ROOM BOOKING", new Color(0, 153, 204));
        btnRoomBooking.setPreferredSize(buttonSize);
        btnRoomBooking.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                CustomerRestaurant cr = new CustomerRestaurant();
                cr.setVisible(true);
                setVisible(false);
            }
        });
        buttonsPanel.add(btnRoomBooking);
        
        
        JButton btnRestaurant = createCenteredButton("️RESTAURANT", new Color(34, 139, 34));
        btnRestaurant.setPreferredSize(buttonSize);
        btnRestaurant.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                CustomerRoom cr = new CustomerRoom();
                cr.setVisible(true);
                setVisible(false);
            }
        });
        buttonsPanel.add(btnRestaurant);
        
        // ADMIN PANEL BUTTON
        JButton btnAdminPanel = createCenteredButton("ADMIN PANEL", new Color(220, 20, 60));
        btnAdminPanel.setPreferredSize(buttonSize);
        btnAdminPanel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                AdminForm adminForm = new AdminForm();
                adminForm.setVisible(true);
                setVisible(false);
            }
        });
        buttonsPanel.add(btnAdminPanel);
        
        contentPane.add(buttonsPanel);
        
        
        contentPane.add(Box.createVerticalStrut(40));
        
        
        JButton btnLogout = new JButton("LOGOUT");
        btnLogout.setFont(new Font("Arial", Font.BOLD, 18));
        btnLogout.setBackground(new Color(139, 0, 0));
        btnLogout.setForeground(Color.WHITE);
        btnLogout.setFocusPainted(false);
        btnLogout.setBorder(BorderFactory.createRaisedBevelBorder());
        btnLogout.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnLogout.setAlignmentX(Component.CENTER_ALIGNMENT); 
        btnLogout.setMaximumSize(new Dimension(200, 50));
        btnLogout.setPreferredSize(new Dimension(200, 50));
        btnLogout.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int option = JOptionPane.showConfirmDialog(SecondPage.this, 
                    "Are you sure you want to logout and exit the application?", 
                    "Logout Confirmation", 
                    JOptionPane.YES_NO_OPTION, 
                    JOptionPane.QUESTION_MESSAGE);
                if (option == JOptionPane.YES_OPTION) {
                    System.exit(0);
                }
            }
        });
        btnLogout.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnLogout.setBackground(new Color(180, 0, 0));
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnLogout.setBackground(new Color(139, 0, 0));
            }
        });
        contentPane.add(btnLogout);
        
        
        contentPane.add(Box.createVerticalStrut(60));
        

        contentPane.add(Box.createVerticalGlue());
    }
    
    
    private JButton createCenteredButton(String text, Color bgColor) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 16));
        button.setBackground(bgColor);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createRaisedBevelBorder());
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        
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
}
