package com.lostandfound.controller;

import com.lostandfound.entity.LostItem;
import com.lostandfound.repository.LostItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/lost-items")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class LostItemController {

    private final LostItemRepository lostItemRepository;

    @GetMapping
    public ResponseEntity<List<LostItem>> getAllLostItems() {
        return ResponseEntity.ok(lostItemRepository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<LostItem> getLostItemById(@PathVariable UUID id) {
        return lostItemRepository.findById(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<LostItem> createLostItem(@RequestBody LostItem lostItem) {
        LostItem savedItem = lostItemRepository.save(lostItem);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedItem);
    }

    @GetMapping("/category/{category}")
    public ResponseEntity<List<LostItem>> getLostItemsByCategory(@PathVariable String category) {
        return ResponseEntity.ok(lostItemRepository.findByCategory(category));
    }

    @GetMapping("/city/{city}")
    public ResponseEntity<List<LostItem>> getLostItemsByCity(@PathVariable String city) {
        return ResponseEntity.ok(lostItemRepository.findByCity(city));
    }
}
