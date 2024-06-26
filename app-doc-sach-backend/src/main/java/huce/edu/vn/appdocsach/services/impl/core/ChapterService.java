package huce.edu.vn.appdocsach.services.impl.core;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import huce.edu.vn.appdocsach.dto.core.chapter.CreateChapterDto;
import huce.edu.vn.appdocsach.dto.core.chapter.FindChapterDto;
import huce.edu.vn.appdocsach.dto.core.chapter.SimpleChapterDto;
import huce.edu.vn.appdocsach.entities.Book;
import huce.edu.vn.appdocsach.entities.Chapter;
import huce.edu.vn.appdocsach.enums.ResponseCode;
import huce.edu.vn.appdocsach.exception.AppException;
import huce.edu.vn.appdocsach.paging.PagingResponse;
import huce.edu.vn.appdocsach.repositories.BookRepo;
import huce.edu.vn.appdocsach.repositories.ChapterRepo;
import huce.edu.vn.appdocsach.services.abstracts.core.IChapterService;
import huce.edu.vn.appdocsach.services.abstracts.file.ICloudinaryService;
import huce.edu.vn.appdocsach.utils.AppLogger;
import huce.edu.vn.appdocsach.utils.ConvertUtils;
import huce.edu.vn.appdocsach.utils.NamingUtil;
import huce.edu.vn.appdocsach.paging.PagingHelper;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ChapterService implements IChapterService {

    ChapterRepo chapterRepo;

    BookRepo bookRepo;

    ICloudinaryService cloudinaryService;

    AppLogger<ChapterService> logger ;

    public ChapterService(ChapterRepo chapterRepo, BookRepo bookRepo, ICloudinaryService cloudinaryService) {
        this.chapterRepo = chapterRepo;
        this.bookRepo = bookRepo;
        this.cloudinaryService = cloudinaryService;
        this.logger = new AppLogger<>(ChapterService.class);
    }

    @Override
    @Transactional
    public List<String> getChapter(Integer id) {
        logger.onStart(Thread.currentThread(), id);
        Chapter chapter = chapterRepo.findById(id)
                .orElseThrow(() -> new AppException(ResponseCode.CHAPTER_NOT_FOUND));
        chapter.getBook().incViewCount();
        return cloudinaryService.getUrls(chapter.getFolderName());
    }

    @Override
    public boolean isEmpty() {
        return chapterRepo.count() == 0;
    }

    @Override
    @Transactional
    public PagingResponse<SimpleChapterDto> getAllChapterSimple(FindChapterDto findChapterDto) {
        logger.onStart(Thread.currentThread(), findChapterDto);
        PageRequest pageRequest = PagingHelper.pageRequest(Chapter.class, findChapterDto);
        Book book = bookRepo.findById(findChapterDto.getBookId())
            .orElseThrow(() -> new AppException(ResponseCode.BOOK_NOT_FOUND));
        book.incViewCount();
        Page<Chapter> chapters = chapterRepo.findByBook(book, pageRequest);
        bookRepo.save(book);
        PagingResponse<SimpleChapterDto> response = new PagingResponse<>();
        response.setTotalPage(chapters.getTotalPages());
        response.setValues(chapters.map(c -> ConvertUtils.convert(c)).getContent());
        return response;
    }

    @Override
    @Transactional
    public Integer create(List<MultipartFile> files, CreateChapterDto createChapterDto) {
        Book book = bookRepo.findById(createChapterDto.getBookId())
                .orElseThrow(() -> new AppException(ResponseCode.BOOK_NOT_FOUND));
        if (chapterRepo.existsByTitle(createChapterDto.getTitle())) {
            throw new AppException(ResponseCode.CHAPTER_TITLE_EXISTED);
        }
        String folderName = NamingUtil.normalizeFolderName(createChapterDto);
        if (chapterRepo.existsByFolderName(folderName)) {
            throw new AppException(ResponseCode.CHAPTER_TITLE_INVALID);
        }
        cloudinaryService.save(files, folderName);
        
        LocalDateTime now = LocalDateTime.now();
        Chapter chapter = new Chapter();
        chapter.setFolderName(folderName);
        chapter.setTitle(createChapterDto.getTitle());
        chapter.setCreatedAt(now);
        book.setUpdatedAt(now);
        chapter.setBook(book);
        chapterRepo.save(chapter);
        return chapter.getId();
    }
}
