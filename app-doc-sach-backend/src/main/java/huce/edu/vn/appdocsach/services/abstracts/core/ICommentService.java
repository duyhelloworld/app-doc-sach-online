package huce.edu.vn.appdocsach.services.abstracts.core;

import huce.edu.vn.appdocsach.dto.core.comment.CommentDto;
import huce.edu.vn.appdocsach.dto.core.comment.CreateCommentDto;
import huce.edu.vn.appdocsach.dto.core.comment.FindCommentDto;
import huce.edu.vn.appdocsach.dto.core.comment.UpdateCommentDto;
import huce.edu.vn.appdocsach.dto.paging.PaginationResponseDto;
import huce.edu.vn.appdocsach.entities.User;
import huce.edu.vn.appdocsach.services.abstracts.test.ITestableService;

public interface ICommentService extends ITestableService {
    
    /**
     * Lấy tất cả bình luận theo sách có phân trang
     * @param findCommentDto {@link FindCommentDto}
     * @return phân trang các bình luận {@link PaginationResponseDto} {@link CommentDto}
     */
    PaginationResponseDto<CommentDto> getComments(FindCommentDto findCommentDto);
    
    /**
     * Lấy thông tin bình luận theo mã
     * @param id mã bình luận
     * @return {@link CommentDto}
     */
    CommentDto getCommentById(Integer id);
    
    /**
     * Viết bình luận
     * @param user người viết
     * @param createCommentDto {@link CreateCommentDto}
     * @return {@link CommentDto}
     */
    CommentDto writeComment(User user, CreateCommentDto createCommentDto);

    /**
     * Cập nhật bình luận
     * @param user người cập nhật
     * @param updateCommentDto {@link UpdateCommentDto}
     * @return {@link CommentDto}
     */
    CommentDto updateComment(User user, UpdateCommentDto updateCommentDto);

    /**
     * Xóa bình luận theo mã
     * @param id mã bình luận
     * @param user người xóa
     */
    void removeComment(Integer id, User user);
}
