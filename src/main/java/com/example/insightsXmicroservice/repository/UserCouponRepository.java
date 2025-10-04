package com.example.insightsXmicroservice.repository;

import com.example.insightsXmicroservice.entity.UserCoupon;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserCouponRepository extends JpaRepository<UserCoupon, Integer> {
    
    @Query("SELECT uc FROM UserCoupon uc JOIN FETCH uc.coupon WHERE uc.userId = :userId")
    Page<UserCoupon> findByUserIdWithCoupon(@Param("userId") String userId, Pageable pageable);
}
