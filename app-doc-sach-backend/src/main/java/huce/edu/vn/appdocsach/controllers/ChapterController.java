package huce.edu.vn.appdocsach.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import huce.edu.vn.appdocsach.annotations.auth.IsAdmin;
import huce.edu.vn.appdocsach.configs.AppObjectMapper;
import huce.edu.vn.appdocsach.dto.core.chapter.CreateChapterDto;
import huce.edu.vn.appdocsach.dto.core.chapter.FindChapterDto;
import huce.edu.vn.appdocsach.dto.core.chapter.ChapterDto;
import huce.edu.vn.appdocsach.dto.paging.PaginationResponseDto;
import huce.edu.vn.appdocsach.services.abstracts.core.IChapterService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.PostMapping;

@RestController
@RequestMapping("api/chapter")
@RequiredArgsConstructor
public class ChapterController {

    private final IChapterService chapterService;

    private final AppObjectMapper mapper;

    @Operation(summary = "Lấy thông tin các chapter")
    @GetMapping
    public PaginationResponseDto<ChapterDto> getChapterSimple(FindChapterDto findChapterDto) {
        return chapterService.getAllChapter(findChapterDto);
    }
    
    @Operation(summary = "Đọc chapter")
    @GetMapping("find/{id}")
    public List<String> getChapter(@PathVariable Integer id) {
        return chapterService.read(id);
    }

    @Operation(summary = "Upload 1 chapter cho 1 sách")
    @IsAdmin
    @PostMapping(path = "add", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Integer addNewChapter(
            @RequestPart List<MultipartFile> files,
            @RequestPart String json) {
        return chapterService.createNew(files, mapper.toPojo(json, CreateChapterDto.class));
    }

}