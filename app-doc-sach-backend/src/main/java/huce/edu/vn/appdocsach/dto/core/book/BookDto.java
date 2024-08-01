package huce.edu.vn.appdocsach.dto.core.book;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BookDto {

    private Integer id;

    private String title;

    private String coverImage;
}
