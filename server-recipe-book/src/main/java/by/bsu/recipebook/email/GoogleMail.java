package by.bsu.recipebook.email;

import by.bsu.recipebook.manager.GoogleMailManager;
import com.sun.mail.smtp.SMTPTransport;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Date;
import java.util.Properties;

public class GoogleMail {
    private static final Logger logger = LogManager.getLogger(GoogleMail.class);
    private final String fromEmail;
    private final String title;
    private final String message;

    public GoogleMail(String fromEmail, String title, String message) {
        this.fromEmail = fromEmail;
        this.title = title;
        this.message = message;
    }

    public void sendMessage() {
        Properties sesiionConfig = GoogleMailProperties.getGoogleMailProperties();
        Session session = Session.getInstance(sesiionConfig);
        MimeMessage mimeMessage = new MimeMessage(session);
        String companyEmail = GoogleMailManager.getProperty(GoogleMailManager.MAIL_COMPANY_EMAIL);
        try {
            mimeMessage.setFrom(new InternetAddress(fromEmail));
            mimeMessage.setRecipients(Message.RecipientType.TO, InternetAddress.parse(companyEmail, false));
            mimeMessage.setSubject(title);
            mimeMessage.setText(message, "UTF-8");
            mimeMessage.setSentDate(new Date());
            try (SMTPTransport transport = (SMTPTransport) session.getTransport("smtps")) {
                transport.connect("smtp.gmail.com", companyEmail,
                        GoogleMailManager.getProperty(GoogleMailManager.MAIL_PASSWORD));
                transport.sendMessage(mimeMessage, mimeMessage.getAllRecipients());
            }
        } catch (MessagingException e) {
            logger.log(Level.ERROR, "Error while trying to send message to company: ", e);
        }
    }
}
