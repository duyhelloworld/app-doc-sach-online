package huce.edu.vn.appdocsach.services.impl.core;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import huce.edu.vn.appdocsach.dto.core.book.BookDto;
import huce.edu.vn.appdocsach.dto.core.book.CreateBookDto;
import huce.edu.vn.appdocsach.dto.core.book.SimpleBookDto;
import huce.edu.vn.appdocsach.entities.Book;
import huce.edu.vn.appdocsach.entities.Category;
import huce.edu.vn.appdocsach.enums.ResponseCode;
import huce.edu.vn.appdocsach.exception.AppException;
import huce.edu.vn.appdocsach.paging.PagingHelper;
import huce.edu.vn.appdocsach.paging.PagingRequest;
import huce.edu.vn.appdocsach.paging.PagingResponse;
import huce.edu.vn.appdocsach.repositories.BookRepo;
import huce.edu.vn.appdocsach.repositories.CategoryRepo;
import huce.edu.vn.appdocsach.services.abstracts.core.IBookService;
import huce.edu.vn.appdocsach.services.abstracts.file.ICloudinaryService;
import huce.edu.vn.appdocsach.utils.AppLogger;
import huce.edu.vn.appdocsach.utils.ConvertUtils;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class BookService implements IBookService {

    BookRepo bookRepo;

    CategoryRepo categoryRepo;

    ICloudinaryService cloudinaryService;

    AppLogger<BookService> logger ;

    public BookService(BookRepo bookRepo, CategoryRepo categoryRepo, ICloudinaryService cloudinaryService) {
        this.bookRepo = bookRepo;
        this.categoryRepo = categoryRepo;
        this.cloudinaryService = cloudinaryService;
        this.logger = new AppLogger<>(BookService.class);
    }

    @Override
    @Transactional
    public PagingResponse<SimpleBookDto> getAllBook(PagingRequest pagingRequest, Integer categoryId, String sort,
            String keyword) {
        logger.onStart(Thread.currentThread(), pagingRequest, "categoryId", categoryId, "sort", sort, "keyword", keyword);
    
        Sort sortResult;
        switch (sort) {
            case "id":
                sortResult = Sort.by(Direction.ASC, "id");
                break;
            case "viewCount":
                sortResult = Sort.by(Direction.DESC, "viewCount");
                break;
            default:
                throw new AppException(ResponseCode.INVALID_SORT_BY);
        }
        Pageable pageRequest = PagingHelper.pageRequest(Book.class, pagingRequest, sortResult);
        
        Page<Book> books;
        if (StringUtils.hasText(keyword)) {
            books = bookRepo.search(keyword, pageRequest);
        } else if (categoryId > 0) {
            books = bookRepo.findByCategoryId(categoryId, pageRequest);
        } else {
            books = bookRepo.findAll(pageRequest);
        }
        PagingResponse<SimpleBookDto> pagingResponse = new PagingResponse<>();
        pagingResponse.setValues(books.map(b -> ConvertUtils.convertSimple(b)).getContent());
        pagingResponse.setTotalPage(books.getTotalPages());
        return pagingResponse;
    }

    @Override
    public boolean isEmpty() {
        return bookRepo.count() == 0;
    }

    @Override
    @Transactional
    public BookDto getBookById(Integer id) {
        logger.onStart(Thread.currentThread(), id);
        return ConvertUtils.convert(bookRepo.findById(id)
                .orElseThrow(() -> new AppException(ResponseCode.BOOK_NOT_FOUND)));
    }

    @Override
    @Transactional
    public Integer addBook(CreateBookDto createBookDto, byte[] coverImage, String fileName) {
        String coverImageUrl;
        logger.onStart(Thread.currentThread(), createBookDto, "Cover image : ", fileName);
        coverImageUrl = cloudinaryService.save(coverImage, fileName);
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
}
