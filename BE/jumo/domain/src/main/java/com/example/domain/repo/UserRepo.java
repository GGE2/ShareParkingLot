package com.example.domain.repo;

import com.example.domain.dto.user.UserProfileResponseDto;
import com.example.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public interface UserRepo extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);
    Optional<User> findBySocialId(String social_id);

    Optional<User> findByEmailAndPassword(String email, String password);

    @Query(value = "SELECT u.userId as user_id,u.name as name, u.profileImg as profile_img, u.email as email, ci.car_str as car_str, u.ptHas as pt_has, COUNT(t.credit_id) as total_transaction " +
            "FROM User u " +
            "LEFT JOIN CarInfo ci ON u.userId = ci.user.userId AND ci.car_rep = true " +
            "LEFT JOIN Transaction t ON u.userId = t.user.userId " +
            "WHERE u.userId = :id " +
            "GROUP BY u.name, u.profileImg, u.email, ci.car_str, u.ptHas", nativeQuery = false)
    Map<String,Object> selectProfileByUserId(@Param(value="id") Long user_id);


    @Query(value = "update User u set u.profileImg = :profile_img where u.userId = :user_id")
    User updateProfileImg(@Param(value="user_id") Long user_id, @Param(value = "profile_img") String profile_img);

}
