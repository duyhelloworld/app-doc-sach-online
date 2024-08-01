package huce.edu.vn.appdocsach.services.abstracts.core;

import java.util.List;

import huce.edu.vn.appdocsach.dto.core.category.CategoryDetailDto;
import huce.edu.vn.appdocsach.dto.core.category.CreateCategoryDto;
import huce.edu.vn.appdocsach.dto.core.category.CategoryDto;
import huce.edu.vn.appdocsach.services.abstracts.test.ITestableService;

public interface ICategoryService extends ITestableService {

    /**
     * Lấy các thể loại 
     * @return list thể loại {@link CategoryDto}
     */
    List<CategoryDto> getAllCategory();

    /**
     * Lấy thông tin chi tiết thể loại theo mã
     * @param id mã thể loại
     * @return thông tin thể loại {@link CategoryDetailDto}
     */
    CategoryDetailDto getCategoryById(Integer id);

    /**
     * Tạo mới thể loại
     * @param createCategoryDto {@link CreateCategoryDto}
     * @return mã thể loại vừa tạo
     */
    Integer createNew(CreateCategoryDto createCategoryDto);
}
