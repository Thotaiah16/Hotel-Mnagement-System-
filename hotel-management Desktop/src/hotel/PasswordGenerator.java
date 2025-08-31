package hotel;

import org.mindrot.jbcrypt.BCrypt;

public class PasswordGenerator {

    /**
     * Run this program to generate a new password hash for the database.
     */
    public static void main(String[] args) {
        
        String plainPassword = "admin";

       
        String hashedPassword = BCrypt.hashpw(plainPassword, BCrypt.gensalt(10));

        
        System.out.println("Plain Password: " + plainPassword);
        System.out.println("Hashed Password for Database: " + hashedPassword);
    }
}
