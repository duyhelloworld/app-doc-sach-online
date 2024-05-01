package huce.edu.vn.appdocsach.services.abstracts.core;

import huce.edu.vn.appdocsach.dto.core.comment.CommentDto;
import huce.edu.vn.appdocsach.dto.core.comment.CreateCommentDto;
import huce.edu.vn.appdocsach.dto.core.comment.FindCommentDto;
import huce.edu.vn.appdocsach.dto.core.comment.UpdateCommentDto;
import huce.edu.vn.appdocsach.entities.User;
import huce.edu.vn.appdocsach.paging.PagingResponse;

public interface ICommentService {
    
    PagingResponse<CommentDto> getCommentsByChapter(FindCommentDto findCommentDto);

    boolean isEmpty();

    CommentDto getCommentById(Integer id);

    Integer writeComment(User user, CreateCommentDto createCommentDto);

    CommentDto updateComment(User user, UpdateCommentDto updateCommentDto);

    void removeComment(Integer id, User user);
}
