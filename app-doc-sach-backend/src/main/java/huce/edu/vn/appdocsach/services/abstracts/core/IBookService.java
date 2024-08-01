package huce.edu.vn.appdocsach.services.abstracts.core;

import org.springframework.web.multipart.MultipartFile;

import huce.edu.vn.appdocsach.dto.core.book.BookDetailDto;
import huce.edu.vn.appdocsach.dto.core.book.CreateBookDto;
import huce.edu.vn.appdocsach.dto.core.book.FindBookDto;
import huce.edu.vn.appdocsach.dto.core.book.BookDto;
import huce.edu.vn.appdocsach.dto.paging.PaginationResponseDto;
import huce.edu.vn.appdocsach.services.abstracts.test.ITestableService;

public interface IBookService extends ITestableService {

    /**
     * Lấy tất cả các truyện với phân trang
     * @param findBookDto {@link FindBookDto} tham số tra cứu truyện
     * @return {@link PaginationResponseDto } {@link BookDto}
     */
    PaginationResponseDto<BookDto> getAllBook(FindBookDto findBookDto);

    /**
     * Lấy sách theo mã
     * @param id mã sách
     * @return {@link BookDetailDto}
     */
    BookDetailDto getBookById(Integer id);

    /**
     * Thêm sách vào cơ sở
     * @param coverImage Ảnh bìa
     * @param createBookDto {@link CreateBookDto}
     * @return Mã sách vừa tạo - Integer
     */
    Integer addBook(MultipartFile coverImage, CreateBookDto createBookDto);

}