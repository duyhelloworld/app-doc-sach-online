package huce.edu.vn.appdocsach.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import huce.edu.vn.appdocsach.entities.Chapter;
import huce.edu.vn.appdocsach.entities.Comment;
import java.util.List;

@Repository
public interface CommentRepo extends JpaRepository<Comment, Integer>{
    List<Comment> findByChapter(Chapter chapter);

    @Query("SELECT c FROM Comment c WHERE c.chapter.id = :chapterId")
    Page<Comment> findByChapterId(Integer chapterId, Pageable pageable);
}
