package huce.edu.vn.appdocsach.services.impl.core;

import java.util.Optional;

import org.springframework.stereotype.Service;

import huce.edu.vn.appdocsach.dto.core.rate.CreateRateDto;
import huce.edu.vn.appdocsach.entities.Book;
import huce.edu.vn.appdocsach.entities.Rating;
import huce.edu.vn.appdocsach.entities.User;
import huce.edu.vn.appdocsach.enums.ResponseCode;
import huce.edu.vn.appdocsach.exception.AppException;
import huce.edu.vn.appdocsach.repositories.database.BookRepo;
import huce.edu.vn.appdocsach.repositories.database.RatingRepo;
import huce.edu.vn.appdocsach.services.abstracts.core.IRateService;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@AllArgsConstructor
@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class RateService implements IRateService {

    RatingRepo ratingRepo;

    BookRepo bookRepo;

    @Override
    @Transactional
    public void rate(User user, CreateRateDto createRateDto, Integer bookId) {
        log.info("Start toggerRate by {} with input : ", user.getUsername(), createRateDto);
        Book book = bookRepo.findById(bookId).orElseThrow(
                () -> new AppException(ResponseCode.BOOK_NOT_FOUND));
        Optional<Rating> ratingOptional = ratingRepo.findByCreateByAndBook(user.getUsername(), book);
        if (ratingOptional.isEmpty()) {
            Rating rating = new Rating();
            rating.setBook(book);
            rating.setStar(createRateDto.star);
            ratingRepo.save(rating);
        }
    }
}
