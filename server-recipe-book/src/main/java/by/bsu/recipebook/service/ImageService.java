package by.bsu.recipebook.service;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.xml.bind.DatatypeConverter;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static by.bsu.recipebook.constants.Constants.*;

@Service
public class ImageService {
    private static final Logger logger = LogManager.getLogger(ImageService.class);

    public static String save(MultipartFile file, String dirName) {
        String date = LocalDateTime.now()
                .format(DateTimeFormatter.ofPattern(DATE_TIME_PATTERN));
        String fileName = date + file.getOriginalFilename();
        String filePath = PATH_TO_UPLOADED_FILES_APP + File.separator +
                dirName + File.separator + fileName;
        try {
            Files.copy(file.getInputStream(), Paths.get(filePath), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            logger.log(Level.ERROR, "Error while saving picture: ", e);
        }
        return filePath;
    }

    public static String get(String location) {
        String picture = null;
        try {
            picture = DatatypeConverter.printBase64Binary(Files.readAllBytes(Paths.get(location)));
        } catch (IOException e) {
            logger.log(Level.ERROR, "Error while get picture: ", e);
        }
        return picture;
    }
}
