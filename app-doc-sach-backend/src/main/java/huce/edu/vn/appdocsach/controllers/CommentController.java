package huce.edu.vn.appdocsach.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import huce.edu.vn.appdocsach.annotations.auth.IsUser;
import huce.edu.vn.appdocsach.dto.core.comment.CommentDto;
import huce.edu.vn.appdocsach.dto.core.comment.CreateCommentDto;
import huce.edu.vn.appdocsach.dto.core.comment.FindCommentDto;
import huce.edu.vn.appdocsach.dto.core.comment.UpdateCommentDto;
import huce.edu.vn.appdocsach.paging.PagingResponse;
import huce.edu.vn.appdocsach.services.abstracts.core.ICommentService;
import huce.edu.vn.appdocsach.services.impl.auth.users.AuthUser;
import io.swagger.v3.oas.annotations.Operation;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
@RequestMapping("api/comment")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CommentController {

    ICommentService commentService;
    
    @Operation(summary = "Lấy comment cho 1 chapter")
    @GetMapping("all")
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
    public CommentDto writeNewComment(
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
