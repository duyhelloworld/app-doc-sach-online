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
import huce.edu.vn.appdocsach.dto.paging.PaginationResponseDto;
import huce.edu.vn.appdocsach.services.abstracts.core.ICommentService;
import huce.edu.vn.appdocsach.services.impl.auth.users.AuthenticatedUser;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
@RequestMapping("api/comment")
@RequiredArgsConstructor
public class CommentController {

    private final ICommentService commentService;

    @Operation(summary = "Lấy các bình luận cho 1 chapter")
    @GetMapping("all")
    public PaginationResponseDto<CommentDto> getAllByChapter(FindCommentDto findCommentDto) {
        return commentService.getComments(findCommentDto);
    }

    @Operation(summary = "Lấy comment theo id")
    @GetMapping("find/{id}")
    public CommentDto getCommmentById(@PathVariable Integer id) {
        return commentService.getCommentById(id);
    }

    @Operation(summary = "Viết 1 comment")
    @IsUser
    @PostMapping("add")
    public CommentDto writeNewComment(
        @AuthenticationPrincipal AuthenticatedUser authUser,   
        @RequestBody CreateCommentDto createCommentDto) {
        return commentService.writeComment(authUser.getUser(),
            createCommentDto);
    }

    @Operation(summary = "Sửa 1 comment")
    @IsUser
    @PutMapping("edit")
    public CommentDto updateComment(
    @AuthenticationPrincipal AuthenticatedUser authUser,    
    @RequestBody UpdateCommentDto updateCommentDto) {
        return commentService.updateComment(authUser.getUser(), updateCommentDto);
    }

    @Operation(summary = "Xóa 1 comment")
    @IsUser
    @DeleteMapping("{id}")
    public void deleteComment(
            @AuthenticationPrincipal AuthenticatedUser authUser,
            @PathVariable Integer id) {
        commentService.removeComment(id, authUser.getUser());
    }

    
}
