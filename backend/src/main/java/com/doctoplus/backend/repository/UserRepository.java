package com.doctoplus.backend.repository;


import com.doctoplus.backend.entity.Role;
import com.doctoplus.backend.entity.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends MongoRepository<User, String> {
    Optional<User> findByEmail(String email);
    List<User> findByRoleOrderByScoreDesc(Role role);
}
