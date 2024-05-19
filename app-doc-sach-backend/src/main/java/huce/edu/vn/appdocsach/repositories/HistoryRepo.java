package huce.edu.vn.appdocsach.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import huce.edu.vn.appdocsach.entities.History;

@Repository
public interface HistoryRepo extends JpaRepository<History, Integer> {
    
}
