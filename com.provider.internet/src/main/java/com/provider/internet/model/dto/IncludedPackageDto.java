package com.provider.internet.model.dto;


import lombok.Data;

import java.time.LocalDate;

@Data
public class IncludedPackageDto {
    private long id;
    private LocalDate subscriptionDate;
    private ServiceDto service;
    private TariffDto tariff;


}
