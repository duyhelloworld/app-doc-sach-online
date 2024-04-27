package huce.edu.vn.appdocsach.dto.core.comment;

import org.hibernate.validator.constraints.Range;

import huce.edu.vn.appdocsach.paging.PagingRequest;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FindCommentDto extends PagingRequest {
    @Range(min = 1)
    private Integer chapterId;
}
