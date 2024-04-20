package huce.edu.vn.appdocsach.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import huce.edu.vn.appdocsach.entities.Book;
import huce.edu.vn.appdocsach.entities.Category;


@Repository
public interface BookRepo extends JpaRepository<Book, Integer> {

    boolean existsByTitle(String title);

    @Query("SELECT b FROM Book b WHERE :category MEMBER OF b.categories")
    Page<Book> findByCategory(@Param("category") Category category, Pageable pageable);
}