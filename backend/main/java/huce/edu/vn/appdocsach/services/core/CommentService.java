package huce.edu.vn.appdocsach.services.core;

import java.time.LocalDateTime;
import java.util.List;

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
import huce.edu.vn.appdocsach.exception.AppError;
import huce.edu.vn.appdocsach.exception.AppException;
import huce.edu.vn.appdocsach.paging.BasePagingResponse;
import huce.edu.vn.appdocsach.repositories.ChapterRepo;
import huce.edu.vn.appdocsach.repositories.CommentRepo;
import huce.edu.vn.appdocsach.services.BaseService;
import huce.edu.vn.appdocsach.utils.AppLogger;
import huce.edu.vn.appdocsach.utils.PagingHelper;
import jakarta.transaction.Transactional;

@Service
public class CommentService extends BaseService<CommentDto> {

    @Autowired
    private CommentRepo commentRepo;

    @Autowired
    private ChapterRepo chapterRepo;

    private AppLogger<CommentService> logger = new AppLogger<>(CommentService.class);

    @Transactional
    public BasePagingResponse<CommentDto> getCommentsByChapter(FindCommentDto findCommentDto) {
        logger.onStart(Thread.currentThread(), findCommentDto);
        Page<Comment> comments = commentRepo.findByChapterId(findCommentDto.chapterId,
                PagingHelper.pageRequest(findCommentDto));
        pagingResponse.totalPage = comments.getTotalPages();
        pagingResponse.values = comments.map(c -> convert(c)).getContent();
        return pagingResponse;
    }

    protected BasePagingResponse<CommentDto> getCommentDto(List<Comment> comments) {
        pagingResponse.totalPage = comments.size();
        pagingResponse.values = comments.stream().map(c -> convert(c)).toList();
        return pagingResponse;
    }

    public boolean isEmpty() {
        return commentRepo.count() == 0;
    }

    @Transactional
    public CommentDto getCommentById(Integer id) {
        logger.onStart(Thread.currentThread(), id);
        return commentRepo.findById(id).map(c -> convert(c)).orElseThrow(
                () -> new AppException(AppError.CommentNotFound));
    }

    @Transactional
    public Integer writeComment(User user, CreateCommentDto createCommentDto) {
        logger.onStart(Thread.currentThread(), user.getUsername(), createCommentDto);
        int chapterId = createCommentDto.getChapterId();
        Chapter chapter = chapterRepo.findById(chapterId)
                .orElseThrow(() -> new AppException(AppError.ChapterNotFound));
        Comment comment = new Comment();
        comment.setUser(user);
        comment.setContent(createCommentDto.getContent());
        comment.setCreatedAt(LocalDateTime.now());
        comment.setChapter(chapter);
        commentRepo.save(comment);
        return comment.getId();
    }

    public CommentDto updateComment(User user, UpdateCommentDto updateCommentDto) {
        Comment comment = commentRepo.findById(updateCommentDto.getId())
                .orElseThrow(() -> new AppException(AppError.CommentNotFound));
                logger.info(user);
                logger.info(comment.getUser());
        if (!comment.getUser().equals(user)) {
            throw new AppException(AppError.DontHaveEditCommentPermission);
        }
        comment.setContent(updateCommentDto.getNewContent());
        comment.setEditedAt(LocalDateTime.now());
        return convert(commentRepo.save(comment));
    }

    public void removeComment() {
        
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
