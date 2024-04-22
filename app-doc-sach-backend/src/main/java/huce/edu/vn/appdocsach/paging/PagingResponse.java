package huce.edu.vn.appdocsach.paging;

import java.util.Collection;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PagingResponse<T> {
    private Integer totalPage;

    private Collection<T> values;
}
