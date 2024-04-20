package huce.edu.vn.appdocsach.exception;

import java.io.FileNotFoundException;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import com.fasterxml.jackson.databind.JsonMappingException;

import huce.edu.vn.appdocsach.utils.AppLogger;

@RestControllerAdvice
public class AppExcpHandler {   

    private AppLogger<AppExcpHandler> logger = new AppLogger<>(AppExcpHandler.class);

    @ExceptionHandler({ Exception.class })
    public ResponseEntity<ErrorResponse> handleAppException(Exception ex) {
        // Exception đã định nghĩa
        logger.error(ex);
        if (ex instanceof AppException appEx) {
            return ResponseEntity
                .status(appEx.getAppError().getErrorCode())
                .body(new ErrorResponse(appEx.getAppError().getValue()));
        }

        // @Valid
        if (ex instanceof MethodArgumentNotValidException invalidEx) {
            return ResponseEntity
                    .status(invalidEx.getStatusCode())
                    .body(new ErrorResponse(invalidEx.getBindingResult().getAllErrors().stream().map(e -> e.getDefaultMessage()).collect(Collectors.toList())));
        }
        // Security 
        if (ex instanceof AccessDeniedException adEx) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(new ErrorResponse(adEx.getMessage()));
        } 
        // Url invalid
        if (ex instanceof FileNotFoundException || ex instanceof NoResourceFoundException) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ErrorResponse(ex.getLocalizedMessage()));
        }

        if (ex instanceof JsonMappingException jsonError) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorResponse(jsonError.getPathReference()));
        }
        return ResponseEntity
                .status(AppError.UnexpectedError.getErrorCode())
                .body(new ErrorResponse(AppError.UnexpectedError.getValue()));
    }
}
