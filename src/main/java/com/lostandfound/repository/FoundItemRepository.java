package com.lostandfound.repository;

import com.lostandfound.entity.FoundItem;
import com.lostandfound.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Repository
public interface FoundItemRepository extends JpaRepository<FoundItem, UUID> {

    List<FoundItem> findByUser(User user);

    List<FoundItem> findByStatus(String status);

    List<FoundItem> findByCategory(String category);

    // Regional filtering for AI search - filters by city/country/radius FIRST
    @Query("SELECT f FROM FoundItem f WHERE f.category = :category AND f.status = 'active' " +
           "AND (6371 * acos(cos(radians(:latitude)) * cos(radians(f.latitude)) * " +
           "cos(radians(f.longitude) - radians(:longitude)) + " +
           "sin(radians(:latitude)) * sin(radians(f.latitude)))) <= :radiusKm")
    List<FoundItem> findByCategoryAndLocationWithinRadius(
        @Param("category") String category,
        @Param("latitude") BigDecimal latitude,
        @Param("longitude") BigDecimal longitude,
        @Param("radiusKm") double radiusKm
    );

    @Query("SELECT f FROM FoundItem f WHERE f.city = :city AND f.status = 'active'")
    List<FoundItem> findByCity(@Param("city") String city);

    @Query("SELECT f FROM FoundItem f WHERE f.country = :country AND f.status = 'active'")
    List<FoundItem> findByCountry(@Param("country") String country);

    // For AI search - get items within region for processing
    @Query("SELECT f FROM FoundItem f WHERE f.country = :country " +
           "AND (:city IS NULL OR f.city = :city) " +
           "AND f.category = :category AND f.status = 'active'")
    List<FoundItem> findByRegion(
        @Param("country") String country,
        @Param("city") String city,
        @Param("category") String category
    );
}
