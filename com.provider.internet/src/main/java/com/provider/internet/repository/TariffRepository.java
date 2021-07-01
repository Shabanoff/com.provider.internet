package com.provider.internet.repository;

import com.provider.internet.model.entity.Tariff;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TariffRepository extends JpaRepository<Tariff, Long> {
    void deleteAllByServiceId(long serviceId);

}
