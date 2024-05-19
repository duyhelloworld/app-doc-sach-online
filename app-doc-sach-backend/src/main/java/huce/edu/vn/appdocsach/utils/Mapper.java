package huce.edu.vn.appdocsach.utils;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import huce.edu.vn.appdocsach.enums.ResponseCode;
import huce.edu.vn.appdocsach.exception.AppException;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class Mapper {

    ObjectMapper objectMapper;

    AppLogger<Mapper> logger;

    public Mapper(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
        this.logger =  new AppLogger<>(Mapper.class);
    }

    public <T> T getInstance(String json, Class<T> input) {
        try {
            return objectMapper.readValue(json, input);
        } catch (JsonProcessingException e) {
            logger.error(e.getLocation().toString());
            throw new AppException(ResponseCode.UNEXPECTED_ERROR);
        }
    }
}
