package huce.edu.vn.appdocsach.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import huce.edu.vn.appdocsach.entities.Chapter;

@Repository
public interface ChapterRepo extends JpaRepository<Chapter, Integer> {
    boolean existsByTitle(String title);

    boolean existsByFolderName(String folderName);

    @Query("SELECT c FROM Chapter c WHERE c.book.id = :bookId")
    Page<Chapter> findByBookId(@Param("bookId") Integer bookId, Pageable pageable);
}
