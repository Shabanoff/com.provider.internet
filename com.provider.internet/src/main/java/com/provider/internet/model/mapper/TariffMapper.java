package com.provider.internet.model.mapper;

import com.provider.internet.model.dto.IncludedOptionDto;
import com.provider.internet.model.dto.TariffDto;
import com.provider.internet.model.entity.IncludedOption;
import com.provider.internet.model.entity.Tariff;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.NullValueCheckStrategy;

import java.util.List;

@Mapper(componentModel = "spring", uses = {OptionMapper.class, ServiceMapper.class},nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
public interface TariffMapper {

    Tariff tariffDtoToTariff(TariffDto tariffDto);

    @Mapping(target = "service", qualifiedByName = "serviceForm")
    TariffDto deepTariffToTariffDto(Tariff tariff);

    @Mapping(target = "service", ignore = true)
    @Named("tariffForm")
    TariffDto tariffToTariffDto(Tariff tariff);

    List<TariffDto> tariffsToTariffsDtoList(List<Tariff> tariffs);


}
