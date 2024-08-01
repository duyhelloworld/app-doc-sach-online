package huce.edu.vn.appdocsach.dto.paging;

import java.util.Collection;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class PaginationResponseDto<T> {

    private Integer totalPage;

    private Collection<T> values;

}
