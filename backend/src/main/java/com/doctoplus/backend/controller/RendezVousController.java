package com.doctoplus.backend.controller;

import com.doctoplus.backend.entity.RendezVous;
import com.doctoplus.backend.service.RendezVousService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/rendezvous")
public class RendezVousController {
    private final RendezVousService rendezVousService;

    public RendezVousController(RendezVousService rendezVousService) {
        this.rendezVousService = rendezVousService;
    }

    @GetMapping("/user/{userId}")
    public List<RendezVous> getRendezVousByUser(@PathVariable String userId) {
        return rendezVousService.getByUser(userId);
    }

    @GetMapping("/pro/{proId}")
    public List<RendezVous> getRendezVousByPro(@PathVariable String proId) {
        return rendezVousService.getByPro(proId);
    }
    @GetMapping("/pro/me")
    public List<RendezVous> getRendezVousByMe() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return rendezVousService.getByProMe(email);
    }


    @GetMapping("/{id}")
    public Optional<RendezVous> getRendezVousById(@PathVariable String id) {
        return rendezVousService.getById(id);
    }

    @PostMapping
    public RendezVous createRendezVous(@RequestBody RendezVous rendezVous) {
        return rendezVousService.saveRendezVous(rendezVous);
    }

    @PostMapping("/{rendezVousId}/rate")
    public void ratePro(
            @PathVariable String rendezVousId,
            @RequestParam int rating
    ) {
        // Get the currently logged-in user's email
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        rendezVousService.ratePro(rendezVousId, rating, email);
    }

    @DeleteMapping("/{id}")
    public void deleteRendezVous(@PathVariable String id) {
        rendezVousService.deleteRendezVous(id);
    }
}
