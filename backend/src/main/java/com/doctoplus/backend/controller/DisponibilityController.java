package com.doctoplus.backend.controller;



import com.doctoplus.backend.entity.Disponibility;
import com.doctoplus.backend.service.DisponibilityService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/disponibilities")
public class DisponibilityController {
    private final DisponibilityService disponibilityService;

    public DisponibilityController(DisponibilityService disponibilityService) {
        this.disponibilityService = disponibilityService;
    }

    @GetMapping("/pro/{proId}")
    public List<Map<String, String>> getDisponibilitiesByPro(@PathVariable String proId) {
        return disponibilityService.getAvailableSlotsWithDate(proId);
    }

    @GetMapping("/{id}")
    public Optional<Disponibility> getDisponibilityById(@PathVariable String id) {
        return disponibilityService.getDisponibilityById(id);
    }

    @PostMapping
    public Disponibility createDisponibility(@RequestBody Disponibility disponibility) {
        return disponibilityService.saveDisponibility(disponibility);
    }

    @DeleteMapping("/{id}")
    public void deleteDisponibility(@PathVariable String id) {
        disponibilityService.deleteDisponibility(id);
    }
}

