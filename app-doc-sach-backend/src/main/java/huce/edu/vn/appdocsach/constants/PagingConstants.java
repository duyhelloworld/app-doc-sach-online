package huce.edu.vn.appdocsach.constants;

import java.util.List;

public class PagingConstants {
    
    public static final Integer DEFAULT_PAGE_SIZE = 1;

    public static final int MAX_PAGE_SIZE = 50;

    public static final int MIN_PAGE_SIZE = 1;

    public static final Integer DEFAULT_PAGE_NUMBER = 1;

    public static final String DEFAULT_SORT_BY = "id";
    
    public static final List<String> VALID_IMAGE_EXTENSIONS = List.of("png", "jpeg", "jpg");
    
    public static final String DELIMITER_IN_CHAPTER_FOLDER_NAME = "_";

    public static final Integer MAX_FILE_RETURN_IN_LOAD_A_CHAPTER = 100;


}
