package huce.edu.vn.appdocsach.services.core;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import huce.edu.vn.appdocsach.dto.core.category.CategoryDto;
import huce.edu.vn.appdocsach.dto.core.category.CreateCategoryDto;
import huce.edu.vn.appdocsach.dto.core.category.SimpleCategoryDto;
import huce.edu.vn.appdocsach.entities.Category;
import huce.edu.vn.appdocsach.enums.ResponseCode;
import huce.edu.vn.appdocsach.exception.AppException;
import huce.edu.vn.appdocsach.repositories.CategoryRepo;
import huce.edu.vn.appdocsach.utils.AppLogger;
import jakarta.transaction.Transactional;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepo categoryRepo;

    private AppLogger<CategoryService> logger = new AppLogger<>(CategoryService.class);

    @Transactional
    public List<SimpleCategoryDto> getAllSimpleCategory() {
        logger.onStart(Thread.currentThread());
        return categoryRepo.findAll().stream().map(c -> convertSimple(c)).toList();
    }

    public boolean isEmpty() {
        return categoryRepo.count() == 0;
    }

    @Transactional
    public CategoryDto getCategoryById(Integer id) {
        logger.onStart(Thread.currentThread(), id);
        return convert(categoryRepo.findById(id)
                .orElseThrow(() -> new AppException(ResponseCode.CATEGORY_NOT_FOUND)));
    }

    @Transactional
    public Integer createNew(CreateCategoryDto createCategoryDto) {
        logger.onStart(Thread.currentThread());
        if (categoryRepo.existsByName(createCategoryDto.getName())) {
            throw new AppException(ResponseCode.CATEGORY_NAME_EXISTED);
        }
        Category category = new Category();
        category.setName(createCategoryDto.getName());
        category.setDescription(createCategoryDto.getDescription());
        categoryRepo.save(category);
        return category.getId();
    }

    public CategoryDto convert(Category category) {
        return CategoryDto.builder()
                .id(category.getId())
                .name(category.getName())
                .description(category.getDescription())
                .build();
    }

    public SimpleCategoryDto convertSimple(Category category) {
        return SimpleCategoryDto.builder()
                        .id(category.getId())
                        .name(category.getName())
                        .build();
    }
}
