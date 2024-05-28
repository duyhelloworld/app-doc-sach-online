package huce.edu.vn.appdocsach.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import huce.edu.vn.appdocsach.entities.Book;
import huce.edu.vn.appdocsach.entities.Chapter;

@Repository
public interface ChapterRepo extends JpaRepository<Chapter, Integer> {
    boolean existsByTitle(String title);

    boolean existsByFolderName(String folderName);

    Page<Chapter> findByBook(Book Book, PageRequest pageable);
}
