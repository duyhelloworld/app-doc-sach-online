package huce.edu.vn.appdocsach.entities;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import lombok.Data;

@Data
@Entity
public class History {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @ManyToOne
    @JoinColumn(nullable = false)
    private User user;
    
    @ManyToOne
    @JoinColumn(nullable = false)
    private Book book;

    @Column(nullable = false)
    private Integer lastChapterId;

    @Temporal(TemporalType.DATE)
    private LocalDate lastReadAt;
}
