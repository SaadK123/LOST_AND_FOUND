package com.lostandfound.service;

import com.lostandfound.entity.FoundItem;
import com.lostandfound.repository.FoundItemRepository;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class AISearchService {

    private final FoundItemRepository foundItemRepository;
    private final Gson gson = new Gson();

    @Value("${openai.api.key:}")
    private String openAiApiKey;

    public List<AIMatchResult> findMatches(String userDescription, String category,
                                           String country, String city, BigDecimal latitude,
                                           BigDecimal longitude, double radiusKm) {

        log.info("Starting AI search for category: {}, location: {}", category, city);

        // Step 1: Regional filtering (reduces millions to hundreds)
        List<FoundItem> regionalItems;
        if (city != null && !city.isEmpty()) {
            regionalItems = foundItemRepository.findByCity(city);
        } else if (country != null && !country.isEmpty()) {
            regionalItems = foundItemRepository.findByCountry(country);
        } else {
            regionalItems = foundItemRepository.findByCategoryAndLocationWithinRadius(
                category, latitude, longitude, radiusKm
            );
        }

        log.info("Found {} items in region after filtering", regionalItems.size());

        if (regionalItems.isEmpty()) {
            return Collections.emptyList();
        }

        // Step 2: For now, return simple text similarity matching
        // TODO: Integrate OpenAI API when key is configured
        if (openAiApiKey == null || openAiApiKey.isEmpty()) {
            log.warn("OpenAI API key not configured. Using simple text matching.");
            return simpleTextMatching(userDescription, regionalItems);
        }

        // Step 3: AI-powered matching (when API key is available)
        return aiPoweredMatching(userDescription, regionalItems);
    }

    private List<AIMatchResult> simpleTextMatching(String userDescription, List<FoundItem> items) {
        List<AIMatchResult> matches = new ArrayList<>();

        for (FoundItem item : items) {
            double score = calculateSimpleSimilarity(userDescription, item.getDescription());
            if (score >= 40) { // Minimum 40% match
                matches.add(new AIMatchResult(
                    item.getId(),
                    item,
                    (int) score,
                    "Text similarity based on keywords"
                ));
            }
        }

        return matches.stream()
            .sorted(Comparator.comparingInt(AIMatchResult::getScore).reversed())
            .collect(Collectors.toList());
    }

    private double calculateSimpleSimilarity(String desc1, String desc2) {
        String[] words1 = desc1.toLowerCase().split("\\s+");
        String[] words2 = desc2.toLowerCase().split("\\s+");

        Set<String> set1 = new HashSet<>(Arrays.asList(words1));
        Set<String> set2 = new HashSet<>(Arrays.asList(words2));

        Set<String> intersection = new HashSet<>(set1);
        intersection.retainAll(set2);

        Set<String> union = new HashSet<>(set1);
        union.addAll(set2);

        return union.isEmpty() ? 0 : (intersection.size() * 100.0 / union.size());
    }

    private List<AIMatchResult> aiPoweredMatching(String userDescription, List<FoundItem> items) {
        // TODO: Implement OpenAI API integration
        // For now, fallback to simple matching
        return simpleTextMatching(userDescription, items);
    }

    // Inner class for match results
    public static class AIMatchResult {
        private UUID itemId;
        private FoundItem item;
        private int score;
        private String reason;

        public AIMatchResult(UUID itemId, FoundItem item, int score, String reason) {
            this.itemId = itemId;
            this.item = item;
            this.score = score;
            this.reason = reason;
        }

        public UUID getItemId() { return itemId; }
        public FoundItem getItem() { return item; }
        public int getScore() { return score; }
        public String getReason() { return reason; }
    }
}
