package com.provider.internet.model;

import com.provider.internet.model.dto.TariffDto;
import com.provider.internet.model.entity.Tariff;
import com.provider.internet.model.mapper.TariffMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class TariffModelMapperUnitTest {

    @Autowired
    private TariffMapper mapper;

    @Test
    void mapTariffToTariffDto_shouldWorkCorrect() {
        Tariff tariff = new Tariff();
        tariff.setCost(BigDecimal.ZERO);
        tariff.setId(1L);
        tariff.setTariffName("test");

        TariffDto actual = mapper.tariffToTariffDto(tariff);

        assertThat(actual.getId()).isEqualTo(tariff.getId());
        assertThat(actual.getTariffName()).isEqualTo(tariff.getTariffName());
        assertThat(actual.getCost()).isEqualTo(tariff.getCost());
    }

    @Test
    void mapTariffToTariffDto_withNullValue_shouldWorkCorrect() {
        Tariff tariff = new Tariff();

        TariffDto actual = mapper.tariffToTariffDto(tariff);

        assertThat(actual.getTariffName()).isNull();
        assertThat(actual.getCost()).isNull();
    }

    @Test
    void mapTariffDtoToTariff_shouldWorkCorrect() {
        TariffDto tariffDto = new TariffDto();
        tariffDto.setCost(BigDecimal.ZERO);
        tariffDto.setId(1L);
        tariffDto.setTariffName("test");

        Tariff actual = mapper.tariffDtoToTariff(tariffDto);

        assertThat(actual.getId()).isEqualTo(tariffDto.getId());
        assertThat(actual.getTariffName()).isEqualTo(tariffDto.getTariffName());
        assertThat(actual.getCost()).isEqualTo(tariffDto.getCost());
    }

}

