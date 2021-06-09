package com.provider.internet.model.dto;


import com.provider.internet.model.entity.Tariff;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
public class IncludedPackageDto {
    private long id;
    private LocalDate subscriptionDate;
    private ServiceDto service;
    private TariffDto tariff;


}
