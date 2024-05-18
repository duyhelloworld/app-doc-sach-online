package huce.edu.vn.appdocsach.constants;

import java.util.List;

public class AppConst {
    
    public static final int DEFAULT_PAGE_SIZE = 1;

    public static final int MAX_PAGE_SIZE = 50;

    public static final int MIN_PAGE_SIZE = 1;

    public static final int DEFAULT_PAGE_NUMBER = 1;

    public static final String DEFAULT_SORT_BY = "id";
    
    public static final List<String> VALID_IMAGE_EXTENSIONS = List.of("png", "jpeg", "jpg");
    
    public static final String DELIMITER_IN_CHAPTER_FOLDER_NAME = "_";

    public static final Integer MAX_FILE_RETURN_IN_LOAD_A_CHAPTER = 100;

    public static final String REGEX_CHECK_PASSWORD = ".{8,}";
    public static final String REGEX_CHECK_USERNAME = "^[A-Za-z0-9]+$";
    public static final String REGEX_CHECK_EMAIL = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?(?:\\.[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?)*$";
}
