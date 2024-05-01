package huce.edu.vn.appdocsach.services.abstracts.core;

import huce.edu.vn.appdocsach.dto.core.rate.CreateRateDto;
import huce.edu.vn.appdocsach.entities.User;

public interface IRateService {
    
    void toggerRate(User user, CreateRateDto createRateDto, Integer bookId);
}
