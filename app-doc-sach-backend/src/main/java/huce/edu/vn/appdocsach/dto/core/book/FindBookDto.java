package huce.edu.vn.appdocsach.dto.core.book;

import org.hibernate.validator.constraints.Range;

import huce.edu.vn.appdocsach.paging.PagingRequest;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FindBookDto extends PagingRequest {
    @Range(min = 0)
    private int categoryId;
}
