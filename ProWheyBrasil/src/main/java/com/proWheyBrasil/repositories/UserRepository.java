package com.proWheyBrasil.repositories;

import com.proWheyBrasil.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


public interface UserRepository extends JpaRepository<User, String> {
    Optional<User> findByLogin (String Login);
}
