package by.bsu.recipebook.service;

import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.thymeleaf.ITemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.context.IContext;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;


public class MailContentBuilderTest {
    private AutoCloseable closeable;

    @Mock
    private ITemplateEngine templateEngine;

    private MailContentBuilder mailContentBuilder;

    @BeforeMethod
    public void setUp() {
        closeable = MockitoAnnotations.openMocks(this);
        mailContentBuilder = new MailContentBuilder(templateEngine);
    }

    @AfterMethod
    public void tearDown() throws Exception {
        closeable.close();
    }

    @Test
    public void shouldBuild() {
        String message = "<span>Tested string</span>";
        when(templateEngine.process(eq("mailTemplate"), any(Context.class)))
                .thenReturn(message);
        String expected = "<span>Tested string</span>";
        String actual = mailContentBuilder.build("Tested string");
        assertThat(actual).isEqualTo(expected);
    }
}
