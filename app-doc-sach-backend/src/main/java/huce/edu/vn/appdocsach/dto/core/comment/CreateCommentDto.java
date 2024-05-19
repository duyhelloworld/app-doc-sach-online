package huce.edu.vn.appdocsach.dto.core.comment;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
@Valid
public class CreateCommentDto {
    @Length(min = 0)
    @NotBlank(message = "COMMENT_CONTENT_MISSING")
    private String content;    

    @Range(min = 1)
    private Integer chapterId;
}
