package huce.edu.vn.appdocsach.mapper;

import huce.edu.vn.appdocsach.dto.auth.AuthDto;
import huce.edu.vn.appdocsach.dto.auth.UserInfoDto;
import huce.edu.vn.appdocsach.dto.core.book.BookDetailDto;
import huce.edu.vn.appdocsach.dto.core.book.BookDto;
import huce.edu.vn.appdocsach.dto.core.category.CategoryDetailDto;
import huce.edu.vn.appdocsach.dto.core.category.CategoryDto;
import huce.edu.vn.appdocsach.dto.core.chapter.ChapterDto;
import huce.edu.vn.appdocsach.dto.core.comment.CommentDto;
import huce.edu.vn.appdocsach.entities.Book;
import huce.edu.vn.appdocsach.entities.Category;
import huce.edu.vn.appdocsach.entities.Chapter;
import huce.edu.vn.appdocsach.entities.Comment;
import huce.edu.vn.appdocsach.entities.Rating;
import huce.edu.vn.appdocsach.entities.User;
import huce.edu.vn.appdocsach.services.impl.auth.users.SupportedOAuth2User;

public class ModelMapper {
    
    public static BookDto convertSimple(Book book) {
        return BookDto.builder()
                .id(book.getId())
                .coverImage(book.getCoverImage())
                .title(book.getTitle())
                .build();
    }

    public static AuthDto convert(String accessToken, String refreshToken, User user) {
        return AuthDto.builder()
            .accessToken(accessToken)
            .refreshToken(refreshToken)
            .userInfo(convert(user))
            .build();
    }


    public static BookDetailDto convert(Book book) {
        return BookDetailDto.builder()
                .id(book.getId())
                .title(book.getTitle())
                .author(book.getAuthor())
                .coverImage(book.getCoverImage())
                .description(book.getDescription())
                .lastUpdatedAt(book.getLastUpdateAt())
                .categories(book.getCategories().stream().map(c -> c.getName()).toList())
                .chapters(book.getChapters().stream().map(ch -> convert(ch)).toList())
                .averageRate(book.getRatings().stream()
                        .mapToDouble(Rating::getStar)
                        .average()
                        .orElse(0.0))
                .viewCount(book.getViewCount())
                .build();
    }

    public static CommentDto convert(Comment comment) {
        return CommentDto.builder()
                .commentAt(comment.getCreateAt())
                .editedAt(comment.getLastUpdateAt())
                .content(comment.getContent())
                .username(comment.getCreateBy())
                .id(comment.getId())
                .build();
    }

    public static ChapterDto convert(Chapter chapter) {
        return ChapterDto.builder()
                .id(chapter.getId())
                .title(chapter.getTitle())
                .lastUpdatedAt(chapter.getLastUpdateAt())
                .build();
    }

    public static CategoryDetailDto convert(Category category) {
        return CategoryDetailDto.builder()
                .id(category.getId())
                .name(category.getName())
                .description(category.getDescription())
                .build();
    }

    public static CategoryDto convertSimple(Category category) {
        return CategoryDto.builder()
                .id(category.getId())
                .name(category.getName())
                .build();
    }

    public static UserInfoDto convert(SupportedOAuth2User authUser) {
        return UserInfoDto.builder()
            .avatar(authUser.getAvatar())
	        .email(authUser.getEmail())
            .fullname(authUser.getFullname())
            .username(authUser.getUsername())
            .build();
    }

    public static UserInfoDto convert(User user) {
        return UserInfoDto.builder()
            .avatar(user.getAvatar())
            .email(user.getEmail())
            .fullname(user.getFullname())
            .username(user.getUsername())
            .build();
    }

}
