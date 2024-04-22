package huce.edu.vn.appdocsach.exception;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;

@Data
@JsonInclude(content = Include.NON_NULL)
public class ErrorResponse {
    
    private int code;

    private List<String> messages;

    @JsonFormat(pattern = "yyyy/MM/dd HH:mm:ss")
    private LocalDateTime createAt = LocalDateTime.now();

    public void setMessages(String... messages) {
        this.messages = Arrays.asList(messages);
    }

    public void setMessages(List<String> messages) {
        this.messages = messages;
    }
}
