package com.example.insightsXmicroservice.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserCouponResponse {
    
    private Integer userCouponId;
    private String title;
    private String brand;
    private String imageUrl;
    private Integer pricePoints;
    private Long claimedAt;
    private String couponCode;
    private String status;
    private Long expiresAt;
}
