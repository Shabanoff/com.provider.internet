package com.provider.internet.service;

import com.provider.internet.model.entity.Tariff;
import com.provider.internet.repository.IncludedPackageRepository;
import com.provider.internet.repository.TariffRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;


/**
 * Intermediate layer between command layer and dao layer. Implements operations of finding,
 * creating, deleting entities. Account dao layer.
 *
 * @author Shabanoff
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class TariffService {

    private final TariffRepository tariffRepository;
    private final IncludedPackageRepository includedPackageRepository;

    public Optional<Tariff> findTariffById(long userId) {
        return tariffRepository.findById(userId);
    }

    public void createTariff(Tariff tariff) {
        Objects.requireNonNull(tariff);
        tariffRepository.save(tariff);
        log.info("Create tariff successfully");
    }

    public void deleteAllByServiceId(long serviceId) {
        tariffRepository.deleteAllByServiceId(serviceId);
    }


    public List<String> deleteTariff(long tariffId) {
        List<String> errors = new ArrayList<>();
        Optional<Tariff> currentTariff = findTariffById(tariffId);
        if (currentTariff.isPresent()) {
            if (includedPackageRepository.existsByTariffId(tariffId)) {
                errors.add("already.add");
                log.info("Tariff has already add by some user");
                return errors;
            }
            tariffRepository.delete(currentTariff.get());
            log.info("Delete tariff successfully");
        }
        return errors;
    }

    public void changeCost(Tariff tariff, BigDecimal cost) {
        Optional<Tariff> currentTariffOpt = findTariffById(tariff.getId());
        if (currentTariffOpt.isPresent()) {
            Tariff currentTariff = currentTariffOpt.get();
            currentTariff.setCost(cost);
            tariffRepository.save(tariff);
            log.info("Change cost successfully");
        }
    }
}

