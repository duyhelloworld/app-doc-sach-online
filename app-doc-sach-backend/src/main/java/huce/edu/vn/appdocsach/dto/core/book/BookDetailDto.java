package huce.edu.vn.appdocsach.dto.core.book;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import huce.edu.vn.appdocsach.annotations.time.ValidDatetimeFormat;
import huce.edu.vn.appdocsach.dto.core.chapter.ChapterDto;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonInclude(value = Include.NON_NULL)
public class BookDetailDto {

    private Integer id;

    private String title;

    @ValidDatetimeFormat
    private LocalDateTime lastUpdatedAt;

    private String coverImage;

    private Long viewCount;

    private String description;

    private String author;

    private Double averageRate;

    private List<String> categories;

    private List<ChapterDto> chapters;
}
