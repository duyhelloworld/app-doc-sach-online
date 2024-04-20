package huce.edu.vn.appdocsach.dto.core.comment;

import org.hibernate.validator.constraints.Range;

import huce.edu.vn.appdocsach.paging.BasePagingRequest;

public class FindCommentDto extends BasePagingRequest {
    @Range(min = 1)
    public Integer chapterId;
}
