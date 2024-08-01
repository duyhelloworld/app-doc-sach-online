package huce.edu.vn.appdocsach.dto.core.comment;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Valid
@NoArgsConstructor
public class CreateCommentDto {
    @NotBlank(message = "COMMENT_CONTENT_MISSING")
    private String content;    

    @Min(1)
    private Integer chapterId;
}
