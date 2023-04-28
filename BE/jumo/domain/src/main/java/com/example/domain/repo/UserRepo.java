package com.example.domain.repo;

import com.example.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepo extends JpaRepository<User, Long> {

    List<User> findByEmail(String email);
    List<User> findBySocialId(String social_id);

    Optional<User> findByEmailAndPassword(String email, String password);
}
