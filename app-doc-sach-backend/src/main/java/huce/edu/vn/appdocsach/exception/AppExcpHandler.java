package huce.edu.vn.appdocsach.exception;

import java.io.FileNotFoundException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
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
        
        logger.error(ex);

        // Custom Exception đã định nghĩa
        if (ex instanceof AppException appEx) {
            return writeResponse(appEx.getResponseCode());
        }
        
        // Security
        if (ex instanceof AccessDeniedException) {
            return writeResponse(ResponseCode.FORBIDDEN);
        }

        // @Valid
        if (ex instanceof MethodArgumentNotValidException invalidException) {
            try {
                ErrorResponse response = new ErrorResponse();
                response.setMessages(invalidException.getBindingResult().getAllErrors()
                    .stream()
                    .map(e -> ResponseCode.valueOf(e.getDefaultMessage()).getMessage())
                    .toList());
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            } catch (IllegalArgumentException e) {
                return writeResponse(ResponseCode.INVALID_KEY);
            }
        }

        // Url invalid
        if (ex instanceof FileNotFoundException || ex instanceof NoResourceFoundException) {
            ErrorResponse response = new ErrorResponse();
            response.setMessages(ex.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(response);
        }

        return writeResponse(ResponseCode.UNEXPECTED_ERROR);
    }

    private ResponseEntity<ErrorResponse> writeResponse(ResponseCode responseCode) {
        ErrorResponse response = new ErrorResponse();
        response.setCode(responseCode.getCode());
        response.setMessages(responseCode.getMessage());
        return ResponseEntity.status(responseCode.getStatusCode()).body(response);
    }
}
