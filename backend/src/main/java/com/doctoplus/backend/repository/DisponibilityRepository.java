package com.doctoplus.backend.repository;

import com.doctoplus.backend.entity.Disponibility;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface DisponibilityRepository extends MongoRepository<Disponibility, String> {
    List<Disponibility> findByProId(String proId);
}
