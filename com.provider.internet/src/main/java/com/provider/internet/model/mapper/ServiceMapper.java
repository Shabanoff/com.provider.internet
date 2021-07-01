package com.provider.internet.model.mapper;

import com.provider.internet.model.dto.ServiceDto;
import com.provider.internet.model.entity.Service;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.NullValueCheckStrategy;

import java.util.List;

@Mapper(componentModel = "spring", uses = {TariffMapper.class}, nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
public interface ServiceMapper {

    Service serviceDtoToService(ServiceDto serviceDto);

    @Mapping(target = "tariffs", qualifiedByName = "tariffForm")
    ServiceDto deepServiceToServiceDto(Service service);

    @Mapping(target = "tariffs", ignore = true)
    @Named("serviceForm")
    ServiceDto serviceToServiceDto(Service service);

    List<ServiceDto> serviceListToServiceDtoList(List<Service> services);


}
