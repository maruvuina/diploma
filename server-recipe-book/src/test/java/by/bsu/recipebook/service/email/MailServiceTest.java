package by.bsu.recipebook.service.email;

import by.bsu.recipebook.entity.NotificationEmail;
import by.bsu.recipebook.exception.ServiceException;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.testng.Assert.*;

public class MailServiceTest {
    private AutoCloseable closeable;

    @Mock
    private JavaMailSender mailSender;

    @Mock
    private MailContentBuilder mailContentBuilder;

    @Mock
    private MailService mailService;

    @BeforeMethod
    public void setUp() {
        closeable = MockitoAnnotations.openMocks(this);
    }

    @AfterMethod
    public void tearDown() throws Exception {
        closeable.close();
    }

    @Test()
    public void shouldSendMail() throws ServiceException {
        String message = "Message content";
        when(mailContentBuilder.build(message)).thenReturn(message);
        doNothing()
                .when(mailSender)
                .send(any(MimeMessagePreparator.class));
        doNothing()
                .doThrow(new ServiceException("Exception occurred when sending mail"))
                .when(mailService)
                .sendMail(any(NotificationEmail.class));
    }
}
