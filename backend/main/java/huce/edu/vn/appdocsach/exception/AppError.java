package huce.edu.vn.appdocsach.exception;

import huce.edu.vn.appdocsach.constant.AppConst;

public enum AppError {
    UnexpectedError(600, "Lỗi không xác định"),

    CategoryNotFound(601, "Không thấy thể loại này"),

    // Auth
    Unauthorized(602, "Không thể xác thực"),
    UsernameNotFound(603, "Không tồn tại tài khoản"),
    UsernameExisted(604, "Tên tài khoản đã tồn tại"),
    UsernameOrPasswordIncorrect(605, "Sai tài khoản hoặc mật khẩu"),
    
    UsernameMissing(606, "Không được bỏ trống tên tài khoản"),
    PasswordMissing(607, "Không được bỏ trống mật khẩu"),
    EmailMissing(608, "Không được bỏ trống email"),

    CommentNotFound(609, "Không thấy bình luận này"),
    BookTitleExisted(610, "Sách này đã tồn tại"),

    FileContentInvalid(611, "Nội dung file không hợp lệ"),
    FileNameInvalid(612, "Tên file/loại file này không được hỗ trợ. Các loại file hỗ trợ là ".formatted(AppConst.VALID_IMAGE_EXTENSION)),
    
    BookNotFound(612, "Không thấy sách này"),
    DontHaveEditCommentPermission(613, "Bạn không có quyền chỉnh sửa bình luận này"),
    CategoryNameExisted(614, "Tên tiêu đề đã tồn tại"),
    
    ChapterNotFound(615,"Không thấy chương truyện này"),
    ChapterTitleCannotEncode(616, "Tiêu đề truyện không thể chuẩn hóa."),
    ChapterTitleExisted(617,"Tiêu đề chương truyện này đã tồn tại");

    private String value;
    private int errorCode;
    private AppError(int errorCode, String value) {
        this.value = value;
        this.errorCode = errorCode;
    }

    public String getValue() {
        return value;
    }

    public int getErrorCode() {
        return errorCode;
    }
}
