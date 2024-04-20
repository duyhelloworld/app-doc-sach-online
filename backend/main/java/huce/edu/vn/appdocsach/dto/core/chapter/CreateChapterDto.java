package huce.edu.vn.appdocsach.dto.core.chapter;

import org.hibernate.validator.constraints.Length;

import huce.edu.vn.appdocsach.exception.ValidationError;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CreateChapterDto {

    @NotBlank(message = ValidationError.BOOK_TITLE_MISSING)
    @Length(max = 255)
    private String title;

    @Min(value = 1)
    private Integer bookId;
}
