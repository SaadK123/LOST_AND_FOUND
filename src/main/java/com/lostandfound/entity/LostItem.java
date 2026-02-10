package com.lostandfound.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "lost_items", indexes = {
    @Index(name = "idx_lost_user", columnList = "user_id"),
    @Index(name = "idx_lost_category", columnList = "category"),
    @Index(name = "idx_lost_status", columnList = "status"),
    @Index(name = "idx_lost_location", columnList = "latitude,longitude")
})
@Data
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class LostItem {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String description;

    @Column(nullable = false)
    private String category;

    @Column(nullable = false)
    private LocalDate dateLost;

    @Column(nullable = false)
    private String locationName;

    private String country;
    private String city;

    @Column(precision = 10, scale = 8)
    private BigDecimal latitude;

    @Column(precision = 11, scale = 8)
    private BigDecimal longitude;

    @Column(nullable = false)
    private String status = "active"; // active, found, closed

    @Column(precision = 10, scale = 2)
    private BigDecimal rewardOffered;

    @OneToMany(mappedBy = "lostItem", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ItemImage> images = new ArrayList<>();

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;

    // Helper method to add image
    public void addImage(ItemImage image) {
        images.add(image);
        image.setLostItem(this);
    }
}
