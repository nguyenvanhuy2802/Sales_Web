package org.example.utils;

import javax.mail.*;
import javax.mail.internet.*;
import java.util.Properties;

public class EmailUtil {

    // Configure your SMTP server details here
    private static final String SMTP_HOST = "smtp.example.com";
    private static final String SMTP_PORT = "587"; // or "465" for SSL
    private static final String SMTP_USERNAME = "vanhuy7703@gmail.com";
    private static final String SMTP_PASSWORD = "your_password";

    public static void sendEmail(String toAddress, String subject, String messageContent) throws MessagingException {
        Properties properties = new Properties();

        // SMTP server configuration
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true"); // Enable STARTTLS
        properties.put("mail.smtp.host", SMTP_HOST);
        properties.put("mail.smtp.port", SMTP_PORT);

        // Create a session with authentication
        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(SMTP_USERNAME, SMTP_PASSWORD);
            }
        });

        // Create a MIME style email message
        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress(SMTP_USERNAME));
        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toAddress));
        message.setSubject(subject);
        message.setContent(messageContent, "text/html; charset=utf-8");

        // Send the email
        Transport.send(message);
    }
}
