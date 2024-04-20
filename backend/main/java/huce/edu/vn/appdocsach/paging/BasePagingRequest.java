package huce.edu.vn.appdocsach.paging;

import huce.edu.vn.appdocsach.constant.AppConst;

public class BasePagingRequest {

    public Integer pageSize = AppConst.PAGE_SIZE;
    
    public Integer pageNumber = 1;

    public String sortBy = "id";
}
