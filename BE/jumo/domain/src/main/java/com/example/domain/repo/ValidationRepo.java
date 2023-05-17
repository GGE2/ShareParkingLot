package com.example.domain.repo;

import com.example.domain.entity.PhoneValidation;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ValidationRepo extends CrudRepository<PhoneValidation, String> {
}
