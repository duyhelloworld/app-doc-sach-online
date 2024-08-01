package huce.edu.vn.appdocsach.entities;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
public class User {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(length = 100, nullable = false, unique = true)
    private String username;

    @Column(length = 200)
    private String fullname;

    @Column(length = 100)
    private String email;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String avatar;

    @Column(length = 100)
    private String password;

    @Enumerated(EnumType.STRING)
    private TokenProvider provider;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    private Boolean isEnabled = true;

    private LocalDateTime willEnableAt;
    
    @OneToOne(mappedBy = "user")
    private RefreshToken refreshToken;

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

    @Override
    public boolean equals(Object object) {
        User user = (User) object;
        return this.id == user.id 
            && this.username.equals(user.username);
    }
}