package huce.edu.vn.appdocsach.entities;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
@Entity
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(unique = true, nullable = false)
    private String title;

    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime createdAt;

    @Column(nullable = true)
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime updatedAt;

    @Column(nullable = false, length = 200)
    private String coverImage;

    @Column(length = 255)
    private String author;

    @Column(columnDefinition = "text")
    private String description;

    @Column(nullable = false)
    private Long viewCount;

    public void incViewCount() {
        this.viewCount++;
    }

    @JsonIgnore
    @ManyToMany
    @JoinTable(name = "books_categories", 
        joinColumns = @JoinColumn(name = "book_id"), 
        inverseJoinColumns = @JoinColumn(name = "category_id"))
    private List<Category> categories = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "book")
    private List<Chapter> chapters = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "book")
    private List<Rating> ratings = new ArrayList<>();
}
