package huce.edu.vn.appdocsach.entities;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class History {

    @EmbeddedId
    private HistoryId id;
    
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime lastRead;

    @Column(nullable = false)
    private boolean isCompleted;
}
