package huce.edu.vn.appdocsach.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
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

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("api/book")
public class BookController {
    @Autowired
    private Mapper mapper;

    @Autowired
    private BookService bookService;

    @Autowired
    private RateService rateService;

    @GetMapping("all")
    public PagingResponse<SimpleBookDto> getAllBook(FindBookDto findBookDto) {
        return bookService.getAllBookSimple(findBookDto);
    }

    @GetMapping("find")
    public BookDto getBookById(@RequestParam Integer id) {
        return bookService.getBookById(id);
    }

    @IsAdmin
    @PostMapping("without-cover")
    public Integer addNew(@AuthenticationPrincipal AuthUser authUser, @RequestBody CreateBookDto createBookDto) {
        return bookService.addBook(createBookDto, null);
    }

    @IsAdmin
    @PostMapping(path = "with-cover", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Integer addNewWithImage(@AuthenticationPrincipal AuthUser authUser,
            @RequestPart String jsonCreateBookDto,
            @RequestPart MultipartFile coverImage) {
        return bookService.addBook(mapper.getInstance(jsonCreateBookDto, CreateBookDto.class), coverImage);
    }

    @IsUser
    @PutMapping("rate")
    public void rateBook(@AuthenticationPrincipal AuthUser authUser, 
        @RequestParam Integer bookId,
        @RequestBody CreateRateDto createRateDto) {
            rateService.toggerRate(authUser.getUser(), createRateDto, bookId);
    }
}
