package com.example.insightsXmicroservice.entity;

import jakarta.persistence.*;
import lombok.*;
import java.util.UUID;

@Entity
@Table(name = "coupons")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Coupon {

    @Id
    @Column(name = "coupon_id", columnDefinition = "uuid")
    private UUID couponId;

    @Column(name = "image_key")
    private String imageKey;

    @Column(name = "image_url")
    private String imageUrl;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "brand")
    private String brand;

    @Column(name = "price_points", nullable = false)
    private Integer pricePoints;
}
