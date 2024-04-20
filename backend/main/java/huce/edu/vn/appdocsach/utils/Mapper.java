package huce.edu.vn.appdocsach.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import huce.edu.vn.appdocsach.exception.AppError;
import huce.edu.vn.appdocsach.exception.AppException;

@Component
public class Mapper {

    @Autowired
    private ObjectMapper objectMapper;

    public <T> T getInstance(String json, Class<T> input) {
        try {
            return objectMapper.readValue(json, input);
        } catch (JsonProcessingException e) {
            throw new AppException(AppError.UnexpectedError);
        }
    }
}
