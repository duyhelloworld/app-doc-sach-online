package huce.edu.vn.appdocsach.configs;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import huce.edu.vn.appdocsach.enums.ResponseCode;
import huce.edu.vn.appdocsach.exception.AppException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class AppObjectMapper extends ObjectMapper {

    public AppObjectMapper() {
        super.registerModules(new JavaTimeModule());
        configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        configure(DeserializationFeature.USE_LONG_FOR_INTS, true); 
        configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
    }

    public <T> T toPojo(String json, Class<T> input) {
        try {
            return readValue(json, input);
        } catch (JsonProcessingException e) {
            log.error("Error process this json to " + input.getSimpleName() + " :\n" + e.getMessage());
            throw new AppException(ResponseCode.JSON_INVALID);
        }
    }
}
