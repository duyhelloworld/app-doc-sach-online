package huce.edu.vn.appdocsach.dto.core.comment;

import huce.edu.vn.appdocsach.dto.paging.PaginationRequestDto;
import jakarta.validation.constraints.Min;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class FindCommentDto extends PaginationRequestDto {
    @Min(1)
    private Integer chapterId;
}
