package huce.edu.vn.appdocsach.dto.core.chapter;

import huce.edu.vn.appdocsach.paging.PagingRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class FindChapterDto extends PagingRequest {
    private Integer bookId;
}
