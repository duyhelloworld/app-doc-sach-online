package huce.edu.vn.appdocsach.entities.base;

import java.time.LocalDateTime;

import org.hibernate.annotations.SoftDelete;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class AuditedEntity {

    @Column(updatable = false, nullable = false)
    @CreatedDate
    private LocalDateTime createAt;

    @Column(length = 100, nullable = false)
    @CreatedBy
    private String createBy;
    
    @Column(insertable = false)
    @LastModifiedDate
    private LocalDateTime updateAt;

    @Column(length = 100, insertable = false)
    @LastModifiedBy
    private String updateBy;

    public LocalDateTime getLastUpdateAt() {
        return updateAt == null ? createAt : updateAt;
    }

    @Column(insertable = false)
    private LocalDateTime deleteAt;

    @Column(insertable = false)
    private String deleteBy;

    @Column(name = "is_deleted")
    @SoftDelete(columnName = "is_deleted")
    private boolean isDeleted = false;

    // If manual audit
    // @PrePersist
    // public void preInsert() {
    //     createAt = LocalDateTime.now();
    //     createBy = AuditConfig.extractAuditor();
    // }

    // @PreUpdate
    // public void preUpdate() {
    //     System.out.println("Pre Update");
    //     updateAt = LocalDateTime.now();
    //     updateBy = AuditConfig.extractAuditor();
    // }
}
