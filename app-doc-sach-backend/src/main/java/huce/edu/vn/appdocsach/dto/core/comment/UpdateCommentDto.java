package huce.edu.vn.appdocsach.dto.core.comment;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
@Valid
public class UpdateCommentDto {
    @Min(1)
    private Integer id;

    @NotBlank(message = "COMMENT_CONTENT_MISSING")
    private String content;
}
