package huce.edu.vn.appdocsach.dto.core.book;

import java.util.List;

import org.hibernate.validator.constraints.Length;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreateBookDto {

    @Length(max = 255)
    @NotBlank(message = "BOOK_TITLE_MISSING")
    private String title;

    private String author;

    private String description;

    private List<Integer> categoryIds;
}
