package huce.edu.vn.appdocsach.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import huce.edu.vn.appdocsach.entities.User;
import java.util.Optional;

@Repository
public interface UserRepo extends JpaRepository<User, Integer> {
    
    @Query("SELECT u FROM User u WHERE u.username = :username")
    Optional<User> findByUsername(@Param("username") String username);

    Boolean existsByUsername(String username);

    Boolean existsByEmail(String email);
}
