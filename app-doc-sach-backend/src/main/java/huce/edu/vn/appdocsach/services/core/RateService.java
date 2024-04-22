package huce.edu.vn.appdocsach.services.core;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import huce.edu.vn.appdocsach.dto.core.rate.CreateRateDto;
import huce.edu.vn.appdocsach.entities.Book;
import huce.edu.vn.appdocsach.entities.Rating;
import huce.edu.vn.appdocsach.entities.User;
import huce.edu.vn.appdocsach.enums.ResponseCode;
import huce.edu.vn.appdocsach.exception.AppException;
import huce.edu.vn.appdocsach.repositories.BookRepo;
import huce.edu.vn.appdocsach.repositories.RatingRepo;
import huce.edu.vn.appdocsach.utils.AppLogger;
import jakarta.transaction.Transactional;

@Service
public class RateService {

    @Autowired
    private RatingRepo ratingRepo;

    @Autowired
    private BookRepo bookRepo;

    private AppLogger<RateService> logger = new AppLogger<>(RateService.class);

    @Transactional
    public void toggerRate(User user, CreateRateDto createRateDto, Integer bookId) {
        logger.onStart(Thread.currentThread(), user.getUsername(), createRateDto);
        Book book = bookRepo.findById(bookId).orElseThrow(
                () -> new AppException(ResponseCode.BookNotFound));
        Optional<Rating> ratingOptional = ratingRepo.findByUserAndBook(user, book);
        if (ratingOptional.isEmpty()) {
            Rating rating = new Rating();
            rating.setBook(book);
            rating.setUser(user);
            rating.setStar(createRateDto.star);
            ratingRepo.save(rating);
        } else {
            ratingRepo.delete(ratingOptional.get());
        }
    }
}
