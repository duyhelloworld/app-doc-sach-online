package huce.edu.vn.appdocsach.utils;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.lang.NonNull;

import huce.edu.vn.appdocsach.paging.PagingRequest;

public class PagingHelper {

    public static PageRequest pageRequest(@NonNull PagingRequest request) {
        return PageRequest
            .ofSize(request.getPageSize())
            .withPage(request.getPageNumber() - 1)
            .withSort(Sort.by(request.getSortBy()));
    }
}
