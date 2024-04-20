package huce.edu.vn.appdocsach.exception;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
public class ErrorResponse {
    private List<String> messages;

    @JsonFormat(pattern = "yyyy/MM/dd HH:mm:ss")
    private LocalDateTime createAt;

    public ErrorResponse(String message) {
        this.createAt = LocalDateTime.now();
        this.messages = List.of(message);
    }

    public ErrorResponse(List<String> messages) {
        this.createAt = LocalDateTime.now();
        this.messages = messages;
    }
}
