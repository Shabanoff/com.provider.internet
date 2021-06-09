package com.provider.internet.model.dto;

import com.provider.internet.model.entity.IncludedOption;
import com.provider.internet.model.entity.Service;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class TariffDto {
    private long id;
    private String tariffName;
    private BigDecimal cost;
    private ServiceDto service;
    private List<IncludedOptionDto> includedOptions;

}
