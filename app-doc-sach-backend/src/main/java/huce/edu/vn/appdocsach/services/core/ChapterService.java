package huce.edu.vn.appdocsach.services.core;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import huce.edu.vn.appdocsach.dto.core.chapter.ChapterDto;
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
import huce.edu.vn.appdocsach.services.file.CloudinaryService;
import huce.edu.vn.appdocsach.utils.AppLogger;
import huce.edu.vn.appdocsach.utils.NamingUtil;
import huce.edu.vn.appdocsach.utils.PagingHelper;
import jakarta.transaction.Transactional;

@Service
public class ChapterService {

    @Autowired
    private ChapterRepo chapterRepo;

    @Autowired
    private BookRepo bookRepo;

    @Autowired
    private CloudinaryService cloudinaryService;

    private AppLogger<ChapterService> logger = new AppLogger<>(ChapterService.class);

    @Transactional
    public ChapterDto getChapter(Integer id) {
        logger.onStart(Thread.currentThread(), id);
        Chapter chapter = chapterRepo.findById(id)
                .orElseThrow(() -> new AppException(ResponseCode.ChapterNotFound));
        return convert(chapter, chapter.getBook());
    }

    public boolean isEmpty() {
        logger.onStart(Thread.currentThread());
        return chapterRepo.count() == 0;
    }

    @Transactional
    public PagingResponse<SimpleChapterDto> getAllChapterSimple(FindChapterDto findChapterDto) {
        logger.onStart(Thread.currentThread(), findChapterDto);
        PageRequest pageRequest = PagingHelper.pageRequest(findChapterDto);
        Page<Chapter> chapters = chapterRepo.findByBookId(findChapterDto.getBookId(), pageRequest);
        PagingResponse<SimpleChapterDto> response = new PagingResponse<>();
        response.setTotalPage(chapters.getTotalPages());
        response.setValues(chapters.map(c -> convert(c)).getContent());
        return response;
    }

    @Transactional
    public Integer create(List<MultipartFile> files, CreateChapterDto createChapterDto) {
        Book book = bookRepo.findById(createChapterDto.getBookId())
                .orElseThrow(() -> new AppException(ResponseCode.BookNotFound));
        if (chapterRepo.existsByTitle(createChapterDto.getTitle())) {
            throw new AppException(ResponseCode.ChapterTitleExisted);
        }
        String folderName = NamingUtil.normalizeFolderName(createChapterDto);
        if (chapterRepo.existsByFolderName(folderName)) {
            throw new AppException(ResponseCode.ChapterTitleCannotEncode);
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

    public ChapterDto convert(Chapter chapter, Book book) {
        return ChapterDto.builder()
                .id(chapter.getId())
                .title(chapter.getTitle())
                .lastUpdatedAt(chapter.getUpdatedAt() == null ? chapter.getCreatedAt() : chapter.getUpdatedAt())
                .bookId(book.getId())
                .bookTitle(book.getTitle())
                .build();
    }

    public SimpleChapterDto convert(Chapter chapter) {
        return SimpleChapterDto.builder()
            .id(chapter.getId())
            .title(chapter.getTitle())
            .lastUpdatedAt(chapter.getUpdatedAt() == null ? chapter.getCreatedAt() : chapter.getUpdatedAt())
            .build();
    }
}