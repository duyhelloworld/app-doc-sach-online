package huce.edu.vn.appdocsach.dto.core.rate;

import org.hibernate.validator.constraints.Range;

import huce.edu.vn.appdocsach.entities.Star;
import jakarta.validation.Valid;
import lombok.ToString;

@ToString
@Valid
public class CreateRateDto {
    
    @Range(min = Star.ONE, max = Star.FIVE, message = "STAR_OUT_OF_RANGE")
    public Short star;
}
