package com.lostandfound.controller;

import com.lostandfound.entity.FoundItem;
import com.lostandfound.repository.FoundItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/found-items")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class FoundItemController {

    private final FoundItemRepository foundItemRepository;

    @GetMapping
    public ResponseEntity<List<FoundItem>> getAllFoundItems() {
        return ResponseEntity.ok(foundItemRepository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<FoundItem> getFoundItemById(@PathVariable UUID id) {
        return foundItemRepository.findById(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<FoundItem> createFoundItem(@RequestBody FoundItem foundItem) {
        FoundItem savedItem = foundItemRepository.save(foundItem);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedItem);
    }

    @GetMapping("/category/{category}")
    public ResponseEntity<List<FoundItem>> getFoundItemsByCategory(@PathVariable String category) {
        return ResponseEntity.ok(foundItemRepository.findByCategory(category));
    }

    @GetMapping("/city/{city}")
    public ResponseEntity<List<FoundItem>> getFoundItemsByCity(@PathVariable String city) {
        return ResponseEntity.ok(foundItemRepository.findByCity(city));
    }
}
