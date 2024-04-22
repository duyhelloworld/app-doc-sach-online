package huce.edu.vn.appdocsach.dto.core.category;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SimpleCategoryDto {
    private Integer id;

    private String name;
}
