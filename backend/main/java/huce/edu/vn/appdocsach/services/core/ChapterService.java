package huce.edu.vn.appdocsach.services.core;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import huce.edu.vn.appdocsach.dto.core.chapter.ChapterInfoDto;
import huce.edu.vn.appdocsach.dto.core.chapter.CreateChapterDto;
import huce.edu.vn.appdocsach.dto.core.chapter.LoadChapterDto;
import huce.edu.vn.appdocsach.dto.core.comment.CommentDto;
import huce.edu.vn.appdocsach.entities.Book;
import huce.edu.vn.appdocsach.entities.Chapter;
import huce.edu.vn.appdocsach.exception.AppError;
import huce.edu.vn.appdocsach.exception.AppException;
import huce.edu.vn.appdocsach.paging.BasePagingResponse;
import huce.edu.vn.appdocsach.repositories.BookRepo;
import huce.edu.vn.appdocsach.repositories.ChapterRepo;
import huce.edu.vn.appdocsach.services.BaseService;
import huce.edu.vn.appdocsach.services.file.CloudinaryService;
import huce.edu.vn.appdocsach.utils.AppLogger;
import huce.edu.vn.appdocsach.utils.NamingUtil;
import jakarta.transaction.Transactional;

@Service
public class ChapterService extends BaseService<ChapterInfoDto> {

    @Autowired
    private ChapterRepo chapterRepo;

    @Autowired
    private BookRepo bookRepo;

    @Autowired
    private CommentService commentService;

    @Autowired
    private CloudinaryService cloudinaryService;

    private AppLogger<ChapterService> logger = new AppLogger<>(ChapterService.class);

    @Transactional
    public ChapterInfoDto getChapter(Integer id) {
        logger.onStart(Thread.currentThread(), id);
        Chapter chapter = chapterRepo.findById(id)
                .orElseThrow(() -> new AppException(AppError.ChapterNotFound));
        BasePagingResponse<CommentDto> commentDtos = commentService.getCommentDto(chapter.getComments());
        return convert(chapter, chapter.getBook(), commentDtos);
    }

    public boolean isEmpty() {
        return chapterRepo.count() == 0;
    }

    @Transactional
    public LoadChapterDto readChapter(Integer id) {
        Chapter chapter = chapterRepo.findById(id)
                .orElseThrow(() -> new AppException(AppError.ChapterNotFound));
        BasePagingResponse<CommentDto> commentDtos = commentService.getCommentDto(chapter.getComments());
        List<String> imageUrls = cloudinaryService.getUrls(chapter.getFolderName());
        return LoadChapterDto.builder()
            .id(id)
            .imageUrls(imageUrls)
            .comments(commentDtos)
            .build();
    }

    @Transactional
    public ChapterInfoDto create(List<MultipartFile> files, CreateChapterDto createChapterDto) {
        Book book = bookRepo.findById(createChapterDto.getBookId())
                .orElseThrow(() -> new AppException(AppError.BookNotFound));
        if (chapterRepo.existsByTitle(createChapterDto.getTitle())) {
            throw new AppException(AppError.ChapterTitleExisted);
        }
        String folderName = NamingUtil.normalizeFolderName(createChapterDto);
        if (chapterRepo.existsByFolderName(folderName)) {
            throw new AppException(AppError.ChapterTitleCannotEncode);
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

        return convert(chapter, book, null);
    }

    private ChapterInfoDto convert(Chapter chapter, Book book, BasePagingResponse<CommentDto> commentDtos) {
        return ChapterInfoDto.builder()
                .id(chapter.getId())
                .title(chapter.getTitle())
                .comments(commentDtos)
                .createdAt(chapter.getCreatedAt())
                .updatedAt(chapter.getUpdatedAt())
                .bookId(book.getId())
                .bookTitle(book.getTitle())
                .build();
    }
}
