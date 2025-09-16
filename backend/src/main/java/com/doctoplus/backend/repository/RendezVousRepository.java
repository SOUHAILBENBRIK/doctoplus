package com.doctoplus.backend.repository;

import com.doctoplus.backend.entity.RendezVous;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface RendezVousRepository extends MongoRepository<RendezVous, String> {
    List<RendezVous> findByUserId(String userId);
    List<RendezVous> findByProId(String proId);
}
