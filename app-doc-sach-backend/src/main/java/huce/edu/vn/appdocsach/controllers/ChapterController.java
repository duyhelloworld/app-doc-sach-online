package huce.edu.vn.appdocsach.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import huce.edu.vn.appdocsach.annotations.auth.IsAdmin;
import huce.edu.vn.appdocsach.dto.core.chapter.CreateChapterDto;
import huce.edu.vn.appdocsach.dto.core.chapter.FindChapterDto;
import huce.edu.vn.appdocsach.dto.core.chapter.SimpleChapterDto;
import huce.edu.vn.appdocsach.paging.PagingResponse;
import huce.edu.vn.appdocsach.services.core.ChapterService;
import huce.edu.vn.appdocsach.utils.Mapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

import org.springframework.web.bind.annotation.PostMapping;

@RestController
@RequestMapping("api/chapter")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ChapterController {

    ChapterService chapterService;

    @Autowired
    private Mapper mapper;

    @Operation(summary = "Lấy thông tin các chapter gần đây")
    @GetMapping
    public PagingResponse<SimpleChapterDto> getChapterSimple(FindChapterDto findChapterDto) {
        return chapterService.getAllChapterSimple(findChapterDto);
    }
    
    @Operation(summary = "Đọc chapter")
    @GetMapping("find/{id}")
    public List<String> getChapter(@PathVariable Integer id) {
        return chapterService.getChapter(id);
    }

    @Operation(summary = "Upload 1 chapter cho 1 sách")
    @IsAdmin
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Integer addNewChapter(
            @Parameter(description = "Upload từng ảnh 1 theo thứ tự")
            @RequestPart List<MultipartFile> files,
            @Parameter(description = "{ \"title\": \"A\" , \"bookId\": 1 }")
            @RequestPart String jsonDto) {
        return chapterService.create(files, mapper.getInstance(jsonDto, CreateChapterDto.class));
    }
}