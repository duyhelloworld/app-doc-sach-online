package huce.edu.vn.appdocsach.exception;

public class ValidationError {

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
