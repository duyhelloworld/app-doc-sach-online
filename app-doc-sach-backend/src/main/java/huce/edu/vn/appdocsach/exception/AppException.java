package huce.edu.vn.appdocsach.exception;


import huce.edu.vn.appdocsach.enums.ResponseCode;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@EqualsAndHashCode(callSuper = false)
public class AppException extends RuntimeException {
    
    @Getter
    private ResponseCode responseCode;

    public AppException(ResponseCode responseCode) {
        super(responseCode.getMessage());
        this.responseCode = responseCode;
    }
}
