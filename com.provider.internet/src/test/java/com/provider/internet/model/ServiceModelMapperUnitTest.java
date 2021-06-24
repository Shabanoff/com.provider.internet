package com.provider.internet.model;

import com.provider.internet.model.dto.ServiceDto;
import com.provider.internet.model.dto.TariffDto;
import com.provider.internet.model.entity.Service;
import com.provider.internet.model.entity.Tariff;
import com.provider.internet.model.mapper.ServiceMapper;
import com.provider.internet.model.mapper.TariffMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class ServiceModelMapperUnitTest {

    @Autowired
    private ServiceMapper mapper;

    @Test
    void mapServiceToServiceDto_shouldWorkCorrect() {
        Service service = new Service();
        service.setId(1L);
        service.setServiceName("test");

        ServiceDto actual = mapper.serviceToServiceDto(service);

        assertThat(actual.getId()).isEqualTo(service.getId());
        assertThat(actual.getServiceName()).isEqualTo(service.getServiceName());
    }

    @Test
    void mapServiceToServiceDto_withNullValue_shouldWorkCorrect() {
        Service service = new Service();

        ServiceDto actual = mapper.serviceToServiceDto(service);

        assertThat(actual.getServiceName()).isNull();
    }

    @Test
    void mapServiceDtoToService_shouldWorkCorrect() {
        ServiceDto serviceDto = new ServiceDto();
        serviceDto.setId(1L);
        serviceDto.setServiceName("test");

        Service actual = mapper.serviceDtoToService(serviceDto);

        assertThat(actual.getId()).isEqualTo(serviceDto.getId());
        assertThat(actual.getServiceName()).isEqualTo(serviceDto.getServiceName());
    }

}

