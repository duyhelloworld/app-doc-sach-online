package huce.edu.vn.appdocsach.services.core;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import huce.edu.vn.appdocsach.dto.core.comment.CommentDto;
import huce.edu.vn.appdocsach.dto.core.comment.CreateCommentDto;
import huce.edu.vn.appdocsach.dto.core.comment.FindCommentDto;
import huce.edu.vn.appdocsach.dto.core.comment.UpdateCommentDto;
import huce.edu.vn.appdocsach.entities.Chapter;
import huce.edu.vn.appdocsach.entities.Comment;
import huce.edu.vn.appdocsach.entities.User;
import huce.edu.vn.appdocsach.enums.ResponseCode;
import huce.edu.vn.appdocsach.exception.AppException;
import huce.edu.vn.appdocsach.paging.PagingResponse;
import huce.edu.vn.appdocsach.repositories.ChapterRepo;
import huce.edu.vn.appdocsach.repositories.CommentRepo;
import huce.edu.vn.appdocsach.utils.AppLogger;
import huce.edu.vn.appdocsach.paging.PagingHelper;
import jakarta.transaction.Transactional;

@Service
public class CommentService {

    @Autowired
    private CommentRepo commentRepo;

    @Autowired
    private ChapterRepo chapterRepo;

    private AppLogger<CommentService> logger = new AppLogger<>(CommentService.class);

    @Transactional
    public PagingResponse<CommentDto> getCommentsByChapter(FindCommentDto findCommentDto) {
        logger.onStart(Thread.currentThread(), findCommentDto);
        Page<Comment> comments = commentRepo.findByChapterId(findCommentDto.chapterId,
                PagingHelper.pageRequest(Comment.class, findCommentDto));
        PagingResponse<CommentDto> response = new PagingResponse<>();
        response.setTotalPage(comments.getTotalPages());
        response.setValues(comments.map(c -> convert(c)).getContent());
        return response;
    }

    public boolean isEmpty() {
        return commentRepo.count() == 0;
    }

    @Transactional
    public CommentDto getCommentById(Integer id) {
        logger.onStart(Thread.currentThread(), id);
        return commentRepo.findById(id).map(c -> convert(c)).orElseThrow(
                () -> new AppException(ResponseCode.COMMENT_NOT_FOUND));
    }

    @Transactional
    public Integer writeComment(User user, CreateCommentDto createCommentDto) {
        logger.onStart(Thread.currentThread(), user.getUsername(), createCommentDto);
        int chapterId = createCommentDto.getChapterId();
        Chapter chapter = chapterRepo.findById(chapterId)
                .orElseThrow(() -> new AppException(ResponseCode.CHAPTER_NOT_FOUND));
        Comment comment = new Comment();
        comment.setUser(user);
        comment.setContent(createCommentDto.getContent());
        comment.setCreatedAt(LocalDateTime.now());
        comment.setChapter(chapter);
        commentRepo.save(comment);
        return comment.getId();
    }

    public CommentDto updateComment(User user, UpdateCommentDto updateCommentDto) {
        logger.onStart(Thread.currentThread(), updateCommentDto, user.getUsername());
        Comment comment = commentRepo.findById(updateCommentDto.getId())
                .orElseThrow(() -> new AppException(ResponseCode.COMMENT_NOT_FOUND));
                logger.info(user);
                logger.info(comment.getUser());
        if (!comment.getUser().equals(user)) {
            throw new AppException(ResponseCode.DONT_HAVE_EDIT_COMMENT_PERMISSION);
        }
        comment.setContent(updateCommentDto.getContent());
        comment.setEditedAt(LocalDateTime.now());
        return convert(commentRepo.save(comment));
    }

    @Transactional
    public void removeComment(Integer id, User user) {
        logger.onStart(Thread.currentThread(), id, user.getUsername());
        Comment comment = commentRepo.findById(id)
                .orElseThrow(() -> new AppException(ResponseCode.COMMENT_NOT_FOUND));
        if (!comment.getUser().equals(user)) {
            throw new AppException(ResponseCode.DONT_HAVE_REMOVE_COMMENT_PERMISSION);
        }  
        commentRepo.delete(comment);
    }

    private CommentDto convert(Comment comment) {
        return CommentDto.builder()
                .commentAt(comment.getCreatedAt())
                .content(comment.getContent())
                .id(comment.getId())
                .username(comment.getUser().getUsername())
                .isEdited(comment.getEditedAt() != null)
                .build();
    }
}
