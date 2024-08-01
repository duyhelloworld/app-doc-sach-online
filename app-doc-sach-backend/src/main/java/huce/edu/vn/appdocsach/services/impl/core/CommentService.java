package huce.edu.vn.appdocsach.services.impl.core;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import huce.edu.vn.appdocsach.dto.core.comment.CommentDto;
import huce.edu.vn.appdocsach.dto.core.comment.CreateCommentDto;
import huce.edu.vn.appdocsach.dto.core.comment.FindCommentDto;
import huce.edu.vn.appdocsach.dto.core.comment.UpdateCommentDto;
import huce.edu.vn.appdocsach.dto.paging.PagingHelper;
import huce.edu.vn.appdocsach.dto.paging.PaginationResponseDto;
import huce.edu.vn.appdocsach.entities.Chapter;
import huce.edu.vn.appdocsach.entities.Comment;
import huce.edu.vn.appdocsach.entities.User;
import huce.edu.vn.appdocsach.enums.ResponseCode;
import huce.edu.vn.appdocsach.exception.AppException;
import huce.edu.vn.appdocsach.mapper.ModelMapper;
import huce.edu.vn.appdocsach.repositories.database.ChapterRepo;
import huce.edu.vn.appdocsach.repositories.database.CommentRepo;
import huce.edu.vn.appdocsach.services.abstracts.core.ICommentService;
import huce.edu.vn.appdocsach.utils.JsonUtils;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@AllArgsConstructor
@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CommentService implements ICommentService {

    CommentRepo commentRepo;

    ChapterRepo chapterRepo;

    @Override
    public PaginationResponseDto<CommentDto> getComments(FindCommentDto findCommentDto) {
        log.info("Start getComments with input : ", JsonUtils.json(findCommentDto));
        Page<Comment> comments = commentRepo.findByChapterId(findCommentDto.getChapterId(),
                PagingHelper.pageRequest(Comment.class, findCommentDto));
        return
            PaginationResponseDto.<CommentDto>builder()
            .values(comments.map(c -> ModelMapper.convert(c)).toList())
            .totalPage(comments.getTotalPages())
            .build();
    }

    @Override
    public boolean isEmpty() {
        return commentRepo.count() == 0;
    }

    @Override
    @Transactional
    public CommentDto getCommentById(Integer id) {
        log.info("Start getCommentById with input : {}", id);
        return commentRepo.findById(id).map(c -> ModelMapper.convert(c))
            .orElseThrow(
                () -> new AppException(ResponseCode.COMMENT_NOT_FOUND));
    }

    @Override
    @Transactional
    public CommentDto writeComment(User user, CreateCommentDto createCommentDto) {
        log.info("Start writeComment by {} with input : {}", user.getUsername(), JsonUtils.json(createCommentDto));
        int chapterId = createCommentDto.getChapterId();
        Chapter chapter = chapterRepo.findById(chapterId)
                .orElseThrow(() -> new AppException(ResponseCode.CHAPTER_NOT_FOUND));
        Comment comment = new Comment();
        comment.setContent(createCommentDto.getContent());
        comment.setChapter(chapter);
        commentRepo.save(comment);
        return ModelMapper.convert(comment);
    }

    @Override
    public CommentDto updateComment(User user, UpdateCommentDto updateCommentDto) {
        log.info("Start updateComment by {} with input : {}", user.getUsername(), JsonUtils.json(updateCommentDto));
        Comment comment = commentRepo.findById(updateCommentDto.getId())
                .orElseThrow(() -> new AppException(ResponseCode.COMMENT_NOT_FOUND));
        if (!comment.getCreateBy().equals(user.getUsername())) {
            throw new AppException(ResponseCode.DONT_HAVE_EDIT_COMMENT_PERMISSION);
        }
        comment.setContent(updateCommentDto.getContent());
        return ModelMapper.convert(commentRepo.save(comment));
    }

    @Override
    @Transactional
    public void removeComment(Integer id, User user) {
        log.info("Start removeComment by {} with input : {}", user.getUsername(), id);
        Comment comment = commentRepo.findById(id)
                .orElseThrow(() -> new AppException(ResponseCode.COMMENT_NOT_FOUND));
        if (!comment.getCreateBy().equals(user.getUsername())) {
            throw new AppException(ResponseCode.DONT_HAVE_REMOVE_COMMENT_PERMISSION);
        }  
        commentRepo.delete(comment);
    }
}
