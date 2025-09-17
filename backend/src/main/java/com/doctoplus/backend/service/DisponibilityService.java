package com.doctoplus.backend.service;

import com.doctoplus.backend.entity.Disponibility;
import com.doctoplus.backend.entity.RendezVous;
import com.doctoplus.backend.entity.Role;
import com.doctoplus.backend.entity.User;
import com.doctoplus.backend.repository.DisponibilityRepository;
import com.doctoplus.backend.repository.RendezVousRepository;
import com.doctoplus.backend.repository.UserRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;

@Service
public class DisponibilityService {
    private final DisponibilityRepository disponibilityRepository;
    private final UserRepository userRepository;
    private final RendezVousRepository rendezVousRepository;

    public DisponibilityService(DisponibilityRepository disponibilityRepository,
                                UserRepository userRepository , RendezVousRepository rendezVousRepository) {
        this.disponibilityRepository = disponibilityRepository;
        this.userRepository = userRepository;
        this.rendezVousRepository = rendezVousRepository;

    }

    public List<Disponibility> getDisponibilitiesByPro(String proId) {
        return disponibilityRepository.findByProId(proId);
    }
    public List<Disponibility> getDisponibilitiesByMe(String email) {
        User pro = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found."));
        return disponibilityRepository.findByProId(pro.getId());
    }


    public List<Map<String, String>> getAvailableSlotsWithDate(String proId) {
        List<Disponibility> disponibilities = disponibilityRepository.findByProId(proId);
        List<RendezVous> reservedRendezvous = rendezVousRepository.findByProId(proId);

        List<Map<String, String>> result = new ArrayList<>();

        for (Disponibility dispo : disponibilities) {
            for (LocalTime slot : dispo.getSlots()) {
                boolean isReserved = reservedRendezvous.stream()
                        .anyMatch(rv -> "RESERVED".equals(rv.getStatus()) &&
                                rv.getDateTime().toLocalTime().equals(slot) &&
                                rv.getDateTime().toLocalDate().equals(dispo.getStartTime().toLocalDate()));

                if (!isReserved) {
                    Map<String, String> slotInfo = new HashMap<>();
                    slotInfo.put("date", dispo.getStartTime().toLocalDate().toString());
                    slotInfo.put("slot", slot.toString());
                    result.add(slotInfo);
                }
            }
        }

        return result;
    }

    public Disponibility saveDisponibility(Disponibility disponibility) {
        // 🔹 Get the connected Pro from the token
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User pro = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found."));

        // 🔹 Only Pros can create disponibilities
        if (pro.getRole() != Role.PRO) {
            throw new RuntimeException("Only professionals can create disponibilities.");
        }

        // 🔹 Check if the same time range already exists
        boolean exists = disponibilityRepository.findByProId(pro.getId()).stream()
                .anyMatch(d ->
                        d.getStartTime().equals(disponibility.getStartTime()) &&
                                d.getEndTime().equals(disponibility.getEndTime())
                );

        if (exists) {
            throw new RuntimeException("This disponibility already exists for this time slot.");
        }

        // 🔹 Assign the Pro automatically
        disponibility.setPro(pro);

        // 🔹 Generate 1-hour slots between startTime and endTime
        LocalDateTime start = disponibility.getStartTime();
        LocalDateTime end = disponibility.getEndTime();
        List<LocalTime> slots = new ArrayList<>();

        while (start.plusHours(1).isBefore(end) || start.plusHours(1).isEqual(end)) {
            slots.add(start.toLocalTime());
            start = start.plusHours(1);
        }

        disponibility.setSlots(slots);

        return disponibilityRepository.save(disponibility);
    }


    public void deleteDisponibility(String id) {
        disponibilityRepository.deleteById(id);
    }

    public Optional<Disponibility> getDisponibilityById(String id) {
        return disponibilityRepository.findById(id);
    }
}
