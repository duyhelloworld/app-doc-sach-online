package huce.edu.vn.appdocsach.utils;

import huce.edu.vn.appdocsach.dto.core.book.BookDto;
import huce.edu.vn.appdocsach.dto.core.book.SimpleBookDto;
import huce.edu.vn.appdocsach.dto.core.category.CategoryDto;
import huce.edu.vn.appdocsach.dto.core.category.SimpleCategoryDto;
import huce.edu.vn.appdocsach.dto.core.chapter.SimpleChapterDto;
import huce.edu.vn.appdocsach.dto.core.comment.CommentDto;
import huce.edu.vn.appdocsach.entities.Book;
import huce.edu.vn.appdocsach.entities.Category;
import huce.edu.vn.appdocsach.entities.Chapter;
import huce.edu.vn.appdocsach.entities.Comment;
import huce.edu.vn.appdocsach.entities.Rating;

public class ConvertUtils {
    
    public static SimpleBookDto convertSimple(Book book) {
        return SimpleBookDto.builder()
                .id(book.getId())
                .coverImage(book.getCoverImage())
                .title(book.getTitle())
                .author(book.getAuthor())
                .lastUpdatedAt(book.getUpdatedAt() == null ? book.getCreatedAt() : book.getUpdatedAt())
                .build();
    }

    public static BookDto convert(Book book) {
        return BookDto.builder()
                .id(book.getId())
                .title(book.getTitle())
                .author(book.getAuthor())
                .coverImage(book.getCoverImage())
                .description(book.getDescription())
                .lastUpdatedAt(book.getUpdatedAt() == null ? book.getCreatedAt() : book.getUpdatedAt())
                .categories(book.getCategories().stream().map(c -> convertSimple(c)).toList())
                .chapters(book.getChapters().stream().map(ch -> convert(ch)).toList())
                .averageRate(book.getRatings().stream()
                        .mapToDouble(Rating::getStar)
                        .average()
                        .orElse(0.0))
                .build();
    }

    public static CommentDto convert(Comment comment) {
        return CommentDto.builder()
                .commentAt(comment.getCreatedAt())
                .content(comment.getContent())
                .id(comment.getId())
                .username(comment.getUser().getUsername())
                .userAvatar(comment.getUser().getAvatar())
                .isEdited(comment.getEditedAt() != null)
                .build();
    }

    public static SimpleChapterDto convert(Chapter chapter) {
        return SimpleChapterDto.builder()
                .id(chapter.getId())
                .title(chapter.getTitle())
                .lastUpdatedAt(chapter.getUpdatedAt() == null ? chapter.getCreatedAt() : chapter.getUpdatedAt())
                .build();
    }

    public static CategoryDto convert(Category category) {
        return CategoryDto.builder()
                .id(category.getId())
                .name(category.getName())
                .description(category.getDescription())
                .build();
    }

    public static SimpleCategoryDto convertSimple(Category category) {
        return SimpleCategoryDto.builder()
                .id(category.getId())
                .name(category.getName())
                .build();
    }
}
