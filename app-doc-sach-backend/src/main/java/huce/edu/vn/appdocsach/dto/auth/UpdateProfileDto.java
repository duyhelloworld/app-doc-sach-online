package huce.edu.vn.appdocsach.dto.auth;

import huce.edu.vn.appdocsach.annotations.valid.ValidEmail;
import jakarta.validation.Valid;
import lombok.Data;

@Data
@Valid
public class UpdateProfileDto {

    private String fullname;
    
    @ValidEmail(nullable = true)
    private String email;
}
