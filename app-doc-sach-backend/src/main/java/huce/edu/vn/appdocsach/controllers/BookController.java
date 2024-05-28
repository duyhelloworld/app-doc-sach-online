package huce.edu.vn.appdocsach.controllers;

import java.io.IOException;

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
import huce.edu.vn.appdocsach.constants.AppConst;
import huce.edu.vn.appdocsach.dto.core.book.BookDto;
import huce.edu.vn.appdocsach.dto.core.book.CreateBookDto;
import huce.edu.vn.appdocsach.dto.core.book.SimpleBookDto;
import huce.edu.vn.appdocsach.dto.core.rate.CreateRateDto;
import huce.edu.vn.appdocsach.paging.PagingRequest;
import huce.edu.vn.appdocsach.paging.PagingResponse;
import huce.edu.vn.appdocsach.services.abstracts.core.IBookService;
import huce.edu.vn.appdocsach.services.abstracts.core.IRateService;
import huce.edu.vn.appdocsach.services.impl.auth.users.AuthUser;
import huce.edu.vn.appdocsach.utils.Mapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("api/book")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class BookController {

    Mapper mapper;

    IBookService bookService;

    IRateService rateService;

    @Operation(summary = "Lấy tất cả sách cho trang home + Tìm kiếm theo thể loại & từ khóa")
    @GetMapping("all")
    public PagingResponse<SimpleBookDto> getAllBook(
        @RequestParam(defaultValue = "1") int pageSize, 
        @RequestParam(defaultValue = "1") int pageNumber,
        @RequestParam(defaultValue = "0") int categoryId,
        @RequestParam(required = false) String keyword,
        @RequestParam(defaultValue = AppConst.DEFAULT_SORT_BY) String sort) {
        return bookService.getAllBook(PagingRequest.of(pageSize, pageNumber), categoryId, sort, keyword);
    }

    @Operation(summary = "Lấy sách theo id, dùng cho trang hiển thị thông tin tin cụ thể của sách")
    @GetMapping("find/{id}")
    public BookDto getBookById(@PathVariable Integer id) {
        return bookService.getBookById(id);
    }

    @Operation(summary = "Thêm mới 1 sách với ảnh bìa cung cấp")
    @IsAdmin
    @PostMapping(path = "with-cover", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Integer addNewWithImage(@AuthenticationPrincipal AuthUser authUser,
	    @Parameter(description = "{\"title\": \"\", \"author\": \"\", \"description\": \"\", \"categoryIds\": [1, 2, 3, 4, 5] }")
            @RequestPart String jsonCreateBookDto,
            @RequestPart(required = true) MultipartFile coverImage) {
        try {
            return bookService.addBook(mapper.getInstance(jsonCreateBookDto, CreateBookDto.class), coverImage.getBytes(), coverImage.getOriginalFilename());
        } catch (IOException e) {
            e.printStackTrace();
            return 0;
        }
    }

    @Operation(summary = "Nhấn đánh giá 1 sách. Gọi lần nữa để hủy")
    @IsUser
    @PutMapping("rate")
    public void rateBook(@AuthenticationPrincipal AuthUser authUser, 
        @RequestParam Integer bookId,
        @RequestBody CreateRateDto createRateDto) {
            rateService.toggerRate(authUser.getUser(), createRateDto, bookId);
    }
}
