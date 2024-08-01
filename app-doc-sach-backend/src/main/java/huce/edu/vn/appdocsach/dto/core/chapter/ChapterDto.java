package huce.edu.vn.appdocsach.dto.core.chapter;

import java.time.LocalDateTime;

import huce.edu.vn.appdocsach.annotations.time.ValidDatetimeFormat;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ChapterDto {
    private Integer id;
    
    private String title;

    @ValidDatetimeFormat
    private LocalDateTime lastUpdatedAt;
}
