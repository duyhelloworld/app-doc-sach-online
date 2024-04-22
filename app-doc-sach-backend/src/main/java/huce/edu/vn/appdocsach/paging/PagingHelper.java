package huce.edu.vn.appdocsach.paging;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.lang.NonNull;

import huce.edu.vn.appdocsach.entities.Book;
import huce.edu.vn.appdocsach.entities.Category;
import huce.edu.vn.appdocsach.entities.Chapter;
import huce.edu.vn.appdocsach.entities.Comment;

public abstract class PagingHelper {
    private static Map<Class<?>, List<String>> validSortProps;

    // Call on CommandLineRunner
    public static void init() {
        if (validSortProps == null) {
            validSortProps = new HashMap<>();
            validSortProps.put(Book.class, List.of("id", "title", "createdAt",
                    "updatedAt", "author", "description", "ratings"));
            validSortProps.put(Chapter.class, List.of("id", "title", "createdAt",
                    "updatedAt"));
            validSortProps.put(Comment.class, List.of("id", "content", "createdAt",
                    "updatedAt"));
            validSortProps.put(Category.class, List.of("id", "name", "description"));
        }
    }

    public static PageRequest pageRequest(Class<?> clazz, @NonNull PagingRequest request) {
        List<String> validProps = validSortProps.get(clazz);
        Sort sort = Sort.unsorted();
        if (validProps != null && validProps.contains(request.getSortBy())) {
            sort = Sort.by(request.getSortBy());
        }
        return PageRequest.of(request.getPageNumber() - 1, request.getPageSize(), sort);
    }
}
