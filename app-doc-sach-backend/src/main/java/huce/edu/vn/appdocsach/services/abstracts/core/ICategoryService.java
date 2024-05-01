package huce.edu.vn.appdocsach.services.abstracts.core;

import java.util.List;

import huce.edu.vn.appdocsach.dto.core.category.CategoryDto;
import huce.edu.vn.appdocsach.dto.core.category.CreateCategoryDto;
import huce.edu.vn.appdocsach.dto.core.category.SimpleCategoryDto;

public interface ICategoryService {
    List<SimpleCategoryDto> getAllSimpleCategory();

    boolean isEmpty();

    CategoryDto getCategoryById(Integer id);

    Integer createNew(CreateCategoryDto createCategoryDto);
}
