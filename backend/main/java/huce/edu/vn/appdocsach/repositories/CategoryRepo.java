package huce.edu.vn.appdocsach.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import huce.edu.vn.appdocsach.entities.Category;

@Repository
public interface CategoryRepo extends JpaRepository<Category, Integer> {
    @Query("SELECT c FROM Category c WHERE c.id IN :categoryIds")
    List<Category> findByIds(@Param("categoryIds") List<Integer> categoryIds);

    boolean existsByName(String name);
}
