package huce.edu.vn.appdocsach.dto.paging;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.lang.NonNull;

public abstract class PagingHelper {

    public static PageRequest pageRequest(Class<?> clazz, @NonNull PaginationRequestDto request) {
        return PageRequest.of(request.getPageNumber() - 1, request.getPageSize());
    }

    public static PageRequest pageRequest(Class<?> clazz, @NonNull PaginationRequestDto request, Sort sort) {
        return PageRequest.of(request.getPageNumber() - 1, request.getPageSize(), sort);
    }
}
