package huce.edu.vn.appdocsach.constants;

import java.net.Inet4Address;
import java.net.UnknownHostException;
import java.util.List;

public class AppConst {
    
    private static final String resourcePath = System.getProperty("user.dir") + "/src/main/resources";
    
    private static String localIp;
    public static final String getLocalIp() {
        if (localIp == null) {
            try {
                localIp = Inet4Address.getLocalHost().getHostAddress();
            } catch (UnknownHostException e) {}
        }
        return localIp;
    } 
        
    public static final String SAMPLE_DATA_PATH = resourcePath + "/sample-data/";
    
    public static final int DEFAULT_PAGE_SIZE = 1;

    public static final int MAX_PAGE_SIZE = 50;

    public static final int MIN_PAGE_SIZE = 1;

    public static final int DEFAULT_PAGE_NUMBER = 1;

    public static final String DEFAULT_SORT_BY = "id";
    
    public static final List<String> VALID_IMAGE_EXTENSIONS = List.of("png", "jpeg", "jpg");
    
    private static final String imageControllerPath = getLocalIp() + "/api/image/";
    
    public static final String AVATAR_DIR = resourcePath + "/avatars/";

    public static final String DEFAULT_AVATAR_FILENAME = "default-avatar.png";
    public static final String DEFAULT_AVATAR_FILE_DIR = AVATAR_DIR + DEFAULT_AVATAR_FILENAME;
    public static final String DEFAULT_AVATAR_URL = imageControllerPath + DEFAULT_AVATAR_FILENAME;

    public static final String COVER_IMAGE_DIR = resourcePath + "/coverimages/";

    public static final String DEFAULT_COVER_IMAGE_FILENAME = "default-cover-image.png";
    public static final String DEFAULT_COVER_IMAGE_FILE_DIR = COVER_IMAGE_DIR + DEFAULT_COVER_IMAGE_FILENAME;
    public static final String DEFAULT_COVER_IMAGE_URL = imageControllerPath + DEFAULT_COVER_IMAGE_FILENAME;

    public static final String DELIMITER_IN_CHAPTER_FOLDER_NAME = "_";

    public static final String REGEX_CHECK_PASSWORD = ".{8,}";
    public static final String REGEX_CHECK_USERNAME = "^[A-Za-z0-9]+$";
    public static final String REGEX_CHECK_EMAIL = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?(?:\\.[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?)*$";
}
