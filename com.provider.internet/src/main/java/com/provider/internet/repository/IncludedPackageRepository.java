package com.provider.internet.repository;

import com.provider.internet.model.entity.IncludedPackage;
import com.provider.internet.model.entity.Service;
import com.provider.internet.model.entity.Tariff;
import com.provider.internet.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface IncludedPackageRepository extends JpaRepository<IncludedPackage, Long> {
    boolean existsByTariffId(long tariffId);
    boolean existsByServiceId(long serviceId);
    List<IncludedPackage> findAllByUserId (long userId);
    Optional<IncludedPackage> findByUserAndService(User user, Service service);
    boolean existsByUserAndTariff (User user, Tariff tariff);
}
