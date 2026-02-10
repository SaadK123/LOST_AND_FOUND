package com.lostandfound.repository;

import com.lostandfound.entity.LostItem;
import com.lostandfound.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Repository
public interface LostItemRepository extends JpaRepository<LostItem, UUID> {

    List<LostItem> findByUser(User user);

    List<LostItem> findByStatus(String status);

    List<LostItem> findByCategory(String category);

    @Query("SELECT l FROM LostItem l WHERE l.category = :category AND l.status = 'active' " +
           "AND (6371 * acos(cos(radians(:latitude)) * cos(radians(l.latitude)) * " +
           "cos(radians(l.longitude) - radians(:longitude)) + " +
           "sin(radians(:latitude)) * sin(radians(l.latitude)))) <= :radiusKm")
    List<LostItem> findByCategoryAndLocationWithinRadius(
        @Param("category") String category,
        @Param("latitude") BigDecimal latitude,
        @Param("longitude") BigDecimal longitude,
        @Param("radiusKm") double radiusKm
    );

    @Query("SELECT l FROM LostItem l WHERE l.city = :city AND l.status = 'active'")
    List<LostItem> findByCity(@Param("city") String city);

    @Query("SELECT l FROM LostItem l WHERE l.country = :country AND l.status = 'active'")
    List<LostItem> findByCountry(@Param("country") String country);
}
