package huce.edu.vn.appdocsach.dto.core.book;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

import huce.edu.vn.appdocsach.dto.core.chapter.SimpleChapterDto;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BookDto {
    private Integer id;

    private String title;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime lastUpdatedAt;

    private String coverImage;

    private Long viewCount;

    private String description;

    private String author;

    private Double averageRate;

    private String categories;

    private List<SimpleChapterDto> chapters;
}
