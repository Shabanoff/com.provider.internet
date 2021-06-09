package com.provider.internet.repository;

import com.provider.internet.model.entity.Service;
import com.provider.internet.model.entity.Tariff;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Set;

public interface TariffRepository extends JpaRepository<Tariff, Long> {
    List<Tariff> getTariffsByService(Service service);

    List<Tariff> getTariffByServiceOrderByCostAsc(Service service);

    List<Tariff> getTariffByServiceOrderByCostDesc(Service service);
}
