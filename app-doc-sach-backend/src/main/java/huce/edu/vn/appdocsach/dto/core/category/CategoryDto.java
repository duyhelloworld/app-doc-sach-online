package huce.edu.vn.appdocsach.dto.core.category;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CategoryDto {
    private Integer id;

    private String name;
}
