package huce.edu.vn.appdocsach.dto.core.book;

import huce.edu.vn.appdocsach.dto.paging.PaginationRequestDto;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class FindBookDto extends PaginationRequestDto {

    private String keyword;

    private Integer categoryId;
}
