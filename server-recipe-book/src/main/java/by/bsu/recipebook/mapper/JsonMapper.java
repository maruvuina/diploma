package by.bsu.recipebook.mapper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

@Component
public class JsonMapper {
    private static final Logger logger = LogManager.getLogger(JsonMapper.class);

    public <T> T mapDto(String jsonString, Class<T> className) {
        ObjectMapper mapper = new ObjectMapper();
        T t = null;
        try {
            t = mapper.readValue(jsonString, className);
        } catch (JsonProcessingException e) {
            logger.log(Level.ERROR, "Error while parse Json to Dto: ", e);
        }
        return t;
    }
}
