package huce.edu.vn.appdocsach.paging;

import huce.edu.vn.appdocsach.constants.AppConst;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PagingRequest {

    private Integer pageSize = AppConst.DEFAULT_PAGE_SIZE;
    
    private Integer pageNumber = AppConst.DEFAULT_PAGE_NUMBER;

    private String sortBy = AppConst.DEFAULT_SORT_BY;
}
