package hotel;

import java.sql.Connection;
import java.sql.DriverManager;
import javax.swing.JOptionPane;

public class GetConnection {

    public Connection getConnection() {   
        Connection connect = null;
        
        // This connection string is correct based on your phpMyAdmin homepage.
        String url = "jdbc:mysql://localhost:3306/java_hotel_management";
        String user = "root";
        String pwd = "";
    
        try {
             connect = DriverManager.getConnection(url, user, pwd);
        } catch(Exception e) {
            // Added an error message here in case the database connection fails.
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, 
                "Could not connect to the database. Please check that your MySQL server is running.", 
                "Connection Error", 
                JOptionPane.ERROR_MESSAGE);
        }
        return connect;
    }
}