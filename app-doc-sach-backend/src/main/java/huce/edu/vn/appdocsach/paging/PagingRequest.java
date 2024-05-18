package huce.edu.vn.appdocsach.paging;

import huce.edu.vn.appdocsach.constants.AppConst;
import lombok.Setter;

@Setter
public class PagingRequest {
    private Integer pageSize;
    
    private Integer pageNumber;

    public Integer getPageSize() {
        if (pageSize > AppConst.MAX_PAGE_SIZE) {
            pageSize = AppConst.MAX_PAGE_SIZE;
        } else if (pageSize < AppConst.MIN_PAGE_SIZE) {
            pageSize = AppConst.DEFAULT_PAGE_SIZE;
        } 
        return this.pageSize;
    }

    public Integer getPageNumber() {
        return this.pageNumber < 1 ? AppConst.DEFAULT_PAGE_NUMBER : this.pageNumber;
    }
}
