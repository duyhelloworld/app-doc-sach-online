package huce.edu.vn.appdocsach.services.core;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import huce.edu.vn.appdocsach.dto.core.category.CategoryDto;
import huce.edu.vn.appdocsach.dto.core.category.CreateCategoryDto;
import huce.edu.vn.appdocsach.entities.Category;
import huce.edu.vn.appdocsach.exception.AppError;
import huce.edu.vn.appdocsach.exception.AppException;
import huce.edu.vn.appdocsach.repositories.CategoryRepo;
import huce.edu.vn.appdocsach.services.BaseService;
import huce.edu.vn.appdocsach.utils.AppLogger;
import jakarta.transaction.Transactional;

@Service
public class CategoryService extends BaseService<CategoryDto> {

    @Autowired
    private CategoryRepo categoryRepo;

    private AppLogger<CategoryService> logger = new AppLogger<>(CategoryService.class);

    @Transactional
    public List<CategoryDto> getAllCategory() {
        logger.onStart(Thread.currentThread());
        return categoryRepo.findAll().stream().map(c -> convert(c)).toList();
    }

    @Transactional
    public CategoryDto getCategoryById(Integer id) {
        logger.onStart(Thread.currentThread(), id);
        Category category = categoryRepo.findById(id)
                .orElseThrow(() -> new AppException(AppError.CategoryNotFound));
        return convert(category);
    }

    public Integer createNew(CreateCategoryDto createCategoryDto) {
        logger.onStart(Thread.currentThread());
        if (categoryRepo.existsByName(createCategoryDto.getName())) {
            throw new AppException(AppError.CategoryNameExisted);
        }
        Category category = new Category();
        category.setName(createCategoryDto.getName());
        category.setDescription(createCategoryDto.getDescription());
        categoryRepo.save(category);
        return category.getId();
    }

    private CategoryDto convert(Category category) {
        return CategoryDto.builder()
                .bookCount(category.getBooks().size())
                .description(category.getDescription())
                .id(category.getId())
                .name(category.getName())
                .build();
    }
}
