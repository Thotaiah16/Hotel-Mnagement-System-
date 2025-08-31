package com.hotel.HotelWebApp;

import java.util.Properties;
import jakarta.mail.*; 
import jakarta.mail.internet.*; 

public class EmailService {

    
    private static final String FROM_EMAIL = "your Gmail Adress"; 
    private static final String PASSWORD = "Your App Pasword"; 

    public static boolean sendOtpEmail(String recipientEmail, String otp) {
       
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");

        
        Session session = Session.getInstance(props, new jakarta.mail.Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(FROM_EMAIL, PASSWORD);
            }
        });

        try {
            
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(FROM_EMAIL));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(recipientEmail));
            message.setSubject("Your One-Time Password (OTP) for Hotel Raar");
            
            String emailBody = "Hello,\n\n"
                    + "Thank you for registering with Hotel Crescent.\n\n"
                    + "Your One-Time Password is: " + otp + "\n\n"
                    + "This code is valid for 10 minutes.\n\n"
                    + "Thank you,\n"
                    + "The Hotel Raar Team";
            
            message.setText(emailBody);
            Transport.send(message);
            System.out.println("OTP email sent successfully to " + recipientEmail);
            return true;

        } catch (MessagingException e) {
            System.err.println("--- EMAIL SEND FAILED ---");
            e.printStackTrace();
            return false;
        }
    }
}