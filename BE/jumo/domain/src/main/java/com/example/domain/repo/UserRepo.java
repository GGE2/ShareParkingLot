package com.example.domain.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import com.example.domain.entity.User;
@Repository
public interface UserRepo extends JpaRepository<User, Long> {

    User findByEmail(String email);
    User findBySocialId(String social_id);
}
