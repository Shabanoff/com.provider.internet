package com.provider.internet.repository;

import com.provider.internet.model.entity.Service;
import com.provider.internet.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ServiceRepository extends JpaRepository<Service, Long> {

}
