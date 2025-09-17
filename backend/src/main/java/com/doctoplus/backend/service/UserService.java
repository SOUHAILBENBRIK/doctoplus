package com.doctoplus.backend.service;



import com.doctoplus.backend.entity.Role;
import com.doctoplus.backend.entity.User;
import com.doctoplus.backend.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> getAllPatients() {
        return userRepository.findByRoleOrderByScoreDesc(Role.PATIENT);
    }

    public List<User> getAllPros() {
        return userRepository.findByRoleOrderByScoreDesc(Role.PRO);
    }

    public Optional<User> getUserById(String id) {
        return userRepository.findById(id);
    }


  // admin can delete any user
    public void deleteUser(String id) {
        userRepository.deleteById(id);
    }

}
