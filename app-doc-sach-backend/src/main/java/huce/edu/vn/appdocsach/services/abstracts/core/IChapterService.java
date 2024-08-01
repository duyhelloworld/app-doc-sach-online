package huce.edu.vn.appdocsach.services.abstracts.core;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import huce.edu.vn.appdocsach.dto.core.chapter.CreateChapterDto;
import huce.edu.vn.appdocsach.dto.core.chapter.FindChapterDto;
import huce.edu.vn.appdocsach.dto.core.chapter.ChapterDto;
import huce.edu.vn.appdocsach.dto.paging.PaginationResponseDto;
import huce.edu.vn.appdocsach.services.abstracts.test.ITestableService;

public interface IChapterService extends ITestableService {
    
    /**
     * Lấy các chương truyện theo phân trang 
     * @param findChapterDto {@link FindChapterDto}
     * @return phân trang các chương truyện  {@link PaginationResponseDto}  {@link ChapterDto}
     */
    PaginationResponseDto<ChapterDto> getAllChapter(FindChapterDto findChapterDto);
    
    /**
     * Đọc url ảnh của chương truyện
     * @param id mã chương
     * @return List<String> urls 
     */
    List<String> read(Integer id);

    /**
     * Tạo mới chương
     * @param files các file ảnh chương
     * @param createChapterDto  {@link CreateChapterDto}
     * @return mã chương vừa tạo
     */
    Integer createNew(List<MultipartFile> files, CreateChapterDto createChapterDto);
}
