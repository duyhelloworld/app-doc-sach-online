package huce.edu.vn.appdocsach.utils;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.lang.NonNull;

import huce.edu.vn.appdocsach.paging.BasePagingRequest;

public class PagingHelper {

    public static PageRequest pageRequest(@NonNull BasePagingRequest request) {
        return PageRequest.of(request.pageNumber - 1, request.pageSize, Sort.by(request.sortBy));
    }
}
