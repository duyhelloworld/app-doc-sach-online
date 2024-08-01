package huce.edu.vn.appdocsach.services.abstracts.auth;

import huce.edu.vn.appdocsach.entities.User;
import huce.edu.vn.appdocsach.services.impl.auth.users.ClaimValues;
import jakarta.servlet.http.HttpServletRequest;

public interface IAccessTokenService {
    
    /**
     * Đọc access token từ request
     * @param request request
     * @return accesstoken nếu đọc được, không thì null
     */
    String getAccessToken(HttpServletRequest request);

    /**
     * Tạo access token
     * @param user lấy các thuộc tính để tạo chữ kí số của user
     * @return access token
     */
    String buildAccessToken(User user);

    /**
     * Kiểm tra tính hợp lệ của access token
     * @param accessToken
     * @return true nếu hợp lệ
     */
    boolean isValidAccessToken(String accessToken);

    /**
     * Đọc các trường đã kí từ access token
     * @param token
     * @return {@link ClaimValues}
     */
    ClaimValues extractClaimValues(String token);
}
