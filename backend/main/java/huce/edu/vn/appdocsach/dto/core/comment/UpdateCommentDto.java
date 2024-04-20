package huce.edu.vn.appdocsach.dto.core.comment;

import org.hibernate.validator.constraints.Range;

import huce.edu.vn.appdocsach.exception.ValidationError;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateCommentDto {
    @Range(min = 1)
    private Integer id;

    @NotBlank(message = ValidationError.COMMENT_CONTENT_MISSING)
    private String newContent;
}
