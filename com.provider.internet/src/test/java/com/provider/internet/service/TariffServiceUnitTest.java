package com.provider.internet.service;

import com.provider.internet.model.entity.Tariff;
import com.provider.internet.model.entity.User;
import com.provider.internet.model.enums.Status;
import com.provider.internet.repository.IncludedPackageRepository;
import com.provider.internet.repository.TariffRepository;
import com.provider.internet.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TariffServiceUnitTest {

    @Mock
    private TariffRepository tariffRepository;
    @InjectMocks
    private TariffService tariffService;

    @Test
    public void findById_worksCorrectly() {
        Tariff tariff = new Tariff();
        tariff.setId(1L);
        when(tariffService.findTariffById(tariff.getId())).thenReturn(Optional.of(tariff));
        assertThat(tariffService.findTariffById(tariff.getId()).get()).isEqualTo(tariff);
    }

    @Test
    public void changeTariffCost_worksCorrectly() {

        Tariff tariff = new Tariff();
        tariff.setId(1L);
        tariff.setCost(BigDecimal.ONE);

        when(tariffService.findTariffById(tariff.getId())).thenReturn(Optional.of(tariff));

        tariffService.changeCost(tariff, BigDecimal.TEN);

        ArgumentCaptor<Tariff> saveCapture = ArgumentCaptor.forClass(Tariff.class);
        verify(tariffRepository).save(saveCapture.capture());
        Tariff actual = saveCapture.getValue();
        assertThat(actual.getCost()).isEqualTo(BigDecimal.TEN);
    }

    @Test
    public void changeTariffCostNotFound_worksCorrectly() {

        Tariff tariff = new Tariff();
        tariff.setId(1L);
        tariff.setCost(BigDecimal.ONE);

         when(tariffService.findTariffById(tariff.getId())).thenReturn(Optional.empty());

        tariffService.changeCost(tariff, BigDecimal.TEN);

        verify(tariffRepository, never()).save(any(Tariff.class));
    }
}
