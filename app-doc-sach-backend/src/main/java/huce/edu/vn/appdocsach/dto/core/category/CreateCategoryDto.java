package huce.edu.vn.appdocsach.dto.core.category;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CreateCategoryDto {

    @NotBlank
    private String name;

    @Nullable
    private String description;
}
