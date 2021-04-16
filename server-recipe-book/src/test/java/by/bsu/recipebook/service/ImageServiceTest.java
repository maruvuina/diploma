package by.bsu.recipebook.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.testng.PowerMockTestCase;
import org.springframework.web.multipart.MultipartFile;
import org.testng.annotations.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


@PrepareForTest({ImageService.class, LogManager.class})
public class ImageServiceTest extends PowerMockTestCase {
    @Test
    public void shouldSave() {
        PowerMockito.mockStatic(LogManager.class);
        when(LogManager.getLogger(any(ImageService.class))).thenReturn(any(Logger.class));
        String imagePath = "\\location\\dir-name\\image.png";
        String dirName = "dir-name";
        PowerMockito.mockStatic(ImageService.class);
        MultipartFile multipartFile = mock(MultipartFile.class);
        when(ImageService.save(multipartFile, dirName))
                .thenReturn(imagePath);
        String actual = ImageService.save(multipartFile, dirName);
        assertThat(actual)
                .as("Static method is mocked")
                .isEqualTo(imagePath);
    }
}
