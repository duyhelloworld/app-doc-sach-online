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
import huce.edu.vn.appdocsach.dto.core.book.BookDto;
import huce.edu.vn.appdocsach.dto.core.book.CreateBookDto;
import huce.edu.vn.appdocsach.dto.core.book.FindBookDto;
import huce.edu.vn.appdocsach.dto.core.book.SimpleBookDto;
import huce.edu.vn.appdocsach.dto.core.rate.CreateRateDto;
import huce.edu.vn.appdocsach.paging.PagingResponse;
import huce.edu.vn.appdocsach.services.auth.users.AuthUser;
import huce.edu.vn.appdocsach.services.core.BookService;
import huce.edu.vn.appdocsach.services.core.RateService;
import huce.edu.vn.appdocsach.utils.Mapper;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
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

    private Mapper mapper;

    private BookService bookService;

    private RateService rateService;

    @Operation(summary = "Lấy tất cả sách cho trang home + Tìm kiếm theo thể loại & từ khóa")
    @GetMapping("all")
    public PagingResponse<SimpleBookDto> getAllBook(FindBookDto findBookDto) {
        return bookService.getAllBookSimple(findBookDto);
    }

    @Operation(summary = "Lấy sách theo id, dùng cho trang hiển thị thông tin tin cụ thể của sách")
    @GetMapping("find/{id}")
    public BookDto getBookById(@PathVariable Integer id) {
        return bookService.getBookById(id);
    }

    @Operation(summary = "Thêm mới 1 sách với ảnh bìa mặc định")
    @IsAdmin
    @PostMapping("without-cover")
    public Integer addNew(@AuthenticationPrincipal AuthUser authUser, @RequestBody CreateBookDto createBookDto) {
        return bookService.addBook(createBookDto, null);
    }

    @Operation(summary = "Thêm mới 1 sách với ảnh bìa cung cấp")
    @IsAdmin
    @PostMapping(path = "with-cover", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Integer addNewWithImage(@AuthenticationPrincipal AuthUser authUser,
            @RequestPart String jsonCreateBookDto,
            @RequestPart MultipartFile coverImage) {
        return bookService.addBook(mapper.getInstance(jsonCreateBookDto, CreateBookDto.class), coverImage);
    }

    @Operation(summary = "Nhấn đánh giá 1 sách. Gọi lần nữa để hủy")
    @IsUser
    @PutMapping("rate")
    public void rateBook(@AuthenticationPrincipal AuthUser authUser, 
        @RequestParam Integer bookId,
        @RequestBody @Valid CreateRateDto createRateDto) {
            rateService.toggerRate(authUser.getUser(), createRateDto, bookId);
    }
}
