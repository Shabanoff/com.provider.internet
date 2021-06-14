package com.provider.internet.repository;

import com.provider.internet.model.entity.IncludedPackage;
import com.provider.internet.model.entity.User;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository  extends JpaRepository<User, Long> {
    Page<User> findAll (Pageable pageable);
    Optional<User> getUserByLogin(String login);
}
