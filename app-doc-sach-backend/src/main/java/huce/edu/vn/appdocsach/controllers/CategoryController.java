package huce.edu.vn.appdocsach.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import huce.edu.vn.appdocsach.annotations.auth.IsAdmin;
import huce.edu.vn.appdocsach.dto.core.category.CategoryDto;
import huce.edu.vn.appdocsach.dto.core.category.CreateCategoryDto;
import huce.edu.vn.appdocsach.dto.core.category.SimpleCategoryDto;
import huce.edu.vn.appdocsach.services.core.CategoryService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("api/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping("all")
    public List<SimpleCategoryDto> getAllCategory() {
        return categoryService.getAllSimpleCategory();
    }

    @GetMapping("find")
    public CategoryDto getCategoryById(@RequestParam Integer id) {
        return categoryService.getCategoryById(id);
    }

    @IsAdmin
    @PostMapping
    public Integer addCategory(@RequestBody CreateCategoryDto createCategoryDto) {
        return categoryService.createNew(createCategoryDto);
    }
}
