package com.example.insightsXmicroservice.service;

import com.example.insightsXmicroservice.entity.Coupon;
import com.example.insightsXmicroservice.entity.UserCoupon;
import com.example.insightsXmicroservice.model.UserCouponResponse;
import com.example.insightsXmicroservice.repository.UserCouponRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor               
@Transactional(readOnly = true)
public class CouponService {

    private final UserCouponRepository userCouponRepository;

    public Page<UserCouponResponse> getUserCoupons(String userId, int page, int size) {

        if (userId == null || userId.isBlank()) {
            throw new IllegalArgumentException("User ID must not be null or empty");
        }

        Pageable pageable = PageRequest.of(page, size);

        return userCouponRepository
        .findByUserIdWithCoupon(userId, pageable) 
        .map(this::mapToResponse);

    }

    private UserCouponResponse mapToResponse(UserCoupon uc) {

        Coupon c = uc.getCoupon();

        long claimed = uc.getClaimedAt() != null
                ? uc.getClaimedAt().getTime()
                : 0L;

        long expires = claimed + 30L * 24 * 60 * 60 * 1_000;  

        String status = System.currentTimeMillis() > expires ? "EXPIRED" : "ACTIVE";

        // Build response DTO
        UserCouponResponse res = new UserCouponResponse();
        res.setUserCouponId(uc.getUserCouponId());
        res.setTitle( c != null ? blankSafe(c.getTitle())      : "" );
        res.setBrand( c != null ? blankSafe(c.getBrand())      : "" );
        res.setImageUrl( c != null ? blankSafe(c.getImageUrl()): "" );
        res.setPricePoints( c != null && c.getPricePoints() != null ? c.getPricePoints() : 0 );
        res.setClaimedAt(claimed);
        res.setExpiresAt(expires);
        res.setStatus(status);
        res.setCouponCode(generateCouponCode(uc.getUserCouponId()));
        return res;
    }

    private static String blankSafe(String s) {
        return s == null ? "" : s;
    }

    private String generateCouponCode(Integer id) {
        // COUP000123
        return "COUP" + String.format("%06d", id);
    }
}
