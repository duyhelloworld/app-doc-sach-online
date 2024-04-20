package huce.edu.vn.appdocsach.services;

import org.springframework.stereotype.Service;

import huce.edu.vn.appdocsach.paging.BasePagingResponse;

@Service
public class BaseService<T> {
    protected BasePagingResponse<T> pagingResponse = new BasePagingResponse<>();
}
