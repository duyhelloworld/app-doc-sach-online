package huce.edu.vn.appdocsach.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import huce.edu.vn.appdocsach.entities.Book;
import huce.edu.vn.appdocsach.entities.Rating;
import huce.edu.vn.appdocsach.entities.User;

@Repository
public interface RatingRepo extends JpaRepository<Rating, Integer> {
    List<Rating> findByUser(User user);

    List<Rating> findByBook(Book book);

    // @Query
    Optional<Rating> findByUserAndBook(User user, Book book);
}
