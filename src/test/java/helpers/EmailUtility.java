
package helpers;

import java.util.Properties;
import javax.mail.Authenticator;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

// import java.net.PasswordAuthentication;

public class EmailUtility {

    protected static WebDriver ldriver;
    protected static WebDriverWait wait;

    public static void sendEmail(String subject, String messagetext) {

        String from= System.getenv("EMAIL_ADDRESS");
        String to= from;
        String host= "smtp.gmail.com";

        // Properties to configure the Gmail SMTP server
        Properties properties = System.getProperties();
        properties.setProperty("mail.smtp.host", host);
        properties.setProperty("mail.smtp.port", "587"); // Use 587 for TLS
        properties.setProperty("mail.smtp.auth", "true");
        properties.setProperty("mail.smtp.starttls.enable", "true");

        // Create a session with authentication
        Session session = Session.getInstance(properties,  new Authenticator(){
            @Override
            protected javax.mail.PasswordAuthentication getPasswordAuthentication() {

                String email = System.getenv("EMAIL_ADDRESS");
                String password= System.getenv("EMAIL_APP_PASSWORD");
                return new javax.mail.PasswordAuthentication(email, password);
            }
        });

        try {
            // Create a MimeMessage
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(from));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
            message.setSubject(subject);
            message.setText(messagetext);

            // Send the email
            Transport.send(message);
            System.out.println("Email sent successfully.");
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

}
