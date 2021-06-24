package com.provider.internet.model;

import com.provider.internet.model.dto.IncludedPackageDto;
import com.provider.internet.model.dto.ServiceDto;
import com.provider.internet.model.entity.IncludedPackage;
import com.provider.internet.model.entity.Service;
import com.provider.internet.model.mapper.IncludedPackageMapper;
import com.provider.internet.model.mapper.ServiceMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class IncludedPackageModelMapperUnitTest {

    @Autowired
    private IncludedPackageMapper mapper;

    @Test
    void mapIncludedPackageToIncludedPackageDto_shouldWorkCorrect() {
        IncludedPackage includedPackage = new IncludedPackage();
        includedPackage.setId(1L);
        includedPackage.setSubscriptionDate(LocalDate.now());

        IncludedPackageDto actual = mapper.includedPackageToIncludedPackageDto(includedPackage);

        assertThat(actual.getId()).isEqualTo(includedPackage.getId());
        assertThat(actual.getSubscriptionDate()).isEqualTo(includedPackage.getSubscriptionDate());
    }

    @Test
    void mapIncludedPackageToIncludedPackageDto_withNullValue_shouldWorkCorrect() {
        IncludedPackage includedPackage = new IncludedPackage();

        IncludedPackageDto actual = mapper.includedPackageToIncludedPackageDto(includedPackage);

        assertThat(actual.getSubscriptionDate()).isNull();
    }

    @Test
    void mapIncludedPackageDtoToIncludedPackage_shouldWorkCorrect() {
        IncludedPackageDto includedPackageDto = new IncludedPackageDto();
        includedPackageDto.setId(1L);
        includedPackageDto.setSubscriptionDate(LocalDate.now());

        IncludedPackage actual = mapper.includedPackageDtoToIncludedPackage(includedPackageDto);

        assertThat(actual.getId()).isEqualTo(includedPackageDto.getId());
        assertThat(actual.getSubscriptionDate()).isEqualTo(includedPackageDto.getSubscriptionDate());
    }

}

