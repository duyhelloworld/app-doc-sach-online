package huce.edu.vn.appdocsach.constants;

import java.util.List;

import org.springframework.http.MediaType;

public class AppConst {
    
    private static final String resourcePath = System.getProperty("user.dir") + "/src/main/resources";
    
    public static final String SAMPLE_DATA_PATH = resourcePath + "/sample-data/";
    
    public static final int DEFAULT_PAGE_SIZE = 5;

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

    public static final String PASSWORD_INVALID = "Mật khẩu không hợp lệ. Hãy đặt mật khẩu dài hơn 8 kí tự";

    public static final String EMAIL_INVALID = "Email không hợp lệ. Hãy sử dụng địa chỉ email hợp lệ để đăng kí";

    public static final String USERNAME_INVALID = "Tên tài khoản không hợp lệ. Tên tài khoản hợp lệ gồm các chữ cái (A-Z, a-z) và số (0-9)";

    public static final String USERNAME_MISSING = "Không được bỏ trống tên tài khoản";
    public static final String PASSWORD_MISSING = "Không được bỏ trống mật khẩu";
    public static final String EMAIL_MISSING = "Không được bỏ trống email";

    public static final String BOOK_TITLE_MISSING = "Title không được để trống";
    public static final String CHAPTER_TITLE_MISSING = "Title không được để trống";

    public static final String TIME_NOT_BEFORE_NOW = "Ngày giờ cần nhập không được lớn hơn hiện tại";
    public static final String TIME_NOT_AFTER_NOW = "Ngày giờ cần nhập không được nhỏ hơn hiện tại";

    public static final String COMMENT_CONTENT_MISSING = "Không tìm thấy nội dung bình luận";
}
