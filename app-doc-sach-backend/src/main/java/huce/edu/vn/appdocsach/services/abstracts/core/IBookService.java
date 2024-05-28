package huce.edu.vn.appdocsach.services.abstracts.core;

import huce.edu.vn.appdocsach.dto.core.book.BookDto;
import huce.edu.vn.appdocsach.dto.core.book.CreateBookDto;
import huce.edu.vn.appdocsach.dto.core.book.SimpleBookDto;
import huce.edu.vn.appdocsach.paging.PagingRequest;
import huce.edu.vn.appdocsach.paging.PagingResponse;

public interface IBookService {

    PagingResponse<SimpleBookDto> getAllBook(PagingRequest pagingRequest, Integer categoryId, String sort, String keyword);

    boolean isEmpty();

    BookDto getBookById(Integer id);

    Integer addBook(CreateBookDto createBookDto, byte[] coverImage, String fileName);
}
