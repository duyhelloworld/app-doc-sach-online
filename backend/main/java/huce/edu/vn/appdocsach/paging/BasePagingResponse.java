package huce.edu.vn.appdocsach.paging;

import java.util.Collection;

public class BasePagingResponse<T> {
    public Integer totalPage;

    public Collection<T> values;
}
