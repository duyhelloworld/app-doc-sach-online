package huce.edu.vn.appdocsach.dto.core.comment;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;

import huce.edu.vn.appdocsach.exception.ValidationError;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CreateCommentDto {
    @Length(min = 0)
    @NotBlank(message = ValidationError.COMMENT_CONTENT_MISSING)
    private String content;    

    @Range(min = 1)
    private Integer chapterId;
}
