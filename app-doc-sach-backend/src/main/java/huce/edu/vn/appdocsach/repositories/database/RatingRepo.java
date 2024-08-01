package huce.edu.vn.appdocsach.repositories.database;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import huce.edu.vn.appdocsach.entities.Book;
import huce.edu.vn.appdocsach.entities.Rating;
import huce.edu.vn.appdocsach.repositories.base.SoftDeleteRepository;

@Repository
public interface RatingRepo extends SoftDeleteRepository<Rating, Integer> {
    
    List<Rating> findByCreateBy(String createBy);

    List<Rating> findByBook(Book book);

    // @Query
    Optional<Rating> findByCreateByAndBook(String createBy, Book book);
}
