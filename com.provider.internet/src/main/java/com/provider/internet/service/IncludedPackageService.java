package com.provider.internet.service;

import com.provider.internet.model.entity.IncludedPackage;
import com.provider.internet.model.entity.Service;
import com.provider.internet.model.entity.Tariff;
import com.provider.internet.model.entity.User;
import com.provider.internet.repository.IncludedPackageRepository;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Objects;
import java.util.Optional;


/**
 * Intermediate layer between command layer and dao layer. Implements operations of finding,
 * creating, deleting entities. Account dao layer.
 *
 * @author Shabanoff
 */
@org.springframework.stereotype.Service
@RequiredArgsConstructor
public class IncludedPackageService {

    private final IncludedPackageRepository includedPackageRepository;

    public Optional<IncludedPackage> findIncludedPackageById(long serviceId) {
        return includedPackageRepository.findById(serviceId);
    }

    public List<IncludedPackage> findByUser(long userId) {
        return includedPackageRepository.findAllByUserId(userId);
    }

    public void updateIncludePackage(IncludedPackage includedPackage) {
        Objects.requireNonNull(includedPackage);
        includedPackageRepository.save(includedPackage);
    }

    public Optional<IncludedPackage> findByUserIdAndServiceId(User user, Service service) {
        return includedPackageRepository.findByUserAndService(user, service);
    }

    public boolean existsByUserIdAndTariffId(User user, Tariff tariff) {
        return includedPackageRepository.existsByUserAndTariff(user, tariff);
    }

    public void deleteIncludedPackage(long id) {
        if (findIncludedPackageById(id).isPresent())
            includedPackageRepository.delete(findIncludedPackageById(id).get());
    }
}

