package com.doctoplus.backend.service;

import com.doctoplus.backend.entity.Disponibility;
import com.doctoplus.backend.entity.RendezVous;
import com.doctoplus.backend.entity.Role;
import com.doctoplus.backend.entity.User;
import com.doctoplus.backend.repository.RendezVousRepository;
import com.doctoplus.backend.repository.UserRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Service
public class RendezVousService {
    private final RendezVousRepository rendezVousRepository;
    private final DisponibilityService disponibilityService;
    private final UserRepository userRepository;

    public RendezVousService(RendezVousRepository rendezVousRepository,
                             DisponibilityService disponibilityService,
                             UserRepository userRepository) {
        this.rendezVousRepository = rendezVousRepository;
        this.disponibilityService = disponibilityService;
        this.userRepository = userRepository;
    }

    public List<RendezVous> getByUser(String userId) {
        return rendezVousRepository.findByUserId(userId);
    }

    public List<RendezVous> getByPro(String proId) {
        return rendezVousRepository.findByProId(proId);
    }
    public List<RendezVous> getByProMe(String email) {
        User pro = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found."));
        return rendezVousRepository.findByProId(pro.getId());
    }

    public RendezVous saveRendezVous(RendezVous rendezVous) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User patient = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found."));

        if (patient.getRole() != Role.PATIENT) {
            throw new RuntimeException("Only patients can create a Appointment.");
        }

        rendezVous.setUserId(patient.getId());
        rendezVous.setStatus("RESERVED");

        User pro = rendezVous.getPro();
        List<Disponibility> disponibilities = disponibilityService.getDisponibilitiesByPro(pro.getId());

        boolean slotAvailable = false;

        for (Disponibility d : disponibilities) {
            // Check if the requested time matches one of the available 1-hour slots
            LocalTime requestedTime = rendezVous.getDateTime().toLocalTime();
            if (d.getSlots().contains(requestedTime)) {
                slotAvailable = true;
                break;
            }
        }

        if (!slotAvailable) {
            throw new RuntimeException("The professional is not available at this time.");
        }

        // Check if the slot is already reserved
        boolean conflict = rendezVousRepository.findByProId(pro.getId()).stream()
                .anyMatch(r -> "RESERVED".equals(r.getStatus()) &&
                        r.getDateTime().equals(rendezVous.getDateTime()));

        if (conflict) {
            throw new RuntimeException("This time slot is already reserved for this professional.");
        }

        return rendezVousRepository.save(rendezVous);
    }


    public void deleteRendezVous(String rendezVousId) {
        RendezVous rv = rendezVousRepository.findById(rendezVousId)
                .orElseThrow(() -> new RuntimeException("Appointments not found"));

        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User patient = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found."));

        if (!rv.getUserId().equals(patient.getId())) {
            throw new RuntimeException("You can only cancel your own Appointments.");
        }

        // Get the professional's disponibilities
        User pro = rv.getPro();
        List<Disponibility> disponibilities = disponibilityService.getDisponibilitiesByPro(pro.getId());

        for (Disponibility d : disponibilities) {
            LocalTime slotTime = rv.getDateTime().toLocalTime();
            if (!d.getSlots().contains(slotTime)) {
                d.getSlots().add(slotTime);
                disponibilityService.saveDisponibility(d);
                break;
            }
        }

        // Remove the RendezVous
        rendezVousRepository.deleteById(rendezVousId);
    }


    public void ratePro(String rendezVousId, int rating, String patientId) {
        RendezVous rv = rendezVousRepository.findById(rendezVousId)
                .orElseThrow(() -> new RuntimeException("Appointment not found"));

        if (!rv.getUserId().equals(patientId)) {
            throw new RuntimeException("You can only rate your own appointments");
        }


        User pro = rv.getPro();
        if (pro.getScore() == null) {
            pro.setScore(rating);
        } else {
            pro.setScore((pro.getScore() + rating) / 2);
        }

        userRepository.save(pro);
    }



    public Optional<RendezVous> getById(String id) {
        return rendezVousRepository.findById(id);
    }
}
