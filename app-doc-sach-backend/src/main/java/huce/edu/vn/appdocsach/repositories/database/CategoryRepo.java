package huce.edu.vn.appdocsach.repositories.database;

import org.springframework.stereotype.Repository;

import huce.edu.vn.appdocsach.entities.Category;
import huce.edu.vn.appdocsach.repositories.base.SoftDeleteRepository;

@Repository
public interface CategoryRepo extends SoftDeleteRepository<Category, Integer> {
    
    boolean existsByName(String name);
}
