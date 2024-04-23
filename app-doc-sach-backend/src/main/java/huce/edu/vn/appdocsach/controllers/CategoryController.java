package huce.edu.vn.appdocsach.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import huce.edu.vn.appdocsach.annotations.auth.IsAdmin;
import huce.edu.vn.appdocsach.dto.core.category.CategoryDto;
import huce.edu.vn.appdocsach.dto.core.category.CreateCategoryDto;
import huce.edu.vn.appdocsach.dto.core.category.SimpleCategoryDto;
import huce.edu.vn.appdocsach.services.core.CategoryService;
import io.swagger.v3.oas.annotations.Operation;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("api/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @Operation(summary = "Tìm tất cả thể loại, lấy cho trang home")
    @GetMapping("all")
    public List<SimpleCategoryDto> getAllCategory() {
        return categoryService.getAllSimpleCategory();
    }

    @Operation(summary = "Tìm thể loại theo id")
    @GetMapping("find/{id}")
    public CategoryDto getCategoryById(@PathVariable Integer id) {
        return categoryService.getCategoryById(id);
    }

    @Operation(summary = "Thêm mới thể loại")
    @IsAdmin
    @PostMapping
    public Integer addCategory(@RequestBody CreateCategoryDto createCategoryDto) {
        return categoryService.createNew(createCategoryDto);
    }
}