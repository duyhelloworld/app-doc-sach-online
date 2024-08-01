package huce.edu.vn.appdocsach.services.impl.core;

import java.util.List;

import org.springframework.stereotype.Service;

import huce.edu.vn.appdocsach.dto.core.category.CategoryDetailDto;
import huce.edu.vn.appdocsach.dto.core.category.CreateCategoryDto;
import huce.edu.vn.appdocsach.dto.core.category.CategoryDto;
import huce.edu.vn.appdocsach.entities.Category;
import huce.edu.vn.appdocsach.enums.ResponseCode;
import huce.edu.vn.appdocsach.exception.AppException;
import huce.edu.vn.appdocsach.mapper.ModelMapper;
import huce.edu.vn.appdocsach.repositories.database.CategoryRepo;
import huce.edu.vn.appdocsach.services.abstracts.core.ICategoryService;
import huce.edu.vn.appdocsach.utils.JsonUtils;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@AllArgsConstructor
public class CategoryService implements ICategoryService {

    CategoryRepo categoryRepo;

    @Override
    @Transactional
    public List<CategoryDto> getAllCategory() {
        log.info("Start getAllCategory with input : ");
        return categoryRepo.findAll().stream().map(c -> ModelMapper.convertSimple(c)).toList();
    }

    @Override
    public boolean isEmpty() {
        return categoryRepo.count() == 0;
    }

    @Override
    @Transactional
    public CategoryDetailDto getCategoryById(Integer id) {
        log.info("Start getCategoryById with input : ", id);
        return ModelMapper.convert(categoryRepo.findById(id)
                .orElseThrow(() -> new AppException(ResponseCode.CATEGORY_NOT_FOUND)));
    }

    @Override
    @Transactional
    public Integer createNew(CreateCategoryDto createCategoryDto) {
        log.info("Start createNew with input : ", JsonUtils.json(createCategoryDto));
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
