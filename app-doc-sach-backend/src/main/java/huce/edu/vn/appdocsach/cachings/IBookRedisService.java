package huce.edu.vn.appdocsach.cachings;

import huce.edu.vn.appdocsach.dto.core.book.FindBookDto;
import huce.edu.vn.appdocsach.dto.core.book.SimpleBookDto;
import huce.edu.vn.appdocsach.paging.PagingResponse;

public interface IBookRedisService {
    void clear();

    PagingResponse<SimpleBookDto> getAllBookSimple(FindBookDto findBookDto);

    void save(PagingResponse<SimpleBookDto> bookDto, FindBookDto findBookDto);
}
