package huce.edu.vn.appdocsach.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import huce.edu.vn.appdocsach.entities.Book;


@Repository
public interface BookRepo extends JpaRepository<Book, Integer> {

    boolean existsByTitle(String title);

    @Query("SELECT b FROM Book b WHERE CONCAT(b.title, ' ', b.author, ' ') LIKE %?1%")
    Page<Book> search(String keyword, Pageable pageable);

    @Query("SELECT b FROM Book b JOIN b.categories c WHERE c.id = :categoryId")
    Page<Book> findByCategoryId(@Param("categoryId") Integer categoryId, Pageable pageable);
}