package com.example.domain.repo;

import com.example.domain.entity.Notification;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotiRepo extends CrudRepository<Notification, Long> {

    List<Notification> findAllById(Long notiId);
}
