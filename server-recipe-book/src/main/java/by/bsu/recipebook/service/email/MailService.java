package by.bsu.recipebook.service.email;

import by.bsu.recipebook.entity.NotificationEmail;
import by.bsu.recipebook.exception.ServiceException;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.mail.MailException;
import org.springframework.stereotype.Service;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.scheduling.annotation.Async;

import static by.bsu.recipebook.constants.Constants.COMPANY_EMAIL;

@Service
@RequiredArgsConstructor
public class MailService {
    private static final Logger logger = LogManager.getLogger(MailService.class);

    private final JavaMailSender mailSender;

    private final MailContentBuilder mailContentBuilder;

    @Async
    public void sendMail(NotificationEmail notificationEmail) throws ServiceException {
        MimeMessagePreparator messagePreparator = mimeMessage -> {
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
            messageHelper.setFrom(COMPANY_EMAIL);
            messageHelper.setTo(notificationEmail.getRecipient());
            messageHelper.setSubject(notificationEmail.getSubject());
            messageHelper.setText(mailContentBuilder.build(notificationEmail.getBody()));
        };
        try {
            mailSender.send(messagePreparator);
            logger.log(Level.INFO, "Activation email sent!");
        } catch (MailException e) {
            logger.log(Level.ERROR, "Exception occurred when sending mail: ", e);
            throw new ServiceException("Exception occurred when sending mail to " + notificationEmail.getRecipient());
        }
    }
}
