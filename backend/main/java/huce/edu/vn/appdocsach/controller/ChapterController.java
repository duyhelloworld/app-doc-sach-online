package huce.edu.vn.appdocsach.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import huce.edu.vn.appdocsach.annotation.auth.IsAdmin;
import huce.edu.vn.appdocsach.dto.core.chapter.ChapterInfoDto;
import huce.edu.vn.appdocsach.dto.core.chapter.CreateChapterDto;
import huce.edu.vn.appdocsach.dto.core.chapter.LoadChapterDto;
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
    public ChapterInfoDto getChapter(@RequestParam Integer id) {
        return chapterService.getChapter(id);
    }

    @GetMapping("read")
    public LoadChapterDto readChapter(@RequestParam Integer id) {
        return chapterService.readChapter(id);
    }

    @IsAdmin
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ChapterInfoDto addNewChapter(
        @RequestPart List<MultipartFile> files,
        @RequestPart String jsonDto) {
        return chapterService.create(files, mapper.getInstance(jsonDto, CreateChapterDto.class));
    }
}