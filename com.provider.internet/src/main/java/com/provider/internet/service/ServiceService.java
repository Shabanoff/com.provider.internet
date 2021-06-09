package com.provider.internet.service;

import com.provider.internet.model.entity.Service;
import com.provider.internet.model.entity.Tariff;
import com.provider.internet.repository.IncludedPackageRepository;
import com.provider.internet.repository.ServiceRepository;
import lombok.RequiredArgsConstructor;

import java.util.*;
import java.util.stream.Collectors;


/**
 * Intermediate layer between command layer and dao layer. Implements operations of finding,
 * creating, deleting entities. Account dao layer.
 *
 * @author Shabanoff
 */
@org.springframework.stereotype.Service
@RequiredArgsConstructor
public class ServiceService {

    private final ServiceRepository serviceRepository;
    private final IncludedPackageRepository includedPackageRepository;

    public List<Service> findAllService() {
        return serviceRepository.findAll();
    }

    public Optional<Service> findServiceById(long serviceId) {
        return serviceRepository.findById(serviceId);
    }


    public void createService(Service service) {
        serviceRepository.save(service);
    }

    public List<Service> sortByCostTariff(String ascDesc) {
       List<Service> services = findAllService();
        if (ascDesc.equals("asc")) {
            for (Service service : services) {
                service.setTariffs(service.getTariffs().stream().sorted(Comparator.comparing(Tariff::getCost)).collect(Collectors.toCollection(LinkedHashSet::new)));
            }
            return services;
        }
            for (Service service : services) {
                service.setTariffs(service.getTariffs().stream().sorted(Comparator.comparing(Tariff::getCost).reversed()).collect(Collectors.toCollection(LinkedHashSet::new)));
            }
            return services;
        }


    public List<String> deleteService(long serviceId) {
        List<String> errors = new ArrayList<>();
        Optional<Service> currentService = findServiceById(serviceId);
        if (currentService.isPresent()) {
            if (includedPackageRepository.existsByServiceId(serviceId)) {
                errors.add("ADDED_BY_USER");
                return errors;
            }
            serviceRepository.delete(currentService.get());
        }
        return errors;
    }
}

