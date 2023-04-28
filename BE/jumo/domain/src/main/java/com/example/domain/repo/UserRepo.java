package com.example.domain.repo;

import com.example.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface UserRepo extends JpaRepository<User, Long> {

    User findByEmail(String email);
    User findBySocialId(String social_id);
}
