package huce.edu.vn.appdocsach.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.swagger.v3.core.util.Json;

public class AppLogger<T> {
    private final Logger log;
    public AppLogger(Class<T> clazz) {
        this.log = LoggerFactory.getLogger(clazz);
    }
    
    public void onStart(Thread currentThread) {
        log.info("Start {}{}{}", ColorCode.BLUE, currentThread.getStackTrace()[2].getMethodName(), ColorCode.RESET);
    }

    public void onStart(Thread currentThread, Object... input) {
        log.info("Start {}{}{} with input = {}", 
            ColorCode.BLUE, currentThread.getStackTrace()[2].getMethodName(), ColorCode.RESET, Json.pretty(input));
    }

    public void info(String message, Object... input) {
        log.info(message, Json.pretty(input));
    }

    public void error(String reason, Object... input) {
        log.error(reason, Json.pretty(input));
    }

    public void info(Object... input) {
        for (Object object : input) {
            log.info("Printing info of {}{}{} : data = {}", ColorCode.BLUE, object.getClass().getName(), ColorCode.RESET, Json.pretty(input));
        }
    }

    public void error(Exception ex) {
        log.error("{}{}{} throw a {}{}{} with message '{}'", ColorCode.BLUE,
                Thread.currentThread().getStackTrace()[2].getMethodName(),
                ColorCode.RESET, ColorCode.RED, ex.getClass().getSimpleName(), ColorCode.RESET, ex.getMessage());
    }

    public void error(String error) {
        log.error("{}{}{} return a error : {}", ColorCode.RED, Thread.currentThread().getStackTrace()[2].getMethodName(), ColorCode.RESET, error);
    }
}
