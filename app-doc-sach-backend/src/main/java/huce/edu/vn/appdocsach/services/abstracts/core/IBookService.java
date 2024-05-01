package huce.edu.vn.appdocsach.services.abstracts.core;

import org.springframework.web.multipart.MultipartFile;

import huce.edu.vn.appdocsach.dto.core.book.BookDto;
import huce.edu.vn.appdocsach.dto.core.book.CreateBookDto;
import huce.edu.vn.appdocsach.dto.core.book.FindBookDto;
import huce.edu.vn.appdocsach.dto.core.book.SimpleBookDto;
import huce.edu.vn.appdocsach.paging.PagingResponse;

public interface IBookService {

    PagingResponse<SimpleBookDto> getAllBookSimple(FindBookDto findBookDto);

    boolean isEmpty();

    BookDto getBookById(Integer id);

    Integer addBook(CreateBookDto createBookDto, MultipartFile coverImage);
    
    Integer addBook(CreateBookDto createBookDto, byte[] coverImage, String fileName);
}
