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
import huce.edu.vn.appdocsach.dto.core.chapter.ChapterDto;
import huce.edu.vn.appdocsach.dto.core.chapter.CreateChapterDto;
import huce.edu.vn.appdocsach.dto.core.chapter.FindChapterDto;
import huce.edu.vn.appdocsach.dto.core.chapter.SimpleChapterDto;
import huce.edu.vn.appdocsach.paging.PagingResponse;
import huce.edu.vn.appdocsach.services.core.ChapterService;
import huce.edu.vn.appdocsach.utils.Mapper;

import org.springframework.web.bind.annotation.PostMapping;

@RestController
@RequestMapping("api/chapter")
public class ChapterController {

    @Autowired
    private ChapterService chapterService;

    @Autowired
    private Mapper mapper;

    @GetMapping
    public PagingResponse<SimpleChapterDto> getChapterSimple(FindChapterDto findChapterDto) {
        return chapterService.getAllChapterSimple(findChapterDto);
    }
    
    @GetMapping("find")
    public ChapterDto getChapter(@PathVariable Integer id) {
        return chapterService.getChapter(id);
    }

    @IsAdmin
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Integer addNewChapter(
        @RequestPart List<MultipartFile> files,
        @RequestPart String jsonDto) {
        return chapterService.create(files, mapper.getInstance(jsonDto, CreateChapterDto.class));
    }
}