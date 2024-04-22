package huce.edu.vn.appdocsach.services.core;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import huce.edu.vn.appdocsach.constants.AppConst;
import huce.edu.vn.appdocsach.dto.core.book.BookDto;
import huce.edu.vn.appdocsach.dto.core.book.CreateBookDto;
import huce.edu.vn.appdocsach.dto.core.book.FindBookDto;
import huce.edu.vn.appdocsach.dto.core.book.SimpleBookDto;
import huce.edu.vn.appdocsach.entities.Book;
import huce.edu.vn.appdocsach.entities.Category;
import huce.edu.vn.appdocsach.entities.Rating;
import huce.edu.vn.appdocsach.enums.ResponseCode;
import huce.edu.vn.appdocsach.exception.AppException;
import huce.edu.vn.appdocsach.paging.PagingHelper;
import huce.edu.vn.appdocsach.paging.PagingResponse;
import huce.edu.vn.appdocsach.repositories.BookRepo;
import huce.edu.vn.appdocsach.repositories.CategoryRepo;
import huce.edu.vn.appdocsach.services.file.CloudinaryService;
import huce.edu.vn.appdocsach.utils.AppLogger;
import jakarta.transaction.Transactional;

@Service
public class BookService {

    @Autowired
    private BookRepo bookRepo;

    @Autowired
    private CategoryRepo categoryRepo;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private CloudinaryService cloudinaryService;

    private AppLogger<BookService> logger = new AppLogger<>(BookService.class);

    @Transactional
    public PagingResponse<SimpleBookDto> getAllBookSimple(FindBookDto findBookDto) {
        logger.onStart(Thread.currentThread(), findBookDto);
        Pageable pageRequest = PagingHelper.pageRequest(Book.class, findBookDto);
        // Pageable pageRequest = Pageable.unpaged();
        Page<Book> books;
        Integer cid = findBookDto.getCategoryId();
        String keyword = findBookDto.getKeyword();
        if (cid != 0) {
            books = bookRepo.findByCategoryId(findBookDto.getCategoryId(), pageRequest);
        } else if (StringUtils.hasText(keyword)) {
            books = bookRepo.search(keyword, pageRequest);
        } else {
            books = bookRepo.findAll(pageRequest);
        }
        PagingResponse<SimpleBookDto> pagingResponse = new PagingResponse<>();
        pagingResponse.setValues(books.map(b -> convertSimple(b)).getContent());
        pagingResponse.setTotalPage(books.getTotalPages());
        return pagingResponse;
    }

    public boolean isEmpty() {
        return bookRepo.count() == 0;
    }

    @Transactional
    public BookDto getBookById(Integer id) {
        logger.onStart(Thread.currentThread(), id);
        return convert(bookRepo.findById(id)
                .orElseThrow(() -> new AppException(ResponseCode.BOOK_NOT_FOUND)));
    }

    @Transactional
    public Integer addBook(CreateBookDto createBookDto, MultipartFile coverImage) {
        try {
            return coverImage != null ? addBook(createBookDto, coverImage.getBytes(), coverImage.getOriginalFilename()) 
                : addBook(createBookDto, null, null);
        } catch (IOException e) {
            logger.error(e);
            return 0;
        }
    }

    @Transactional
    public Integer addBook(CreateBookDto createBookDto, byte[] coverImage, String fileName) {
        String coverImageUrl;
        if (coverImage != null) {
            logger.onStart(Thread.currentThread(), createBookDto, "Cover image : ", fileName);
            coverImageUrl = cloudinaryService.save(coverImage, fileName);
        } else {
            logger.onStart(Thread.currentThread(), createBookDto, "Cover image : ", null);
            coverImageUrl = AppConst.DEFAULT_COVER_IMAGE_URL;
        }
        if (bookRepo.existsByTitle(createBookDto.getTitle())) {
            throw new AppException(ResponseCode.BOOK_TITLE_EXISTED);
        }
        List<Category> categories = categoryRepo.findByIds(createBookDto.getCategoryIds());
        Book book = new Book();
        book.setAuthor(createBookDto.getAuthor());
        book.setTitle(createBookDto.getTitle());
        book.setDescription(createBookDto.getDescription());
        book.setCoverImage(coverImageUrl);
        book.setCreatedAt(LocalDateTime.now());
        book.setCategories(categories);
        bookRepo.save(book);
        return book.getId();
    }

    public SimpleBookDto convertSimple(Book book) {
        return SimpleBookDto.builder()
            .id(book.getId())
            .coverImage(book.getCoverImage())
            .title(book.getTitle())
            .author(book.getAuthor())
            .lastUpdatedAt(book.getUpdatedAt() == null ? book.getCreatedAt() : book.getUpdatedAt())
            .build();
    }

    public BookDto convert(Book book) {
        return BookDto.builder()
            .id(book.getId())
            .title(book.getTitle())
            .author(book.getAuthor())
            .coverImage(book.getCoverImage())
            .description(book.getDescription())
            .lastUpdatedAt(book.getUpdatedAt() == null ? book.getCreatedAt() : book.getUpdatedAt())
            .categories(book.getCategories().stream()
                .map(category -> categoryService.convertSimple(category))
                .collect(Collectors.toList()))
            .averageRate(book.getRatings().stream()
                        .mapToDouble(Rating::getStar)
                        .average()
                        .orElse(0.0))
            .build();
    }
}
