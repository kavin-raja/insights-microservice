package com.example.insightsXmicroservice.entity;

import jakarta.persistence.*;
import lombok.*;
import java.util.UUID;

@Entity
@Table(name = "user_coupons")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserCoupon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_coupon_id")
    private Integer userCouponId;

    @Column(name = "user_id", nullable = false)
    private String userId;

    @Column(name = "coupon_id", columnDefinition = "uuid", nullable = false)
    private UUID couponId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "coupon_id", insertable = false, updatable = false)
    private Coupon coupon;

    @Column(name = "claimed_at", nullable = false)
    private java.sql.Timestamp claimedAt;
}
