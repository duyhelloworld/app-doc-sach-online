package huce.edu.vn.appdocsach.dto.core.comment;

import java.time.LocalDateTime;

import huce.edu.vn.appdocsach.annotations.time.ValidDatetimeFormat;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CommentDto {
    
    private Integer id;

    private String content;

    @ValidDatetimeFormat
    private LocalDateTime commentAt;

    @ValidDatetimeFormat
    private LocalDateTime editedAt;

    private String username;
}
