package huce.edu.vn.appdocsach.controllers;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import huce.edu.vn.appdocsach.annotations.auth.IsAdmin;
import huce.edu.vn.appdocsach.dto.core.category.CategoryDetailDto;
import huce.edu.vn.appdocsach.dto.core.category.CreateCategoryDto;
import huce.edu.vn.appdocsach.dto.core.category.CategoryDto;
import huce.edu.vn.appdocsach.services.abstracts.core.ICategoryService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("api/category")
@RequiredArgsConstructor
public class CategoryController {

    private final ICategoryService categoryService;

    @Operation(summary = "Tìm tất cả thể loại cho trang home")
    @GetMapping("all")
    public List<CategoryDto> getAllCategory() {
        return categoryService.getAllCategory();
    }

    @Operation(summary = "Tìm thể loại theo id")
    @GetMapping("find/{id}")
    public CategoryDetailDto getCategoryById(@PathVariable Integer id) {
        return categoryService.getCategoryById(id);
    }

    @Operation(summary = "Thêm mới thể loại")
    @IsAdmin
    @PostMapping
    public Integer addCategory(@RequestBody CreateCategoryDto createCategoryDto) {
        return categoryService.createNew(createCategoryDto);
    }
}