package com.provider.internet.model.dto;

import lombok.Data;

import java.util.List;

@Data
public class ServiceDto {
    private long id;
    private String serviceName;
    private List<TariffDto> tariffs;

}
