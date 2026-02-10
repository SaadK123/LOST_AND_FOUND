package com.lostandfound.controller;

import com.lostandfound.service.AISearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/search")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class SearchController {

    private final AISearchService aiSearchService;

    @PostMapping
    public ResponseEntity<?> search(@RequestBody SearchRequest request) {
        List<AISearchService.AIMatchResult> matches = aiSearchService.findMatches(
            request.getDescription(),
            request.getCategory(),
            request.getCountry(),
            request.getCity(),
            request.getLatitude(),
            request.getLongitude(),
            request.getRadiusKm() != null ? request.getRadiusKm() : 10.0
        );

        return ResponseEntity.ok(Map.of(
            "matches", matches,
            "count", matches.size(),
            "message", "Search completed successfully"
        ));
    }

    // Inner class for request
    public static class SearchRequest {
        private String description;
        private String category;
        private String country;
        private String city;
        private BigDecimal latitude;
        private BigDecimal longitude;
        private Double radiusKm;

        // Getters and setters
        public String getDescription() { return description; }
        public void setDescription(String description) { this.description = description; }
        public String getCategory() { return category; }
        public void setCategory(String category) { this.category = category; }
        public String getCountry() { return country; }
        public void setCountry(String country) { this.country = country; }
        public String getCity() { return city; }
        public void setCity(String city) { this.city = city; }
        public BigDecimal getLatitude() { return latitude; }
        public void setLatitude(BigDecimal latitude) { this.latitude = latitude; }
        public BigDecimal getLongitude() { return longitude; }
        public void setLongitude(BigDecimal longitude) { this.longitude = longitude; }
        public Double getRadiusKm() { return radiusKm; }
        public void setRadiusKm(Double radiusKm) { this.radiusKm = radiusKm; }
    }
}
