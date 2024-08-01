package huce.edu.vn.appdocsach.dto.paging;

import huce.edu.vn.appdocsach.constants.PagingConstants;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PaginationRequestDto {
    
    @Builder.Default
    private Integer pageSize = PagingConstants.DEFAULT_PAGE_SIZE;
    
    @Builder.Default
    private Integer pageNumber = PagingConstants.DEFAULT_PAGE_NUMBER;

    @Builder.Default
    private String sortBy = PagingConstants.DEFAULT_SORT_BY;

    public Integer getPageSize() {
        if (pageSize > PagingConstants.MAX_PAGE_SIZE) {
            pageSize = PagingConstants.MAX_PAGE_SIZE;
        } else if (pageSize < PagingConstants.MIN_PAGE_SIZE) {
            pageSize = PagingConstants.DEFAULT_PAGE_SIZE;
        } 
        return pageSize;
    }

    public Integer getPageNumber() {
        return pageNumber < 1 
            ? PagingConstants.DEFAULT_PAGE_NUMBER 
            : pageNumber;
    }
}
