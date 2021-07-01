package com.provider.internet.repository;

import com.provider.internet.model.entity.Service;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ServiceRepository extends JpaRepository<Service, Long> {

}
