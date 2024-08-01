package huce.edu.vn.appdocsach.entities;

import java.util.ArrayList;
import java.util.List;

import huce.edu.vn.appdocsach.entities.base.AuditedEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
public class Book extends AuditedEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(unique = true, nullable = false)
    private String title;

    @Column(nullable = false, length = 200)
    private String coverImage;

    @Column(length = 255)
    private String author;

    @Column(columnDefinition = "text")
    private String description;

    @Column(nullable = false)
    private Long viewCount = 0l;

    public void incrementViewCount() {
        this.viewCount++;
    }

    @ManyToMany
    @JoinTable(name = "books_categories", 
        joinColumns = @JoinColumn(name = "book_id"), 
        inverseJoinColumns = @JoinColumn(name = "category_id"))
    private List<Category> categories = new ArrayList<>();

    @OneToMany(mappedBy = "book")
    private List<Chapter> chapters = new ArrayList<>();

    @OneToMany(mappedBy = "book")
    private List<Rating> ratings = new ArrayList<>();

    @OneToMany(mappedBy = "book")
    private List<History> histories = new ArrayList<>();
}
