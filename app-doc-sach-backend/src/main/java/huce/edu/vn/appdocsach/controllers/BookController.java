package huce.edu.vn.appdocsach.controllers;

import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import huce.edu.vn.appdocsach.annotations.auth.IsAdmin;
import huce.edu.vn.appdocsach.annotations.auth.IsUser;
import huce.edu.vn.appdocsach.configs.AppObjectMapper;
import huce.edu.vn.appdocsach.dto.core.book.BookDetailDto;
import huce.edu.vn.appdocsach.dto.core.book.CreateBookDto;
import huce.edu.vn.appdocsach.dto.core.book.FindBookDto;
import huce.edu.vn.appdocsach.dto.core.book.BookDto;
import huce.edu.vn.appdocsach.dto.core.rate.CreateRateDto;
import huce.edu.vn.appdocsach.dto.paging.PaginationResponseDto;
import huce.edu.vn.appdocsach.services.abstracts.core.IBookService;
import huce.edu.vn.appdocsach.services.abstracts.core.IRateService;
import huce.edu.vn.appdocsach.services.impl.auth.users.AuthenticatedUser;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("api/book")
@RequiredArgsConstructor
public class BookController {

    AppObjectMapper mapper;

    IBookService bookService;

    IRateService rateService;

    @Operation(summary = "Lấy tất cả sách cho trang home + Tìm kiếm theo thể loại & từ khóa")
    @GetMapping("all")
    public PaginationResponseDto<BookDto> getAllBook(FindBookDto findBookDto) {
        return bookService.getAllBook(findBookDto);
    }

    @Operation(summary = "Lấy sách theo id, dùng cho trang hiển thị thông tin tin cụ thể của sách")
    @GetMapping("{id}")
    public BookDetailDto getBookById(@PathVariable Integer id) {
        return bookService.getBookById(id);
    }

    @Operation(summary = "Thêm mới 1 sách với ảnh bìa cung cấp")
    @IsAdmin
    @PostMapping(path = "add", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Integer addNewBook(
        @RequestPart MultipartFile coverImage, 
        @RequestPart String json) {
        return bookService.addBook(coverImage, mapper.toPojo(json, CreateBookDto.class));
    }

    @Operation(summary = "Đánh giá sách")
    @IsUser
    @PutMapping("rate")
    public void rateBook(@AuthenticationPrincipal AuthenticatedUser authUser, 
        @RequestParam Integer bookId,
        @RequestBody CreateRateDto createRateDto) {
            rateService.rate(authUser.getUser(), createRateDto, bookId);
    }
}
