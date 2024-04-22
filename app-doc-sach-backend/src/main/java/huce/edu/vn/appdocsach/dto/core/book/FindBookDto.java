package huce.edu.vn.appdocsach.dto.core.book;

import huce.edu.vn.appdocsach.paging.PagingRequest;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FindBookDto extends PagingRequest {
    private Integer categoryId;

    private String keyword;
}
