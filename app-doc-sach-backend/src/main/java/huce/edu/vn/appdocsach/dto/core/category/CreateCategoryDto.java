package huce.edu.vn.appdocsach.dto.core.category;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Valid
public class CreateCategoryDto {

    @NotBlank(message = "CATEGORY_NAME_NOT_FOUND")
    private String name;

    private String description;
}
