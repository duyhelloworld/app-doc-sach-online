package huce.edu.vn.appdocsach.dto.core.comment;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CommentDto {
    
    private Integer id;

    private String content;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime commentAt;

    private boolean isEdited;

    private String username;

    private String userAvatar;
}
