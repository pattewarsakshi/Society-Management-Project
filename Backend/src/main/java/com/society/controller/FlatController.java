package com.society.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.society.entity.Flat;
import com.society.service.FlatService;

@RestController
@RequestMapping("/api/flats")
public class FlatController {

    private final FlatService flatService;

    public FlatController(FlatService flatService) {
        this.flatService = flatService;
    }

    // ================= ADD FLAT =================
    @PostMapping("/society/{societyId}")
    public ResponseEntity<Flat> addFlat(
            @PathVariable Integer societyId,
            @RequestBody Flat flat) {

        return ResponseEntity.ok(flatService.addFlat(societyId, flat));
    }

    // ================= GET ALL FLATS =================
    @GetMapping("/society/{societyId}")
    public ResponseEntity<List<Flat>> getFlats(
            @PathVariable Integer societyId) {

        return ResponseEntity.ok(flatService.getFlatsBySociety(societyId));
    }

    // ================= GET FLAT BY ID =================
    @GetMapping("/{flatId}")
    public ResponseEntity<Flat> getFlat(@PathVariable Integer flatId) {
        return ResponseEntity.ok(flatService.getFlatById(flatId));
    }
    
    // ================= UPDATE FLAT =================
    @PutMapping("/{flatId}")
    public ResponseEntity<Flat> updateFlat(
            @PathVariable Integer flatId,
            @RequestBody Flat flat) {

        return ResponseEntity.ok(flatService.updateFlat(flatId, flat));
    }

    // ================= DELETE FLAT =================
    @DeleteMapping("/{flatId}")
    public ResponseEntity<String> deleteFlat(@PathVariable Integer flatId) {
        flatService.deleteFlat(flatId);
        return ResponseEntity.ok("Flat deleted successfully");
    }
}
