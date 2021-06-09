package com.provider.internet.service;

import com.provider.internet.model.entity.IncludedOption;
import com.provider.internet.model.entity.Tariff;
import com.provider.internet.repository.IncludedOptionRepository;
import com.provider.internet.repository.IncludedPackageRepository;
import com.provider.internet.repository.ServiceRepository;
import com.provider.internet.repository.TariffRepository;
import lombok.RequiredArgsConstructor;
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
public class IncludedOptionService {
    private final IncludedOptionRepository includedOptionRepository;
    public List<IncludedOption> findAllIncludedOption() {
        return includedOptionRepository.findAll();
    }

    public Optional<IncludedOption> findIncludedOptionById(long userId) {
        return includedOptionRepository.findById(userId);
    }

    public void createIncludedOption(IncludedOption includedOption) {
        Objects.requireNonNull(includedOption);
        includedOptionRepository.save(includedOption);
    }


    public void deleteIncludedOption(long includedOptionId) {
        if (findIncludedOptionById(includedOptionId).isPresent())
        includedOptionRepository.delete(findIncludedOptionById(includedOptionId).get());

    }

}

