package huce.edu.vn.appdocsach.exception;

import java.io.FileNotFoundException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import huce.edu.vn.appdocsach.enums.ResponseCode;
import huce.edu.vn.appdocsach.utils.AppLogger;

@RestControllerAdvice
public class AppExcpHandler {   

    private AppLogger<AppExcpHandler> logger = new AppLogger<>(AppExcpHandler.class);

    @ExceptionHandler({ Exception.class })
    ResponseEntity<ErrorResponse> handleAppException(Exception ex) {
        // CustomException đã định nghĩa
        ErrorResponse response = new ErrorResponse();
        logger.error(ex);

        if (ex instanceof AppException appEx) {
            ResponseCode responseCode = appEx.getResponseCode();
            response.setCode(responseCode.getCode());
            response.setMessages(responseCode.getMessage());
            return ResponseEntity.status(responseCode.getStatusCode()).body(response);
        }

        // @Valid
        if (ex instanceof MethodArgumentNotValidException invalidException) {
            try {
                response.setMessages(invalidException.getBindingResult().getAllErrors()
                    .stream()
                    .map(e -> ResponseCode.valueOf(e.getDefaultMessage()).getMessage())
                    .toList());
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            } catch (IllegalArgumentException e) {
                ResponseCode invalidKey = ResponseCode.InvalidKey;
                response.setCode(invalidKey.getCode());
                response.setMessages(invalidKey.getMessage());
                return ResponseEntity.status(invalidKey.getStatusCode()).body(response);
            }
        }

        // Url invalid
        if (ex instanceof FileNotFoundException || ex instanceof NoResourceFoundException) {
            response.setMessages(ex.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(response);
        }
        
        ResponseCode unexpected = ResponseCode.UnexpectedError;
        response.setCode(unexpected.getCode());
        response.setMessages(unexpected.getMessage());
        return ResponseEntity.status(unexpected.getStatusCode()).body(response);
    }
}
