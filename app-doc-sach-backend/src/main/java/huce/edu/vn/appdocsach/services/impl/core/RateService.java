package huce.edu.vn.appdocsach.services.impl.core;

import java.util.Optional;

import org.springframework.stereotype.Service;

import huce.edu.vn.appdocsach.dto.core.rate.CreateRateDto;
import huce.edu.vn.appdocsach.entities.Book;
import huce.edu.vn.appdocsach.entities.Rating;
import huce.edu.vn.appdocsach.entities.User;
import huce.edu.vn.appdocsach.enums.ResponseCode;
import huce.edu.vn.appdocsach.exception.AppException;
import huce.edu.vn.appdocsach.repositories.BookRepo;
import huce.edu.vn.appdocsach.repositories.RatingRepo;
import huce.edu.vn.appdocsach.services.abstracts.core.IRateService;
import huce.edu.vn.appdocsach.utils.AppLogger;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class RateService implements IRateService {

    RatingRepo ratingRepo;

    BookRepo bookRepo;

    AppLogger<RateService> logger;

    public RateService(RatingRepo ratingRepo, BookRepo bookRepo) {
        this.ratingRepo = ratingRepo;
        this.bookRepo = bookRepo;
        this.logger = new AppLogger<>(RateService.class);
    }
    
    @Override
    @Transactional
    public void toggerRate(User user, CreateRateDto createRateDto, Integer bookId) {
        logger.onStart(Thread.currentThread(), user.getUsername(), createRateDto);
        Book book = bookRepo.findById(bookId).orElseThrow(
                () -> new AppException(ResponseCode.BOOK_NOT_FOUND));
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
