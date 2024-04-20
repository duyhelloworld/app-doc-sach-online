package huce.edu.vn.appdocsach.dto.core.chapter;

import java.util.List;

import huce.edu.vn.appdocsach.dto.core.comment.CommentDto;
import huce.edu.vn.appdocsach.paging.BasePagingResponse;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LoadChapterDto {
    private Integer id;

    private List<String> imageUrls;

    private BasePagingResponse<CommentDto> comments;
}
