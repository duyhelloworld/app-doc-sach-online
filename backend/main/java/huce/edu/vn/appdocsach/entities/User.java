package huce.edu.vn.appdocsach.entities;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(length = 100, nullable = false, unique = true)
    private String username;

    @Column(length = 150)
    private String fullname;

    @Column(length = 100)
    private String email;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String avatar;

    @ToString.Exclude
    private String password;

    @Enumerated(EnumType.STRING)
    private TokenProvider provider;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    @ToString.Exclude
    @JsonIgnore
    @OneToMany(mappedBy = "user")
    private List<Comment> comments = new ArrayList<>();

    @ToString.Exclude
    @JsonIgnore
    @OneToMany(mappedBy = "user")
    private List<Rating> ratings = new ArrayList<>();

    @Override
    public boolean equals(Object object) {
        User user = (User) object;
        return this.id == user.id && this.username.equals(user.username);
    }

    public User() {}

    public User(String username, String fullname, String email, String avatar, String password, TokenProvider provider, Role role) {
        this.username = username;
        this.fullname = fullname;
        this.email = email;
        this.avatar = avatar;
        this.password = password;
        this.provider = provider;
        this.role = role;
    }

}