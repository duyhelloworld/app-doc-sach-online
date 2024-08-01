package huce.edu.vn.appdocsach.utils;

import com.fasterxml.jackson.core.JsonProcessingException;

import huce.edu.vn.appdocsach.configs.AppObjectMapper;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class JsonUtils {
    
    private static AppObjectMapper mapper = new AppObjectMapper();

    public static String json(Object... data) {
        try {
            StringBuilder builder = new StringBuilder();
            for (Object object : data) {
                builder
                    .append("\n\tClass : ")
                    .append(object.getClass().getName())
                    .append("\n\tData : ")
                    .append(mapper.writeValueAsString(object));
            }
            return builder.toString();
        } catch (JsonProcessingException e) {
            log.error("Cannot json this object! Error: \n" + e.getMessage());
            return null;
        }
    }
}
