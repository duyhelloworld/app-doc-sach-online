package huce.edu.vn.appdocsach.dto.core.chapter;

import java.time.LocalDateTime;

import huce.edu.vn.appdocsach.dto.core.comment.CommentDto;
import huce.edu.vn.appdocsach.paging.BasePagingResponse;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ChapterInfoDto {
    private Integer id;

    private String title;

    private Integer bookId;

    private String bookTitle;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private BasePagingResponse<CommentDto> comments;
}
