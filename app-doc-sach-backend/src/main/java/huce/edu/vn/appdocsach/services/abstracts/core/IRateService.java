package huce.edu.vn.appdocsach.services.abstracts.core;

import huce.edu.vn.appdocsach.dto.core.rate.CreateRateDto;
import huce.edu.vn.appdocsach.entities.User;

public interface IRateService {
    
    /**
     * Đánh giá truyện
     * @param user người thực hiện
     * @param createRateDto thông tin đánh giá {@link CreateRateDto}
     * @param bookId mã sách
     */
    void rate(User user, CreateRateDto createRateDto, Integer bookId);
}
