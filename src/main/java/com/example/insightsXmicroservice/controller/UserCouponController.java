package com.example.insightsXmicroservice.controller;

import com.example.insightsXmicroservice.model.UserCouponResponse;
import com.example.insightsXmicroservice.service.CouponService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
public class UserCouponController {
    
    @Autowired
    private CouponService couponService;
    
    @GetMapping("/{userId}/coupons")  
    public ResponseEntity<Page<UserCouponResponse>> getUserCoupons(
        @PathVariable String userId,
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int size
    ) {
        // Your logic to fetch coupons for the user
        Page<UserCouponResponse> coupons = couponService.getUserCoupons(userId, page, size);
        return ResponseEntity.ok(coupons);
    }
}
