package huce.edu.vn.appdocsach.enums;


import org.springframework.http.HttpStatus;

import huce.edu.vn.appdocsach.constants.AppConst;
import huce.edu.vn.appdocsach.entities.Star;
import lombok.Getter;

public enum ResponseCode {

    // Out of Bussiness Code
    SUCCESS_CODE(1000, "Thành công", HttpStatus.OK),
    UNEXPECTED_ERROR(1001, "Lỗi không xác định", HttpStatus.INTERNAL_SERVER_ERROR),
    // Auth
    UNAUTHORIZED(1002, "Không thể xác thực", HttpStatus.UNAUTHORIZED),
    FORBIDDEN(1002, "Bạn không có quyền truy cập", HttpStatus.FORBIDDEN),
    USERNAME_NOT_FOUND(1003, "Không tồn tại tài khoản", HttpStatus.UNAUTHORIZED),
    USERNAME_EXISTED(1004, "Tên tài khoản đã tồn tại", HttpStatus.CONFLICT),
    USERNAME_OR_PASSWORD_INCORRECT(1005, "Sai tài khoản hoặc mật khẩu", HttpStatus.UNAUTHORIZED),
    OLD_PASSWORD_NOT_MATCH(1006, "Mật khẩu không chính xác", HttpStatus.UNAUTHORIZED),

    // @Valid User
    USERNAME_MISSING(1007, "Không được bỏ trống tên tài khoản", HttpStatus.BAD_REQUEST),
    USERNAME_INVALID(1008, "Tên tài khoản không hợp lệ. Tên tài khoản hợp lệ gồm các chữ cái (A-Z, a-z) và số (0-9)", HttpStatus.BAD_REQUEST),
    PASSWORD_MISSING(1009, "Không được bỏ trống mật khẩu", HttpStatus.BAD_REQUEST),
    EMAIL_MISSING(1010, "Không được bỏ trống email", HttpStatus.BAD_REQUEST),
    EMAIL_INVALID(1011, "Email không hợp lệ. Hãy sử dụng địa chỉ email hợp lệ để đăng kí", HttpStatus.BAD_REQUEST),
    PASSWORD_INVALID(1012, "Mật khẩu không hợp lệ. Hãy đặt mật khẩu dài hơn 8 kí tự", HttpStatus.BAD_REQUEST),
    
    // File working
    FILE_CONTENT_INVALID(1013, "Nội dung file không hợp lệ", HttpStatus.BAD_REQUEST),
    FILE_TYPE_INVALID(1014, "Tên file/loại file này không được hỗ trợ. Các loại file hỗ trợ là "
        .formatted(AppConst.VALID_IMAGE_EXTENSIONS), HttpStatus.EXPECTATION_FAILED),
    FILE_CONTENT_MISSING(1015, "Thiếu file gửi lên", HttpStatus.NOT_FOUND),

    // Bussiness Logic Code
    INVALID_KEY(1016, "Lỗi khi kiểm tra dữ liệu hợp lệ", HttpStatus.INTERNAL_SERVER_ERROR),
    
    CATEGORY_NOT_FOUND(2000, "Không thấy thể loại này", HttpStatus.NOT_FOUND),
    CATEGORY_NAME_EXISTED(2001, "Tên tiêu đề đã tồn tại", HttpStatus.CONFLICT),

    COMMENT_NOT_FOUND(2002, "Không thấy bình luận này", HttpStatus.NOT_FOUND),
    COMMENT_CONTENT_MISSING(2003, "Nội dung bình luận không được để trống", HttpStatus.BAD_REQUEST),
    DONT_HAVE_EDIT_COMMENT_PERMISSION(2004, "Bạn không có quyền chỉnh sửa bình luận này", HttpStatus.FORBIDDEN),
    DONT_HAVE_REMOVE_COMMENT_PERMISSION(2005, "Bạn không có quyền xóa bình luận này", HttpStatus.FORBIDDEN),
    
    BOOK_TITLE_EXISTED(2006, "Sách này đã tồn tại", HttpStatus.CONFLICT),
    BOOK_TITLE_MISSING(2007, "Title sách không được để trống", HttpStatus.BAD_REQUEST),
    BOOK_NOT_FOUND(2008, "Không thấy sách này", HttpStatus.NOT_FOUND),
    
    CHAPTER_TITLE_MISSING(2009, "Title chương không được để trống", HttpStatus.BAD_REQUEST),
    CHAPTER_NOT_FOUND(2010,"Không thấy chương truyện này", HttpStatus.NOT_FOUND),
    CHAPTER_TITLE_EXISTED(2011,"Tiêu đề chương truyện này đã tồn tại", HttpStatus.BAD_REQUEST),
    CHAPTER_TITLE_INVALID(2012, "Tiêu đề truyện không thể chuẩn hóa.", HttpStatus.BAD_REQUEST),
    STAR_OUT_OF_RANGE(2013, Star.ERROR_MESSAGE, HttpStatus.BAD_REQUEST);
    

    @Getter
    private String message;

    @Getter
    private int code;

    @Getter
    private HttpStatus statusCode;
    
    private ResponseCode(int code, String message, HttpStatus statusCode) {
        this.message = message;
        this.code = code;
        this.statusCode = statusCode;
    }
}
