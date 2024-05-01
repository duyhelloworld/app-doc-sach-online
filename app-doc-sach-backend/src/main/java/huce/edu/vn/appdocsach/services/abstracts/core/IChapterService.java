package huce.edu.vn.appdocsach.services.abstracts.core;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import huce.edu.vn.appdocsach.dto.core.chapter.CreateChapterDto;
import huce.edu.vn.appdocsach.dto.core.chapter.FindChapterDto;
import huce.edu.vn.appdocsach.dto.core.chapter.SimpleChapterDto;
import huce.edu.vn.appdocsach.paging.PagingResponse;

public interface IChapterService {
    
    PagingResponse<SimpleChapterDto> getAllChapterSimple(FindChapterDto findChapterDto);
    
    List<String> getChapter(Integer id);

    boolean isEmpty();

    Integer create(List<MultipartFile> files, CreateChapterDto createChapterDto);
}
