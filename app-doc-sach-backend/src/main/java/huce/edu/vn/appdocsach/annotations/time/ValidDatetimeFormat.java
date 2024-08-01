package huce.edu.vn.appdocsach.annotations.time;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.fasterxml.jackson.annotation.JsonFormat;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.PARAMETER})
@JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
public @interface ValidDatetimeFormat {
    
}
