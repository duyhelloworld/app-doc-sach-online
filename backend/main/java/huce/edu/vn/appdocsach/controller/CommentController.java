package huce.edu.vn.appdocsach.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import huce.edu.vn.appdocsach.annotation.auth.IsAdmin;
import huce.edu.vn.appdocsach.annotation.auth.IsUser;
import huce.edu.vn.appdocsach.dto.core.comment.CommentDto;
import huce.edu.vn.appdocsach.dto.core.comment.CreateCommentDto;
import huce.edu.vn.appdocsach.dto.core.comment.FindCommentDto;
import huce.edu.vn.appdocsach.dto.core.comment.UpdateCommentDto;
import huce.edu.vn.appdocsach.paging.BasePagingResponse;
import huce.edu.vn.appdocsach.services.auth.users.AuthUser;
import huce.edu.vn.appdocsach.services.core.CommentService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
@RequestMapping("api/comment")
public class CommentController {

    @Autowired
    private CommentService commentService;
    
    @IsAdmin
    @GetMapping
    public BasePagingResponse<CommentDto> getAllByChapter(FindCommentDto findCommentDto) {
        return commentService.getCommentsByChapter(findCommentDto);
    }

    @GetMapping("find")
    public CommentDto getCommmentById(@RequestParam Integer id) {
        return commentService.getCommentById(id);
    }

    @IsUser
    @PostMapping
    public Integer writeNewComment(
        @AuthenticationPrincipal AuthUser authUser,   
        @RequestBody CreateCommentDto createCommentDto) {
        return commentService.writeComment(authUser.getUser(), createCommentDto);
    }

    @IsUser
    @PutMapping
    public CommentDto updateComment(
    @AuthenticationPrincipal AuthUser authUser,    
    @RequestBody UpdateCommentDto updateCommentDto) {
        return commentService.updateComment(authUser.getUser(), updateCommentDto);
    }
}
