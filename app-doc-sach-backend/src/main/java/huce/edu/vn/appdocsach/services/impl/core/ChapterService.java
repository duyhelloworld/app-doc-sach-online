package huce.edu.vn.appdocsach.services.impl.core;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import huce.edu.vn.appdocsach.dto.core.chapter.CreateChapterDto;
import huce.edu.vn.appdocsach.dto.core.chapter.FindChapterDto;
import huce.edu.vn.appdocsach.dto.core.chapter.ChapterDto;
import huce.edu.vn.appdocsach.dto.paging.PagingHelper;
import huce.edu.vn.appdocsach.dto.paging.PaginationResponseDto;
import huce.edu.vn.appdocsach.entities.Book;
import huce.edu.vn.appdocsach.entities.Chapter;
import huce.edu.vn.appdocsach.enums.ResponseCode;
import huce.edu.vn.appdocsach.exception.AppException;
import huce.edu.vn.appdocsach.mapper.ModelMapper;
import huce.edu.vn.appdocsach.repositories.database.BookRepo;
import huce.edu.vn.appdocsach.repositories.database.ChapterRepo;
import huce.edu.vn.appdocsach.services.abstracts.core.IChapterService;
import huce.edu.vn.appdocsach.services.abstracts.file.ICloudinaryService;
import huce.edu.vn.appdocsach.utils.JsonUtils;
import huce.edu.vn.appdocsach.utils.NamingUtil;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@AllArgsConstructor
public class ChapterService implements IChapterService {

    ChapterRepo chapterRepo;

    BookRepo bookRepo;

    ICloudinaryService cloudinaryService;

    @Override
    @Transactional
    public List<String> read(Integer id) {
        log.info("Start read with input : ", id);
        Chapter chapter = chapterRepo.findById(id)
                .orElseThrow(() -> new AppException(ResponseCode.CHAPTER_NOT_FOUND));
        chapter.getBook().incrementViewCount();
        return cloudinaryService.getUrls(chapter.getFolderName());
    }

    @Override
    public boolean isEmpty() {
        return chapterRepo.count() == 0;
    }

    @Override
    @Transactional
    public PaginationResponseDto<ChapterDto> getAllChapter(FindChapterDto findChapterDto) {
        log.info("Start getAllChapter with input : ", JsonUtils.json(findChapterDto));
        PageRequest pageRequest = PagingHelper.pageRequest(Chapter.class, findChapterDto);
        Book book = bookRepo.findById(findChapterDto.getBookId())
            .orElseThrow(() -> new AppException(ResponseCode.BOOK_NOT_FOUND));
        book.incrementViewCount();
        Page<Chapter> chapters = chapterRepo.findByBook(book, pageRequest);
        bookRepo.save(book);
        return PaginationResponseDto.<ChapterDto>builder()
            .values(chapters.stream().map(c -> ModelMapper.convert(c)).toList())
            .totalPage(chapters.getTotalPages())
            .build();
    }

    @Override
    @Transactional
    public Integer createNew(List<MultipartFile> files, CreateChapterDto createChapterDto) {
        log.info("Start createNew with input : ", JsonUtils.json(createChapterDto), files.size() + " files");
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
        chapter.setCreateAt(now);
        book.setUpdateAt(now);
        chapter.setBook(book);
        chapterRepo.save(chapter);
        return chapter.getId();
    }
}
