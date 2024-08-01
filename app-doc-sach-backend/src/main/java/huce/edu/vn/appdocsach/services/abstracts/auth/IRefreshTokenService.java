package huce.edu.vn.appdocsach.services.abstracts.auth;

import java.time.LocalDateTime;

import huce.edu.vn.appdocsach.entities.RefreshToken;
import jakarta.servlet.http.HttpServletRequest;

public interface IRefreshTokenService {
    /**
     * Lấy giá trị refresh token từ request
     * @param request servlet request
     * @return giá trị refresh token
     */
    String getRefreshToken(HttpServletRequest request);

    /**
     * Sinh 1 refresh token
     * @return giá trị của refresh token
     */
    String generateRefreshToken();

    /**
     * Lấy thời gian expire refreshToken tính từ thời điểm hiện tại
     * @return {@code LocalDateTime}
     */
    LocalDateTime getNewExpiredFromNow();

    /**
     * Kiểm tra tính chính xác của refreshToken
     * @param refreshToken {@link RefreshToken}
     * @return true nếu token hợp lệ
     */
    boolean verifyExpiration(RefreshToken refreshToken);

    /** 
     * Dọn dẹp các token của user đã lâu không sử dụng
    */
    void cleanUp();
}
