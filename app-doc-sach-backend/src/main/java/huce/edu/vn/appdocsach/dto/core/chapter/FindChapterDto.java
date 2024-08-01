package huce.edu.vn.appdocsach.dto.core.chapter;

import huce.edu.vn.appdocsach.dto.paging.PaginationRequestDto;
import jakarta.validation.constraints.Min;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class FindChapterDto extends PaginationRequestDto {

    @Min(1)
    private Integer bookId;

}
