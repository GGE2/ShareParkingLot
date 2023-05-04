package com.example.domain.repo;



import com.example.domain.entity.FcmToken;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NotiRepo extends CrudRepository<FcmToken, Long> {

}
