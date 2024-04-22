package huce.edu.vn.appdocsach.constants;

import java.util.List;

import org.springframework.http.MediaType;

public class AppConst {
    
    private static final String resourcePath = System.getProperty("user.dir") + "/src/main/resources";
    
    public static final String SAMPLE_DATA_PATH = resourcePath + "/sample-data/";
    
    public static final int DEFAULT_PAGE_SIZE = 1;

    public static final int MAX_PAGE_SIZE = 50;

    public static final int MIN_PAGE_SIZE = 1;

    public static final int DEFAULT_PAGE_NUMBER = 1;

    public static final String DEFAULT_SORT_BY = "id";
    
    public static final MediaType PNG = MediaType.valueOf("image/png");
    
    public static final List<String> VALID_IMAGE_EXTENSION = List.of("png", "jpeg", "jpg");
    
    public static final MediaType JPG = MediaType.valueOf("image/jpg");
    
    public static final String AVATAR_DIR = resourcePath + "/avatars/";
    public static final String DEFAULT_AVATAR_FILENAME = "default-avatar";
    public static final String DEFAULT_AVATAR_FILENAME_WITH_EXTENSION = "default-avatar.png";
    public static final String DEFAULT_AVATAR_PATH = AVATAR_DIR + DEFAULT_AVATAR_FILENAME_WITH_EXTENSION;
    public static final String DEFAULT_AVATAR_URL = "http://localhost:8080/api/image/" + DEFAULT_AVATAR_FILENAME_WITH_EXTENSION;

    public static final String COVER_IMAGE_DIR = resourcePath + "/coverimages/";
    public static final String DEFAULT_COVER_IMAGE_FILENAME = "default-cover-image";
    public static final String DEFAULT_COVER_IMAGE_FILENAME_WITH_EXTENSION = "default-cover-image.png";
    public static final String DEFAULT_COVER_IMAGE_PATH = COVER_IMAGE_DIR + DEFAULT_COVER_IMAGE_FILENAME_WITH_EXTENSION;
    public static final String DEFAULT_COVER_IMAGE_URL = "http://localhost:8080/api/image/" + DEFAULT_COVER_IMAGE_FILENAME_WITH_EXTENSION;

    public static final String DELIMITER_IN_CHAPTER_FOLDER_NAME = "_";

    public static final String REGEX_CHECK_PASSWORD = ".{8,}";
    public static final String REGEX_CHECK_USERNAME = "^[A-Za-z0-9]+$";
    public static final String REGEX_CHECK_EMAIL = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?(?:\\.[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?)*$";
}
