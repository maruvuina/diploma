package by.bsu.recipebook.service.email;

import by.bsu.recipebook.dto.EmailDto;
import by.bsu.recipebook.email.GoogleMail;
import by.bsu.recipebook.mapper.EmailMapper;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.mockito.Mockito.*;

public class GmailServiceTest {
    private AutoCloseable closeable;

    @Mock
    private EmailMapper emailMapper;

    @Mock
    private GmailService gmailService;

    @BeforeMethod
    public void setUp() {
        closeable = MockitoAnnotations.openMocks(this);
    }

    @AfterMethod
    public void tearDown() throws Exception {
        closeable.close();
    }

    @Test
    public void shouldSendMessageToCompany() {
        GoogleMail googleMail = mock(GoogleMail.class);
        EmailDto emailDto = new EmailDto("test@email.com", "Test", "Test message");
        when(emailMapper
                .mapToGoogleMail(emailDto))
                .thenReturn(new GoogleMail("test@email.com", "Test", "Test message"));
        doNothing()
                .when(googleMail)
                .sendMessage();
        doNothing()
                .when(gmailService)
                .sendMessageToCompany(emailDto);
    }
}
