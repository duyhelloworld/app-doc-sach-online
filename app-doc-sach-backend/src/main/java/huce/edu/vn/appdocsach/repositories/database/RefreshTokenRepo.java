package huce.edu.vn.appdocsach.repositories.database;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import huce.edu.vn.appdocsach.entities.RefreshToken;
import huce.edu.vn.appdocsach.entities.User;

@Repository
public interface RefreshTokenRepo extends JpaRepository<RefreshToken, Integer> {
    
    Optional<RefreshToken> findByToken(String token);

    Optional<RefreshToken> findByUser(User user);

}
