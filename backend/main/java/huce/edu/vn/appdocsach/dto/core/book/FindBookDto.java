package huce.edu.vn.appdocsach.dto.core.book;

import org.hibernate.validator.constraints.Range;

import huce.edu.vn.appdocsach.paging.BasePagingRequest;

public class FindBookDto extends BasePagingRequest {
    @Range(min = 0)
    public int categoryId;
}
