package huce.edu.vn.appdocsach.repositories.base;

import java.time.LocalDateTime;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.lang.NonNull;

import huce.edu.vn.appdocsach.configs.AuditConfig;
import huce.edu.vn.appdocsach.entities.base.AuditedEntity;

@SuppressWarnings("hiding")
@NoRepositoryBean
public interface SoftDeleteRepository<T extends AuditedEntity, Integer> extends JpaRepository<T, Integer> {
    
    @Override
    default void delete(@NonNull AuditedEntity entity) {
        entity.setDeleteAt(LocalDateTime.now());
        entity.setDeleteBy(AuditConfig.extractAuditor());
    }

    @Override
    default void deleteById(@NonNull Integer id) {
        delete(findById(id).orElse(null));
    }
}
