package huce.edu.vn.appdocsach.services.impl.core;

import java.util.List;

import org.springframework.stereotype.Service;

import huce.edu.vn.appdocsach.dto.core.category.CategoryDto;
import huce.edu.vn.appdocsach.dto.core.category.CreateCategoryDto;
import huce.edu.vn.appdocsach.dto.core.category.SimpleCategoryDto;
import huce.edu.vn.appdocsach.entities.Category;
import huce.edu.vn.appdocsach.enums.ResponseCode;
import huce.edu.vn.appdocsach.exception.AppException;
import huce.edu.vn.appdocsach.repositories.CategoryRepo;
import huce.edu.vn.appdocsach.services.abstracts.core.ICategoryService;
import huce.edu.vn.appdocsach.utils.AppLogger;
import huce.edu.vn.appdocsach.utils.ConvertUtils;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CategoryService implements ICategoryService {

    CategoryRepo categoryRepo;

    AppLogger<CategoryService> logger;

    public CategoryService(CategoryRepo categoryRepo) {
        this.categoryRepo = categoryRepo;
        this.logger = new AppLogger<>(CategoryService.class);
    }

    @Override
    @Transactional
    public List<SimpleCategoryDto> getAllSimpleCategory() {
        logger.onStart(Thread.currentThread());
        return categoryRepo.findAll().stream().map(c -> ConvertUtils.convertSimple(c)).toList();
    }

    @Override
    public boolean isEmpty() {
        return categoryRepo.count() == 0;
    }

    @Override
    @Transactional
    public CategoryDto getCategoryById(Integer id) {
        logger.onStart(Thread.currentThread(), id);
        return ConvertUtils.convert(categoryRepo.findById(id)
                .orElseThrow(() -> new AppException(ResponseCode.CATEGORY_NOT_FOUND)));
    }

    @Override
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
}
