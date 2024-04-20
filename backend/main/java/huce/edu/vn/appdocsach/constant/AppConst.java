package huce.edu.vn.appdocsach.constant;

import java.util.List;

import org.springframework.http.MediaType;

public class AppConst {
    
    private static final String resourcePath = System.getProperty("user.dir") + "/src/main/resources";
    
    public static final String SAMPLE_DATA_PATH = resourcePath + "/sample-data/";
    public static final int PAGE_SIZE = 2;
    
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
}
