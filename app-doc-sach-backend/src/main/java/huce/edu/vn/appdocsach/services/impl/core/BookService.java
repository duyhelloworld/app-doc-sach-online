package huce.edu.vn.appdocsach.services.impl.core;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import huce.edu.vn.appdocsach.dto.core.book.BookDetailDto;
import huce.edu.vn.appdocsach.dto.core.book.CreateBookDto;
import huce.edu.vn.appdocsach.dto.core.book.FindBookDto;
import huce.edu.vn.appdocsach.dto.core.book.BookDto;
import huce.edu.vn.appdocsach.dto.paging.PagingHelper;
import huce.edu.vn.appdocsach.dto.paging.PaginationResponseDto;
import huce.edu.vn.appdocsach.entities.Book;
import huce.edu.vn.appdocsach.entities.Category;
import huce.edu.vn.appdocsach.enums.ResponseCode;
import huce.edu.vn.appdocsach.exception.AppException;
import huce.edu.vn.appdocsach.mapper.ModelMapper;
import huce.edu.vn.appdocsach.repositories.database.BookRepo;
import huce.edu.vn.appdocsach.repositories.database.CategoryRepo;
import huce.edu.vn.appdocsach.services.abstracts.core.IBookService;
import huce.edu.vn.appdocsach.services.abstracts.file.ICloudinaryService;
import huce.edu.vn.appdocsach.utils.JsonUtils;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@AllArgsConstructor
public class BookService implements IBookService {

    BookRepo bookRepo;

    CategoryRepo categoryRepo;

    ICloudinaryService cloudinaryService;

    @Override
    @Transactional
    public PaginationResponseDto<BookDto> getAllBook(FindBookDto findBookDto) {
        log.info("Start getAllBook with input : ", JsonUtils.json(findBookDto));
        
        Sort sortResult;
        switch (findBookDto.getSortBy()) {
            case "id":
                sortResult = Sort.by(Direction.ASC, "id");
                break;
            case "viewCount":
                sortResult = Sort.by(Direction.DESC, "viewCount");
                break;
            default:
                throw new AppException(ResponseCode.INVALID_SORT_BY);
        }
        Pageable pageRequest = PagingHelper.pageRequest(Book.class, findBookDto, sortResult);
        
        Page<Book> books;
        if (StringUtils.hasText(findBookDto.getKeyword())) {
            books = bookRepo.search(findBookDto.getKeyword(), pageRequest);
        } else if (findBookDto.getCategoryId() != null) {
            books = bookRepo.findByCategoryId(findBookDto.getCategoryId(), pageRequest);
        } else {
            books = bookRepo.findAll(pageRequest);
        }
        return PaginationResponseDto.<BookDto>builder()
            .values(books.map(b -> ModelMapper.convertSimple(b)).getContent())
            .totalPage(books.getTotalPages())
            .build();
    }

    @Override
    public boolean isEmpty() {
        return bookRepo.count() == 0;
    }

    @Override
    @Transactional
    public BookDetailDto getBookById(Integer id) {
        log.info("Start getBookById with input : {}", id);
        return ModelMapper.convert(bookRepo.findById(id)
                .orElseThrow(() -> new AppException(ResponseCode.BOOK_NOT_FOUND)));
    }

    @Override
    @Transactional
    public Integer addBook(MultipartFile coverImage, CreateBookDto createBookDto) {
        String coverImageUrl;
        log.info("Start getBookById with input : ", JsonUtils.json(createBookDto));
        coverImageUrl = cloudinaryService.save(coverImage);
        if (bookRepo.existsByTitle(createBookDto.getTitle())) {
            throw new AppException(ResponseCode.BOOK_TITLE_EXISTED);
        }
        List<Category> categories = categoryRepo.findAllById(createBookDto.getCategoryIds());
        Book book = new Book();
        book.setAuthor(createBookDto.getAuthor());
        book.setTitle(createBookDto.getTitle());
        book.setDescription(createBookDto.getDescription());
        book.setCoverImage(coverImageUrl);
        book.setCreateAt(LocalDateTime.now());
        book.setCategories(categories);
        bookRepo.save(book);
        return book.getId();
    }
}
