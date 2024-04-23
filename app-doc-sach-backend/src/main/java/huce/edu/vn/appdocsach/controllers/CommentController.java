package huce.edu.vn.appdocsach.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import huce.edu.vn.appdocsach.annotations.auth.IsAdmin;
import huce.edu.vn.appdocsach.annotations.auth.IsUser;
import huce.edu.vn.appdocsach.dto.core.comment.CommentDto;
import huce.edu.vn.appdocsach.dto.core.comment.CreateCommentDto;
import huce.edu.vn.appdocsach.dto.core.comment.FindCommentDto;
import huce.edu.vn.appdocsach.dto.core.comment.UpdateCommentDto;
import huce.edu.vn.appdocsach.paging.PagingResponse;
import huce.edu.vn.appdocsach.services.auth.users.AuthUser;
import huce.edu.vn.appdocsach.services.core.CommentService;
import io.swagger.v3.oas.annotations.Operation;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
@RequestMapping("api/comment")
public class CommentController {

    @Autowired
    private CommentService commentService;
    
    @Operation(summary = "Lấy comment cho 1 chapter")
    @IsAdmin
    @GetMapping
    public PagingResponse<CommentDto> getAllByChapter(FindCommentDto findCommentDto) {
        return commentService.getCommentsByChapter(findCommentDto);
    }

    @Operation(summary = "Lấy comment theo id")
    @GetMapping("find/{id}")
    public CommentDto getCommmentById(@PathVariable Integer id) {
        return commentService.getCommentById(id);
    }

    @Operation(summary = "Viết 1 comment")
    @IsUser
    @PostMapping
    public Integer writeNewComment(
        @AuthenticationPrincipal AuthUser authUser,   
        @RequestBody CreateCommentDto createCommentDto) {
        return commentService.writeComment(authUser.getUser(), createCommentDto);
    }

    @Operation(summary = "Sửa 1 comment")
    @IsUser
    @PutMapping
    public CommentDto updateComment(
    @AuthenticationPrincipal AuthUser authUser,    
    @RequestBody UpdateCommentDto updateCommentDto) {
        return commentService.updateComment(authUser.getUser(), updateCommentDto);
    }

    @Operation(summary = "Xóa 1 comment")
    @IsUser
    @DeleteMapping("{id}")
    public void deleteComment(
            @AuthenticationPrincipal AuthUser authUser,
            @PathVariable Integer id) {
        commentService.removeComment(id, authUser.getUser());
    }
}
