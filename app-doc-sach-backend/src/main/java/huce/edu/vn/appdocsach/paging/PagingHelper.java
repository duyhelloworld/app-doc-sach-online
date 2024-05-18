package huce.edu.vn.appdocsach.paging;

import org.springframework.data.domain.PageRequest;
import org.springframework.lang.NonNull;

public abstract class PagingHelper {

    public static PageRequest pageRequest(Class<?> clazz, @NonNull PagingRequest request) {
        return PageRequest.of(request.getPageNumber() - 1, request.getPageSize());
    }
}
