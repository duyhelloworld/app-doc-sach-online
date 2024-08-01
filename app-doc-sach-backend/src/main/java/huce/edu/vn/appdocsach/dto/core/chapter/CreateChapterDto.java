package huce.edu.vn.appdocsach.dto.core.chapter;

import org.hibernate.validator.constraints.Length;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Valid
public class CreateChapterDto {

    @NotBlank(message = "BOOK_TITLE_MISSING")
    @Length(max = 255)
    private String title;

    @Min(value = 1)
    private Integer bookId;
}
