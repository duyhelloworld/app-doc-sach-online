package huce.edu.vn.appdocsach.services.core;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import huce.edu.vn.appdocsach.constant.AppConst;
import huce.edu.vn.appdocsach.dto.core.book.BookDto;
import huce.edu.vn.appdocsach.dto.core.book.CreateBookDto;
import huce.edu.vn.appdocsach.dto.core.book.FindBookDto;
import huce.edu.vn.appdocsach.dto.core.category.MapCategory;
import huce.edu.vn.appdocsach.entities.Book;
import huce.edu.vn.appdocsach.entities.Category;
import huce.edu.vn.appdocsach.entities.Rating;
import huce.edu.vn.appdocsach.exception.AppError;
import huce.edu.vn.appdocsach.exception.AppException;
import huce.edu.vn.appdocsach.paging.BasePagingResponse;
import huce.edu.vn.appdocsach.repositories.BookRepo;
import huce.edu.vn.appdocsach.repositories.CategoryRepo;
import huce.edu.vn.appdocsach.services.BaseService;
import huce.edu.vn.appdocsach.services.file.CloudinaryService;
import huce.edu.vn.appdocsach.utils.AppLogger;
import huce.edu.vn.appdocsach.utils.PagingHelper;
import jakarta.transaction.Transactional;

@Service
public class BookService extends BaseService<BookDto> {

    @Autowired
    private BookRepo bookRepo;

    @Autowired
    private CategoryRepo categoryRepo;

    @Autowired
    private CloudinaryService cloudinaryService;

    private AppLogger<BookService> logger = new AppLogger<>(BookService.class);

    @Transactional
    public BasePagingResponse<BookDto> getAllBook(FindBookDto findBookDto) {
        logger.onStart(Thread.currentThread(), findBookDto);
        Pageable pageable = PagingHelper.pageRequest(findBookDto);
        Page<Book> books;
        if (findBookDto.categoryId != 0) {
            Category category = categoryRepo.findById(findBookDto.categoryId)
                .orElseThrow(
                    () -> new AppException(AppError.CategoryNotFound));
            books = bookRepo.findByCategory(category, pageable);
        } else {
            books = bookRepo.findAll(pageable);   
        }
        pagingResponse.values = books.map(b -> convert(b)).getContent();
        pagingResponse.totalPage = books.getTotalPages();
        return pagingResponse;
    }

    public boolean isEmpty() {
        return bookRepo.count() == 0;
    }

    @Transactional
    public BookDto getBookById(Integer id) {
        logger.onStart(Thread.currentThread(), id);
        return convert(bookRepo.findById(id)
                .orElseThrow(() -> new AppException(AppError.BookNotFound)));
    }

    @Transactional
    public Integer addBook(CreateBookDto createBookDto, MultipartFile coverImage) {
        try {
            return addBook(createBookDto, coverImage.getBytes(), coverImage.getOriginalFilename());
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
            throw new AppException(AppError.BookTitleExisted);
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

    public void deleteBookById(Integer id) {
        logger.onStart(Thread.currentThread(), id);
        Book book = bookRepo.findById(id)
                .orElseThrow(() -> new AppException(AppError.BookNotFound));
        bookRepo.delete(book);
    }

    private BookDto convert(Book book) {
        List<MapCategory> mapCategories = book.getCategories().stream()
                .map(category -> MapCategory.builder()
                        .id(category.getId())
                        .name(category.getName())
                        .build())
                .collect(Collectors.toList());
        return BookDto.builder()
            .id(book.getId())
            .title(book.getTitle())
            .author(book.getAuthor())
            .coverImage(book.getCoverImage())
            .description(book.getDescription())
            .createdAt(book.getCreatedAt())
            .updatedAt(book.getUpdatedAt())
            .categories(mapCategories)
            .averageRate(book.getRatings().stream()
                        .mapToDouble(Rating::getStar)
                        .average()
                        .orElse(0.0))
            .build();
    }
}
