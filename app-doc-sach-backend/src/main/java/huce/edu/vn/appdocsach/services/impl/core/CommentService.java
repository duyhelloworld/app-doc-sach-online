package huce.edu.vn.appdocsach.services.impl.core;

import java.time.LocalDateTime;

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
import huce.edu.vn.appdocsach.services.abstracts.core.ICommentService;
import huce.edu.vn.appdocsach.utils.AppLogger;
import huce.edu.vn.appdocsach.utils.ConvertUtils;
import huce.edu.vn.appdocsach.paging.PagingHelper;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CommentService implements ICommentService {

    CommentRepo commentRepo;

    ChapterRepo chapterRepo;

    AppLogger<CommentService> logger;

    public CommentService(CommentRepo commentRepo, ChapterRepo chapterRepo) {
        this.commentRepo = commentRepo;
        this.chapterRepo = chapterRepo;
        this.logger = new AppLogger<>(CommentService.class);
    }

    @Override
    @Transactional
    public PagingResponse<CommentDto> getCommentsByChapter(FindCommentDto findCommentDto) {
        logger.onStart(Thread.currentThread(), findCommentDto);
        Page<Comment> comments = commentRepo.findByChapterId(findCommentDto.getChapterId(),
                PagingHelper.pageRequest(Comment.class, findCommentDto));
        PagingResponse<CommentDto> response = new PagingResponse<>();
        response.setTotalPage(comments.getTotalPages());
        response.setValues(comments.map(c -> ConvertUtils.convert(c)).getContent());
        return response;
    }

    @Override
    public boolean isEmpty() {
        return commentRepo.count() == 0;
    }

    @Override
    @Transactional
    public CommentDto getCommentById(Integer id) {
        logger.onStart(Thread.currentThread(), id);
        return commentRepo.findById(id).map(c -> ConvertUtils.convert(c))
            .orElseThrow(
                () -> new AppException(ResponseCode.COMMENT_NOT_FOUND));
    }

    @Override
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

    @Override
    public CommentDto updateComment(User user, UpdateCommentDto updateCommentDto) {
        logger.onStart(Thread.currentThread(), updateCommentDto, user.getUsername());
        Comment comment = commentRepo.findById(updateCommentDto.getId())
                .orElseThrow(() -> new AppException(ResponseCode.COMMENT_NOT_FOUND));
        if (!comment.getUser().equals(user)) {
            throw new AppException(ResponseCode.DONT_HAVE_EDIT_COMMENT_PERMISSION);
        }
        comment.setContent(updateCommentDto.getContent());
        comment.setEditedAt(LocalDateTime.now());
        return ConvertUtils.convert(commentRepo.save(comment));
    }

    @Override
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
}
