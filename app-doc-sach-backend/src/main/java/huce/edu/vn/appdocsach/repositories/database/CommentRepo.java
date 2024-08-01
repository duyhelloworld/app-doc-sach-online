package huce.edu.vn.appdocsach.repositories.database;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import huce.edu.vn.appdocsach.entities.Chapter;
import huce.edu.vn.appdocsach.entities.Comment;
import huce.edu.vn.appdocsach.repositories.base.SoftDeleteRepository;

import java.util.List;

@Repository
public interface CommentRepo extends SoftDeleteRepository<Comment, Integer> {
    
    List<Comment> findByChapter(Chapter chapter);

    @Query("SELECT c FROM Comment c WHERE c.chapter.id = :chapterId")
    Page<Comment> findByChapterId(@Param("chapterId") Integer chapterId, Pageable pageable);
}
