package huce.edu.vn.appdocsach.repositories.cache;

import org.springframework.data.keyvalue.repository.KeyValueRepository;
import org.springframework.stereotype.Repository;

import huce.edu.vn.appdocsach.entities.User;

@Repository
public interface RedisUserRepo extends KeyValueRepository<User, Integer> {
    
}
