package huce.edu.vn.appdocsach.enums;


import org.springframework.http.HttpStatus;

import huce.edu.vn.appdocsach.constants.AppConst;
import lombok.Getter;

public enum ResponseCode {
    SuccessCode(1000, "Thành công", HttpStatus.OK),
    UnexpectedError(1001, "Lỗi không xác định", HttpStatus.INTERNAL_SERVER_ERROR),
    // Auth
    Unauthorized(1002, "Không thể xác thực", HttpStatus.UNAUTHORIZED),
    Forbidden(1002, "Bạn không có quyền truy cập", HttpStatus.FORBIDDEN),
    
    UsernameNotFound(1003, "Không tồn tại tài khoản", HttpStatus.UNAUTHORIZED),
    UsernameExisted(1004, "Tên tài khoản đã tồn tại", HttpStatus.CONFLICT),
    UsernameOrPasswordIncorrect(1005, "Sai tài khoản hoặc mật khẩu", HttpStatus.UNAUTHORIZED),
    // Valid user
    UsernameMissing(1006, "Không được bỏ trống tên tài khoản", HttpStatus.BAD_REQUEST),
    PasswordMissing(1007, "Không được bỏ trống mật khẩu", HttpStatus.BAD_REQUEST),
    EmailMissing(1008, "Không được bỏ trống email", HttpStatus.BAD_REQUEST),
    
    FileContentInvalid(1009, "Nội dung file không hợp lệ", HttpStatus.BAD_REQUEST),
    FileNameInvalid(1010, "Tên file/loại file này không được hỗ trợ. Các loại file hỗ trợ là "
        .formatted(AppConst.VALID_IMAGE_EXTENSION), HttpStatus.EXPECTATION_FAILED),

    InvalidKey(1011, "Lỗi khi kiểm tra dữ liệu hợp lệ", HttpStatus.INTERNAL_SERVER_ERROR),

    
    CategoryNotFound(2000, "Không thấy thể loại này", HttpStatus.NOT_FOUND),
    CommentNotFound(2001, "Không thấy bình luận này", HttpStatus.NOT_FOUND),
    BookTitleExisted(2002, "Sách này đã tồn tại", HttpStatus.CONFLICT),
    
    BookNotFound(2003, "Không thấy sách này", HttpStatus.NOT_FOUND),
    DontHaveEditCommentPermission(2004, "Bạn không có quyền chỉnh sửa bình luận này", HttpStatus.FORBIDDEN),
    CategoryNameExisted(2005, "Tên tiêu đề đã tồn tại", HttpStatus.CONFLICT),
    
    ChapterNotFound(2006,"Không thấy chương truyện này", HttpStatus.NOT_FOUND),
    ChapterTitleCannotEncode(2007, "Tiêu đề truyện không thể chuẩn hóa.", HttpStatus.BAD_REQUEST),
    ChapterTitleExisted(2008,"Tiêu đề chương truyện này đã tồn tại", HttpStatus.BAD_REQUEST);

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
