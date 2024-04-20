package huce.edu.vn.appdocsach.exception;


import lombok.EqualsAndHashCode;
import lombok.Getter;

@EqualsAndHashCode(callSuper = false)
public class AppException extends RuntimeException {
    
    @Getter
    private AppError appError;

    public AppException(AppError appError) {
        this.appError = appError;
    }

    public AppException() {
        this(AppError.UnexpectedError);
    }
}
